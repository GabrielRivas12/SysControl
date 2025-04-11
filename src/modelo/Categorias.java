package modelo;

/**
 *
 * @author gabriel rivas
 */
public class Categorias {

    int id_categoria;
    String nombreCategoria;
    
    public Categorias(int id_categoria, String nombreCategoria) {
        this.id_categoria = id_categoria;
        this.nombreCategoria = nombreCategoria;
    }
    
     public Categorias(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    
    @Override
    public String toString(){
    return id_categoria + " - " + nombreCategoria;
    }
    

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
            
    
    
}
