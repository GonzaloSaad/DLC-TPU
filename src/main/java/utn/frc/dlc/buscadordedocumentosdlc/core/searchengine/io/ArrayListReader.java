/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core.searchengine.io;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 *
 * @author Gonzalo
 */
public class ArrayListReader {

    private String PATH;

    /**
     * Crea un objeto SimpleListReader. Fija el nombre del archivo desde el cual
     * se recupera con el nombre tomado como parametro.
     *
     * @param nom - el nombre del archivo a abrir para iniciar la recuperacion.
     */
    public ArrayListReader(String nom) {
        PATH = nom;
    }

    /**
     * Recupera un objeto arraylist. 
     * 
     * @return - el objeto si lo encuentra, null si hay un error. 
     */
    public ArrayList read() {
        ArrayList al;

        try (FileInputStream istream = new FileInputStream(PATH)) {
            ObjectInputStream p = new ObjectInputStream(istream);

            al = (ArrayList) p.readObject();

            p.close();
        } catch (Exception e) {
            al = null;            
        }

        return al;
    }
}
