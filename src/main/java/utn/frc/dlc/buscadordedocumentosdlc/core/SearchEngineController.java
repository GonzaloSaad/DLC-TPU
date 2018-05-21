/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core;


import com.google.api.services.drive.model.File;
import org.apache.commons.io.FileUtils;
import utn.frc.dlc.buscadordedocumentosdlc.core.files.FileParser;
import utn.frc.dlc.buscadordedocumentosdlc.core.files.FolderFileList;
import utn.frc.dlc.buscadordedocumentosdlc.core.googledrive.GoogleDriveDowloader;
import utn.frc.dlc.buscadordedocumentosdlc.core.googledrive.GoogleDriveFileList;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.Document;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.PostList;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.VocabularyEntry;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Gonzalo
 */
public class SearchEngineController {

    private static final Logger logger = Logger.getLogger(SearchEngineController.class.getSimpleName());
    private static final SearchHelper searchHelper = new SearchHelper();
    private static SearchEngineController instance;

    public static SearchEngineController getInstance()  {
        if (instance==null){
            instance = new SearchEngineController();
        }
        return instance;
    }

    public SearchEngineController()  {
        EngineModel.getInstance();
    }

    public List<Document> getDocumentsForQuery(String query) throws IOException {
        logger.log(Level.INFO, "Query: {0}",query);
        return searchHelper.handle(query);
    }

    public void runIndexation(String folderUID) throws IOException, GeneralSecurityException {

            logger.log(Level.INFO, "Starting indexing.");

            IndexHelper indexHelper = new IndexHelper();

            int indexedTerms = 0;
            long sizeOfIndexed = 0;

            GoogleDriveFileList drive = new GoogleDriveFileList(folderUID);

            for (File f : drive) {
                long sizeOfFile = 0;
                sizeOfIndexed += sizeOfFile;
                logger.log(Level.INFO, "Document to ingest: [{0}] \tSize: {1}KB\tTotal: {2}KB.", new Object[]{f.getName(), sizeOfFile,sizeOfIndexed});


                boolean shouldSave = false;
                int termsRed = 0;

                Integer docID = indexHelper.getDocumentID(f);

                String text = read(f);
                FileParser fp = new FileParser(text);

                for (String term : fp) {
                    if (!term.trim().isEmpty()) {

                        termsRed++;
                        indexedTerms++;

                        if (indexedTerms % DLCConstants.LIMIT_WITHOUT_SAVE == 0) {
                            shouldSave = true;
                        }

                        VocabularyEntry ve = indexHelper.getVocabularyEntryForTerm(term);
                        PostList pl = indexHelper.getPostList(ve);

                        ve.addTermOcurrance();
                        pl.addDocument(docID);

                    }
                }
                logger.log(Level.INFO, "Terms red for document [{0}]. Total terms indexed [{1}]", new Object[]{termsRed, indexedTerms});
                if (shouldSave) {
                    indexHelper.commit();
                }
            }
            logger.log(Level.INFO, "Terms red [{0}].", indexedTerms);
            indexHelper.finishIndexing();
            searchHelper.update();

        }

        private String read(com.google.api.services.drive.model.File file) throws IOException {
            return GoogleDriveDowloader.download(file);
        }


}
