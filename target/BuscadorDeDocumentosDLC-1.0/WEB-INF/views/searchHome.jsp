<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Buscador de Documentos</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1>Buscador de Documentos - TP1</h1>
        <hr>
        <div class=search_header>
          <form action="/search" method="post">
                <input type="text" name="q" value="${q}">                
                <input type="submit" value="Buscar">
          </form>
        </div>
        <hr>
        <div class=search_result>
          <p>${message}</p>
          <ol>
        <c:forEach items="${results}" var="result">
          <li>
            <a href="${result.url}" target="_blank">${result.name}</a>
            <br>            
          </li>

        </c:forEach>
      </ol>
        </div>
        
    </body>
</html>