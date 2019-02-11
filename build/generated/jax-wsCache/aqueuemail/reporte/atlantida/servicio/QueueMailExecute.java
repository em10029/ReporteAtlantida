
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
 *         &lt;element name="Prddsc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Emlttl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Cco" type="{CORREOS}listaStrings"/>
 *         &lt;element name="Att" type="{CORREOS}listaStrings"/>
 *         &lt;element name="Dst" type="{CORREOS}listaStrings"/>
 *         &lt;element name="Cuerpo" type="{CORREOS}listaStrings"/>
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
    "prddsc",
    "emlttl",
    "cco",
    "att",
    "dst",
    "cuerpo"
})
@XmlRootElement(name = "queueMail.Execute")
public class QueueMailExecute {

    @XmlElement(name = "Prddsc", required = true)
    protected String prddsc;
    @XmlElement(name = "Emlttl", required = true)
    protected String emlttl;
    @XmlElement(name = "Cco", required = true)
    protected ListaStrings cco;
    @XmlElement(name = "Att", required = true)
    protected ListaStrings att;
    @XmlElement(name = "Dst", required = true)
    protected ListaStrings dst;
    @XmlElement(name = "Cuerpo", required = true)
    protected ListaStrings cuerpo;

    /**
     * Obtiene el valor de la propiedad prddsc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrddsc() {
        return prddsc;
    }

    /**
     * Define el valor de la propiedad prddsc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrddsc(String value) {
        this.prddsc = value;
    }

    /**
     * Obtiene el valor de la propiedad emlttl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmlttl() {
        return emlttl;
    }

    /**
     * Define el valor de la propiedad emlttl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmlttl(String value) {
        this.emlttl = value;
    }

    /**
     * Obtiene el valor de la propiedad cco.
     * 
     * @return
     *     possible object is
     *     {@link ListaStrings }
     *     
     */
    public ListaStrings getCco() {
        return cco;
    }

    /**
     * Define el valor de la propiedad cco.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaStrings }
     *     
     */
    public void setCco(ListaStrings value) {
        this.cco = value;
    }

    /**
     * Obtiene el valor de la propiedad att.
     * 
     * @return
     *     possible object is
     *     {@link ListaStrings }
     *     
     */
    public ListaStrings getAtt() {
        return att;
    }

    /**
     * Define el valor de la propiedad att.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaStrings }
     *     
     */
    public void setAtt(ListaStrings value) {
        this.att = value;
    }

    /**
     * Obtiene el valor de la propiedad dst.
     * 
     * @return
     *     possible object is
     *     {@link ListaStrings }
     *     
     */
    public ListaStrings getDst() {
        return dst;
    }

    /**
     * Define el valor de la propiedad dst.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaStrings }
     *     
     */
    public void setDst(ListaStrings value) {
        this.dst = value;
    }

    /**
     * Obtiene el valor de la propiedad cuerpo.
     * 
     * @return
     *     possible object is
     *     {@link ListaStrings }
     *     
     */
    public ListaStrings getCuerpo() {
        return cuerpo;
    }

    /**
     * Define el valor de la propiedad cuerpo.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaStrings }
     *     
     */
    public void setCuerpo(ListaStrings value) {
        this.cuerpo = value;
    }

}
