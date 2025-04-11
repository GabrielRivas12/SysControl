package controlador;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import modelo.Banco;
import modelo.Conexion;
import modelo.Database;

/**
 *
 * @author Gabriel Rivas
 */
public class DAOBanco {
     Conexion conectar = Conexion.getInstance();
      
      public List <Banco> ObtenerBancos() throws SQLException {
          //Nomnbre del procedimiento almacenado
          String proced = "listarBancos()";
          
          //Llamada a metodo listar de database.java, se le pasa el proced
          List<Map<String, Object>> registros = new Database().Listar(proced);
          List<Banco> Bancos = new ArrayList<>(); //ARREGLO
          
          //-------ciclo que recorre cada registro y los add al array----------
          for (Map registro : registros) {
              Banco mp = new Banco((int) registro.get("id_banco"),
              (String)registro.get("nombre")
              );
           Bancos.add(mp);
          }
          return Bancos; // retorna los  registros ubicados en la tabla BD
      }   
      
       //======Llamado de procedimiento para insertar en la tabla producto======//
    public int InsertarNuevo(Banco bac) throws SQLException {
        try {

            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL insertarBanco(?) }");
            st.setString(1, bac.getNombre());
          

            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e + "Error");
            conectar.cerrarConexion();
            return -1;
        }
        conectar.cerrarConexion();
        return 0;
    }
}
