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

    /**
     * @return the idS
     */
    public java.lang.String getIdS() {
        return idS;
    }

    /**
     * @return the nameS
     */
    public java.lang.String getNameS() {
        return nameS;
    }

    /**
     * @return the webS
     */
    public java.lang.String getWebS() {
        return webS;
    }

    /**
     * @return the contS
     */
    public java.lang.String getContS() {
        return contS;
    }
    private final String id;
    private final String name;
    private final String webContentLink;
    private final String webViewLink;
    private int DLCID;
    private static final long serialVersionUID = -7524606415607260318L;
    
    private java.lang.String idS;
    private java.lang.String nameS;
    private java.lang.String webS;
    private java.lang.String contS;
    
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
