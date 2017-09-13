package Controlador;

import com.toedter.calendar.JDateChooser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;

public class Fecha {

    SimpleDateFormat Formato = new SimpleDateFormat("yyyy-MM-dd");
    
    SimpleDateFormat Formatook = new SimpleDateFormat("dd-MM-yyyy");
    
    public String getFecha(JDateChooser jd) {
        if (jd.getDate() != null) {
            return Formato.format(jd.getDate());
        } else {
            return null;
        }
    }
    
    public String getfechaok (JDateChooser jd) {
        if (jd.getDate() != null) {
            return Formatook.format(jd.getDate());
        } else {
            return null;
        }
    }

    public java.util.Date stringAdateok(String fecha) {
        
        SimpleDateFormat formato_de_texto = new SimpleDateFormat("dd-MM-yyyy");
        
        Date fechaE = null;
        
        try{
            fechaE=formato_de_texto.parse(fecha);
                   
            return fechaE;
                    
        }catch(ParseException ex)   {
                    
            return null;
                    
                                    }
    
}
    public String dateastring(Date fecha)
        {
        String fechacadena, año, mes, dia;
        SimpleDateFormat formato_de_texto = new SimpleDateFormat("yyyy-MM-dd");
        //2017-08-31
        fechacadena= formato_de_texto.format(fecha);
            año= fechacadena.substring(0,4);
            mes= fechacadena.substring(5,7);
            dia= fechacadena.substring(8,10);
            fechacadena=dia+"-"+mes+"-"+año; 
        return fechacadena;
        }
    
    
     public java.util.Date stringAdate(String fecha) {
        
        SimpleDateFormat formato_de_texto = new SimpleDateFormat("yyyy-MM-dd");
        
        Date fechaE = null;
        
        try{
            fechaE=formato_de_texto.parse(fecha);
                   
            return fechaE;
                    
        }catch(ParseException ex)   {
                    
            return null;
                    
                                    }
    
}
}