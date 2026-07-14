package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    Connection con = null;
    String bd = "bn0jgh3gevgtewsmefiy";
    String url = "jdbc:mysql://bn0jgh3gevgtewsmefiy-mysql.services.clever-cloud.com:3306/" + bd;
    String user = "uxll8lv7l1ynssnx";
    String password = "wPSHsZc6bkByP4ItcY8W";
    
    public Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e);
        }
        return con;
    }
}

