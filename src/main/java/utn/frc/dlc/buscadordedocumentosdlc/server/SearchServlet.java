/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscadordedocumentosdlc.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import utn.frc.dlc.buscadordedocumentosdlc.core.SearchEngineController;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.Document;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @author gonzalo.saad
 */
@WebServlet(urlPatterns = "/search")
public class SearchServlet extends HttpServlet {

    private static SearchEngineController searchEngineController = SearchEngineController.getInstance();

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("/WEB-INF/views/searchHome.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String query = httpServletRequest.getParameter("q");
        List<Document> results = searchEngineController.getDocumentsForQuery(query);
        ObjectMapper mapper = new ObjectMapper();
        
        
        httpServletRequest.setAttribute("q", query);
        httpServletRequest.setAttribute("results", mapper.writeValueAsString(results));
        httpServletRequest.getRequestDispatcher("/WEB-INF/views/searchHome.jsp").forward(httpServletRequest, httpServletResponse);
    }

}
