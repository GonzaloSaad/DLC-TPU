/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core.searchengine;


import utn.frc.dlc.buscadordedocumentosdlc.core.filesmanagement.FolderFileList;
import utn.frc.dlc.buscadordedocumentosdlc.core.filesmanagement.FileParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import utn.frc.dlc.buscadordedocumentosdlc.core.searchengine.modelcomponents.DocumentResult;
import utn.frc.dlc.buscadordedocumentosdlc.server.SearchEngine;

/**
 *
 * @author Gonzalo
 */
public class SearchEngineController implements SearchEngine {

    private SearchEngineModel searchEngineModel;

    public SearchEngineController() {
        this(new SearchEngineModel());
    }

    public SearchEngineController(SearchEngineModel model) {
        searchEngineModel = model;
    }
    
    private void resetModel(){
        searchEngineModel.reset();
    }

    private void index(String term, File document) {
        searchEngineModel.processTerm(term, document);
    }

    @Override
    public void runIndexation(File folderToIndex, boolean resetModel) throws FileNotFoundException {
        
        if (resetModel){
            resetModel();
        }       
        
        FolderFileList fl = new FolderFileList(folderToIndex);

        for (File file : fl) {
            FileParser fp = new FileParser(file);            
            for (String term : fp) {
                this.index(term, file);
            }
        }
        
        

    }
    
    @Override
    public List<DocumentResult> getDocumentsForSearch(String search){
        return null;
    }

}
