package utn.frc.dlc.buscadordedocumentosdlc.core.io.management;

import utn.frc.dlc.buscadordedocumentosdlc.core.io.util.DLCObjectReader;
import utn.frc.dlc.buscadordedocumentosdlc.core.io.util.DLCObjectWriter;

import java.util.List;

public class FolderListManagement {

    private static final String LIST_PATH = "dlc/flist.dlc";
    private static FolderListManagement instance;

    public static FolderListManagement getInstance(){
        if (instance == null){
            instance = new FolderListManagement();
        }
        return instance;
    }

    private FolderListManagement() {
    }

    public List<String> getFolderList(){
        DLCObjectReader<List<String>> or = new DLCObjectReader<>();
        return or.read(LIST_PATH);
    }

    public void saveFolderList(List<String> folderList){
        DLCObjectWriter<List<String>> ow = new DLCObjectWriter<>();
        ow.write(folderList,LIST_PATH);
    }
}
