package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;

public class ConsultaCliente extends Conexion {

    // --- MÉTODO PARA CARGAR EL COMBOBOX AUTOMÁTICAMENTE ---
    public void cargarUsuarios(JComboBox combo) {
        Connection con = getConexion();
        String sql = "SELECT ID_Usuario, Username FROM USUARIO";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            combo.removeAllItems(); 
            combo.addItem("Sin Usuario / Seleccione..."); // Esta es la opción para los (NULL)
            
            while (rs.next()) {
                combo.addItem(rs.getInt("ID_Usuario") + " - " + rs.getString("Username"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            try { con.close(); } catch (SQLException e) { System.err.println(e); }
        }
    }

    // --- GUARDAR ---
    public boolean guardar(Cliente cli) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "INSERT INTO CLIENTE (DNI, Nombre, Telefono, Correo, ID_Usuario) VALUES (?,?,?,?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cli.getDni());
            ps.setString(2, cli.getNombre());
            ps.setString(3, cli.getTelefono());
            ps.setString(4, cli.getCorreo());
            
            // Lógica para los NULL
            if (cli.getIdUsuario() == 0) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, cli.getIdUsuario());
            }
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try { con.close(); } catch (SQLException e) { System.err.println(e); }
        }
    }

    // --- EDITAR ---
    public boolean editar(Cliente cli) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "UPDATE CLIENTE SET Nombre = ?, Telefono = ?, Correo = ?, ID_Usuario = ? WHERE DNI = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cli.getNombre());
            ps.setString(2, cli.getTelefono());
            ps.setString(3, cli.getCorreo());
            
            if (cli.getIdUsuario() == 0) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, cli.getIdUsuario());
            }
            
            ps.setString(5, cli.getDni()); // El WHERE por DNI
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try { con.close(); } catch (SQLException e) { System.err.println(e); }
        }
    }

    // --- BUSCAR ---
    public boolean buscar(Cliente cli) {
        PreparedStatement ps = null;
        ResultSet rs = null; 
        Connection con = getConexion();
        
        String sql = "SELECT * FROM CLIENTE WHERE DNI = ?"; 
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cli.getDni()); 
            rs = ps.executeQuery(); 
            
            if (rs.next()) {
                cli.setIdCliente(rs.getInt("ID_Cliente"));
                cli.setDni(rs.getString("DNI"));
                cli.setNombre(rs.getString("Nombre"));
                cli.setTelefono(rs.getString("Telefono"));
                cli.setCorreo(rs.getString("Correo"));
                cli.setIdUsuario(rs.getInt("ID_Usuario")); 
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try { con.close(); } catch (SQLException e) { System.err.println(e); }
        }
    }

    // --- ELIMINAR ---
    public boolean eliminar(Cliente cli) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM CLIENTE WHERE DNI = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cli.getDni());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try { con.close(); } catch (SQLException e) { System.err.println(e); }
        }
    }
}