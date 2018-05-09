/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frc.utn.searchcore.io.management;

import com.frc.utn.searchcore.io.util.DLCObjectReader;
import com.frc.utn.searchcore.io.util.DLCObjectWriter;

import java.util.Map;

/**
 *
 * @author Gonzalo
 */
public class DocumentMapManagement {
    
    private static final String DOCUMENT_MAP_PATH = "src/main/resources/dlc/dmap.dlc";
    private static com.frc.utn.searchcore.io.management.DocumentMapManagement instance;
    
    private DocumentMapManagement(){
       
    }
    
    public static com.frc.utn.searchcore.io.management.DocumentMapManagement getInstance(){
        if(instance == null){
            instance = new com.frc.utn.searchcore.io.management.DocumentMapManagement();
        }
        return instance;
    }
    
    public Map<String,Integer> getDocumentMap(){
        DLCObjectReader<Map<String,Integer>> or = new DLCObjectReader();
        return or.read(DOCUMENT_MAP_PATH);
    }
    
    public void saveDocumentMap(Map<String,Integer> map){
        DLCObjectWriter<Map<String,Integer>> ow = new DLCObjectWriter();
        ow.write(map, DOCUMENT_MAP_PATH);
    }
}
