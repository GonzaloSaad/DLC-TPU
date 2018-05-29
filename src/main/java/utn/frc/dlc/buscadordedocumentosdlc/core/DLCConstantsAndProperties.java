package utn.frc.dlc.buscadordedocumentosdlc.core;

import java.io.IOException;
import java.util.Properties;

public class DLCConstantsAndProperties {

    public static final String RESOURCE_PATH = getPathForResourceFolder();

    public static final int LIMIT_WITHOUT_SAVE = 5000000;
    public static final int POST_FILES = 1000;
    public static final double FILES_CACHED_PROP = 0.1;
    public static final int SEARCH_CACHE_SIZE = (int) (POST_FILES * FILES_CACHED_PROP);
    public static final int INDEX_CACHE_SIZE = POST_FILES;
    public static final int R = 30;
    public static final int DOCUMENT_ALREADY_INDEXED = -1;
    public static final String GOOGLE_DRIVE_FOLDER_UID_REGEX = "[a-zA-Z0-9_-]+";


    private static String getPathForResourceFolder(){
        String path;
        Properties prop =  new Properties();
        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("dlc.properties"));
        } catch (IOException ignored) {
            //Exception ignored, path gets null, then runtimeException happens.
        }
        path = prop.getProperty("resource.path");
        if (path == null){
            throw new RuntimeException("Resource folder could not be retrieved.");
        }
        return path;
    }

    public static String getCompletePath(String argument){
        return RESOURCE_PATH + argument;
    }

}
