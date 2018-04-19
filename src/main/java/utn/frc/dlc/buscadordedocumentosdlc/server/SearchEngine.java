/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import utn.frc.dlc.buscadordedocumentosdlc.core.searchengine.modelcomponents.DocumentResult;

/**
 *
 * @author gonzalo.saad
 */
public interface SearchEngine {
    public List<DocumentResult> getDocumentsForSearch(String search);
    
    public void runIndexation(File folderToIndex, boolean resetModel) throws FileNotFoundException;
}
