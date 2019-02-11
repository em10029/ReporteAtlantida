/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import reporte.atlantida.data.Conexion;
import reporte.atlantida.data.Data;
import reporte.atlantida.data.Query;
import reporte.atlantida.estructura.Dinamico;
import reporte.atlantida.estructura.Empresa;
import reporte.atlantida.estructura.Reporte;
import reporte.atlantida.estructura.Servicio;
import reporte.atlantida.fichero.Archivo;
import reporte.atlantida.fichero.ArchivoExcel;
import reporte.atlantida.fichero.ArchivoTextoPlano;
import reporte.atlantida.utilitario.Configuracion;
import reporte.atlantida.utilitario.Util;

/**
 * Clase encargada de generar los archivos de reportaría.
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 24-oct-2018
 */
public class Generacion {

    /**
     * Formato de trama para archivos de pagos con versión FORMATOV01.
     */
    private static final String FORMATO_PAGOS_ANCHO_FIJO = "%-8.8s%-2.2s%3.3s%-3.3s%-4.4s%9.9s%3.3s%-16.16s%-16.16s%-10.10s%2.2s%16.16s%10.10s%12.12s%12.12s_%-3.3s"; //16    

    /**
     * Formato de trama para archivos de pagos con versión FORMATOV02 y
     * FORMATOV03.
     */
    private static final String FORMATO_PAGOS_SEPARACION_PIPE = "%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s"; //30

    /**
     * Formato de trama para archivos de pagos con versión FORMATOV04.
     */
    private static final String FORMATO_PAGOS_DETALLADO = "%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s"; //29    

    /**
     * Formato de trama para archivos de saldos con versión FORMATOV01.
     */
    private static final String FORMATO_SALDOS_ANCHO_FIJO = "%-9.9s%-2.2s%-1.1s%-15.15s%-40.40s%16.16s%16.16s%10.10s%10.10s%10.10s%10.10s%10.10s"; //13

    /**
     * Formato de trama para archivos de saldos con versión FORMATOV02.
     */
    private static final String FORMATO_SALDOS_SEPARACION_PIPE = "%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s"; //24

    /**
     * Constructor privado, clase de comportamiento estático.
     */
    private Generacion() {
    }

    /**
     * Genera los archivos del reporte.
     *
     * @param conexion
     * @param reporte
     * @return boolean : True si la generación fue exitosa y False en caso
     * contrario.
     */
    public static boolean generar(Conexion conexion, Reporte reporte) {

        String nombreArchivo = "";
        boolean generacionExitosa = false;

        Empresa empresa = reporte.getEmpresa();
        ArrayList<Servicio> servicios = empresa.getServicios();
        int size = servicios.size();

        switch (reporte.getInformacion()) {

            case "PAGOS":

                switch (reporte.getEmpresa().getNivel()) {

                    case "NIVELEMP":

                        nombreArchivo = "c_temp_out_Pagos"
                                + reporte.getEmpresa().toString()
                                + "_S000_"
                                + reporte.getFechaInicial() + "-" + reporte.getFechaFinal();

                        switch (reporte.getEmpresa().getVersion()) {

                            case "FORMATOV01": //ANCHO FIJO

                                ArchivoTextoPlano archivoAnchoFijo = new ArchivoTextoPlano(reporte.getDirectorio(), nombreArchivo, ".log");

                                if (archivoAnchoFijo.crear()) {

                                    //NO CONTIENE ENCABEZADO
                                    //CUERPO
                                    for (Servicio servicio : reporte.getEmpresa().getServicios()) {
                                        switch (reporte.getContenido()) {
                                            case "T": //DIARIO E HISTORICO
                                                pagoAnchoFijo(conexion, Query.SELECT_PAGOS_ANCHO_FIJO_DIARIO, reporte, servicio, archivoAnchoFijo);
                                                pagoAnchoFijo(conexion, Query.SELECT_PAGOS_ANCHO_FIJO_HISTORICO, reporte, servicio, archivoAnchoFijo);
                                                break;
                                            case "D": //DIARIO
                                                pagoAnchoFijo(conexion, Query.SELECT_PAGOS_ANCHO_FIJO_DIARIO, reporte, servicio, archivoAnchoFijo);
                                                break;
                                            case "H": //HISTORICO
                                                pagoAnchoFijo(conexion, Query.SELECT_PAGOS_ANCHO_FIJO_HISTORICO, reporte, servicio, archivoAnchoFijo);
                                                break;
                                            default:
                                                break;
                                        }
                                        servicio.getArchivos().add(archivoAnchoFijo);
                                    }

                                    archivoAnchoFijo.cerrar();
                                    archivoAnchoFijo.setGenerado(true);
                                    reporte.getArchivos().add(archivoAnchoFijo);

                                } else {
                                    reporte.setInfoError("ERROR de fichero, no se pudo crear el archivo: " + archivoAnchoFijo.getNombre());
                                    archivoAnchoFijo.setGenerado(false);
                                }

                                break;

                            case "FORMATOV02": //SEPARACION PIPE
                            case "FORMATOV03": //ESPECIAL - DEFAULT

                                ArchivoTextoPlano archivoSeparacionPipe = new ArchivoTextoPlano(reporte.getDirectorio(), nombreArchivo, ".log");

                                if (archivoSeparacionPipe.crear()) {

                                    //ENCABEZADO
                                    generarEncabezadoEmpresa(reporte, archivoSeparacionPipe);

                                    //CUERPO
                                    for (Servicio servicio : reporte.getEmpresa().getServicios()) {
                                        switch (reporte.getContenido()) {
                                            case "T": //DIARIO E HISTORICO
                                                pagoSeparacionPipe(conexion, Query.SELECT_PAGOS_SEPARACION_PIPE_DIARIO, reporte, servicio, archivoSeparacionPipe);
                                                pagoSeparacionPipe(conexion, Query.SELECT_PAGOS_SEPARACION_PIPE_HISTORICO, reporte, servicio, archivoSeparacionPipe);
                                                break;
                                            case "D": //DIARIO
                                                pagoSeparacionPipe(conexion, Query.SELECT_PAGOS_SEPARACION_PIPE_DIARIO, reporte, servicio, archivoSeparacionPipe);
                                                break;
                                            case "H": //HISTORICO
                                                pagoSeparacionPipe(conexion, Query.SELECT_PAGOS_SEPARACION_PIPE_HISTORICO, reporte, servicio, archivoSeparacionPipe);
                                                break;
                                            default:
                                                break;
                                        }
                                        servicio.getArchivos().add(archivoSeparacionPipe);
                                    }

                                    archivoSeparacionPipe.cerrar();
                                    archivoSeparacionPipe.setGenerado(true);
                                    reporte.getArchivos().add(archivoSeparacionPipe);

                                } else {
                                    reporte.setInfoError("ERROR de fichero, no se pudo crear el archivo: " + archivoSeparacionPipe.getNombre());
                                    archivoSeparacionPipe.setGenerado(false);
                                }

                                break;

                            case "FORMATOV04": //DETALLADO

                                Data[] detalles = new Data[size]; //Utilizado para la informacion y el excel

                                //Informacion
                                reporte.setInfoGeneracion("*Conceptos: " + reporte.getEmpresa().getCantidadConcepto() + "\r\n");
                                for (int i = 0; i < size; i++) {
                                    detalles[i] = Data.getDetalle(reporte, servicios.get(i), true);
                                    reporte.setInfoGeneracion(Data.toString(reporte, servicios.get(i), detalles[i]));
                                }

                                String concepto = reporte.getEmpresa().getConcepto();

                                //Archivo .log separado por barras
                                if (concepto.equals("999") || concepto.equals("110") || concepto.equals("10")) {

                                    ArchivoTextoPlano archivoDetallado = new ArchivoTextoPlano(reporte.getDirectorio(), nombreArchivo, ".log");

                                    if (archivoDetallado.crear()) {

                                        //ENCABEZADO
                                        generarEncabezadoEmpresa(reporte, archivoDetallado);

                                        //CUERPO
                                        for (int i = 0; i < size; i++) {
                                            //DATA
                                            Data detalle = Data.getDetalle(reporte, servicios.get(i), false);

                                            switch (reporte.getContenido()) {
                                                case "T": //DIARIO E HISTORICO
                                                    reportar(conexion, detalle.getQueryDiario(), archivoDetallado, "|");
                                                    reportar(conexion, detalle.getQueryHistorico(), archivoDetallado, "|");
                                                    break;
                                                case "D": //DIARIO
                                                    reportar(conexion, detalle.getQueryDiario(), archivoDetallado, "|");
                                                    break;
                                                case "H": //HISTORICO
                                                    reportar(conexion, detalle.getQueryHistorico(), archivoDetallado, "|");
                                                    break;
                                                default:
                                                    break;
                                            }

                                            servicios.get(i).getArchivos().add(archivoDetallado);
                                            detalle = null;
                                        }

                                        archivoDetallado.cerrar();
                                        archivoDetallado.setGenerado(true);
                                        reporte.getArchivos().add(archivoDetallado);

                                    } else {
                                        reporte.setInfoError("ERROR de fichero, no se pudo crear el archivo: " + archivoDetallado.getNombre());
                                        archivoDetallado.setGenerado(false);
                                    }
                                }

                                //Archivo .xlsx
                                if (concepto.equals("999") || concepto.equals("110") || concepto.equals("100")) {

                                    ArchivoExcel archivoDetallado = new ArchivoExcel(reporte.getDirectorio(), nombreArchivo, ".xlsx");

                                    if (archivoDetallado.crear(Configuracion.PLANTILLA_EXCEL_DINAMICA)) {

                                        //ENCABEZADO
                                        archivoDetallado.agregarEncabezado(reporte.getEmpresa().getIdentificador(),
                                                reporte.getEmpresa().getTransacciones(),
                                                Util.getFechaHoraActual("dd/MM/YYYY"),
                                                Util.getFechaHoraActual("hh:mm:ss"));

                                        //TABLA
                                        archivoDetallado.agregarTabla(detalles[0].getEncabezado());

                                        //CUERPO                                        
                                        for (int i = 0; i < size; i++) {

                                            switch (reporte.getContenido()) {
                                                case "T": //DIARIO E HISTORICO
                                                    pagoDetallado(conexion, detalles[i].getQueryDiario(), archivoDetallado, detalles[i].getEstilos());
                                                    pagoDetallado(conexion, detalles[i].getQueryHistorico(), archivoDetallado, detalles[i].getEstilos());
                                                    break;
                                                case "D": //DIARIO
                                                    pagoDetallado(conexion, detalles[i].getQueryDiario(), archivoDetallado, detalles[i].getEstilos());
                                                    break;
                                                case "H": //HISTORICO
                                                    pagoDetallado(conexion, detalles[i].getQueryHistorico(), archivoDetallado, detalles[i].getEstilos());
                                                    break;
                                                default:
                                                    break;
                                            }

                                            servicios.get(i).getArchivos().add(archivoDetallado);
                                        }

                                        archivoDetallado.cerrar();
                                        archivoDetallado.setGenerado(true);
                                        reporte.getArchivos().add(archivoDetallado);

                                    } else {
                                        reporte.setInfoError("ERROR de fichero, no se pudo crear el archivo: " + archivoDetallado.getNombre());
                                        archivoDetallado.setGenerado(false);
                                    }
                                }

                                detalles = null;

                                break;

                            case "FORMATOV05": //DINAMICO

                                Dinamico dinamico = pagoDinamino(conexion, reporte);
                                //Informacion
                                reporte.setInfoGeneracion(dinamico.toString());
                                reporte.setInfoGeneracion("*Conceptos: " + reporte.getEmpresa().getCantidadConcepto() + "\r\n");

                                ArchivoTextoPlano archivoDinamico = new ArchivoTextoPlano(reporte.getDirectorio(), nombreArchivo, ".txt");

                                if (archivoDinamico.crear()) {

                                    //ENCABEZADO
                                    if (dinamico.getEncabezado().equals("A")) {
                                        generarEncabezadoEmpresa(reporte, archivoDinamico);
                                    }

                                    //CUERPO
                                    for (Servicio servicio : servicios) {

                                        //DATA
                                        Data data = Data.getDinamico(reporte, servicio, dinamico);
                                        reporte.setInfoGeneracion(Data.toString(reporte, servicio, data)); //Informacion

                                        switch (reporte.getContenido()) {
                                            case "T": //DIARIO E HISTORICO                                                
                                                reportar(conexion, data.getQueryDiario(), archivoDinamico, dinamico.getSeparador());
                                                reportar(conexion, data.getQueryHistorico(), archivoDinamico, dinamico.getSeparador());
                                                break;
                                            case "D": //DIARIO
                                                reportar(conexion, data.getQueryDiario(), archivoDinamico, dinamico.getSeparador());
                                                break;
                                            case "H": //HISTORICO
                                                reportar(conexion, data.getQueryHistorico(), archivoDinamico, dinamico.getSeparador());
                                                break;
                                            default:
                                                break;
                                        }
                                        servicio.getArchivos().add(archivoDinamico);
                                        data = null;
                                    }

                                    archivoDinamico.cerrar();
                                    archivoDinamico.setGenerado(true);
                                    reporte.getArchivos().add(archivoDinamico);

                                } else {
                                    reporte.setInfoError("ERROR de fichero, no se pudo crear el archivo: " + archivoDinamico.getNombre());
                                    archivoDinamico.setGenerado(false);
                                }

                                dinamico = null;
                                break;

                            default:
                                break;

                        }

                        break;

                    case "NIVELSER":

                        switch (reporte.getEmpresa().getVersion()) {

                            case "FORMATOV01": //ANCHO FIJO

                                for (Servicio servicio : reporte.getEmpresa().getServicios()) {

                                    nombreArchivo = "c_temp_out_Pagos"
                                            + reporte.getEmpresa().toString()
                                            + "_S" + servicio.toString() + "_"
                                            + reporte.getFechaInicial() + "-" + reporte.getFechaFinal();

                                    ArchivoTextoPlano archivoAnchoFijo = new ArchivoTextoPlano(reporte.getDirectorio(), nombreArchivo, ".log");

                                    if (archivoAnchoFijo.crear()) {

                                        //NO CONTIENE ENCABEZADO
                                        //CUERPO
                                        switch (reporte.getContenido()) {
                                            case "T": //DIARIO E HISTORICO
                                                pagoAnchoFijo(conexion, Query.SELECT_PAGOS_ANCHO_FIJO_DIARIO, reporte, servicio, archivoAnchoFijo);
                                                pagoAnchoFijo(conexion, Query.SELECT_PAGOS_ANCHO_FIJO_HISTORICO, reporte, servicio, archivoAnchoFijo);
                                                break;
                                            case "D": //DIARIO
                                                pagoAnchoFijo(conexion, Query.SELECT_PAGOS_ANCHO_FIJO_DIARIO, reporte, servicio, archivoAnchoFijo);
                                                break;
                                            case "H": //HISTORICO
                                                pagoAnchoFijo(conexion, Query.SELECT_PAGOS_ANCHO_FIJO_HISTORICO, reporte, servicio, archivoAnchoFijo);
                                                break;
                                            default:
                                                break;
                                        }

                                        archivoAnchoFijo.cerrar();
                                        archivoAnchoFijo.setGenerado(true);
                                        servicio.getArchivos().add(archivoAnchoFijo);
                                        reporte.getArchivos().add(archivoAnchoFijo);

                                    } else {
                                        reporte.setInfoError("ERROR de fichero, no se pudo crear el archivo: " + archivoAnchoFijo.getNombre());
                                        archivoAnchoFijo.setGenerado(false);
                                    }
                                }

                                break;

                            case "FORMATOV02": //SEPARACION PIPE
                            case "FORMATOV03": //ESPECIAL - DEFAULT

                                for (Servicio servicio : reporte.getEmpresa().getServicios()) {

                                    nombreArchivo = "c_temp_out_Pagos"
                                            + reporte.getEmpresa().toString()
                                            + "_S" + servicio.toString() + "_"
                                            + reporte.getFechaInicial() + "-" + reporte.getFechaFinal();

                                    ArchivoTextoPlano archivoSeparacionPipe = new ArchivoTextoPlano(reporte.getDirectorio(), nombreArchivo, ".log");

                                    if (archivoSeparacionPipe.crear()) {

                                        //ENCABEZADO
                                        generarEncabezadoServicio(reporte, servicio, archivoSeparacionPipe);

                                        //CUERPO
                                        switch (reporte.getContenido()) {
                                            case "T": //DIARIO E HISTORICO
                                                pagoSeparacionPipe(conexion, Query.SELECT_PAGOS_SEPARACION_PIPE_DIARIO, reporte, servicio, archivoSeparacionPipe);
                                                pagoSeparacionPipe(conexion, Query.SELECT_PAGOS_SEPARACION_PIPE_HISTORICO, reporte, servicio, archivoSeparacionPipe);
                                                break;
                                            case "D": //DIARIO
                                                pagoSeparacionPipe(conexion, Query.SELECT_PAGOS_SEPARACION_PIPE_DIARIO, reporte, servicio, archivoSeparacionPipe);
                                                break;
                                            case "H": //HISTORICO
                                                pagoSeparacionPipe(conexion, Query.SELECT_PAGOS_SEPARACION_PIPE_HISTORICO, reporte, servicio, archivoSeparacionPipe);
                                                break;
                                            default:
                                                break;
                                        }

                                        archivoSeparacionPipe.cerrar();
                                        archivoSeparacionPipe.setGenerado(true);
                                        servicio.getArchivos().add(archivoSeparacionPipe);
                                        reporte.getArchivos().add(archivoSeparacionPipe);

                                    } else {
                                        reporte.setInfoError("ERROR de fichero, no se pudo crear el archivo: " + archivoSeparacionPipe.getNombre());
                                        archivoSeparacionPipe.setGenerado(false);
                                    }
                                }

                                break;

                            case "FORMATOV04": //DETALLADO

                                String concepto = reporte.getEmpresa().getConcepto();
                                Data[] detalles = new Data[size]; //Utilizado para la informacion y el excel

                                //Informacion                                
                                for (int i = 0; i < size; i++) {
                                    detalles[i] = Data.getDetalle(reporte, servicios.get(i), true);
                                    reporte.setInfoGeneracion(Data.toString(reporte, servicios.get(i), detalles[i]));
                                }

                                //Archivo .log
                                if (concepto.equals("999") || concepto.equals("110") || concepto.equals("10")) {

                                    for (int i = 0; i < size; i++) {

                                        nombreArchivo = "c_temp_out_Pagos"
                                                + reporte.getEmpresa().toString()
                                                + "_S" + servicios.get(i).toString() + "_"
                                                + reporte.getFechaInicial() + "-" + reporte.getFechaFinal();

                                        ArchivoTextoPlano archivoDetallado = new ArchivoTextoPlano(reporte.getDirectorio(), nombreArchivo, ".log");

                                        if (archivoDetallado.crear()) {
                                            //DATA
                                            Data detalle = Data.getDetalle(reporte, servicios.get(i), false);

                                            //ENCABEZADO
                                            generarEncabezadoServicio(reporte, servicios.get(i), archivoDetallado);

                                            //CUERPO                                            
                                            switch (reporte.getContenido()) {
                                                case "T": //DIARIO E HISTORICO
                                                    reportar(conexion, detalle.getQueryDiario(), archivoDetallado, "|");
                                                    reportar(conexion, detalle.getQueryHistorico(), archivoDetallado, "|");
                                                    break;
                                                case "D": //DIARIO
                                                    reportar(conexion, detalle.getQueryDiario(), archivoDetallado, "|");
                                                    break;
                                                case "H": //HISTORICO
                                                    reportar(conexion, detalle.getQueryHistorico(), archivoDetallado, "|");
                                                    break;
                                                default:
                                                    break;
                                            }

                                            archivoDetallado.cerrar();
                                            archivoDetallado.setGenerado(true);
                                            servicios.get(i).getArchivos().add(archivoDetallado);
                                            reporte.getArchivos().add(archivoDetallado);

                                            detalle = null;

                                        } else {
                                            reporte.setInfoError("ERROR de fichero, no se pudo crear el archivo: " + archivoDetallado.getNombre());
                                            archivoDetallado.setGenerado(false);
                                        }
                                    }
                                }

                                //Archivo .xlsx
                                if (concepto.equals("999") || concepto.equals("110") || concepto.equals("100")) {

                                    for (int i = 0; i < size; i++) {

                                        nombreArchivo = "c_temp_out_Pagos"
                                                + reporte.getEmpresa().toString()
                                                + "_S" + servicios.get(i).toString() + "_"
                                                + reporte.getFechaInicial() + "-" + reporte.getFechaFinal();

                                        ArchivoExcel archivoDetallado = new ArchivoExcel(reporte.getDirectorio(), nombreArchivo, ".xlsx");

                                        if (archivoDetallado.crear(Configuracion.PLANTILLA_EXCEL_DINAMICA)) {
                                            //ENCABEZADO
                                            archivoDetallado.agregarEncabezado(reporte.getEmpresa().getIdentificador(),
                                                    servicios.get(i).getTransacciones(),
                                                    Util.getFechaHoraActual("dd/MM/YYYY"),
                                                    Util.getFechaHoraActual("hh:mm:ss"));

                                            //TABLA
                                            archivoDetallado.agregarTabla(detalles[i].getEncabezado());

                                            //CUERPO                                        
                                            switch (reporte.getContenido()) {
                                                case "T": //DIARIO E HISTORICO
                                                    pagoDetallado(conexion, detalles[i].getQueryDiario(), archivoDetallado, detalles[i].getEstilos());
                                                    pagoDetallado(conexion, detalles[i].getQueryHistorico(), archivoDetallado, detalles[i].getEstilos());
                                                    break;
                                                case "D": //DIARIO
                                                    pagoDetallado(conexion, detalles[i].getQueryDiario(), archivoDetallado, detalles[i].getEstilos());
                                                    break;
                                                case "H": //HISTORICO
                                                    pagoDetallado(conexion, detalles[i].getQueryHistorico(), archivoDetallado, detalles[i].getEstilos());
                                                    break;
                                                default:
                                                    break;
                                            }

                                            archivoDetallado.cerrar();
                                            archivoDetallado.setGenerado(true);
                                            servicios.get(i).getArchivos().add(archivoDetallado);
                                            reporte.getArchivos().add(archivoDetallado);

                                        } else {
                                            reporte.setInfoError("ERROR de fichero, no se pudo crear el archivo: " + archivoDetallado.getNombre());
                                            archivoDetallado.setGenerado(false);
                                        }

                                    }
                                }

                                detalles = null;

                                break;

                            case "FORMATOV05": //DINAMICO

                                Dinamico dinamico = pagoDinamino(conexion, reporte);
                                reporte.setInfoGeneracion(dinamico.toString()); //Informacion

                                for (Servicio servicio : reporte.getEmpresa().getServicios()) {

                                    nombreArchivo = "c_temp_out_Pagos"
                                            + reporte.getEmpresa().toString()
                                            + "_S" + servicio.toString() + "_"
                                            + reporte.getFechaInicial() + "-" + reporte.getFechaFinal();

                                    ArchivoTextoPlano archivoDinamico = new ArchivoTextoPlano(reporte.getDirectorio(), nombreArchivo, ".txt");

                                    if (archivoDinamico.crear()) {

                                        //DATA
                                        Data data = Data.getDinamico(reporte, servicio, dinamico);
                                        reporte.setInfoGeneracion(Data.toString(reporte, servicio, data)); //Informacion

                                        //ENCABEZADO
                                        if (dinamico.getEncabezado().equals("A")) {
                                            generarEncabezadoServicio(reporte, servicio, archivoDinamico);
                                        }

                                        //CUERPO
                                        switch (reporte.getContenido()) {
                                            case "T": //DIARIO E HISTORICO
                                                reportar(conexion, data.getQueryDiario(), archivoDinamico, dinamico.getSeparador());
                                                reportar(conexion, data.getQueryHistorico(), archivoDinamico, dinamico.getSeparador());
                                                break;
                                            case "D": //DIARIO
                                                reportar(conexion, data.getQueryDiario(), archivoDinamico, dinamico.getSeparador());
                                                break;
                                            case "H": //HISTORICO
                                                reportar(conexion, data.getQueryHistorico(), archivoDinamico, dinamico.getSeparador());
                                                break;
                                            default:
                                                break;
                                        }

                                        archivoDinamico.cerrar();
                                        archivoDinamico.setGenerado(true);
                                        servicio.getArchivos().add(archivoDinamico);
                                        reporte.getArchivos().add(archivoDinamico);

                                        data = null;

                                    } else {
                                        reporte.setInfoError("ERROR de fichero, no se pudo crear el archivo: " + archivoDinamico.getNombre());
                                        archivoDinamico.setGenerado(false);
                                    }
                                }

                                dinamico = null;
                                break;

                            default:
                                break;

                        }

                        break;

                    default:
                        break;
                }

                break;

            case "SALDOS":

                switch (reporte.getEmpresa().getNivel()) {

                    case "NIVELEMP":

                        nombreArchivo = "Saldos"
                                + reporte.getEmpresa().toString()
                                + "_S000_"
                                + reporte.getFechaInicial() + "-" + reporte.getFechaFinal();

                        switch (reporte.getEmpresa().getVersion()) {

                            case "FORMATOV01": //ANCHO FIJO

                                ArchivoTextoPlano archivoAnchoFijo = new ArchivoTextoPlano(reporte.getDirectorio(), nombreArchivo, ".log");

                                if (archivoAnchoFijo.crear()) {

                                    //NO CONTIENE ENCABEZADO
                                    //CUERPO
                                    for (Servicio servicio : reporte.getEmpresa().getServicios()) {
                                        saldoAnchoFijo(conexion, Query.SELECT_SALDOS_ANCHO_FIJO, reporte, servicio, archivoAnchoFijo);
                                        servicio.getArchivos().add(archivoAnchoFijo);
                                    }

                                    archivoAnchoFijo.cerrar();
                                    archivoAnchoFijo.setGenerado(true);
                                    reporte.getArchivos().add(archivoAnchoFijo);

                                } else {
                                    reporte.setInfoError("ERROR de fichero, no se pudo crear el archivo: " + archivoAnchoFijo.getNombre());
                                    archivoAnchoFijo.setGenerado(false);
                                }

                                break;

                            case "FORMATOV02": //SEPARACION PIPE
                            case "FORMATOV03": //ESPECIAL - DEFAULT

                                ArchivoTextoPlano archivoSeparacionPipe = new ArchivoTextoPlano(reporte.getDirectorio(), nombreArchivo, ".log");

                                if (archivoSeparacionPipe.crear()) {

                                    //ENCABEZADO
                                    generarEncabezadoEmpresa(reporte, archivoSeparacionPipe);

                                    //CUERPO
                                    for (Servicio servicio : reporte.getEmpresa().getServicios()) {
                                        saldoSeparacionPipe(conexion, Query.SELECT_SALDOS_SEPARACION_PIPE, reporte, servicio, archivoSeparacionPipe);
                                        servicio.getArchivos().add(archivoSeparacionPipe);
                                    }

                                    archivoSeparacionPipe.cerrar();
                                    archivoSeparacionPipe.setGenerado(true);
                                    reporte.getArchivos().add(archivoSeparacionPipe);

                                } else {
                                    reporte.setInfoError("ERROR de fichero, no se pudo crear el archivo: " + archivoSeparacionPipe.getNombre());
                                    archivoSeparacionPipe.setGenerado(false);
                                }

                                break;

                            default:
                                break;
                        }

                        break;

                    case "NIVELSER":

                        switch (reporte.getEmpresa().getVersion()) {

                            case "FORMATOV01": //ANCHO FIJO

                                for (Servicio servicio : reporte.getEmpresa().getServicios()) {

                                    nombreArchivo = "Saldos"
                                            + reporte.getEmpresa().toString()
                                            + "_S" + servicio.toString() + "_"
                                            + reporte.getFechaInicial() + "-" + reporte.getFechaFinal();

                                    ArchivoTextoPlano archivoAnchoFijo = new ArchivoTextoPlano(reporte.getDirectorio(), nombreArchivo, ".log");

                                    if (archivoAnchoFijo.crear()) {

                                        //NO CONTIENE ENCABEZADO
                                        //CUERPO                                                                                
                                        saldoAnchoFijo(conexion, Query.SELECT_SALDOS_ANCHO_FIJO, reporte, servicio, archivoAnchoFijo);

                                        archivoAnchoFijo.cerrar();
                                        archivoAnchoFijo.setGenerado(true);
                                        servicio.getArchivos().add(archivoAnchoFijo);
                                        reporte.getArchivos().add(archivoAnchoFijo);

                                    } else {
                                        reporte.setInfoError("ERROR de fichero, no se pudo crear el archivo: " + archivoAnchoFijo.getNombre());
                                        archivoAnchoFijo.setGenerado(false);
                                    }
                                }

                                break;

                            case "FORMATOV02": //SEPARACION PIPE
                            case "FORMATOV03": //ESPECIAL - DEFAULT

                                for (Servicio servicio : reporte.getEmpresa().getServicios()) {

                                    nombreArchivo = "Saldos"
                                            + reporte.getEmpresa().toString()
                                            + "_S" + servicio.toString() + "_"
                                            + reporte.getFechaInicial() + "-" + reporte.getFechaFinal();

                                    ArchivoTextoPlano archivoSeparacionPipe = new ArchivoTextoPlano(reporte.getDirectorio(), nombreArchivo, ".log");

                                    if (archivoSeparacionPipe.crear()) {

                                        //ENCABEZADO
                                        generarEncabezadoServicio(reporte, servicio, archivoSeparacionPipe);

                                        //CUERPO                                                                                
                                        saldoSeparacionPipe(conexion, Query.SELECT_SALDOS_SEPARACION_PIPE, reporte, servicio, archivoSeparacionPipe);

                                        archivoSeparacionPipe.cerrar();
                                        archivoSeparacionPipe.setGenerado(true);
                                        servicio.getArchivos().add(archivoSeparacionPipe);
                                        reporte.getArchivos().add(archivoSeparacionPipe);

                                    } else {
                                        reporte.setInfoError("ERROR de fichero, no se pudo crear el archivo: " + archivoSeparacionPipe.getNombre());
                                        archivoSeparacionPipe.setGenerado(false);
                                    }
                                }

                                break;

                            default:
                                break;
                        }

                        break;

                    default:
                        break;
                }

                break;

            default:
                break;
        }

        //Vericando estado de generacion
        if (reporte.getArchivos().size() > 0) {
            for (Archivo archivo : reporte.getArchivos()) {
                if (archivo.isGenerado()) {
                    generacionExitosa = true;
                } else {
                    generacionExitosa = false;
                    break;
                }
            }
        } else {
            reporte.setInfoError("No se genero ningún archivo");
            generacionExitosa = false;
        }

        return generacionExitosa;
    }

    /**
     * Generación de reportes generales.
     *
     * @param conexion
     * @param query
     * @param archivo
     * @param delimitador
     */
    private static void reportar(Conexion conexion, String query, ArchivoTextoPlano archivo, String delimitador) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String linea = "";

        try {
            ps = conexion.getConexion().prepareStatement(query);
            int campos = ps.getMetaData().getColumnCount();
            rs = ps.executeQuery();

            while (rs.next()) {

                for (int i = 0; i < campos; i++) {
                    linea += rs.getString(i + 1).trim() + delimitador;
                }

                //Elimina el ultimo separador
                linea = linea.substring(0, linea.length() - 1);

                archivo.agregarLinea(linea);
                linea = "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Generacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Query.cerrar(rs);
            Query.cerrar(ps);
        }
    }

    /**
     * Generación de reportes de pagos con versión FORMATOV01 - Ancho Fijo.
     *
     * @param conexion
     * @param query
     * @param reporte
     * @param servicio
     * @param archivo
     */
    private static void pagoAnchoFijo(Conexion conexion, String query, Reporte reporte, Servicio servicio, ArchivoTextoPlano archivo) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String linea = "";

        try {
            ps = conexion.getConexion().prepareStatement(query);
            ps.setString(1, reporte.getEmpresa().getIdentificador());
            ps.setString(2, servicio.getIdentificador());
            ps.setString(3, reporte.getFechaInicial());
            ps.setString(4, reporte.getFechaFinal());
            rs = ps.executeQuery();

            while (rs.next()) {

                linea = String.format(FORMATO_PAGOS_ANCHO_FIJO,
                        rs.getString(1).trim(), //1-CORRELATIVO DE PAGO - TRDNUM, TRHNUM
                        "01", //2-CODIGO BANCO ATLANTIDA
                        rs.getString(2).trim(), //3-SUCURSAL - TRDSUC, TRHSUC
                        rs.getString(3).trim(), //4-AGENCIA - AGECOD, AGECOD
                        rs.getString(4).trim(), //5-CAJERO - TRDREA, TRHREA
                        reporte.getEmpresa().getIdentificador(), //6-CLIENTE IBS - CEAEMP
                        servicio.getIdentificador(), //7-CODIGO DE SERVICIO - CEASER
                        rs.getString(5).trim(), //8-REFERENCIA1 - TRDID1, TRHID1
                        rs.getString(6).trim(), //9-REFERENCIA2 - TRDID2, TRHID2
                        rs.getString(7).trim(), //10-FECHA DE PAGO - TRDREF, TRHREF
                        rs.getString(8).trim(), //11-BANCO DEL CHEQUE - TRDRECB, TRHRECB
                        rs.getString(9).trim(), //12-CUENTA DEL CHEQUE - TRDRECN, TRHRECN
                        rs.getString(10).trim(), //13-VALOR DEL CHEQUE - TRDREC, TRHREC
                        rs.getString(11).trim(), //14-VALOR EN EFECTIVO - TRDREE, TRHREE
                        rs.getString(12).trim(), //15-TOTAL RECIBIDO - TRDRET, TRHRET
                        rs.getString(13).trim()); //16-UTILITARIO (MONEDA) - TRDREM, TRHREM

                archivo.agregarLinea(linea);
                linea = "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Generacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Query.cerrar(rs);
            Query.cerrar(ps);
        }
    }

    /**
     * Generación de reportes de pagos con versión FORMATOV02 y FORMATOV03 -
     * Separación Pipe (|).
     *
     * @param conexion
     * @param query
     * @param reporte
     * @param servicio
     * @param archivo
     */
    private static void pagoSeparacionPipe(Conexion conexion, String query, Reporte reporte, Servicio servicio, ArchivoTextoPlano archivo) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String linea = "";

        try {
            ps = conexion.getConexion().prepareStatement(query);
            ps.setString(1, reporte.getEmpresa().getIdentificador());
            ps.setString(2, servicio.getIdentificador());
            ps.setString(3, reporte.getFechaInicial());
            ps.setString(4, reporte.getFechaFinal());
            rs = ps.executeQuery();

            while (rs.next()) {

                linea = String.format(FORMATO_PAGOS_SEPARACION_PIPE,
                        rs.getString(1).trim(), //1-ACCION                         
                        rs.getString(2).trim(), //2-NUMERO DE RECIBO
                        servicio.getIdentificador(), //3- NUMERO DE SERVICIO
                        rs.getString(3).trim(), //4-IDENTIFICADOR1
                        rs.getString(4).trim(), //5-IDENTIFICADOR2
                        rs.getString(5).trim(), //6-IDENTIFICADOR3
                        rs.getString(6).trim(), //7-MONEDA DE PAGO
                        rs.getString(7).trim(), //8-FECHA DE PAGO
                        "", //9-CAMPO ESPECIAL
                        rs.getString(8).trim(), //10-HORA DE PAGO
                        rs.getString(9).trim(), //11-TOTAL RECAUDADO
                        rs.getString(10).trim(), //12-RECAUDO EN EFECTIVO
                        rs.getString(11).trim(), //13-RECAUDO EN CHEQUE
                        rs.getString(12).trim(), //14-BANCO DEL CHEQUE CODIGO
                        Util.getBancoCheque(rs.getString(12)), //15-BANCO DEL CHEQUE DESCRIPCION
                        rs.getString(13).trim(), //16-NUMERO DE CHEQUE
                        rs.getString(14).trim(), //17-AGENCIA DONDE SE RECAUDO
                        rs.getString(15).trim(), //18-CAJA DEL PAGO
                        rs.getString(16).trim(), //19-CAJERO QUE RECIBIO EL PAGO
                        rs.getString(17).trim(), //20-INFO. DE SALDO FECHA DEL SALDO
                        rs.getString(18).trim(), //21-INFO. DE SALDO HORA DEL SALDO
                        rs.getString(19).trim(), //22-INFO. DE SALDO TOTAL A COBRAR
                        rs.getString(20).trim(), //23-NOMBRE DE CLIENTE
                        rs.getString(21).trim(), //24-NUMERO ID/RTN
                        rs.getString(22).trim(), //25-TAS DE CAMBIO
                        "", //26-CAMPO ESPECIAL DE PAGO 1
                        "", //27-CAMPO ESPECIAL DE PAGO 2
                        "", //28-CAMPO ESPECIAL DE PAGO 3
                        "", //29-CAMPO ESPECIAL DE PAGO 4
                        ""); //30-CAMPO ESPECIAL DE PAGO 5

                archivo.agregarLinea(linea);
                linea = "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Generacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Query.cerrar(rs);
            Query.cerrar(ps);
        }
    }

    /**
     * Generación de reportes de pagos con versión FORMATOV04 - Detallado.
     *
     * @param conexion
     * @param query
     * @param archivo
     * @param estilo
     */
    private static void pagoDetallado(Conexion conexion, String query, ArchivoExcel archivo, ArrayList estilo) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int campos = estilo.size();
        String[] linea = new String[campos];

        try {
            ps = conexion.getConexion().prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                for (int i = 0; i < campos; i++) {
                    linea[i] = rs.getString(i + 1).trim();
                }
                archivo.agregarLinea(linea, estilo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Generacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Query.cerrar(rs);
            Query.cerrar(ps);
        }
    }

    /**
     * Generación de reportes de pagos con versión FORMATOV05 - Dinamico.
     *
     * @param conexion
     * @param reporte
     * @return
     */
    private static Dinamico pagoDinamino(Conexion conexion, Reporte reporte) {

        Dinamico dinamico = new Dinamico();
        PreparedStatement ps = null;
        ResultSet rs = null;

        /**
         * *********** OPCIONES DE EMPRESA ************
         */
        try {
            ps = conexion.getConexion().prepareStatement(Query.SELECT_OPCIONES);
            ps.setString(1, reporte.getEmpresa().getIdentificador());
            rs = ps.executeQuery();
            if (rs.next()) {
                dinamico.setSeparador(rs.getString("OPCSEP").trim()); //Separador
                dinamico.setEncabezado(rs.getString("OPCENC").trim()); //Encabezado (A:Activo, I:Inactivo)
                dinamico.setFormatoFecha(rs.getString("OPCFOR").trim().equals("E") ? "DDMMAAAA" : "AAAAMMDD"); //Formato de fecha (E:DD/MM/AAAA , I:AAAA/MM/DD)
                dinamico.setConceptos(rs.getString("OPCCOC").trim()); //Conceptos (A:Activo, I:Inactivo)
            }
        } catch (SQLException ex) {
            Logger.getLogger(Generacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Query.cerrar(rs);
            Query.cerrar(ps);
        }

        /**
         * *********** PARAMETROS DE EMPRESA ************
         */
        try {
            ps = conexion.getConexion().prepareStatement(Query.SELECT_PARAMETROS);
            ps.setInt(1, Integer.parseInt(reporte.getEmpresa().getIdentificador()));
            rs = ps.executeQuery();
            while (rs.next()) {
                dinamico.getCampos().add(rs.getString("PAECAM").trim());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Generacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Query.cerrar(rs);
            Query.cerrar(ps);
        }

        return dinamico;
    }

    /**
     * Generación de reportes de saldos con versión FORMATOV01 - Ancho Fijo.
     *
     * @param conexion
     * @param query
     * @param reporte
     * @param servicio
     * @param archivo
     */
    private static void saldoAnchoFijo(Conexion conexion, String query, Reporte reporte, Servicio servicio, ArchivoTextoPlano archivo) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String linea = "";

        try {
            ps = conexion.getConexion().prepareStatement(query);
            ps.setString(1, reporte.getEmpresa().getIdentificador());
            ps.setString(2, servicio.getIdentificador());

            rs = ps.executeQuery();

            while (rs.next()) {
                linea
                        = String.format(FORMATO_SALDOS_ANCHO_FIJO,
                                reporte.getEmpresa().getIdentificador(),//1-NUMERO IBS
                                servicio.getIdentificador(), //2-CODIGO DE SERVICIO
                                rs.getString("TRSPAM").trim(), //3-CODIGO DE MONEDA
                                rs.getString("TRSCCE").trim(), //4-NUMERO IBS
                                rs.getString("TRSNOA").trim(), //5-NOMBRE DEL CLIENTE Y 6-APELLIDO DEL CLIENTE                                
                                rs.getString("TRSID1").trim(), //7-REFERENCIA1
                                rs.getString("TRSID2").trim(), //8-REFERENCIA2                                
                                rs.getString("CONCEPTO1").trim(), //9-VALOR1
                                rs.getString("CONCEPTO2").trim(), //10-VALOR2
                                rs.getString("CONCEPTO3").trim(), //11-VALOR3
                                rs.getString("CONCEPTO4").trim(), //12-VALOR4
                                rs.getString("CONCEPTO5").trim() //13-VALOR5
                        );

                archivo.agregarLinea(linea);
                linea = "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Generacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Query.cerrar(rs);
            Query.cerrar(ps);
        }
    }

    /**
     * Generación de reportes de saldos con versión FORMATOV02 - Separación Pipe
     * (|).
     *
     * @param conexion
     * @param query
     * @param reporte
     * @param servicio
     * @param archivo
     */
    private static void saldoSeparacionPipe(Conexion conexion, String query, Reporte reporte, Servicio servicio, ArchivoTextoPlano archivo) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String linea = "";

        try {
            ps = conexion.getConexion().prepareStatement(query);
            ps.setString(1, reporte.getEmpresa().getIdentificador());
            ps.setString(2, servicio.getIdentificador());

            rs = ps.executeQuery();

            while (rs.next()) {
                linea
                        = String.format(FORMATO_SALDOS_SEPARACION_PIPE,
                                "N", //1-ACCION
                                servicio.getIdentificador(), //2- NUMERO DE SERVICIO
                                rs.getString("TRSID1").trim(), //3-IDENTIFICADOR1
                                rs.getString("TRSID2").trim(), //4-IDENTIFICADOR2
                                rs.getString("TRSID3").trim(), //5-IDENTIFICADOR3
                                rs.getString("TRSIDC").trim(), //6-NUMERO ID/RTN
                                rs.getString("TRSCCE").trim(), //7-NUMERO DE CLIENTE
                                rs.getString("TRSNOA").trim(), //8-NOMBRE APELLIDO CLIENTE
                                rs.getString("TRSCAF").trim(), //9-FECHA DE ACTIVACION DE SALDO
                                rs.getString("TRSSAV").trim(), //10-SALDO VIGENTE HASTA
                                rs.getString("TRSFEV").trim(), //11-FECHA DE VENCIMIENTO DEL SALDO
                                rs.getString("TRSCOM").trim(), //12-COMENTARIO
                                "", //13-CAMPO ESPECIAS 1
                                rs.getString("AUTOGENERACION").trim(), //14-LIMITE AUTOGENERACION
                                "", //15-CAMPO ESPECIAS 3
                                "", //16-CAMPO ESPECIAS 4
                                "", //17-CAMPO ESPECIAS 5                            
                                rs.getString("TRSPAM").trim(), //18-MONEDA DE PAGO
                                rs.getString("TRSVAM").trim(), //19-MONEDA DE SALDO
                                rs.getString("CONCEPTO1").trim(), //20-CONCEPTO1
                                rs.getString("CONCEPTO2").trim(), //21-CONCEPTO2
                                rs.getString("CONCEPTO3").trim(), //22-CONCEPTO3
                                rs.getString("CONCEPTO4").trim(), //23-CONCEPTO4
                                rs.getString("CONCEPTO5").trim() //24-CONCEPTO5
                        );
                archivo.agregarLinea(linea);
                linea = "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Generacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Query.cerrar(rs);
            Query.cerrar(ps);
        }
    }

    /**
     * Genera el encabezado para los archivos planos a nivel de empresa.
     *
     * @param reporte
     * @param archivo
     */
    private static void generarEncabezadoEmpresa(Reporte reporte, ArchivoTextoPlano archivo) {
        String encabezado = "";

        switch (reporte.getInformacion()) {
            case "PAGOS":
                encabezado
                        += reporte.getEmpresa().getIdentificador() + "|"
                        + reporte.getEmpresa().getTransacciones() + "|"
                        + Util.getFechaHoraActual("YYYYMMdd") + "|"
                        + Util.getFechaHoraActual("hh:mm:ss");
                break;
            case "SALDOS":
                encabezado
                        += reporte.getEmpresa().getIdentificador() + "|NA|"
                        + reporte.getEmpresa().getTransacciones() + "|NA|"
                        + Util.getFechaHoraActual("YYYYMMdd");
                break;
        }

        archivo.agregarLinea(encabezado);

    }

    /**
     * Genera el encabezado para los archivos planos a nivel de servicio.
     *
     * @param reporte
     * @param servicio
     * @param archivo
     */
    private static void generarEncabezadoServicio(Reporte reporte, Servicio servicio, ArchivoTextoPlano archivo) {
        String encabezado = "";

        switch (reporte.getInformacion()) {
            case "PAGOS":
                encabezado
                        += reporte.getEmpresa().getIdentificador() + "|"
                        + servicio.getTransacciones() + "|"
                        + Util.getFechaHoraActual("YYYYMMdd") + "|"
                        + Util.getFechaHoraActual("hh:mm:ss");
                break;
            case "SALDOS":
                encabezado
                        += reporte.getEmpresa().getIdentificador() + "|NA|"
                        + servicio.getTransacciones() + "|NA|"
                        + Util.getFechaHoraActual("YYYYMMdd");
                break;
        }

        archivo.agregarLinea(encabezado);

    }

}
