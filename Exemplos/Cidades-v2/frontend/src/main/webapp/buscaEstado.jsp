<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AJAX (dynamic select)</title>
        <script src="js/ajaxEstado.js" defer></script>
    </head>
    <body onload='recuperaEstados()'>
        <br/>

        <form name='form'>
            <table>
                <tr>
                    <td>Estado</td>
                    <td>
                        <select id = 'estado' name='estado' onchange='estadoSelecionado(this.value)'>
                            <option value='--'>--</option>
                        </select>   
                    </td>
                </tr>
                <tr id='cidades'>    
                    <td>
                        Cidade
                    </td>
                    <td>
                        <select id='cidade' name='cidade' onchange='apresenta()'>
                        </select>
                    </td>   
                </tr>
            </table>
        </form>
        <br/>
        <a href="index.jsp">Voltar</a>
    </body>
</html>