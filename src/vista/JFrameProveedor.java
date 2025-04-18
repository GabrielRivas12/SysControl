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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    private final DAOTransaccion daoTrans;  // Declaración

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
    

    private void llenarBanco() throws SQLException {
        DAOBanco daobancos = new DAOBanco();
        List<Banco> listarBanco = daobancos.ObtenerBancos();

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

    public final void ObtenerProveedor() throws SQLException {

        List<Proveedor> proveedores = new DAOProveedor().ObtenerProveedores();
        List<Banco> bancoslista = new DAOBanco().ObtenerBancos();
        Map<Integer, Banco> mapaBancos = new HashMap<>();
        for (Banco banco : bancoslista) {
            mapaBancos.put(banco.getId_banco(), banco);
        }

        DefaultTableModel modelo = new DefaultTableModel();
        String[] columnas = {"ID Proveedor", "Nombre", "Tipo", "Nombre contacto", "Telefono", "correo", "ID Banco", "Numero cuenta"};

        modelo.setColumnIdentifiers(columnas);
        for (Proveedor pr : proveedores) {
            Banco banco = mapaBancos.get(pr.getId_banco());
            String formatoBanco = banco != null ? banco.toString() : "N/A";

            String[] renglon = {Integer.toString(pr.getId_proveedor()),
                pr.getNombre(),
                pr.getTipo_proveedor(),
                pr.getNombre_contacto(),
                Integer.toString(pr.getTelefono()),
                pr.getCorreo(),
                //    Integer.toString(pr.getId_banco()),
                formatoBanco,
                Integer.toString(pr.getNumero_cuenta()),};
            modelo.addRow(renglon);
        }
        jTableProveedores.setModel(modelo);

    }

    public final void ObtenerHistoriales() throws SQLException {

        List<Transacciones> Transs = new DAOTransaccion().ObtenerHistorial();

        DefaultTableModel modelo = new DefaultTableModel();
        String[] columnas = {"ID", "ID Proveedor", "ID Producto", "Cantidad", "Monto", "Fecha", "Estado", "Imprimir"};

        // Formateador de fecha para mostrar de manera legible
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

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
        jComboBoxBanco = new javax.swing.JComboBox<>();
        jTextNombreContacto = new javax.swing.JTextField();
        jTextTelefono = new javax.swing.JTextField();
        jTextCorreo = new javax.swing.JTextField();
        jTextNumeroCuenta = new javax.swing.JTextField();
        jComboTipo = new javax.swing.JComboBox<>();
        jTextIDproveedor = new javax.swing.JTextField();
        jTextIDproducto = new javax.swing.JTextField();
        jTextCantidad = new javax.swing.JTextField();
        jTextFecha = new javax.swing.JTextField();
        jButtonBorrarProveedor = new javax.swing.JButton();
        jButtonActualizarProveedor = new javax.swing.JButton();
        jButtonAñadirProveedor = new javax.swing.JButton();
        jButtonAñadirHistorial = new javax.swing.JButton();
        jButtonActualizarHistorial = new javax.swing.JButton();
        jButtonBorrarHistorial = new javax.swing.JButton();
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
        jPanel10 = new javax.swing.JPanel();
        Sistemaventas = new javax.swing.JLabel();
        registrosSalida = new javax.swing.JLabel();
        Proveedores = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        Inventario = new javax.swing.JLabel();
        Imagen5a = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        Home = new javax.swing.JLabel();
        cerrarsesion = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableProveedores.setForeground(new java.awt.Color(0, 0, 0));
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
        jTableProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProveedoresMouseClicked(evt);
            }
        });
        jTableProveedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableProveedoresKeyReleased(evt);
            }
        });
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
        jTableHistorial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableHistorialKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTableHistorial);

        jComboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minorista", "Mayorista", "Directo", "Exclusivo", "Local" }));

        jButtonBorrarProveedor.setText("Borrar");
        jButtonBorrarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarProveedorActionPerformed(evt);
            }
        });

        jButtonActualizarProveedor.setText("Actualizar");
        jButtonActualizarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualizarProveedorActionPerformed(evt);
            }
        });

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

        jButtonActualizarHistorial.setText("Actualizar");
        jButtonActualizarHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualizarHistorialActionPerformed(evt);
            }
        });

        jButtonBorrarHistorial.setText("Borrar");
        jButtonBorrarHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarHistorialActionPerformed(evt);
            }
        });

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(jButtonActualizarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonBorrarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonAñadirProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(jButtonActualizarHistorial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonBorrarHistorial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonAñadirHistorial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(773, Short.MAX_VALUE))
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
                                        .addComponent(jButtonActualizarProveedor)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel11))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButtonBorrarProveedor)
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
                        .addComponent(jButtonActualizarHistorial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonBorrarHistorial)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(209, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 1890, 1080));

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
        jPanel10.add(Proveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 140, -1));

        jLabel28.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Registro de salidas");
        jPanel10.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, -1, -1));

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
        jPanel10.add(cerrarsesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 450, 140, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Proveedores.png"))); // NOI18N
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 50, 50));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/home.png"))); // NOI18N
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Carrito.png"))); // NOI18N
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Inventario.png"))); // NOI18N
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Salida.png"))); // NOI18N
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/login.png"))); // NOI18N
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, -1, -1));

        getContentPane().add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 1080));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAñadirProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAñadirProveedorActionPerformed
        int banco;

        String Tipo = (String) jComboTipo.getSelectedItem();
        String seleccionado = jComboBoxBanco.getSelectedItem().toString();
        String[] partes = seleccionado.split("-"); // Divide el String en partes usando "-"
        banco = Integer.parseInt(partes[0].trim()); // Convierte la primera parte en número

        String nom = jTextNombre.getText();
        String nomContac = jTextNombreContacto.getText();
        String telef = jTextTelefono.getText();
        String correo = jTextCorreo.getText();
        String Ncuenta = jTextNumeroCuenta.getText();

        if (banco == 0 || Tipo.equals("") || nom.equals("") || nomContac.equals("") || telef.equals("") || correo.equals("") || Ncuenta.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Todos los campos son obligatorios");
        } else {
            try {
                int teleff = Integer.parseInt(telef);
                int Ncuentaa = Integer.parseInt(Ncuenta);

                Proveedor proveedor = new Proveedor(nom, Tipo, nomContac, teleff, correo, banco, Ncuentaa);
                DAOProveedor dao = new DAOProveedor();

                if (dao.Insertar(proveedor) == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Registro agregado");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error a; agregar el registro");
                }

            } catch (SQLException ex) {
                Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
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
        String estad = (String) jComboEstado.getSelectedItem();

        if (idprove.equals("") || idpro.equals("") || cant.equals("") || fech.equals("") || estad.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Todos los campos son obligatorios");
        } else {
            try {
                //Definir e; formato de la fecha
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-mm-dd");

                int idproveedor = Integer.parseInt(idprove);
                int idproducto = Integer.parseInt(idpro);
                int cantidad = Integer.parseInt(cant);
                Date Fecha = formato.parse(fech);

                Transacciones trans = new Transacciones(idproveedor, idproducto, cantidad, Fecha, estad);
                DAOTransaccion dao = new DAOTransaccion();
                if (dao.Insertar(trans) == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Registro agregado");
                }

            } catch (SQLException | ParseException ex) {
                Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ObtenerHistoriales();
                botonImprimir(daoTrans);
                limpiar_campoHistorial();
            } catch (SQLException ex) {
                Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAñadirHistorialActionPerformed

    private void jButtonBorrarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarProveedorActionPerformed
        try {
            int fila = jTableProveedores.getSelectedRow();
            int id;

            if (fila == -1) {
                JOptionPane.showMessageDialog(rootPane, "Seleccione un Proveedor para borrar");
                return;
            } else {
                id = Integer.parseInt(jTableProveedores.getValueAt(fila, 0).toString());
            }

            DAOProveedor dao = new DAOProveedor();

            if (dao.Borrar(id) == 0) {
                ObtenerProveedor();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Error al borrar Producto");
                ObtenerProveedor();
            }
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButtonBorrarProveedorActionPerformed


    private void jButtonBorrarHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarHistorialActionPerformed
        try {
            int fila = jTableHistorial.getSelectedRow();
            int id;

            if (fila == -1) {
                JOptionPane.showMessageDialog(rootPane, "Seleccione una fila para borrar");
                return;
            } else {
                id = Integer.parseInt(jTableHistorial.getValueAt(fila, 0).toString());
            }

            DAOTransaccion dao = new DAOTransaccion();

            if (dao.Borrar(id) == 0) {
                ObtenerHistoriales();
                botonImprimir(daoTrans);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Error al borrar la fila");
                ObtenerHistoriales();
                botonImprimir(daoTrans);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonBorrarHistorialActionPerformed

    private final List<Proveedor> ProvedoresModificados = new ArrayList<>();

    private void jButtonActualizarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarProveedorActionPerformed
        if (!ProvedoresModificados.isEmpty()) {
            try {
                DAOProveedor dao = new DAOProveedor();
                int resultadoExitoso = 0;
                int resultadoFallido = 0;

                for (Proveedor proveedores : ProvedoresModificados) {
                    int resultado = dao.Actualizar(proveedores);
                    if (resultado == 0) {
                        resultadoExitoso++;
                    } else {
                        resultadoFallido++;
                    }
                }
                if (resultadoExitoso > 0) {
                    JOptionPane.showMessageDialog(rootPane, resultadoExitoso + "Proveedor Actualizado");
                }
                if (resultadoFallido > 0) {
                    JOptionPane.showMessageDialog(rootPane, resultadoFallido + "No se puedo actualizar el proveedor");

                }

                ProvedoresModificados.clear();
                ObtenerProveedor();
                ComboBancos();
            } catch (SQLException ex) {
                Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "No se han realizado cambios en los proveedores");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonActualizarProveedorActionPerformed

    private void jTableProveedoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableProveedoresKeyReleased
        int filaSeleccionada = jTableProveedores.getSelectedRow();
        if (filaSeleccionada == -1) {
            return;
        }

        System.out.println("Evento KeyReleased detectado en fila: " + filaSeleccionada);

        int columnaSeleccionada = jTableProveedores.getSelectedColumn();
        if (columnaSeleccionada == -1) {
            return;
        }

        // Obtener valores de la tabla
        String idprov = jTableProveedores.getValueAt(filaSeleccionada, 0).toString();
        String nom = jTableProveedores.getValueAt(filaSeleccionada, 1).toString();
        String tipo = jTableProveedores.getValueAt(filaSeleccionada, 2).toString();
        String nomcont = jTableProveedores.getValueAt(filaSeleccionada, 3).toString();
        String telef = jTableProveedores.getValueAt(filaSeleccionada, 4).toString();
        String corr = jTableProveedores.getValueAt(filaSeleccionada, 5).toString();
        String idban = jTableProveedores.getValueAt(filaSeleccionada, 6).toString();
        String ncuenta = jTableProveedores.getValueAt(filaSeleccionada, 7).toString();

        try {
            int idProveedor = Integer.parseInt(idprov);
            int telefono = Integer.parseInt(telef);
            int numeroCuenta = Integer.parseInt(ncuenta);
            String[] partes = idban.split("-");
            int banco = Integer.parseInt(partes[0].trim());

            Proveedor proveedorModificado = new Proveedor(idProveedor, nom, tipo, nomcont, telefono, corr, banco, numeroCuenta);

            // Verificar si ya existe en la lista y actualizar
            boolean encontrado = false;
            for (Proveedor prove : ProvedoresModificados) {
                if (prove.getId_proveedor() == idProveedor) {
                    prove.setNombre(nom);
                    prove.setTipo_proveedor(tipo);
                    prove.setNombre_contacto(nomcont);
                    prove.setTelefono(telefono);
                    prove.setCorreo(corr);
                    prove.setId_banco(banco);
                    prove.setNumero_cuenta(numeroCuenta);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                ProvedoresModificados.add(proveedorModificado);
                System.out.println("Proveedor agregado: " + proveedorModificado.getNombre());

            } else {
                System.out.println("Proveedor actualizado en la lista.");
            }

        } catch (NumberFormatException e) {
            System.err.println("Error en conversión de valores numéricos: " + e.getMessage());
        }
    }//GEN-LAST:event_jTableProveedoresKeyReleased

    private final List<Transacciones> TransacionesModificados = new ArrayList<>();
    private void jButtonActualizarHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarHistorialActionPerformed

        if (!TransacionesModificados.isEmpty()) {
            try {
                DAOTransaccion dao = new DAOTransaccion();
                int resultadoExitoso = 0;
                int resultadoFallido = 0;

                for (Transacciones trans : TransacionesModificados) {
                    int resultado = dao.Actualizar(trans);
                    if (resultado == 0) {
                        resultadoExitoso++;
                    } else {
                        resultadoFallido++;
                    }
                }
                if (resultadoExitoso > 0) {
                    JOptionPane.showMessageDialog(rootPane, resultadoExitoso + "Historial Actualizado");
                }
                if (resultadoFallido > 0) {
                    JOptionPane.showMessageDialog(rootPane, resultadoFallido + "No se puedo actualizar el Historial");
                }

                TransacionesModificados.clear();
                ObtenerHistoriales();
                botonImprimir(daoTrans);
            } catch (SQLException ex) {
                Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "No se han realizado cambios en el historial");
        }
    }//GEN-LAST:event_jButtonActualizarHistorialActionPerformed

    private void jTableHistorialKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableHistorialKeyReleased
        int filaSeleccionada = jTableHistorial.getSelectedRow();
        if (filaSeleccionada == -1) {
            return;
        }

        System.out.println("Evento KeyReleased detectado en fila: " + filaSeleccionada);

        int columnaSeleccionada = jTableHistorial.getSelectedColumn();
        if (columnaSeleccionada == -1) {
            return;
        }

        // Obtener valores de la tabla
        String idtransa = jTableHistorial.getValueAt(filaSeleccionada, 0).toString();
        String idprovee = jTableHistorial.getValueAt(filaSeleccionada, 1).toString();
        String idproduct = jTableHistorial.getValueAt(filaSeleccionada, 2).toString();
        String cantid = jTableHistorial.getValueAt(filaSeleccionada, 3).toString();
        String fech = jTableHistorial.getValueAt(filaSeleccionada, 5).toString();
        String estado = jTableHistorial.getValueAt(filaSeleccionada, 6).toString();

        try {
            int idTransaccion = Integer.parseInt(idtransa);
            int idProveedor = Integer.parseInt(idprovee);
            int idProducto = Integer.parseInt(idproduct);
            int Cantidad = Integer.parseInt(cantid);
            // Formateador de fecha para mostrar de manera legible
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date Fecha = formato.parse(fech);

            Transacciones transaccion = new Transacciones(idProveedor, idProducto, Cantidad, Fecha, estado);
            // Verificar si ya existe en la lista y actualizar
            boolean encontrado = false;
            for (Transacciones transs : TransacionesModificados) {
                if (transs.getId_transacion() == idTransaccion) {
                    transs.setId_producto(idProducto);
                    transs.setCantidad(Cantidad);
                    transs.setFecha(Fecha);
                    transs.setEstado(estado);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                TransacionesModificados.add(transaccion);
                ObtenerHistoriales();
                botonImprimir(daoTrans);

            } else {
                System.out.println("Transacion actualizado en la lista.");
            }

        } catch (NumberFormatException e) {
            System.err.println("Error en conversión de valores numéricos: " + e.getMessage());
        } catch (ParseException | SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTableHistorialKeyReleased

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

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        try {
            JFrameProveedor _prov = new JFrameProveedor();
            _prov.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        JframeHome _home = new JframeHome(); // TODO add your handling code here:
        _home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        try {
            JFrameVenta _venta = new JFrameVenta();
            _venta.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        try {
            JFrameInventario _invent = new JFrameInventario();
            _invent.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        try {
            JFrameSalida _salida = new JFrameSalida();
            _salida.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jTableProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProveedoresMouseClicked

    }//GEN-LAST:event_jTableProveedoresMouseClicked

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
    
    private void limpiar_campoPro() {
        jTextNombre.setText("");
        jTextNombreContacto.setText("");
        jTextTelefono.setText("");
        jTextCorreo.setText("");
        jTextNumeroCuenta.setText("");
    }

    private void limpiar_campoHistorial() {
        jTextIDproveedor.setText("");
        jTextIDproducto.setText("");
        jTextCantidad.setText("");
        jTextFecha.setText("");
    }
    
    public JTable getTablaProveedores(){
        return jTableProveedores;
    }

    public JTable getTablaHistorialPedidos(){
        return jTableHistorial;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Home;
    private javax.swing.JLabel Imagen5a;
    private javax.swing.JLabel Inventario;
    private javax.swing.JLabel Proveedores;
    private javax.swing.JLabel Sistemaventas;
    private javax.swing.JLabel cerrarsesion;
    private javax.swing.JButton jButtonActualizarHistorial;
    private javax.swing.JButton jButtonActualizarProveedor;
    private javax.swing.JButton jButtonAñadirHistorial;
    private javax.swing.JButton jButtonAñadirProveedor;
    private javax.swing.JButton jButtonBorrarHistorial;
    private javax.swing.JButton jButtonBorrarProveedor;
    private javax.swing.JComboBox<Banco> jComboBoxBanco;
    private javax.swing.JComboBox<String> jComboEstado;
    private javax.swing.JComboBox<String> jComboTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel14;
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
    // End of variables declaration//GEN-END:variables
}
