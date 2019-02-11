/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.envio;

import java.util.ArrayList;
import java.util.Arrays;
import reporte.atlantida.estructura.Reporte;
import reporte.atlantida.estructura.Servicio;
import reporte.atlantida.fichero.Archivo;
import reporte.atlantida.servicio.ListaStrings;
import reporte.atlantida.servicio.ListaStringsListaStringsItem;
import reporte.atlantida.servicio.QueueMail;
import reporte.atlantida.servicio.QueueMailExecute;
import reporte.atlantida.servicio.QueueMailExecuteResponse;
import reporte.atlantida.servicio.QueueMailSoapPort;
import reporte.atlantida.utilitario.Configuracion;
import reporte.atlantida.utilitario.Util;

/**
 * Utilizada para envió de correos electrónicos.
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 Fecha: 04-23-2018
 */
public class SMTP {

    /**
     * Constructor privado, clase de comportamiento estático.
     */
    private SMTP() {
    }

    private static final byte NOTIFICACION_EXITOSA = 1; //DEV

    /**
     * Genera las notificaciones del reporte.
     *
     * @param reporte
     */
    public static void enviar(Reporte reporte) {

        String correos = "";
        String archivos = "";
        String infoEnvio = "";

        if (reporte.getDestino().equals("S")) { //S: Correos Definidos, se evalua el nivel para generar las notificaciones.

            if (reporte.getEmpresa().getNivel().equals("NIVELEMP")) { //Correos de la empresa y de los servicio, una solo notificacion

                correos = reporte.getEmpresa().getCorreos() + ","; //Correos de la empresa

                for (Servicio servicio : reporte.getEmpresa().getServicios()) { //Correos de los servicios                    
                    correos += servicio.getCorreos() + ",";
                }

                for (Archivo archivo : reporte.getArchivos()) { //Archivo que se generaron
                    archivos += archivo.getURL() + ",";
                }

                //Parametros de notificacion
                String prddsc = Configuracion.SERVICIO_PRODUCTO; //Producto que enviara la notificacion
                String emlttl = "Caja Empresarial " + reporte.getEmpresa().getNombre(); ////Titulo EMLDTA.EMLEMA
                ListaStrings dst = convertir(getLista(correos)); //Destinatarios
                ListaStrings cco = convertir(getLista(reporte.getEmpresa().getCopiasOcultas())); //Copias ocultas
                ListaStrings att = convertir(getLista(archivos)); //Documentos adjuntos
                ListaStrings bdy = convertir(generarCuerpo(reporte)); //Cuerpo

                //Prepara la notificacion
                QueueMailExecute notificacion = new QueueMailExecute();
                notificacion.setPrddsc(prddsc); //Producto EMLDTA.EMLPRD debe de estar activo
                notificacion.setEmlttl(emlttl);//Titulo EMLDTA.EMLEMA
                notificacion.setDst(dst);//Correos Destino EMLDTA.EMLDST
                notificacion.setCco(cco);//Correos Ocultos EMLDTA.EMLCCO                               
                notificacion.setAtt(att);//Documentos Adjunto EMLDTA.EMLATT               
                notificacion.setCuerpo(bdy);//Cuerpo EMLDTA.EMLBDY

                //LLamado al WS Notificaciones
                byte respuestaWS = execute(notificacion).getR();
                for (Archivo archivo : reporte.getArchivos()) {

                    if (respuestaWS == NOTIFICACION_EXITOSA) {
                        archivo.setEnviado(true);
                    } else {
                        archivo.setEnviado(false);
                    }

                }

                //Informacion de la notificacion
                infoEnvio = "Notificación generada a nivel de empresa\r\n\r\n";
                infoEnvio += "Destinatarios: " + Arrays.toString(getLista(correos).toArray()) + "\r\n";
                infoEnvio += "Copias Ocultas: " + Arrays.toString(getLista(reporte.getEmpresa().getCopiasOcultas()).toArray()) + "\r\n";
                infoEnvio += "Archivos: " + Arrays.toString(getLista(archivos).toArray()) + "\r\n";
                infoEnvio += "Cuerpo: " + generarCuerpo(reporte) + "\r\n";
                infoEnvio += "Respuesta WS: " + respuestaWS + " - " + getInformacion(respuestaWS) + "\r\n";

                //System.out.println(info);
            } else { //Correos de los servicios, notificaciones segun servicios.

                infoEnvio = "Notificaciónes generadas a nivel de servicios\r\n\r\n";

                for (Servicio servicio : reporte.getEmpresa().getServicios()) {

                    correos = servicio.getCorreos(); //Correos del servicio

                    for (Archivo archivo : servicio.getArchivos()) { //Archivo que se generaron
                        archivos += archivo.getURL() + ",";
                    }

                    //Parametros de notificacion
                    String prddsc = Configuracion.SERVICIO_PRODUCTO; //Producto que enviara la notificacion
                    String emlttl = "Caja Empresarial " + reporte.getEmpresa().getNombre(); ////Titulo EMLDTA.EMLEMA
                    ListaStrings dst = convertir(getLista(correos)); //Destinatarios
                    ListaStrings cco = convertir(getLista(reporte.getEmpresa().getCopiasOcultas())); //Copias ocultas
                    ListaStrings att = convertir(getLista(archivos)); //Documentos adjuntos
                    ListaStrings bdy = convertir(generarCuerpo(reporte)); //Cuerpo

                    //Prepara la notificacion
                    QueueMailExecute notificacion = new QueueMailExecute();
                    notificacion.setPrddsc(prddsc); //Producto EMLDTA.EMLPRD debe de estar activo
                    notificacion.setEmlttl(emlttl);//Titulo EMLDTA.EMLEMA
                    notificacion.setDst(dst);//Correos Destino EMLDTA.EMLDST
                    notificacion.setCco(cco);//Correos Ocultos EMLDTA.EMLCCO                               
                    notificacion.setAtt(att);//Documentos Adjunto EMLDTA.EMLATT               
                    notificacion.setCuerpo(bdy);//Cuerpo EMLDTA.EMLBDY

                    //LLamado al WS Notificaciones
                    byte respuestaWS = execute(notificacion).getR();
                    for (Archivo archivo : servicio.getArchivos()) {

                        if (respuestaWS == NOTIFICACION_EXITOSA) {
                            archivo.setEnviado(true);
                        } else {
                            archivo.setEnviado(false);
                        }

                    }

                    //Informacion de la notificacion                    
                    infoEnvio += "Notifición del servicio " + servicio.toString() + "\r\n";
                    infoEnvio += "Destinatarios: " + Arrays.toString(getLista(correos).toArray()) + "\r\n";
                    infoEnvio += "Copias Ocultas: " + Arrays.toString(getLista(reporte.getEmpresa().getCopiasOcultas()).toArray()) + "\r\n";
                    infoEnvio += "Archivos: " + Arrays.toString(getLista(archivos).toArray()) + "\r\n";
                    infoEnvio += "Cuerpo: " + generarCuerpo(reporte) + "\r\n";
                    infoEnvio += "Respuesta WS: " + respuestaWS + " - " + getInformacion(respuestaWS) + "\r\n\r\n";

                    archivos = "";

                }

            }

        } else { //N: Otros Correos.

            /**
             * Generando solo una notificacion
             */
            correos += reporte.getCorreos(); //Correos de los destinatarios

            for (Archivo archivo : reporte.getArchivos()) { //Archivo que se generaron
                archivos += archivo.getURL() + ",";
            }

            //Parametros de notificacion
            String prddsc = Configuracion.SERVICIO_PRODUCTO; //Producto que enviara la notificacion
            String emlttl = "Caja Empresarial " + reporte.getEmpresa().getNombre(); ////Titulo EMLDTA.EMLEMA
            ListaStrings dst = convertir(getLista(correos)); //Destinatarios
            ListaStrings cco = convertir(getLista(reporte.getEmpresa().getCopiasOcultas())); //Copias ocultas
            ListaStrings att = convertir(getLista(archivos)); //Documentos adjuntos
            ListaStrings bdy = convertir(generarCuerpo(reporte)); //Cuerpo

            //Prepara la notificacion
            QueueMailExecute notificacion = new QueueMailExecute();
            notificacion.setPrddsc(prddsc); //Producto EMLDTA.EMLPRD debe de estar activo
            notificacion.setEmlttl(emlttl);//Titulo EMLDTA.EMLEMA
            notificacion.setDst(dst);//Correos Destino EMLDTA.EMLDST
            notificacion.setCco(cco);//Correos Ocultos EMLDTA.EMLCCO                               
            notificacion.setAtt(att);//Documentos Adjunto EMLDTA.EMLATT               
            notificacion.setCuerpo(bdy);//Cuerpo EMLDTA.EMLBDY

            //LLamado al WS Notificaciones
            byte respuestaWS = execute(notificacion).getR();
            for (Archivo archivo : reporte.getArchivos()) {

                if (respuestaWS == NOTIFICACION_EXITOSA) {
                    archivo.setEnviado(true);
                } else {
                    archivo.setEnviado(false);
                }

            }

            //Informacion de la notificacion
            infoEnvio = "Notificación generada\r\n\r\n";
            infoEnvio += "Destinatarios: " + Arrays.toString(getLista(correos).toArray()) + "\r\n";
            infoEnvio += "Copias Ocultas: " + Arrays.toString(getLista(reporte.getEmpresa().getCopiasOcultas()).toArray()) + "\r\n";
            infoEnvio += "Archivos: " + Arrays.toString(getLista(archivos).toArray()) + "\r\n";
            infoEnvio += "Cuerpo: " + generarCuerpo(reporte) + "\r\n";
            infoEnvio += "Respuesta WS: " + respuestaWS + " - " + getInformacion(respuestaWS) + "\r\n";

        }

        reporte.setInfoEnvio(infoEnvio);
    }

    /**
     * LLamado al WS de notificaciones.
     *
     * @param parameters
     * @return QueueMailExecuteResponse
     */
    private static QueueMailExecuteResponse execute(QueueMailExecute parameters) {
        QueueMail service = new QueueMail();
        QueueMailSoapPort port = service.getQueueMailSoapPort();
        return port.execute(parameters);
    }

    /**
     * Retorna ArrayList con el split (,) de una lista y elimina la duplicidad.
     *
     * @param lista
     * @return ArrayList<String>
     */
    public static ArrayList<String> getLista(String lista) {

        ArrayList<String> array = new ArrayList<>();

        //Es para separar los correos por coma(,)        
        for (String cadena : lista.split(",")) {
            String item = cadena.replaceAll("\\s", ""); //eliminando espacios en blanco

            //Eliminar duplicidad de destinatarios y verifica que el item no este vacio
            if (!array.contains(item) && !item.isEmpty()) {
                array.add(item); //Agregando al array
            }
        }
        return array;
    }

    /**
     * ------------ POSIBLES VALORES DE RESPUESTA DEL WS ------------ 
     * 0 - Pendiente de envío 
     * 1 - Notificación Enviada 
     * 2 - Error de CCO 
     * 3 - Error de destinatario 
     * 4 - Copia Oculta/Destinatarios en lista negra 
     * 5 - Archivos adjuntos no encontrados 
     * 6 - Titulo vacío 
     * 7 - Notificación sin cuerpo 
     * 8 - Archivos adjuntos superan los 20MB 
     * 9 - Error general (Consultar log)
     *
     * @param respuestaSMTP
     * @return String
     */
    public static String getInformacion(byte respuestaSMTP) {
        String informacion;
        switch (respuestaSMTP) {
            case 0:
                informacion = "Pendiente de envío";
                break;
            case 1:
                informacion = "Notificación enviada";
                break;
            case 2:
                informacion = "Error de CCO";
                break;
            case 3:
                informacion = "Error de destinatario";
                break;
            case 4:
                informacion = "Copia Oculta/Destinatarios en lista negra";
                break;
            case 5:
                informacion = "Archivos adjuntos no encontrados";
                break;
            case 6:
                informacion = "Titulo vacío";
                break;
            case 7:
                informacion = "Notificación sin cuerpo";
                break;
            case 8:
                informacion = "Destinatarios vacio";
                break;
            case 9:
                informacion = "Error general (Consultar log)";
                break;
            default:
                informacion = "No hay registro del error";
                break;
        }

        return informacion;
    }

    /**
     * Convierte una cadena String en ListaStrings el cual es la estructura
     * aceptada por el WS. Se toma encuente el limite de salto 256 caracteres.
     *
     * @param cadena
     * @return ListaStrings
     */
    private static ListaStrings convertir(String cadena) {

        ListaStrings listaStrings = new ListaStrings();
        ListaStringsListaStringsItem item;

        //Verificaion de limite maximo de longitud
        int limite = 256;
        int caracteres = cadena.length();
        //int contador = 0;

        //System.out.println("Caracteres: " + caracteres);
        //System.out.println("Cadena: " + cadena);
        if (caracteres <= limite) {
            item = new ListaStringsListaStringsItem();
            item.setCadena(cadena);
            listaStrings.getListaStringsListaStringsItem().add(item);

            //contador++;
            //System.out.println(contador +"->"+ cadena);
        } else {
            while (cadena.length() > limite) {
                item = new ListaStringsListaStringsItem();
                item.setCadena(cadena.substring(0, limite));
                listaStrings.getListaStringsListaStringsItem().add(item);

                //contador++;
                //System.out.println(contador +"->"+cadena.substring(0, limite));
                cadena = cadena.substring(limite, cadena.length());
            }

            if (!cadena.isEmpty()) {
                item = new ListaStringsListaStringsItem();
                item.setCadena(cadena);
                listaStrings.getListaStringsListaStringsItem().add(item);
                //contador++;
                //System.out.println(contador +"->"+cadena);
            }
        }
        return listaStrings;
    }

    /**
     * Convierte un ArrayList<String> en ListaStrings el cual es la estructura
     * aceptada por el WS.
     *
     * @param arrayList
     * @return ListaStrings
     */
    private static ListaStrings convertir(ArrayList<String> arrayList) {
        ListaStrings listaStrings = new ListaStrings();

        for (String cadena : arrayList) {
            ListaStringsListaStringsItem item = new ListaStringsListaStringsItem();
            item.setCadena(cadena);
            listaStrings.getListaStringsListaStringsItem().add(item);
        }

        return listaStrings;
    }

    /**
     * Genera una cadena con formato HTML la cual contendra el contenido del
     * correo electronico.
     *
     * @param reporte
     * @return String
     */
    private static String generarCuerpo(Reporte reporte) {
        String cuerpo = "<blockquote align='center'><h2><strong><span style='color: #FF0000'>Caja Empresarial</span></strong></h2>";

        cuerpo += "Estimado cliente, se adjunta archivo con los " + reporte.getInformacion().toLowerCase() + " de la empresa: <b> " + reporte.getEmpresa().getNombre() + "</b> ";

        if (reporte.getFechaInicial().equals(reporte.getFechaFinal())) {
            cuerpo += "Registrados el " + Util.getFormatoFecha(reporte.getFechaInicial(), "-", "AAAAMMDD", "DDMMAAAA");
        } else {
            cuerpo += "Registrados del " + Util.getFormatoFecha(reporte.getFechaInicial(), "-", "AAAAMMDD", "DDMMAAAA") + " al " + Util.getFormatoFecha(reporte.getFechaFinal(), "-", "AAAAMMDD", "DDMMAAAA");
        }

        cuerpo += "<br><br>";

        cuerpo += "<b>Si desea más información comuníquese con: Service Centre Soporte Cash Management, <a href='mailto:servicecentre@bancatlan.hn'>servicecentre@bancatlan.hn</a> (504) 2280-0000 ext. 2158, 1227, 1223 </b>";

        cuerpo += "<br><br>";

        //cuerpo += "<img src='https://em10029.github.io/banco-atlantida/logo.png' width='50%' height='50%' align='center' alt='Banco Atlántida'></blockquote>";

        return cuerpo;
    }

}
