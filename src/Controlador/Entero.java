package Controlador;


public class Entero {
    
   public boolean isInteger(String str){
try {
int v = Integer.parseInt(str);
return true;
} catch(NumberFormatException e) {
return false;
}
}
}
