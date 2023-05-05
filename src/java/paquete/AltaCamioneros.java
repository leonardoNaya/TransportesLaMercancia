package paquete;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AltaCamioneros", urlPatterns = {"/AltaCamioneros"})
public class AltaCamioneros extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        try {
            String nombre= request.getParameter("txtNombre");
            String apellido= request.getParameter("txtApellido");
            String domicilio= request.getParameter("txtDomicilio");
            String telefono= request.getParameter("txtTelefono");
            if (!nombre.equals("") && !apellido.equals("") && !domicilio.equals("") && !telefono.equals("")){
                try{
                    statement.executeUpdate("INSERT INTO camioneros (nombre,apellido,domicilio,telefono) VALUES('"+nombre+"','"+apellido+"','"+domicilio+"','"+telefono+"')");
                    responderOK(out);
                }catch(SQLException ex){
                    responderError(out,ex.getMessage());
                }
            }
            else{
                responderFaltanDatos(out);
            }
        }
        catch (NumberFormatException nfe){
            responderDatosIncorrectos(out);
        }
    }
    private void responderOK(PrintWriter out){
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Alta Ok.</h1>");
        out.println("<a href='index.html'>Volver al index</a>");
        out.println("</body>");
        out.println("</html>");
    }
    private void responderError(PrintWriter out, String msg){
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Hubo un problema con el Alta. Intente en un momento o informe al administrador.");
        out.println("<p>" + msg + "</p>");
        out.println("</body>");
        out.println("</html>");
}
    private void responderDatosIncorrectos(PrintWriter out){
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>El precio es incorrecto. Vuelva a ingresarlo.</h1>");
        out.println("</body>");
        out.println("</html>");
    }
    private void responderFaltanDatos(PrintWriter out){
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Faltan datos en el formulario</h1>");
        out.println("<a href='index.html'>Volver al index</a>");
        out.println("</body>");
        out.println("</html>");
    }
    
}