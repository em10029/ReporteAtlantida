/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.utilitario;

import java.text.SimpleDateFormat;
import java.util.Date;
import reporte.atlantida.estructura.Reporte;
import reporte.atlantida.estructura.Servicio;
import reporte.atlantida.fichero.Archivo;

/**
 * Clase utilitario con metomos de uso común.
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 25-oct-2018
 */
public class Util {

    /**
     * Constructor privado, clase de comportamiento estático.
     */
    private Util() {
    }

    /**
     * Obtiene la fecha y la hora según formato requerido.
     *
     * @param formato: Formato de fecha que se desea. Ejemplo: DD/MM/YYYY ->
     * 10/11/1994
     * @return Fecha del sistema.
     */
    public static String getFechaHoraActual(String formato) {
        SimpleDateFormat fecha = new SimpleDateFormat(formato);
        Date fechaActual = new Date(System.currentTimeMillis());
        return fecha.format(fechaActual);
    }

    /**
     * Realiza un formato de fecha para una cadena de 8 caracteres.
     *
     * @param fecha: Cadena a la cual se le dará formato.
     * @param delimitador: Carácter con el cual será separado el día, mes y año.
     * @param formatoActual: Formato de la actual cadena. Ejemplo AAAAMMDD
     * @param formatoDestino: Formato de salida. Ejemplo DDMMAAAA
     * @return fechaFormato: Fecha formateada. Ejemplo de uso: ('20180619', '/',
     * 'AAAAMMDD', 'DDMMAAAA') --> 19/06/2018
     */
    public static String getFormatoFecha(String fecha, String delimitador, String formatoActual, String formatoDestino) {
        String fechaFormato = null;

        if (fecha.length() == 8) {
            String anio = null;
            String mes = null;
            String dia = null;

            //Obteniendo valores de fecha
            switch (formatoActual) {
                case "AAAAMMDD":
                    anio = fecha.substring(0, 4);
                    mes = fecha.substring(4, 6);
                    dia = fecha.substring(6, 8);
                    break;
                default:
                    break;
            }

            //Formateando fecha 
            switch (formatoDestino) {
                case "DDMMAAAA":
                    fechaFormato = dia + delimitador + mes + delimitador + anio;
                    break;
                case "AAAAMMDD":
                    fechaFormato = anio + delimitador + mes + delimitador + dia;
                    break;
                default:
                    break;
            }

        } else {
            fechaFormato = "01" + delimitador + "01" + delimitador + "1990";
        }

        return fechaFormato;
    }

    /**
     * Retorna el nombre del banco (verificar tabla de códigos de banco para
     * cheques).
     *
     * @param codigo
     * @return String nombre del banco.
     */
    public static String getBancoCheque(String codigo) {

        String banco = null;

        switch (codigo) {
            case "1":
                banco = "Banco Central de Honduras";
                break;
            case "2":
                banco = "Banco Atlántida";
                break;
            case "3":
                banco = "Continental";
                break;
            case "4":
                banco = "NO USADO";
                break;
            case "5":
                banco = "Banco de Londres";
                break;
            case "6":
                banco = "Banco de los Trabajadores";
                break;
            case "7":
                banco = "Banco de Occdente";
                break;
            case "8":
                banco = "Banco del Comercio";
                break;
            case "9":
                banco = "NO USADO";
                break;
            case "10":
                banco = "Banco Sogerin";
                break;
            case "11":
                banco = "Banffa";
                break;
            case "12":
                banco = "Banco Mercantil";
                break;
            case "13":
                banco = "Banco de Honduras";
                break;
            case "14":
                banco = "Banhcafe";
                break;
            case "15":
                banco = "Banco del País";
                break;
            case "16":
                banco = "Banco UNO";
                break;
            case "17":
                banco = "Banco Futuro";
                break;
            case "18":
                banco = "Ficensa";
                break;
            case "19":
                banco = "NO USADO";
                break;
            case "20":
                banco = "BanPro";
                break;
            case "21":
                banco = "NO USADO";
                break;
            case "22":
                banco = "NO USADO";
                break;
            case "23":
                banco = "NO USADO";
                break;
            case "24":
                banco = "Banco Credomatic";
                break;
            case "25":
                banco = "Banco Promérica";
                break;
            case "28":
                banco = "Ficohsa";
                break;
            case "29":
                banco = "NO USADO";
                break;
            case "30":
                banco = "Banco B.G.A.";
                break;
            case "51":
                banco = "Banadesa";
                break;
            default:
                banco = "N/A";
                break;
        }

        return banco;
    }

    /**
     * Reporta toda la información de la petición de reporte y muestra los
     * detalles de generación, envio y actualización.
     *
     * @param reporte
     * @return String Información del procesamiento.
     */
    public static String info(Reporte reporte) {

        String estado = reporte.getEstado();
        String version = reporte.getEmpresa().getVersion();
        String concepto = reporte.getEmpresa().getConcepto();
        String nivel = reporte.getEmpresa().getNivel();
        String generacion = reporte.getGeneracion();
        String contenido = reporte.getContenido();

        String destino = "";//reporte.getDestino();
        String envio = "";//reporte.getEmpresa().getTipoEnvio();
        String infoEnvio = "";

        //ESTADO
        switch (estado) {
            case "I":
                estado += " - Sin Procesar";
                break;
            case "A":
                estado += " - Procesado Exitoso";
                break;
            case "F":
                estado += " - Proceso Fallido";
                break;
        }

        //VERSION
        switch (version) {
            case "FORMATOV01":
                version += " - Ancho Fijo";
                break;
            case "FORMATOV02":
            case "FORMATOV03":
                version += " - Separación Pipe";
                break;
            case "FORMATOV04":
                version += " - Concepto detallado";
                if (concepto.equals("999") || concepto.equals("110")) {
                    concepto += " (.xlsx y .log)";
                } else if (concepto.equals("100")) {
                    concepto += " (.xlsx)";
                } else if (concepto.equals("10")) {
                    concepto += " (.log)";
                }
                break;
            case "FORMATOV05":
                version += " - Dinamico";
                break;
        }

        //NIVEL
        if (nivel.equals("NIVELEMP")) {
            nivel += " - Empresa";
        } else {
            nivel += " - Servicio";
        }

        //GENERACION
        switch (generacion) {
            case "M":
                generacion += " - Manual";
                break;
            case "A":
                generacion += " - Automatica";
                break;
            case "C":
                generacion += " - Cierre";
                break;
        }

        //CONTENIDO        
        if (reporte.getInformacion().equals("PAGOS")) {
            switch (contenido) {
                case "T":
                    contenido += " - Diario e Histórico";
                    break;
                case "D":
                    contenido += " - Diario";
                    break;
                case "H":
                    contenido += " - Histórico";
                    break;
            }
        } else { //SALDOS
            contenido = "Saldos";
        }

        //DESTINO Y ENVIO
        if (reporte.getDestino().equals("S")) { //S: Por definicion, puede ser COR o FTP
            if (reporte.getEmpresa().getTipoEnvio().equals("COR")) { //Correo electronico
                destino = "S - COR - Correo Electrónico";

                if (reporte.getEmpresa().getNivel().equals("NIVELEMP")) { //Empresa
                    envio = "Correos de la empresa y de los servicios";
                    infoEnvio += "Correos empresa: " + reporte.getEmpresa().getCorreos() + "\r\n";
                } else { //Servicios
                    envio = "Correos de los servicios";
                }

                //Correos de servicios
                for (Servicio servicio : reporte.getEmpresa().getServicios()) {
                    infoEnvio += "Correos servicio " + servicio.toString() + " : " + servicio.getCorreos() + "\r\n";
                }

                //Copias ocultas
                infoEnvio += "Copias ocultas (CCO): " + reporte.getEmpresa().getCopiasOcultas() + "\r\n";

            } else { //Servidor de archivos
                destino = "S - FTP - Servidor de archivos";
                envio = "Server";
                infoEnvio += "URL: " + reporte.getEmpresa().getUrl() + "\r\n";
                infoEnvio += "Directorio: " + reporte.getEmpresa().getDirectorio() + "\r\n";
                infoEnvio += "Código de envío: " + reporte.getEmpresa().getCodigoEnvio() + "\r\n";
            }
        } else { //N: Correo electronico, otros correos
            destino = "N - Correo Electrónico";
            envio = "Correos definidos";
            infoEnvio += "Correos: " + reporte.getCorreos() + "\r\n";
            //Copias ocultas
            infoEnvio += "Copias ocultas (CCO): " + reporte.getEmpresa().getCopiasOcultas() + "\r\n";
        }

        //INFORMACION
        String info = "";
        //Reporte Empresa
        info += "\r\n----------- # " + Reporte.contador + " -> " + reporte.toString() + " -----------\r\n";
        info += "\r\n------------>    INFO   <------------\r\n";
        info += "Estado...............> " + estado + " |Actualizar: " + reporte.getEmpresa().actualizar() + "\r\n";
        info += "Empresa..............> " + reporte.getEmpresa().getNombre() + "\r\n";
        info += "IBS..................> " + reporte.getEmpresa().toString() + "\r\n";
        info += "Información..........> " + reporte.getInformacion() + "\r\n";
        info += "Rango de fecha.......> " + reporte.getFechaInicial() + " - " + reporte.getFechaFinal() + "\r\n";
        info += "Concepto.............> " + concepto + "\r\n";
        info += "Versión..............> " + version + "\r\n";
        info += "Nivel................> " + nivel + "\r\n";
        info += "Generación...........> " + generacion + "\r\n";
        info += "Contenido............> " + contenido + "\r\n";
        info += "Destino..............> " + destino + "\r\n";
        info += "Tipo de envío........> " + envio + "\r\n";

        info += "\r\n------------> SERVICIOS <------------\r\n";
        //Servicio -> Transaccion
        info += "Servicios -> Descripción -> Transacciones -> Estado de envio\r\n";
        for (Servicio servicio : reporte.getEmpresa().getServicios()) {
            info += servicio + " -> " + servicio.getDescripcion() + " -> "
                    + servicio.getTransacciones();

            if (reporte.getInformacion().equals("PAGOS") && reporte.getContenido().equals("T")) {
                info += " D:" + servicio.getTransaccionesDiario() + " H:" + servicio.getTransaccionesHistorico();
            }

            info += " -> " + servicio.getEstadoEnvio() + " |Actualizar: " + servicio.actualizar() + "\r\n";

        }
        info += "Total de transacciones: " + reporte.getEmpresa().getTransacciones() + "\r\n";

        info += "\r\n------------> ARCHIVOS  <------------\r\n";
        //Directorio y Archivos
        info += "Directorio de archivos -> " + reporte.getDirectorio().getUbicacion() + "\r\n";
        info += "Total de archivos: " + reporte.getArchivos().size() + "\r\n\r\n";

        for (Archivo archivo : reporte.getArchivos()) {
            info += ">" + archivo.getNombre() + " |Generado: " + archivo.isGenerado() + " |Enviado: " + archivo.isEnviado() + "\r\n";
        }

        if (reporte.getInfoGeneracion().length() != 0) {
            info += "\r\n------------> GENERACION  <------------\r\n";
            info += reporte.getInfoGeneracion();
        }

        info += "\r\n------------> ENVIO  <------------\r\n";
        info += infoEnvio + "\r\n";
        info += reporte.getInfoEnvio();

        if (reporte.getInfoError().length() != 0) {
            info += "\r\n------------> ERROR  <------------\r\n";
            info += reporte.getInfoError();
        }

        info += "\r\n";

        return info;
    }

}
