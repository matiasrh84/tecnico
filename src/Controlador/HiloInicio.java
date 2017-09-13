package Controlador;

import Vista.Login;
import javax.swing.JProgressBar;

public class HiloInicio extends Thread {

    JProgressBar progreso;

    public HiloInicio(JProgressBar progreso1) {
        super();
        this.progreso = progreso1;
    }

    public void run() {
        for (int i = 1; (i <= 100); i++) {
            progreso.setValue(i);
            pausa(10);
        }
        new Login().setVisible(true);
        
    }

    public void pausa(int mlSeg) {
        try {
            // pausa para el splash
            Thread.sleep(mlSeg);
        } catch (Exception e) {
        }
    }
}
