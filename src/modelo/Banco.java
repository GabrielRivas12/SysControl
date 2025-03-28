/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Gabriel Rivas
 */
public class Banco {
    
    private int id_banco;
    private String nombre;
    
     @Override
    public String toString(){
    return id_banco + " - "+ nombre;
    }
    
    
    

    public Banco(int id_banco, String nombre) {
        this.id_banco = id_banco;
        this.nombre = nombre;
    }

    public Banco(String nombre) {
        this.nombre = nombre;
    }

    public int getId_banco() {
        return id_banco;
    }

    public void setId_banco(int id_banco) {
        this.id_banco = id_banco;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
   
}
