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
import java.util.logging.Level;
import java.util.logging.Logger;
import reporte.atlantida.fichero.Archivo;
import reporte.atlantida.fichero.Directorio;

/**
 * Estructura referente a una petición de reporte (CAECEA).
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 24-oct-2018
 */
public class Reporte {

    /**
     * Contador de objetos
     */
    public static int contador = 0;

    /**
     * (PK)Fecha - CAECEA.CAEFEC
     */
    private String fecha;

    /**
     * (PK)Hora - CAECEA.CAEHOR
     */
    private String hora;

    /**
     * (PK)Canal CAECEA.CAECAN
     */
    private String canal;

    /**
     * (PK)Correlativo - CAECEA.CAECOR
     */
    private String correlativo;

    /**
     * Estado - CEACEA.CAESTA
     */
    private String estado;

    /**
     * Tipo de informacion (PAGOS,SALDOS) - CAECEA.CAETIP
     */
    private String informacion;

    /**
     * Tipo de generacion (M,A,C) Manula, Automatico, Cierre - CAECEA.CAETGE
     */
    private String generacion;

    /**
     * Informacion que contiene el reporte (T,H,D) Todos, Historio, Diario -
     * CAECEA.CAETIP
     */
    private String contenido;

    /**
     * Fecha inicial - CAECEA.CAEFEI
     */
    private String fechaInicial;

    /**
     * Fecha final - CAECEA.CAEFEF
     */
    private String fechaFinal;

    /**
     * Tipo de destinatarios que se le enviaran los reportes (S,N) correo
     * empresa, correos de los servicio
     */
    private String destino;

    /**
     * Lista de correos electronicos - CAECEA.CAECOE
     */
    private String correos;

    /**
     * Empresa
     */
    private Empresa empresa;

    /**
     * Direcorio de archivos
     */
    private Directorio directorio;

    /**
     * Contiene todos los archivos
     */
    private ArrayList<Archivo> archivos;

    /**
     * Información de generación
     */
    private String infoGeneracion;

    /**
     * Información de envió
     */
    private String infoEnvio;

    /**
     * Información de error
     */
    private String infoError;

    //private String accion;
    /**
     * Constructor de vista.
     */
    public Reporte() {

    }

    /**
     * Constructor de procesamiento.
     *
     * @param root Directorio raiz.
     * @throws reporte.atlantida.estructura.ReporteAtlantidaExcepcion
     */
    public Reporte(Directorio root) throws ReporteAtlantidaExcepcion {
        contador++;
        this.empresa = new Empresa();
        this.archivos = new ArrayList<>();
        this.directorio = new Directorio(root.getUbicacion(), root.getURL());
        if (!this.directorio.crear("R" + Reporte.contador)) {
            throw new ReporteAtlantidaExcepcion("Error al crear directorio: " + directorio.getNombre());
        }

        //Informacion 
        this.infoGeneracion = "";
        this.infoEnvio = "";
        this.infoError = "";
    }

    /**
     * Retorna el objeto en forma de cadena.
     *
     * @return String
     */
    @Override
    public String toString() {
        return this.fecha + "-" + this.hora + "-" + this.canal + "-" + this.correlativo;
    }

    /**
     * Get del campo fecha.
     *
     * @return fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Set del campo fecha.
     *
     * @param fecha
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Get del campo hora.
     *
     * @return hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * Set del campo hora.
     *
     * @param hora
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * Get del campo canal.
     *
     * @return canal
     */
    public String getCanal() {
        return canal;
    }

    /**
     * Set del campo canal.
     *
     * @param canal
     */
    public void setCanal(String canal) {
        this.canal = canal;
    }

    /**
     * Get del campo correlativo.
     *
     * @return correlativo
     */
    public String getCorrelativo() {
        return correlativo;
    }

    /**
     * Set del campo correlativo.
     *
     * @param correlativo
     */
    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    /**
     * Get del campo estado.
     *
     * @return estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Set del campo estado.
     *
     * @param estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Get del campo informacion.
     *
     * @return informacion
     */
    public String getInformacion() {
        return informacion;
    }

    /**
     * Set del campo informacion.
     *
     * @param informacion
     */
    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    /**
     * Get del campo generacion.
     *
     * @return generacion
     */
    public String getGeneracion() {
        return generacion;
    }

    /**
     * Set del campo generacion.
     *
     * @param generacion
     */
    public void setGeneracion(String generacion) {
        this.generacion = generacion;
    }

    /**
     * Get del campo contenido.
     *
     * @return contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Set del campo contenido.
     *
     * @param contenido
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * Get del campo fechaInicial.
     *
     * @return fechaInicial
     */
    public String getFechaInicial() {
        return fechaInicial;
    }

    /**
     * Set del campo fechaInicial.
     *
     * @param fechaInicial
     */
    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    /**
     * Get del campo fechaFinal.
     *
     * @return fechaFinal
     */
    public String getFechaFinal() {
        return fechaFinal;
    }

    /**
     * Set del campo fechaFinal.
     *
     * @param fechaFinal
     */
    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * Get del campo destino.
     *
     * @return destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Set del campo destino.
     *
     * @param destino
     */
    public void setDestino(String destino) {
        this.destino = destino;
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
     * Get del campo empresa.
     *
     * @return empresa
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * Set del campo empresa.
     *
     * @param empresa
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * Get del campo directorio.
     *
     * @return directorio
     */
    public Directorio getDirectorio() {
        return directorio;
    }

    /**
     * Set del campo directorio.
     *
     * @param directorio
     */
    public void setDirectorio(Directorio directorio) {
        this.directorio = directorio;
    }

    /**
     * Get del campo archivos.
     *
     * @return archivos
     */
    public ArrayList<Archivo> getArchivos() {
        return archivos;
    }

    /**
     * Set del campo archivos.
     *
     * @param archivos
     */
    public void setArchivos(ArrayList<Archivo> archivos) {
        this.archivos = archivos;
    }

    /**
     * Get del campo infoGeneracion.
     *
     * @return infoGeneracion
     */
    public String getInfoGeneracion() {
        return infoGeneracion;
    }

    /**
     * Set del campo infoGeneracion.
     *
     * @param infoGeneracion
     */
    public void setInfoGeneracion(String infoGeneracion) {
        this.infoGeneracion += infoGeneracion + "\r\n";
    }

    /**
     * Get del campo infoEnvio.
     *
     * @return infoEnvio
     */
    public String getInfoEnvio() {
        return infoEnvio;
    }

    /**
     * Set del campo infoEnvio.
     *
     * @param infoEnvio
     */
    public void setInfoEnvio(String infoEnvio) {
        this.infoEnvio += infoEnvio + "\r\n";
    }

    /**
     * Get del campo infoError.
     *
     * @return infoError
     */
    public String getInfoError() {
        return infoError;
    }

    /**
     * Set del campo infoError.
     *
     * @param infoError
     */
    public void setInfoError(String infoError) {
        this.infoError += infoError + "\r\n";
    }

    /**
     * Destructor del objeto.
     */
    @Override
    public void finalize() {
        try {
            System.out.println("Eliminando proceso: " + this.toString());
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
