package vista;

import java.util.List;
import java.util.Date;
import java.text.ParseException;
import javax.swing.table.DefaultTableModel;
import controlador.DAOProducto;
import modelo.Producto;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import controlador.DAOCategoria;
import modelo.Categorias;
import modelo.FechaExpiracion;
import controlador.DAOFechaExpiracion;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.ToolTipManager;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author gabriel rivas
 */
public class JFrameInventario extends javax.swing.JFrame {

    public JFrameInventario() throws SQLException {
        initComponents();
        ObtenerProductos();
        ObtenerCategoria();
        ObtenerExpiracion();
        // Habilitar tooltips globalmente
        ToolTipManager.sharedInstance().setEnabled(true);

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
        try {
            llenarCombCategoria();

        } catch (SQLException ex) {
            Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void llenarCombCategoria() throws SQLException {

        List<Categorias> categorias = new DAOCategoria().ObtenerDatos();
        for (int i = 0; i < categorias.size(); i++) {

            jComboCategoria.addItem(new Categorias(categorias.
                    get(i).getId_categoria(),
                    categorias.get(i).getNombreCategoria()));
        }
    }

    public final void ObtenerProductos() throws SQLException {

        List<Producto> proodd = new DAOProducto().ObtenerProducto();

        DefaultTableModel modelo = new DefaultTableModel();
        String[] columnas = {"ID Producto", "ID Categoria", "Producto", "Precio", "Existencia", "Iva", "Precio compra", "Precio Descuento", "Descuento"};

        modelo.setColumnIdentifiers(columnas);
        for (Producto pr : proodd) {

            String[] renglon = {Integer.toString(pr.getId_producto()),
                Integer.toString(pr.getId_categoria()),
                pr.getNombreProducto(),
                Double.toString(pr.getPrecio()),
                Integer.toString(pr.getExistencia()),
                Double.toString(pr.getIva()),
                Double.toString(pr.getPreciocompra()),
                Double.toString(pr.getPreciodescuento()),
                Double.toString(pr.getDescuento())

            };
            modelo.addRow(renglon);
        }
        jTableInventario.setModel(modelo);

    }

    public final void ObtenerCategoria() throws SQLException {

        List<Categorias> catt = new DAOCategoria().ObtenerDatos();

        DefaultTableModel modelo = new DefaultTableModel();
        String[] columnas = {"ID Categoria", "Nombre"};

        modelo.setColumnIdentifiers(columnas);
        for (Categorias ct : catt) {

            String[] renglon = {Integer.toString(ct.getId_categoria()),
                ct.getNombreCategoria()

            };
            modelo.addRow(renglon);
        }
        jTableCategoria.setModel(modelo);

    }

    public final void ObtenerExpiracion() throws SQLException {

        List<FechaExpiracion> catt = new DAOFechaExpiracion().ObtenerExpiracion();

        DefaultTableModel modelo = new DefaultTableModel();
        String[] columnas = {"ID Expiracion", "ID Producto", " Producto ", " Año / Mes / Dia ", " Codigo Lote "};

        // Formateador de fecha para mostrar de manera legible
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");

        modelo.setColumnIdentifiers(columnas);
        for (FechaExpiracion ct : catt) {
            // Convertir la fecha a String
            String fechaFormateada = (ct.getFechaExpiracion() != null)
                    ? formatoFecha.format(ct.getFechaExpiracion())
                    : "Fecha no disponible";

            String[] renglon = {Integer.toString(ct.getId_expiracion()),
                Integer.toString(ct.getId_producto()),
                ct.getNombreP(),
                fechaFormateada,
                ct.getCodigoLote()

            };
            modelo.addRow(renglon);
        }
        jTableExpiracion.setModel(modelo);

    }

    private void buscarDatosProductos(String dato) throws SQLException {
        List<Producto> productos = new DAOProducto().busquedaProducto(dato);

        DefaultTableModel modelo = new DefaultTableModel();

        String[] columnas = {"ID Producto", "ID Categoria", "Producto", "Precio", "Existencia", "Iva", "Precio compra", "Precio Descuento", "Descuento"};

        modelo.setColumnIdentifiers(columnas);
        for (Producto pro : productos) {

            String[] renglon = {
                Integer.toString(pro.getId_producto()),
                Integer.toString(pro.getId_categoria()),
                pro.getNombreProducto(),
                Double.toString(pro.getPrecio()),
                Integer.toString(pro.getExistencia()),
                Double.toString(pro.getIva()),
                Double.toString(pro.getPreciocompra()),
                Double.toString(pro.getPreciodescuento()),
                Double.toString(pro.getDescuento())
            };
            modelo.addRow(renglon);
        }
        jTableInventario.setModel(modelo);
    }

    private void buscarDatosProductosVencidos(String dato) throws SQLException {
        List<FechaExpiracion> vencido = new DAOFechaExpiracion().busquedaProductoVencido(dato);

        DefaultTableModel modelo = new DefaultTableModel();

        String[] columnas = {"ID Expiracion", "ID Producto", "Producto", "Año / Mes / Dia", "Lote"};

        // Formateador de fecha para mostrar de manera legible
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");

        modelo.setColumnIdentifiers(columnas);
        for (FechaExpiracion pro : vencido) {

            String fechaFormateada = (pro.getFechaExpiracion() != null)
                    ? formatoFecha.format(pro.getFechaExpiracion())
                    : "Fecha no disponible";

            String[] renglon = {
                Integer.toString(pro.getId_expiracion()),
                Integer.toString(pro.getId_producto()),
                pro.getNombreP(),
                fechaFormateada,
                pro.getCodigoLote()
            };
            modelo.addRow(renglon);
        }
        jTableExpiracion.setModel(modelo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextNombreCat = new javax.swing.JTextField();
        jBAnadirCategoria = new javax.swing.JButton();
        jBActualizarCategoria = new javax.swing.JButton();
        jBEliminarCategoria = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCategoria = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jTextNombreProducto = new javax.swing.JTextField();
        jComboCategoria = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTextPrecio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextExistencia = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTPrecioCompra = new javax.swing.JTextField();
        jBAgregar = new javax.swing.JButton();
        jBActualizar = new javax.swing.JButton();
        jBorrar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTIDProducto = new javax.swing.JTextField();
        jTExpiracionF = new javax.swing.JTextField();
        jTCodigoL = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jBAñadirExpiracion = new javax.swing.JButton();
        jBActualizarExpiracion = new javax.swing.JButton();
        jBorrarExpiracion = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
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
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTextBuscar = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableInventario = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableExpiracion = new javax.swing.JTable();
        jTextBuscarVencido = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        TipExpiracion = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jPanel4.setBackground(new java.awt.Color(211, 217, 254));

        jPanel5.setBackground(new java.awt.Color(159, 175, 254));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 108, 240), 5));

        jLabel9.setText("Nombre");

        jBAnadirCategoria.setText("Añadir");
        jBAnadirCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAnadirCategoriaActionPerformed(evt);
            }
        });

        jBActualizarCategoria.setText("Actualizar");
        jBActualizarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBActualizarCategoriaActionPerformed(evt);
            }
        });

        jBEliminarCategoria.setText("Eliminar");
        jBEliminarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEliminarCategoriaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Registrar una nueva categoria");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jBAnadirCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jBActualizarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBEliminarCategoria))
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextNombreCat, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextNombreCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBAnadirCategoria)
                    .addComponent(jBEliminarCategoria)
                    .addComponent(jBActualizarCategoria))
                .addContainerGap(144, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setText("Categorias");

        jPanel7.setBackground(new java.awt.Color(159, 175, 254));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 108, 240), 5));

        jTableCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID Categoria", "Nombre"
            }
        ));
        jTableCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCategoriaMouseClicked(evt);
            }
        });
        jTableCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableCategoriaKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTableCategoria);
        if (jTableCategoria.getColumnModel().getColumnCount() > 0) {
            jTableCategoria.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(425, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(111, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(211, 217, 254));

        jTextNombreProducto.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextNombreProducto.setDisabledTextColor(new java.awt.Color(204, 204, 204));

        jComboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboCategoriaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Nombre del producto");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Precio");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Existencia");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Precio compra");

        jBAgregar.setText("Añadir");
        jBAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarActionPerformed(evt);
            }
        });

        jBActualizar.setText("Actualizar");
        jBActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBActualizarActionPerformed(evt);
            }
        });

        jBorrar.setText("Borrar");
        jBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBorrarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Registrar fecha de expiración");

        jTIDProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTIDProductoActionPerformed(evt);
            }
        });

        jTExpiracionF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTExpiracionFActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("ID Producto");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Fecha Vencimiento");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Codigo lote");

        jBAñadirExpiracion.setText("Añadir");
        jBAñadirExpiracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAñadirExpiracionActionPerformed(evt);
            }
        });

        jBActualizarExpiracion.setText("Actualizar");
        jBActualizarExpiracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBActualizarExpiracionActionPerformed(evt);
            }
        });
        jBActualizarExpiracion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jBActualizarExpiracionKeyReleased(evt);
            }
        });

        jBorrarExpiracion.setText("Borrar");
        jBorrarExpiracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBorrarExpiracionActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Registrar un nuevo producto");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Categoria");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel12.setText("Control de Inventario");

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

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Proveedores.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 50, 50));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/home.png"))); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Carrito.png"))); // NOI18N
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Inventario.png"))); // NOI18N
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Salida.png"))); // NOI18N
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/login.png"))); // NOI18N
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, -1, -1));

        jTextBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextBuscarActionPerformed(evt);
            }
        });
        jTextBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextBuscarKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Buscar");

        jButton2.setText("Añadir Categoria");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTableInventario.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableInventario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableInventarioKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableInventario);

        jTableExpiracion.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableExpiracion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableExpiracionKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTableExpiracion);

        jTextBuscarVencido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextBuscarVencidoKeyReleased(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Buscar");

        TipExpiracion.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        TipExpiracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/interrogacion.png"))); // NOI18N
        TipExpiracion.setToolTipText("Las Fechas se insertan de la siguiente manera Año-Mes-Dia con guiones");

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(131, 131, 131)
                                .addComponent(jLabel19))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jTextPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jBAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jTextBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jButton2))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(87, 87, 87)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jTIDProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(jTExpiracionF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTCodigoL, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jBorrarExpiracion, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBActualizarExpiracion, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBAñadirExpiracion, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextBuscarVencido, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TipExpiracion))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(46, 46, 46))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTIDProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTCodigoL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jBAñadirExpiracion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(0, 0, 0)
                                .addComponent(jTExpiracionF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jBActualizarExpiracion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBorrarExpiracion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextBuscarVencido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TipExpiracion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jLabel12)
                                .addGap(22, 22, 22)
                                .addComponent(jLabel8)
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1)
                                .addGap(38, 38, 38)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel19))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addComponent(jTextPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addComponent(jTextExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(jTPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jBAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jBActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jTextBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel24)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(335, 335, 335))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 929, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarActionPerformed

        int categoria;

        categoria = jComboCategoria.getItemAt(jComboCategoria.getSelectedIndex())
                .getId_categoria();

        String nom = jTextNombreProducto.getText();
        String precio = jTextPrecio.getText();
        String existen = jTextExistencia.getText();

        if (categoria == 0 || nom.equals("") || precio.equals("") || existen.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Todos los campos son obligatorios");
        } else {
            try {

                double precioo = Double.parseDouble(precio);
                int existenn = Integer.parseInt(existen);
                double precioc = Double.parseDouble(jTPrecioCompra.getText());

                Producto pro = new Producto(categoria, nom, precioo, existenn, precioc);
                DAOProducto dao = new DAOProducto();

                if (dao.Insertar(pro) == 0) {
                    // JOptionPane.showMessageDialog(rootPane, "Registro agregado");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error al agregar el registro");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(rootPane, "Verifique que los campos numéricos sean válidos o coloque un 0", "Error de formato", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(rootPane, "Error de base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            try {
                ObtenerProductos();
                limpiarCampos();
            } catch (SQLException ex) {
                Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_jBAgregarActionPerformed

    private List<Producto> ProductosModificados = new ArrayList<>();

    private void jBActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActualizarActionPerformed
        // Verificar si se han realizado cambios en los productos
        if (!ProductosModificados.isEmpty()) {
            try {
                DAOProducto dao = new DAOProducto();
                int resultadoExitoso = 0;
                int resultadoFallido = 0;

                // Iterar sobre los productos modificados
                for (Producto producto : ProductosModificados) {
                    int resultado = dao.Actualizar(producto);
                    if (resultado == 0) {
                        resultadoExitoso++;
                    } else {
                        resultadoFallido++;
                    }
                }

                // Mostrar mensaje de éxito o error
                if (resultadoExitoso > 0) {
                    JOptionPane.showMessageDialog(rootPane, resultadoExitoso + " productos actualizados correctamente.");
                }
                if (resultadoFallido > 0) {
                    JOptionPane.showMessageDialog(rootPane, resultadoFallido + " productos no pudieron actualizarse.");
                }

                // Limpiar la lista de productos modificados
                ProductosModificados.clear();

                // Actualizar la tabla
                ObtenerProductos();

            } catch (SQLException ex) {
                Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "No se han realizado cambios en los productos.");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jBActualizarActionPerformed

    private void jBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBorrarActionPerformed

        try {
            //OBTIENE EL INDICE DEW LA FILA SELECCIONADA
            int fila = jTableInventario.getSelectedRow();
            int id;
            // Verifica si se seleccionó una fila
            if (fila == -1) {
                JOptionPane.showMessageDialog(rootPane, "Seleccione un Producto para borrar");
                return;
            } else {
                // Obtiene el ID del autor seleccionado
                id = Integer.parseInt(jTableInventario.getValueAt(fila, 0).toString());
            }

            // Instancia la clase DAautor
            DAOProducto dao = new DAOProducto();

            // Llama al método Borrar y muestra un mensaje según el resultado
            if (dao.Borrar(id) == 0) {
                //JOptionPane.showMessageDialog(rootPane, "Producto borrado correctamente");
            } else {
                JOptionPane.showMessageDialog(rootPane, "Error al borrar Producto");
            }

            // Vuelve a cargar los datos en la tabla después de borrar
            ObtenerProductos();
            ObtenerExpiracion();

        } catch (SQLException ex) {
            Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jBorrarActionPerformed

    private void jComboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboCategoriaActionPerformed

    private void jTextBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextBuscarKeyReleased

        try {
            String datoprod = jTextBuscar.getText();

            buscarDatosProductos(datoprod);

        } catch (SQLException ex) {
            Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextBuscarKeyReleased

    private void jTextBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextBuscarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        jDialog1.setVisible(true);
        jDialog1.setSize(780, 400);
        jDialog1.setLocationRelativeTo(null);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jBAnadirCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAnadirCategoriaActionPerformed

        String nombre = jTextNombreCat.getText();

        if (nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "El nombre no debe estar vacio");
            return;
        }

        try {
            Categorias cat = new Categorias(nombre);
            DAOCategoria dao = new DAOCategoria();

            if (dao.Insertar(cat) == 0) {
                //   JOptionPane.showMessageDialog(rootPane, "Registro agregado");
            } else {
                JOptionPane.showMessageDialog(rootPane, "Error al agregar el registro");
            }

        } catch (SQLException ex) {
            Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Error de base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            ObtenerCategoria();
            limpiarCamposCategoria();

        } catch (SQLException ex) {
            Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:

    }//GEN-LAST:event_jBAnadirCategoriaActionPerformed

    // Lista para almacenar las categorías modificadas
    private List<Categorias> categoriasModificadas = new ArrayList<>();

    //================================================================================================//
    private void jBActualizarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActualizarCategoriaActionPerformed
        // Verificar si hay categorías modificadas
        if (!categoriasModificadas.isEmpty()) {
            try {
                DAOCategoria dao = new DAOCategoria();
                int resultadoExitoso = 0;
                int resultadoFallido = 0;

                // Iterar sobre las categorías modificadas
                for (Categorias categoria : categoriasModificadas) {
                    int resultado = dao.Actualizar(categoria);
                    if (resultado == 0) {
                        resultadoExitoso++;
                    } else {
                        resultadoFallido++;
                    }
                }

                if (resultadoExitoso > 0) {
                    JOptionPane.showMessageDialog(rootPane, resultadoExitoso + " categorías actualizadas correctamente");
                }
                if (resultadoFallido > 0) {
                    JOptionPane.showMessageDialog(rootPane, resultadoFallido + " categorías no pudieron actualizarse");
                }

                // Limpiar la lista de categorías modificadas
                categoriasModificadas.clear();

                // Actualizar la tabla
                ObtenerCategoria();
            } catch (SQLException ex) {
                Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "No se han realizado cambios en las categorías");
        }
    }//GEN-LAST:event_jBActualizarCategoriaActionPerformed
//=====================================================================================================================//

    private void jBEliminarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEliminarCategoriaActionPerformed

        try {
            //OBTIENE EL INDICE DEW LA FILA SELECCIONADA
            int fila = jTableCategoria.getSelectedRow();
            int id;
            // Verifica si se seleccionó una fila
            if (fila == -1) {
                JOptionPane.showMessageDialog(rootPane, "Seleccione una categoria para borrar");
                return;
            } else {
                // Obtiene el ID de la categoria seleccionado
                id = Integer.parseInt(jTableCategoria.getValueAt(fila, 0).toString());
            }

            // Instancia la clase DAautor
            DAOCategoria dao = new DAOCategoria();

            // Llama al método Borrar y muestra un mensaje según el resultado
            if (dao.Borrar(id) == 0) {
                // JOptionPane.showMessageDialog(rootPane, "Categoria borrado correctamente");
                ObtenerCategoria();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Error al borrar Producto");
            }

            // Vuelve a cargar los datos en la tabla después de borrar
            ObtenerCategoria();

        } catch (SQLException ex) {
            Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jBEliminarCategoriaActionPerformed

    // Variable global para almacenar la categoría seleccionada

    private void jTableCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCategoriaMouseClicked


    }//GEN-LAST:event_jTableCategoriaMouseClicked

    //===============================================================================================//
    private void jTableCategoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableCategoriaKeyReleased
        // Detectar si se ha editado una celda
        int filaSeleccionada = jTableCategoria.getSelectedRow();
        int columnaSeleccionada = jTableCategoria.getSelectedColumn();

        // Verificar que la fila seleccionada y la columna sean válidas
        if (filaSeleccionada != -1 && columnaSeleccionada == 1) { // Columna 1 es la columna "Nombre"
            String nuevoNombre = jTableCategoria.getValueAt(filaSeleccionada, columnaSeleccionada).toString();

            // Obtener el ID de la categoría
            String idCategoria = jTableCategoria.getValueAt(filaSeleccionada, 0).toString();  // Columna 0 es "ID Categoria"
            int id_categoria = Integer.parseInt(idCategoria);

            // Verificar si la categoría ya está en la lista de modificaciones
            boolean encontrada = false;
            for (Categorias categoria : categoriasModificadas) {
                if (categoria.getId_categoria() == id_categoria) {
                    categoria.setNombreCategoria(nuevoNombre); // Actualizar el nombre en la lista
                    encontrada = true;
                    break;
                }
            }

            // Si no estaba en la lista, agregarla
            if (!encontrada) {
                categoriasModificadas.add(new Categorias(id_categoria, nuevoNombre));
            }
        }


    }//GEN-LAST:event_jTableCategoriaKeyReleased

    private void jTIDProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTIDProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTIDProductoActionPerformed

    private void jTExpiracionFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTExpiracionFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTExpiracionFActionPerformed

    private void jBAñadirExpiracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAñadirExpiracionActionPerformed

        String id = jTIDProducto.getText();
        String fecha = jTExpiracionF.getText();
        String lote = jTCodigoL.getText();

        if (id.equals("") || fecha.equals("") || lote.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Todos los campos son obligatorios");
        } else {
            try {

                // Definir el formato de la fecha
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-mm-dd");

                int IDp = Integer.parseInt(id);
                Date Fecha = formato.parse(fecha);
                String Lote = lote;

                FechaExpiracion exp = new FechaExpiracion(IDp, Fecha, Lote);
                DAOFechaExpiracion dao = new DAOFechaExpiracion();

                if (dao.Insertar(exp) == 0) {
                    // JOptionPane.showMessageDialog(rootPane, "Registro agregado");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error al agregar el registro");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(rootPane, "Verifique que los campos numéricos sean válidos o coloque un 0", "Error de formato", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(rootPane, "Error de base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ObtenerExpiracion();

            } catch (SQLException ex) {
                Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jBAñadirExpiracionActionPerformed

    // Lista para almacenar las categorías modificadas
    private List<FechaExpiracion> ExpiracionModificadas = new ArrayList<>();

    private void jBActualizarExpiracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActualizarExpiracionActionPerformed
        // Verificar si se ha modificado alguna expiración
        if (!ExpiracionModificadas.isEmpty()) {
            try {
                DAOFechaExpiracion dao = new DAOFechaExpiracion();
                int resultadoExitoso = 0;
                int resultadoFallido = 0;

                // Iterar sobre las expiraciones modificadas
                for (FechaExpiracion expiracion : ExpiracionModificadas) {
                    int resultado = dao.Actualizar(expiracion);
                    if (resultado == 0) {
                        resultadoExitoso++;
                    } else {
                        resultadoFallido++;
                    }
                }

                // Mostrar mensaje de éxito o error
                if (resultadoExitoso > 0) {
                    JOptionPane.showMessageDialog(rootPane, resultadoExitoso + " expiraciones actualizadas correctamente.");
                }
                if (resultadoFallido > 0) {
                    JOptionPane.showMessageDialog(rootPane, resultadoFallido + " expiraciones no pudieron actualizarse.");
                }

                // Limpiar la lista de expiraciones modificadas
                ExpiracionModificadas.clear();

                // Actualizar la tabla
                ObtenerExpiracion();
            } catch (SQLException ex) {
                Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "No se han realizado cambios en las fechas de expiración.");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jBActualizarExpiracionActionPerformed


    private void jBActualizarExpiracionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBActualizarExpiracionKeyReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_jBActualizarExpiracionKeyReleased

    private void jTableExpiracionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableExpiracionKeyReleased
        int filaSeleccionada = jTableExpiracion.getSelectedRow();
        int columnaSeleccionada = jTableExpiracion.getSelectedColumn();
// Obtener valores de las celdas
        String idEXP = jTableExpiracion.getValueAt(filaSeleccionada, 0).toString();
        String IdProducto = jTableExpiracion.getValueAt(filaSeleccionada, 1).toString();
        String fechaexp = jTableExpiracion.getValueAt(filaSeleccionada, 3).toString(); // Suponiendo que esta columna es "Fecha"
        String codigo = jTableExpiracion.getValueAt(filaSeleccionada, 4).toString(); // Suponiendo que esta columna es "Código Lote"

        // Convertir ID de producto a entero
        int idProducto = Integer.parseInt(IdProducto);
        int id_exp = Integer.parseInt(idEXP);

        // Convertir la fecha de String a Date
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        Date Fecha = null;
        try {
            Fecha = formato.parse(fechaexp);
        } catch (ParseException ex) {
            Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Verificar si la expiración ya está en la lista de modificaciones
        boolean encontrada = false;
        for (FechaExpiracion exp : ExpiracionModificadas) {
            if (exp.getId_expiracion() == id_exp) {
                // Si ya está en la lista, actualizar los valores
                exp.setFechaExpiracion(Fecha);
                exp.setCodigoLote(codigo);
                exp.setId_producto(idProducto);
                encontrada = true;
                break;
            }
        }

        // Si no estaba en la lista, agregarla
        if (!encontrada) {
            ExpiracionModificadas.add(new FechaExpiracion(id_exp, idProducto, Fecha, codigo));
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTableExpiracionKeyReleased

    private void jBorrarExpiracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBorrarExpiracionActionPerformed

        try {
            //OBTIENE EL INDICE DEW LA FILA SELECCIONADA
            int fila = jTableExpiracion.getSelectedRow();
            int id;
            // Verifica si se seleccionó una fila
            if (fila == -1) {
                JOptionPane.showMessageDialog(rootPane, "Seleccione un Producto para borrar");
                return;
            } else {
                // Obtiene el ID del producto seleccionado
                id = Integer.parseInt(jTableExpiracion.getValueAt(fila, 0).toString());
            }

            // Instancia la clase DAautor
            DAOFechaExpiracion dao = new DAOFechaExpiracion();

            // Llama al método Borrar y muestra un mensaje según el resultado
            if (dao.Borrar(id) == 0) {
                // JOptionPane.showMessageDialog(rootPane, "Producto borrado correctamente");
                ObtenerExpiracion();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Error al borrar Producto");
                ObtenerExpiracion();
            }

            // Vuelve a cargar los datos en la tabla después de borrar
            ObtenerCategoria();

        } catch (SQLException ex) {
            Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jBorrarExpiracionActionPerformed

    //====>Aqui se llena la lista de objetos de productosmodificados<=========//
    private void jTableInventarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableInventarioKeyReleased
        int filaSeleccionada = jTableInventario.getSelectedRow();
        int columnaSeleccionada = jTableInventario.getSelectedColumn();

        // Obtener valores de las celdas
        String idProductoStr = jTableInventario.getValueAt(filaSeleccionada, 0).toString();
        String categoria = jTableInventario.getValueAt(filaSeleccionada, 1).toString();
        String nombreProducto = jTableInventario.getValueAt(filaSeleccionada, 2).toString();
        String precio = jTableInventario.getValueAt(filaSeleccionada, 3).toString();
        String existencia = jTableInventario.getValueAt(filaSeleccionada, 4).toString();
        String precioCompra = jTableInventario.getValueAt(filaSeleccionada, 6).toString();

        // Convertir ID de producto a entero
        int idProducto = Integer.parseInt(idProductoStr);
        double pre = Double.parseDouble(precio);
        double prec = Double.parseDouble(precioCompra);

        // Crear un objeto Producto con los nuevos valores
        Producto productoModificado = new Producto(idProducto, Integer.parseInt(categoria), nombreProducto,
                pre, Integer.parseInt(existencia), prec);

        // Verificar si el producto ya está en la lista de modificaciones
        boolean encontrada = false;
        for (Producto prod : ProductosModificados) {
            if (prod.getId_producto() == idProducto) {
                // Si ya está en la lista, actualizar los valores
                prod.setId_categoria(Integer.parseInt(categoria));
                prod.setNombreProducto(nombreProducto);
                prod.setPrecio(pre);
                prod.setExistencia(Integer.parseInt(existencia));
                prod.setPrecio(prec);
                encontrada = true;
                break;
            }
        }

        // Si no estaba en la lista, agregarlo
        if (!encontrada) {
            ProductosModificados.add(productoModificado);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTableInventarioKeyReleased

    private void jTextBuscarVencidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextBuscarVencidoKeyReleased

        try {
            String datoprod = jTextBuscarVencido.getText();

            buscarDatosProductosVencidos(datoprod);

        } catch (SQLException ex) {
            Logger.getLogger(JFrameVenta.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTextBuscarVencidoKeyReleased

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

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        try {
            JFrameProveedor _prov = new JFrameProveedor();
            _prov.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        JframeHome _home = new JframeHome(); // TODO add your handling code here:
        _home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

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

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        try {
            JFrameInventario _invent = new JFrameInventario();
            _invent.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        try {
            JFrameSalida _salida = new JFrameSalida();
            _salida.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(JFrameProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
 try {
        exportar(jTableInventario); // Asegúrate de usar el nombre de tu JTable
    } catch(IOException ex){
        System.out.println("Error: " + ex);
    }




        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed
//====================================================================================================//

    public void limpiarCampos() {

        jComboCategoria.setSelectedIndex(0);

        jTextNombreProducto.setText("");
        jTextPrecio.setText("");
        jTextExistencia.setText("");
    }

    public void limpiarCamposCategoria() {

        jTextNombreCat.setText("");

    }

    public void limpiarCamposExpiracion() {

        jTIDProducto.setText("");
        jTExpiracionF.setText("");
        jTCodigoL.setText("");
    }
    
    public void exportar(JTable t) throws IOException{
       
       JFileChooser chooser = new JFileChooser();
       FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo de excel", "xls");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("Guardar archivo");
            chooser.setAcceptAllFileFilterUsed(false);
            if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                String ruta = chooser.getSelectedFile().toString().concat(".xls");
                try{
                    File archivoXLS = new File(ruta);
                    if (archivoXLS.exists()){
                        archivoXLS.delete();
                    }
                    
                    archivoXLS.createNewFile();
                    Workbook libro = new HSSFWorkbook();
                    FileOutputStream archivo = new FileOutputStream(archivoXLS);
                    Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
                    hoja.setDisplayGridlines(false);
                    for(int f = 0; f < t.getRowCount(); f++){
                        Row fila = hoja.createRow(f);
                        for(int c=0; c < t.getColumnCount(); c++){
                            Cell celda = fila.createCell(c);
                            if(f==0){
                                celda.setCellValue(t.getColumnName(c));
                                
                            }
                        }
                    }
                    int filaInicio = 1;
                    for (int f = 0; f < t.getRowCount(); f++){
                        Row fila = hoja.createRow(filaInicio);
                        filaInicio++;
                        for(int c = 0; c < t.getColumnCount(); c++){
                            Cell celda = fila.createCell(c);
                            if (t.getValueAt(f, c) instanceof Double){
                                celda.setCellValue(Double.parseDouble(t.getValueAt(f, c).toString()));
                            }else if(t.getValueAt(f, c) instanceof Float) {
                                celda.setCellValue(Float.parseFloat((String) t.getValueAt(f, c)));
                            } else{
                                celda.setCellValue(String.valueOf(t.getValueAt(f, c)));
                            }
                        }
                    }
                    libro.write(archivo);
                    archivo.close();
                    Desktop.getDesktop().open(archivoXLS);
                } catch (IOException | NumberFormatException e){
                    throw e;
                }
                    
                
            }
      
       
       
   }
    
    // metodos get para obtener las tablas 
    public JTable getTablaInventario() {
    return jTableInventario;
}
    
    public JTable getTableExpiracion(){
        return jTableExpiracion;
    }
    
    public JTable getTableCategoria(){
        return jTableCategoria;
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Home;
    private javax.swing.JLabel Imagen5a;
    private javax.swing.JLabel Inventario;
    private javax.swing.JLabel Proveedores;
    private javax.swing.JLabel Sistemaventas;
    private javax.swing.JLabel TipExpiracion;
    private javax.swing.JLabel cerrarsesion;
    private javax.swing.JButton jBActualizar;
    private javax.swing.JButton jBActualizarCategoria;
    private javax.swing.JButton jBActualizarExpiracion;
    private javax.swing.JButton jBAgregar;
    private javax.swing.JButton jBAnadirCategoria;
    private javax.swing.JButton jBAñadirExpiracion;
    private javax.swing.JButton jBEliminarCategoria;
    private javax.swing.JButton jBorrar;
    private javax.swing.JButton jBorrarExpiracion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<Categorias> jComboCategoria;
    private javax.swing.JDialog jDialog1;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTCodigoL;
    private javax.swing.JTextField jTExpiracionF;
    private javax.swing.JTextField jTIDProducto;
    private javax.swing.JTextField jTPrecioCompra;
    private javax.swing.JTable jTableCategoria;
    private javax.swing.JTable jTableExpiracion;
    private javax.swing.JTable jTableInventario;
    private javax.swing.JTextField jTextBuscar;
    private javax.swing.JTextField jTextBuscarVencido;
    private javax.swing.JTextField jTextExistencia;
    private javax.swing.JTextField jTextNombreCat;
    private javax.swing.JTextField jTextNombreProducto;
    private javax.swing.JTextField jTextPrecio;
    private javax.swing.JLabel registrosSalida;
    // End of variables declaration//GEN-END:variables
}
