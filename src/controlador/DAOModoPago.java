/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.Conexion; // Importar la clase para la conexión
import java.sql.SQLException; // Importar SQLException
import java.util.ArrayList; // Importar ArrayList
import java.util.List; // Importar java.util.List
import java.util.Map; // Importar Map
import modelo.Database;
import modelo.ModoPago;

/**
 *
 * @author gabriel rivas
 */
public class DAOModoPago {
    
      Conexion conectar = Conexion.getInstance();
      
      public List ObtenerDatos() throws SQLException {
          //Nomnbre del procedimiento almacenado
          String proced = "listarModoPago()";
          
          //Llamada a metodo listar de database.java, se le pasa el proced
          List<Map> registros = new Database().Listar(proced);
          List<ModoPago> modoPago = new ArrayList(); //ARREGLO
          
          //-------ciclo que recorre cada registro y los add al array----------
          for (Map registro : registros) {
              ModoPago mp = new ModoPago((int) registro.get("num_pago"),
              (String)registro.get("nombreModoP")
              );
           modoPago.add(mp);
          }
          return modoPago; // retorna los  registros ubicados en la tabla BD
      }   
}
