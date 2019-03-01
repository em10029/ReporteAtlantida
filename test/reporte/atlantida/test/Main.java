/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import reporte.atlantida.estructura.ReporteAtlantidaExcepcion;
import reporte.atlantida.utilitario.Util;

/**
 * Inicio principal del programa.
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 24-oct-2018
 */
public class Main {
    
    /**
     * Constructor privado, clase de comportamiento estático.
     */
    private Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {             
        
        
        try {
            //Despliegue de aplicacion
            Controlador app = new Controlador(); //Reporte Atlantida Automatico
        } catch (ReporteAtlantidaExcepcion ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally{            
            System.exit(0);
        } 
        
        /*
        String hora = Util.getFechaHoraActual("HH:mm:ss");
        System.out.println(hora);
        */
    }
    
}
