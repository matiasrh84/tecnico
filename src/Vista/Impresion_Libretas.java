/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.ConexionMySQL;
import Controlador.Escape;
import Controlador.VerticalTableHeaderCellRenderer;
import Controlador.camposbd;
import static Vista.Notas_Alumnos.Periodo;
import static Vista.Notas_Alumnos.id_alumno;
import static Vista.Notas_Alumnos.curso;
import static Vista.Notas_Alumnos.dni;
import static Vista.Notas_Alumnos.legajo;
import static Vista.Notas_Alumnos.orientacion;
import java.awt.Color;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author LUKS1
 */
public class Impresion_Libretas extends javax.swing.JDialog {

    DefaultTableModel model;
    DefaultTableCellRenderer alinearCentroR, alinearCentroA, alinearDerecha, alinearIzquierda, alinearCentroV, alinearDerechaV, alinearIzquierdaV;

    public Impresion_Libretas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        Escape.funcionescape(this);
        cargartabla();
    }

    public void cargartabla() {
        ConexionMySQL cc = new ConexionMySQL();
        Connection cn = cc.Conectar();
        String[] Titulo = {"Asignatura", "Nº Orden", "Apellido", "Nombre", "Concepto 1", "Cuatrimestral 1", "Promedio 1", "Concepto 2", "Cuatrimestral 2", "Promedio 2", "Promedio Anual", "Promedio TP", "Diciembre", "Marzo", "Previa", "C. Definitiva", "idAlumnos", "idCursos", "idAsignaturas", "idPeriodo"};
        Object[] Registros = new Object[20];
        model = new DefaultTableModel(null, Titulo) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String sql = "Select * from notas_libreta where idAlumnos =" + id_alumno + " AND Año='" + Periodo + "' AND Numero = '" + curso + "'";

        try {//where alumnos.idalumnos=1 and cursos.Numero='1º A' and periodolectivo.Año='2017'
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Registros[0] = rs.getObject(1);
                Registros[1] = rs.getObject(2);
                Registros[2] = rs.getObject(3);
                Registros[3] = rs.getObject(4);
                Registros[4] = rs.getObject(5);
                Registros[5] = rs.getObject(6);
                Registros[6] = rs.getObject(6);
                Registros[7] = rs.getObject(8);
                Registros[8] = rs.getObject(9);
                Registros[9] = rs.getObject(10);
                Registros[10] = rs.getObject(11);
                Registros[11] = rs.getObject(12);
                Registros[12] = rs.getObject(13);
                Registros[13] = rs.getObject(14);
                Registros[14] = rs.getObject(15);
                Registros[15] = rs.getObject(16);
                Registros[16] = rs.getObject(17);
                Registros[17] = rs.getObject(18);
                Registros[18] = rs.getObject(19);
                Registros[19] = rs.getObject(20);
                model.addRow(Registros);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        tablamaterias.setModel(model);
        tablamaterias.setAutoCreateRowSorter(true);
        txtalumno.setText(tablamaterias.getValueAt(0, 2).toString() + " " + tablamaterias.getValueAt(0, 3).toString());
        txtorden.setText(tablamaterias.getValueAt(0, 1).toString());
        txtperiodo.setText(tablamaterias.getValueAt(0, 18).toString());
        txtdni.setText(dni);
        txtjegajo.setText(legajo);
        alinear();
        tablamaterias.getColumnModel().getColumn(0).setCellRenderer(alinearCentroA);
        tablamaterias.getColumnModel().getColumn(4).setCellRenderer(alinearCentroA);
        tablamaterias.getColumnModel().getColumn(5).setCellRenderer(alinearCentroA);
        tablamaterias.getColumnModel().getColumn(6).setCellRenderer(alinearCentroA);
        tablamaterias.getColumnModel().getColumn(7).setCellRenderer(alinearCentroA);
        tablamaterias.getColumnModel().getColumn(8).setCellRenderer(alinearCentroA);
        tablamaterias.getColumnModel().getColumn(9).setCellRenderer(alinearCentroA);
        tablamaterias.getColumnModel().getColumn(10).setCellRenderer(alinearCentroA);
        tablamaterias.getColumnModel().getColumn(11).setCellRenderer(alinearCentroA);
        tablamaterias.getColumnModel().getColumn(12).setCellRenderer(alinearCentroA);
        tablamaterias.getColumnModel().getColumn(13).setCellRenderer(alinearCentroA);
        tablamaterias.getColumnModel().getColumn(14).setCellRenderer(alinearCentroA);

        tablamaterias.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablamaterias.getColumnModel().getColumn(4).setPreferredWidth(60);
        tablamaterias.getColumnModel().getColumn(5).setPreferredWidth(75);
        tablamaterias.getColumnModel().getColumn(6).setPreferredWidth(60);
        tablamaterias.getColumnModel().getColumn(7).setPreferredWidth(60);
        tablamaterias.getColumnModel().getColumn(8).setPreferredWidth(75);
        tablamaterias.getColumnModel().getColumn(9).setPreferredWidth(60);
        tablamaterias.getColumnModel().getColumn(10).setPreferredWidth(75);
        tablamaterias.getColumnModel().getColumn(11).setPreferredWidth(60);
        tablamaterias.getColumnModel().getColumn(12).setPreferredWidth(40);
        tablamaterias.getColumnModel().getColumn(13).setPreferredWidth(40);
        tablamaterias.getColumnModel().getColumn(14).setPreferredWidth(70);

        /*tablanotas.getColumnModel().getColumn(3).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(4).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(5).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(6).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(7).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(8).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(9).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(10).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(11).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(12).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(13).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(14).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(15).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(16).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(17).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(18).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(19).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(20).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(21).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(22).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(23).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(24).setMinWidth(60);
        tablanotas.getColumnModel().getColumn(25).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(26).setMinWidth(35);
        tablanotas.getColumnModel().getColumn(31).setMinWidth(35);

        tablanotas.getColumnModel().getColumn(1).setPreferredWidth(140);
        tablanotas.getColumnModel().getColumn(2).setPreferredWidth(140);
        tablanotas.getColumnModel().getColumn(1).setMinWidth(140);
        tablanotas.getColumnModel().getColumn(2).setMinWidth(140);*/
        tablamaterias.getColumnModel().getColumn(1).setMaxWidth(0);
        tablamaterias.getColumnModel().getColumn(1).setMinWidth(0);
        tablamaterias.getColumnModel().getColumn(1).setPreferredWidth(0);
        ////////////////////////////////////////////////////////////////
        tablamaterias.getColumnModel().getColumn(2).setMaxWidth(0);
        tablamaterias.getColumnModel().getColumn(2).setMinWidth(0);
        tablamaterias.getColumnModel().getColumn(2).setPreferredWidth(0);
        ////////////////////////////////////////////////////////////////
        tablamaterias.getColumnModel().getColumn(3).setMaxWidth(0);
        tablamaterias.getColumnModel().getColumn(3).setMinWidth(0);
        tablamaterias.getColumnModel().getColumn(3).setPreferredWidth(0);
        ////////////////////////////////////////////////////////////////
        tablamaterias.getColumnModel().getColumn(15).setMaxWidth(0);
        tablamaterias.getColumnModel().getColumn(15).setMinWidth(0);
        tablamaterias.getColumnModel().getColumn(15).setPreferredWidth(0);
        ////////////////////////////////////////////////////////////////
        tablamaterias.getColumnModel().getColumn(16).setMaxWidth(0);
        tablamaterias.getColumnModel().getColumn(16).setMinWidth(0);
        tablamaterias.getColumnModel().getColumn(16).setPreferredWidth(0);
        ////////////////////////////////////////////////////////////////
        tablamaterias.getColumnModel().getColumn(17).setMaxWidth(0);
        tablamaterias.getColumnModel().getColumn(17).setMinWidth(0);
        tablamaterias.getColumnModel().getColumn(17).setPreferredWidth(0);
        ////////////////////////////////////////////////////////////////
        tablamaterias.getColumnModel().getColumn(18).setMaxWidth(0);
        tablamaterias.getColumnModel().getColumn(18).setMinWidth(0);
        tablamaterias.getColumnModel().getColumn(18).setPreferredWidth(0);
        ////////////////////////////////////////////////////////////////
        ///////Ultima Fila///////
        Rectangle r = tablamaterias.getCellRect(tablamaterias.getRowCount() - 1, 0, true);
        tablamaterias.scrollRectToVisible(r);
        tablamaterias.getSelectionModel().setSelectionInterval(tablamaterias.getRowCount() - 1, tablamaterias.getRowCount() - 1);

        //////////////////////////
    }

    void alinear() {
        alinearCentroR = new DefaultTableCellRenderer();
        alinearCentroR.setHorizontalAlignment(SwingConstants.CENTER);
        alinearDerecha = new DefaultTableCellRenderer();
        alinearDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        alinearIzquierda = new DefaultTableCellRenderer();
        alinearIzquierda.setHorizontalAlignment(SwingConstants.LEFT);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtalumno = new javax.swing.JTextField();
        txtjegajo = new javax.swing.JTextField();
        txtorden = new javax.swing.JTextField();
        txtdni = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtperiodo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablamaterias = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Alumno"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("Dni:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 204));
        jLabel2.setText("Apellido y Nombre:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 204));
        jLabel3.setText("Legajo:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 204));
        jLabel4.setText("N° Orden:");

        txtalumno.setEditable(false);
        txtalumno.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtalumno.setOpaque(false);

        txtjegajo.setEditable(false);
        txtjegajo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtjegajo.setOpaque(false);

        txtorden.setEditable(false);
        txtorden.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtorden.setOpaque(false);

        txtdni.setEditable(false);
        txtdni.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtdni.setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 204));
        jLabel5.setText("Periodo:");

        txtperiodo.setEditable(false);
        txtperiodo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtperiodo.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdni))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtjegajo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtperiodo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtorden, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtalumno, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(107, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtalumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtperiodo)
                        .addComponent(txtjegajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtorden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtdni, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Asignaturas"));

        tablamaterias.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablamaterias);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("Imprimir Libreta");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        LinkedList<camposbd> Resultados = new LinkedList<camposbd>();
        Resultados.clear();
        int i = 0;
        String alumno="",curso="",orientacion_libreta="",legajo_libreta="",año="",orden_libreta="";
        int n2 = tablamaterias.getRowCount();
        if (n2 != 0) {
//"Asignatura", "Nº Orden", "Apellido", "Nombre", "Concepto 1", "Cuatrimestral 1", "Promedio 1", 
//"Concepto 2", "Cuatrimestral 2", "Promedio 2", "Promedio Anual", "Diciembre", "Marzo", "Previa",
//"C. Definitiva", "idAlumnos", "idCursos", "idAsignaturas", "idPeriodo"};            
            while (i < n2) {
                alumno=tablamaterias.getValueAt(i, 2).toString() + " "+tablamaterias.getValueAt(i, 3).toString();                
                orientacion_libreta=orientacion;
                legajo_libreta=legajo;
                año=tablamaterias.getValueAt(i, 18).toString();
                orden_libreta=tablamaterias.getValueAt(i, 1).toString();
                //////////////////////////////////
                camposbd tipo;
                tipo = new camposbd(
                        String.valueOf(i + 1),
                        String.valueOf(tablamaterias.getValueAt(i, 0)),//Asignatura
                        String.valueOf(tablamaterias.getValueAt(i, 4)),//Concepto 1
                        String.valueOf(tablamaterias.getValueAt(i, 5)),//Cuatrimestral 1
                        String.valueOf(tablamaterias.getValueAt(i, 6)),//Promedio 1
                        String.valueOf(tablamaterias.getValueAt(i, 7)),//Concepto 2
                        String.valueOf(tablamaterias.getValueAt(i, 8)),//Cuatrimestral 2
                        String.valueOf(tablamaterias.getValueAt(i, 7)),//Promedio 2
                        String.valueOf(tablamaterias.getValueAt(i, 8)),//Promedio anual
                        String.valueOf(tablamaterias.getValueAt(i, 9)),//Promedio tp
                        String.valueOf(tablamaterias.getValueAt(i, i + 1)),//ord
                        String.valueOf(tablamaterias.getValueAt(i, 10)),//diciembre
                        String.valueOf(tablamaterias.getValueAt(i, 11)),//marzo
                        String.valueOf(tablamaterias.getValueAt(i, 12)),//previa
                        String.valueOf(tablamaterias.getValueAt(i, 13)),//definitiva
                        "INASIST. SIN JUSTIFICAR",//inasistecia
                        String.valueOf(0.5),//periodo1
                        String.valueOf(0)// periodo2    
                );
                Resultados.add(tipo);
                i++;
            }
            ///////////////////////////////////////////////////////////////////////
            try {
                this.dispose();
                JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/Libreta.jasper"));
                Map parametros = new HashMap();
                parametros.put("alumno", alumno);
                parametros.put("curso", curso);
                parametros.put("orientacion", orientacion);
                parametros.put("legajo", legajo);
                parametros.put("año", año);
                parametros.put("orden", orden_libreta);
                parametros.put("promedio1", "10.0");
                parametros.put("promedio2", "0.0");
                parametros.put("promedio3", "0.0");
                parametros.put("promedio4", "0.0");              
                //  JasperPrint jPrint = JasperFillManager.fillReport(reporte, parametros, new JREmptyDataSource());
                //  JasperViewer.viewReport(jPrint, true); 

                JasperPrint jPrint = JasperFillManager.fillReport(reporte, parametros, new JRBeanCollectionDataSource(Resultados));
                JasperViewer viewer = new JasperViewer(jPrint, false);
                //JasperViewer.viewReport(jPrint, true); 
                ///JasperPrintManager.printReport(jPrint, false);
                viewer.setTitle("Libreta de calificaciones");
                viewer.setVisible(true);
                //viewer.setAlwaysOnTop(true);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(Impresion_Libretas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Impresion_Libretas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Impresion_Libretas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Impresion_Libretas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Impresion_Libretas dialog = new Impresion_Libretas(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablamaterias;
    private javax.swing.JTextField txtalumno;
    private javax.swing.JTextField txtdni;
    private javax.swing.JTextField txtjegajo;
    private javax.swing.JTextField txtorden;
    private javax.swing.JTextField txtperiodo;
    // End of variables declaration//GEN-END:variables
}
