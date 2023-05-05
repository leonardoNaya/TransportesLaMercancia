package paquete;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Servlet2", urlPatterns = {"/Servlet2"})
public class Servlet2 extends HttpServlet {
    Connection connection=null;
    Statement statement=null;
    ResultSet resultset=null;
    @Override
    public void init(ServletConfig config)throws ServletException{
        super.init(config);
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection=DriverManager.getConnection("jdbc:derby://localhost:1527/BDDLaMercancia");
            statement=(Statement) connection.createStatement();
            
        }
        catch(SQLException ex){
            System.out.println("Hubo un error en el SQL: "+ex.getMessage());
        }
        catch(ClassNotFoundException ex){
            System.out.println("No se pudo cargar el controlador: "+ex.getMessage());
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet consulta de Tabla 1</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Tabla</h1>");
            resultset=statement.executeQuery("Select id_viaje,fecha,importe,destino,camionero,id_camioneros, camioneros.nombre as nombreCam, id_destinos, destinos.nombre as nombreDes "
                    + "from viajes,destinos,camioneros "
                    + "where id_destinos = viajes.destino and id_camioneros = viajes.camionero");
            out.println("<table border=1>");
            out.println("<tr>");
            out.println("<td>Nro Viaje");
            out.println("<td>Fecha");
            out.println("<td>Importe");
            out.println("<td>Destino");
            out.println("<td>Camionero");
            out.println("</tr>");
            while (resultset.next()){
                out.println("<tr>");
                out.println("<td>"+resultset.getString("id_viaje"));
                out.println("<td>"+resultset.getString("fecha"));
                out.println("<td>"+resultset.getString("importe"));
                out.println("<td>"+resultset.getString("nombreDes"));
                out.println("<td>"+resultset.getString("nombreCam"));
                out.println("</tr>");
            }
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex){
            System.out.println("Hubo un error en el SQL: "+ex.getMessage());
            out.println("<h1>ERROR");
        }
    }
}
