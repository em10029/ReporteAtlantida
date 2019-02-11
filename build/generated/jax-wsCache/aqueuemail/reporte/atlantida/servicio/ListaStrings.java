
package reporte.atlantida.servicio;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para listaStrings complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="listaStrings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listaStrings.listaStringsItem" type="{CORREOS}listaStrings.listaStringsItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listaStrings", propOrder = {
    "listaStringsListaStringsItem"
})
public class ListaStrings {

    @XmlElement(name = "listaStrings.listaStringsItem")
    protected List<ListaStringsListaStringsItem> listaStringsListaStringsItem;

    /**
     * Gets the value of the listaStringsListaStringsItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaStringsListaStringsItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaStringsListaStringsItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ListaStringsListaStringsItem }
     * 
     * 
     */
    public List<ListaStringsListaStringsItem> getListaStringsListaStringsItem() {
        if (listaStringsListaStringsItem == null) {
            listaStringsListaStringsItem = new ArrayList<ListaStringsListaStringsItem>();
        }
        return this.listaStringsListaStringsItem;
    }

}
