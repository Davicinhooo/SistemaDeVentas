package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultasMetodoPago extends Conexion {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;

    // --- REGISTRAR ---
    public boolean registrar(MetodoPago mp) {
        // No incluimos ID_Metodo_Pago porque es AUTO_INCREMENT
        String sql = "INSERT INTO METODO_PAGO (Tipo_Pago, Descripcion) VALUES (?,?)";
        try {
            con = getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, mp.getTipoPago());
            ps.setString(2, mp.getDescripcion());
            return ps.executeUpdate() > 0; // Cambiado a executeUpdate para confirmar inserción
        } catch (SQLException e) {
            System.err.println("Error al registrar: " + e.toString());
            return false;
        } finally {
            closeResources();
        }
    }

    // --- MODIFICAR ---
    public boolean modificar(MetodoPago mp) {
        String sql = "UPDATE METODO_PAGO SET Tipo_Pago=?, Descripcion=? WHERE ID_Metodo_Pago=?";
        try {
            con = getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, mp.getTipoPago());
            ps.setString(2, mp.getDescripcion());
            ps.setInt(3, mp.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar: " + e.toString());
            return false;
        } finally {
            closeResources();
        }
    }

    // --- EXISTE REGISTRO ---
    public boolean existeRegistro(int id) {
        String sql = "SELECT COUNT(*) FROM METODO_PAGO WHERE ID_Metodo_Pago = ?";
        try {
            con = getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al validar existencia: " + e.toString());
        } finally {
            closeResources();
        }
        return false;
    }

    // --- BUSCAR, ELIMINAR y LISTAR (Se mantienen similares pero con closeResources) ---
    public boolean buscar(MetodoPago mp) {
        String sql = "SELECT * FROM METODO_PAGO WHERE ID_Metodo_Pago=?";
        try {
            con = getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, mp.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                mp.setId(rs.getInt("ID_Metodo_Pago"));
                mp.setTipoPago(rs.getString("Tipo_Pago"));
                mp.setDescripcion(rs.getString("Descripcion"));
                return true;
            }
            return false;
        } catch (SQLException e) { return false; } finally { closeResources(); }
    }

    public boolean eliminar(MetodoPago mp) {
        String sql = "DELETE FROM METODO_PAGO WHERE ID_Metodo_Pago=?";
        try {
            con = getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, mp.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        } finally { closeResources(); }
    }

    public List<MetodoPago> listarMetodos() {
        List<MetodoPago> datos = new ArrayList<>();
        String sql = "SELECT * FROM METODO_PAGO ORDER BY ID_Metodo_Pago DESC";
        try {
            con = getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MetodoPago mp = new MetodoPago();
                mp.setId(rs.getInt("ID_Metodo_Pago"));
                mp.setTipoPago(rs.getString("Tipo_Pago"));
                mp.setDescripcion(rs.getString("Descripcion"));
                datos.add(mp);
            }
        } catch (SQLException e) { } finally { closeResources(); }
        return datos;
    }

    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) { System.err.println(e); }
    }
}