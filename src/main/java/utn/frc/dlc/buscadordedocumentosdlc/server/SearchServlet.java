/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.server;

import Prueba.drive.ObtenerNombres;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utn.frc.dlc.buscadordedocumentosdlc.core.searchengine.SearchEngineController;
import utn.frc.dlc.buscadordedocumentosdlc.core.searchengine.modelcomponents.DocumentResult;

/**
 *
 * @author gonzalo.saad
 */
@WebServlet(urlPatterns = "/search")
public class SearchServlet extends HttpServlet {
    
    private static SearchEngine searchEngine = new SearchEngineController();
    
    
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        

        httpServletRequest.getRequestDispatcher("/WEB-INF/views/searchHome.jsp").forward(httpServletRequest,httpServletResponse);
        

    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
      
//        String query = httpServletRequest.getParameter("q");
////        List<DocumentResult> results = searchEngine.getDocumentsForSearch(query);
//        httpServletRequest.setAttribute("q", query);
        
        List<File> results = null;
        ObtenerNombres o = new ObtenerNombres();
        try {
            o.Descargar();
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            results = o.resultados();
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
//        httpServletRequest.setAttribute("results", results);
        httpServletRequest.setAttribute("results", results);
        httpServletRequest.getRequestDispatcher("/WEB-INF/views/searchHome.jsp").forward(httpServletRequest, httpServletResponse);
    
        
    }
    
}
