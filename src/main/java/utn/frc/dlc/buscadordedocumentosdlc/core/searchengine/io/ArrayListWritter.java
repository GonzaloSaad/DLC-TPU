/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core.searchengine.io;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Gonzalo
 */
public class ArrayListWritter {

    // nombre del archivo serializado...
    private final String PATH; 

    /**
     * Crea un objeto ArrayListWritter. Fija el nombre del archivo que se graba
     * con el nombre tomado como parametro.
     *
     * @param nom - el nombre del archivo a grabar.
     */
    public ArrayListWritter(String nom) {
        PATH = nom;
    }

    /**
     * Guarda un array en un archivo.
     *
     * @param al - el array a guardar.
     * @return - true si puede grabar.
     */
    public boolean write(ArrayList al) {

        try (FileOutputStream ostream = new FileOutputStream(PATH)) {
            ObjectOutputStream p = new ObjectOutputStream(ostream);

            p.writeObject(al);

            p.flush();
        } catch (Exception e) {
            System.out.println(e); //***************************** CHECK
            return false;
        }
        return true;
    }
}
