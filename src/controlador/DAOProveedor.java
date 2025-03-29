/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import modelo.Proveedor;

/**
 *
 * @author Gabriel Rivas
 */
public class DAOProveedor {

    Conexion conectar = Conexion.getInstance();

    //========Peticion que obtiene los datos de la tabla producto============//
    public List ObtenerProveedores() throws SQLException {

        String proced = "listarProveedores()"; //lista de productos
        List<Map> registros = new Database().Listar(proced);
        List<Proveedor> proveedores = new ArrayList();
        for (Map registro : registros) {
            Proveedor provee = new Proveedor((int) registro.get("id_proveedor"),
                    (String) registro.get("nombre"),
                    (String) registro.get("tipo_proveedor"),
                    (String) registro.get("nombre_contacto"),
                    (int) registro.get("telefono"),
                    (String) registro.get("correo"),
                    (int) registro.get("id_banco"),
                    (int) registro.get("numero_cuenta")
            );
            proveedores.add(provee);

        }
        return proveedores;
    }

    //========Peticion de busqueda de producto a la base de datos============//
    public List busquedaProveedores(String parametroBusqueda) throws SQLException {
        ResultSet rs = null;
        List<Map> registros = null;
        List<Proveedor> proveedores = new ArrayList();

        List resultado = new ArrayList();

        try {
            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL buscarProveedores(?)}");

            st.setString(1, parametroBusqueda);
            rs = st.executeQuery();
            resultado = OrganizarDatos(rs);
            registros = resultado;

            for (Map registro : registros) {
                Proveedor provee = new Proveedor((int) registro.get("id_proveedor"),
                        (String) registro.get("nombre"),
                        (String) registro.get("tipo_proveedor"),
                        (String) registro.get("nombre_contacto"),
                        (int) registro.get("telefono"),
                        (String) registro.get("correo"),
                        (int) registro.get("id_banco"),
                        (int) registro.get("cuenta")
                );
                proveedores.add(provee);

            }
        } catch (SQLException e) {
            System.out.println("No se realizo la consulta:" + e.getMessage());
        }
        return proveedores;
    }

    //==============Metodo que organiza los datos obtenidos==================//
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

    public int Insertar(Proveedor provee) throws SQLException {
        try {

            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL insertarProveedor(?,?,?,?,?,?,?) }");
            st.setString(1, provee.getNombre());
            st.setString(2, provee.getTipo_proveedor());
            st.setString(3, provee.getNombre_contacto());
            st.setInt(4, provee.getTelefono());
            st.setString(5, provee.getCorreo());
            st.setInt(6, provee.getId_banco());
            st.setInt(7, provee.getNumero_cuenta());

            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e + "Error");
            conectar.cerrarConexion();
            return -1;
        }
        conectar.cerrarConexion();
        return 0;
    }

    public int Borrar(int idProveedor) throws SQLException {
        try {
            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL borrarProveedor(?)}");
            st.setInt(1, idProveedor);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e + "ERROR");
            conectar.cerrarConexion();
            return -1;
        }
        conectar.cerrarConexion();
        return 0;
    }

    public int Actualizar(Proveedor provee) throws SQLException {
        try {
            CallableStatement st = conectar.Conectar().
                    prepareCall("{CALL actualizarProveedor(?,?,?,?,?,?,?,?)}");

            st.setInt(1,provee.getId_proveedor());
            st.setString(2, provee.getNombre());
            st.setString(3, provee.getTipo_proveedor());
            st.setString(4, provee.getNombre_contacto());
            st.setInt(5, provee.getTelefono());
            st.setString(6, provee.getCorreo());
            st.setInt(7, provee.getId_banco());
            st.setInt(8, provee.getNumero_cuenta());

            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e + " Error al actualizar autor");
            conectar.cerrarConexion();
            return -1;
        }
        conectar.cerrarConexion();
        return 0;
    }
}
