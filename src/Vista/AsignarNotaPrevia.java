/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.ConexionMySQL;
import Controlador.EnterarFecha;
import Controlador.Escape;
import Controlador.Fecha;
import Controlador.VerticalTableHeaderCellRenderer;
import Controlador.isNumeric;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author PC1
 */
public class AsignarNotaPrevia extends javax.swing.JDialog {

    DefaultTableModel model, dt;
    DefaultTableCellRenderer alinearCentroC, alinearCentroN, alinearCentroA, alinearCentroP, alinearCentroD, alinearCentroM, alinearCentroL;
    public DecimalFormatSymbols sim = new DecimalFormatSymbols();
    Fecha fechas = new Fecha();
    TableModelListener tml;

    public AsignarNotaPrevia(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setResizable(false);
        setLocationRelativeTo(null);
        Escape.funcionescape(this);
        cargarcbocarrera();
        cargartabla("");
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Character c = evt.getKeyChar();
                if (Character.isLetter(c)) {
                    evt.setKeyChar(Character.toUpperCase(c));
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txtbuscar = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        cbocarreras = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablanotas = new JTable(){
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return colIndex != 0 && colIndex != 1 && colIndex != 2
                && colIndex != 3 && colIndex != 10 && colIndex != 11;
            }
        };
        jPanel6 = new javax.swing.JPanel();
        cbocursos = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        cbomaterias = new javax.swing.JComboBox<>();
        btnasigfeclibfol = new javax.swing.JButton();
        btnaceptar = new javax.swing.JButton();
        btnaplicar = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Asignar"));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar"));

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtbuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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
                .addComponent(cbocarreras, 0, 240, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbocarreras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                .addContainerGap())
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
                .addComponent(cbocursos, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbocursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Materia"));

        cbomaterias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbomateriasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbomaterias, 0, 342, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbomaterias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnasigfeclibfol.setText("Asignar Fecha Libro y Folio");
        btnasigfeclibfol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnasigfeclibfolActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnasigfeclibfol)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnaceptar)
                        .addGap(18, 18, 18)
                        .addComponent(btnaplicar)
                        .addGap(18, 18, 18)
                        .addComponent(btnsalir)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnaceptar)
                    .addComponent(btnaplicar)
                    .addComponent(btnsalir)
                    .addComponent(btnasigfeclibfol))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    void cargarcbomaterias() {
        cbomaterias.removeAllItems();
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        String sSQL = "SELECT * FROM vista_materias WHERE Carrera = '" + cbocarreras.getSelectedItem().toString() + "' AND Curso = '" + cbocursos.getSelectedItem().toString() + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                cbomaterias.addItem(rs.getString("Materia"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos...");
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void cargartabla(String valor) {
        ConexionMySQL cc = new ConexionMySQL();
        Connection cn = cc.Conectar();
        String[] Titulo = {"Nº Orden", "Apellido", "Nombre", "Anual", "Rec. TP Previa 1", "Previa 1", "Rec. TP Previa 2", "Previa 2", "Rec. TP Previa 3", "Previa 3", "Calif. Def.", "Fecha Aprob.", "Libro", "Folio", "idAlumnos", "idCursos", "idAsignaturas", "idPeriodoLectivo", "Numero", "Materia", "RecuperaTp"};
        Object[] Registros = new Object[21];
        model = new DefaultTableModel(null, Titulo) {
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        String sql = "SELECT * FROM vista_previas "
                + "WHERE Materia = '" + cbomaterias.getSelectedItem() + "' "
                + "AND Numero = '" + cbocursos.getSelectedItem() + "'" + " AND CONCAT (Apellido, ' ', Nombre) LIKE '%" + txtbuscar.getText() + "%' ORDER BY Norden ASC";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            DecimalFormat f = new DecimalFormat("##.##", sim);
            while (rs.next()) {
                Registros[0] = rs.getObject(1);
                Registros[1] = rs.getObject(2);
                Registros[2] = rs.getObject(3);

                if (rs.getObject(4) != null) {
                    Registros[3] = f.format(rs.getObject(4));
                } else {
                    Registros[3] = rs.getObject(6);
                }

                Registros[4] = rs.getObject(5);

                if (rs.getObject(6) != null) {
                    Registros[5] = f.format(rs.getObject(6));
                } else {
                    Registros[5] = rs.getObject(6);
                }
                if (rs.getObject(7) != null) {
                    Registros[6] = f.format(rs.getObject(7));
                } else {
                    Registros[6] = rs.getObject(6);
                }
                Registros[7] = rs.getObject(8);
                Registros[8] = rs.getObject(9);
                Registros[9] = rs.getObject(10);
                Registros[10] = rs.getObject(11);

                if (rs.getObject(14) != null) {
                    Registros[11] = fechas.dateastring(rs.getDate(12));
                } else {
                    Registros[11] = rs.getObject(12);
                }

                Registros[12] = rs.getObject(13);
                if (rs.getObject(14) != null) {
                    Registros[13] = f.format(rs.getObject(14));
                } else {
                    Registros[13] = rs.getObject(14);
                }

                if (rs.getObject(15) != null) {
                    Registros[14] = f.format(rs.getObject(15));
                } else {
                    Registros[14] = rs.getObject(15);
                }
                Registros[15] = rs.getObject(16);
                Registros[16] = rs.getObject(17);
                Registros[17] = rs.getObject(18);
                Registros[18] = rs.getObject(19);
                Registros[19] = rs.getObject(20);
                Registros[20] = rs.getObject(21);

                model.addRow(Registros);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        tablanotas.setCellSelectionEnabled(true);

        tablanotas.setModel(model);
        tablanotas.setAutoCreateRowSorter(true);

        String[] datos = {"APROBADO", "DESAPROBADO"};
        JComboBox cborectp1 = new JComboBox(datos);
        JComboBox cborectp2 = new JComboBox(datos);
        JComboBox cborectp3 = new JComboBox(datos);
        TableColumn tc1 = tablanotas.getColumnModel().getColumn(4);
        TableColumn tc2 = tablanotas.getColumnModel().getColumn(6);
        TableColumn tc3 = tablanotas.getColumnModel().getColumn(8);
        TableCellEditor tce1 = new DefaultCellEditor(cborectp1);
        TableCellEditor tce2 = new DefaultCellEditor(cborectp2);
        TableCellEditor tce3 = new DefaultCellEditor(cborectp3);
        tc1.setCellEditor(tce1);
        tc2.setCellEditor(tce2);
        tc3.setCellEditor(tce3);

        TableCellRenderer headerRenderer = new VerticalTableHeaderCellRenderer(Color.white, Color.black);

        for (int i = 0; i < 21; i++) {
            if (i == 0) {
                tablanotas.getColumnModel().getColumn(0).setPreferredWidth(35);
            }

            if (i >= 14 && i < 21) {
                tablanotas.getColumnModel().getColumn(i).setMaxWidth(0);
                tablanotas.getColumnModel().getColumn(i).setMinWidth(0);
                tablanotas.getColumnModel().getColumn(i).setPreferredWidth(0);
                tablanotas.getTableHeader().getColumnModel().getColumn(i).setMaxWidth(0);
            }
            tablanotas.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);

            if (i <= 2) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroA);
            } else if (i == 3 || i == 5 || i == 7 || i == 9) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroN);
            } else if (i == 4 || i == 6 || i == 8) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroP);
                tablanotas.getColumnModel().getColumn(i).setPreferredWidth(100);
                tablanotas.getColumnModel().getColumn(i).setMinWidth(100);
            } else if (i == 10) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroC);
            } else if (i > 10 && i <= 13) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroL);
            }
            if (i == 11) {
                tablanotas.getColumnModel().getColumn(i).setPreferredWidth(70);
                tablanotas.getColumnModel().getColumn(i).setMinWidth(70);
            }
            if (i >= 3 && i != 11 && i < 19 && i != 4 && i != 6 && i != 8) {
                tablanotas.getColumnModel().getColumn(i).setPreferredWidth(35);
                tablanotas.getColumnModel().getColumn(i).setMinWidth(35);
            }
            if (i >= 1 && i < 3) {
                tablanotas.getColumnModel().getColumn(i).setPreferredWidth(140);
                tablanotas.getColumnModel().getColumn(i).setMinWidth(140);
            }
        }
        tablanotas.getModel().addTableModelListener(new TableModelListener(){

      public void tableChanged(TableModelEvent e) {
         System.out.println(e);
      }

           
    });
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

        alinearCentroL = new DefaultTableCellRenderer();
        alinearCentroL.setHorizontalAlignment(SwingConstants.CENTER);
        alinearCentroL.setBackground(Color.decode("#8968CD"));
        alinearCentroL.setForeground(Color.white);

        alinearCentroC = new DefaultTableCellRenderer();
        alinearCentroC.setHorizontalAlignment(SwingConstants.CENTER);
        alinearCentroC.setBackground(Color.decode("#BFEFFF"));
        alinearCentroC.setForeground(Color.black);
    }


    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased

        cargartabla(txtbuscar.getText());
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void cbocarrerasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbocarrerasActionPerformed

        cargarcbocursos();
        cargarcbomaterias();
    }//GEN-LAST:event_cbocarrerasActionPerformed

    private void tablanotasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablanotasKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            DecimalFormat f = new DecimalFormat("##.##", sim);
            isNumeric num = new isNumeric();
            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 4) != null) {
                if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 4) == "DESAPROBADO") {
                    tablanotas.setValueAt(null, tablanotas.getSelectedRow(), 5);
                }
            }

            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 6) != null) {
                if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 6) == "DESAPROBADO") {
                    tablanotas.setValueAt(null, tablanotas.getSelectedRow(), 5);
                    tablanotas.setValueAt(null, tablanotas.getSelectedRow(), 7);
                }
            }

            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 8) != null) {
                if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 8) == "DESAPROBADO") {
                    tablanotas.setValueAt(null, tablanotas.getSelectedRow(), 5);
                    tablanotas.setValueAt(null, tablanotas.getSelectedRow(), 7);
                    tablanotas.setValueAt(null, tablanotas.getSelectedRow(), 9);
                    tablanotas.setValueAt(null, tablanotas.getSelectedRow(), 10);
                }
            }

            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 5) != null) {
                if (Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 5).toString()) >= 4) {
                    if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 20) != "DESAPROBADO" || tablanotas.getValueAt(tablanotas.getSelectedRow(), 4) == "APROBADO") {
                        
                     double cdefinitiva, anual;
                     int previa;
                     anual = Double.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 3).toString());
                     previa = Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 5).toString());
                        cdefinitiva = Math.round(((anual + previa) / 2) * 100);
                        cdefinitiva = cdefinitiva/100;
                        tablanotas.setValueAt(cdefinitiva, tablanotas.getSelectedRow(), 10);
                    } else
                        {
                        tablanotas.setValueAt(null, tablanotas.getSelectedRow(), 5);
                        }
                }
            }
            
            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 7) != null) {
                if (Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 7).toString()) >= 4) {
                    if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 4) != "DESAPROBADO" || tablanotas.getValueAt(tablanotas.getSelectedRow(), 6) == "APROBADO") {
                        
                     double cdefinitiva, anual;
                     int previa;
                     anual = Double.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 3).toString());
                     previa = Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 7).toString());
                        cdefinitiva = Math.round(((anual + previa) / 2) * 100);
                        cdefinitiva = cdefinitiva/100;
                        tablanotas.setValueAt(cdefinitiva, tablanotas.getSelectedRow(), 10);
                    } else
                        {
                        tablanotas.setValueAt(null, tablanotas.getSelectedRow(), 7);
                        }
                }
            }
            
            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 9) != null) {
                if (Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 9).toString()) >= 4) {
                    if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 6) != "DESAPROBADO" || tablanotas.getValueAt(tablanotas.getSelectedRow(), 8) == "APROBADO") {
                        
                     double cdefinitiva, anual;
                     int previa;
                     anual = Double.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 3).toString());
                     previa = Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 9).toString());
                        cdefinitiva = Math.round(((anual + previa) / 2) * 100);
                        cdefinitiva = cdefinitiva/100;
                        tablanotas.setValueAt(cdefinitiva, tablanotas.getSelectedRow(), 10);
                    } else
                        {
                        tablanotas.setValueAt(null, tablanotas.getSelectedRow(), 9);
                        }
                }
            }
           
            evt.consume();
        }
    }//GEN-LAST:event_tablanotasKeyPressed

    private void cbocursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbocursosActionPerformed

        if (cbocursos.getItemCount() != 0) {
            cargarcbomaterias();
            cargartabla("");
        }

    }//GEN-LAST:event_cbocursosActionPerformed

    private void cbomateriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbomateriasActionPerformed

        if (cbomaterias.getItemCount() != 0) {
            cargartabla("");
        }
    }//GEN-LAST:event_cbomateriasActionPerformed

    private void btnaceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaceptarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnaceptarActionPerformed

    private void btnaplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaplicarActionPerformed

        EnterarFecha efechas = new EnterarFecha();
        try {
            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();
            CallableStatement Cpst = cn.prepareCall("{ call altaPrevias(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
            for (int i = 0; i < tablanotas.getRowCount(); i++) {

                if (tablanotas.getValueAt(i, 4) != null) {
                    Cpst.setString(1, tablanotas.getValueAt(i, 4).toString());
                } else {
                    Cpst.setObject(1, null);
                }

                if (tablanotas.getValueAt(i, 5) != null) {
                    Cpst.setInt(2, Integer.valueOf(tablanotas.getValueAt(i, 5).toString()));
                } else {
                    Cpst.setObject(2, null);
                }

                if (tablanotas.getValueAt(i, 6) != null) {
                    Cpst.setString(3, tablanotas.getValueAt(i, 6).toString());
                } else {
                    Cpst.setObject(3, null);
                }

                if (tablanotas.getValueAt(i, 7) != null) {
                    Cpst.setInt(4, Integer.valueOf(tablanotas.getValueAt(i, 7).toString()));
                } else {
                    Cpst.setObject(4, null);
                }

                if (tablanotas.getValueAt(i, 8) != null) {
                    Cpst.setString(5, tablanotas.getValueAt(i, 8).toString());
                } else {
                    Cpst.setObject(5, null);
                }

                if (tablanotas.getValueAt(i, 9) != null) {
                    Cpst.setInt(6, Integer.valueOf(tablanotas.getValueAt(i, 9).toString()));
                } else {
                    Cpst.setObject(6, null);
                }

                if (tablanotas.getValueAt(i, 10) != null) {
                    Cpst.setDouble(7, Double.valueOf(tablanotas.getValueAt(i, 10).toString()));
                } else {
                    Cpst.setObject(7, null);
                }

                if (tablanotas.getValueAt(i, 11) != null) {

                    Cpst.setInt(8, efechas.fechaentera(tablanotas.getValueAt(i, 11).toString()));

                } else {
                    Cpst.setObject(8, null);
                }

                if (tablanotas.getValueAt(i, 12) != null) {
                    Cpst.setInt(9, Integer.valueOf(tablanotas.getValueAt(i, 12).toString()));
                } else {
                    Cpst.setObject(9, null);
                }

                if (tablanotas.getValueAt(i, 13) != null) {
                    Cpst.setInt(10, Integer.valueOf(tablanotas.getValueAt(i, 13).toString()));
                } else {
                    Cpst.setObject(10, null);
                }

                Cpst.setInt(11, Integer.valueOf(tablanotas.getValueAt(i, 14).toString()));
                Cpst.setInt(12, Integer.valueOf(tablanotas.getValueAt(i, 15).toString()));
                Cpst.setInt(13, Integer.valueOf(tablanotas.getValueAt(i, 16).toString()));
                Cpst.setInt(14, Integer.valueOf(tablanotas.getValueAt(i, 17).toString()));

                // ejecutar el SP
                Cpst.execute();
                // confirmar si se ejecuto sin errores
                // cn.commit();

            }
            JOptionPane.showMessageDialog(null, "Los registros se cargaron exitosamente");
            cargartabla("");

        } catch (SQLException ex) {
            Logger.getLogger(RegistrarNotas.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnaplicarActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed

        this.dispose();
    }//GEN-LAST:event_btnsalirActionPerformed

    private void btnasigfeclibfolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnasigfeclibfolActionPerformed

        new AsignarFechaLibroFolio(null, true).setVisible(true);
        if (AsignarFechaLibroFolio.banderacancelar == 0) {
            for (int i = 0; i < tablanotas.getRowCount(); i++) {
                if (tablanotas.getValueAt(i, 10) != null) {
                    tablanotas.setValueAt(AsignarFechaLibroFolio.fecha, i, 11);
                    tablanotas.setValueAt(AsignarFechaLibroFolio.libro, i, 12);
                    tablanotas.setValueAt(AsignarFechaLibroFolio.folio, i, 13);
                }
            }
        }
    }//GEN-LAST:event_btnasigfeclibfolActionPerformed

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
            java.util.logging.Logger.getLogger(AsignarNotaPrevia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AsignarNotaPrevia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AsignarNotaPrevia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AsignarNotaPrevia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AsignarNotaPrevia dialog = new AsignarNotaPrevia(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnaceptar;
    private javax.swing.JButton btnaplicar;
    private javax.swing.JButton btnasigfeclibfol;
    private javax.swing.JButton btnsalir;
    private javax.swing.JComboBox<String> cbocarreras;
    private javax.swing.JComboBox<String> cbocursos;
    private javax.swing.JComboBox<String> cbomaterias;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablanotas;
    private javax.swing.JTextField txtbuscar;
    // End of variables declaration//GEN-END:variables
}
