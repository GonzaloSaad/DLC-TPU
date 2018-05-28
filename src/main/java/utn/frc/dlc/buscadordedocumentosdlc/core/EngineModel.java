package utn.frc.dlc.buscadordedocumentosdlc.core;



import com.google.api.services.drive.model.File;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utn.frc.dlc.buscadordedocumentosdlc.core.io.management.*;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.Document;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.VocabularyEntry;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EngineModel {
    private static final Logger logger = Logger.getLogger(EngineModel.class.getName());
    private static EngineModel instance;
    private final Map<String, VocabularyEntry> VOCABULARY;
    private final Map<String, Integer> DOC_ID_MAP;
    private final List<String> FOLDERS_INDEXED;

    public static EngineModel getInstance() {
        if (instance == null) {
            instance = new EngineModel();
        }
        return instance;
    }

    private EngineModel() {

        Map<String, Integer> dmap = null;
        Map<String, VocabularyEntry> voc = null;

        List<String> flist = FolderListManagement.getInstance().getFolderList();
        if (flist != null) {
            dmap = DocumentMapManagement.getInstance().getDocumentMap();
            if (dmap !=null) {
                voc = VocabularyManagement.getInstance().getVocabulary();
            }
        }


        if (voc == null || dmap == null || flist==null) {
            voc = new HashMap<>();
            dmap = new HashMap<>();
            flist = new ArrayList<>();
            clearWorkingDirectory();
            logger.log(Level.INFO, "No data recovered, vocabulary and doc map initialized.");
        } else {
            logger.log(Level.INFO, MessageFormat.format("Vocabulary recovered with [{0}] terms. Doc map recovered with [{1}] docs. Indexed Document recovered with [{2}].", voc.size(), dmap.size(), flist.size()));
            logger.log(Level.INFO, MessageFormat.format("Indexed folders: {0}",flist));

        }
        VOCABULARY = voc;
        DOC_ID_MAP = dmap;
        FOLDERS_INDEXED = flist;
    }



    private void clearWorkingDirectory() {
        InternalFoldersManagement.getInstance().clearAll();
    }

    public Map<String, VocabularyEntry> getVocabulary() {
        return VOCABULARY;
    }

    public VocabularyEntry getFromVocabulary(String term) {
        return getVocabulary().get(term);
    }

    public void addToVocabulary(VocabularyEntry ve) {
        getVocabulary().put(ve.getTerm(), ve);
    }

    public Map<String, Integer> getDocMap() {
        return DOC_ID_MAP;
    }

    public Integer getFromDocMap(File file) {
        return getDocMap().get(file.getName());
    }

    public void addToDocMap(File file, int docID) {
        getDocMap().put(file.getName(), docID);
        persistDocument(file, docID);
    }

    public List<String> getFoldersIndexed() {
        return FOLDERS_INDEXED;
    }

    public boolean wasFolderIndexed(String uid){
        return getFoldersIndexed().contains(uid);
    }

    public void addIndexedFolder(String uid){
        if (!wasFolderIndexed(uid)){
            getFoldersIndexed().add(uid);
        }
    }

    public void commit() {
        persistDocMap();
        persistVocabulary();
        persistIndexedFolderList();
    }

    private void persistVocabulary() {
        VocabularyManagement.getInstance().saveVocabulary(getVocabulary());
    }

    private void persistDocMap() {
        DocumentMapManagement.getInstance().saveDocumentMap(getDocMap());
    }

    private void persistIndexedFolderList(){
        FolderListManagement.getInstance().saveFolderList(getFoldersIndexed());
    }

    private void persistDocument(File file, int docID) {
        new DocumentPersistingThread(file, docID).start();
    }

    private class DocumentPersistingThread extends Thread {

        Document doc;

        public DocumentPersistingThread(File file, int docID) {
            doc = new Document(file.getId(),file.getName(),file.getWebContentLink(),file.getWebViewLink(),docID);

        }

        @Override
        public void run() {
            DocumentManagement.getInstance().saveDocument(doc);
        }
    }
}
