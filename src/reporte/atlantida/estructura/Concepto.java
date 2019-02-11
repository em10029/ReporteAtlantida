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
 * Estructura referente a un concepto de servicio (CAESCO).
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 06-nov-2018
 */
public class Concepto {

    /**
     * Número (SCONUM).
     */
    private int numero;

    /**
     * Estado (SCOEST).
     */
    private String estado;

    /**
     * Descripción (SCODES).
     */
    private String descripcion;

    /**
     * Operador (SCOOPE).
     */
    private String operador;

    /**
     * Constructor
     */
    public Concepto() {
    }

    /**
     * Convierte el objeto en una cadena.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Concepto{" + "numero=" + numero + ", estado=" + estado + ", descripcion=" + descripcion + ", operador=" + operador + '}';
    }

    /**
     * Get del campo numero.
     *
     * @return numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Set del campo numero.
     *
     * @param numero
     */
    public void setNumero(int numero) {
        this.numero = numero;
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
     * Get del campo operador.
     *
     * @return operador
     */
    public String getOperador() {
        return operador;
    }

    /**
     * Set del campo operador.
     *
     * @param operador
     */
    public void setOperador(String operador) {
        this.operador = operador;
    }

}
