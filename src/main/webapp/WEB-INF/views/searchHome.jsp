<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Buscador de Documentos</title>
        <link href="/CSS/bootstrap.css" rel="stylesheet"/>
        <link rel="stylesheet" href="CSS/bootstrap.min.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <style>
            tr:nth-of-type(odd){background-color: #ccffcc;}
            tr:nth-of-type(even){background-color: transparent;}

            input[type=text]:focus, textArea:focus{ border-color: #ccffcc ; box-shadow: inset 2px 2px 2px 2px rgba(204, 255, 204, 255), 2px 2px 2px 2px rgba(204, 255, 204, 255);}
        </style>

    </head>



    <script >
        function addNewRow(a) {

           var d = document.getElementById('tabla1');
        var tablaHtml;
    if(a.length > 0)
    {
    tablaHtml = "<div class='container' id='tabla' style='background-color: #ffffff ;border:ridge #ffffcb'><div class='row' sytle='overflow-x:auto;'>";
    tablaHtml += "<table class='table table-bordered '>";
    tablaHtml = tablaHtml + "<tr><th>Id</th><th>Nombre</th><th>Previsualizar</th><th>Descargar</th>";


    for (i=0; i<a.length; i++ ) {

        tablaHtml = tablaHtml + "<tr>";

        var td1 = "<td>" + a[i].id + "</td>";
        var td2 = "<td>" + a[i].name + "</td>";
        var td3 = "<td>" + "<a href=" + a[i].webViewLink + " target='_blank'>"+ a[i].webViewLink + "</a>" + "</td>";
        var td4 = "<td>" + "<a href=" + a[i].webContentLink + ">" + a[i].webContentLink + "</td>";



        tablaHtml = tablaHtml + td1 + td2 + td3 + td4 + "</tr>";
    }

        tablaHtml = tablaHtml + "</table>" + "</div>" + "</div>";



        }
        else
        {
           tablaHtml ="<div class='container' id='tabla' style='background-color: #ffffff ;border:ridge #ffffcb'><div class='row'><div class='col' style='text-align:center'>" + "<h2 style='text-align:center'>"+  "La busqueda no produjo resultados" + "</h2>" + "</div>" +  "</div>" + "</div>";

        }

        d.innerHTML = tablaHtml;
    }


    </script>

    <body style="background-color: beige">
        <h1 style="text-align: center">DLC - TPU - Buscador de Documentos</h1>
        <hr>
        <div class=search_header>
        	<form class="form-inline" role="form" >
                    <div class="container" id="tabla" style="border:ridge #ffffcb ">
                    <div class="row">
                    <input type="text" name="q" value="${q}" class="form-control" style="  width: 400px; margin: 1em 1em">
                    <div class="form-group" role="form" >
                        <input type="submit"  formmethod="post" formaction="/dlc/search" class="btn-sm btn-group-toggle" value="Buscar" style=" background: #ccffcc ;width: 120px; margin: 1em ; font-size: 15px"  >
                    </div>                    
                    </div>
                    </div>
	        </form>
        </div>
        <hr>

        <div id="tabla1"></div>



        <div class=search_result>



            <script>
                addNewRow(${results});
            </script>


        </div>
        
    </body>
</html>