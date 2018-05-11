/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core.searchengine.modelcomponents;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gonzalo
 */
public class PostEntry {
   
    private Map<File, Integer> DOCUMENT_LIST;
    private int TF;

    public PostEntry() {
        this(0);
    }

    public PostEntry(int tf) {        
        DOCUMENT_LIST = new HashMap();
        TF = tf;
    }

    public boolean addDocument(File document) {
        return addDocument(document, 1);
    }

    public boolean addDocument(File document, int tf) {
        // INSERT IN D-ORDER TODOOOOO
        boolean documentExisted = DOCUMENT_LIST.get(document) != null;
        DOCUMENT_LIST.put(document, tf);        
        return documentExisted;
    }

}
