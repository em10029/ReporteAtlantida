/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.estructura;

/**
 * Manejo de errores con respecto a la funcionalidad de la aplicación.
 *
 * @author Erick Fabricio Martínez Castellanos (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 24-oct-2018
 */
public class ReporteAtlantidaExcepcion extends Exception {
    
    public static final String ERROR_APLICACION_01 = "ERROR de apliación, el proceso no puede continuar.";    
    
    public static final String ERROR_CONFIGURACION_01 = "ERROR con el archivo de congiguración ReporteAtlantida.properties.";
    public static final String ERROR_CONFIGURACION_02 = "ERROR con el archivo de congiguración ReporteAtlantida.properties no se encuentra.";
    public static final String ERROR_CONFIGURACION_03 = "ERROR con el archivo de congiguración ReporteAtlantida.properties las propiedades han sido renombradas.";
    
    public static final String ERROR_CONEXION_01 = "ERROR con la conexión AS400.";
    public static final String ERROR_CONEXION_02 = "ERROR al establecer la conexión AS400.";
    public static final String ERROR_CONEXION_03 = "ERROR al cerrar la conexión AS400.";
    
    /**
     * Constructor para crear una instancia de ERROR perteneciente a la
     * funcionalidad de ReporteAtlantida.
     *
     * @param mensaje
     */
    public ReporteAtlantidaExcepcion(String mensaje) {
        super(mensaje);
    }
}
