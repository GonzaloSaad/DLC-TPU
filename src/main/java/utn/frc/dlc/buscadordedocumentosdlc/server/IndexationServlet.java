package utn.frc.dlc.buscadordedocumentosdlc.server;

import utn.frc.dlc.buscadordedocumentosdlc.core.SearchEngineController;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.Document;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/index")
public class IndexationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("/WEB-INF/views/indexation.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String query = httpServletRequest.getParameter("q");
        List<Document> results = SearchEngineController.getInstance().getDocumentsForQuery(query);
        httpServletRequest.setAttribute("q", query);
        httpServletRequest.setAttribute("results", results);
        httpServletRequest.getRequestDispatcher("/WEB-INF/views/indexation.jsp").forward(httpServletRequest, httpServletResponse);}
}
