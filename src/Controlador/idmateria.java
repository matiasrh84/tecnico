package Controlador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class idmateria {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
public void sacarid(String materia, String curso)
    {
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        String sSQL = "SELECT asignaturas.idAsignaturas FROM asignaturas INNER JOIN cursos_tienen_asignaturas ON cursos_tienen_asignaturas.idAsignaturas = asignaturas.idAsignaturas INNER JOIN cursos ON cursos_tienen_asignaturas.idCursos = cursos.idCursos WHERE asignaturas.Nombre = '"+materia+"' AND cursos.Numero = '"+curso+"'";
            try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                id=Integer.valueOf(rs.getString("asignaturas.idAsignaturas"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos...");
            JOptionPane.showMessageDialog(null, ex);
        }
            
    }
}
