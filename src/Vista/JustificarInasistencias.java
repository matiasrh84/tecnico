package Vista;

import Controlador.ConexionMySQL;
import Controlador.Escape;
import Controlador.Fecha;
import Controlador.TableHeder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormatSymbols;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class JustificarInasistencias extends javax.swing.JDialog {

    DefaultTableModel model, dt;
    DefaultTableCellRenderer alinearCentroN, alinearCentroA, alinearCentroP, alinearCentroD, alinearCentroM, alinearCentroL, alinearCentroC;
    public DecimalFormatSymbols sim = new DecimalFormatSymbols();
    Fecha fechas = new Fecha();

    public JustificarInasistencias(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        Escape.funcionescape(this);
        cargarcbocarrera();
        cargarcbocursos();
        dobleclick();

        tablaAlumnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel rowSM = tablaAlumnos.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                //Ignore extra messages.
                if (e.getValueIsAdjusting()) {
                    return;
                }

                ListSelectionModel lsm
                        = (ListSelectionModel) e.getSource();

                if (lsm.isSelectionEmpty()) {

                } else {
                    int selectedRow = lsm.getMinSelectionIndex();
                    cargartablaFaltas();

                }
            }
        });

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Character c = evt.getKeyChar();
                if (Character.isLetter(c)) {
                    evt.setKeyChar(Character.toUpperCase(c));
                }
            }
        });
    }

    public void cargartablaFaltas() {
        ConexionMySQL cc = new ConexionMySQL();
        Connection cn = cc.Conectar();
        String[] Titulo = {"Fecha", "Lugar", "Tipo", "Turno", "Justificación", "id"};
        Object[] Registros = new Object[6];
        model = new DefaultTableModel(null, Titulo) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;

            }
        };
        String sql = "SELECT inasistencias.Fecha, inasistencias.Lugar, inasistencias.Tipo, "
                + "inasistencias.Turno, inasistencias.Justificacion, inasistencias.idInasistencias FROM inasistencias "
                + "WHERE inasistencias.idAlumnos =  '" + tablaAlumnos.getValueAt(tablaAlumnos.getSelectedRow(), 7).toString() + "' "
                + "AND inasistencias.idPeriodoLectivo =  '" + tablaAlumnos.getValueAt(tablaAlumnos.getSelectedRow(), 8).toString() + "'";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Registros[0] = rs.getObject(1);
                Registros[1] = rs.getObject(2);
                Registros[2] = rs.getObject(3);
                Registros[3] = rs.getObject(4);
                Registros[4] = rs.getObject(5);
                Registros[5] = rs.getInt(6);
                model.addRow(Registros);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        tablaFaltas.setModel(model);
        tablaFaltas.setAutoCreateRowSorter(true);
        TableCellRenderer headerRenderer = new TableHeder(Color.white, Color.black);

        for (int i = 0; i < 6; i++) {
            tablaFaltas.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            switch (i) {
                case 0:
                    tablaFaltas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroA);
                    tablaFaltas.getColumnModel().getColumn(i).setMaxWidth(70);
                    tablaFaltas.getColumnModel().getColumn(i).setMinWidth(70);
                    tablaFaltas.getColumnModel().getColumn(i).setPreferredWidth(70);
                    break;
                case 1:
                    tablaFaltas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroC);
                    tablaFaltas.getColumnModel().getColumn(i).setMinWidth(60);
                    tablaFaltas.getColumnModel().getColumn(i).setPreferredWidth(60);
                    break;
                case 2:
                    tablaFaltas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroD);
                    tablaFaltas.getColumnModel().getColumn(i).setMinWidth(70);
                    tablaFaltas.getColumnModel().getColumn(i).setPreferredWidth(70);
                    break;
                case 3:
                    tablaFaltas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroP);
                    tablaFaltas.getColumnModel().getColumn(i).setMinWidth(60);
                    tablaFaltas.getColumnModel().getColumn(i).setPreferredWidth(60);
                    break;
                case 4:
                    tablaFaltas.getColumnModel().getColumn(i).setCellRenderer(alinearCentroL);
                    tablaFaltas.getColumnModel().getColumn(i).setMaxWidth(80);
                    tablaFaltas.getColumnModel().getColumn(i).setMinWidth(80);
                    tablaFaltas.getColumnModel().getColumn(i).setPreferredWidth(80);
                    break;

                case 5:
                    tablaFaltas.getColumnModel().getColumn(i).setMaxWidth(0);
                    tablaFaltas.getColumnModel().getColumn(i).setMinWidth(0);
                    tablaFaltas.getColumnModel().getColumn(i).setPreferredWidth(0);
                    tablaFaltas.getTableHeader().getColumnModel().getColumn(i).setMaxWidth(0);
                    break;
            }

        }

        alinear();

    }

    void dobleclick() {
        tablaFaltas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if(!tablaFaltas.getValueAt(tablaFaltas.getSelectedRow(), 4).toString().equals("N/C")){
                    if(tablaFaltas.getValueAt(tablaFaltas.getSelectedRow(), 4).toString().equals("NO")){

                    try {
                        new Nota(null, true).setVisible(true);
                        ConexionMySQL mysql = new ConexionMySQL();
                        Connection cn = mysql.Conectar();
                        String sql = "UPDATE inasistencias SET "
                                + "Justificacion =?, Nota=? "
                                + "WHERE idInasistencias ="
                                + tablaFaltas.getValueAt(tablaFaltas.getSelectedRow(), 5).toString();
                        PreparedStatement pst = cn.prepareStatement(sql);
                        pst.setString(1, "SI");
                        pst.setString(2, Nota.nota);
                        int n = pst.executeUpdate();

                        if (n > 0) {
                            JOptionPane.showMessageDialog(null, "Los Datos se modificaron exitosamente...");
                        }
                        cargartablaAlumnos("");
                        tablaAlumnos.requestFocus();
                        tablaAlumnos.getSelectionModel().setSelectionInterval (0, 0);
                    } catch (SQLException ex) {
                        Logger.getLogger(JustificarInasistencias.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    else
                        {
                        JOptionPane.showMessageDialog(null, "La falta ya está justificada");
                        tablaAlumnos.requestFocus();
                        tablaAlumnos.getSelectionModel().setSelectionInterval (0, 0);
                        }
                }
                    else
                        {
                        JOptionPane.showMessageDialog(null, "No se puede justificar una suspensión");
                        tablaAlumnos.requestFocus();
                        tablaAlumnos.getSelectionModel().setSelectionInterval (0, 0);
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

        String sql = "SELECT * FROM vista_justificaciones "
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
                Registros[6] = rs.getObject(7);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaAlumnos = new JTable(){
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return colIndex != 0 && colIndex != 1 && colIndex != 2;
            }
        };
        tablaAlumnos = new javax.swing.JTable();
        btnaceptar = new javax.swing.JButton();
        btnaplicar = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        txtbuscar = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaAlumnos = new JTable(){
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return colIndex != 0 && colIndex != 1 && colIndex != 2;
            }
        };
        tablaFaltas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Justificar Inasistencias"));

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
                .addComponent(cbocarreras, 0, 209, Short.MAX_VALUE)
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
                .addComponent(cbocursos, 0, 99, Short.MAX_VALUE)
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
        tablaAlumnos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tablaAlumnos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnaceptar.setText("Aceptar");

        btnaplicar.setText("Aplicar");

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
                .addComponent(txtbuscar)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Inasistencias"));

        tablaFaltas.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tablaFaltas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tablaFaltas);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(155, 155, 155))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(541, 541, 541)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnaceptar)
                        .addGap(18, 18, 18)
                        .addComponent(btnaplicar)
                        .addGap(18, 18, 18)
                        .addComponent(btnsalir)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnsalir)
                            .addComponent(btnaceptar)
                            .addComponent(btnaplicar)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
        cargartablaAlumnos("");
    }//GEN-LAST:event_cbocarrerasActionPerformed

    private void cbocursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbocursosActionPerformed

        cargartablaAlumnos("");
        txtbuscar.setText("");
    }//GEN-LAST:event_cbocursosActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed

        dispose();
    }//GEN-LAST:event_btnsalirActionPerformed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased

        cargartablaAlumnos(txtbuscar.getText());
    }//GEN-LAST:event_txtbuscarKeyReleased

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
            java.util.logging.Logger.getLogger(JustificarInasistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JustificarInasistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JustificarInasistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JustificarInasistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JustificarInasistencias dialog = new JustificarInasistencias(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnsalir;
    private javax.swing.JComboBox<String> cbocarreras;
    private javax.swing.JComboBox<String> cbocursos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tablaAlumnos;
    private javax.swing.JTable tablaFaltas;
    private javax.swing.JTextField txtbuscar;
    // End of variables declaration//GEN-END:variables
}
