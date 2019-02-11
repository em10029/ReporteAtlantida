
package reporte.atlantida.servicio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="R" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "r"
})
@XmlRootElement(name = "queueMail.ExecuteResponse")
public class QueueMailExecuteResponse {

    @XmlElement(name = "R")
    protected byte r;

    /**
     * Obtiene el valor de la propiedad r.
     * 
     */
    public byte getR() {
        return r;
    }

    /**
     * Define el valor de la propiedad r.
     * 
     */
    public void setR(byte value) {
        this.r = value;
    }

}
