/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Gabriel Rivas
 */
public class Proveedor {

    public Proveedor(int id_proveedor, String nombre, String tipo_proveedor, String nombre_contacto, int telefono, String correo, int id_banco, int numero_cuenta) {
        this.id_proveedor = id_proveedor;
        this.nombre = nombre;
        this.tipo_proveedor = tipo_proveedor;
        this.nombre_contacto = nombre_contacto;
        this.telefono = telefono;
        this.correo = correo;
        this.id_banco = id_banco;
        this.numero_cuenta = numero_cuenta;
    }

    
    int id_proveedor;
    String nombre;
    String tipo_proveedor;
    String nombre_contacto;
    int telefono;
    String correo;
    int id_banco;
    int numero_cuenta;
    
       
        
        public Proveedor( String nombre, String tipo_proveedor, String nombre_contacto, int telefono, String correo, int id_banco, int numero_cuenta) {
        this.nombre = nombre;
        this.tipo_proveedor = tipo_proveedor;
        this.nombre_contacto = nombre_contacto;
        this.telefono = telefono;
        this.correo = correo;
        this.id_banco = id_banco;
        this.numero_cuenta = numero_cuenta;
    }
    
    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo_proveedor() {
        return tipo_proveedor;
    }

    public void setTipo_proveedor(String tipo_proveedor) {
        this.tipo_proveedor = tipo_proveedor;
    }

    public String getNombre_contacto() {
        return nombre_contacto;
    }

    public void setNombre_contacto(String nombre_contacto) {
        this.nombre_contacto = nombre_contacto;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getId_banco() {
        return id_banco;
    }

    public void setId_banco(int id_banco) {
        this.id_banco = id_banco;
    }

    public int getNumero_cuenta() {
        return numero_cuenta;
    }

    public void setNumero_cuenta(int numero_cuenta) {
        this.numero_cuenta = numero_cuenta;
    }


    

    
}
