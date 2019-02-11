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
import reporte.atlantida.fichero.Archivo;

/**
 * Estructura referente a un servicio (CAESER).
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 24-oct-2018
 */
public class Servicio {

    /**
     * (PK)Servicio - CEACEAS.CEASER
     */
    private String identificador;

    /**
     * Descripcion del servicio - CAESER.SERDES
     */
    private String descripcion;

    /**
     * Estado del servicio (A-activo ,I-inactivo) - CAESER.SEREST
     */
    private String estado;

    /**
     * Lista de correos del servicio - CAEEMP.SERCOR
     */
    private String correos;

    /**
     * Estado de envio del servicio (A(activo),I(inactivo)) - CEACEAS.CEASERE
     */
    private String estadoEnvio;

    /**
     * Es una lista porque el caso en el que formata sea detallado con dos
     * archivos.
     */
    private ArrayList<Archivo> archivos;

    /**
     * Cantidad de transacciones en el diario.
     */
    private int transaccionesDiario;

    /**
     * Cantidad de transacciones en el histroico.
     */
    private int transaccionesHistorico;

    /**
     * Total de transacciones contenidas en el servicio.
     */
    private int transacciones;

    /**
     * Estado del identificador1 SERI1D.
     */
    private String estadoIdentificador1;

    /**
     * Descripción del identificador1 SERI1U.
     */
    private String descripcionIdentificador1;

    /**
     * Estado del identificador2 SERI2D.
     */
    private String estadoIdentificador2;

    /**
     * * Descripción del identificador2 SERI2U.
     */
    private String descripcionIdentificador2;

    /**
     * Estado del identificador3 SERI3D.
     */
    private String estadoIdentificador3;

    /**
     * Descripción del identificador3 SERI3U.
     */
    private String descripcionIdentificador3;

    /**
     * Conceptos del servicio.
     */
    private ArrayList<Concepto> conceptos;

    /**
     * Contructor.
     */
    public Servicio() {
        this.transaccionesDiario = 0;
        this.transaccionesHistorico = 0;
        this.transacciones = 0;
        this.archivos = new ArrayList<>();
        this.conceptos = new ArrayList<>();
    }

    /**
     * Retorna el objeto en forma de cadena.
     *
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%3s", this.identificador).replace(' ', '0');
    }

    /**
     * Determina si se debe realizar la actualización del servicio (CAECEAS).
     *
     * @return actualizar
     */
    public boolean actualizar() {

        boolean actualizar = false; //Respuesta -> si es o no correcto actualizar el servicio

        for (Archivo archivo : this.archivos) {
            if (archivo.isGenerado() && archivo.isEnviado()) {
                actualizar = true;
            } else {
                actualizar = false;
                break;
            }
        }

        return actualizar;
    }

    /**
     * Retorna la cantidad de conceptos activos
     *
     * @return contador
     */
    public int getCantidadConcepto() {
        int contador = 0;
        for (Concepto concepto : this.conceptos) {
            if (concepto.getEstado().equals("A")) {
                contador++;
            }
        }
        return contador;
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
     * Get del campo descripcion.
     *
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Set del campo descripcion.
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
     * Get del campo estadoEnvio.
     *
     * @return estadoEnvio
     */
    public String getEstadoEnvio() {
        return estadoEnvio;
    }

    /**
     * Set del campo estadoEnvio.
     *
     * @param estadoEnvio
     */
    public void setEstadoEnvio(String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    /**
     * Get del campo transacciones.
     *
     * @return transacciones
     */
    public int getTransacciones() {
        return transacciones;
    }

    /**
     * Set del campo transacciones.
     *
     * @param transacciones
     */
    public void setTransacciones(int transacciones) {
        this.transacciones = transacciones;
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
     * Get del campo estadoIdentificador1.
     *
     * @return estadoIdentificador1
     */
    public String getEstadoIdentificador1() {
        return estadoIdentificador1;
    }

    /**
     * Set del campo estadoIdentificador1.
     *
     * @param estadoIdentificador1
     */
    public void setEstadoIdentificador1(String estadoIdentificador1) {
        this.estadoIdentificador1 = estadoIdentificador1;
    }

    /**
     * Get del campo descripcionIdentificador1.
     *
     * @return descripcionIdentificador1
     */
    public String getDescripcionIdentificador1() {
        return descripcionIdentificador1;
    }

    /**
     * Set del campo descripcionIdentificador1.
     *
     * @param descripcionIdentificador1
     */
    public void setDescripcionIdentificador1(String descripcionIdentificador1) {
        this.descripcionIdentificador1 = descripcionIdentificador1;
    }

    /**
     * Get del campo estadoIdentificador2.
     *
     * @return estadoIdentificador2
     */
    public String getEstadoIdentificador2() {
        return estadoIdentificador2;
    }

    /**
     * Set del campo estadoIdentificador2.
     *
     * @param estadoIdentificador2
     */
    public void setEstadoIdentificador2(String estadoIdentificador2) {
        this.estadoIdentificador2 = estadoIdentificador2;
    }

    /**
     * Get del campo descripcionIdentificador2.
     *
     * @return descripcionIdentificador2
     */
    public String getDescripcionIdentificador2() {
        return descripcionIdentificador2;
    }

    /**
     * Set del campo descripcionIdentificador2.
     *
     * @param descripcionIdentificador2
     */
    public void setDescripcionIdentificador2(String descripcionIdentificador2) {
        this.descripcionIdentificador2 = descripcionIdentificador2;
    }

    /**
     * Get del campo estadoIdentificador3.
     *
     * @return estadoIdentificador3
     */
    public String getEstadoIdentificador3() {
        return estadoIdentificador3;
    }

    /**
     * Set del campo estadoIdentificador3.
     *
     * @param estadoIdentificador3
     */
    public void setEstadoIdentificador3(String estadoIdentificador3) {
        this.estadoIdentificador3 = estadoIdentificador3;
    }

    /**
     * Get del campo descripcionIdentificador3.
     *
     * @return descripcionIdentificador3
     */
    public String getDescripcionIdentificador3() {
        return descripcionIdentificador3;
    }

    /**
     * Set del campo descripcionIdentificador3.
     *
     * @param descripcionIdentificador3
     */
    public void setDescripcionIdentificador3(String descripcionIdentificador3) {
        this.descripcionIdentificador3 = descripcionIdentificador3;
    }

    /**
     * Get del campo conceptos.
     *
     * @return conceptos
     */
    public ArrayList<Concepto> getConceptos() {
        return conceptos;
    }

    /**
     * Set del campo conceptos.
     *
     * @param conceptos
     */
    public void setConceptos(ArrayList<Concepto> conceptos) {
        this.conceptos = conceptos;
    }

    /**
     * Get del campo transaccionesDiario.
     *
     * @return transaccionesDiario
     */
    public int getTransaccionesDiario() {
        return transaccionesDiario;
    }

    /**
     * Set del campo transaccionesDiario.
     *
     * @param transaccionesDiario
     */
    public void setTransaccionesDiario(int transaccionesDiario) {
        this.transaccionesDiario = transaccionesDiario;
    }

    /**
     * Get del campo transaccionesHistorico.
     *
     * @return transaccionesHistorico
     */
    public int getTransaccionesHistorico() {
        return transaccionesHistorico;
    }

    /**
     * Set del campo transaccionesHistorico.
     *
     * @param transaccionesHistorico
     */
    public void setTransaccionesHistorico(int transaccionesHistorico) {
        this.transaccionesHistorico = transaccionesHistorico;
    }

}
