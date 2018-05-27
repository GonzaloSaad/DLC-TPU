/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core.model;

import java.io.Serializable;

/**
 *
 * @author Gonzalo
 */
public class VocabularyEntry implements Serializable {

    private final String TERM;
    private final int POST_FILE;
    private int TF;


    public int getPostFile() {
        return POST_FILE;
    }

    public VocabularyEntry(String term, int file) {
        TERM = term;
        TF = 1;
        POST_FILE = file;

    }

    public String getTerm() {
        return TERM;
    }

    public void addTermOcurrance() {
        TF++;
    }

    public int getTF() {
        return TF;
    }
}
