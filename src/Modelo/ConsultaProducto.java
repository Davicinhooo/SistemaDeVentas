package Modelo;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConsultaProducto extends Conexion {
    
    public boolean guardar(Producto pro) {
    PreparedStatement ps = null;
    Connection con = getConexion();
    
    String sql = "INSERT INTO PRODUCTO (id_categoria, id_proveedor, nombre_prod, descripcion, precio, stock_disponible) VALUES (?,?,?,?,?,?)";
    
    try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, pro.getId_categoria());
        ps.setInt(2, pro.getId_proveedor());
        ps.setString(3, pro.getNombre_prod());
        ps.setString(4, pro.getDescripcion());
        ps.setDouble(5, pro.getPrecio());
        ps.setInt(6, pro.getStock_disponible());
        ps.execute();
        return true;
        
    } catch (Exception e){
        System.err.println(e);
        return false;
    } finally {
        try {
            con.close();
            } catch (Exception e){
                System.err.println(e);    
        }
    }
  }
    public boolean eliminar(Producto pro) {
    PreparedStatement ps = null;
    Connection con = getConexion();
    
    String sql = "DELETE FROM PRODUCTO WHERE id_producto = ?";
    
    try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, pro.getId_producto());
        ps.execute();
        return true;
    } catch (Exception e){
        System.err.println(e);
        return false;
    } finally {
        try {
            con.close();
            } catch (Exception e){
                System.err.println(e);    
        }
    }
  }
    public boolean modificar(Producto pro) {
    PreparedStatement ps = null;
    Connection con = getConexion();
    
    String sql = "UPDATE PRODUCTO SET id_categoria = ?, id_proveedor = ?, nombre_prod = ?, descripcion = ?, precio = ?, stock_disponible = ? WHERE id_producto = ?";
    
    try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, pro.getId_categoria());
        ps.setInt(2, pro.getId_proveedor());
        ps.setString(3, pro.getNombre_prod());
        ps.setString(4, pro.getDescripcion());
        ps.setDouble(5, pro.getPrecio());
        ps.setInt(6, pro.getStock_disponible());
        ps.setInt(7, pro.getId_producto());
        ps.execute();
        return true;
    } catch (Exception e){
        System.err.println(e);
        return false;
    } finally {
        try {
            con.close();
            } catch (Exception e){
                System.err.println(e);    
        }
      }
   }
    public boolean buscar(Producto pro) {
    PreparedStatement ps = null;
    Connection con = getConexion();
    ResultSet rs = null;
    
    String sql = "SELECT * FROM PRODUCTO WHERE id_producto = ?";
    
    try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, pro.getId_producto());
        rs = ps.executeQuery();
        
        if(rs.next()){
                pro.setId_producto(rs.getInt("id_producto"));
                pro.setId_categoria(rs.getInt("id_categoria")); 
                pro.setId_proveedor(rs.getInt("id_proveedor"));
                pro.setNombre_prod(rs.getString("nombre_prod"));
                pro.setDescripcion(rs.getString("descripcion"));
                pro.setPrecio(rs.getDouble("precio"));
                pro.setStock_disponible(rs.getInt("stock_disponible"));
                return true;
    }
        return false;
                
    } catch (Exception e){
        System.err.println(e);
        return false;
    } finally {
        try {
            con.close();
            } catch (Exception e){
                System.err.println(e);    
        }
      }
   }
    
    public List<Producto> listarProducto(){
        List<Producto> lista = new ArrayList<>();
        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT p.id_producto, " +
             "c.nombre AS nombre," +
             "pr.nombre_empresa AS nombre_empresa," +
             "p.nombre_prod, " +
             "p.descripcion, " +
             "p.precio, " +
             "p.stock_disponible " +
             "FROM PRODUCTO AS p " +
             "JOIN CATEGORIA AS c ON p.id_categoria = c.id_categoria " +
             "JOIN PROVEEDOR AS pr ON p.id_proveedor = pr.id_proveedor";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Producto pro = new Producto();
                pro.setId_producto(rs.getInt("Id_producto"));
                pro.setNombre(rs.getString("nombre"));
                pro.setNombre_empresa(rs.getString("nombre_empresa"));
                pro.setNombre_prod(rs.getString("Nombre_prod"));
                pro.setDescripcion(rs.getString("Descripcion"));
                pro.setPrecio(rs.getDouble("Precio"));
                pro.setStock_disponible(rs.getInt("Stock_disponible"));
                lista.add(pro);
                
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