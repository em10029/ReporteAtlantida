/*
 * ©Informática Atlántida 2019.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */

package reporte.atlantida.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import reporte.atlantida.control.Control;
import static reporte.atlantida.control.Control.getReporte;
import static reporte.atlantida.control.Control.registrarProceso;
import reporte.atlantida.control.Proceso;
import reporte.atlantida.data.Query;
import reporte.atlantida.estructura.Reporte;
import reporte.atlantida.estructura.ReporteAtlantidaExcepcion;
import reporte.atlantida.utilitario.Configuracion;
import reporte.atlantida.utilitario.Util;

/**
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 31-ene-2019
 */
public final class Controlador {
    
    public Controlador() throws ReporteAtlantidaExcepcion{
        Control.estado = "I";
        if(Control.configurar()){            
            while(true){                
                this.iniciar();                
                //PAUSAR-MOTOR
                try {
                    Runtime garbage = Runtime.getRuntime();
                    garbage.gc();
                    //System.out.println("Espera... Tiempo: " + Configuracion.CONTROL_TIEMPO + " minutos");
                    Thread.sleep(Configuracion.CONTROL_TIEMPO * 5000); //Pausa en milisegundos
                } catch (InterruptedException ex) {
                    Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
    
    private void iniciar() {
        //Busqueda de solicitudes
        if (Control.conexion.abrir()) {
            //INICIO-BUQUEDA
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = Control.conexion.getConexion().prepareStatement(Query.SELECT_REPORTE);
                ps.setString(1, Control.estado);
                rs = ps.executeQuery();

                while (rs.next()) {

                    //PROCESAMIENTO DE REPORTE
                    Reporte reporte = getReporte(rs); //Genera la estructura de la peticion
                    Proceso.procesar(Control.conexion, reporte); //Procesamiento de la peticion

                    //VIEW-LOG-NULL
                    String info = Util.info(reporte);
                    System.out.println(info);
                    registrarProceso(info);
                    reporte = null;
                }

            } catch (SQLException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                Query.cerrar(rs);
                Query.cerrar(ps);
            }
            Control.conexion.cerrar();
        }
    }
    
}

