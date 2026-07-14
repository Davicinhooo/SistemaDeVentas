package Modelo;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

public class ConsultaCategoria extends Conexion{
    public boolean guardar(Categoria cat) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "INSERT INTO CATEGORIA (id_categoria, nombre) VALUES (?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cat.getId_categoria());
            ps.setString(2, cat.getNombre());
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
    
    public boolean buscar (Categoria cat){
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        
        String sql = "SELECT * FROM CATEGORIA WHERE id_categoria = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cat.getId_categoria());
            rs = ps.executeQuery();
            
            if(rs.next()){
            cat.setId_categoria(rs.getInt("id_categoria"));
            cat.setNombre(rs.getString("nombre"));
            return true;
        }
        return false;
    } catch(Exception e){
        System.err.println(e);
        return false;
    } finally {
        try {
            con.close();
        } catch(Exception e){
            System.err.println(e);
        }
    }
}
    
    public boolean eliminar(Categoria cat) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM CATEGORIA WHERE id_categoria = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cat.getId_categoria());
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
    public boolean modificar(Categoria cat) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "UPDATE CATEGORIA SET nombre = ? WHERE id_categoria = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cat.getNombre());
            ps.setInt(2, cat.getId_categoria());
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
    
    public List<Categoria> listarCategoria(){
        List<Categoria> lista = new ArrayList<>();
        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM CATEGORIA";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Categoria cat = new Categoria();
                cat.setId_categoria(rs.getInt("id_categoria"));
                cat.setNombre(rs.getString("nombre"));
                lista.add(cat);
                
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
