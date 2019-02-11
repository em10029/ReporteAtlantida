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
import reporte.atlantida.control.Control;
import reporte.atlantida.estructura.ReporteAtlantidaExcepcion;
import static reporte.atlantida.estructura.ReporteAtlantidaExcepcion.ERROR_CONFIGURACION_01;

/**
 *
 * @author efmartinez
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Control control = new Control("I");
        try {
            if (control.configurar()) {
                while (true) {
                    control.iniciar();
                }
            }
        } catch (ReporteAtlantidaExcepcion ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ERROR_CONFIGURACION_01, ex);
        }
        System.exit(0);

    }

}
