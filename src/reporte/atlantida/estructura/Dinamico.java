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
import java.util.Arrays;

/**
 * Estructura auxiliar para reportes con versión FORMATOV05 - Dinámica (CAEOPC).
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 30-oct-2018
 */
public class Dinamico {

    /**
     * Separador (OPCSEP).
     */
    private String separador;

    /**
     * Encabezado (OPCENC).
     */
    private String encabezado;

    /**
     * Concepto (OPCCOC).
     */
    private String conceptos;

    /**
     * Formato de fecha (OPCFOR).
     */
    private String formatoFecha;

    /**
     * Lista de campos.
     */
    private ArrayList<String> campos;

    /**
     * Constructor.
     */
    public Dinamico() {
        this.campos = new ArrayList<>();
        //default
        this.separador = ";";
        this.encabezado = "I";
        this.conceptos = "I";
        this.formatoFecha = "DDMMAAAA";
    }

    /**
     * Retorna el objeto en forma de cadena.
     *
     * @return String
     */
    @Override
    public String toString() {
        String info = ">Parametros: " + Arrays.toString(this.campos.toArray()) + "\r\n"
                + ">Encabezado: " + this.encabezado + "\r\n"
                + ">Conceptos: " + this.conceptos + "\r\n"
                + ">Formato de fecha: " + this.formatoFecha + "\r\n"
                + ">Delimitador: " + this.separador + "\r\n"
                + ">Campos: " + this.campos.size() + "\r\n";

        return info;
    }

    /**
     * Get del campo separador.
     *
     * @return separador
     */
    public String getSeparador() {
        return separador;
    }

    /**
     * Set del campo separador.
     *
     * @param separador
     */
    public void setSeparador(String separador) {
        this.separador = separador;
    }

    /**
     * Get del campo encabezado.
     *
     * @return encabezado
     */
    public String getEncabezado() {
        return encabezado;
    }

    /**
     * Set del campo encabezado.
     *
     * @param encabezado
     */
    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    /**
     * Get del campo formatoFecha.
     *
     * @return formatoFecha
     */
    public String getFormatoFecha() {
        return formatoFecha;
    }

    /**
     * Set del campo formatoFecha.
     *
     * @param formatoFecha
     */
    public void setFormatoFecha(String formatoFecha) {
        this.formatoFecha = formatoFecha;
    }

    /**
     * Get del campo conceptos.
     *
     * @return conceptos
     */
    public String getConceptos() {
        return conceptos;
    }

    /**
     * Set del campo conceptos.
     *
     * @param conceptos
     */
    public void setConceptos(String conceptos) {
        this.conceptos = conceptos;
    }

    /**
     * Get del campo campos.
     *
     * @return campos
     */
    public ArrayList<String> getCampos() {
        return campos;
    }

    /**
     * Set del campo campos.
     *
     * @param campos
     */
    public void setCampos(ArrayList<String> campos) {
        this.campos = campos;
    }

}
