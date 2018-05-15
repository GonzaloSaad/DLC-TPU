package utn.frc.dlc.buscadordedocumentosdlc.server;

import utn.frc.dlc.buscadordedocumentosdlc.core.SearchEngineController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

@WebServlet(urlPatterns = "/index")
public class IndexationServlet extends HttpServlet {

    private static SearchEngineController searchEngineController = SearchEngineController.getInstance();


    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("/WEB-INF/views/indexerHome.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String folderUID = httpServletRequest.getParameter("f");

        try {
            searchEngineController.runIndexation(folderUID);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        httpServletRequest.getRequestDispatcher("/WEB-INF/views/indexerHome.jsp").forward(httpServletRequest, httpServletResponse);

    }



}
