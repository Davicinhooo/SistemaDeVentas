package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaPedido_Virtual extends Conexion {

    // --- GUARDAR (Botón Aceptar) ---
    public boolean guardar(Pedido_Virtual ped) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        // ID_Pedido suele ser Auto_Increment, por eso enviamos Venta y Estado
        String sql = "INSERT INTO PEDIDO_VIRTUAL (ID_Venta, Estado_Pedido) VALUES (?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ped.getId_venta());
            ps.setString(2, ped.getEstado_pedido());
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
    public boolean editar(Pedido_Virtual ped) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "UPDATE PEDIDO_VIRTUAL SET ID_Venta = ?, Estado_Pedido = ? WHERE ID_Pedido = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ped.getId_venta());
            ps.setString(2, ped.getEstado_pedido());
            ps.setInt(3, ped.getId_pedido());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try { con.close(); } catch (SQLException e) { System.err.println(e); }
        }
    }

    // --- ELIMINAR ---
    public boolean eliminar(Pedido_Virtual ped) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM PEDIDO_VIRTUAL WHERE ID_Pedido = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ped.getId_pedido());
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
    public boolean buscar(Pedido_Virtual ped) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        
        String sql = "SELECT * FROM PEDIDO_VIRTUAL WHERE ID_Pedido = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ped.getId_pedido());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                ped.setId_pedido(rs.getInt("ID_Pedido"));
                ped.setId_venta(rs.getInt("ID_Venta"));
                ped.setEstado_pedido(rs.getString("Estado_Pedido"));
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
}