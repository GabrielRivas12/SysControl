/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import com.sun.jdi.connect.spi.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.Conexion;
import modelo.Database;
import modelo.Transacciones;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Gabriel Rivas
 */
public class DAOTransaccion {

    Conexion conectar = Conexion.getInstance();

    //========Peticion que obtiene los datos de la tabla producto============//
    public List ObtenerHistorial() throws SQLException {

        String proced = "listarHistorial()"; //lista de productos
        List<Map> registros = new Database().Listar(proced);
        List<Transacciones> trans = new ArrayList();
        for (Map registro : registros) {
            Transacciones trancc = new Transacciones(
                    (int) registro.get("id_transaccion"),
                    (int) registro.get("id_proveedor"),
                    (int) registro.get("id_producto"),
                    (int) registro.get("cantidad"),
                    (double) registro.get("monto"),
                    (Date) registro.get("fecha"),
                    (String) registro.get("estado")
            );
            trans.add(trancc);

        }
        return trans;
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

public void FacturaProvicional(int id_proveedor) throws JRException {

    
    
    // Ruta del archivo .jrxml
    String path = "src/Reportes/FacturaProvicional.jrxml";

    JasperReport jr;

    try {
        // Compilar el archivo .jrxml
        jr = JasperCompileManager.compileReport(path);

        // Crear un mapa de parámetros y pasar el ProveedorID
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("id_proveedor", id_proveedor);  // Pasa el ProveedorID como parámetro

        // Llenar el reporte con los parámetros
        JasperPrint mostrarReporte4 = JasperFillManager.fillReport(jr, parametros, conectar.Conectar());

        // Mostrar el reporte
        JasperViewer.viewReport(mostrarReporte4, false);
    } catch (JRException e) {
        JOptionPane.showMessageDialog(null, e);
        System.out.println("Error" + e);
    }
}




}
