package vista;

import java.awt.Image;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import controlador.DAODetalle;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import modelo.Detalle;

/**
 *
 * @author gabriel rivas
 */
public class JFrameSalida extends javax.swing.JFrame {

    private ImageIcon imagen;
    private Icon icono;

    public JFrameSalida() throws SQLException {
        initComponents();
        
        
          //======================FULLSCREEN===============================//
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = screenSize.width;
            int screenHeight = screenSize.height;
               
            // Configurar la ventana para que inicie en pantalla completa con bordes
            setSize(screenWidth, screenHeight);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            
            // 3️⃣ Agregar un listener para ajustar los componentes cuando se redimensione
            addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
        int newWidth = getWidth();
        int newHeight = getHeight();
        getContentPane().setPreferredSize(new Dimension(newWidth, newHeight));
        getContentPane().revalidate();
    }
});
    

        setTitle("SysControl");
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Icono.png")).getImage());
        ObtenerSalida();

    }

    private void ObtenerSalida() throws SQLException {

        List<Detalle> deta = new DAODetalle().ObtenerSalida();

        DefaultTableModel modelo = new DefaultTableModel();
        String[] columnas = {"Numero factura", "ID Producto", "Producto", "Cantidad", "Precioventa", "Fecha"};

        modelo.setColumnIdentifiers(columnas);
        for (Detalle de : deta) {

            String[] renglon = {Integer.toString(de.getNum_factura()), Integer.toString(de.getId_producto()), de.getNombreProducto(), Integer.toString(de.getCantidad()), Double.toString(de.getPrecioventa()), de.getFecha().toString()};
            modelo.addRow(renglon);
            jTableSalida.setModel(modelo);

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableSalida = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        Sistemaventas = new javax.swing.JLabel();
        registrosSalida = new javax.swing.JLabel();
        Proveedores = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        reportes = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        Inventario = new javax.swing.JLabel();
        Imagen5a = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        Home = new javax.swing.JLabel();
        cerrarsesion = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(211, 217, 254));

        jPanel1.setBackground(new java.awt.Color(211, 217, 254));
        jPanel1.setForeground(new java.awt.Color(211, 217, 254));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(159, 175, 254));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(37, 108, 240), 5, true));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jTableSalida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Numero factura", "ID Producto", "Producto", "Cantidad", "Precio venta", "Fecha"
            }
        ));
        jScrollPane2.setViewportView(jTableSalida);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Ingrese un numero de factura o fecha");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(478, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 813, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, 830, 530));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel13.setText("Registro de salidas");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Tabla de facturas registradas");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, -1, -1));

        jPanel10.setBackground(new java.awt.Color(70, 132, 244));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Sistemaventas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SistemaventasMouseClicked(evt);
            }
        });
        jPanel10.add(Sistemaventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 170, 40));

        registrosSalida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registrosSalidaMouseClicked(evt);
            }
        });
        jPanel10.add(registrosSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, 150, 40));

        Proveedores.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Proveedores.setForeground(new java.awt.Color(255, 255, 255));
        Proveedores.setText("Proveedores");
        Proveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProveedoresMouseClicked(evt);
            }
        });
        jPanel10.add(Proveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 460, 140, -1));

        jLabel28.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Registro de salidas");
        jPanel10.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, -1, -1));

        reportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportesMouseClicked(evt);
            }
        });
        jPanel10.add(reportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 150, 40));

        jLabel29.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Reportes");
        jPanel10.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 110, -1));

        Inventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                InventarioMouseClicked(evt);
            }
        });
        jPanel10.add(Inventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 160, 40));

        Imagen5a.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen5aMouseClicked(evt);
            }
        });
        jPanel10.add(Imagen5a, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 440, 170, 40));

        jLabel30.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Inventario");
        jPanel10.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 140, -1));

        jPanel14.setBackground(new java.awt.Color(35, 97, 191));

        jLabel31.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("SysControl");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel31)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 80));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Sistema de ventas");
        jPanel10.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 140, -1));

        jLabel32.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Home");
        jPanel10.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 140, -1));

        Home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HomeMouseClicked(evt);
            }
        });
        jPanel10.add(Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 160, 40));

        cerrarsesion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cerrarsesion.setForeground(new java.awt.Color(255, 255, 255));
        cerrarsesion.setText("Cerrar sesión");
        jPanel10.add(cerrarsesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 520, 140, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Proveedores.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 50, 50));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/home.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Carrito.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Inventario.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Salida.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Reporte.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, -1, -1));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/login.png"))); // NOI18N
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, -1, -1));

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 1080));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1920, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased

        try {
            String datoprod = jTextField1.getText();

            buscarDetalles(datoprod);

        } catch (SQLException ex) {
            Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyReleased

    private void SistemaventasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SistemaventasMouseClicked

        try {
            JFrameVenta ven = new JFrameVenta(); // TODO add your handling code here:
            ven.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SistemaventasMouseClicked

    private void registrosSalidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registrosSalidaMouseClicked

        try {
            JFrameSalida _salida = new JFrameSalida(); // TODO add your handling code here:
            _salida.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_registrosSalidaMouseClicked

    private void ProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProveedoresMouseClicked
        try {
            JFrameProveedor proveedor = new JFrameProveedor();
            proveedor.setVisible(true);
            this.dispose();
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ProveedoresMouseClicked

    private void reportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportesMouseClicked

        JFrameReporte _reporte = new JFrameReporte(); // TODO add your handling code here:
        _reporte.setVisible(true);
    }//GEN-LAST:event_reportesMouseClicked

    private void InventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InventarioMouseClicked
        try {
            JFrameInventario _inventario = new JFrameInventario(); // TODO add your handling code here:
            _inventario.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_InventarioMouseClicked

    private void Imagen5aMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen5aMouseClicked
        System.exit(0);

        // TODO add your handling code here:
    }//GEN-LAST:event_Imagen5aMouseClicked

    private void HomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeMouseClicked
        JframeHome _home = new JframeHome(); // TODO add your handling code here:
        _home.setVisible(true);
        this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_HomeMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        try {
            JFrameProveedor _prov = new JFrameProveedor();
            _prov.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        JframeHome _home = new JframeHome(); // TODO add your handling code here:
        _home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        try {
            JFrameVenta _venta = new JFrameVenta();
            _venta.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        try {
            JFrameInventario _invent = new JFrameInventario();
            _invent.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        try {
            JFrameSalida _salida = new JFrameSalida();
            _salida.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        JFrameReporte _report = new JFrameReporte(); // TODO add your handling code here:
        _report.setVisible(true);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel33MouseClicked

    private void buscarDetalles(String dato) throws SQLException {
        List<Detalle> deta = new DAODetalle().busquedaPorFecha(dato);

        DefaultTableModel modelo = new DefaultTableModel();

        String[] columnas = {"num_factura", "id_producto", "nombreProducto", "cantidad", "precioventa", "fecha"};

        modelo.setColumnIdentifiers(columnas);
        for (Detalle de : deta) {

            String[] renglon = {Integer.toString(de.getNum_factura()), Integer.toString(de.getId_producto()), de.getNombreProducto(), Integer.toString(de.getCantidad()), Double.toString(de.getPrecioventa()), de.getFecha().toString()};
            modelo.addRow(renglon);
            jTableSalida.setModel(modelo);
        }
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
    private javax.swing.JLabel Home;
    private javax.swing.JLabel Imagen5a;
    private javax.swing.JLabel Inventario;
    private javax.swing.JLabel Proveedores;
    private javax.swing.JLabel Sistemaventas;
    private javax.swing.JLabel cerrarsesion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableSalida;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel registrosSalida;
    private javax.swing.JLabel reportes;
    // End of variables declaration//GEN-END:variables
}
