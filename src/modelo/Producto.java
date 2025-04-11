package modelo;

/**
 *
 * @author gabriel rivas
 */
public class Producto {
    
    int id_producto;
    int id_categoria;
    String nombreProducto;
    Double precio;
    int existencia;
    double iva;
    double preciocompra;
    double preciodescuento;
    double descuento;

    
      public Producto(int id_producto, int id_categoria, String nombreProducto, Double precio, int existencia,double preciocompra ) {
        this.id_producto = id_producto;
        this.id_categoria = id_categoria;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.existencia = existencia;
        this.preciocompra = preciocompra;
    }
      
        public Producto(int id_producto, int id_categoria, String nombreProducto, Double precio, int existencia,  double iva, double preciocompra, double preciodescuento, double descuento) {
        this.id_producto = id_producto;
        this.id_categoria = id_categoria;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.existencia = existencia;
        this.iva = iva;
        this.preciocompra = preciocompra;
        this.preciodescuento = preciodescuento;
        this.descuento = descuento;
        
    }


    public Producto(int id_categoria, String nombreProducto, Double precio, int existencia, double preciocompra) {
        this.id_categoria = id_categoria;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.existencia = existencia;
        this.preciocompra = preciocompra;
        
        
    }

    public Producto(int id_producto, int existencia) {
        this.id_producto = id_producto;
        this.existencia = existencia;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }
    
     public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }
    
     public double getPreciocompra() {
        return preciocompra;
    }

    public void setPreciocompra(double preciocompra) {
        this.preciocompra = preciocompra;
    }
    
        public double getPreciodescuento() {
        return preciodescuento;
    }

    public void setPreciodescuento(double preciodescuento) {
        this.preciodescuento = preciodescuento;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    
}


