/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core.io.management;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utn.frc.dlc.buscadordedocumentosdlc.core.DLCConstantsAndProperties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;


/**
 * @author Gonzalo
 */
public class InternalFoldersManagement {
    private static final String ROOT_PATH = "dlc";
    private static InternalFoldersManagement instance;
    private static final Logger logger = Logger.getLogger(InternalFoldersManagement.class.getName());
    private static final File ROOT_FILE = new File(DLCConstantsAndProperties.getCompletePath(ROOT_PATH));

    private InternalFoldersManagement() {

    }

    public static InternalFoldersManagement getInstance() {
        if (instance == null) {
            instance = new InternalFoldersManagement();
        }
        return instance;
    }

    public void clearAll() {

        try {
            delete(ROOT_FILE);
            logger.log(Level.INFO, "All files erased.");
        } catch (Exception e) {
            
            throw new RuntimeException("Failed to clear files, system will not be consistent");
        }
    }

    private void delete(File f) throws IOException {
        if (f.isDirectory()) {
            logger.log(Level.INFO, MessageFormat.format("Cleaning [{0}]", f.getName()));
            for (File c : f.listFiles())
                delete(c);
        } else {
            if (!f.delete()) {
                throw new FileNotFoundException("Failed to delete file: " + f);
            }
        }
    }
}
