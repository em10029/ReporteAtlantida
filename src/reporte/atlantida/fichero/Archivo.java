/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.fichero;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Estructura padre para la creación de cualquier tipo de archivo.
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 Apr 22, 2018
 */
public class Archivo {

    /**
     * Directorio donde se alojara el archivo
     */
    private Directorio directorio;

    /**
     * Nombre del archivo
     */
    private final String nombre;

    /**
     * Determina si el archivo fue generado
     */
    private boolean generado;

    /**
     * Determina si el archivo fue enviado
     */
    private boolean enviado;

    /**
     * Constructor
     *
     * @param directorio
     * @param nombre
     * @param extencion
     */
    public Archivo(Directorio directorio, String nombre, String extencion) {
        this.directorio = directorio;
        this.nombre = nombre + extencion;
        this.generado = false;
        this.enviado = false;
    }

    /**
     * Get del campo nombre.
     *
     * @return nombre
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Retorna la ubicación física del archivo.
     *
     * @return String
     */
    public String getUbicacion() {
        return this.directorio.getUbicacion() + "\\" + this.nombre;
    }

    /**
     * Retorna la URL del archivo.
     *
     * @return String
     */
    public String getURL() {
        return this.directorio.getURL() + "/" + this.nombre;
    }

    /**
     * Destructor
     */
    @Override
    public void finalize() {
        try {
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * Get del campo generado.
     *
     * @return generado
     */
    public boolean isGenerado() {
        return generado;
    }

    /**
     * Set del campo generado.
     *
     * @param generado
     */
    public void setGenerado(boolean generado) {
        this.generado = generado;
    }

    /**
     * Get del campo enviado.
     *
     * @return enviado
     */
    public boolean isEnviado() {
        return enviado;
    }

    /**
     * Set del campo enviado.
     *
     * @param enviado
     */
    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

}
