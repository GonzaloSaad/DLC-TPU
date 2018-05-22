<%-- 
    Document   : indexerHome
    Created on : 09-may-2018, 11:07:32
    Author     : JuanB
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Indexador de Documentos</title>
        <link href="/CSS/bootstrap.css" rel="stylesheet"/>
        <link rel="stylesheet" href="CSS/bootstrap.min.css"/>
        
        <style>
            input[type=text]:focus, textArea:focus{ border-color: #ccffcc ; box-shadow: inset 2px 2px 2px 2px rgba(204, 255, 204, 255), 2px 2px 2px 2px rgba(204, 255, 204, 255);}
        </style>
    </head>
    <body style="background-color: beige">
        <h1 style="text-align: center">DLC - TPU - Indexador de Documentos</h1>
        <hr>
        <div class=search_header>
            <form class="form-inline" role="form" >
                    <div class="container" id="tabla" style="border:ridge #ffffcb ">
                        <div class="row" style="align-content: space-around">
                    
                        
                    <input type="text" name="f" value="${f}" class="form-control" style="align-self: stretch;  width: 400px; margin: 1em 1em">  
                    
                    
                    <div class="form-group" role="form"  >
                        <input type="submit" formmethod="post" formaction="/dlc/index" class="btn-sm btn-group-toggle" value="Cargar Url" style=" align-self: stretch; background: #ccffcc ;width: 120px; margin: 1em 1em ; font-size: 15px">
                    </div>
                     
                    
                    </div>
                    </div>
	        </form>
        </div>
    </body>
</html>
