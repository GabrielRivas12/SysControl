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
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import modelo.Transacciones;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


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

        ObtenerProveedor();
        ObtenerHistoriales();
        ComboBancos();
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
        jButton1 = new javax.swing.JButton();

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 751, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(569, 569, 569)
                        .addComponent(jButton1)))
                .addContainerGap(154, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap(179, Short.MAX_VALUE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
            //imprimirFacturaProvicional();

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableHistorial;
    private javax.swing.JTable jTableProveedores;
    // End of variables declaration//GEN-END:variables
}
