/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core;



import com.google.api.services.drive.model.File;
import utn.frc.dlc.buscadordedocumentosdlc.core.io.cache.Cache;
import utn.frc.dlc.buscadordedocumentosdlc.core.io.cache.IntermediateCache;
import utn.frc.dlc.buscadordedocumentosdlc.core.io.management.PostPackManagement;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.PostList;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.VocabularyEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gonzalo
 */
public class IndexHelper {

    private static Logger logger = Logger.getLogger(IndexHelper.class.getName());
    private Cache cache;

    public IndexHelper() {
        startCache();
    }

    private void startCache() {
        cache = new IntermediateCache(DLCConstantsAndProperties.INDEX_CACHE_SIZE);
    }

    private int getNextFileIndex() {
        return (EngineModel.getInstance().getVocabulary().size() % DLCConstantsAndProperties.POST_FILES);
    }

    private int getNextDocumentID() {
        return EngineModel.getInstance().getDocMap().size();
    }

    public PostList getPostList(VocabularyEntry ve) {

        if (ve==null){
            return null;
        }

        PostList pl = null;

        String term = ve.getTerm();
        int file = ve.getPostFile();

        Map<String, PostList> postPack = getPostPack(file);

        if (postPack != null) {
            pl = postPack.get(term);
            if (pl == null) {
                pl = new PostList();
                postPack.put(term, pl);
            }
        }

        return pl;
    }

    public VocabularyEntry getVocabularyEntryForTerm(String term) {

        VocabularyEntry ve = EngineModel.getInstance().getFromVocabulary(term);

        if (ve == null) {
            int postFile = getNextFileIndex();
            ve = new VocabularyEntry(term, postFile);
            EngineModel.getInstance().addToVocabulary(ve);
        }

        return ve;
    }

    public int getDocumentID(File file) {
        Integer docID = EngineModel.getInstance().getFromDocMap(file);
        if (docID == null) {
            docID = getNextDocumentID();
            EngineModel.getInstance().addToDocMap(file, docID);
            logger.log(Level.INFO, "Document [{0}] did not exist. Created entry with [{1}] sequence number.", new Object[]{file.getName(), docID});
        } else {
            logger.log(Level.INFO, "Document [{0}] did exist, with [{1}] sequence number.", new Object[]{file.getName(), docID});
        }
        return docID;
    }

    private Map<String, PostList> getPostPack(int file) {
        Map<String, PostList> postPack = cache.getPostPack(file);

        if (postPack == null) {
            postPack = new HashMap<>();
            cache.putPostPack(postPack, file);
        }
        return postPack;
    }

    public void commit(boolean parallel) {
        logger.log(Level.INFO, "Saving indexes and cache to storage.");
        cache.dump(parallel);
        EngineModel.getInstance().commit();
    }

    public void commit() {
        commit(true);
    }

    public void finishIndexing() {
        logger.log(Level.INFO, "Finalize indexing...");
        commit(false);
        logger.log(Level.INFO, "Done.");

    }



}
