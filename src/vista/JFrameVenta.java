package vista;

import controlador.DAOModoPago;
import controlador.DAOProducto;
import controlador.DAOVenta;
import java.awt.Dimension;
import modelo.ModoPago;
import modelo.Producto;
import modelo.Venta;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author gabriel rivas
 */
public class JFrameVenta extends javax.swing.JFrame {

    private ImageIcon imagen;
    private Icon icono;

    private DefaultTableModel modeloTablaVenta;
    private Object[] objetoVentaTabla = new Object[6];
    private Double total = 0.0;
    private int item = 0;
    private java.sql.Date fech;
    private int numfac = 0;
    private int cantidad = 0;
    private int idproducto;

    public JFrameVenta() throws SQLException {

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
        

        jDialogProducto.setSize(100, 100);
        ObtenerProductos();
        ObtenerProductos();
        setTitle("SysControl");
        this.setLocationRelativeTo(null); // Centra el JFrame en la pantalla
        jDialogProducto.setLocationRelativeTo(null);
        fech = java.sql.Date.valueOf(LocalDate.now());
        jLabelFecha.setText(fech.toString());
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Icono.png")).getImage());

        try {
            llenarCombModoPago();

        } catch (SQLException ex) {
            Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
        }

        modeloTablaVenta = (DefaultTableModel) jTableProductosVenta.getModel();
    }

    //================================================================================//
    private void ObtenerProductos() throws SQLException {

        List<Producto> proo = new DAOProducto().ObtenerProducto();

        DefaultTableModel modelo = new DefaultTableModel();
        String[] columnas = {"ID Producto", "Producto", "Precio", "Existencia"};

        modelo.setColumnIdentifiers(columnas);
        for (Producto pr : proo) {

            String[] renglon = {Integer.toString(pr.getId_producto()), pr.getNombreProducto(), Double.toString(pr.getPrecio()), Integer.toString(pr.getExistencia())};
            modelo.addRow(renglon);
        }
        jTableBuscarProducto.setModel(modelo);

    }
    //================================================================================//

    //================================================================================//
    public void llenarCombModoPago() throws SQLException {

        List<ModoPago> modoPago = new DAOModoPago().ObtenerDatos();
        for (int i = 0; i < modoPago.size(); i++) {

            jComboModoPago.addItem(new ModoPago(modoPago.
                    get(i).getNum_pago(),
                    modoPago.get(i).getNombreModoP()));
        }
    }

    //================================================================================//
    private void buscarDatosProductos(String dato) throws SQLException {
        List<Producto> productos = new DAOProducto().busquedaProducto(dato);

        DefaultTableModel modelo = new DefaultTableModel();

        String[] columnas = {"ID Producto", "Producto", "Precio", "Existencia"};

        modelo.setColumnIdentifiers(columnas);
        for (Producto pro : productos) {

            String[] renglon = {
                Integer.toString(pro.getId_producto()),
                pro.getNombreProducto(),
                Double.toString(pro.getPrecio()),
                Integer.toString(pro.getExistencia())
            };
            modelo.addRow(renglon);
        }
        jTableBuscarProducto.setModel(modelo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogProducto = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jTextDbuscarProducto12 = new javax.swing.JTextField();
        jBDagregarProducto12 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableBuscarProducto44 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableBuscarProducto = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jTextDbuscarProducto = new javax.swing.JTextField();
        jLabelFecha = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableProductosVenta = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabelTotal = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        JTextcantidadEfectivo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboModoPago = new javax.swing.JComboBox<>();
        jBguardarVenta = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
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
        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();

        jPanel7.setBackground(new java.awt.Color(211, 217, 254));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(159, 175, 254));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(14, 94, 237), 5));

        jTextDbuscarProducto12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDbuscarProducto12ActionPerformed(evt);
            }
        });
        jTextDbuscarProducto12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextDbuscarProducto12KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextDbuscarProducto12KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextDbuscarProducto12KeyTyped(evt);
            }
        });

        jBDagregarProducto12.setText("Añadir");
        jBDagregarProducto12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDagregarProducto12ActionPerformed(evt);
            }
        });

        jTableBuscarProducto44.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio", "Existencia"
            }
        ));
        jScrollPane3.setViewportView(jTableBuscarProducto44);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Buscar");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextDbuscarProducto12, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBDagregarProducto12))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextDbuscarProducto12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBDagregarProducto12)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 630, 250));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel17.setText("Buscar Producto");
        jPanel7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(267, 36, -1, -1));

        javax.swing.GroupLayout jDialogProductoLayout = new javax.swing.GroupLayout(jDialogProducto.getContentPane());
        jDialogProducto.getContentPane().setLayout(jDialogProductoLayout);
        jDialogProductoLayout.setHorizontalGroup(
            jDialogProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jDialogProductoLayout.setVerticalGroup(
            jDialogProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(211, 217, 254));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(159, 175, 254));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 108, 240), 5));

        jTableBuscarProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio", "Existencia"
            }
        ));
        jTableBuscarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableBuscarProductoMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableBuscarProducto);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Buscar");

        jTextDbuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDbuscarProductoActionPerformed(evt);
            }
        });
        jTextDbuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextDbuscarProductoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextDbuscarProductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextDbuscarProductoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel18)
                .addGap(11, 11, 11)
                .addComponent(jTextDbuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextDbuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, 550, 450));

        jLabelFecha.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabelFecha.setText("Fecha");
        jPanel1.add(jLabelFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(1380, 20, 151, -1));

        jPanel2.setBackground(new java.awt.Color(14, 94, 237));

        jTableProductosVenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jTableProductosVenta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTableProductosVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {}, // Arreglo de datos vacío, sin filas
            new String[] { "Items", "Código", "Producto", "Cantidad", "Precio", "Subtotal" } // Nombre de las columnas

        ));
        jTableProductosVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProductosVentaMouseClicked(evt);
            }
        });
        jTableProductosVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableProductosVentaKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(jTableProductosVenta);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 150, -1, 450));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Total: C$");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1370, 620, -1, 20));

        jLabelTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTotal.setText("0");
        jPanel1.add(jLabelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1430, 620, 100, 20));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setText("Lista de productos facturados");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 110, 260, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Datos del pago");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 620, 210, 30));
        jPanel1.add(JTextcantidadEfectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 710, 140, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Efectivo Recibido");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 710, -1, 20));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Modo de Pago");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 670, -1, -1));

        jComboModoPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboModoPagoActionPerformed(evt);
            }
        });
        jPanel1.add(jComboModoPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 670, -1, -1));

        jBguardarVenta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jBguardarVenta.setText("Guardar");
        jBguardarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBguardarVentaActionPerformed(evt);
            }
        });
        jPanel1.add(jBguardarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 620, 90, 31));

        jBCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jBCancelar.setText("Cancelar");
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(jBCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 620, 90, 31));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel19.setText("Sistema de venta");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, -1, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setText("Productos en el inventario");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, 260, 30));

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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/home.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Carrito.png"))); // NOI18N
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Inventario.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Salida.png"))); // NOI18N
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //=========================================================================================//
    private void jBDagregarProducto12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDagregarProducto12ActionPerformed

        int fila = this.jTableBuscarProducto44.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione un registro de la tabla");
        } else {
            try {
                int id = Integer.parseInt((String) this.jTableBuscarProducto44.
                        getValueAt(fila, 0).toString());

                String nom = (String) this.jTableBuscarProducto44.getValueAt(fila, 1);

                double pre = Double.parseDouble((String) this.jTableBuscarProducto44.
                        getValueAt(fila, 2).toString());

                int Exis = Integer.parseInt((String) this.jTableBuscarProducto44.
                        getValueAt(fila, 3).toString());

                // --------------- se ubican las cajas de texto lo datos capturados ----------//
//                jTextIdProducto.setText("" + id);
                //  jTextproducto.setText(nom);
                //   jTextPrecio.setText("" + pre);
                // jTextExistenciaProducto.setText("" + Exis);
                jDialogProducto.dispose();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(rootPane, "Ocurrio un ERROR" + e.getMessage());
            }

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jBDagregarProducto12ActionPerformed

    //==============================================================================//
    private void jBguardarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBguardarVentaActionPerformed

        try {

            if (jTableProductosVenta.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay productos en la tabla para procesar la venta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return; // Salir si no hay productos
            }
            // Obtener el total de la venta desde el jLabel
            double totalVenta = Double.parseDouble(jLabelTotal.getText().trim());

            // Obtener el efectivo ingresado y verificar que no esté vacío
            String efectivoStr = JTextcantidadEfectivo.getText().trim();

            if (efectivoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa la cantidad de efectivo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return; // Salir si no se ingresó efectivo
            }

            // Validar si el efectivo es suficiente en la misma línea
            if (Double.parseDouble(efectivoStr) < totalVenta) {
                JOptionPane.showMessageDialog(this, "Efectivo insuficiente. Total de la venta: " + totalVenta, "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {

                // Si todo está bien, continuar con la transacción
                guardarVenta(); // Aquí puedes llamar a guardarVenta si no lo has hecho antes
                ObtenerProductos();

                total = 0.0;
                jLabelTotal.setText("0");
                JTextcantidadEfectivo.setText(" "); // Limpia el campo de efectivo

                // Llama a DAOVenta para procesar la factura
                DAOVenta daoventa = new DAOVenta();
                daoventa.factura();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El efectivo ingresado no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jBguardarVentaActionPerformed

    //=============================================================================//
    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        total = 0.0;
        jLabelTotal.setText("0");
        
        limpiartabla();
        try {
            ObtenerProductos();
        } catch (SQLException ex) {
            Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBCancelarActionPerformed

    //=======================================================================================//
    private void jComboModoPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboModoPagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboModoPagoActionPerformed

    //=======================================================================================//
    private void jTextDbuscarProducto12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextDbuscarProducto12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDbuscarProducto12ActionPerformed

    private void jTextDbuscarProducto12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDbuscarProducto12KeyPressed
    }//GEN-LAST:event_jTextDbuscarProducto12KeyPressed

    private void jTextDbuscarProducto12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDbuscarProducto12KeyTyped

        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDbuscarProducto12KeyTyped

    //=======================================================================================//
    private void jTextDbuscarProducto12KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDbuscarProducto12KeyReleased
        String datoprod = jTextDbuscarProducto12.getText().trim();

        if (datoprod.isEmpty()) {
            // Si el campo de búsqueda está vacío, muestra todos los productos o un mensaje
            try {
                ObtenerProductos();
            } catch (SQLException ex) {
                Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Realiza la búsqueda dinámica según el texto ingresado
            try {
                buscarDatosProductos(datoprod);
            } catch (SQLException ex) {
                Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDbuscarProducto12KeyReleased

    private void jTextDbuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextDbuscarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDbuscarProductoActionPerformed

    private void jTextDbuscarProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDbuscarProductoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDbuscarProductoKeyPressed

    private void jTextDbuscarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDbuscarProductoKeyReleased
        String datoprod = jTextDbuscarProducto.getText().trim();

        if (datoprod.isEmpty()) {
            // Si el campo de búsqueda está vacío, muestra todos los productos o un mensaje
            try {
                ObtenerProductos();
            } catch (SQLException ex) {
                Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Realiza la búsqueda dinámica según el texto ingresado
            try {
                buscarDatosProductos(datoprod);
            } catch (SQLException ex) {
                Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDbuscarProductoKeyReleased

    private void jTextDbuscarProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDbuscarProductoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDbuscarProductoKeyTyped

    private void jTableBuscarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableBuscarProductoMouseClicked
        int fila = this.jTableBuscarProducto.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione un registro de la tabla");
        } else {
            try {
                // Obtener datos de la fila seleccionada
                int id = Integer.parseInt(this.jTableBuscarProducto.getValueAt(fila, 0).toString());
                String nombre = this.jTableBuscarProducto.getValueAt(fila, 1).toString();
                double precio = Double.parseDouble(this.jTableBuscarProducto.getValueAt(fila, 2).toString());
                int existencia = Integer.parseInt(this.jTableBuscarProducto.getValueAt(fila, 3).toString());

                // Pedir cantidad al usuario
                String cantidadStr = JOptionPane.showInputDialog(rootPane,
                        "Ingrese la cantidad a retirar (disponible: " + existencia + "):",
                        "Cantidad",
                        JOptionPane.QUESTION_MESSAGE);

                if (cantidadStr != null && !cantidadStr.trim().isEmpty()) {
                    int cantidad = Integer.parseInt(cantidadStr.trim());

                    // Validar la cantidad solicitada
                    if (cantidad <= 0) {
                        JOptionPane.showMessageDialog(rootPane, "La cantidad debe ser mayor que 0.");
                    } else if (cantidad > existencia) {
                        JOptionPane.showMessageDialog(rootPane, "No hay suficiente stock. Disponible: " + existencia);
                    } else {
                        // Crear una nueva fila para la tabla de ventas
                        Object[] nuevaFila = new Object[6];
                        nuevaFila[0] = jTableProductosVenta.getRowCount() + 1; // Número de ítem
                        nuevaFila[1] = id;                            // ID del producto
                        nuevaFila[2] = nombre;                        // Nombre del producto
                        nuevaFila[3] = cantidad;                      // Cantidad solicitada
                        nuevaFila[4] = precio;                        // Precio unitario
                        nuevaFila[5] = cantidad * precio;             // Subtotal

                        // Agregar la nueva fila a la tabla de ventas
                        modeloTablaVenta.addRow(nuevaFila);

                        // Actualizar el total
                        total += (cantidad * precio);
                        jLabelTotal.setText(String.format("%.0f", total));
                        //JOptionPane.showMessageDialog(rootPane, "Producto agregado correctamente.");

                        // Descontar la cantidad del stock en la tabla de productos
                        for (int i = 0; i < jTableBuscarProducto.getRowCount(); i++) {
                            int idProducto = Integer.parseInt(this.jTableBuscarProducto.getValueAt(i, 0).toString());
                            if (idProducto == id) {
                                // Obtener la nueva cantidad
                                int nuevaExistencia = existencia - cantidad;
                                // Actualizar la existencia en la tabla de productos
                                this.jTableBuscarProducto.setValueAt(nuevaExistencia, i, 3);
                                break;
                            }
                        }

                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(rootPane, "Ocurrió un error: " + e.getMessage());
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTableBuscarProductoMouseClicked

    private void jTableProductosVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProductosVentaMouseClicked
        int fila = jTableProductosVenta.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione una fila para editar.");
        } else {
            try {
                // Obtener la cantidad actual de la fila seleccionada
                int cantidadActual = Integer.parseInt(jTableProductosVenta.getValueAt(fila, 3).toString());
                int idProductoVenta = Integer.parseInt(jTableProductosVenta.getValueAt(fila, 1).toString()); // ID del producto en la venta
                String nombreProductoVenta = jTableProductosVenta.getValueAt(fila, 2).toString(); // Nombre del producto
                double precioProductoVenta = Double.parseDouble(jTableProductosVenta.getValueAt(fila, 4).toString()); // Precio

                // Crear opciones personalizadas para el JOptionPane
                Object[] opciones = {"Actualizar", "Eliminar", "Cancelar"};
                String mensaje = "Ingrese la nueva cantidad (actual: " + cantidadActual + "):";

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                JLabel etiqueta = new JLabel(mensaje);
                JTextField campoCantidad = new JTextField();
                panel.add(etiqueta);
                panel.add(campoCantidad);

                // Mostrar JOptionPane personalizado
                int opcionSeleccionada = JOptionPane.showOptionDialog(
                        rootPane,
                        panel,
                        "Editar cantidad",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]);

                if (opcionSeleccionada == JOptionPane.YES_OPTION) { // Actualizar
                    String nuevaCantidadStr = campoCantidad.getText().trim();
                    if (nuevaCantidadStr.isEmpty()) {
                        JOptionPane.showMessageDialog(rootPane, "Debe ingresar una cantidad.");
                    } else {
                        int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);

                        if (nuevaCantidad <= 0) {
                            JOptionPane.showMessageDialog(rootPane, "La cantidad debe ser mayor que 0.");
                        } else {
                            // Calcular la diferencia de la cantidad
                            int diferenciaCantidad = nuevaCantidad - cantidadActual;

                            // Actualizar la cantidad en la tabla de ventas
                            jTableProductosVenta.setValueAt(nuevaCantidad, fila, 3);

                            // Calcular el nuevo subtotal
                            double nuevoSubtotal = nuevaCantidad * precioProductoVenta;
                            jTableProductosVenta.setValueAt(nuevoSubtotal, fila, 5);

                            // Actualizar el total general
                            actualizarTotal();

                            // Descontar la diferencia de la cantidad en la tabla de productos
                            for (int i = 0; i < jTableBuscarProducto.getRowCount(); i++) {
                                int idProducto = Integer.parseInt(this.jTableBuscarProducto.getValueAt(i, 0).toString());
                                if (idProducto == idProductoVenta) {
                                    // Obtener la existencia actual del producto en la tabla de productos
                                    int existenciaProducto = Integer.parseInt(this.jTableBuscarProducto.getValueAt(i, 3).toString());

                                    // Actualizar la existencia en la tabla de productos según la diferencia
                                    this.jTableBuscarProducto.setValueAt(existenciaProducto - diferenciaCantidad, i, 3);
                                    break;
                                }
                            }

                        }
                    }
                } else if (opcionSeleccionada == JOptionPane.NO_OPTION) { // Eliminar
                    // Recuperar el id y la cantidad antes de eliminar la fila
                    int cantidadEliminada = Integer.parseInt(jTableProductosVenta.getValueAt(fila, 3).toString());
                    int idProductoEliminado = Integer.parseInt(jTableProductosVenta.getValueAt(fila, 1).toString()); // ID del producto en la venta

                    // Eliminar la fila de la tabla de ventas
                    modeloTablaVenta.removeRow(fila);

                    // Actualizar el total
                    actualizarTotal();

                    // Sumar la cantidad eliminada a la tabla de productos
                    for (int i = 0; i < jTableBuscarProducto.getRowCount(); i++) {
                        int idProducto = Integer.parseInt(this.jTableBuscarProducto.getValueAt(i, 0).toString());
                        if (idProducto == idProductoEliminado) {
                            // Obtener la existencia actual del producto en la tabla de productos
                            int existenciaProducto = Integer.parseInt(this.jTableBuscarProducto.getValueAt(i, 3).toString());

                            // Actualizar la existencia en la tabla de productos sumando la cantidad eliminada
                            this.jTableBuscarProducto.setValueAt(existenciaProducto + cantidadEliminada, i, 3);
                            break;
                        }
                    }

                    // JOptionPane.showMessageDialog(rootPane, "Fila eliminada correctamente.");
                }

                // Si la opción fue "Cancelar", no se hace nada
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(rootPane, "Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_jTableProductosVentaMouseClicked

    private void actualizarTotal() {
        total = 0.0;
        for (int i = 0; i < jTableProductosVenta.getRowCount(); i++) {
            double subtotal = Double.parseDouble(jTableProductosVenta.getValueAt(i, 5).toString());
            total += subtotal;
        }
        // Reflejar el total en el JLabel
        jLabelTotal.setText(String.format("%.0f", total));
    }

    private void jTableProductosVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableProductosVentaKeyReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_jTableProductosVentaKeyReleased

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

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        JframeHome _home = new JframeHome(); // TODO add your handling code here:
        _home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        try {
            JFrameVenta _venta = new JFrameVenta();
            _venta.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13MouseClicked

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

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        try {
            JFrameSalida _salida = new JFrameSalida();
            _salida.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        JFrameReporte _report = new JFrameReporte(); // TODO add your handling code here:
        _report.setVisible(true);
        
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel33MouseClicked
//=======================================================================================//

    public void guardarDetalleVenta() throws SQLException {

        double precio;

        if (numfac == 0 || jTableProductosVenta.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "No se ha obtenido número de factura o no tiene productos añadidos para vender");
            return; // Salir si no hay datos válidos
        }

        boolean huboError = false; // Iniciar como false, cambiar a true si ocurre algún error

        try {
            for (int i = 0; i < jTableProductosVenta.getRowCount(); i++) {

                int idproducto = Integer.parseInt(jTableProductosVenta.getValueAt(i, 1).toString());
                int cantidad = Integer.parseInt(jTableProductosVenta.getValueAt(i, 3).toString());
                precio = Double.parseDouble(jTableProductosVenta.getValueAt(i, 4).toString());

                Venta detalleventa = new Venta(numfac, idproducto, cantidad, precio);

                DAOVenta daoDetalleVenta = new DAOVenta();

                // Intentar insertar el detalle de venta
                if (daoDetalleVenta.insertarDetalleVenta(detalleventa) != 0) {
                    huboError = true; // Marcar error si no se pudo insertar el detalle
                }
            }
        } catch (SQLException | NumberFormatException ex) {
            Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
            huboError = true; // Marcar error si hay una excepción
        }

        // Mostrar mensaje de éxito o error después de procesar todos los productos
        if (huboError) {
            JOptionPane.showMessageDialog(rootPane, "Ocurrió un error al procesar algunos productos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Venta guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiartabla();
        }

    }
    //=======================================================================================//

    public void limpiartabla() {
        DefaultTableModel model = (DefaultTableModel) jTableProductosVenta.getModel();
        model.setRowCount(0); // Borra todas las filas
    }
//=======================================================================================//

    public void actualizaExistencia() {
        int existenciaActual;
        int nuevaExistencia;

        for (int i = 0; i < jTableProductosVenta.getRowCount(); i++) {
            idproducto = Integer.parseInt(jTableProductosVenta.
                    getValueAt(i, 1).toString());
            cantidad = Integer.parseInt(jTableProductosVenta.
                    getValueAt(i, 3).toString());

            DAOProducto daopro = new DAOProducto();
            try {
                List<Producto> p = daopro.busquedaProducto(String.valueOf(idproducto).toString());

                if (p.isEmpty()) {
                    // Manejar el caso donde no se encuentra el producto
                    JOptionPane.showMessageDialog(rootPane, "Producto no encontrado para el ID: " + idproducto);
                    continue; // Saltar a la siguiente iteración
                }

                existenciaActual = p.get(0).getExistencia();

                nuevaExistencia = existenciaActual - cantidad;

                Producto pro = new Producto(idproducto, nuevaExistencia);

                DAOVenta daoVenta = new DAOVenta();

                if (daoVenta.actualizarExistenciaProductos(pro) == 0) {
                    //   JOptionPane.showMessageDialog(rootPane, "Existencia Actualizada");

                }

            } catch (SQLException ex) {
                Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //=======================================================================================//

    public void guardarVenta() throws SQLException {
        int numpago = 0, idcliente;

        numpago = jComboModoPago.getItemAt(jComboModoPago.getSelectedIndex())
                .getNum_pago();

        double pago = 0;
        String efectivoTexto = JTextcantidadEfectivo.getText().trim();
        if (efectivoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "El campo de efectivo no puede estar vacío.");
            return; // Salir si el campo está vacío
        }

        try {
            // Convertir el texto a un double
            pago = Double.parseDouble(efectivoTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Por favor, ingresa un número válido para el efectivo.");
            return; // Salir si la conversión falla
        }

        if (jLabelFecha.getText().contentEquals("")
                || numpago == 0 || jTableProductosVenta.getRowCount() == 0) {

        }
        try {
            // Crear el objeto Venta con los datos necesarios
            Venta vent = new Venta(numpago, fech, pago);

            // Instancia de DAOVenta
            DAOVenta daoventa = new DAOVenta();

            // Insertar la venta en la base de datos
            if (daoventa.insertarVenta(vent) == 0) {
                // Si la inserción es exitosa, obtener el número de factura
                numfac = daoventa.obtenerUltimoNumFactura();

                actualizaExistencia();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Ha ocurrido un error, no se insertó la venta");
                return;  // Salir del método si no se insertó la venta correctamente
            }

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Error al guardar la venta: " + e.getMessage());
            return;  // Salir del método si ocurre una excepción
        }

        try {
            // Guardar los detalles de la venta
            guardarDetalleVenta();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Error al guardar los detalles de la venta o actualizar existencias: " + e.getMessage());
        }
    }
    //=======================================================================================//

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
//=======================================================================================//

     

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Home;
    private javax.swing.JLabel Imagen5a;
    private javax.swing.JLabel Inventario;
    private javax.swing.JTextField JTextcantidadEfectivo;
    private javax.swing.JLabel Proveedores;
    private javax.swing.JLabel Sistemaventas;
    private javax.swing.JLabel cerrarsesion;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBDagregarProducto12;
    private javax.swing.JButton jBguardarVenta;
    private javax.swing.JComboBox<ModoPago> jComboModoPago;
    private javax.swing.JDialog jDialogProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JLabel jLabelFecha;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTableBuscarProducto;
    private javax.swing.JTable jTableBuscarProducto44;
    private javax.swing.JTable jTableProductosVenta;
    private javax.swing.JTextField jTextDbuscarProducto;
    private javax.swing.JTextField jTextDbuscarProducto12;
    private javax.swing.JLabel registrosSalida;
    private javax.swing.JLabel reportes;
    // End of variables declaration//GEN-END:variables

}
