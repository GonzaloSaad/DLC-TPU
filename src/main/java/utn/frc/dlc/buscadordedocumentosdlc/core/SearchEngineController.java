/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core;


import com.google.api.services.drive.model.File;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utn.frc.dlc.buscadordedocumentosdlc.core.files.FileParser;
import utn.frc.dlc.buscadordedocumentosdlc.core.googledrive.GoogleDriveDowloader;
import utn.frc.dlc.buscadordedocumentosdlc.core.googledrive.GoogleDriveFileList;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.Document;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.PostList;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.VocabularyEntry;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;


/**
 * @author Gonzalo
 */
public class SearchEngineController {

    private static final Logger logger = Logger.getLogger(SearchEngineController.class.getSimpleName());
    private static final SearchHelper searchHelper = new SearchHelper();
    private static SearchEngineController instance;

    public static SearchEngineController getInstance() {
        if (instance == null) {
            instance = new SearchEngineController();
        }
        return instance;
    }

    public SearchEngineController() {
        EngineModel.getInstance();
    }

    public List<Document> getDocumentsForQuery(String query) {

        Instant start = Instant.now();
        List<Document> results = searchHelper.handle(query);
        Instant end = Instant.now();
        Duration duration = Duration.between(start,end);
        logger.log(Level.INFO, MessageFormat.format("Results: {0}\t Time: {1}ms \tQuery: {2}", results.size(), duration.toMillis(),query));
        return results;
    }

    public void runIndexation(String folderUID) throws IOException {

        if (!folderUID.matches(DLCConstantsAndProperties.GOOGLE_DRIVE_FOLDER_UID_REGEX)) {
            logger.log(Level.ERROR, MessageFormat.format("The string [{0}] is not a folder uid.", folderUID));
            return;
        }

        logger.log(Level.INFO, MessageFormat.format("Folder [{0}] to be indexed.", folderUID));

        if (EngineModel.getInstance().wasFolderIndexed(folderUID)) {
            logger.log(Level.INFO, "Folder already indexed.");
            return;
        }

        GoogleDriveFileList drive;
        try {
            drive = new GoogleDriveFileList(folderUID);
        } catch (Exception e){
            logger.log(Level.ERROR, MessageFormat.format("There was an error loading drive. [{0}].", e.getClass()));
            return;
        }

        if (drive.isEmpty()){
            logger.log(Level.ERROR, "The folder is empty.");
            return;
        }

        logger.log(Level.INFO, "Starting indexing.");
        Instant start = Instant.now();
        IndexHelper indexHelper = new IndexHelper();

        int indexedTerms = 0;

        for (File f : drive) {

            logger.log(Level.INFO, MessageFormat.format("Document to ingest: [{0}]", f.getName()));

            Integer docID = indexHelper.getDocumentID(f);

            if (docID == DLCConstantsAndProperties.DOCUMENT_ALREADY_INDEXED) {
                logger.log(Level.INFO, "Document already existed.");
                continue;
            }

            boolean shouldSave = false;
            int termsRed = 0;

            String text = read(f);
            FileParser fp = new FileParser(text);

            for (String term : fp) {
                if (!term.trim().isEmpty()) {

                    termsRed++;
                    indexedTerms++;

                    if (indexedTerms % DLCConstantsAndProperties.LIMIT_WITHOUT_SAVE == 0) {
                        shouldSave = true;
                    }

                    VocabularyEntry ve = indexHelper.getVocabularyEntryForTerm(term);
                    PostList pl = indexHelper.getPostList(ve);

                    ve.addTermOcurrance();
                    pl.addDocument(docID);

                }
            }
            logger.log(Level.INFO, MessageFormat.format("Terms red for document [{0}]. Total terms indexed [{1}]", termsRed, indexedTerms));
            if (shouldSave) {
                indexHelper.commit();
            }
        }

        EngineModel.getInstance().addIndexedFolder(folderUID);
        indexHelper.finishIndexing();
        searchHelper.update();

        Instant end = Instant.now();
        Duration duration = Duration.between(start,end);
        logger.log(Level.INFO, MessageFormat.format("Terms red {0}\tTime: {1}mins.", indexedTerms, duration.toMinutes()));


    }

    private String read(com.google.api.services.drive.model.File file) throws IOException {
        return GoogleDriveDowloader.download(file);
    }


}
