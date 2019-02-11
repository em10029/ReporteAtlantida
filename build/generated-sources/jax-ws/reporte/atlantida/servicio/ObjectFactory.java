
package reporte.atlantida.servicio;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the reporte.atlantida.servicio package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: reporte.atlantida.servicio
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueueMailExecuteResponse }
     * 
     */
    public QueueMailExecuteResponse createQueueMailExecuteResponse() {
        return new QueueMailExecuteResponse();
    }

    /**
     * Create an instance of {@link QueueMailExecute }
     * 
     */
    public QueueMailExecute createQueueMailExecute() {
        return new QueueMailExecute();
    }

    /**
     * Create an instance of {@link ListaStrings }
     * 
     */
    public ListaStrings createListaStrings() {
        return new ListaStrings();
    }

    /**
     * Create an instance of {@link ListaStringsListaStringsItem }
     * 
     */
    public ListaStringsListaStringsItem createListaStringsListaStringsItem() {
        return new ListaStringsListaStringsItem();
    }

}
