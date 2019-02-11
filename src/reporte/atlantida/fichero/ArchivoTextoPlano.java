/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 * 
 */
package reporte.atlantida.fichero;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representa un archivo Plano (.txt / .log / .cvs / *).
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 Fecha: 01-30-2018
 */
public class ArchivoTextoPlano extends Archivo {

    /**
     * Archivo que se creara 
     */
    private File archivo;

    /**
     * Escrito de lineas
     */
    private PrintWriter escritor;

    /**
     * Constructor
     *
     * @param directorio
     * @param nombre
     * @param extencion
     */
    public ArchivoTextoPlano(Directorio directorio, String nombre, String extencion) {
        super(directorio, nombre, extencion);
    }

    /**
     * Crea y prepara el archivo y para su procesamiento.
     *
     * @return creacionExitosa
     */
    public boolean crear() {
        boolean creacionExitosa = false;

        try {
            this.archivo = new File(super.getUbicacion());
            if (this.archivo.createNewFile()) {
                this.escritor = new PrintWriter(this.archivo, "UTF-8");
                creacionExitosa = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(ArchivoTextoPlano.class.getName()).log(Level.SEVERE, null, ex);
        }

        return creacionExitosa;
    }

    /**
     * Agrega la trama(datos) en la plantilla.
     *
     * @param linea
     */
    public void agregarLinea(String linea) {
        this.escritor.println(linea);
    }

    /**
     * Guarda y cierra los cambios en la plantilla.
     */
    public void cerrar() {
        this.escritor.close();
    }

    /**
     * Destructor.
     */
    @Override
    public void finalize() {
        try {
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(ArchivoTextoPlano.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
