package Modelo;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

public class ConsultaDatos_Envio extends Conexion{
    public boolean guardar (Datos_Envio dt){
       PreparedStatement ps = null;
       Connection con = getConexion();
       ResultSet rs = null;
       
       String sql = "INSERT INTO DATOS_ENVIO (direccion, referencia, contacto) VALUES (?,?,?)";
       
        try {
           ps = con.prepareStatement(sql);
           ps.setString(1, dt.getDireccion());
           ps.setString(2, dt.getReferencia());
           ps.setString(3, dt.getContacto());
           ps.execute();
           return true;
           
            }catch (Exception e) {
                System.err.println(e);
                return false;
                
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    public boolean modificar(Datos_Envio dt){
       PreparedStatement ps = null;
       Connection con = getConexion();
       ResultSet rs = null;
       
       String sql = "UPDATE DATOS_ENVIO SET direccion = ?, referencia = ?, contacto = ? WHERE id_envio = ?";
       
        try {
           ps = con.prepareStatement(sql);
           ps.setString(1, dt.getDireccion());
           ps.setString(2, dt.getReferencia());
           ps.setString(3, dt.getContacto());
           ps.setInt(4, dt.getID_Envio());
           ps.execute();
           return true;
           
            }catch (Exception e) {
                System.err.println(e);
                return false;
                
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    public boolean buscar(Datos_Envio dt){
       PreparedStatement ps = null;
       Connection con = getConexion();
       ResultSet rs = null;
       
       String sql = "SELECT * FROM DATOS_ENVIO WHERE id_envio = ?";
       
        try {
           ps = con.prepareStatement(sql);
           ps.setInt(1, dt.getID_Envio());
           rs = ps.executeQuery();
           
           if(rs.next()){
               dt.setID_Envio(rs.getInt("id_envio"));
               dt.setDireccion(rs.getString("direccion"));
               dt.setReferencia(rs.getString("referencia"));
               dt.setContacto(rs.getString("contacto"));
               return true;
           }
               return false;
               
            }catch (Exception e) {
                System.err.println(e);
                return false;
                
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    public boolean eliminar(Datos_Envio dt){
       PreparedStatement ps = null;
       Connection con = getConexion();
       ResultSet rs = null;
       
       String sql = "DELETE FROM DATOS_ENVIO WHERE id_envio = ?";
       
        try {
           ps = con.prepareStatement(sql);
           ps.setInt(1, dt.getID_Envio());
           ps.execute();
           return true;
            
            }catch (Exception e) {
                System.err.println(e);
                return false;
                
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    public List<Datos_Envio> listarDatosEnvio(){
        List<Datos_Envio> lista = new ArrayList<>();
        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM DATOS_ENVIO";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Datos_Envio DT = new Datos_Envio();
                DT.setID_Envio(rs.getInt("id_envio"));
                DT.setID_Pedido(rs.getInt("id_pedido"));
                DT.setDireccion(rs.getString("direccion"));
                DT.setReferencia(rs.getString("referencia"));
                DT.setContacto(rs.getString("contacto"));
                lista.add(DT);
                
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
