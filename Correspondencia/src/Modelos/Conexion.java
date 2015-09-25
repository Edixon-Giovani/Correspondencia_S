package Modelos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 * @author Johana Serrano, Erika Tique, Giovani...
 */
public class Conexion {
    private Connection conexion = null;
    private String Driver ="com.mysql.jdbc.Driver";     
    private String bd ="correspondencia";
    private String url = "jdbc:mysql://localhost/"+bd;
    private String User = "root";
    private String Password = "";
    private Connection Conexion = null;
        
    public Conexion (){
        try{
            Class.forName(Driver);
            conexion = DriverManager.getConnection(this.url, this.User, this.Password);
        }catch (ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, e.getMessage());     
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());  
        }
    }
    public Connection getConexion(){
        return this.conexion;
    }
}