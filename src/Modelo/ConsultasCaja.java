package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultasCaja extends Conexion {

    
    public boolean existeCajaPorId(int id) {
        String sql = "SELECT COUNT(*) FROM CAJA WHERE ID_Caja = ?";
        try (Connection con = getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }
    
    // REGISTRAR: No incluimos ID_Caja porque es AUTO_INCREMENT
    public boolean guardar(Caja c) {
        String sql = "INSERT INTO CAJA (Nombre_Caja, Estado, Sucursal) VALUES (?,?,?)";
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getEstado());
            ps.setString(3, c.getSucursal());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar: " + e);
            return false;
        }
    }

    public boolean modificar(Caja c) {
        String sql = "UPDATE CAJA SET Nombre_Caja=?, Estado=?, Sucursal=? WHERE ID_Caja=?";
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getEstado());
            ps.setString(3, c.getSucursal());
            ps.setInt(4, c.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar: " + e);
            return false;
        }
    }

    public boolean eliminar(Caja c) {
        String sql = "DELETE FROM CAJA WHERE ID_Caja=?";
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // Manejo de error si la caja está en uso (Llave foránea)
            if (e.getErrorCode() == 1451) return false; 
            System.err.println("Error al eliminar: " + e);
            return false;
        }
    }

    public boolean buscar(Caja c) {
        String sql = "SELECT * FROM CAJA WHERE ID_Caja=?";
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c.setNombre(rs.getString("Nombre_Caja"));
                    c.setEstado(rs.getString("Estado"));
                    c.setSucursal(rs.getString("Sucursal"));
                    return true;
                }
            }
        } catch (SQLException e) { System.err.println(e); }
        return false;
    }
    public List<Caja> listar() {
    List<Caja> lista = new ArrayList<>();
    String sql = "SELECT * FROM CAJA ORDER BY ID_Caja ASC";
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = getConexion();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Caja c = new Caja();
            c.setId(rs.getInt("ID_Caja"));
            c.setNombre(rs.getString("Nombre_Caja"));
            c.setEstado(rs.getString("Estado"));
            c.setSucursal(rs.getString("Sucursal"));
            lista.add(c);
        }
    } catch (SQLException e) {
        System.err.println("Error al listar: " + e);
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) { System.err.println(e); }
    }
    return lista;
}
}