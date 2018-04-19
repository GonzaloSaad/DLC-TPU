/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core.searchengine.modelcomponents;

import java.io.Serializable;

/**
 *
 * @author Gonzalo
 */
public class VocabularyEntry implements Serializable {

    private final String TERM;
    private int TF;
    private int Nr;

    public VocabularyEntry(String term) {
        TERM = term;
        TF = 0;
        Nr = 0;
    }

    public String getTerm() {
        return TERM;
    }

    public void addTermOcurrance() {
        TF++;
    }

    public void addDocumentAppearance() {
        Nr++;
    }

    public int getTF() {
        return TF;
    }

    public int getNr() {
        return Nr;
    }

}
