package modelo;

import java.util.Date;

/**
 *
 * @author Gabriel Rivas
 */
public class Transacciones {

    private int id_transacion;
    private int id_proveedor;
    private int id_producto;
    private int cantidad;
    private double monto;
    private Date fecha;
    private String estado;

    public Transacciones(int id_transacion, int id_proveedor, int id_producto, int cantidad, double monto, Date fecha, String estado) {
        this.id_transacion = id_transacion;
        this.id_proveedor = id_proveedor;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.monto = monto;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Transacciones(int id_proveedor, int id_producto, int cantidad, double monto, Date fecha, String estado) {
        this.id_proveedor = id_proveedor;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.monto = monto;
        this.fecha = fecha;
        this.estado = estado;
    }
    
    public Transacciones(int id_proveedor, int id_producto, int cantidad, Date fecha, String estado) {
        this.id_proveedor = id_proveedor;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getId_transacion() {
        return id_transacion;
    }

    public void setId_transacion(int id_transacion) {
        this.id_transacion = id_transacion;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
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

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
