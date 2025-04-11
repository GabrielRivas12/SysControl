package controlador;

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
    
      public List <ModoPago> ObtenerDatos() throws SQLException {
          //Nomnbre del procedimiento almacenado
          String proced = "listarModoPago()";
          
          //Llamada a metodo listar de database.java, se le pasa el proced
          List<Map<String, Object>> registros = new Database().Listar(proced);
          List<ModoPago> modoPago = new ArrayList<>(); //ARREGLO
          
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
