/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author gabriel rivas
 */
public class ModoPago {

    int num_pago;
    String nombreModoP;
    
    public ModoPago(int num_pago, String nombreModoP) {
        this.num_pago = num_pago;
        this.nombreModoP = nombreModoP;
    }

    public ModoPago(String nombreModoP) {
        this.nombreModoP = nombreModoP;
    }
    
    @Override
    public String toString(){
    return num_pago + " - " + nombreModoP;
    }

    public int getNum_pago() {
        return num_pago;
    }

    public void setNum_pago(int num_pago) {
        this.num_pago = num_pago;
    }

    public String getNombreModoP() {
        return nombreModoP;
    }

    public void setNombreModoP(String nombreModoP) {
        this.nombreModoP = nombreModoP;
    }
    
   
    
}
