/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core.io.util;

import utn.frc.dlc.buscadordedocumentosdlc.core.DLCConstantsAndProperties;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalo
 */
public class DLCObjectReader<T> {

    public DLCObjectReader() {

    }

    public T read(String path) {

        Object o;
        File file = new File(DLCConstantsAndProperties.getCompletePath(path));

        try(InputStream inputStream = new FileInputStream(file)) {
            ObjectInput objectInput = new ObjectInputStream(inputStream);
            o = objectInput.readObject();
            objectInput.close();
        }catch (IOException | ClassNotFoundException e){
            return null;
        }

        return (T) o;
    }
}
