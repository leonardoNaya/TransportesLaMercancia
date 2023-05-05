package paquete;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet(name = "Servlet1", urlPatterns = {"/Servlet1"})
public class Servlet1 extends HttpServlet {
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
            out.println("<table border=1>");
            out.println("<tr>");
            out.println("<td>ID");
            out.println("<td>Nombre");
            out.println("<td>Apellido");
            out.println("<td>Domicilio");
            out.println("<td>Telefono");
            out.println("</tr>");
            resultset=statement.executeQuery("Select * from camioneros");            
            while (resultset.next()){
                out.println("<tr>");
                out.println("<td>"+resultset.getString("ID_Camioneros"));
                out.println("<td>"+resultset.getString("Nombre"));
                out.println("<td>"+resultset.getString("Apellido"));
                out.println("<td>"+resultset.getString("Domicilio"));
                out.println("<td>"+resultset.getString("Telefono"));
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex){
            System.out.println("Hubo un error en el SQL: "+ex.getMessage());
        }
    }
}
