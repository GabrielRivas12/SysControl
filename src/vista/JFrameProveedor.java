/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.DAOProveedor;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import modelo.Proveedor;
import modelo.Banco;
import controlador.DAOBanco;
import controlador.DAOTransaccion;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import modelo.Transacciones;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Gabriel Rivas
 */
public class JFrameProveedor extends javax.swing.JFrame {

    private DAOTransaccion daoTrans;  // Declaración

    /**
     * Creates new form JFrameProveedor
     */
    public JFrameProveedor() throws SQLException {
        this.daoTrans = new DAOTransaccion(); // Inicialización correcta
        initComponents();
        fullscreen();

        ObtenerProveedor();
        ObtenerHistoriales();
        ComboBancos();
        llenarBanco();
        botonImprimir(daoTrans);

    }

    private void ComboBancos() throws SQLException {
        DAOBanco daobancos = new DAOBanco();
        List<Banco> listarBanco = daobancos.ObtenerBancos();

        JComboBox<Banco> comboBoxbanco = new JComboBox<>();
        for (Banco bancos : listarBanco) {
            comboBoxbanco.addItem(bancos);
        }

        jTableProveedores.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comboBoxbanco));

        Banco AgregarNuevo = new Banco(0, "Agregar Nuevo");
        comboBoxbanco.addItem(AgregarNuevo);

        // Manejar la acción del JComboBox
        comboBoxbanco.addActionListener(e -> {
            Banco seleccionado = (Banco) comboBoxbanco.getSelectedItem();

            if (seleccionado != null && "Agregar Nuevo".equals(seleccionado.getNombre())) {

                // Si se selecciona "Agregar Nuevo", pedir el nombre
                String nuevoNombre = JOptionPane.showInputDialog(jTableProveedores, "Ingrese el nombre del nuevo banco:");

                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    try {
                        Banco nuevoBanco = new Banco(daobancos.ObtenerBancos().size() + 1, nuevoNombre);
                        daobancos.InsertarNuevo(nuevoBanco); // Guardar en la BD

                        // Agregar al JComboBox
                        comboBoxbanco.insertItemAt(nuevoBanco, comboBoxbanco.getItemCount() - 1);
                        comboBoxbanco.setSelectedItem(nuevoBanco); // Seleccionar el nuevo banco

                        // 🔄 Forzar actualización de la tabla para reflejar el cambio
                        jTableProveedores.repaint();
                        jTableProveedores.revalidate();
                    } catch (SQLException ex) {
                        Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    // Si no se ingresa nada, volver a la opción original
                    comboBoxbanco.setSelectedIndex(0);
                }
            }
        });

    }
    
    private void llenarBanco() throws SQLException{
         DAOBanco daobancos = new DAOBanco();
        List<Banco> listarBanco = daobancos.ObtenerBancos();

        JComboBox<Banco> comboBoxbanco = new JComboBox<>();
        for (Banco bancos : listarBanco) {
            jComboBoxBanco.addItem(bancos);
        }
    }
    

    private void botonImprimir(DAOTransaccion daoTrans) {

        jTableHistorial.getColumnModel().getColumn(7).setCellRenderer(new TableCellRenderer() {
            private final JButton boton = new JButton("Imprimir");

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return boton;  // Solo devuelve el botón sin lógica de eventos
            }
        });

        jTableHistorial.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            private final JButton boton = new JButton("Imprimir");

            {
                boton.addActionListener(e -> {
                    int selectedRow = jTableHistorial.getSelectedRow(); // Obtener la fila seleccionada
                    if (selectedRow != -1) {
                        Object id_pproveedor = jTableHistorial.getValueAt(selectedRow, 1); // Asegúrate de que esta columna tiene el id_proveedor

                        if (id_pproveedor != null) {
                            try {
                                int id_proveedor = Integer.parseInt(id_pproveedor.toString()); // Convertir el valor a Integer

                                System.out.println("Imprimiendo factura para ProveedorID: " + id_proveedor);
                                daoTrans.FacturaProvicional(id_proveedor); // Llamar al método con el parámetro

                            } catch (NumberFormatException | JRException ex) {
                                Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null, "Error al obtener el ID del proveedor.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "El ID del proveedor no es válido.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor selecciona una fila.");
                    }
                });
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return boton;
            }
        });
    }

    private void ObtenerProveedor() throws SQLException {

        List<Proveedor> proveedores = new DAOProveedor().ObtenerProveedores();

        DefaultTableModel modelo = new DefaultTableModel();
        String[] columnas = {"ID Proveedor", "Nombre", "Tipo", "Nombre contacto", "Telefono", "correo", "ID Banco", "Numero cuenta"};

        modelo.setColumnIdentifiers(columnas);
        for (Proveedor pr : proveedores) {

            String[] renglon = {Integer.toString(pr.getId_proveedor()),
                pr.getNombre(),
                pr.getTipo_proveedor(),
                pr.getNombre_contacto(),
                Integer.toString(pr.getTelefono()),
                pr.getCorreo(),
                Integer.toString(pr.getId_banco()),
                Integer.toString(pr.getNumero_cuenta()),};
            modelo.addRow(renglon);
        }
        jTableProveedores.setModel(modelo);

    }

    private void ObtenerHistoriales() throws SQLException {

        List<Transacciones> Transs = new DAOTransaccion().ObtenerHistorial();

        DefaultTableModel modelo = new DefaultTableModel();
        String[] columnas = {"ID", "ID Proveedor", "ID Producto", "Cantidad", "Monto", "Fecha", "Estado", "Imprimir"};

        // Formateador de fecha para mostrar de manera legible
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");

        modelo.setColumnIdentifiers(columnas);
        for (Transacciones pr : Transs) {

            String fechaFormateada = (pr.getFecha() != null)
                    ? formatoFecha.format(pr.getFecha())
                    : "Fecha no disponible";

            String[] renglon = {
                Integer.toString(pr.getId_transacion()),
                Integer.toString(pr.getId_proveedor()),
                Integer.toString(pr.getId_producto()),
                Integer.toString(pr.getCantidad()),
                Double.toString(pr.getMonto()),
                fechaFormateada,
                pr.getEstado(),};
            modelo.addRow(renglon);
        }
        jTableHistorial.setModel(modelo);

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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProveedores = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableHistorial = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        Imagen1a = new javax.swing.JLabel();
        Imagen3a = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Imagen3 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Imagen4a = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Imagen1 = new javax.swing.JLabel();
        Imagen2 = new javax.swing.JLabel();
        Imagen2a = new javax.swing.JLabel();
        Imagen4 = new javax.swing.JLabel();
        Imagen5a = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Imagen5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ImagenH = new javax.swing.JLabel();
        ImagenH1 = new javax.swing.JLabel();
        jTextNombre = new javax.swing.JTextField();
        jComboBoxBanco = new javax.swing.JComboBox();
        jTextNombreContacto = new javax.swing.JTextField();
        jTextTelefono = new javax.swing.JTextField();
        jTextCorreo = new javax.swing.JTextField();
        jTextNumeroCuenta = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableProveedores);

        jTableHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTableHistorial);

        jPanel5.setBackground(new java.awt.Color(70, 132, 244));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Imagen1a.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen1aMouseClicked(evt);
            }
        });
        jPanel5.add(Imagen1a, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 170, 40));

        Imagen3a.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen3aMouseClicked(evt);
            }
        });
        jPanel5.add(Imagen3a, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, 150, 40));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Cerrar sesión");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 460, 140, -1));

        Imagen3.setText("Imagen 3");
        Imagen3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen3MouseClicked(evt);
            }
        });
        jPanel5.add(Imagen3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 40, 40));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Registro de salidas");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, -1, -1));

        Imagen4a.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen4aMouseClicked(evt);
            }
        });
        jPanel5.add(Imagen4a, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 150, 40));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Reportes");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 110, -1));

        Imagen1.setText("Imagen 1");
        Imagen1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen1MouseClicked(evt);
            }
        });
        jPanel5.add(Imagen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 40, 40));

        Imagen2.setText("Imagen 2");
        Imagen2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen2MouseClicked(evt);
            }
        });
        jPanel5.add(Imagen2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 40, 40));

        Imagen2a.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen2aMouseClicked(evt);
            }
        });
        jPanel5.add(Imagen2a, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 160, 40));

        Imagen4.setText("Imagen 4");
        Imagen4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen4MouseClicked(evt);
            }
        });
        jPanel5.add(Imagen4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 40, 40));

        Imagen5a.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen5aMouseClicked(evt);
            }
        });
        jPanel5.add(Imagen5a, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 440, 170, 40));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Inventario");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 140, -1));

        Imagen5.setText("Imagen5");
        Imagen5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen5MouseClicked(evt);
            }
        });
        jPanel5.add(Imagen5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 40, 40));

        jPanel2.setBackground(new java.awt.Color(35, 97, 191));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SysControl");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 80));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Sistema de ventas");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 140, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Home");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 140, -1));

        ImagenH.setText("Home");
        ImagenH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ImagenHMouseClicked(evt);
            }
        });
        jPanel5.add(ImagenH, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 40, 40));

        ImagenH1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ImagenH1MouseClicked(evt);
            }
        });
        jPanel5.add(ImagenH1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 160, 40));

        jTextNombre.setText("Nombre");

        jTextNombreContacto.setText("Nombre contac");

        jTextTelefono.setText("Telefono");

        jTextCorreo.setText("Correo");

        jTextNumeroCuenta.setText("Cuenta");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minorista", "Mayorista", "Directo", "Exclusivo", "Local" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextNombreContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextNumeroCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboBoxBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(141, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 89, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextNombreContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextNumeroCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Imagen1aMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen1aMouseClicked

        try {
            JFrameVenta ven = new JFrameVenta(); // TODO add your handling code here:
            ven.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Imagen1aMouseClicked

    private void Imagen3aMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen3aMouseClicked

        try {
            JFrameSalida _salida = new JFrameSalida(); // TODO add your handling code here:
            _salida.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_Imagen3aMouseClicked

    private void Imagen3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen3MouseClicked

        try {
            JFrameSalida _salida = new JFrameSalida(); // TODO add your handling code here:
            _salida.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_Imagen3MouseClicked

    private void Imagen4aMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen4aMouseClicked

        JFrameReporte _reporte = new JFrameReporte(); // TODO add your handling code here:
        _reporte.setVisible(true);

    }//GEN-LAST:event_Imagen4aMouseClicked

    private void Imagen1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen1MouseClicked
        try {
            JFrameVenta ven = new JFrameVenta(); // TODO add your handling code here:
            ven.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_Imagen1MouseClicked

    private void Imagen2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen2MouseClicked
        try {
            JFrameInventario _inventario = new JFrameInventario(); // TODO add your handling code here:
            _inventario.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Imagen2MouseClicked

    private void Imagen2aMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen2aMouseClicked
        try {
            JFrameInventario _inventario = new JFrameInventario(); // TODO add your handling code here:
            _inventario.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Imagen2aMouseClicked

    private void Imagen4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen4MouseClicked
        JFrameReporte _reporte = new JFrameReporte(); // TODO add your handling code here:
        _reporte.setVisible(true);

    }//GEN-LAST:event_Imagen4MouseClicked

    private void Imagen5aMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen5aMouseClicked
        System.exit(0);

        // TODO add your handling code here:
    }//GEN-LAST:event_Imagen5aMouseClicked

    private void Imagen5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen5MouseClicked
        System.exit(0);
    }//GEN-LAST:event_Imagen5MouseClicked

    private void ImagenHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImagenHMouseClicked
        JframeHome _home = new JframeHome(); // TODO add your handling code here:
        _home.setVisible(true);
        this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_ImagenHMouseClicked

    private void ImagenH1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImagenH1MouseClicked
        JframeHome _home = new JframeHome(); // TODO add your handling code here:
        _home.setVisible(true);
        this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_ImagenH1MouseClicked

    private void fullscreen() {
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
    }

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
            java.util.logging.Logger.getLogger(JFrameProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JFrameProveedor().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Imagen1;
    private javax.swing.JLabel Imagen1a;
    private javax.swing.JLabel Imagen2;
    private javax.swing.JLabel Imagen2a;
    private javax.swing.JLabel Imagen3;
    private javax.swing.JLabel Imagen3a;
    private javax.swing.JLabel Imagen4;
    private javax.swing.JLabel Imagen4a;
    private javax.swing.JLabel Imagen5;
    private javax.swing.JLabel Imagen5a;
    private javax.swing.JLabel ImagenH;
    private javax.swing.JLabel ImagenH1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox jComboBoxBanco;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableHistorial;
    private javax.swing.JTable jTableProveedores;
    private javax.swing.JTextField jTextCorreo;
    private javax.swing.JTextField jTextNombre;
    private javax.swing.JTextField jTextNombreContacto;
    private javax.swing.JTextField jTextNumeroCuenta;
    private javax.swing.JTextField jTextTelefono;
    // End of variables declaration//GEN-END:variables
}
