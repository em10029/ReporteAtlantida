/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.envio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Arrays;
import static org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import reporte.atlantida.estructura.Reporte;
import reporte.atlantida.estructura.ReporteAtlantidaExcepcion;
import reporte.atlantida.fichero.Archivo;

/**
 * Envió de archivos a servidores FTP y FTPS.
 * @author Erick Fabricio Martínez Castellanos (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 01-nov-2018
 */
public class FTP {
    
    /**
     * Constructor privado, clase de comportamiento estático.
     */
    private FTP() {
    }

    /**
     * Determina el tipo de servidor (FTP o FTPS).
     * @param reporte
     * @throws ReporteAtlantidaExcepcion
     * @throws IOException 
     */
    public static void enviar(Reporte reporte) throws ReporteAtlantidaExcepcion, IOException {

        String server = reporte.getEmpresa().getUrl();

        if (server.contains(":")) {
            server = server.substring(0, server.indexOf(":")).trim().toUpperCase();
            switch (server) {
                case "FTP":
                    enviarFTP(reporte);
                    break;

                case "FTPS":
                    enviarFTPS(reporte);
                    break;

                default:
                    //ERROR
                    String error = "ERROR de envio, el tipo de servidor: " + server + " no es valido. \r\n";
                    error += "Verificar campo SAEDTA.SAEPA1.PA1FIN valor actual: " + reporte.getEmpresa().getUrl() + "\r\n";
                    error += "Tipos de servidores permitidos: FTP y FPTS \r\n";
                    error += "Ejemplo de campo correcto: ftps://150.150.10.103 \r\n";
                    error += "ID:" + reporte.toString() + "\r\n";
                    //System.out.println(error);                    
                    break;
            }
        } else {
            //ERROR
            String error = "ERROR de envio, no se pudo comprobar el tipo de servidor. \r\n";
            error += "Verificar campo SAEDTA.SAEPA1.PA1FIN valor actual: " + reporte.getEmpresa().getUrl() + "\r\n";
            error += "Tipos de servidores permitidos: FTP y FPTS \r\n";
            error += "Ejemplo de campo correcto: ftps://150.150.10.103 \r\n";
            error += "ID:" + reporte.toString() + "\r\n";
            //System.out.println(error);
        }
    }

    /**
     * Realiza la conversión correcta de la dirección IP. 
     * Ejem: ftp:\\127.0.0.1 --> 127.0.0.1
     * @param IP
     * @return String
     * @throws ReporteAtlantidaExcepcion 
     */
    public static String getIPFormato(String IP) throws ReporteAtlantidaExcepcion {

        String IPFormato = ""; //Contiene los octetos con el formato adecuado
        String[] separacion = IP.split("\\."); //Separacion de cadena

        for (String octeto : separacion) {
            octeto = octeto.replaceAll("[^0-9]", "");
            IPFormato += octeto + ".";
        }

        if (IPFormato.length() > 1) {//Elimina el ultimo punto(.)
            IPFormato = IPFormato.substring(0, IPFormato.length() - 1);
        } else {
            //ERROR        	
            throw new ReporteAtlantidaExcepcion("ERROR. Conversión IP, la dirección " + IP + " no es reconocida.");
        }

        return IPFormato;
    }

    /**
     * Convierte la dirección IP un arreglo de  bites.
     * @param IP
     * @return byte[]
     * @throws ReporteAtlantidaExcepcion 
     */
    public static byte[] getIP(String IP) throws ReporteAtlantidaExcepcion {

        byte[] direccionIP = new byte[4]; //Valor de retorno

        String[] octetos = getIPFormato(IP).trim().split("\\."); //Separacion de cadena en octetos

        if (octetos.length == 4) {
            direccionIP[0] = (byte) Integer.parseInt(octetos[0]);
            direccionIP[1] = (byte) Integer.parseInt(octetos[1]);
            direccionIP[2] = (byte) Integer.parseInt(octetos[2]);
            direccionIP[3] = (byte) Integer.parseInt(octetos[3]);
        } else {
            //ERROR        	
            throw new ReporteAtlantidaExcepcion("ERROR. Conversión IP, la dirección " + IP + " no es reconocida.");
        }

        return direccionIP;
    }

    /**
     * Envía los archivos a un servidor FTP.
     * @param reporte
     * @throws ReporteAtlantidaExcepcion
     * @throws IOException 
     */
    public static void enviarFTP(Reporte reporte) throws ReporteAtlantidaExcepcion, IOException {

        byte[] IP = getIP(reporte.getEmpresa().getUrl());

        FTPClient ftp = new FTPSClient();

        InetAddress conexion = InetAddress.getByAddress(IP);

        ftp.connect(conexion);

        boolean login = ftp.login(reporte.getEmpresa().getUser(), reporte.getEmpresa().getPassword());

        if (login) {

            //System.out.println("ENTRO LOGIN");  
            boolean carpeta = ftp.changeWorkingDirectory(reporte.getEmpresa().getDirectorio());//Cambiar directorio de server

            if (carpeta) {

                for (Archivo file : reporte.getArchivos()) {

                    File archivo = new File(file.getUbicacion());

                    if (archivo.exists()) {

                        InputStream inputStream = new FileInputStream(archivo); //Canal de envio

                        ftp.enterLocalPassiveMode(); //Modo pasido de server

                        //ftp.execPROT("P"); //Cifrado sobre TLS
                        boolean permite_archivo = ftp.setFileType(BINARY_FILE_TYPE); //Especifica el tipo de envio
                        //System.out.println("Tipo permitido: " + permite_archivo);

                        if (permite_archivo) {

                            boolean enviado = ftp.storeFile(archivo.getName(), inputStream);//Envio de archivo
                            //System.out.println("Enviado: " + enviado);

                            if (enviado) {
                                //System.out.println("Archivo " + archivo.getName() + " enviado satisfactoriamente.");
                                inputStream.close(); //Cierre de input	
                                file.setEnviado(true);
                            } else {
                                inputStream.close(); //Cierre de input
                                file.setEnviado(false);

                                int codigo = ftp.getReplyCode();
                                boolean respuestaCodigo = FTPReply.isPositiveCompletion(codigo);
                                String mensajeCodigo = ftp.getReplyString();

                                String error = "Codigo reply: " + codigo + "\n"
                                        + "Respuesta del codigo: " + respuestaCodigo + "\n"
                                        + "Mensaje del codigo: " + mensajeCodigo;

                                throw new ReporteAtlantidaExcepcion("ERROR. No se puedo enviar el archivo: " + archivo.getName() + " Detalle del error: \n" + error);
                            }
                        } else {
                            inputStream.close(); //Cierre de input
                            throw new ReporteAtlantidaExcepcion("ERROR. El servidor no admite el envio de archivos BINARY_FILE_TYPE");
                        }

                    } else {
                        throw new ReporteAtlantidaExcepcion("ERROR. El archivo: " + archivo.getName() + " no se encuentra en el directorio: " + reporte.getDirectorio().getUbicacion());
                    }
                }

            } else {
                //ERROR        	
                throw new ReporteAtlantidaExcepcion("ERROR. La carpeta: " + reporte.getEmpresa().getDirectorio() + " no existe.");
            }

            ftp.logout(); //Cierre de sesion
            ftp.disconnect();//Desconectarse del servidor            
        } else {
            //ERROR        	
            throw new ReporteAtlantidaExcepcion("ERROR. Credenciales invalidas.");
        }
    }

    /**
     * Envía los archivos a un servidor FTPS.
     * @param reporte
     * @throws ReporteAtlantidaExcepcion
     * @throws IOException 
     */
    public static void enviarFTPS(Reporte reporte) throws ReporteAtlantidaExcepcion, IOException {

        String info = "SERVIDOR FTPS \r\n";
        info += ">Server: " + reporte.getEmpresa().getUrl() + "\r\n";
        info += ">IP: " + getIPFormato(reporte.getEmpresa().getUrl()) + "\r\n";
        info += ">Conversión IP: " + Arrays.toString(getIP(reporte.getEmpresa().getUrl())) + "\r\n\r\n";

        byte[] IP = getIP(reporte.getEmpresa().getUrl());

        FTPSClient ftps = new FTPSClient();

        InetAddress conexion = InetAddress.getByAddress(IP);

        ftps.connect(conexion);

        boolean login = ftps.login(reporte.getEmpresa().getUser(), reporte.getEmpresa().getPassword());

        if (login) {

            //System.out.println("ENTRO LOGIN");  
            boolean carpeta = ftps.changeWorkingDirectory(reporte.getEmpresa().getDirectorio());//Cambiar directorio de server

            if (carpeta) {

                for (Archivo file : reporte.getArchivos()) {

                    File archivo = new File(file.getUbicacion());

                    if (archivo.exists()) {

                        InputStream inputStream = new FileInputStream(archivo); //Canal de envio

                        ftps.enterLocalPassiveMode(); //Modo pasido de server

                        ftps.execPROT("P"); //Cifrado sobre TLS

                        boolean permite_archivo = ftps.setFileType(BINARY_FILE_TYPE); //Especifica el tipo de envio
                        //System.out.println("Tipo permitido: " + permite_archivo);

                        if (permite_archivo) {

                            boolean enviado = ftps.storeFile(archivo.getName(), inputStream);//Envio de archivo
                            //System.out.println("Enviado: " + enviado);

                            if (enviado) {
                                info += "Archivo " + archivo.getName() + " enviado satisfactoriamente.\r\n";
                                //System.out.println("Archivo " + archivo.getName() + " enviado satisfactoriamente.");
                                inputStream.close(); //Cierre de input		
                                file.setEnviado(true);
                            } else {
                                info += "Archivo " + archivo.getName() + " no se pudo enviar.\r\n";
                                inputStream.close(); //Cierre de input
                                file.setEnviado(false);

                                int codigo = ftps.getReplyCode();
                                boolean respuestaCodigo = FTPReply.isPositiveCompletion(codigo);
                                String mensajeCodigo = ftps.getReplyString();

                                String error = "Codigo reply: " + codigo + "\n"
                                        + "Respuesta del codigo: " + respuestaCodigo + "\n"
                                        + "Mensaje del codigo: " + mensajeCodigo;

                                throw new ReporteAtlantidaExcepcion("ERROR. No se puedo enviar el archivo: " + archivo.getName() + " Detalle del error: \n" + error);
                            }
                        } else {
                            inputStream.close(); //Cierre de input
                            throw new ReporteAtlantidaExcepcion("ERROR. El servidor no admite el envio de archivos BINARY_FILE_TYPE");
                        }

                    } else {
                        throw new ReporteAtlantidaExcepcion("ERROR. El archivo: " + archivo.getName() + " no se encuentra en el directorio: " + reporte.getDirectorio().getUbicacion());
                    }
                }

                //reporte.setNotificacion(info);
            } else {
                //ERROR        	
                throw new ReporteAtlantidaExcepcion("ERROR. La carpeta: " + reporte.getEmpresa().getDirectorio() + " no existe.");
            }

            ftps.logout(); //Cierre de sesion
            ftps.disconnect();//Desconectarse del servidor            
        } else {
            //ERROR        	
            throw new ReporteAtlantidaExcepcion("ERROR. Credenciales invalidas.");
        }
    }

}
