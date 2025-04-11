package modelo;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author gabriel rivas
 */
public class Database {
    
     // hace llamada al matodo getinstance de la clase conexion
    Conexion conectar =Conexion.getInstance();
    
    public List<Map<String, Object>> Listar(String procedimiento) throws SQLException{
    ResultSet rs;
    //Arreglo de elementos en el que se almacenara los datos obtenidos de bd
    List <Map<String, Object>> resultado = new ArrayList<>();
    try{
        // llamada a procedimientos almacenado sin parametros
         CallableStatement st =conectar.Conectar().
            prepareCall("{Call " + procedimiento + "}");
            //ejecucion de procimiento / obtencion de datos en Resulset
            rs=st.executeQuery();
            resultado=OrganizarDatos(rs); //Llama a metodo que organiza datos
            
            //conectar.cerrarconexion();
    }catch(SQLException e){
        System.out.println("No se realizo la consulta"+ e.getMessage());
    }
    return resultado;
    }
    
    private List <Map<String,Object>> OrganizarDatos(ResultSet rs){
        List <Map<String, Object>>filas=new ArrayList<>(); //Arreglo de elementos
        try{
        int numColumnas=rs.getMetaData().getColumnCount();
        while(rs.next()){ //recorre cada registro de la tabla
            Map<String, Object> renglon=new HashMap<>();
            for (int i = 1; i <=numColumnas; i++) {
                //se obtiene nombre de campo en la bd
                String nombreCampo=rs.getMetaData().getColumnName(i);
                Object valor=rs.getObject(nombreCampo);
                //por cada campo, se obtiene el nombre y el valor del mismo
                
                renglon.put(nombreCampo, valor);
            }
            filas.add(renglon);
        
        }
        }catch(SQLException e){
            System.out.println(e+"Error");
        }
        return filas;
    }

}
