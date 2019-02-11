/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static reporte.atlantida.estructura.ReporteAtlantidaExcepcion.*;

/**
 * Control de conexiones con se servidor de datos DB2 for AS400.
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 24-oct-2018
 */
public class Conexion {

    /**
     * Conexión con bases de datos.
     */
    private Connection conexion;

    /**
     * URL de la conexión.
     */
    private final String url;

    /**
     * Usuario con acceso a la conexión.
     */
    private final String user;

    /**
     * Clave de autenticación.
     */
    private final String password;

    /**
     * Constructor, inicializador de propiedades.
     *
     * @param url URL de la conexión a la base de datos.
     * @param user Usuario con acceso a la conexión.
     * @param password Contraseña de autenticación.
     */
    public Conexion(String url, String user, String password) {
        this.conexion = null;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Estable conexión con una base de datos.
     *
     * @return TRUE si se establecio correctamente la conexión, FALSE en caso
     * contrario.
     */
    public boolean abrir() {

        boolean conexionExitosa = false;

        try {
            Class.forName("com.ibm.as400.access.AS400JDBCDriver");
            this.conexion = DriverManager.getConnection(this.url, this.user, this.password);
            conexionExitosa = true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, ERROR_CONEXION_02, ex);
        }

        return conexionExitosa;
    }

    /**
     * Cierra la conexión con una base de datos.
     */
    public void cerrar() {
        try {
            this.conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, ERROR_CONEXION_03, ex);
        }
    }

    /**
     * Obtine la conexión a la base de datos.
     *
     * @return conexion
     */
    public Connection getConexion() {
        return this.conexion;
    }

}
