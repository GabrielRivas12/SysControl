package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author gabriel rivas
 */
public class Conexion {
    
       //Cadena de conexion de la BD sql
    
    private final String URL="jdbc:mysql://localhost:3306/facturacion";   
    private final String user="root";//usuario
    private final String password="grivas12";// password
    
    private static Conexion Instancia= null;
    private static Connection Conex=null;
          
        public Conexion(){} // Constructor privado para evitar crear instancias
        
    public Connection Conectar(){
        try{
        //usando Driver y cadena de conexion para conectar BD
        Conex=DriverManager.getConnection(URL,user,password);
            System.out.println("Conexion Establecida");
            return Conex;
        }catch(SQLException e){
            System.out.println("Error de conexion"+e);
        }return Conex;
    }
    
    public void cerrarConexion()throws SQLException{
    try{
        Conex.close();
        System.out.println("Conexion Cerrada");
    }catch(SQLException e){
        System.out.println("Error de conexion "+e);
        Conex.close();
    }finally{
        Conex.close();
    }
    }
    
    public static Conexion getInstance(){
        if (Instancia == null) {
            Instancia = new Conexion();
        }
        return Instancia;
    }
}
