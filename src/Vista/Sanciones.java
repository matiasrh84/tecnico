
package Vista;

import Controlador.ConexionMySQL;
import Controlador.Escape;
import Controlador.Fecha;
import Controlador.TableHeder;
import static Vista.RegistrarSancion.fecha;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormatSymbols;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class Sanciones extends javax.swing.JDialog {

     DefaultTableModel model, dt;
    DefaultTableCellRenderer alinearCentroN, alinearCentroA, alinearCentroP, alinearCentroD, alinearCentroM, alinearCentroL, alinearCentroC;
    public DecimalFormatSymbols sim = new DecimalFormatSymbols();
    Fecha fechas = new Fecha();
    
    public Sanciones(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        Escape.funcionescape(this);
        dobleclick();
        cargarcbocarrera();
        
    }
    
    void dobleclick() {
        tablaAlumnos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            new RegistrarSancion(null, true).setVisible(true);
            if(RegistrarSancion.banderaAceptar==1)
                {
                try {
                ConexionMySQL mysql = new ConexionMySQL();
                Connection cn = mysql.Conectar();
                
               
                    CallableStatement Cpst = cn.prepareCall("{ call altaSanciones(?,?,?,?,?,?) }");
                    

                        

                            Cpst.setString(1, RegistrarSancion.denunciante);
                            Cpst.setString(2, RegistrarSancion.motivo);
                            Cpst.setInt(3, RegistrarSancion.cantidad);
                            Cpst.setString(4, RegistrarSancion.fecha);
                            Cpst.setInt(5, Integer.valueOf(tablaAlumnos.getValueAt(tablaAlumnos.getSelectedRow(), 7).toString()));
                            Cpst.setInt(6, Integer.valueOf(tablaAlumnos.getValueAt(tablaAlumnos.getSelectedRow(), 8).toString()));
                            

                            // ejecutar el SP
                            Cpst.execute();
                            // confirmar si se ejecuto sin errores
                            // cn.commit();
                        

                    
                    JOptionPane.showMessageDialog(null, "Los registros se cargaron exitosamente");
                    cargartablaAlumnos("");
                    for (int i = 0; i < tablaAlumnos.getRowCount(); i++) 
                        {
                        if (Double.valueOf(tablaAlumnos.getValueAt(i, 5).toString()) >= 10) {
                    JOptionPane.showMessageDialog(null, "El alumno "+tablaAlumnos.getValueAt(i, 2)+" "+tablaAlumnos.getValueAt(i, 1)+" llegó al límite de faltas");
                }
                        }
                    
                    
                    
                
            } catch (SQLException ex) {
                Logger.getLogger(RegistrarNotas.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
            }
        });
    }
    
        public void cargartablaAlumnos(String valor) {
        ConexionMySQL cc = new ConexionMySQL();
        Connection cn = cc.Conectar();
        String[] Titulo = {"Nº Orden", "Apellido", "Nombre", "Justificadas", "Injustificadas", "Suspensiones", "Total", "idalumnos", "idperiodolectivo"};
        Object[] Registros = new Object[9];
        model = new DefaultTableModel(null, Titulo) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;

            }
        };

        String sql = "SELECT * FROM vista_faltas_suspensiones "
                + "WHERE Numero = '" + cbocursos.getSelectedItem() + "'" + " AND CONCAT (Apellido, ' ', Nombre) LIKE '%" + txtbuscar.getText() + "%' ORDER BY Norden ASC";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Registros[0] = rs.getObject(1);
                Registros[1] = rs.getObject(2);
                Registros[2] = rs.getObject(3);
                Registros[3] = rs.getObject(4);
                Registros[4] = rs.getObject(5);
                Registros[5] = rs.getObject(6);
                Registros[6] = rs.getDouble(7);
                Registros[7] = rs.getInt(8);
                Registros[8] = rs.getInt(9);
                model.addRow(Registros);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        //tablaAlumnos.setCellSelectionEnabled(true); //Sirve para que se seleccione la celda
        tablaAlumnos.setModel(model);
        tablaAlumnos.setAutoCreateRowSorter(true);

        TableCellRenderer headerRenderer = new TableHeder(Color.white, Color.black);

        for (int i = 0; i < 9; i++) {
            tablaAlumnos.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            if (i == 0) {
                tablaAlumnos.getColumnModel().getColumn(i).setPreferredWidth(60);
                tablaAlumnos.getColumnModel().getColumn(i).setMaxWidth(60);
            }
            if (i >= 7 && i < 9) {
                tablaAlumnos.getColumnModel().getColumn(i).setMaxWidth(0);
                tablaAlumnos.getColumnModel().getColumn(i).setMinWidth(0);
                tablaAlumnos.getColumnModel().getColumn(i).setPreferredWidth(0);
                tablaAlumnos.getTableHeader().getColumnModel().getColumn(i).setMaxWidth(0);
            }

            if (i < 1) {
                tablaAlumnos.getColumnModel().getColumn(i).setCellRenderer(alinearCentroA);
            } else if (i == 2 || i == 1) {
                tablaAlumnos.getColumnModel().getColumn(i).setCellRenderer(alinearCentroN);
                tablaAlumnos.getColumnModel().getColumn(i).setPreferredWidth(120);
                tablaAlumnos.getColumnModel().getColumn(i).setMinWidth(120);
            } else if (i == 3) {
                tablaAlumnos.getColumnModel().getColumn(i).setPreferredWidth(80);
                tablaAlumnos.getColumnModel().getColumn(i).setMinWidth(80);
                tablaAlumnos.getColumnModel().getColumn(i).setMaxWidth(80);
                tablaAlumnos.getColumnModel().getColumn(i).setCellRenderer(alinearCentroD);
            } else if (i == 4) {
                tablaAlumnos.getColumnModel().getColumn(i).setPreferredWidth(80);
                tablaAlumnos.getColumnModel().getColumn(i).setMinWidth(80);
                tablaAlumnos.getColumnModel().getColumn(i).setMaxWidth(80);
                tablaAlumnos.getColumnModel().getColumn(i).setCellRenderer(alinearCentroP);
            } else if (i == 5) {
                tablaAlumnos.getColumnModel().getColumn(i).setPreferredWidth(85);
                tablaAlumnos.getColumnModel().getColumn(i).setMinWidth(85);
                tablaAlumnos.getColumnModel().getColumn(i).setMaxWidth(85);
                tablaAlumnos.getColumnModel().getColumn(i).setCellRenderer(alinearCentroL);
            }else if (i == 6) {
                tablaAlumnos.getColumnModel().getColumn(i).setPreferredWidth(50);
                tablaAlumnos.getColumnModel().getColumn(i).setMinWidth(50);
                tablaAlumnos.getColumnModel().getColumn(i).setMaxWidth(50);
                tablaAlumnos.getColumnModel().getColumn(i).setCellRenderer(alinearCentroM);
            }
        }

        alinear();
    }

    void alinear() {
        alinearCentroN = new DefaultTableCellRenderer();
        alinearCentroN.setHorizontalAlignment(SwingConstants.CENTER);
        alinearCentroN.setBackground(Color.decode("#FFFACD"));
        alinearCentroN.setForeground(Color.black);

        alinearCentroA = new DefaultTableCellRenderer();
        alinearCentroA.setHorizontalAlignment(SwingConstants.CENTER);
        alinearCentroA.setBackground(Color.decode("#3A5FCD"));
        alinearCentroA.setForeground(Color.white);

        alinearCentroP = new DefaultTableCellRenderer();
        alinearCentroP.setHorizontalAlignment(SwingConstants.CENTER);
        alinearCentroP.setBackground(Color.decode("#C1FFC1"));
        alinearCentroP.setForeground(Color.black);

        alinearCentroD = new DefaultTableCellRenderer();
        alinearCentroD.setHorizontalAlignment(SwingConstants.CENTER);
        alinearCentroD.setBackground(Color.decode("#FFA54F"));
        alinearCentroD.setForeground(Color.black);

        alinearCentroM = new DefaultTableCellRenderer();
        alinearCentroM.setHorizontalAlignment(SwingConstants.CENTER);
        alinearCentroM.setBackground(Color.decode("#FFB5C5"));
        alinearCentroM.setForeground(Color.black);

        alinearCentroL = new DefaultTableCellRenderer();
        alinearCentroL.setHorizontalAlignment(SwingConstants.CENTER);
        alinearCentroL.setBackground(Color.decode("#8968CD"));
        alinearCentroL.setForeground(Color.white);

        alinearCentroC = new DefaultTableCellRenderer();
        alinearCentroC.setHorizontalAlignment(SwingConstants.CENTER);
        alinearCentroC.setBackground(Color.decode("#BFEFFF"));
        alinearCentroC.setForeground(Color.black);

        /*
        alinearDerecha = new DefaultTableCellRenderer();
        alinearDerecha.setHorizontalAlignment(SwingConstants.RIGHT);

        alinearIzquierda = new DefaultTableCellRenderer();
        alinearIzquierda.setHorizontalAlignment(SwingConstants.LEFT);
         */
    }
    
    void cargarcbocarrera() {
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        String sSQL = "SELECT Nombre FROM carreras  WHERE Estado = 'ACTIVA'";
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
        String sSQL = "SELECT * FROM vista_cursos WHERE carrera = '" + cbocarreras.getSelectedItem().toString() + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                cbocursos.addItem(rs.getString("Numero"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos...");
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cbocarreras = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        cbocursos = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaAlumnos = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        txtbuscar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Registrar Sanciones"));

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
                .addComponent(cbocarreras, 0, 224, Short.MAX_VALUE)
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
                .addComponent(cbocursos, 0, 95, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbocursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Alumnos"));

        tablaAlumnos.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tablaAlumnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaAlumnos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setText("Aceptar");

        jButton2.setText("Aplicar");

        jButton3.setText("Cancelar");

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar"));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtbuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbocarrerasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbocarrerasActionPerformed

        cargarcbocursos();
        cargartablaAlumnos("");
        
    }//GEN-LAST:event_cbocarrerasActionPerformed

    private void cbocursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbocursosActionPerformed
        
         cargartablaAlumnos("");
        txtbuscar.setText("");
        
    }//GEN-LAST:event_cbocursosActionPerformed

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbocarreras;
    private javax.swing.JComboBox<String> cbocursos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaAlumnos;
    private javax.swing.JTextField txtbuscar;
    // End of variables declaration//GEN-END:variables
}
