package Modelo;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class ConsultaProveedor extends Conexion{
    
    public boolean guardar(Proveedor pro) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "INSERT INTO PROVEEDOR (nombre_empresa,"
                + "contacto, telefono) VALUES (?,?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getNombre_empresa());
            ps.setString(2, pro.getContacto());
            ps.setString(3, pro.getTelefono());
            ps.execute();
            return true;
        } catch (Exception e) {
            System.err.print(e);
            return false;
        }
        finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.print(e);
            }
        }
    }
    public boolean eliminar(Proveedor pro) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM PROVEEDOR WHERE ID_Proveedor = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, pro.getId_proveedor());
            
          
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (Exception e) {

            System.err.println("Error SQL al eliminar: " + e.getMessage());
            return false;
        }
        finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.print(e);
            }
        }
    }   
    public boolean modificar(Proveedor pro) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "UPDATE PROVEEDOR SET  nombre_empresa = ?,"
                + "contacto = ?, telefono = ? WHERE id_proveedor = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getNombre_empresa());
            ps.setString(2, pro.getContacto());
            ps.setString(3, pro.getTelefono());
            ps.setInt(4, pro.getId_proveedor());
            ps.execute();
            return true;
        } catch (Exception e) {
            System.err.print(e);
            return false;
        }
        finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.print(e);
            }
        }
    }
    public boolean buscar(Proveedor pro) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        
        String sql = "SELECT * FROM PROVEEDOR WHERE id_proveedor = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, pro.getId_proveedor());
            rs = ps.executeQuery();
            if(rs.next()){
                pro.setId_proveedor(rs.getInt("Id_proveedor"));
                pro.setNombre_empresa(rs.getString("Nombre_empresa"));
                pro.setContacto(rs.getString("Contacto"));
                pro.setTelefono(rs.getString("Telefono"));
                
                return true;    
            }   
                return false;
                
        } catch (Exception e) {
            System.err.print(e);
            return false;
        }
        finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.print(e);
            }
        }
    }
    
    public List<Proveedor> listaProveedor(){
        List<Proveedor> lista = new ArrayList<>();
        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM PROVEEDOR";
        
        try{
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Proveedor pro = new Proveedor();
                pro.setId_proveedor(rs.getInt("Id_proveedor"));
                pro.setNombre_empresa(rs.getString("Nombre_empresa"));
                pro.setContacto(rs.getString("Contacto"));
                pro.setTelefono(rs.getString("Telefono"));
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