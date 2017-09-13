/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.ConexionMySQL;
import Controlador.Escape;
import Controlador.VerticalTableHeaderCellRenderer;
import java.awt.Color;
import java.awt.Rectangle;
import com.sun.glass.events.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

public class Notas_Alumnos extends javax.swing.JDialog {

    DefaultTableModel model;
    DefaultTableCellRenderer alinearCentro, alinearDerecha, alinearIzquierda, alinearCentroV, alinearDerechaV, alinearIzquierdaV;
    public static int id_alumno = 0;
    public static String Periodo = "", curso = "", dni = "", legajo = "",orientacion="",orden="";

    public Notas_Alumnos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        Escape.funcionescape(this);
        cargarcbocarrera();
        cargarcboperiodo();
        cargartabla("");
        dobleclick();
    }

    void cargarcbocarrera() {
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        String sSQL = "SELECT Nombre FROM carreras";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                cbocarreras.addItem(rs.getString("Nombre"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos...");
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void cargarcbocursos() {
        cbocursos.removeAllItems();
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        String sSQL = "SELECT cursos.Numero FROM cursos INNER JOIN carreras ON cursos.idCarreras = carreras.idCarreras WHERE carreras.Nombre = '" + cbocarreras.getSelectedItem().toString() + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                cbocursos.addItem(rs.getString("cursos.Numero"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos...");
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void cargarcboperiodo() {
        cboperiodo.removeAllItems();
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        String sSQL = "SELECT * FROM periodolectivo";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                cboperiodo.addItem(rs.getString(2));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos...");
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void cargartabla(String valor) {
        ConexionMySQL cc = new ConexionMySQL();
        Connection cn = cc.Conectar();
        String[] Titulo = {"Legajo", "Dni", "Apellido", "Nombre", "idAlumnos", "idPeriodo", "idcurso", "carrera","Orden"};
        Object[] Registros = new Object[9];
        model = new DefaultTableModel(null, Titulo) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String sql = "Select * From notas_alumnos where CONCAT(numero, ' ', año,' ', carrera)"
                + "LIKE '%" + valor + "%'";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Registros[0] = rs.getString(1);
                Registros[1] = rs.getString(2);
                Registros[2] = rs.getString(3);
                Registros[3] = rs.getString(4);
                Registros[4] = rs.getString(5);
                Registros[5] = rs.getString(6);
                Registros[6] = rs.getString(7);
                Registros[7] = rs.getString(8);
                Registros[8] = rs.getString(9);
                model.addRow(Registros);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        tablanotas.setModel(model);
        tablanotas.setAutoCreateRowSorter(true);

        alinear();

        ///TableCellRenderer headerRenderer = new VerticalTableHeaderCellRenderer(Color.orange, Color.BLACK);

        /* tablanotas.getColumnModel().getColumn(0).setHeaderRenderer(headerRenderer);
        tablanotas.getColumnModel().getColumn(1).setHeaderRenderer(headerRenderer);
        tablanotas.getColumnModel().getColumn(2).setHeaderRenderer(headerRenderer);
        tablanotas.getColumnModel().getColumn(3).setHeaderRenderer(headerRenderer);
        tablanotas.getColumnModel().getColumn(4).setHeaderRenderer(headerRenderer);
        tablanotas.getColumnModel().getColumn(5).setHeaderRenderer(headerRenderer);*/
        tablanotas.getColumnModel().getColumn(0).setCellRenderer(alinearCentro);
        tablanotas.getColumnModel().getColumn(1).setCellRenderer(alinearCentro);
        tablanotas.getColumnModel().getColumn(2).setCellRenderer(alinearCentro);
        tablanotas.getColumnModel().getColumn(3).setCellRenderer(alinearCentro);

        tablanotas.getColumnModel().getColumn(2).setPreferredWidth(140);
        tablanotas.getColumnModel().getColumn(3).setPreferredWidth(140);
        tablanotas.getColumnModel().getColumn(2).setMinWidth(140);
        tablanotas.getColumnModel().getColumn(3).setMinWidth(140);
        //////////////////////////////////////////////////////////////////
        tablanotas.getColumnModel().getColumn(4).setMaxWidth(0);
        tablanotas.getColumnModel().getColumn(4).setMinWidth(0);
        tablanotas.getColumnModel().getColumn(4).setPreferredWidth(0);
        ////////////////////////////////////////////////////////////////
        tablanotas.getColumnModel().getColumn(5).setMaxWidth(0);
        tablanotas.getColumnModel().getColumn(5).setMinWidth(0);
        tablanotas.getColumnModel().getColumn(5).setPreferredWidth(0);
        ////////////////////////////////////////////////////////////////
        tablanotas.getColumnModel().getColumn(6).setMaxWidth(0);
        tablanotas.getColumnModel().getColumn(6).setMinWidth(0);
        tablanotas.getColumnModel().getColumn(6).setPreferredWidth(0);
        ////////////////////////////////////////////////////////////////
        tablanotas.getColumnModel().getColumn(7).setMaxWidth(0);
        tablanotas.getColumnModel().getColumn(7).setMinWidth(0);
        tablanotas.getColumnModel().getColumn(7).setPreferredWidth(0);
        ///////Ultima Fila///////
        Rectangle r = tablanotas.getCellRect(tablanotas.getRowCount() - 1, 0, true);
        tablanotas.scrollRectToVisible(r);
        tablanotas.getSelectionModel().setSelectionInterval(tablanotas.getRowCount() - 1, tablanotas.getRowCount() - 1);

        //////////////////////////
    }

    void alinear() {
        alinearCentro = new DefaultTableCellRenderer();
        alinearCentro.setHorizontalAlignment(SwingConstants.CENTER);
        alinearDerecha = new DefaultTableCellRenderer();
        alinearDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        alinearIzquierda = new DefaultTableCellRenderer();
        alinearIzquierda.setHorizontalAlignment(SwingConstants.LEFT);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cbocarreras = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        cbocursos = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        cboperiodo = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablanotas = new JTable(){
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return colIndex != 0 && colIndex != 1 && colIndex != 2 
                && colIndex != 6 && colIndex != 7 && colIndex != 8 
                && colIndex != 9 && colIndex != 10 && colIndex != 14 
                && colIndex != 15 && colIndex != 16 && colIndex != 17 
                && colIndex != 18 && colIndex != 19 && colIndex != 20 
                && colIndex != 23;
            }
        };
        txtbuscar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Carreras"));

        cbocarreras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbocarrerasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbocarreras, 0, 231, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbocarreras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cursos"));

        cbocursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbocursosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbocursos, 0, 108, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbocursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Periodo"));

        cboperiodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboperiodoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboperiodo, 0, 108, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboperiodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Alumnos"));

        tablanotas.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tablanotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablanotas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablanotas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablanotasKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablanotas);

        txtbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarActionPerformed(evt);
            }
        });
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 237, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("Ver Estado");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbocarrerasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbocarrerasActionPerformed
        if (cbocarreras.getItemCount() != 0) {
            cargartabla(cbocarreras.getSelectedItem().toString());
        }
        cargarcbocursos();
    }//GEN-LAST:event_cbocarrerasActionPerformed

    private void cbocursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbocursosActionPerformed
        if (cbocursos.getItemCount() != 0) {
            cargartabla(cbocursos.getSelectedItem().toString());
        }
    }//GEN-LAST:event_cbocursosActionPerformed

    private void cboperiodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboperiodoActionPerformed
        if (cboperiodo.getItemCount() != 0) {
            cargartabla(cboperiodo.getSelectedItem().toString());
        }
    }//GEN-LAST:event_cboperiodoActionPerformed

    void dobleclick() {
        tablanotas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    id_alumno = Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 4).toString());
                    Periodo = tablanotas.getValueAt(tablanotas.getSelectedRow(), 5).toString();
                    curso = tablanotas.getValueAt(tablanotas.getSelectedRow(), 6).toString();
                    legajo = tablanotas.getValueAt(tablanotas.getSelectedRow(), 0).toString();
                    dni = tablanotas.getValueAt(tablanotas.getSelectedRow(), 1).toString();
                    orientacion=cbocarreras.getSelectedItem().toString();
                    orden=tablanotas.getValueAt(tablanotas.getSelectedRow(), 8).toString();
                    dispose();
                    new Impresion_Libretas(null, true).setVisible(true);
                    
                }
            }
        
        });
        
    }

    private void tablanotasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablanotasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            id_alumno = Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 4).toString());
            Periodo = tablanotas.getValueAt(tablanotas.getSelectedRow(), 5).toString();
            curso = tablanotas.getValueAt(tablanotas.getSelectedRow(), 6).toString();
            legajo = tablanotas.getValueAt(tablanotas.getSelectedRow(), 0).toString();
            dni = tablanotas.getValueAt(tablanotas.getSelectedRow(), 1).toString();
            new Impresion_Libretas(null, true).setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_tablanotasKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        id_alumno = Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 4).toString());
        Periodo = tablanotas.getValueAt(tablanotas.getSelectedRow(), 5).toString();
        curso = tablanotas.getValueAt(tablanotas.getSelectedRow(), 6).toString();
        legajo = tablanotas.getValueAt(tablanotas.getSelectedRow(), 0).toString();
        dni = tablanotas.getValueAt(tablanotas.getSelectedRow(), 1).toString();
        new Impresion_Libretas(null, true).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscarActionPerformed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        TableRowSorter sorter = new TableRowSorter(model);
        sorter.setRowFilter(RowFilter.regexFilter(".*" + txtbuscar.getText() + ".*"));
        tablanotas.setRowSorter(sorter);        
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
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
            java.util.logging.Logger.getLogger(Notas_Alumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Notas_Alumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Notas_Alumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Notas_Alumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Notas_Alumnos dialog = new Notas_Alumnos(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbocarreras;
    private javax.swing.JComboBox<String> cbocursos;
    private javax.swing.JComboBox<String> cboperiodo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablanotas;
    private javax.swing.JTextField txtbuscar;
    // End of variables declaration//GEN-END:variables
}
