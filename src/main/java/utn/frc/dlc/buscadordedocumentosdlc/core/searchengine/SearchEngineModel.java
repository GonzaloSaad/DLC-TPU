/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core.searchengine;

import utn.frc.dlc.buscadordedocumentosdlc.core.searchengine.modelcomponents.PostEntry;
import utn.frc.dlc.buscadordedocumentosdlc.core.searchengine.modelcomponents.VocabularyEntry;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gonzalo
 */
public class SearchEngineModel {

    private final Map<String, VocabularyEntry> VOCABULARY;
    private final Map<String, PostEntry> POST;

    public SearchEngineModel() {
        this(new HashMap<String, VocabularyEntry>(), new HashMap<String,PostEntry>());
    }

    public SearchEngineModel(Map<String, VocabularyEntry> vocabulary, Map<String, PostEntry> post) {
        VOCABULARY = vocabulary;
        POST = post;

    }
    
    public void reset(){
        VOCABULARY.clear();
        POST.clear();
    }
    
    private Map<String, VocabularyEntry> getVocabulary(){
        return VOCABULARY;
    }
    
    private void addToVocabulary(String term, VocabularyEntry ve){
        VOCABULARY.put(term, ve);
    }
    
    private VocabularyEntry getFromVocabulary(String term){
        return VOCABULARY.get(term);
    }
    
    private Map<String, PostEntry> getPost(){
        return POST;
    }
    
    private void addToPost(String term, PostEntry pe){
        POST.put(term, pe);
    }
    
    private PostEntry getFromPost(String term){
        return POST.get(term);
    }

    private VocabularyEntry getVocabularyEntryForTerm(String term) {

        VocabularyEntry ve = getFromVocabulary(term) ;

        if (ve == null) {
            ve = new VocabularyEntry(term);
            addToVocabulary(term,ve);
        }

        return ve;

    }

    private PostEntry getPostEntryForTerm(String term) {
        PostEntry pe = getFromPost(term);

        if (pe == null) {
            pe = new PostEntry();
            addToPost(term,pe);
        }

        return pe;
    }

    public void processTerm(String term, File document) {

        VocabularyEntry ve = getVocabularyEntryForTerm(term);
        ve.addTermOcurrance();

        PostEntry pe = getPostEntryForTerm(term);
        boolean documentExisted = pe.addDocument(document, ve.getTF());
        
        if (!documentExisted){
            ve.addDocumentAppearance();
        }
    }

}
