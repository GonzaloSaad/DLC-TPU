/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Prueba.drive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 *
 * @author JuanB
 */
public class ObtenerNombres {
    
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FOLDER = "credentials"; // Directory to store user credentials.

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    
    File a = new File();
    
    private static final String CLIENT_SECRET_DIR = "/client_secret.json";
    
    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If there is no client_secret.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        
        InputStream in = ObtenerNombres.class.getResourceAsStream(CLIENT_SECRET_DIR);
        
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_FOLDER)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
    
    public List<File> resultados() throws IOException, GeneralSecurityException
    {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list().setQ("'0B_R7SeoAotsmUUtYendIX04zRjA' in parents")
                .setPageSize(500)
                .setFields("nextPageToken, files(id, name, webContentLink, webViewLink)")
                .execute();
        List<File> files = result.getFiles();
//        if (files == null || files.isEmpty()) {
//            System.out.println("No files found.");
//        } else {
//            System.out.println("Files:");
//            for (File file : files) {
//                System.out.printf("%s (%s)\n", file.getName(), file.getId(), file.getWebContentLink(), file.getWebViewLink());
//            }
            
            
//        }
        
        return files;
        }

        
        public void Descargar() throws IOException, GeneralSecurityException
        {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
            
            FileList result = service.files().list().setQ("'0B_R7SeoAotsmUUtYendIX04zRjA' in parents")
                .setPageSize(500)
                .setFields("nextPageToken, files(id, name, webContentLink, webViewLink)")
                .execute();
            List<File> files = result.getFiles();
            
//Lista donde guardo todos los outputStream(archivos)            
            List<OutputStream> myData = new ArrayList<OutputStream>();
            
            for (int i = 0; i < 500; i++) {
                String fileId = files.get(i).getId();
//Este es el output donde se guardan los archivos
                OutputStream outputStream = new ByteArrayOutputStream();
                service.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);
                
                myData.add(outputStream);
                outputStream.close();
                }
               
           }

//Descomentar esto para guardar en disco
                
//                FileOutputStream fop = null;
//		java.io.File file;
//                
//                try {
//
//			file = new java.io.File("./Resources/pruebaDrive.txt");
//			fop = new FileOutputStream(file);
//
//			// if file doesnt exists, then create it
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//
//			// get the content in bytes
//                        ByteArrayOutputStream help = (ByteArrayOutputStream) outputStream;
//			byte[] contentInBytes = help.toByteArray();
//
//			fop.write(contentInBytes);
//			fop.flush();
//			fop.close();
//
//			System.out.println("Done");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (fop != null) {
//					fop.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	
        
        }

