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
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
 private ImageIcon imagen;
    private Icon icono;
    private DAOTransaccion daoTrans;  // Declaración

    public JFrameProveedor() throws SQLException {
        this.daoTrans = new DAOTransaccion(); // Inicialización correcta
        initComponents();
        icons();
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
        jTextNombre = new javax.swing.JTextField();
        jComboBoxBanco = new javax.swing.JComboBox();
        jTextNombreContacto = new javax.swing.JTextField();
        jTextTelefono = new javax.swing.JTextField();
        jTextCorreo = new javax.swing.JTextField();
        jTextNumeroCuenta = new javax.swing.JTextField();
        jComboTipo = new javax.swing.JComboBox<>();
        jTextIDproveedor = new javax.swing.JTextField();
        jTextIDproducto = new javax.swing.JTextField();
        jTextCantidad = new javax.swing.JTextField();
        jTextFecha = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButtonAñadirProveedor = new javax.swing.JButton();
        jButtonAñadirHistorial = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComboEstado = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        Sistemaventas = new javax.swing.JLabel();
        registrosSalida = new javax.swing.JLabel();
        Proveedores = new javax.swing.JLabel();
        ImagenH4 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        reportes = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        ImagenH2 = new javax.swing.JLabel();
        ImagenH3 = new javax.swing.JLabel();
        Inventario = new javax.swing.JLabel();
        ImagenH5 = new javax.swing.JLabel();
        Imagen5a = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        ImagenH6 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        ImagenH = new javax.swing.JLabel();
        Home = new javax.swing.JLabel();
        ImagenH7 = new javax.swing.JLabel();
        cerrarsesion = new javax.swing.JLabel();

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

        jComboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minorista", "Mayorista", "Directo", "Exclusivo", "Local" }));

        jButton1.setText("Borrar");

        jButton2.setText("Actualizar");

        jButtonAñadirProveedor.setText("Añadir");
        jButtonAñadirProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAñadirProveedorActionPerformed(evt);
            }
        });

        jButtonAñadirHistorial.setText("Añadir");
        jButtonAñadirHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAñadirHistorialActionPerformed(evt);
            }
        });

        jButton5.setText("Actualizar");

        jButton6.setText("Borrar");

        jLabel1.setText("Nombre del proveedor");

        jLabel2.setText("Tipo de proveedor");

        jLabel3.setText("Nombre de contacto");

        jLabel4.setText("Banco");

        jLabel6.setText("Correo");

        jLabel7.setText("Telefono");

        jLabel8.setText("ID Producto");

        jLabel9.setText("N. Cuenta");

        jLabel10.setText("ID Proveedor");

        jLabel11.setText("Fecha");

        jLabel12.setText("Cantidad");

        jLabel13.setText("Estado");

        jComboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pendiente", "No entregado", "Cancelado", "Entregado" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jComboTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextNombre))
                                .addGap(12, 12, 12)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7)
                            .addComponent(jTextNombreContacto, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(jTextTelefono))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextCorreo)
                                .addComponent(jComboBoxBanco, 0, 88, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(47, 47, 47)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextNumeroCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonAñadirProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6))
                    .addComponent(jScrollPane1))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextIDproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jTextIDproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel10))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel13))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonAñadirHistorial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(2915, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonAñadirProveedor)
                            .addComponent(jTextCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextNombreContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextIDproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton2)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel11))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jTextNumeroCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboBoxBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextIDproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel4))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTextTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonAñadirHistorial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(280, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(70, 132, 244));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Sistemaventas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SistemaventasMouseClicked(evt);
            }
        });
        jPanel8.add(Sistemaventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 170, 40));

        registrosSalida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registrosSalidaMouseClicked(evt);
            }
        });
        jPanel8.add(registrosSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, 150, 40));

        Proveedores.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Proveedores.setForeground(new java.awt.Color(255, 255, 255));
        Proveedores.setText("Proveedores");
        Proveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProveedoresMouseClicked(evt);
            }
        });
        jPanel8.add(Proveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 460, 140, -1));

        ImagenH4.setText("Imagen 3");
        ImagenH4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ImagenH4MouseClicked(evt);
            }
        });
        jPanel8.add(ImagenH4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 40, 40));

        jLabel25.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Registro de salidas");
        jPanel8.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, -1, -1));

        reportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportesMouseClicked(evt);
            }
        });
        jPanel8.add(reportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 150, 40));

        jLabel26.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Reportes");
        jPanel8.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 110, -1));

        ImagenH2.setText("Imagen 1");
        ImagenH2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ImagenH2MouseClicked(evt);
            }
        });
        jPanel8.add(ImagenH2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 40, 40));

        ImagenH3.setText("Imagen 2");
        ImagenH3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ImagenH3MouseClicked(evt);
            }
        });
        jPanel8.add(ImagenH3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 40, 40));

        Inventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                InventarioMouseClicked(evt);
            }
        });
        jPanel8.add(Inventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 160, 40));

        ImagenH5.setText("Imagen 4");
        ImagenH5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ImagenH5MouseClicked(evt);
            }
        });
        jPanel8.add(ImagenH5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 40, 40));

        Imagen5a.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Imagen5aMouseClicked(evt);
            }
        });
        jPanel8.add(Imagen5a, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 440, 170, 40));

        jLabel27.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Inventario");
        jPanel8.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 140, -1));

        ImagenH6.setText("Imagen5");
        ImagenH6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ImagenH6MouseClicked(evt);
            }
        });
        jPanel8.add(ImagenH6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 40, 40));

        jPanel10.setBackground(new java.awt.Color(35, 97, 191));

        jLabel28.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("SysControl");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel28)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 80));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Sistema de ventas");
        jPanel8.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 140, -1));

        jLabel29.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Home");
        jPanel8.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 140, -1));

        ImagenH.setText("Home");
        ImagenH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ImagenHMouseClicked(evt);
            }
        });
        jPanel8.add(ImagenH, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 40, 40));

        Home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HomeMouseClicked(evt);
            }
        });
        jPanel8.add(Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 160, 40));

        ImagenH7.setText("Imagen5");
        ImagenH7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ImagenH7MouseClicked(evt);
            }
        });
        jPanel8.add(ImagenH7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 40, 40));

        cerrarsesion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cerrarsesion.setForeground(new java.awt.Color(255, 255, 255));
        cerrarsesion.setText("Cerrar sesión");
        jPanel8.add(cerrarsesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 520, 140, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ImagenH7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImagenH7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ImagenH7MouseClicked

    private void HomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeMouseClicked
        JframeHome _home = new JframeHome(); // TODO add your handling code here:
        _home.setVisible(true);
        this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_HomeMouseClicked

    private void ImagenHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImagenHMouseClicked
        JframeHome _home = new JframeHome(); // TODO add your handling code here:
        _home.setVisible(true);
        this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_ImagenHMouseClicked

    private void ImagenH6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImagenH6MouseClicked
     try {
         JFrameProveedor _prov = new JFrameProveedor();
         _prov.setVisible(true);
         this.dispose();
     } catch (SQLException ex) {
         Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
     }
    }//GEN-LAST:event_ImagenH6MouseClicked

    private void Imagen5aMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Imagen5aMouseClicked
        System.exit(0);

        // TODO add your handling code here:
    }//GEN-LAST:event_Imagen5aMouseClicked

    private void ImagenH5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImagenH5MouseClicked
        JFrameReporte _reporte = new JFrameReporte(); // TODO add your handling code here:
        _reporte.setVisible(true);
    }//GEN-LAST:event_ImagenH5MouseClicked

    private void InventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InventarioMouseClicked
        try {
            JFrameInventario _inventario = new JFrameInventario(); // TODO add your handling code here:
            _inventario.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_InventarioMouseClicked

    private void ImagenH3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImagenH3MouseClicked
        try {
            JFrameInventario _inventario = new JFrameInventario(); // TODO add your handling code here:
            _inventario.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ImagenH3MouseClicked

    private void ImagenH2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImagenH2MouseClicked
        try {
            JFrameVenta ven = new JFrameVenta(); // TODO add your handling code here:
            ven.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_ImagenH2MouseClicked

    private void reportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportesMouseClicked

        JFrameReporte _reporte = new JFrameReporte(); // TODO add your handling code here:
        _reporte.setVisible(true);
    }//GEN-LAST:event_reportesMouseClicked

    private void ImagenH4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImagenH4MouseClicked

        try {
            JFrameSalida _salida = new JFrameSalida(); // TODO add your handling code here:
            _salida.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_ImagenH4MouseClicked

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

    private void SistemaventasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SistemaventasMouseClicked

        try {
            JFrameVenta ven = new JFrameVenta(); // TODO add your handling code here:
            ven.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JframeHome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SistemaventasMouseClicked

    private void jButtonAñadirProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAñadirProveedorActionPerformed
       int banco = 0;
    
        
      String Tipo = (String)jComboTipo.getSelectedItem();
      String seleccionado = jComboBoxBanco.getSelectedItem().toString();
      String[] partes = seleccionado.split("-"); // Divide el String en partes usando "-"
      banco = Integer.parseInt(partes[0].trim()); // Convierte la primera parte en número

      
      String nom = jTextNombre.getText();
      String nomContac = jTextNombreContacto.getText();
      String telef =jTextTelefono.getText();
      String correo = jTextCorreo.getText();
      String Ncuenta = jTextNumeroCuenta.getText();
      
        if (banco == 0 || Tipo.equals("") || nom.equals("") || nomContac.equals("") || telef.equals("") || correo.equals("") || Ncuenta.equals("") ) {
            JOptionPane.showMessageDialog(rootPane, "Todos los campos son obligatorios");
        }else{
            try{
                   int teleff = Integer.parseInt(telef);
                   int Ncuentaa = Integer.parseInt(Ncuenta);
                   
                   Proveedor proveedor = new Proveedor(nom, Tipo, nomContac, teleff, correo, banco, Ncuentaa);
                   DAOProveedor dao = new DAOProveedor();
                   
                   if(dao.Insertar(proveedor) == 0){
                       JOptionPane.showMessageDialog(rootPane, "Registro agregado");
                   }else{
                       JOptionPane.showMessageDialog(rootPane, "Error a; agregar el registro");
                   }
                   
            } catch (SQLException ex) {
               Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
           } try {
               ObtenerProveedor();
               limpiar_campoPro();
               ComboBancos();
           } catch (SQLException ex) {
                Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
         
            
           }
        }

    }//GEN-LAST:event_jButtonAñadirProveedorActionPerformed

    private void jButtonAñadirHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAñadirHistorialActionPerformed

        String idprove = jTextIDproveedor.getText();
        String idpro = jTextIDproducto.getText();
        String cant = jTextCantidad.getText();
        String fech = jTextFecha.getText();
        String estad = (String)jComboEstado.getSelectedItem();

        
        if (idprove.equals("") || idpro.equals("") || cant.equals("") || fech.equals("") || estad.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Todos los campos son obligatorios");
        }else{
            try{
                //Definir e; formato de la fecha
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-mm-dd");
                
                int idproveedor = Integer.parseInt(idprove);
                int idproducto = Integer.parseInt(idpro);
                int cantidad = Integer.parseInt(cant);
                Date Fecha = formato.parse(fech);
                
                Transacciones trans = new Transacciones(idproveedor, idproducto, cantidad, Fecha, estad);
                DAOTransaccion dao = new DAOTransaccion();
                if(dao.Insertar(trans) == 0){
                    JOptionPane.showMessageDialog(rootPane, "Registro agregado");
                }
                
                
            } catch (ParseException ex) {
                Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
            }try{
                ObtenerHistoriales();
                botonImprimir(daoTrans);
                limpiar_campoHistorial();
            }catch (SQLException ex) {
                Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }




        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAñadirHistorialActionPerformed

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
    
       private void icons(){
        
     setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Icono.png")).getImage());
     this.mostrarImagen(ImagenH,
                "src\\Imagenes\\home.png");     
     this.mostrarImagen(ImagenH2,
                "src\\Imagenes\\Carrito.png");
        this.mostrarImagen(ImagenH3,
                "src\\Imagenes\\Inventario.png");
        this.mostrarImagen(ImagenH4,
                "src\\Imagenes\\Salida.png");
        this.mostrarImagen(ImagenH5,
                "src\\Imagenes\\Reporte.png");
        this.mostrarImagen(ImagenH6,
                "src\\Imagenes\\Proveedores.png");
            this.mostrarImagen(ImagenH7,
                "src\\Imagenes\\login.png");
            
    }

    private void limpiar_campoPro(){
    jTextNombre.setText("");
    jTextNombreContacto.setText("");
    jTextTelefono.setText("");
    jTextCorreo.setText("");
    jTextNumeroCuenta.setText("");
}
    
    private void limpiar_campoHistorial(){
     jTextIDproveedor.setText("");
     jTextIDproducto.setText("");
     jTextCantidad.setText("");
     jTextFecha.setText("");
    }
       
       
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Home;
    private javax.swing.JLabel Imagen5a;
    private javax.swing.JLabel ImagenH;
    private javax.swing.JLabel ImagenH2;
    private javax.swing.JLabel ImagenH3;
    private javax.swing.JLabel ImagenH4;
    private javax.swing.JLabel ImagenH5;
    private javax.swing.JLabel ImagenH6;
    private javax.swing.JLabel ImagenH7;
    private javax.swing.JLabel Inventario;
    private javax.swing.JLabel Proveedores;
    private javax.swing.JLabel Sistemaventas;
    private javax.swing.JLabel cerrarsesion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButtonAñadirHistorial;
    private javax.swing.JButton jButtonAñadirProveedor;
    private javax.swing.JComboBox jComboBoxBanco;
    private javax.swing.JComboBox<String> jComboEstado;
    private javax.swing.JComboBox<String> jComboTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableHistorial;
    private javax.swing.JTable jTableProveedores;
    private javax.swing.JTextField jTextCantidad;
    private javax.swing.JTextField jTextCorreo;
    private javax.swing.JTextField jTextFecha;
    private javax.swing.JTextField jTextIDproducto;
    private javax.swing.JTextField jTextIDproveedor;
    private javax.swing.JTextField jTextNombre;
    private javax.swing.JTextField jTextNombreContacto;
    private javax.swing.JTextField jTextNumeroCuenta;
    private javax.swing.JTextField jTextTelefono;
    private javax.swing.JLabel registrosSalida;
    private javax.swing.JLabel reportes;
    // End of variables declaration//GEN-END:variables
}
