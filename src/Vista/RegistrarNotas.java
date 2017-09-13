package Vista;

import Controlador.ConexionMySQL;
import Controlador.EnterarFecha;
import Controlador.Escape;
import Controlador.Fecha;
import Controlador.VerticalTableHeaderCellRenderer;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import Controlador.isNumeric;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

public class RegistrarNotas extends javax.swing.JDialog {

    DefaultTableModel model, dt;
    DefaultTableCellRenderer alinearCentroN, alinearCentroA, alinearCentroP, alinearCentroD, alinearCentroM, alinearCentroL, alinearCentroC;
    public DecimalFormatSymbols sim = new DecimalFormatSymbols();
    Fecha fechas = new Fecha();

    public RegistrarNotas(java.awt.Frame parent, boolean modal) {

        super(parent, modal);

        sim.setDecimalSeparator('.');

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
        String[] Titulo = {"Nº Orden", "Apellido", "Nombre", "Diagnostico 1", "Concepto 1", "Cuatrimestral 1", "Promedio 1", "Tp Asig 1", "Tp Pres 1", "Tp Aprob 1", "Promedio TP 1", "Diagnóstico 2", "Concepto 2", "Cuatrimestral 2", "Promedio 2", "Tp Asig. 2", "TP Pres. 2", "TP Aprob. 2", "Promedio TP 2", "Anual", "Anual TP", "Diciembre", "Recupera TP", "Marzo", "C. Definitiva", "Fecha Aprob.", "Libro", "Folio", "idAlumnos", "idCursos", "idAsignaturas", "idPeriodo", "Tipo"};
        Object[] Registros = new Object[33];
        model = new DefaultTableModel(null, Titulo) {
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        String sql = "SELECT * FROM vista_notas "
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
                Registros[3] = rs.getObject(4);
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
                Registros[11] = rs.getObject(12);
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

                if (rs.getObject(20) != null) {
                    Registros[19] = f.format(rs.getObject(20));
                } else {
                    Registros[19] = rs.getObject(20);
                }

                Registros[20] = rs.getObject(21);
                Registros[21] = rs.getObject(22);
                Registros[22] = rs.getObject(23);
                Registros[23] = rs.getObject(24);

                if (rs.getObject(25) != null) {
                    Registros[24] = f.format(rs.getObject(25));
                } else {
                    Registros[24] = rs.getObject(25);
                }

////////////////////////////////////////////////////////////////////////////////
                if (rs.getObject(26) != null) {
                    Registros[25] = fechas.dateastring(rs.getDate(26));
                } else {
                    Registros[25] = rs.getObject(26);
                }

////////////////////////////////////////////////////////////////////////////////                
                Registros[26] = rs.getObject(27);
                Registros[27] = rs.getObject(28);
                Registros[28] = rs.getInt(29);
                Registros[29] = rs.getInt(30);
                Registros[30] = rs.getInt(31);
                Registros[31] = rs.getInt(32);
                Registros[32] = rs.getObject(33);

                model.addRow(Registros);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        tablanotas.setCellSelectionEnabled(true);

        tablanotas.setModel(model);
        tablanotas.setAutoCreateRowSorter(true);

        /////////////////////////////////////////////////////////////////////////
        String[] datos = {"APROBADO", "DESAPROBADO"};
        JComboBox cbotp = new JComboBox(datos);
        TableColumn tc = tablanotas.getColumnModel().getColumn(22);
        TableCellEditor tce = new DefaultCellEditor(cbotp);
        tc.setCellEditor(tce);
        //////////////////////////////////////////////////////////////////////
        TableCellRenderer headerRenderer = new VerticalTableHeaderCellRenderer(Color.white, Color.black);

        for (int i = 0; i < 33; i++) {
            if (i == 0) {
                tablanotas.getColumnModel().getColumn(0).setPreferredWidth(35);
            }
            if (i >= 28 && i < 33) {
                tablanotas.getColumnModel().getColumn(i).setMaxWidth(0);
                tablanotas.getColumnModel().getColumn(i).setMinWidth(0);
                tablanotas.getColumnModel().getColumn(i).setPreferredWidth(0);
                tablanotas.getTableHeader().getColumnModel().getColumn(i).setMaxWidth(0);
            }
            tablanotas.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            if (i <= 2) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroA);
            } else if (i > 2 && i <= 6) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroN);
            } else if (i > 6 && i <= 10) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroP);
            } else if (i > 10 && i <= 14) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroN);
            } else if (i > 14 && i <= 18) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroP);
            } else if (i == 19) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroN);
            } else if (i == 20) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroP);
            } else if (i == 21) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroD);
            } else if (i == 22) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroP);
            } else if (i == 23) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroM);
            } else if (i == 24) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroC);
            } else if (i > 24) {
                tablanotas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroL);
            }

            if (i == 22) {
                tablanotas.getColumnModel().getColumn(i).setPreferredWidth(100);
                tablanotas.getColumnModel().getColumn(i).setMinWidth(100);
            }

            if (i == 25) {
                tablanotas.getColumnModel().getColumn(i).setPreferredWidth(70);
                tablanotas.getColumnModel().getColumn(i).setMinWidth(70);
            }
            if (i >= 3 && i != 25 && i != 22 && i < 28) {
                tablanotas.getColumnModel().getColumn(i).setPreferredWidth(30);
                tablanotas.getColumnModel().getColumn(i).setMinWidth(30);
                tablanotas.getColumnModel().getColumn(i).setMaxWidth(30);
            }
            if (i >= 1 && i < 3) {
                tablanotas.getColumnModel().getColumn(i).setPreferredWidth(140);
                tablanotas.getColumnModel().getColumn(i).setMinWidth(140);
            }
        }
        /////////////////////////////////LISTENER DE LA TABLA/////////////////////////////
        /*tablanotas.getModel().addTableModelListener(new TableModelListener() {

            //DecimalFormat f = new DecimalFormat("##.##", sim);
            public void tableChanged(TableModelEvent e) {
                
                
                                 

                    JOptionPane.showMessageDialog(null, "Evento");
                

            
            
            }
            });*/
        ////////////////////////////////////////////////////////////////////////////////
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
        tablanotas = new JTable(){
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return colIndex != 0 && colIndex != 1 && colIndex != 2
                && colIndex != 6 && colIndex != 7 && colIndex != 10 && colIndex != 14
                && colIndex != 15 && colIndex != 18 && colIndex != 19 && colIndex != 20
                && colIndex != 23;
            }
        };
        btnaceptar = new javax.swing.JButton();
        btnaplicar = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtbuscar = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        cbomaterias = new javax.swing.JComboBox<>();
        btnasignar1 = new javax.swing.JButton();
        btnasignar2 = new javax.swing.JButton();
        btnasigfeclibfol = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Registrar Notas"));

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
                .addComponent(cbocarreras, 0, 310, Short.MAX_VALUE)
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
                .addComponent(cbocursos, 0, 185, Short.MAX_VALUE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1207, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                .addContainerGap())
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
                .addComponent(cbomaterias, 0, 200, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbomaterias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnasignar1.setText("Asignar Prácticos 1º C");
        btnasignar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnasignar1ActionPerformed(evt);
            }
        });

        btnasignar2.setText("Asignar Prácticos 2º C");
        btnasignar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnasignar2ActionPerformed(evt);
            }
        });

        btnasigfeclibfol.setText("Asignar Fecha Libro y Folio");
        btnasigfeclibfol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnasigfeclibfolActionPerformed(evt);
            }
        });

        jButton1.setText("Asignar Nota de Previa");
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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnasignar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnasignar2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnasigfeclibfol)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnaceptar)
                        .addGap(18, 18, 18)
                        .addComponent(btnaplicar)
                        .addGap(18, 18, 18)
                        .addComponent(btnsalir))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnaceptar)
                    .addComponent(btnaplicar)
                    .addComponent(btnsalir)
                    .addComponent(btnasignar1)
                    .addComponent(btnasignar2)
                    .addComponent(btnasigfeclibfol)
                    .addComponent(jButton1))
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

    private void cbocarrerasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbocarrerasActionPerformed

        cargarcbocursos();
        cargarcbomaterias();

    }//GEN-LAST:event_cbocarrerasActionPerformed

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

    private void btnaplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaplicarActionPerformed

        EnterarFecha efechas = new EnterarFecha();
        try {
            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();
            CallableStatement Cpst = cn.prepareCall("{ call altaNotas(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
            for (int i = 0; i < tablanotas.getRowCount(); i++) {

                if (tablanotas.getValueAt(i, 3) != null) {
                    Cpst.setInt(1, Integer.valueOf(tablanotas.getValueAt(i, 3).toString()));
                } else {
                    Cpst.setObject(1, null);
                }

                if (tablanotas.getValueAt(i, 4) != null) {
                    Cpst.setInt(2, Integer.valueOf(tablanotas.getValueAt(i, 4).toString()));
                } else {
                    Cpst.setObject(2, null);
                }

                if (tablanotas.getValueAt(i, 5) != null) {
                    Cpst.setDouble(3, Double.valueOf(tablanotas.getValueAt(i, 5).toString()));
                } else {
                    Cpst.setObject(3, null);
                }
                if (tablanotas.getValueAt(i, 6) != null) {
                    Cpst.setDouble(4, Double.valueOf(tablanotas.getValueAt(i, 6).toString()));
                } else {
                    Cpst.setObject(4, null);
                }
                /////////////////////////////////////////////////////////////////
                if (tablanotas.getValueAt(i, 7) != null) {
                    Cpst.setInt(5, Integer.valueOf(tablanotas.getValueAt(i, 7).toString()));
                } else {
                    Cpst.setObject(5, null);
                }

                if (tablanotas.getValueAt(i, 8) != null) {
                    Cpst.setInt(6, Integer.valueOf(tablanotas.getValueAt(i, 8).toString()));
                } else {
                    Cpst.setObject(6, null);
                }

                if (tablanotas.getValueAt(i, 9) != null) {
                    Cpst.setInt(7, Integer.valueOf(tablanotas.getValueAt(i, 9).toString()));

                } else {

                    Cpst.setObject(7, null);
                }

                if (tablanotas.getValueAt(i, 10) != null) {
                    Cpst.setDouble(8, Double.valueOf(tablanotas.getValueAt(i, 10).toString()));
                } else {
                    Cpst.setObject(8, null);
                }
                /////////////////////////////////////////////////////////////////
                if (tablanotas.getValueAt(i, 11) != null) {
                    Cpst.setInt(9, Integer.valueOf(tablanotas.getValueAt(i, 11).toString()));
                } else {
                    Cpst.setObject(9, null);
                }
                if (tablanotas.getValueAt(i, 12) != null) {
                    Cpst.setInt(10, Integer.valueOf(tablanotas.getValueAt(i, 12).toString()));
                } else {
                    Cpst.setObject(10, null);
                }
                if (tablanotas.getValueAt(i, 13) != null) {
                    Cpst.setDouble(11, Double.valueOf(tablanotas.getValueAt(i, 13).toString()));
                } else {
                    Cpst.setObject(11, null);
                }
                if (tablanotas.getValueAt(i, 14) != null) {
                    Cpst.setDouble(12, Double.valueOf(tablanotas.getValueAt(i, 14).toString()));
                } else {
                    Cpst.setObject(12, null);
                }
                ////////////////////////////////////////////////////////////////////////////
                if (tablanotas.getValueAt(i, 15) != null) {
                    Cpst.setInt(13, Integer.valueOf(tablanotas.getValueAt(i, 15).toString()));
                } else {
                    Cpst.setObject(13, null);
                }

                if (tablanotas.getValueAt(i, 16) != null) {
                    Cpst.setInt(14, Integer.valueOf(tablanotas.getValueAt(i, 16).toString()));
                } else {
                    Cpst.setObject(14, null);
                }

                if (tablanotas.getValueAt(i, 17) != null) {
                    Cpst.setInt(15, Integer.valueOf(tablanotas.getValueAt(i, 17).toString()));
                } else {
                    Cpst.setObject(15, null);
                }

                if (tablanotas.getValueAt(i, 18) != null) {
                    Cpst.setDouble(16, Double.valueOf(tablanotas.getValueAt(i, 18).toString()));
                } else {
                    Cpst.setObject(16, null);
                }
                ////////////////////////////////////////////////////////////////////////////
                if (tablanotas.getValueAt(i, 19) != null) {
                    Cpst.setDouble(17, Double.valueOf(tablanotas.getValueAt(i, 19).toString()));
                } else {
                    Cpst.setObject(17, null);
                }
                ////////////////////////////////////////////////////////////////////////////
                if (tablanotas.getValueAt(i, 20) != null) {
                    Cpst.setDouble(18, Double.valueOf(tablanotas.getValueAt(i, 20).toString()));
                } else {
                    Cpst.setObject(18, null);
                }
                ////////////////////////////////////////////////////////////////////////////
                if (tablanotas.getValueAt(i, 21) != null) {
                    Cpst.setInt(19, Integer.valueOf(tablanotas.getValueAt(i, 21).toString()));
                } else {
                    Cpst.setObject(19, null);
                }

                if (tablanotas.getValueAt(i, 22) != null) {
                    Cpst.setString(20, tablanotas.getValueAt(i, 22).toString());

                } else {
                    Cpst.setObject(20, null);
                }

                if (tablanotas.getValueAt(i, 23) != null) {
                    Cpst.setInt(21, Integer.valueOf(tablanotas.getValueAt(i, 23).toString()));
                } else {
                    Cpst.setObject(21, null);
                }

                if (tablanotas.getValueAt(i, 24) != null) {
                    Cpst.setDouble(22, Double.valueOf(tablanotas.getValueAt(i, 24).toString()));
                } else {
                    Cpst.setObject(22, null);
                }
                if (tablanotas.getValueAt(i, 25) != null) {

                    Cpst.setInt(23, efechas.fechaentera(tablanotas.getValueAt(i, 25).toString()));
                } else {
                    Cpst.setObject(23, null);
                }
                if (tablanotas.getValueAt(i, 26) != null) {
                    Cpst.setInt(24, Integer.valueOf(tablanotas.getValueAt(i, 26).toString()));
                } else {
                    Cpst.setObject(24, null);
                }
                if (tablanotas.getValueAt(i, 27) != null) {
                    Cpst.setInt(25, Integer.valueOf(tablanotas.getValueAt(i, 27).toString()));
                } else {
                    Cpst.setObject(25, null);
                }

                Cpst.setInt(26, Integer.valueOf(tablanotas.getValueAt(i, 28).toString()));
                Cpst.setInt(27, Integer.valueOf(tablanotas.getValueAt(i, 29).toString()));
                Cpst.setInt(28, Integer.valueOf(tablanotas.getValueAt(i, 30).toString()));
                Cpst.setInt(29, Integer.valueOf(tablanotas.getValueAt(i, 31).toString()));

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


    private void tablanotasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablanotasKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            DecimalFormat f = new DecimalFormat("##.##", sim);
            isNumeric num = new isNumeric();

            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 4) != null && tablanotas.getValueAt(tablanotas.getSelectedRow(), 5) != null) {

                if (num.isNumeric(tablanotas.getValueAt(tablanotas.getSelectedRow(), 4).toString()) && num.isNumeric(tablanotas.getValueAt(tablanotas.getSelectedRow(), 5).toString())) {
                    {
                        int concepto1;
                        double cuatrimestral1, promedio1;
                        concepto1 = Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 4).toString());
                        cuatrimestral1 = Double.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 5).toString());
                        promedio1 = Math.round(((concepto1 + cuatrimestral1) / 2) * 100);
                        promedio1 = promedio1 / 100;
                        tablanotas.setValueAt(f.format(promedio1), tablanotas.getSelectedRow(), 6);
                        tablanotas.isCellEditable(tablanotas.getSelectedRow(), 6);

                    }
                }

            }

            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 12) != null && tablanotas.getValueAt(tablanotas.getSelectedRow(), 13) != null) {

                if (num.isNumeric(tablanotas.getValueAt(tablanotas.getSelectedRow(), 12).toString()) && num.isNumeric(tablanotas.getValueAt(tablanotas.getSelectedRow(), 13).toString())) {
                    {
                        int concepto2;
                        double cuatrimestral2, promedio2;
                        concepto2 = Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 12).toString());
                        cuatrimestral2 = Double.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 13).toString());
                        promedio2 = Math.round(((concepto2 + cuatrimestral2) / 2) * 100);
                        promedio2 = promedio2 / 100;
                        tablanotas.setValueAt(f.format(promedio2), tablanotas.getSelectedRow(), 14);

                    }
                }

            }
            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 4) != null && tablanotas.getValueAt(tablanotas.getSelectedRow(), 5) != null && tablanotas.getValueAt(tablanotas.getSelectedRow(), 12) != null && tablanotas.getValueAt(tablanotas.getSelectedRow(), 13) != null) {
                double promedio1, promedio2, anual;
                promedio1 = Double.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 6).toString());
                promedio2 = Double.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 14).toString());
                anual = Math.round(((promedio1 + promedio2) / 2) * 100);
                anual = anual / 100;
                tablanotas.setValueAt(f.format(anual), tablanotas.getSelectedRow(), 19);
            }

            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 19) != null) {
                double cdefinitiva = Double.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 19).toString());
                if (!tablanotas.getValueAt(tablanotas.getSelectedRow(), 32).equals("OBLIGATORIA") && Double.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 19).toString()) >= 7) {

                    tablanotas.setValueAt(f.format(cdefinitiva), tablanotas.getSelectedRow(), 24);
                }
            }
            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 21) != null) {
                if (num.isNumeric(tablanotas.getValueAt(tablanotas.getSelectedRow(), 21).toString())) {
                    if (Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 21).toString()) >= 4) {
                        int diciembre = Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 21).toString());
                        if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 19) != null) {
                            double anual = Double.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 19).toString());
                            double cdefinitiva = Math.round(((diciembre + anual) / 2) * 100);
                            cdefinitiva = cdefinitiva / 100;
                            tablanotas.setValueAt(f.format(cdefinitiva), tablanotas.getSelectedRow(), 24);
                        }
                    }
                } else {
                    tablanotas.setValueAt(null, tablanotas.getSelectedRow(), 21);
                }
            }
            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 23) != null) {
                if (num.isNumeric(tablanotas.getValueAt(tablanotas.getSelectedRow(), 23).toString())) {
                    if (Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 23).toString()) >= 4) {
                        int marzo = Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 23).toString());

                        if (Double.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 19).toString()) < 4) {
                            tablanotas.setValueAt(4, tablanotas.getSelectedRow(), 24);
                        } else {
                            double anual = Double.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 19).toString());
                            double cdefinitiva = Math.round(((marzo + anual) / 2) * 100);
                            cdefinitiva = cdefinitiva / 100;
                            tablanotas.setValueAt(f.format(cdefinitiva), tablanotas.getSelectedRow(), 24);
                        }

                    }
                } else {
                    tablanotas.setValueAt(null, tablanotas.getSelectedRow(), 23);
                }
            }
            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 8) != null && tablanotas.getValueAt(tablanotas.getSelectedRow(), 9) != null) {
                if (num.isNumeric(tablanotas.getValueAt(tablanotas.getSelectedRow(), 8).toString()) && num.isNumeric(tablanotas.getValueAt(tablanotas.getSelectedRow(), 9).toString())) {

                    double asignados, aprobados, promedio;

                    asignados = Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 8).toString());
                    aprobados = Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 9).toString());
                    promedio = Math.round(aprobados / asignados * 100);

                    tablanotas.setValueAt(f.format(promedio), tablanotas.getSelectedRow(), 10);

                }

            }

            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 16) != null && tablanotas.getValueAt(tablanotas.getSelectedRow(), 17) != null) {
                if (num.isNumeric(tablanotas.getValueAt(tablanotas.getSelectedRow(), 16).toString()) && num.isNumeric(tablanotas.getValueAt(tablanotas.getSelectedRow(), 17).toString())) {

                    double asignados, aprobados, promedio;

                    asignados = Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 16).toString());
                    aprobados = Integer.valueOf(tablanotas.getValueAt(tablanotas.getSelectedRow(), 17).toString());
                    promedio = Math.round(aprobados / asignados * 100);
                    tablanotas.setValueAt(f.format(promedio), tablanotas.getSelectedRow(), 18);

                }

            }
            if (tablanotas.getValueAt(tablanotas.getSelectedRow(), 24) != null || tablanotas.getValueAt(tablanotas.getSelectedRow(), 21) != null) {
                tablanotas.setValueAt(null, tablanotas.getSelectedRow(), 22);
            }

            evt.consume();
        }

    }//GEN-LAST:event_tablanotasKeyPressed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed

        this.dispose();

    }//GEN-LAST:event_btnsalirActionPerformed

    private void btnasignar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnasignar1ActionPerformed
        new AsignarPracticos(null, true).setVisible(true);
        if (AsignarPracticos.bandera == 1) {
            DecimalFormat f = new DecimalFormat("##.##", sim);
            sim.setDecimalSeparator('.');

            if (AsignarPracticos.chkaprobados.isSelected() && AsignarPracticos.chkpresentados.isSelected()) {
                double promedio;

                for (int i = 0; i < tablanotas.getRowCount(); i++) {

                    tablanotas.setValueAt(AsignarPracticos.asignados, i, 7);
                    tablanotas.setValueAt(AsignarPracticos.asignados, i, 8);
                    tablanotas.setValueAt(AsignarPracticos.asignados, i, 9);
                    tablanotas.setValueAt(100, i, 10);

                }
            } else if (AsignarPracticos.chkpresentados.isSelected()) {

                for (int i = 0; i < tablanotas.getRowCount(); i++) {
                    tablanotas.setValueAt(AsignarPracticos.asignados, i, 7);
                    tablanotas.setValueAt(AsignarPracticos.asignados, i, 8);
                    tablanotas.setValueAt(null, i, 9);
                    tablanotas.setValueAt(null, i, 10);

                }
            } else {
                for (int i = 0; i < tablanotas.getRowCount(); i++) {
                    tablanotas.setValueAt(AsignarPracticos.asignados, i, 7);

                    tablanotas.setValueAt(null, i, 8);
                    tablanotas.setValueAt(null, i, 9);
                    tablanotas.setValueAt(null, i, 10);
                }
            }
        }
    }//GEN-LAST:event_btnasignar1ActionPerformed

    private void btnasignar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnasignar2ActionPerformed

        new AsignarPracticos(null, true).setVisible(true);
        if (AsignarPracticos.bandera == 1) {

            if (AsignarPracticos.chkaprobados.isSelected() && AsignarPracticos.chkpresentados.isSelected()) {
                double promedio;

                for (int i = 0; i < tablanotas.getRowCount(); i++) {

                    tablanotas.setValueAt(AsignarPracticos.asignados, i, 15);
                    tablanotas.setValueAt(AsignarPracticos.asignados, i, 16);
                    tablanotas.setValueAt(AsignarPracticos.asignados, i, 17);
                    tablanotas.setValueAt(100, i, 18);

                }
            } else if (AsignarPracticos.chkpresentados.isSelected()) {
                for (int i = 0; i < tablanotas.getRowCount(); i++) {
                    tablanotas.setValueAt(AsignarPracticos.asignados, i, 15);
                    tablanotas.setValueAt(AsignarPracticos.asignados, i, 16);
                    tablanotas.setValueAt(null, i, 17);
                    tablanotas.setValueAt(null, i, 18);
                }
            } else {
                for (int i = 0; i < tablanotas.getRowCount(); i++) {
                    tablanotas.setValueAt(AsignarPracticos.asignados, i, 15);
                    tablanotas.setValueAt(null, i, 16);
                    tablanotas.setValueAt(null, i, 17);
                    tablanotas.setValueAt(null, i, 18);
                }
            }
        }

    }//GEN-LAST:event_btnasignar2ActionPerformed

    private void btnasigfeclibfolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnasigfeclibfolActionPerformed

        new AsignarFechaLibroFolio(null, true).setVisible(true);
        if (AsignarFechaLibroFolio.banderacancelar == 0) {
            for (int i = 0; i < tablanotas.getRowCount(); i++) {
                if (tablanotas.getValueAt(i, 24) != null) {
                    tablanotas.setValueAt(AsignarFechaLibroFolio.fecha, i, 25);
                    tablanotas.setValueAt(AsignarFechaLibroFolio.libro, i, 26);
                    tablanotas.setValueAt(AsignarFechaLibroFolio.folio, i, 27);
                }
            }
        }

    }//GEN-LAST:event_btnasigfeclibfolActionPerformed

    private void btnaceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaceptarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnaceptarActionPerformed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased

        cargartabla(txtbuscar.getText());

    }//GEN-LAST:event_txtbuscarKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        new AsignarNotaPrevia(null, true).setVisible(true);
        cargartabla("");
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnaceptar;
    private javax.swing.JButton btnaplicar;
    private javax.swing.JButton btnasigfeclibfol;
    private javax.swing.JButton btnasignar1;
    private javax.swing.JButton btnasignar2;
    private javax.swing.JButton btnsalir;
    private javax.swing.JComboBox<String> cbocarreras;
    private javax.swing.JComboBox<String> cbocursos;
    private javax.swing.JComboBox<String> cbomaterias;
    private javax.swing.JButton jButton1;
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
