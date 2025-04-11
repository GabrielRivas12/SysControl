package controlador;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.List;
import modelo.Conexion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.Database;
import modelo.Detalle;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author gabriel rivas
 */
public class DAODetalle {
    
    Conexion conectar = Conexion.getInstance();
    
    public List <Detalle> ObtenerSalida()throws SQLException{
        String Proced ="listaVenta";
        
         List<Map<String, Object>> registros = new Database().Listar(Proced);
           List<Detalle> detall = new ArrayList<>();
           
           for(Map registro: registros ){
               Detalle deta = new Detalle ((int) registro.get("num_factura"),
                       (int) registro.get("id_producto"),
                       (String) registro.get("nombreProducto"),
                       (int) registro.get("cantidad"),
                       (Double) registro.get("precioventa"),
                       (Date) registro.get("fecha"));
                   detall.add(deta);
           }return detall;
    
    }
    
     public List <Detalle> busquedaPorFecha(String parametroBusqueda) throws SQLException{
        ResultSet rs;
        List<Map<String, Object>> registros;
        List<Detalle> detalles = new ArrayList<>();
        
        List <Map<String, Object>> resultado;
        
        try{
            CallableStatement st = conectar.Conectar(). 
                    prepareCall("{CALL buscarFechaoFact(?)}");
                    
            st.setString(1, parametroBusqueda);
            rs=st.executeQuery();
            resultado = OrganizarDatos(rs);
            registros = resultado;
            
            for (Map registro : registros) {
              Detalle deta = new Detalle ((int) registro.get("num_factura"),
                       (int) registro.get("id_producto"),
                       (String) registro.get("nombreProducto"),
                       (int) registro.get("cantidad"),
                       (Double) registro.get("precioventa"),
                       (Date) registro.get("fecha"));
                    detalles.add(deta);
                
            }
        }catch (SQLException e){
            System.out.println("No se realizo la consulta:" + e.getMessage());
        }
        return detalles;
    }
     
     private List <Map<String, Object>> OrganizarDatos(ResultSet rs) {
        List <Map<String,Object>> filas = new ArrayList<>(); 
        
        try{
            
            int numColumnas = rs.getMetaData().getColumnCount();
            while(rs.next()){
                Map<String, Object> renglon = new HashMap<>();
                
                for (int i = 1; i <= numColumnas; i++) {
                    
                    String nombreCampo = rs.getMetaData().getColumnName(i);
                    Object valor = rs.getObject(nombreCampo);
                    renglon.put(nombreCampo,valor);
                }
                filas.add(renglon);
            }
        }catch(SQLException e){
            System.out.println("Error"+ e);
        }
        return filas;
    }
     
      public void gananaciasDias()throws JRException{
        conectar.Conectar();
        
        //ruta 
        String path =  "src/Reportes/Ganancias.jrxml";
        
        JasperReport jr;
        
        try{
            jr = JasperCompileManager.compileReport(path);
            JasperPrint mostrarReporte2 = JasperFillManager.fillReport
                    (jr,null,conectar.Conectar());
            
            JasperViewer.viewReport(mostrarReporte2, false);
        }catch(JRException e){
            JOptionPane.showMessageDialog(null, e);
            System.out.println("Error" + e);
        }
    }
}
