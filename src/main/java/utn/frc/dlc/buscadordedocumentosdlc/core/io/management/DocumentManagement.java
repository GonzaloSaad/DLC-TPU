/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core.io.management;


import utn.frc.dlc.buscadordedocumentosdlc.core.io.util.DLCObjectReader;
import utn.frc.dlc.buscadordedocumentosdlc.core.io.util.DLCObjectWriter;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.Document;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Gonzalo
 */
public class DocumentManagement {

    private static final String DOCUMENTS_PATH = "src/main/resources/dlc/docs/doc";
    private static final String DOCUMENT_EXTENSION = ".dlc";
    private static DocumentManagement instance;

    private DocumentManagement() {

    }

    public static DocumentManagement getInstance() {
        if (instance == null) {
            instance = new DocumentManagement();
        }
        return instance;
    }

    public Document getDocument(int docId) {
        DLCObjectReader<Document> or = new DLCObjectReader<>();
        return or.read(createPath(docId));
    }

    public void saveDocument(Document doc) {
        DLCObjectWriter<Document> ow = new DLCObjectWriter<>();
        ow.write(doc, createPath(doc.getDLCID()));
    }

    private String createPath(int docId) {
        return DOCUMENTS_PATH + StringUtils.leftPad(Integer.toString(docId),3,"0") + DOCUMENT_EXTENSION;
    }
}
