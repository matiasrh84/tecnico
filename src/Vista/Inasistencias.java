package Vista;

import Controlador.ConexionMySQL;
import Controlador.Escape;
import Controlador.Fecha;
import Controlador.TableHeder;
import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormatSymbols;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class Inasistencias extends javax.swing.JDialog {

    DefaultTableModel model, dt;
    DefaultTableCellRenderer alinearCentroN, alinearCentroA, alinearCentroP, alinearCentroD, alinearCentroM, alinearCentroL, alinearCentroC;
    public DecimalFormatSymbols sim = new DecimalFormatSymbols();
    Fecha fechas = new Fecha();

    public Inasistencias(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        Escape.funcionescape(this);
        cargarcbocarrera();
        cargarcbocursos();
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Character c = evt.getKeyChar();
                if (Character.isLetter(c)) {
                    evt.setKeyChar(Character.toUpperCase(c));
                }
            }
        });
    }

    public void cargartabla(String valor) {
        ConexionMySQL cc = new ConexionMySQL();
        Connection cn = cc.Conectar();
        String[] Titulo = {"Nº Orden", "Apellido", "Nombre", "Justificadas", "Injustificadas", "Suspensiones", "Total", "Estado", "idalumnos", "idperiodolectivo"};
        Object[] Registros = new Object[10];
        model = new DefaultTableModel(null, Titulo) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 7) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        String sql = "SELECT * FROM vista_inasistencias "
                + "WHERE Numero = '" + cbocursos.getSelectedItem() + "'" + " AND CONCAT (Apellido, ' ', Nombre) LIKE '%" + txtbuscar.getText() + "%' ORDER BY Norden ASC";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Registros[0] = rs.getObject(1);
                Registros[1] = rs.getObject(2);
                Registros[2] = rs.getObject(3);
                ///////////////////////////////
                Registros[3] = rs.getObject(4);
                Registros[4] = rs.getObject(5);
                Registros[5] = rs.getObject(6);
                Registros[6] = rs.getDouble(7);
                ///////////////////////////////
                Registros[7] = "PRESENTE";
                Registros[8] = rs.getInt(8);
                Registros[9] = rs.getInt(9);
                model.addRow(Registros);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        tablaAlumnos.setCellSelectionEnabled(true);

        tablaAlumnos.setModel(model);
        tablaAlumnos.setAutoCreateRowSorter(true);

        String[] datos = {"PRESENTE", "TARDE", "AUSENTE"};
        JComboBox cbotp = new JComboBox(datos);
        TableColumn tc = tablaAlumnos.getColumnModel().getColumn(7);
        TableCellEditor tce = new DefaultCellEditor(cbotp);
        tc.setCellEditor(tce);

        TableCellRenderer headerRenderer = new TableHeder(Color.white, Color.black);

        for (int i = 0; i < 10; i++) {
            tablaAlumnos.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            if (i == 0) {
                tablaAlumnos.getColumnModel().getColumn(i).setPreferredWidth(60);
                tablaAlumnos.getColumnModel().getColumn(i).setMaxWidth(60);
            }
            if (i >= 8 && i < 10) {
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
                tablaAlumnos.getColumnModel().getColumn(i).setCellRenderer(alinearCentroD);
            } else if (i == 4) {
                tablaAlumnos.getColumnModel().getColumn(i).setCellRenderer(alinearCentroP);
            } else if (i == 5) {
                tablaAlumnos.getColumnModel().getColumn(i).setCellRenderer(alinearCentroL);
                
            } else if (i == 6) {
                tablaAlumnos.getColumnModel().getColumn(i).setCellRenderer(alinearCentroM);
                tablaAlumnos.getColumnModel().getColumn(i).setPreferredWidth(50);
                tablaAlumnos.getColumnModel().getColumn(i).setMaxWidth(50);
            }
            else if (i == 7) {
                tablaAlumnos.getColumnModel().getColumn(i).setCellRenderer(alinearCentroC);
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
        jPanel3 = new javax.swing.JPanel();
        cboturno = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        cbolugar = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaAlumnos = new JTable(){
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return colIndex != 0 && colIndex != 1 && colIndex != 2;
            }
        };
        tablaAlumnos = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        dcfecha = new com.toedter.calendar.JDateChooser();
        btnaceptar = new javax.swing.JButton();
        btnaplicar = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        txtbuscar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Registrar Inasistencias"));

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
                .addComponent(cbocarreras, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(cbocursos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbocursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Turno"));

        cboturno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MAÑANA", "TARDE" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboturno, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboturno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Lugar"));

        cbolugar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AULA", "TALLER", "ED FISICA" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbolugar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbolugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        jScrollPane2.setViewportView(tablaAlumnos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Fecha"));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dcfecha, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dcfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnaceptar.setText("Aceptar");
        btnaceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaceptarActionPerformed(evt);
            }
        });

        btnaplicar.setText("Aplicar");
        btnaplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaplicarActionPerformed(evt);
            }
        });

        btnsalir.setText("Salir");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar"));

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtbuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(btnaceptar)
                            .addGap(18, 18, 18)
                            .addComponent(btnaplicar))
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 702, Short.MAX_VALUE)
                        .addComponent(btnsalir)
                        .addContainerGap())
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnaceptar)
                    .addComponent(btnaplicar)
                    .addComponent(btnsalir))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased

        cargartabla(txtbuscar.getText());

    }//GEN-LAST:event_txtbuscarKeyReleased

    private void cbocarrerasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbocarrerasActionPerformed

        cargarcbocursos();

    }//GEN-LAST:event_cbocarrerasActionPerformed

    private void cbocursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbocursosActionPerformed

        cargartabla("");
        txtbuscar.setText("");

    }//GEN-LAST:event_cbocursosActionPerformed

    private void btnaplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaplicarActionPerformed

        if (dcfecha.getDate() != null) {

            try {
                ConexionMySQL mysql = new ConexionMySQL();
                Connection cn = mysql.Conectar();
                Statement st = cn.createStatement();
                String sql = "SELECT Count(inasistencias.idAlumnos) FROM inasistencias "
                        + "INNER JOIN alumnos_estan_en_cursos ON inasistencias.idAlumnos = alumnos_estan_en_cursos.idAlumnos "
                        + "INNER JOIN cursos ON alumnos_estan_en_cursos.idCursos = cursos.idCursos "
                        + "WHERE inasistencias.Fecha = '" + fechas.getFecha(dcfecha) + "' "
                        + "AND inasistencias.Lugar = '" + cbolugar.getSelectedItem().toString() + "' "
                        + "AND inasistencias.Turno = '" + cboturno.getSelectedItem().toString() + "' "
                        + "AND cursos.Numero = '" + cbocursos.getSelectedItem().toString() + "'";
                ResultSet rs = st.executeQuery(sql);
                rs.next();
                if (rs.getInt(1) == 0) {
                    CallableStatement Cpst = cn.prepareCall("{ call altaInasistencias(?,?,?,?,?,?,?,?) }");
                    for (int i = 0; i < tablaAlumnos.getRowCount(); i++) {

                        if (!tablaAlumnos.getValueAt(i, 7).equals("PRESENTE")) {

                            Cpst.setString(1, fechas.getFecha(dcfecha));
                            Cpst.setString(2, cbolugar.getSelectedItem().toString());
                            Cpst.setString(3, tablaAlumnos.getValueAt(i, 7).toString());
                            Cpst.setString(4, cboturno.getSelectedItem().toString());
                            Cpst.setInt(5, Integer.valueOf(tablaAlumnos.getValueAt(i, 8).toString()));
                            Cpst.setString(6, "NO");
                            Cpst.setString(7, null);
                            Cpst.setInt(8, Integer.valueOf(tablaAlumnos.getValueAt(i, 9).toString()));

                            // ejecutar el SP
                            Cpst.execute();
                            // confirmar si se ejecuto sin errores
                            // cn.commit();
                        }

                    }
                    JOptionPane.showMessageDialog(null, "Los registros se cargaron exitosamente");
                    cargartabla("");
                    for (int i = 0; i < tablaAlumnos.getRowCount(); i++) 
                        {
                        if (Double.valueOf(tablaAlumnos.getValueAt(i, 5).toString()) >= 10) {
                    JOptionPane.showMessageDialog(null, "El alumno "+tablaAlumnos.getValueAt(i, 2)+" "+tablaAlumnos.getValueAt(i, 1)+" llegó al límite de faltas");
                }
                        }
                    
                    
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Ya se registraron inasistencias para este curso en el turno, lugar y fecha indicados");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistrarNotas.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Debe especificar una fecha");
            dcfecha.requestFocus();
        }
    }//GEN-LAST:event_btnaplicarActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed

        dispose();

    }//GEN-LAST:event_btnsalirActionPerformed

    private void btnaceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaceptarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnaceptarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnaceptar;
    private javax.swing.JButton btnaplicar;
    private javax.swing.JButton btnsalir;
    private javax.swing.JComboBox<String> cbocarreras;
    private javax.swing.JComboBox<String> cbocursos;
    private javax.swing.JComboBox<String> cbolugar;
    private javax.swing.JComboBox<String> cboturno;
    private com.toedter.calendar.JDateChooser dcfecha;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaAlumnos;
    private javax.swing.JTextField txtbuscar;
    // End of variables declaration//GEN-END:variables
}
