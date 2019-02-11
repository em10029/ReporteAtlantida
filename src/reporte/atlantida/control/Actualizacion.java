/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.control;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import reporte.atlantida.data.Conexion;
import reporte.atlantida.data.Query;
import reporte.atlantida.estructura.Reporte;
import reporte.atlantida.estructura.Servicio;

/**
 * Realiza las actualizaciones correspondientes a los estados de los reportes y
 * servicios. (CAECEA y CAECEAS). Verifica los criterios de generación y envió.
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 24-oct-2018
 */
public class Actualizacion {

    /**
     * Constructor privado, clase de comportamiento estático.
     */
    private Actualizacion() {
    }

    /**
     * Proceso de actualización de la petición de reporte.
     *
     * @param conexion
     * @param reporte
     */
    public static void actualizar(Conexion conexion, Reporte reporte) {

        //Actualizando los servicio CAECEAS
        for (Servicio servicio : reporte.getEmpresa().getServicios()) {
            if (servicio.actualizar()) {
                actualizarServicio(conexion, reporte, servicio, "A");
            }
        }

        //Actualizando el registro CAECEA        
        if (reporte.getEmpresa().actualizar()) {
            actualizarReporte(conexion, reporte, "A");
        } else {
            actualizarReporte(conexion, reporte, "F");
        }

    }

    /**
     * Actualización del estado del servicio. CAEDTA.CAECEAS.CEASERE. A: Si la
     * generación y envio de los archivos para dicho servicio fue exitoso. I:
     * Estado por defecto, indicando que el servicio no fue procesado.
     *
     * @param conexion
     * @param reporte
     * @param servicio
     * @param estado
     */
    private static void actualizarServicio(Conexion conexion, Reporte reporte, Servicio servicio, String estado) {

        PreparedStatement ps = null;

        try {
            ps = conexion.getConexion().prepareStatement(Query.UPDATE_SERVICIO);
            ps.setString(1, estado);
            ps.setString(2, reporte.getFecha());
            ps.setString(3, reporte.getHora());
            ps.setString(4, reporte.getCanal());
            ps.setString(5, reporte.getCorrelativo());
            ps.setString(6, servicio.getIdentificador());

            if (ps.executeUpdate() == 1) {
                servicio.setEstadoEnvio(estado);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Actualizacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Query.cerrar(ps);
        }
    }

    /**
     * Actualización del estado del la petición de reporte.
     * CAEDTA.CAECEA.CEASTA. A: Estado activo, indica que todos los servicio
     * fueron generados y enviados exitosamente. I: Estado por defecto inactivo,
     * indica que la petición no fue procesada. F: Estado fallido, indica que
     * surgió algún problema en la generación o envió.
     *
     * @param conexion
     * @param reporte
     * @param estado
     */
    private static void actualizarReporte(Conexion conexion, Reporte reporte, String estado) {

        PreparedStatement ps = null;

        try {
            ps = conexion.getConexion().prepareStatement(Query.UPDATE_REPORTE);
            ps.setString(1, estado);
            ps.setString(2, reporte.getFecha());
            ps.setString(3, reporte.getHora());
            ps.setString(4, reporte.getCanal());
            ps.setString(5, reporte.getCorrelativo());

            if (ps.executeUpdate() == 1) {
                reporte.setEstado(estado);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Actualizacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Query.cerrar(ps);
        }
    }

}
