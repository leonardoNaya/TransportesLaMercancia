<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consulta/Alta de viajes</title>
        <link rel="stylesheet" text="text/css" href="estilos.css">
    </head>
    <body>
        <h1>Alta de Viajes</h1>
        <sql:setDataSource var="bdd"
                           driver="org.apache.derby.jdbc.ClientDriver"
                           url="jdbc:derby://localhost:1527/BDDLaMercancia"/>
        <sql:query var="cdr" dataSource="${bdd}">
            select * from viajes
        </sql:query>
        <sql:query var="cdr2" dataSource="${bdd}">
            select * from destinos
        </sql:query>
        <sql:query var="cdr3" dataSource="${bdd}">
            select * from camioneros
        </sql:query>
                
        <form action="altaViajes.jsp" method="post">
            <p>Nueva Fecha: <input type="datetime-local" name="txtFecha"/>
            <p>Nuevo Importe: <input type="number" name="txtImporte"/>
            <p>Nuevo Destino: <select name="selectDestinos">
                    <c:forEach var="fila" items="${cdr2.rows}">
                        <option value="${fila.ID_Destinos}">${fila.nombre}</option>
                    </c:forEach>
                </select></p>
            <p>Camionero que hizo el viaje: <select name="selectCamioneros">
                    <c:forEach var="fila" items="${cdr3.rows}">
                        <option value="${fila.ID_Camioneros}">${fila.nombre} ${fila.apellido}</option>
                    </c:forEach>
                </select></p>
            <p><input type="submit" value="Guardar" name="btnGuardar"/></p>
        </form>
        <a href="index.html">Volver al index</a>
    </body>
</html>
<c:if test="${!empty param.txtFecha && !empty param.txtImporte && !empty param.selectCamioneros && !empty param.selectDestinos}">
    <c:catch var="errorOcurrido">
        <sql:update var="cont" dataSource="${bdd}">
            INSERT INTO VIAJES (fecha,importe,destino,camionero) VALUES (?,?,?,?)
            <sql:param value="${param.txtFecha}"/>
            <sql:param value="${param.txtImporte}"/>
            <sql:param value="${param.selectDestinos}"/>
            <sql:param value="${param.selectCamioneros}"/>
        </sql:update>
        <c:redirect url="altaViajes.jsp"/>
    </c:catch>
    <c:if test="${not empty errorOcurrido}">
        Error: no se pudo actualizar la base de datos
    </c:if>
</c:if>