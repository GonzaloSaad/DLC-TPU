package utn.frc.dlc.buscadordedocumentosdlc.common;

public class GoogleDriveFormater {

    public static String GOOGLE_DRIVE_DOCUMENT_URL = "https://drive.google.com/file/d/";

    public static String formatToGoogleDriveURL(String UUID){
        return GOOGLE_DRIVE_DOCUMENT_URL + UUID;
    }
}
