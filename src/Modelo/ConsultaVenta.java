package Modelo;

import java.sql.*;
import javax.swing.JComboBox;

public class ConsultaVenta extends Conexion {

    // Carga de combos (igual que antes, pero asegurando la conexión)
    public void cargarClientes(JComboBox combo) {
        Connection con = getConexion();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT ID_Cliente, Nombre FROM CLIENTE");
            ResultSet rs = ps.executeQuery();
            combo.removeAllItems();
            combo.addItem("Seleccione Cliente...");
            while (rs.next()) combo.addItem(rs.getInt(1) + " - " + rs.getString(2));
        } catch (SQLException e) { System.err.println(e); }
    }

    public void cargarCajeros(JComboBox combo) {
        Connection con = getConexion();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT ID_Usuario, Username FROM USUARIO WHERE Rol IN ('Empleado', 'Admin')");
            ResultSet rs = ps.executeQuery();
            combo.removeAllItems();
            combo.addItem("Seleccione Cajero...");
            while (rs.next()) combo.addItem(rs.getInt(1) + " - " + rs.getString(2));
        } catch (SQLException e) { System.err.println(e); }
    }

    public void cargarTurnosAbiertos(JComboBox combo) {
        Connection con = getConexion();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT ID_Turno_Caja, ID_Caja FROM TURNO_CAJA WHERE Estado = 'Abierto'");
            ResultSet rs = ps.executeQuery();
            combo.removeAllItems();
            combo.addItem("Seleccione Turno...");
            while (rs.next()) combo.addItem(rs.getInt(1) + " - Caja " + rs.getInt(2));
        } catch (SQLException e) { System.err.println(e); }
    }

    public void cargarProductos(JComboBox combo) {
        Connection con = getConexion();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT ID_Producto, Nombre_Prod FROM PRODUCTO");
            ResultSet rs = ps.executeQuery();
            combo.removeAllItems();
            combo.addItem("Seleccione Producto...");
            while (rs.next()) combo.addItem(rs.getInt(1) + " - " + rs.getString(2));
        } catch (SQLException e) { System.err.println(e); }
    }

    public void cargarMetodoPago(JComboBox combo) {
        Connection con = getConexion();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT ID_Metodo_Pago, Tipo_Pago FROM METODO_PAGO");
            ResultSet rs = ps.executeQuery();
            combo.removeAllItems();
            combo.addItem("Seleccione Pago...");
            while (rs.next()) combo.addItem(rs.getInt(1) + " - " + rs.getString(2));
        } catch (SQLException e) { System.err.println(e); }
    }

    public double obtenerPrecio(int id) {
        Connection con = getConexion();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT Precio FROM PRODUCTO WHERE ID_Producto = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { System.err.println(e); }
        return 0;
    }

    // MÉTODO GUARDAR VENTA (Ajustado a tus setters en minúsculas)
    public int guardarVenta(Venta v) {
        Connection con = getConexion();
        String sql = "INSERT INTO VENTA (ID_Cliente, ID_Usuario, ID_Turno_Caja, ID_Metodo_Pago, Fecha_Hora, Tipo_Venta, Total) VALUES (?,?,?,?,NOW(),?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, v.getId_cliente());
            ps.setInt(2, v.getId_usuario());
            ps.setInt(3, v.getId_turno_caja());
            ps.setInt(4, v.getId_metodo_pago());
            ps.setString(5, v.getTipo_venta());
            ps.setDouble(6, v.getTotal());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { System.err.println("Error al guardar venta: " + e); }
        return 0;
    }

    public void guardarDetalle(int idV, int idP, int cant, double pre) {
        Connection con = getConexion();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO DETALLE_VENTA (ID_Venta, ID_Producto, Cantidad, Precio_Unitario) VALUES (?,?,?,?)");
            ps.setInt(1, idV);
            ps.setInt(2, idP);
            ps.setInt(3, cant);
            ps.setDouble(4, pre);
            ps.executeUpdate();
        } catch (SQLException e) { System.err.println("Error al guardar detalle: " + e); }
    }
}