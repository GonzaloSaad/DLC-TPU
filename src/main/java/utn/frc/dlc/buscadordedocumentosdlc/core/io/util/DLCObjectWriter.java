/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core.io.util;

import utn.frc.dlc.buscadordedocumentosdlc.core.DLCConstantsAndProperties;

import java.io.*;

/**
 *
 * @author Gonzalo
 */
public class DLCObjectWriter<T> {

    
    public DLCObjectWriter() {
        
    }

    
    public boolean write(T o, String path) {
        
        try {
            File file = new File(DLCConstantsAndProperties.getCompletePath(path));
            if (!file.exists()){
                file.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(file);
            ObjectOutput objectOutput = new ObjectOutputStream(outputStream);
            objectOutput.writeObject(o);
            objectOutput.flush();
            outputStream.close();
            
        } catch (Exception e) {            
            return false;
        } finally{
                    
        }
        return true;
    }
}
