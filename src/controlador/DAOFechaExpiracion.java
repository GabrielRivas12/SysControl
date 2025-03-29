package controlador;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.Conexion;
import modelo.Database;
import modelo.FechaExpiracion;
import java.util.Date;

/**
 *
 * @author Gabriel Rivas
 */
public class DAOFechaExpiracion {

    Conexion conectar = Conexion.getInstance();

    public List ObtenerExpiracion() throws SQLException {

        String proced = "listarExpiracion()"; //lista de productos
        List<Map> registros = new Database().Listar(proced);
        List<FechaExpiracion> produ = new ArrayList();

        for (Map registro : registros) {
            FechaExpiracion fecha = new FechaExpiracion((int) registro.get("id_expiracion"),
                    (int) registro.get("id_producto"),
                    (String) registro.get("nombreProducto"),
                    (Date) registro.get("fechaExpiracion"),
                    (String) registro.get("codigoLote")
            );

            produ.add(fecha);
        }
        return produ;

    }

    private List OrganizarDatos(ResultSet rs) {
        List filas = new ArrayList();

        try {

            int numColumnas = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Map<String, Object> renglon = new HashMap();

                for (int i = 1; i <= numColumnas; i++) {

                    String nombreCampo = rs.getMetaData().getColumnName(i);
                    Object valor = rs.getObject(nombreCampo);
                    renglon.put(nombreCampo, valor);
                }
                filas.add(renglon);
            }
        } catch (SQLException e) {
            System.out.println("Error" + e);
        }
        return filas;
    }

    public int Insertar(FechaExpiracion exp) throws SQLException {
        try {

            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL insertarExpiracion(?,?,?) }");

            st.setInt(1, exp.getId_producto());

           
            // Convertir java.util.Date a java.sql.Date si es necesario
            java.sql.Date fechaSQL = new java.sql.Date(exp.getFechaExpiracion().getTime());
            st.setDate(2, fechaSQL);

            st.setString(3, exp.getCodigoLote());

            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e + "Error");
            conectar.cerrarConexion();
            return -1;
        }
        conectar.cerrarConexion();
        return 0;
    }
    
        public int Actualizar(FechaExpiracion exp) throws SQLException {
        try {
            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL actualizarExpiracion(?,?,?,?)}");

            
             st.setInt(1, exp.getId_expiracion());
             st.setInt(2, exp.getId_producto());
           
            // Convertir java.util.Date a java.sql.Date si es necesario
            java.sql.Date fechaSQL = new java.sql.Date(exp.getFechaExpiracion().getTime());
            st.setDate(3, fechaSQL);

            st.setString(4, exp.getCodigoLote());

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
                    prepareCall("{CALL borrarExpiracion(?)}");
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
    
   public List<FechaExpiracion> ObtenerExpiracionHoy() throws SQLException {
    List<FechaExpiracion> productosHoy = new ArrayList<>();

    try {
        // Consulta para productos que expiran hoy
        CallableStatement st = conectar.Conectar().prepareCall("{CALL productosExpiranHoy()}");
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            // Crear un objeto FechaExpiracion con los datos obtenidos
            FechaExpiracion producto = new FechaExpiracion(
                rs.getInt("id_expiracion"),
                rs.getInt("id_producto"),
                rs.getString("nombreProducto"),
                rs.getDate("fechaExpiracion"),
                rs.getString("codigoLote")
            );
            productosHoy.add(producto);
        }
        st.close();
    } catch (SQLException e) {
        System.out.println("Error al obtener productos por expirar hoy: " + e.getMessage());
    } finally {
        conectar.cerrarConexion();
    }

    return productosHoy;
}
    public List busquedaProductoVencido(String parametroBusqueda) throws SQLException {
        ResultSet rs = null;
        List<Map> registros = null;
        List<FechaExpiracion> fech = new ArrayList();

        List resultado = new ArrayList();

        try {
            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL buscarProductoVencido(?)}");

            st.setString(1, parametroBusqueda);
            rs = st.executeQuery();
            resultado = OrganizarDatos(rs);
            registros = resultado;

            for (Map registro : registros) {
                 FechaExpiracion fecha = new FechaExpiracion((int) registro.get("id_expiracion"),
                    (int) registro.get("id_producto"),
                    (String) registro.get("nombreProducto"),
                    (Date) registro.get("fechaExpiracion"),
                    (String) registro.get("codigoLote")
            );

            fech.add(fecha);
        }
        
    } catch (SQLException e) {
            System.out.println("No se realizo la consulta:" + e.getMessage());
        } return fech;

    }
}
