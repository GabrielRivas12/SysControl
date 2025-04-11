package controlador;

import java.sql.CallableStatement;
import modelo.Conexion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import modelo.Categorias;
import modelo.Database;

/**
 *
 * @author gabriel rivas
 */
public class DAOCategoria {

    Conexion conectar = Conexion.getInstance();

    public List ObtenerDatos() throws SQLException {

        String proced = "listarCategoria()";
        List<Map<String, Object>> registros = new Database().Listar(proced);
        List<Categorias> cat = new ArrayList<>();

        for (Map registro : registros) { // aqui recupera los datos de la base de datos CUIDADO
            Categorias ca = new Categorias((int) registro.get("id_categoria"),
                    (String) registro.get("nombrecategoria")
            );
            cat.add(ca);
        }
        return cat;
    }

    public int Insertar(Categorias cat) throws SQLException {
        try {

            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL insertarCategoria(?) }");

            st.setString(1, cat.getNombreCategoria());

            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e + "Error");
            conectar.cerrarConexion();
            return -1;
        }
        conectar.cerrarConexion();
        return 0;
    }

    public int Actualizar(Categorias cat) throws SQLException {
        try {
            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL actualizarCategoria(?,?)}");

            st.setInt(1, cat.getId_categoria());
            st.setString(2, cat.getNombreCategoria());

            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e + " Error al actualizar autor");
            conectar.cerrarConexion();
            return -1;
        }
        conectar.cerrarConexion();
        return 0;
    }

    public int Borrar(int id) throws SQLException {
        try {
            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL borrarCategoria(?)}");
            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e + "ERROR");
            conectar.cerrarConexion();
            return -1;
        }
        conectar.cerrarConexion();
        return 0;
    }

}
