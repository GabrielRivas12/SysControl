/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import vista.JFrameInventario;
import vista.JFrameVenta;
import vista.JFrameSalida;
import vista.JFrameReporte;

/**
 *
 * @author gabriel rivas
 */
public class JframeHome extends javax.swing.JFrame {

    private ImageIcon imagen;
    private Icon icono;

//==============================================================================//
    public JframeHome() {
        initComponents();
       this.setLocationRelativeTo(null);
        this.setSize(1550, 1000);
        
        this.mostrarImagen(Imagen1,
                "src\\Imagenes\\Carrito.png");
        this.mostrarImagen(Imagen2,
                "src\\Imagenes\\Inventario.png");
        this.mostrarImagen(Imagen3,
                "src\\Imagenes\\Salida.png");
        this.mostrarImagen(Imagen4,
                "src\\Imagenes\\Reporte.png");
    }
    //==========================================================================//

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOTaass modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Imagen2 = new javax.swing.JLabel();
        Imagen1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Imagen3 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Imagen4 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(211, 217, 254));

        jPanel1.setBackground(new java.awt.Color(211, 217, 254));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Imagen2.setText("jLabel1");
        Imagen2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen2MouseClicked(evt);
            }
        });
        jPanel1.add(Imagen2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 360, 117, 109));

        Imagen1.setText("Imagen 1");
        Imagen1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen1MouseClicked(evt);
            }
        });
        jPanel1.add(Imagen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 360, 115, 109));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Sistema de ventas");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 510, 170, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("Inventario");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 510, 140, -1));

        Imagen3.setText("jLabel1");
        Imagen3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen3MouseClicked(evt);
            }
        });
        jPanel1.add(Imagen3, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 350, 121, 117));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Registro de salidas");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 510, -1, -1));

        Imagen4.setText("jLabel1");
        Imagen4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen4MouseClicked(evt);
            }
        });
        jPanel1.add(Imagen4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 360, 112, 109));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("Reportes");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 510, 110, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1920, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1080, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Imagen1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen1MouseClicked

        try {
            JFrameVenta ven = new JFrameVenta(); // TODO add your handling code here:
            ven.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_Imagen1MouseClicked

    private void Imagen2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen2MouseClicked

        try {
            JFrameInventario inv = new JFrameInventario(); // TODO add your handling code here:
            inv.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_Imagen2MouseClicked

    private void Imagen3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen3MouseClicked

        try {
            JFrameSalida _salida = new JFrameSalida(); // TODO add your handling code here:
            _salida.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_Imagen3MouseClicked

    private void Imagen4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen4MouseClicked

        JFrameReporte _reporte = new JFrameReporte(); // TODO add your handling code here:
        _reporte.setVisible(true);
    }//GEN-LAST:event_Imagen4MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JframeHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JframeHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JframeHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JframeHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JframeHome().setVisible(true);
            }
        });
    }

    private void mostrarImagen(JLabel lbl, String ruta) {
        this.imagen = new ImageIcon(ruta);
        this.icono = new ImageIcon(
                this.imagen.getImage().getScaledInstance(
                        lbl.getWidth(),
                        lbl.getHeight(),
                        Image.SCALE_DEFAULT));
        lbl.setIcon(this.icono);
        this.repaint();

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Imagen1;
    private javax.swing.JLabel Imagen2;
    private javax.swing.JLabel Imagen3;
    private javax.swing.JLabel Imagen4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
