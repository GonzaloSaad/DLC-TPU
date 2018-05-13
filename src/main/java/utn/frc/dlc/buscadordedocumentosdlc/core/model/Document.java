/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core.model;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author gonzalo.saad
 */
public class Document implements Serializable {
    private final String id;
    private final String name;
    private final String webContentLink;
    private final String webViewLink;
    private int DLCID;

    public Document(String ID, String name, String downloadLink, String viewLink, int id) {
        this.id = ID;
        this.name = name;
        this.webContentLink = downloadLink;
        this.webViewLink = viewLink;
        this.DLCID = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWebContentLink() {
        return webContentLink;
    }

    public String getWebViewLink() {
        return webViewLink;
    }

    public int getDLCID() {
        return DLCID;
    }




}
