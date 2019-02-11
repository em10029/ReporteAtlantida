/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.utilitario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static reporte.atlantida.estructura.ReporteAtlantidaExcepcion.*;

/**
 * Destinada para la configuración de aplicación, carga el archivo
 * <b>ReporteAtlantida.properties</b>
 * y obtiene las variables de propiedades.
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 24-oct-2018
 */
public class Configuracion {

    /**
     * URL de conexión.
     */
    public static String CONEXION_URL;

    /**
     * Usuario de conexión.
     */
    public static String CONEXION_USER;

    /**
     * Clave de conexión.
     */
    public static String CONEXION_PASSWORD;

    /**
     * Directorio de aplicación, en este se almacenaran los archivos de
     * reportes.
     */
    public static String DIRECTORIO_INTERNO;

    /**
     * Directorio http utilizado por servicio de notificaciones (crear
     * directorio como sitio web).
     */
    public static String DIRECTORIO_EXTERNO;

    /**
     * Tiempo que se pausará la aplicación cuando no halla solicitudes de
     * reportes pendientes para procesar.
     */
    public static long CONTROL_TIEMPO;

    /**
     * Producto que enviará los correos electronicos, EMLDTA.EMLPRD verificar
     * que el estado este activo.
     */
    public static String SERVICIO_PRODUCTO;

    /**
     * Plantilla que utilizará para la generación de reportes de pago con nivel:
     * NIVELEMP - Empresa y versión: FORMATOV04 - Concepto detalldo.
     */
    public static String PLANTILLA_EXCEL_ESTATICA;

    /**
     * Plantilla que utilizará para la generación de reportes de pago con nivel:
     * NIVELSER - Servicio y versión: FORMATOV04 - Concepto detalldo.
     */
    public static String PLANTILLA_EXCEL_DINAMICA;

    /**
     * Constructor privado, clase de comportamiento estático.
     */
    private Configuracion() {
    }

    /**
     * Carga el archivo <b>ReporteAtlantida.properties</b> y obtiene las
     * variables de propiedades.
     *
     * @return boolean True si todas la variables han sido cargadas
     * exitosamente, False en caso contrario.
     */
    public static boolean configurar() {

        boolean config = false; //determinara si la configuracion del programa es correcta
        Properties propiedades = new Properties(); //prodiedades del programa
        InputStream configuracion = null; //archivo de propiedades

        try {
            //Obteniento la ruta absoluta del archivo de propiedades
            String ruta = System.getProperty("user.dir");
            String rutaAbsoluta = ruta + "/configs/ReporteAtlantida.properties";

            //Cargando el archivo de configuracion
            configuracion = new FileInputStream(rutaAbsoluta);
            propiedades.load(configuracion);

            //------------OBTENIENDO LAS VARIABLES DE CONFIGURACION------------//
            //CONEXION AS400
            CONEXION_URL = propiedades.getProperty("conexion.url").trim();
            CONEXION_USER = propiedades.getProperty("conexion.user").trim();
            CONEXION_PASSWORD = propiedades.getProperty("conexion.password").trim();

            //DIRECTORIOS DE ARCHIVOS
            DIRECTORIO_INTERNO = propiedades.getProperty("directorio.interno").trim();
            DIRECTORIO_EXTERNO = propiedades.getProperty("directorio.externo").trim();

            //PROGRAMA
            CONTROL_TIEMPO = Long.parseLong(propiedades.getProperty("control.tiempo").trim());

            //SERVICIO
            SERVICIO_PRODUCTO = propiedades.getProperty("servicio.producto").trim();

            //PANTILLA            
            PLANTILLA_EXCEL_ESTATICA = ruta + "/resources/PlantillaEstatica.xlsx";
            PLANTILLA_EXCEL_DINAMICA = ruta + "/resources/PlantillaDinamica.xlsx";

            configuracion.close();
            config = true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, ERROR_CONFIGURACION_02, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, ERROR_CONFIGURACION_01, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, ERROR_CONFIGURACION_03, ex);
        }

        return config;
    }

    /**
     * Muesta los valores de las varibles de configuración.
     *
     * @return String Cadena representativa propiedad...>valor
     */
    public static String mostrar() {
        String datos = "\r\nInicio: " + Util.getFechaHoraActual("dd/MM/YYYY - hh:mm:ss a") + "\r\n";

        datos += "\r\n*********** CONFIGURACION ************\r\n";
        datos += "--------------------------------------\r\n";
        datos += "------------- CONEXION -------------\r\n";
        datos += "+ CONEXION BD..........> " + CONEXION_URL + "\r\n";
        datos += "+ USUARIO..............> " + CONEXION_USER + "\r\n";
        datos += "------------- DIRECTORIOS ------------\r\n";
        datos += "+ DIRECTORIO INTERNO...> " + DIRECTORIO_INTERNO + "\r\n";
        datos += "+ DIRECTORIO EXTERNO...> " + DIRECTORIO_EXTERNO + "\r\n";
        datos += "------------- CONTROL ----------------\r\n";
        datos += "+ TIEMPO DE ESPERA.....> " + CONTROL_TIEMPO + " minutos\r\n";
        datos += "------------- SERVICIO ----------------\r\n";
        datos += "+ PRODUCTO WS..........> " + SERVICIO_PRODUCTO + "\r\n";
        datos += "--------------------------------------\r\n\r\n";
        return datos;
    }

}
