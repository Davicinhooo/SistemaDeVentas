package Modelo;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaUsuario extends Conexion {
    
    
    public boolean Guardar(Usuario User){
        PreparedStatement ps = null;
        Connection con = getConexion();
        
       
        String sql = "INSERT INTO USUARIO (Username, Password, Rol) VALUES (?,?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, User.getUsername());
            ps.setString(2, User.getPassword());
            ps.setString(3, User.getRol());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    
    public boolean Editar(Usuario User) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        
        String sql = "UPDATE USUARIO SET Username = ?, Password = ?, Rol = ? WHERE ID_Usuario = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, User.getUsername());
            ps.setString(2, User.getPassword());
            ps.setString(3, User.getRol());
            ps.setInt(4, User.getIdUsuario()); // El ID va al final por el WHERE
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
   
    public boolean Buscar(Usuario User) {
        PreparedStatement ps = null;
        ResultSet rs = null; 
        Connection con = getConexion();
        
        String sql = "SELECT * FROM USUARIO WHERE Username = ?"; 
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, User.getUsername());
            rs = ps.executeQuery(); // Usamos executeQuery para los SELECT
            
            if (rs.next()) {
                // Si lo encuentra, llenamos el modelo con los datos de la BD
                User.setIdUsuario(rs.getInt("ID_Usuario"));
                User.setUsername(rs.getString("Username"));
                User.setPassword(rs.getString("Password"));
                User.setRol(rs.getString("Rol"));
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    
    public boolean Eliminar(Usuario User) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM USUARIO WHERE ID_Usuario = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, User.getIdUsuario());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}