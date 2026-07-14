package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultasTurnoCaja extends Conexion {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;

    // --- MODIFICAR TURNO A "ABIERTO" ---
    public boolean abrirTurno(TurnoCaja t) {
        // Solo UPDATE: Cambia el estado y limpia datos de cierre previos
        String sql = "UPDATE TURNO_CAJA SET ID_Caja=?, Fecha_Hora_Apertura=?, Monto_Inicial=?, Estado='ABIERTO', Fecha_Hora_Cierre=NULL, Monto_Final=0 WHERE ID_Turno_Caja=?";
        try {
            con = getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getIdCaja());
            ps.setString(2, t.getFechaApertura());
            ps.setDouble(3, t.getMontoInicial());
            ps.setInt(4, t.getId());
            
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al abrir turno: " + e.toString());
            return false;
        } finally {
            try { if(con != null) con.close(); } catch (SQLException e) { System.err.println(e); }
        }
    }

    // --- MODIFICAR TURNO A "CERRADO" ---
    public boolean cerrarTurno(TurnoCaja t) {
        String sql = "UPDATE TURNO_CAJA SET Fecha_Hora_Cierre=?, Monto_Final=?, Estado='CERRADO' WHERE ID_Turno_Caja=?";
        try {
            con = getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, t.getFechaCierre());
            ps.setDouble(2, t.getMontoFinal());
            ps.setInt(3, t.getId());
            
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al cerrar turno: " + e.toString());
            return false;
        } finally {
            try { if(con != null) con.close(); } catch (SQLException e) { System.err.println(e); }
        }
    }

    // --- BUSCAR POR ID ---
    public boolean buscar(TurnoCaja t) {
        String sql = "SELECT * FROM TURNO_CAJA WHERE ID_Turno_Caja=?";
        try {
            con = getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                t.setId(rs.getInt("ID_Turno_Caja"));
                t.setIdCaja(rs.getInt("ID_Caja"));
                t.setFechaApertura(rs.getString("Fecha_Hora_Apertura"));
                t.setFechaCierre(rs.getString("Fecha_Hora_Cierre"));
                t.setMontoInicial(rs.getDouble("Monto_Inicial"));
                t.setMontoFinal(rs.getDouble("Monto_Final"));
                t.setEstado(rs.getString("Estado"));
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al buscar: " + e.toString());
            return false;
        } finally {
            try { if(con != null) con.close(); } catch (SQLException e) { System.err.println(e); }
        }
    }

    // --- LISTAR PARA LA TABLA ---
    public List<TurnoCaja> listarTurnos() {
        List<TurnoCaja> lista = new ArrayList<>();
        String sql = "SELECT * FROM TURNO_CAJA ORDER BY ID_Turno_Caja DESC";
        try {
            con = getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TurnoCaja t = new TurnoCaja();
                t.setId(rs.getInt("ID_Turno_Caja"));
                t.setIdCaja(rs.getInt("ID_Caja"));
                t.setFechaApertura(rs.getString("Fecha_Hora_Apertura"));
                t.setFechaCierre(rs.getString("Fecha_Hora_Cierre"));
                t.setMontoInicial(rs.getDouble("Monto_Inicial"));
                t.setMontoFinal(rs.getDouble("Monto_Final"));
                t.setEstado(rs.getString("Estado"));
                lista.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar: " + e.toString());
        } finally {
            try { if(con != null) con.close(); } catch (SQLException e) { System.err.println(e); }
        }
        return lista;
    }
}