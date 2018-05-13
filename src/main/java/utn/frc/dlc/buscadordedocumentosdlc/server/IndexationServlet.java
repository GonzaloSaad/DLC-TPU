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

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("/WEB-INF/views/indexerHome.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String folderUID = httpServletRequest.getParameter("f");
        String message = "Index Failure.";

        try {
            SearchEngineController.getInstance().runIndexation(folderUID);
            message = "Indexation done.";
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        httpServletRequest.setAttribute("message", message);
        httpServletRequest.getRequestDispatcher("/WEB-INF/views/indexerHome.jsp").forward(httpServletRequest, httpServletResponse);

    }



}
