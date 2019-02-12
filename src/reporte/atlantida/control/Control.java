/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.control;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import reporte.atlantida.data.*;
import reporte.atlantida.estructura.*;
import reporte.atlantida.fichero.*;
import reporte.atlantida.utilitario.*;

/**
 * Destinada la ejecución principal del programa.
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 24-oct-2018
 */
public class Control {

    /**
     * Estado de búsqueda CAESTA. A: Activo, I: Inactivo, F: Fallido.
     */
    public static String estado;

    /**
     * Conexión con el servidor de base de datos.
     */
    public static Conexion conexion;

    /**
     * Directorio raíz.
     */
    public static Directorio directorio;

    /**
     * Log de aplicación.
     */
    public static ArchivoTextoPlano log;
    
    /**
     * Constructor privado, clase de comportamiento estático.
     */
    private Control() {
    }

    /**
     * Carga las propiedades de configuración, crea el directorio raíz y
     * verifica que las credenciales de usuario al servidor de base de datos
     * sean correctas.
     *
     * @return True si la configuración se realizo correctamente, False en caso
     * contrario.
     * @throws ReporteAtlantidaExcepcion Error de configuración.
     */
    public static boolean configurar() throws ReporteAtlantidaExcepcion {

        boolean config = false; //Determina si toda la preparacion del ambiente esta correcta

        //Carga de configuracion del programa
        if (Configuracion.configurar()) {
            conexion = new Conexion(Configuracion.CONEXION_URL,
                    Configuracion.CONEXION_USER,
                    Configuracion.CONEXION_PASSWORD);
            //La configuracion es correcta y se establecio la conexion AS400                
            if (conexion.abrir()) {
                conexion.cerrar();
                directorio = new Directorio(Configuracion.DIRECTORIO_INTERNO,
                        Configuracion.DIRECTORIO_EXTERNO);
                //Creacion del directorio raiz
                if (directorio.crear(Util.getFechaHoraActual("dd.MM.YYYY-hh.mm.ss"))) {
                    log = new ArchivoTextoPlano(directorio, "App", ".log");
                    //Creacion de archivo log de apliacion
                    if (log.crear()) {
                        config = true;
                        String configuracionLog = Configuracion.mostrar();
                        String directorioLog = "Directorio raíz: " + directorio.getUbicacion() + "\r\n";
                        System.out.println(configuracionLog);
                        System.out.println(directorioLog);
                        registrarProceso(configuracionLog);
                        registrarProceso(directorioLog);
                    }
                }
            }
        }

        return config;
    }

    /**
     * Crea las estructuras necesaria de la petición de reporte.
     *
     * @param rs
     * @return Reporte
     * @see Query#SELECT_REPORTE
     */
    public static Reporte getReporte(ResultSet rs) {
        Reporte reporte = null;
        try {
            //REPORTE
            reporte = new Reporte(directorio);
            reporte.setFecha(rs.getString("CEAFEC").trim());
            reporte.setHora(rs.getString("CEAHOR").trim());
            reporte.setCanal(rs.getString("CEACAN").trim());
            reporte.setCorrelativo(rs.getString("CEACOR").trim());
            reporte.setEstado(rs.getString("CEASTA").trim());
            reporte.setInformacion(rs.getString("CEATIF").trim());
            reporte.setGeneracion(rs.getString("CEATGE").trim());
            //reporte.setContenido(rs.getString("CEATIP").trim());

            switch (reporte.getGeneracion()) {
                case "M":
                    //MANUAL
                    reporte.setContenido("T"); //DIARIO E HISTORICO
                    //reporte.setAccion("P");
                    break;
                case "A":
                    //AUTOMATICO
                    reporte.setContenido("D"); //DIARIO
                    //reporte.setAccion("A");
                    break;
                case "C":
                    //CIERRE
                    reporte.setContenido("H"); //HISTORICO
                    //reporte.setAccion("D");
                    break;
                default:
                    reporte.setContenido("T");
                    break;
            }

            reporte.setFechaInicial(rs.getString("CEAFEI").trim());
            reporte.setFechaFinal(rs.getString("CEAFEF").trim());
            reporte.setDestino(rs.getString("CEATCO").trim());
            reporte.setCorreos(rs.getString("CEACOE").trim());

            //EMPRESA
            reporte.getEmpresa().setIdentificador(rs.getString("CEAEMP").trim());
            reporte.getEmpresa().setNombre(rs.getString("EMPDES").trim());
            reporte.getEmpresa().setCorreos(rs.getString("EMPCOR").trim());
            reporte.getEmpresa().setConcepto(rs.getString("EMPNUS").trim());
            reporte.getEmpresa().setNivel(rs.getString("NIVEL").trim());
            reporte.getEmpresa().setVersion(rs.getString("VERSION").trim());

            //SERVICIOS
            PreparedStatement ps2 = null;
            ResultSet rs2 = null;
            try {
                ps2 = conexion.getConexion().prepareStatement(Query.SELECT_SERVICIOS);
                ps2.setString(1, reporte.getFecha());
                ps2.setString(2, reporte.getHora());
                ps2.setString(3, reporte.getCanal());
                ps2.setString(4, reporte.getCorrelativo());
                ps2.setString(5, reporte.getEmpresa().getIdentificador());

                rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    Servicio servicio = new Servicio();
                    servicio.setIdentificador(rs2.getString("CEASER").trim());
                    servicio.setDescripcion(rs2.getString("SERDES").trim());
                    servicio.setEstadoEnvio(rs2.getString("CEASERE").trim());
                    servicio.setEstado(rs2.getString("SEREST").trim());
                    servicio.setCorreos(rs2.getString("SERCOR").trim());
                    servicio.setEstadoIdentificador1(rs2.getString("SERI1U").trim());
                    servicio.setDescripcionIdentificador1(rs2.getString("SERI1D").trim());
                    servicio.setEstadoIdentificador2(rs2.getString("SERI2U").trim());
                    servicio.setDescripcionIdentificador2(rs2.getString("SERI2D").trim());
                    servicio.setEstadoIdentificador3(rs2.getString("SERI3U").trim());
                    servicio.setDescripcionIdentificador3(rs2.getString("SERI3D").trim());

                    getTransaciones(reporte, servicio); //Cantidad de transacciones
                    getConceptos(reporte, servicio); //Conceptos del servicio

                    reporte.getEmpresa().getServicios().add(servicio); //Agrega el servicio a la empresa
                }
            } catch (SQLException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, "ERROR SQL Query.SELECT_SERVICIOS", ex);
            } finally {
                Query.cerrar(rs2);
                Query.cerrar(ps2);
            }

            //ENVIO
            getEnvio(reporte);

        } catch (ReporteAtlantidaExcepcion ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, "ERROR al crear el directorio", ex);
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, "ERROR SQL Query.SELECT_REPORTE", ex);
        }

        return reporte;
    }

    /**
     * Obtiene la cantidad de transacciones registradas en el servicio.
     *
     * @param reporte
     * @param servicio
     */
    private static void getTransaciones(Reporte reporte, Servicio servicio) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int transacciones = 0;

        switch (reporte.getInformacion()) {
            case "PAGOS":

                //DIARIO
                if (reporte.getContenido().equals("T") || reporte.getContenido().equals("D")) {
                    try {
                        ps = conexion.getConexion().prepareStatement(Query.SELECT_TRANSACCIONES_DIARIO);
                        ps.setString(1, reporte.getEmpresa().getIdentificador());
                        ps.setString(2, servicio.getIdentificador());
                        ps.setString(3, reporte.getFechaInicial());
                        ps.setString(4, reporte.getFechaFinal());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            transacciones += rs.getInt("TOTAL");
                            servicio.setTransaccionesDiario(rs.getInt("TOTAL"));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        Query.cerrar(rs);
                        Query.cerrar(ps);
                    }
                }

                //HISTORICO
                if (reporte.getContenido().equals("T") || reporte.getContenido().equals("H")) {
                    try {
                        ps = conexion.getConexion().prepareStatement(Query.SELECT_TRANSACCIONES_HISTORICO);
                        ps.setString(1, reporte.getEmpresa().getIdentificador());
                        ps.setString(2, servicio.getIdentificador());
                        ps.setString(3, reporte.getFechaInicial());
                        ps.setString(4, reporte.getFechaFinal());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            transacciones += rs.getInt("TOTAL");
                            servicio.setTransaccionesHistorico(rs.getInt("TOTAL"));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        Query.cerrar(rs);
                        Query.cerrar(ps);
                    }
                }
                break;

            case "SALDOS":
                try {
                    ps = conexion.getConexion().prepareStatement(Query.SELECT_TRANSACCIONES_SALDOS);
                    ps.setString(1, reporte.getEmpresa().getIdentificador());
                    ps.setString(2, servicio.getIdentificador());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        transacciones = rs.getInt("TOTAL");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    Query.cerrar(rs);
                    Query.cerrar(ps);
                }
                break;
        }

        servicio.setTransacciones(transacciones);

    }

    /**
     * Obtiene los conceptos relacionados al servicio.
     *
     * @param reporte
     * @param servicio
     */
    private static void getConceptos(Reporte reporte, Servicio servicio) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.getConexion().prepareStatement(Query.SELECT_CONCEPTOS);
            ps.setString(1, reporte.getEmpresa().getIdentificador());
            ps.setString(2, servicio.getIdentificador());
            rs = ps.executeQuery();
            while (rs.next()) {
                Concepto concepto = new Concepto();
                concepto.setNumero(rs.getInt("SCONUM"));
                concepto.setEstado(rs.getString("SCOEST").trim());
                concepto.setDescripcion(rs.getString("SCODES").trim());
                concepto.setOperador(rs.getString("SCOOPE").trim());
                servicio.getConceptos().add(concepto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Query.cerrar(rs);
            Query.cerrar(ps);
        }
    }

    /**
     * Obtiene el tipo de envió del reporte.
     *
     * @param reporte
     * @throws ReporteAtlantidaExcepcion
     */
    private static void getEnvio(Reporte reporte) throws ReporteAtlantidaExcepcion {
        PreparedStatement ps = null;
        ResultSet rs = null;

        //TIPO DE ENVIO
        try {
            ps = conexion.getConexion().prepareStatement(Query.SELECT_ENVIO);
            ps.setString(1, "TENA");
            ps.setString(2, reporte.getEmpresa().getIdentificador());
            rs = ps.executeQuery();
            if (rs.next()) {
                reporte.getEmpresa().setTipoEnvio(rs.getString("EMPDAT").trim());
            } else { //Default
                reporte.getEmpresa().setTipoEnvio("COR");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Query.cerrar(rs);
            Query.cerrar(ps);
        }

        //ENVIO SMTP
        if (reporte.getEmpresa().getTipoEnvio().equals("COR") || reporte.getDestino().equals("N")) {
            try {
                ps = conexion.getConexion().prepareStatement(Query.SELECT_ENVIO_SMTP);
                rs = ps.executeQuery();
                if (rs.next()) {
                    reporte.getEmpresa().setCopiasOcultas(rs.getString("PARCON").trim()); //Copias ocultas
                }
            } catch (SQLException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                Query.cerrar(rs);
                Query.cerrar(ps);
            }
        } else { //ENVIO FTP            
            try {
                ps = conexion.getConexion().prepareStatement(Query.SELECT_ENVIO);
                ps.setString(1, "CENA");
                ps.setString(2, reporte.getEmpresa().getIdentificador());
                rs = ps.executeQuery();
                if (rs.next()) {
                    reporte.getEmpresa().setCodigoEnvio(rs.getString("EMPDAT").trim());
                } else { //ERROR
                    String error = "Error de data, no se ha encontrado en codigo CENA para el envio FTP, verificar CAEDTA.CAEPEA.";
                    throw new ReporteAtlantidaExcepcion(error + "\r\n" + reporte.toString());
                }
            } catch (SQLException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                Query.cerrar(rs);
                Query.cerrar(ps);
            }

            try {
                ps = conexion.getConexion().prepareStatement(Query.SELECT_ENVIO_FTP);
                ps.setString(1, reporte.getEmpresa().getCodigoEnvio());
                rs = ps.executeQuery();
                if (rs.next()) {
                    reporte.getEmpresa().setUrl(rs.getString("PA1FIN").trim());
                    reporte.getEmpresa().setDirectorio(rs.getString("PA1ORG").trim());
                    reporte.getEmpresa().setUser(rs.getString("PA1USE").trim());
                    reporte.getEmpresa().setPassword(rs.getString("PA1PAS").trim());
                } else { //ERROR
                    String error = "Error de data, no se ha encontrado la información del servidor FTP con el codigo: " + reporte.getEmpresa().getCodigoEnvio() + " verificar SAEDTA.SAEPA1";
                    throw new ReporteAtlantidaExcepcion(error + "\r\n" + reporte.toString());
                }
            } catch (SQLException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                Query.cerrar(rs);
                Query.cerrar(ps);
            }
        }

    }

    /**
     * Escribe en el archivo .log del programa. Se utiliza para registrar los
     * procesos realizados.
     *
     * @param proceso
     */
    public static void registrarProceso(String proceso) {

        FileWriter lector = null;
        PrintWriter escritor = null;

        try {
            //#1 Abrir
            lector = new FileWriter(log.getUbicacion(), true);
            escritor = new PrintWriter(lector);

            //#2 Escribir
            escritor.write(proceso);

            //#3 Cerrar
            escritor.close();
            lector.close();

        } catch (IOException ex) {
            Logger.getLogger(reporte.atlantida.control.Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
