package Modelo;
    
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsultaLogin extends Conexion{
    
    public boolean Login (Login log){
    
    PreparedStatement ps = null;
    Connection con = getConexion();
    ResultSet rs = null;
    
    String sql = "SELECT usuario, contrasena FROM LOGIN WHERE usuario = ? AND contrasena = ?";
    
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, log.getUsuario());
            ps.setString(2, log.getContrasena());
            rs = ps.executeQuery();
            
            if(rs.next()){
                return true;
            }
                return false;

        } catch (Exception e) {
            System.err.println("Error el usuario no existe " + e);
            return false;

        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.println(e);
            }
        }  
    }
    
}
