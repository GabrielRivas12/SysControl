package modelo;

import java.util.Date;

/**
 *
 * @author Gabriel Rivas
 */
public class FechaExpiracion {

    
    private int id_expiracion;
    private int id_producto;
    private String NombreP;
    private Date FechaExpiracion;
    private String CodigoLote;
    
      public FechaExpiracion(int id_expiracion, int id_producto, Date FechaExpiracion, String CodigoLote) {
        this.id_expiracion = id_expiracion;
        this.id_producto = id_producto;
        this.FechaExpiracion = FechaExpiracion;
        this.CodigoLote = CodigoLote;
    }
    
    public FechaExpiracion(int id_expiracion, int id_producto, String NombreP, Date FechaExpiracion, String CodigoLote) {
        this.id_expiracion = id_expiracion;
        this.id_producto = id_producto;
        this.NombreP = NombreP;
        this.FechaExpiracion = FechaExpiracion;
        this.CodigoLote = CodigoLote;
    }

    public FechaExpiracion(int id_producto, Date FechaExpiracion, String CodigoLote) {
        this.id_producto = id_producto;
        this.FechaExpiracion = FechaExpiracion;
        this.CodigoLote = CodigoLote;
    }
    
     

    public int getId_expiracion() {
        return id_expiracion;
    }

    public void setId_expiracion(int id_expiracion) {
        this.id_expiracion = id_expiracion;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombreP() {
        return NombreP;
    }

    public void setNombreP(String NombreP) {
        this.NombreP = NombreP;
    }

    public Date getFechaExpiracion() {
        return FechaExpiracion;
    }

    public void setFechaExpiracion(Date FechaExpiracion) {
        this.FechaExpiracion = FechaExpiracion;
    }

    public String getCodigoLote() {
        return CodigoLote;
    }

    public void setCodigoLote(String CodigoLote) {
        this.CodigoLote = CodigoLote;
    }
}

   

   
