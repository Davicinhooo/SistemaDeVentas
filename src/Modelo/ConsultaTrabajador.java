package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class ConsultaTrabajador extends Conexion {

    // --- GUARDAR ---
    public boolean guardar(Trabajador trab){
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        // Incluimos todos los campos de tu diseño + el ID de la llave foránea
        String sql = "INSERT INTO TRABAJADOR (DNI, Nombre, Telefono, Cargo, Sueldo, ID_Usuario) VALUES (?,?,?,?,?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, trab.getDni());
            ps.setString(2, trab.getNombre());
            ps.setString(3, trab.getTelefono());
            ps.setString(4, trab.getCargo());
            ps.setDouble(5, trab.getSueldo()); 
            
            // Lógica para la Llave Foránea: Si es 0, enviamos NULL a MySQL
            if (trab.getIdUsuario() == 0) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, trab.getIdUsuario());
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

    // --- MÉTODO PARA CARGAR LA LLAVE FORÁNEA ---
    // Este método va a la tabla USUARIO y trae los datos válidos para que no haya errores
    public void cargarUsuarios(JComboBox combo) {
        Connection con = getConexion();
        String sql = "SELECT ID_Usuario, Username FROM USUARIO";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            combo.removeAllItems(); 
            combo.addItem("Sin Usuario / Seleccione..."); // Opción para enviar NULL
            
            while (rs.next()) {
                combo.addItem(rs.getInt("ID_Usuario") + " - " + rs.getString("Username"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            try { con.close(); } catch (SQLException e) { System.err.println(e); }
        }
    }
    
    // (Aquí irían tus métodos editar, eliminar y buscar usando la misma lógica SQL)
public boolean buscar(Trabajador trab) {
        PreparedStatement ps = null;
        ResultSet rs = null; 
        Connection con = getConexion();
        
        // 1. Buscamos en la tabla TRABAJADOR filtrando por DNI
        String sql = "SELECT * FROM TRABAJADOR WHERE DNI = ?"; 
        
        try {
            ps = con.prepareStatement(sql);
            // 2. Le pasamos el DNI que escribieron en la ventana
            ps.setString(1, trab.getDni()); 
            rs = ps.executeQuery(); 
            
            if (rs.next()) {
                // 3. Llenamos el modelo con los SET y las columnas exactas de la BD
                trab.setIdTrabajador(rs.getInt("ID_Trabajador"));
                trab.setDni(rs.getString("DNI"));
                trab.setNombre(rs.getString("Nombre"));
                trab.setTelefono(rs.getString("Telefono"));
                trab.setCargo(rs.getString("Cargo"));
                trab.setSueldo(rs.getDouble("Sueldo"));
                trab.setIdUsuario(rs.getInt("ID_Usuario")); // Importante para la llave foránea
                
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
    
    
    public boolean Eliminar(Trabajador trab) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM TRABAJADOR WHERE ID_Trabajador = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, trab.getIdTrabajador());
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
public boolean editar(Trabajador trab) {
       PreparedStatement ps = null;
        Connection con = getConexion();
        
        
        String sql = "UPDATE TRABAJADOR SET Nombre = ?, Telefono = ?, Cargo = ?, Sueldo = ?, ID_Usuario = ? WHERE DNI = ?";
        
        try {
            ps = con.prepareStatement(sql);
            
            // Llenamos los datos en el mismo orden que los signos de interrogación (?)
            ps.setString(1, trab.getNombre());
            ps.setString(2, trab.getTelefono());
            ps.setString(3, trab.getCargo());
            ps.setDouble(4, trab.getSueldo());
            
            
            
            // Lógica para la Llave Foránea: Si es 0, enviamos NULL a MySQL
            if (trab.getIdUsuario() == 0) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, trab.getIdUsuario());
            }
            
            // El DNI va al final porque es el que está después del WHERE
            ps.setString(6, trab.getDni()); 
            
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
    
    public List<Trabajador> listarTrabajador(){
        List<Trabajador> lista = new ArrayList<>();
        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM TRABAJADOR";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Trabajador tra = new Trabajador();
                tra.setIdTrabajador(rs.getInt("ID_Trabajador"));
                tra.setDni(rs.getString("DNI"));
                tra.setNombre(rs.getString("Nombre"));
                tra.setTelefono(rs.getString("Telefono"));
                tra.setCargo(rs.getString("Cargo"));
                tra.setSueldo(rs.getDouble("Sueldo"));
                tra.setIdUsuario(rs.getInt("ID_Usuario"));
                lista.add(tra);
                    
            }
    } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        return lista;
        
    }

}