/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import javax.swing.JOptionPane;

/**
 *
 * @author PC1
 */
public class EnterarFecha {
    
public int fechaentera(String fecha)
    {
    int fechaentera;
    String año, mes, dia, fechatotal;
    año= fecha.substring(6,10);
    mes= fecha.substring(3,5);
    dia= fecha.substring(0,2);
    fechatotal=año+mes+dia;
    JOptionPane.showMessageDialog(null, fechatotal);
        fechaentera=Integer.valueOf(fechatotal);
        return fechaentera;
    }
}
