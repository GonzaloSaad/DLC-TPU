/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core.io.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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
        Object o = null;
        InputStream in = DLCObjectReader.class.getClassLoader().getResourceAsStream(path);
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
       
        ObjectInputStream p = null;
        try {
            p = new ObjectInputStream(in);
        } catch (IOException ex) {
            Logger.getLogger(DLCObjectReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            o = p.readObject();
        } catch (IOException ex) {
            Logger.getLogger(DLCObjectReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DLCObjectReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            p.close();
        } catch (IOException ex) {
            Logger.getLogger(DLCObjectReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        return (T) o;
    }
}
