/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.control;

import reporte.atlantida.data.Conexion;
import reporte.atlantida.estructura.Reporte;

/**
 * Control de procesos, designa las etapas de ejecución.
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 24-oct-2018
 */
public class Proceso {

    /**
     * Constructor privado, clase de comportamiento estático.
     */
    private Proceso() {
    }

    /**
     * Realiza la ejecución de una petición de reporte.
     *
     * @param conexion
     * @param reporte
     */
    public static void procesar(Conexion conexion, Reporte reporte) {
        //GENERACION Y ENVIO                
        if (generar(conexion, reporte)) {
            enviar(reporte);
        }
        //ACTUALIZACION              
        actualizar(conexion, reporte);
    }

    /**
     * Generación de archivos del reporte.
     *
     * @param conexion
     * @param reporte
     * @return
     */
    public static boolean generar(Conexion conexion, Reporte reporte) {
        boolean generacionExitosa = false;
        try {
            generacionExitosa = Generacion.generar(conexion, reporte);
        } catch (java.lang.OutOfMemoryError ex) {
            reporte.setInfoError("ERROR de generación: " + ex);
        }
        return generacionExitosa;
    }

    /**
     * Envió de los archivos generados.
     *
     * @param reporte
     */
    public static void enviar(Reporte reporte) {
        try {
            Envio.enviar(reporte);
        } catch (Exception ex) {
            reporte.setInfoError("ERROR de envió: " + ex);
        }
    }

    /**
     * Actualización de la petición de reporte.
     *
     * @param conexion
     * @param reporte
     */
    public static void actualizar(Conexion conexion, Reporte reporte) {
        Actualizacion.actualizar(conexion, reporte);
    }

}
