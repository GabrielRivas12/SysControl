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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import modelo.Transacciones;


/**
 *
 * @author Gabriel Rivas
 */
public class JFrameProveedor extends javax.swing.JFrame {

    /**
     * Creates new form JFrameProveedor
     */
    public JFrameProveedor() throws SQLException {
        initComponents();
        
        ObtenerProveedor();
        ObtenerHistoriales();
        ComboBancos();
        botonImprimir();
       
     

      
     
    }
    
    private void ComboBancos() throws SQLException{
     DAOBanco daobancos = new DAOBanco();
      List<Banco> listarBanco = daobancos.ObtenerBancos();
       
      JComboBox<Banco> comboBoxbanco = new JComboBox<>();
      for (Banco bancos : listarBanco)
          comboBoxbanco.addItem(bancos);

      jTableProveedores.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comboBoxbanco));
      
     Banco AgregarNuevo = new Banco (0,"Agregar Nuevo");comboBoxbanco.addItem(AgregarNuevo);
     
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
    
    private void botonImprimir(){
       // Definir el renderizador para mostrar el botón en la celda
    TableCellRenderer renderizador = new TableCellRenderer() {
        private final JButton boton = new JButton("Imprimir");

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return boton;
        }
    };

    // Definir el editor de celda para permitir la interacción con el botón
    TableCellEditor editor = new DefaultCellEditor(new JCheckBox()) {
        private final JButton boton = new JButton("Imprimir");

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return boton;
        }
    };

    // Asignar el renderizador y el editor a la columna 8
    jTableHistorial.getColumnModel().getColumn(7).setCellRenderer(renderizador);
    jTableHistorial.getColumnModel().getColumn(7).setCellEditor(editor);    }
        
    
    
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
                Integer.toString(pr.getNumero_cuenta()),



            };
            modelo.addRow(renglon);
        }
        jTableProveedores.setModel(modelo);

    }
      
      private void ObtenerHistoriales() throws SQLException {

        List<Transacciones> Transs = new DAOTransaccion().ObtenerHistorial();

        DefaultTableModel modelo = new DefaultTableModel();
        String[] columnas = {"ID", "ID Proveedor","ID Producto", "Cantidad", "Monto", "Fecha", "Estado", "Imprimir"};

         // Formateador de fecha para mostrar de manera legible
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
        
        modelo.setColumnIdentifiers(columnas);
        for (Transacciones pr : Transs) {
            
            String fechaFormateada = (pr.getFecha()!= null)
                    ? formatoFecha.format(pr.getFecha())
                    : "Fecha no disponible";

            String[] renglon = {
                Integer.toString(pr.getId_transacion()),
                Integer.toString(pr.getId_proveedor()),
                Integer.toString(pr.getId_producto()),
                Integer.toString(pr.getCantidad()),
                Double.toString(pr.getMonto()),  
                fechaFormateada,
                pr.getEstado(),



            };
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 751, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableHistorial;
    private javax.swing.JTable jTableProveedores;
    // End of variables declaration//GEN-END:variables
}
