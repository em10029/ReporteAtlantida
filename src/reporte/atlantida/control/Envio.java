/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.control;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import reporte.atlantida.envio.SMTP;
import reporte.atlantida.envio.FTP;
import reporte.atlantida.estructura.Reporte;
import reporte.atlantida.estructura.ReporteAtlantidaExcepcion;

/**
 * Realiza los envíos correspondientes de los archivos.
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 24-oct-2018
 */
public class Envio {

    /**
     * Constructor privado, clase de comportamiento estático.
     */
    private Envio() {
    }

    /**
     * Determina el tipo de envió (SMTP, FTPS o FTP).
     *
     * @param reporte
     */
    public static void enviar(Reporte reporte) {
        if (reporte.getDestino().equals("S")) { //Por definicion, puede ser COR o FTP
            if (reporte.getEmpresa().getTipoEnvio().equals("COR")) {
                enviarSMTP(reporte);
            } else {//Servidor FTP 
                enviarFTP(reporte);
            }
        } else { //N: Correo electronico, otros correos
            enviarSMTP(reporte);
        }
    }

    /**
     * Realiza un envió SMTP.
     *
     * @param reporte
     */
    public static void enviarSMTP(Reporte reporte) {
        SMTP.enviar(reporte);
    }

    /**
     * Realiza un envió FTPS o FTP.
     *
     * @param reporte
     */
    public static void enviarFTP(Reporte reporte) {
        try {
            FTP.enviar(reporte);
        } catch (ReporteAtlantidaExcepcion ex) {
            Logger.getLogger(Envio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Envio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
