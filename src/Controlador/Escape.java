package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

public class Escape {
    public static void funcionescape(final JDialog windowDialog) {
        ActionListener escAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                windowDialog.dispose();
            }
        };
        windowDialog.getRootPane().registerKeyboardAction(escAction,
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
}
