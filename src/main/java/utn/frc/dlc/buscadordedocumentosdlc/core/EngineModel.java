package utn.frc.dlc.buscadordedocumentosdlc.core;



import com.google.api.services.drive.model.File;
import utn.frc.dlc.buscadordedocumentosdlc.core.io.management.DocumentManagement;
import utn.frc.dlc.buscadordedocumentosdlc.core.io.management.DocumentMapManagement;
import utn.frc.dlc.buscadordedocumentosdlc.core.io.management.InternalFoldersManagement;
import utn.frc.dlc.buscadordedocumentosdlc.core.io.management.VocabularyManagement;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.Document;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.VocabularyEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EngineModel {
    private static final Logger logger = Logger.getLogger(EngineModel.class.getName());
    private static EngineModel instance;
    private final Map<String, VocabularyEntry> VOCABULARY;
    private final Map<String, Integer> DOC_ID_MAP;

    public static EngineModel getInstance() {
        if (instance == null) {
            instance = new EngineModel();
        }
        return instance;
    }

    private EngineModel() {

        Map<String, Integer> dmap = DocumentMapManagement.getInstance().getDocumentMap();
        Map<String, VocabularyEntry> voc = VocabularyManagement.getInstance().getVocabulary();

        if (voc == null || dmap == null) {
            voc = new HashMap<>();
            dmap = new HashMap<>();
            clearWorkingDirectory();
            logger.log(Level.INFO, "No data recovered, vocabulary and doc map initialized.");
        } else {
            logger.log(Level.INFO, "Vocabulary recovered with [{0}] terms. Doc map recovered with [{1}] docs.", new Object[]{voc.size(), dmap.size()});
        }
        VOCABULARY = voc;
        DOC_ID_MAP = dmap;
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

    public Integer getFromDocMap(com.google.api.services.drive.model.File file) {
        return getDocMap().get(file.getName());
    }

    public void addToDocMap(com.google.api.services.drive.model.File file, int docID) {
        getDocMap().put(file.getName(), docID);
        persistDocument(file, docID);
    }

    public void commit() {
        persistDocMap();
        persistVocabulary();
    }

    private void persistVocabulary() {
        VocabularyManagement.getInstance().saveVocabulary(VOCABULARY);
    }

    private void persistDocMap() {
        DocumentMapManagement.getInstance().saveDocumentMap(DOC_ID_MAP);
    }

    private void persistDocument(com.google.api.services.drive.model.File file, int docID) {
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
