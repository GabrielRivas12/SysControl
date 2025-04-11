package controlador;

import java.sql.CallableStatement;
import java.util.List;
import modelo.Conexion;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import modelo.Database;
import modelo.Producto;

/**
 *
 * @author gabriel rivas
 */
public class DAOProducto {

    Conexion conectar = Conexion.getInstance();

    //========Peticion de busqueda de producto a la base de datos============//
    public List <Producto> busquedaProducto(String parametroBusqueda) throws SQLException {
        ResultSet rs = null;
        List<Map> registros = null;
        List <Producto> productos = new ArrayList<>();

        List resultado = new ArrayList();

        try {
            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL buscarProducto(?)}");

            st.setString(1, parametroBusqueda);
            rs = st.executeQuery();
            resultado = OrganizarDatos(rs);
            registros = resultado;

            for (Map registro : registros) {
                Producto pro = new Producto((int) registro.get("id_producto"),
                        (int) registro.get("id_categoria"),
                        (String) registro.get("nombreProducto"),
                        (double) registro.get("precio"),
                        (int) registro.get("Existencia"),
                        (double) registro.get("iva"),
                        (double) registro.get("preciocompra"),
                        (double) registro.get("preciodescuento"),
                        (double) registro.get("descuento")
                );
                productos.add(pro);

            }
        } catch (SQLException e) {
            System.out.println("No se realizo la consulta:" + e.getMessage());
        }
        return productos;
    }

    //==============Metodo que organiza los datos obtenidos==================//
    private List OrganizarDatos(ResultSet rs) {
        List filas = new ArrayList();

        try {

            int numColumnas = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Map<String, Object> renglon = new HashMap<>();

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

    //========Peticion que obtiene los datos de la tabla producto============//
    public List <Producto> ObtenerProducto() throws SQLException {

        String proced = "listarProductos()"; //lista de productos
        List <Map<String, Object>> registros = new Database().Listar(proced);
        List<Producto> products = new ArrayList<>();

        for (Map registro : registros) {
            Producto proo = new Producto((int) registro.get("id_producto"),
                    (int) registro.get("id_categoria"),
                    (String) registro.get("nombreProducto"),
                    (double) registro.get("precio"),
                    (int) registro.get("Existencia"),
                    (double) registro.get("iva"),
                    (double) registro.get("preciocompra"),
                    (double) registro.get("preciodescuento"),
                    (double) registro.get("descuento")
            );

            products.add(proo);
        }
        return products;

    }

    //======Llamado de procedimiento para insertar en la tabla producto======//
    public int Insertar(Producto prod) throws SQLException {
        try {

            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL insertarProducto(?,?,?,?,?) }");
            st.setInt(1, prod.getId_categoria());
            st.setString(2, prod.getNombreProducto());
            st.setDouble(3, prod.getPrecio());
            st.setInt(4, prod.getExistencia());
            st.setDouble(5,prod.getPreciocompra());

            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e + "Error");
            conectar.cerrarConexion();
            return -1;
        }
        conectar.cerrarConexion();
        return 0;
    }

    //=======Llamado de procedimiento para borrar en la tabla producto=======//
    public int Borrar(int idProducto) throws SQLException {
        try {
            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL borrarProducto(?)}");
            st.setInt(1, idProducto);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e + "ERROR");
            conectar.cerrarConexion();
            return -1;
        }
        conectar.cerrarConexion();
        return 0;
    }

    //=====Llamado de procedimiento para actualizar en la tabla producto=====//
    public int Actualizar(Producto pro) throws SQLException {
        try {
            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL actualizarProducto(?,?,?,?,?,?)}");

            // Asegúrate de pasar los parámetros en el orden correcto
            st.setInt(1, pro.getId_producto()); // Se requiere el ID del autor para actualizar
            st.setInt(2, pro.getId_categoria());
            st.setString(3, pro.getNombreProducto());
            st.setDouble(4, pro.getPrecio());
            st.setInt(5, pro.getExistencia());
            st.setDouble(6,pro.getPreciocompra());

            // Aquí es donde se debe especificar que solo se actualiza el autor con el ID proporcionado
            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e + " Error al actualizar autor");
            conectar.cerrarConexion();
            return -1;
        }
        conectar.cerrarConexion();
        return 0;
    }
    
    public double obtenerSumaTotalCantidad() throws SQLException {
    double totalCantidad = 0;
    ResultSet rs = null;

    try {
        // Consulta SQL para obtener la suma de todas las cantidades de productos
        CallableStatement st = conectar.Conectar().prepareCall("{CALL CantidadStock()}");

        rs = st.executeQuery();

        if (rs.next()) {
            // Asumimos que el valor de la suma está en la columna "totalCantidad"
            totalCantidad = rs.getDouble("totalCantidad");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener la suma de la cantidad de productos: " + e.getMessage());
    } finally {
        if (rs != null) {
            rs.close();
        }
    }

    return totalCantidad;
}

    public double obtenerInventarioTotal() throws SQLException {
    double inventarioTotal = 0;
    ResultSet rs = null;

    try {
        // Consulta SQL para obtener el total del inventario
        CallableStatement st = conectar.Conectar().prepareCall("{CALL obtenerInventarioTotal()}");

        rs = st.executeQuery();

        if (rs.next()) {
            // Obtenemos el valor del inventario total
            inventarioTotal = rs.getDouble("inventarioTotal");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener el inventario total: " + e.getMessage());
    } finally {
        if (rs != null) {
            rs.close();
        }
    }

    return inventarioTotal;
}

}
