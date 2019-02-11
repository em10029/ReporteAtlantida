/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.fichero;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representa un directorio (caperta) del sistema. 
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 Fecha: 01-30-2018
 */
public class Directorio {

    /**
     * Udicación física del directorio (carpeta).
     */
    private String ubicacion;

    /**
     * URL del directorio.
     */
    private String URL;

    /**
     * Nombre.
     */
    private String nombre;

    /**
     * Constructor.
     *
     * @param ubicacion
     * @param URL
     */
    public Directorio(String ubicacion, String URL) {
        this.ubicacion = ubicacion;
        this.URL = URL;
    }

    /**
     * Crea el directorio
     *
     * @param nombre
     * @return creacionExitosa
     */
    public boolean crear(String nombre) {
        this.nombre = nombre;
        this.ubicacion = this.ubicacion + "\\" + this.nombre;
        this.URL = this.URL + "/" + nombre;

        File carpeta = new File(this.ubicacion);
        return carpeta.mkdirs();
    }

    /**
     * Get del campo ubicacion.
     *
     * @return ubicacion
     */
    public String getUbicacion() {
        return this.ubicacion;
    }

    /**
     * Get del campo URL.
     *
     * @return URL
     */
    public String getURL() {
        return this.URL;
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
     * Destructor.
     */
    @Override
    public void finalize() {
        try {
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(Directorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
