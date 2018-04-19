/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.core.filesmanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Gonzalo
 */
public class FolderFileList implements Iterable<File> {

    private final ArrayList<File> FOLDER_FILE_LIST;

    public FolderFileList(String path) {
        this(new File(path));
    }
    
    public FolderFileList(File folder){
        FOLDER_FILE_LIST = listFilesForFolder(folder);
    }

    public final ArrayList<File> listFilesForFolder(File folder) {

        ArrayList<File> files = new ArrayList();

        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                files.add(fileEntry);
            }
        }
        return files;
    }

    @Override
    public Iterator<File> iterator() {
        return FOLDER_FILE_LIST.iterator();
    }

}
