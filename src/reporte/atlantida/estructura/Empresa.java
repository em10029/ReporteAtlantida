/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.estructura;

import java.util.ArrayList;

/**
 * Estructura referente a la empresa (CAEEMP).
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 24-oct-2018
 */
public class Empresa {

    /**
     * IBS Empresa - CAECEA.CEAEMP
     */
    private String identificador;

    /**
     * Nombre de la empresa - CAEEMP.EMPDES
     */
    private String nombre;

    /**
     * Version de la empresa - CAEEMP.EMPAVP o CAEEMP.EMPAVS
     */
    private String version;

    /**
     * Nivel de envio de la empresa - CAEEMP.EMPANP o CAEEMP.EMPANS
     */
    private String nivel;

    /**
     * Numero de concepto - CAEEMP.EMPNUS
     */
    private String concepto;

    /**
     * Campo - CAEPEA.PEACAM
     */
    private String tipoEnvio;

    /**
     * Datos - CAEPEA.EMPDAT
     */
    private String codigoEnvio;

    /**
     * Lista de correos de la empresa- CAEEMP.EMPCOR
     */
    private String correos; //Lista de correos de la empresa- CAEEMP.EMPCOR

    /**
     * En caso de que el envio sea SMTP
     */
    private String copiasOcultas;

    /**
     * URL de conexion FTP
     */
    private String url;

    /**
     * Directorio donde se almacenaran los archivos
     */
    private String directorio;

    /**
     * Usuario con acceso al servidor FTP
     */
    private String user;

    /**
     * Clave de autenticacion al servidor FTP
     */
    private String password;

    /**
     * Servicios - CAECEAS
     */
    private ArrayList<Servicio> servicios;

    /**
     * Constructor
     */
    public Empresa() {
        this.servicios = new ArrayList<>();
        //this.tipoEnvio = "COR"; //Valor por defecto
        this.copiasOcultas = "";
    }

    /**
     * Retorna el objeto en forma de cadena.
     *
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%9s", this.identificador).replace(' ', '0');
    }

    /**
     * Determina si se debe realizar la actualización de la petición (CAECEA).
     *
     * @return actualizar
     */
    public boolean actualizar() {

        boolean actualizar = false;

        for (Servicio servicio : this.servicios) {
            if (servicio.actualizar()) {
                actualizar = true;
            } else {
                actualizar = false;
                break;
            }
        }

        return actualizar;
    }

    /**
     * Retorna la cantidad total de transacciones.
     *
     * @return transacciones
     */
    public int getTransacciones() {
        int transacciones = 0;
        for (Servicio servicio : this.servicios) {
            transacciones += servicio.getTransacciones();
        }
        return transacciones;
    }

    /**
     * Retorna la cantidad de conceptos activos segun el servicio
     *
     * @return max
     */
    public int getCantidadConcepto() {
        int max = 0;
        int ref = 0;
        for (Servicio servicio : this.servicios) {
            ref = servicio.getCantidadConcepto();
            if (ref > max) {
                max = ref;
            }
        }
        return max;
    }

    /**
     * Get del campo identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Set del campo identificador.
     *
     * @param identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Get del campo nombre.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Set del campo nombre.
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Get del campo version.
     *
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set del campo version.
     *
     * @param version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Get del campo nivel.
     *
     * @return nivel
     */
    public String getNivel() {
        return nivel;
    }

    /**
     * Set del campo nivel.
     *
     * @param nivel
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    /**
     * Get del campo concepto.
     *
     * @return
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * Set del campo concepto.
     *
     * @param concepto
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * Get del campo tipoEnvio.
     *
     * @return tipoEnvio
     */
    public String getTipoEnvio() {
        return tipoEnvio;
    }

    /**
     * Set del campo tipoEnvio.
     *
     * @param tipoEnvio
     */
    public void setTipoEnvio(String tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    /**
     * Get del campo codigoEnvio.
     *
     * @return codigoEnvio
     */
    public String getCodigoEnvio() {
        return codigoEnvio;
    }

    /**
     * Set del campo codigoEnvio.
     *
     * @param codigoEnvio
     */
    public void setCodigoEnvio(String codigoEnvio) {
        this.codigoEnvio = codigoEnvio;
    }

    /**
     * Get del campo correos.
     *
     * @return correos
     */
    public String getCorreos() {
        return correos;
    }

    /**
     * Set del campo correos.
     *
     * @param correos
     */
    public void setCorreos(String correos) {
        this.correos = correos;
    }

    /**
     * Get del campo copiasOcultas.
     *
     * @return copiasOcultas
     */
    public String getCopiasOcultas() {
        return copiasOcultas;
    }

    /**
     * Set del campo copiasOcultas.
     *
     * @param copiasOcultas
     */
    public void setCopiasOcultas(String copiasOcultas) {
        this.copiasOcultas = copiasOcultas;
    }

    /**
     * Get del campo url.
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set del campo url.
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get del campo directorio.
     *
     * @return directorio
     */
    public String getDirectorio() {
        return directorio;
    }

    /**
     * Set del campo directorio.
     *
     * @param directorio
     */
    public void setDirectorio(String directorio) {
        this.directorio = directorio;
    }

    /**
     * Get del campo user.
     *
     * @return user
     */
    public String getUser() {
        return user;
    }

    /**
     * Set del campo user.
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Get del campo password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set del campo password.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get del campo servicios.
     *
     * @return servicios
     */
    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    /**
     * Set del campo servicios.
     *
     * @param servicios
     */
    public void setServicios(ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }

}
