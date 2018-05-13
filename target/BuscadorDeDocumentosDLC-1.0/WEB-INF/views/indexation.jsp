<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Buscador de Documentos</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1>Buscador de Documentos - TP1 - Indexacion</h1>
        <hr>
        <div class=search_header>
            <form action="/index" method="post">
                <input type="text" name="p" value="${p}">                
                <input type="submit" value="Indexar">
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