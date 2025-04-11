package modelo;

import java.util.Date;

/**
 *
 * @author gabriel rivas
 */
public class Detalle {

    public Detalle(int num_factura, int id_producto, String nombreProducto, int cantidad, Double precioventa, Date fecha) {
        this.num_factura = num_factura;
        this.id_producto = id_producto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioventa = precioventa;
        this.fecha = fecha;
    }

    public int getNum_factura() {
        return num_factura;
    }

    public void setNum_factura(int num_factura) {
        this.num_factura = num_factura;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(Double precioventa) {
        this.precioventa = precioventa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    private int num_factura;
    private int id_producto;
    private String nombreProducto;
    private int cantidad;
    private Double precioventa;
    private Date fecha;
    
    
    
    
    
}
