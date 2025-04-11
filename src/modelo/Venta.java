package modelo;

import java.util.Date;

/**
 *
 * @author gabriel rivas
 */
public class Venta {
    
    int num_factura;
    int num_pago;
    Date fecha;
    int num_detalle;
    int id_producto;
    int cantidad;
    Double precio;
    Double subtotal;  // sin usar
    Double total;    // sin usar

   
    Double cantidad_efectivo;
    
    // constructor con todas las variables
    public Venta(int num_factura, int num_pago, Date fecha, int num_detalle, int id_producto, int cantidad, Double precio, Double subtotal, Double total, Double cantidad_efectivo) {
        this.num_factura = num_factura;
        this.num_pago = num_pago;
        this.fecha = fecha;
        this.num_detalle = num_detalle;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = subtotal;
        this.total = total;
        this.cantidad_efectivo = cantidad_efectivo;
    }
    // un constructor para actualizar 
    public Venta(int num_pago, Date fecha, int id_producto, int cantidad, Double precio) {
        
        this.num_pago = num_pago;
        this.fecha = fecha;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

   //-------------------- Para guardar en la tabla factura -------------------------//
    public Venta(int num_pago, Date fecha,  Double cantidad_efectivo ) {
       
        this.num_pago = num_pago;
        this.fecha = fecha;
        this.cantidad_efectivo = cantidad_efectivo;
        
    }
    //------------------------------------------------------------------------------//


    //-------------------- Para guardar en la tabla detalle ------------------------//
    public Venta(int num_factura, int id_producto, int cantidad, Double precio) {
        this.num_factura = num_factura;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio = precio;
    }
    
   

    public int getNum_factura() {
        return num_factura;
    }

    public void setNum_factura(int num_factura) {
        this.num_factura = num_factura;
    }

    

   
    

    public int getNum_pago() {
        return num_pago;
    }

    public void setNum_pago(int num_pago) {
        this.num_pago = num_pago;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getNum_detalle() {
        return num_detalle;
    }

    public void setNum_detalle(int num_detalle) {
        this.num_detalle = num_detalle;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
 
    public Double getCantidad_efectivo() {
        return cantidad_efectivo;
    }

    public void setCantidad_efectivo(Double cantidad_efectivo) {
        this.cantidad_efectivo = cantidad_efectivo;
    }
    
}
