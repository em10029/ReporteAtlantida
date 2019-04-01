/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.data;

import java.util.ArrayList;
import java.util.Arrays;
import reporte.atlantida.estructura.Concepto;
import reporte.atlantida.estructura.Dinamico;
import reporte.atlantida.estructura.Reporte;
import reporte.atlantida.estructura.Servicio;

/**
 * Clase estructural para peticiones con versiones detalladas y dinámicas.
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 27-nov-2018
 */
public class Data {

    /**
     * Contenido descriptivo del encabezado del archivo.
     */
    private ArrayList<String> encabezado;

    /**
     * Campos del diario CAEDTA.CAETRD.
     */
    private ArrayList<String> camposDiario;

    /**
     * Campos del historico CAEDTA.CAETRH.
     */
    private ArrayList<String> camposHistorico;

    /**
     * Estilo y alineación del campo.
     */
    private ArrayList<String> estilos;

    /**
     * Consulta SQL sobre el diario CAEDTA.CAETRD.
     */
    private String queryDiario;

    /**
     * Consulta SQL sobre el historico CAEDTA.CAETRH.
     */
    private String queryHistorico;

    /**
     * Cantidad de campos.
     */
    private int campos;

    /**
     * Constructor.
     */
    private Data() {
        this.encabezado = new ArrayList<>();
        this.camposDiario = new ArrayList<>();
        this.camposHistorico = new ArrayList<>();
        this.estilos = new ArrayList<>();
    }

    /**
     * Retorna el objeto en forma de cadena.
     *
     * @return String
     */
    @Override
    public String toString() {
        String cadena
                = "Encabezado: " + Arrays.toString(this.encabezado.toArray()) + "\r\n"
                + "Campos Diario: " + Arrays.toString(this.camposDiario.toArray()) + "\r\n"
                + "Campos Historico: " + Arrays.toString(this.camposHistorico.toArray()) + "\r\n"
                + "Query Diario: " + this.queryDiario + "\r\n"
                + "Query Historico: " + this.queryHistorico + "\r\n"
                + "Campos: " + this.campos;

        return cadena;
    }

    /**
     * Get de la propiedad encabezado.
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getEncabezado() {
        return encabezado;
    }

    /**
     * Get de la propiedad camposDiario.
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getCamposDiario() {
        return camposDiario;
    }

    /**
     * Get de la propiedad camposHistorico.
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getCamposHistorico() {
        return camposHistorico;
    }

    /**
     * Get de la propiedad estilos.
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getEstilos() {
        return estilos;
    }

    /**
     * Get de la propiedad queryDiario.
     *
     * @return String
     */
    public String getQueryDiario() {
        return queryDiario;
    }

    /**
     * Get de la propiedad queryHistorico.
     *
     * @return String
     */
    public String getQueryHistorico() {
        return queryHistorico;
    }

    /**
     * Get de la propiedad campos.
     *
     * @return int
     */
    public int getCampos() {
        return campos;
    }

    //**************** ELEMENTOS ESTATICOS ****************//
    /**
     * Retorna la estructura Data para una petición con versión de detalle
     * (FORMATOV04).
     *
     * @param reporte
     * @param servicio
     * @param formatoFecha
     * @param evaluaIdentificador
     * @return Data
     */
    public static Data getDetalle(Reporte reporte, Servicio servicio, boolean formatoFecha, boolean evaluaIdentificador) {

        Data detallado = new Data();

        ArrayList<String> encabezado = detallado.encabezado;
        ArrayList<String> camposDiario = detallado.camposDiario;
        ArrayList<String> camposHistorico = detallado.camposHistorico;
        ArrayList<String> estilos = detallado.estilos;

        //1-ACCION ***
        encabezado.add("Estado");
        camposDiario.add("'D' AS ACCION");
        camposHistorico.add("'P' AS ACCION");
        estilos.add("C");

        //2-NUMERO DE RECIBO
        encabezado.add("No. Recibo");
        camposDiario.add("TRDNUM");
        camposHistorico.add("TRHNUM");
        estilos.add("I");

        //3-NUMERO DE SERVICIO ***
        encabezado.add("No. Servicio");
        camposDiario.add("\'" + servicio.getIdentificador() + "\'");
        camposHistorico.add("\'" + servicio.getIdentificador() + "\'");
        estilos.add("C");

        //Identificadores
        if(evaluaIdentificador){ //Aplica solo para nivel de servicio excel.
            //4-IDENTIFICADOR1
            if (servicio.getEstadoIdentificador1().equals("S")) {
                encabezado.add(servicio.getDescripcionIdentificador1());
                camposDiario.add("TRDID1");
                camposHistorico.add("TRHID1");
                estilos.add("I");
            }
            //5-IDENTIFICADOR2
            if (servicio.getEstadoIdentificador2().equals("S")) {
                encabezado.add(servicio.getDescripcionIdentificador2());
                camposDiario.add("TRDID2");
                camposHistorico.add("TRHID2");
                estilos.add("I");
            }
            //6-IDENTIFICADOR3
            if (servicio.getEstadoIdentificador3().equals("S")) {
                encabezado.add(servicio.getDescripcionIdentificador3());
                camposDiario.add("TRDID3");
                camposHistorico.add("TRHID3");
                estilos.add("I");
            }
        }else{
            //4-IDENTIFICADOR1
            encabezado.add("ID1");
            camposDiario.add("TRDID1");
            camposHistorico.add("TRHID1");
            estilos.add("I");

            //5-IDENTIFICADOR2
            encabezado.add("ID2");
            camposDiario.add("TRDID2");
            camposHistorico.add("TRHID2");
            estilos.add("I");

            //6-IDENTIFICADOR3
            encabezado.add("ID3");
            camposDiario.add("TRDID3");
            camposHistorico.add("TRHID3");
            estilos.add("I");
        }
        
        //7-MONEDA DE PAGO
        encabezado.add("Moneda Pago");
        camposDiario.add("TRDREM");
        camposHistorico.add("TRHREM");
        estilos.add("C");

        //8-FECHA DE PAGO
        encabezado.add("Fecha Pago");
        if (formatoFecha) {
            camposDiario.add("(CONCAT(CONCAT(CONCAT(TRIM(SUBSTR(TRDREF,7,8)), '/'), CONCAT(SUBSTR(TRDREF,5,2), '/')), SUBSTR(TRDREF,1,4))) AS TRDREF");
            camposHistorico.add("(CONCAT(CONCAT(CONCAT(TRIM(SUBSTR(TRHREF,7,8)), '/'), CONCAT(SUBSTR(TRHREF,5,2), '/')), SUBSTR(TRHREF,1,4))) AS TRHREF");
        } else {
            camposDiario.add("TRDREF");
            camposHistorico.add("TRHREF");
        }
        estilos.add("C");

        //9-CAMPO ESPECIAL ***
        encabezado.add("Campo Especial");
        camposDiario.add("\' \'");
        camposHistorico.add("\' \'");
        estilos.add("C");

        //10-HORA DE PAGO
        encabezado.add("Hora Pago");
        camposDiario.add("TRDREH");
        camposHistorico.add("TRHREH");
        estilos.add("C");

        //11-TOTAL RECAUDADO
        encabezado.add("Monto Pagado");
        camposDiario.add("TRDRET");
        camposHistorico.add("TRHRET");
        estilos.add("D");

        //12-RECAUDO EN EFECTIVO
        encabezado.add("Pago Efectivo");
        camposDiario.add("TRDREE");
        camposHistorico.add("TRHREE");
        estilos.add("D");

        //13-RECAUDO EN CHEQUE
        encabezado.add("Pago Cheque");
        camposDiario.add("TRDREC");
        camposHistorico.add("TRHREC");
        estilos.add("D");

        //14-BANCO DEL CHEQUE CODIGO                
        encabezado.add("Cod. Banco Cheque");
        camposDiario.add("TRDRECB");
        camposHistorico.add("TRHRECB");
        estilos.add("D");

        //15-BANCO DEL CHEQUE DESCRIPCION ***
        encabezado.add("Nombre Banco");
        camposDiario.add("CASE TRDRECB WHEN 1 THEN 'Banco Central de Honduras'WHEN 2 THEN 'Banco Atlántida'WHEN 3 THEN 'Continental'WHEN 4 THEN 'NO USADO'WHEN 5 THEN 'Banco de Londres'WHEN 6 THEN 'Banco de los Trabajadores'WHEN 7 THEN 'Banco de Occdente'WHEN 8 THEN 'Banco del Comercio'WHEN 9 THEN 'NO USADO'WHEN 10 THEN 'Banco Sogerin'WHEN 11 THEN 'Banffa'WHEN 12 THEN 'Banco Mercantil'WHEN 13 THEN 'Banco de Honduras'WHEN 14 THEN 'Banhcafe'WHEN 15 THEN 'Banco del País'WHEN 16 THEN 'Banco UNO'WHEN 17 THEN 'Banco Futuro'WHEN 18 THEN 'Ficensa'WHEN 19 THEN 'NO USADO'WHEN 20 THEN 'BanPro'WHEN 21 THEN 'NO USADO'WHEN 22 THEN 'NO USADO'WHEN 23 THEN 'NO USADO'WHEN 24 THEN 'Banco Credomatic'WHEN 25 THEN 'Banco Promérica'WHEN 28 THEN 'Ficohsa'WHEN 29 THEN 'NO USADO'WHEN 30 THEN 'Banco B.G.A.'WHEN 51 THEN 'Banadesa'ELSE 'N/A' END AS BANCO");
        camposHistorico.add("CASE TRHRECB WHEN 1 THEN 'Banco Central de Honduras'WHEN 2 THEN 'Banco Atlántida'WHEN 3 THEN 'Continental'WHEN 4 THEN 'NO USADO'WHEN 5 THEN 'Banco de Londres'WHEN 6 THEN 'Banco de los Trabajadores'WHEN 7 THEN 'Banco de Occdente'WHEN 8 THEN 'Banco del Comercio'WHEN 9 THEN 'NO USADO'WHEN 10 THEN 'Banco Sogerin'WHEN 11 THEN 'Banffa'WHEN 12 THEN 'Banco Mercantil'WHEN 13 THEN 'Banco de Honduras'WHEN 14 THEN 'Banhcafe'WHEN 15 THEN 'Banco del País'WHEN 16 THEN 'Banco UNO'WHEN 17 THEN 'Banco Futuro'WHEN 18 THEN 'Ficensa'WHEN 19 THEN 'NO USADO'WHEN 20 THEN 'BanPro'WHEN 21 THEN 'NO USADO'WHEN 22 THEN 'NO USADO'WHEN 23 THEN 'NO USADO'WHEN 24 THEN 'Banco Credomatic'WHEN 25 THEN 'Banco Promérica'WHEN 28 THEN 'Ficohsa'WHEN 29 THEN 'NO USADO'WHEN 30 THEN 'Banco B.G.A.'WHEN 51 THEN 'Banadesa'ELSE 'N/A' END AS BANCO");
        estilos.add("I");

        //16-NUMERO DE CHEQUE
        encabezado.add("No. Cheque");
        camposDiario.add("TRDRECN");
        camposHistorico.add("TRHRECN");
        estilos.add("D");

        //17-AGENCIA DONDE SE RECAUDO
        encabezado.add("Agencia");
        camposDiario.add("AGECOD");
        camposHistorico.add("AGECOD");
        estilos.add("C");

        //18-CAJA DEL PAGO (CANAL)
        encabezado.add("Canal");
        camposDiario.add("TRDCAJ");
        camposHistorico.add("TRHCAJ");
        estilos.add("C");

        //19-CAJERO QUE RECIBIO EL PAGO
        encabezado.add("Cajero");
        camposDiario.add("TRDREA");
        camposHistorico.add("TRHREA");
        estilos.add("C");

        //20-INFO. DE SALDO FECHA DEL SALDO
        encabezado.add("Fecha Base Datos");
        if (formatoFecha) {
            camposDiario.add("(CONCAT(CONCAT(CONCAT(TRIM(SUBSTR(TRDCAF,7,8)), '/'), CONCAT(SUBSTR(TRDCAF,5,2), '/')), SUBSTR(TRDCAF,1,4))) AS TRDCAF");
            camposHistorico.add("(CONCAT(CONCAT(CONCAT(TRIM(SUBSTR(TRHCAF,7,8)), '/'), CONCAT(SUBSTR(TRHCAF,5,2), '/')), SUBSTR(TRHCAF,1,4))) AS TRHCAF");
        } else {
            camposDiario.add("TRDCAF");
            camposHistorico.add("TRHCAF");
        }
        estilos.add("C");

        //21-INFO. DE SALDO HORA DEL SALDO
        encabezado.add("Hora Base Datos");
        camposDiario.add("TRDCAH");
        camposHistorico.add("TRHCAH");
        estilos.add("C");

        //22-INFO. DE SALDO TOTAL A COBRAR
        encabezado.add("Saldo Base Datos");
        camposDiario.add("TRDVAC");
        camposHistorico.add("TRHVAC");
        estilos.add("D");

        //23-NOMBRE DE CLIENTE
        encabezado.add("Nombre Cliente");
        camposDiario.add("TRDNOA");
        camposHistorico.add("TRHNOA");
        estilos.add("I");

        //24-NUMERO ID/RTN
        encabezado.add("ID / RTN");
        camposDiario.add("TRDIDC");
        camposHistorico.add("TRHIDC");
        estilos.add("C");

        //25-TASA DE CAMBIO
        encabezado.add("Tasa de Cambio");
        camposDiario.add("TRDTAC");
        camposHistorico.add("TRHTAC");
        estilos.add("D");

        //26-MORA PAGADA
        encabezado.add("Mora Pagada");
        camposDiario.add("TRDMOC");
        camposHistorico.add("TRHMOC");
        estilos.add("D");

        //+27 CONCEPTOS
        if (reporte.getEmpresa().getNivel().equals("NIVELEMP")) {

            int contador = 1;
            int diferencia = reporte.getEmpresa().getCantidadConcepto() - servicio.getCantidadConcepto();

            for (Concepto concepto : servicio.getConceptos()) {
                if (concepto.getEstado().equals("A")) {
                    encabezado.add("Concepto" + contador++);
                    camposDiario.add("COALESCE((SELECT CAEDTA.CAETRDC.TRDCMO FROM CAEDTA.CAETRDC WHERE CAEDTA.CAETRDC.TRDNUM = CAEDTA.CAETRD.TRDNUM AND TRDCNU = " + concepto.getNumero() + "), '0.00') AS CONCEPTO" + concepto.getNumero());
                    camposHistorico.add("COALESCE((SELECT CAEDTA.CAETRHC.TRHCMO FROM CAEDTA.CAETRHC WHERE CAEDTA.CAETRHC.TRHNUM = CAEDTA.CAETRH.TRHNUM AND TRHCNU = " + concepto.getNumero() + "), '0.00') AS CONCEPTO" + concepto.getNumero());
                    estilos.add("D");
                }
            }

            //Completacion de conceptos (valor por defecto, para completar conceptos)            
            while (diferencia != 0) {
                encabezado.add("Concepto" + contador++);
                camposDiario.add("\'0.00\'");
                camposHistorico.add("\'0.00\'");
                estilos.add("D");
                diferencia--;
            }

        } else {
            for (Concepto concepto : servicio.getConceptos()) {
                if (concepto.getEstado().equals("A")) {
                    encabezado.add(concepto.getDescripcion() + "(" + concepto.getOperador() + ")");
                    camposDiario.add("COALESCE((SELECT CAEDTA.CAETRDC.TRDCMO FROM CAEDTA.CAETRDC WHERE CAEDTA.CAETRDC.TRDNUM = CAEDTA.CAETRD.TRDNUM AND TRDCNU = " + concepto.getNumero() + "), '0.00') AS CONCEPTO" + concepto.getNumero());
                    camposHistorico.add("COALESCE((SELECT CAEDTA.CAETRHC.TRHCMO FROM CAEDTA.CAETRHC WHERE CAEDTA.CAETRHC.TRHNUM = CAEDTA.CAETRH.TRHNUM AND TRHCNU = " + concepto.getNumero() + "), '0.00') AS CONCEPTO" + concepto.getNumero());
                    estilos.add("D");
                }
            }
        }

        //DIARIO
        String queryDiario = "";
        //Generacion de query                        
        for (String campo : camposDiario) {
            queryDiario += campo + ",";
        }
        //Eliminando la ultima coma
        queryDiario = "SELECT " + queryDiario.substring(0, queryDiario.length() - 1);
        //Condicion de query
        queryDiario += " FROM CAEDTA.CAETRD WHERE EMPNUM = " + reporte.getEmpresa().getIdentificador() + " AND SERNUM = " + servicio.getIdentificador() + " AND TRDREF >= " + reporte.getFechaInicial() + " AND TRDREF <= " + reporte.getFechaFinal();

        detallado.queryDiario = queryDiario;

        //HISTORICO
        String queryHistorico = "";
        //Generacion de query                        
        for (String campo : camposHistorico) {
            queryHistorico += campo + ",";
        }
        //Eliminando la ultima coma
        queryHistorico = "SELECT " + queryHistorico.substring(0, queryHistorico.length() - 1);
        
        //Condicion de query
        if(reporte.getGeneracion().equals("C")){ //Cierre
            queryHistorico += " FROM CAEDTA.CAETRH WHERE EMPNUM = " + reporte.getEmpresa().getIdentificador() + " AND SERNUM = " + servicio.getIdentificador() + " AND TRHACF >= " + reporte.getFechaInicial() + " AND TRHACF <= " + reporte.getFechaFinal();
        }else{
            queryHistorico += " FROM CAEDTA.CAETRH WHERE EMPNUM = " + reporte.getEmpresa().getIdentificador() + " AND SERNUM = " + servicio.getIdentificador() + " AND TRHREF >= " + reporte.getFechaInicial() + " AND TRHREF <= " + reporte.getFechaFinal();
        }
        

        detallado.queryHistorico = queryHistorico;

        //CANTIDAD DE CAMPOS
        detallado.campos = camposHistorico.size();

        return detallado;
    }

    /**
     * Retorna la estructura Data para una petición con versión de dinamica
     * (FORMATOV05).
     *
     * @param reporte
     * @param servicio
     * @param dinamico
     * @return Data
     */
    public static Data getDinamico(Reporte reporte, Servicio servicio, Dinamico dinamico) {

        Data data = new Data();
        ArrayList<String> encabezado = data.encabezado;
        ArrayList<String> camposDiario = data.camposDiario;
        ArrayList<String> camposHistorico = data.camposHistorico;

        for (String campo : dinamico.getCampos()) {
            switch (campo) {

                case "TRHNUM": //1-NUMERO DE RECIBO
                    encabezado.add("NUMERO DE RECIBO");
                    camposDiario.add("TRDNUM");
                    camposHistorico.add("TRHNUM");
                    break;

                case "TRHSER": //2-NUMERO DE SERVICIO
                    encabezado.add("NUMERO DE SERVICIO");
                    camposDiario.add("\'" + servicio.getIdentificador() + "\'");
                    camposHistorico.add("\'" + servicio.getIdentificador() + "\'");
                    break;

                case "TRHID1": //3-IDENTIFICADOR1
                    encabezado.add("IDENTIFICADOR1");
                    camposDiario.add("TRDID1");
                    camposHistorico.add("TRHID1");
                    break;

                case "TRHID2": //4-IDENTIFICADOR2
                    encabezado.add("IDENTIFICADOR2");
                    camposDiario.add("TRDID2");
                    camposHistorico.add("TRHID2");
                    break;

                case "TRHID3": //5-IDENTIFICADOR3
                    encabezado.add("IDENTIFICADOR3");
                    camposDiario.add("TRDID3");
                    camposHistorico.add("TRHID3");
                    break;

                case "TRHREM": //6-MONEDA DE PAGO      
                    encabezado.add("MONEDA DE PAGO");
                    camposDiario.add("TRDREM");
                    camposHistorico.add("TRHREM");
                    break;

                case "TRHREF": //7-FECHA DE PAGO ***
                    encabezado.add("FECHA DE PAGO");
                    if (dinamico.getFormatoFecha().equals("DDMMAAAA")) {
                        camposDiario.add("CONCAT((CONCAT((CONCAT((TRIM(SUBSTR(TRDREF,7,8))), '/')), (CONCAT((TRIM(SUBSTR(TRDREF,5,2))), '/')))), (TRIM(SUBSTR(TRDREF,1,4)))) AS TRDREF");
                        camposHistorico.add("CONCAT((CONCAT((CONCAT((TRIM(SUBSTR(TRHREF,7,8))), '/')), (CONCAT((TRIM(SUBSTR(TRHREF,5,2))), '/')))), (TRIM(SUBSTR(TRHREF,1,4)))) AS TRHREF");
                    } else {
                        camposDiario.add("CONCAT((CONCAT((CONCAT((TRIM(SUBSTR(TRDREF,1,4))), '/')), (CONCAT((TRIM(SUBSTR(TRDREF,5,2))), '/')))), (TRIM(SUBSTR(TRDREF,7,8)))) AS TRDREF");
                        camposHistorico.add("CONCAT((CONCAT((CONCAT((TRIM(SUBSTR(TRHREF,1,4))), '/')), (CONCAT((TRIM(SUBSTR(TRHREF,5,2))), '/')))), (TRIM(SUBSTR(TRHREF,7,8)))) AS TRHREF");
                    }
                    break;

                case "TRHCOM": //8-COMENTARIO - Identificador Transaccion Empresa
                    encabezado.add("COMENTARIO");
                    camposDiario.add("TRDCOM");
                    camposHistorico.add("TRHCOM");
                    break;

                case "TRHREH": //9-HORA DE PAGO
                    encabezado.add("HORA DE PAGO");
                    camposDiario.add("TRDREH");
                    camposHistorico.add("TRHREH");
                    break;

                case "TRHRET": //10-TOTAL RECAUDADO
                    encabezado.add("TOTAL RECAUDADO");
                    camposDiario.add("TRDRET");
                    camposHistorico.add("TRHRET");
                    break;

                case "TRHREE": //11-RECAUDO EN EFECTIVO
                    encabezado.add("RECAUDO EN EFECTIVO");
                    camposDiario.add("TRDREE");
                    camposHistorico.add("TRHREE");
                    break;

                case "TRHREC": //12-RECAUDO EN CHEQUE   
                    encabezado.add("RECAUDO EN CHEQUE");
                    camposDiario.add("TRDREC");
                    camposHistorico.add("TRHREC");
                    break;

                case "TRHRECB": //13-BANCO DEL CHEQUE CODIGO
                    encabezado.add("BANCO DEL CHEQUE CODIGO");
                    camposDiario.add("TRDRECB");
                    camposHistorico.add("TRHRECB");
                    break;

                case "TRHRENB": //14-DESCRIPCION DE BANCO CHEQUE 
                    encabezado.add("DESCRIPCION DE BANCO CHEQUE");
                    camposDiario.add("CASE TRDRECB WHEN 1 THEN 'Banco Central de Honduras'WHEN 2 THEN 'Banco Atlántida'WHEN 3 THEN 'Continental'WHEN 4 THEN 'NO USADO'WHEN 5 THEN 'Banco de Londres'WHEN 6 THEN 'Banco de los Trabajadores'WHEN 7 THEN 'Banco de Occdente'WHEN 8 THEN 'Banco del Comercio'WHEN 9 THEN 'NO USADO'WHEN 10 THEN 'Banco Sogerin'WHEN 11 THEN 'Banffa'WHEN 12 THEN 'Banco Mercantil'WHEN 13 THEN 'Banco de Honduras'WHEN 14 THEN 'Banhcafe'WHEN 15 THEN 'Banco del País'WHEN 16 THEN 'Banco UNO'WHEN 17 THEN 'Banco Futuro'WHEN 18 THEN 'Ficensa'WHEN 19 THEN 'NO USADO'WHEN 20 THEN 'BanPro'WHEN 21 THEN 'NO USADO'WHEN 22 THEN 'NO USADO'WHEN 23 THEN 'NO USADO'WHEN 24 THEN 'Banco Credomatic'WHEN 25 THEN 'Banco Promérica'WHEN 28 THEN 'Ficohsa'WHEN 29 THEN 'NO USADO'WHEN 30 THEN 'Banco B.G.A.'WHEN 51 THEN 'Banadesa'ELSE 'N/A' END AS BANCO");
                    camposHistorico.add("CASE TRHRECB WHEN 1 THEN 'Banco Central de Honduras'WHEN 2 THEN 'Banco Atlántida'WHEN 3 THEN 'Continental'WHEN 4 THEN 'NO USADO'WHEN 5 THEN 'Banco de Londres'WHEN 6 THEN 'Banco de los Trabajadores'WHEN 7 THEN 'Banco de Occdente'WHEN 8 THEN 'Banco del Comercio'WHEN 9 THEN 'NO USADO'WHEN 10 THEN 'Banco Sogerin'WHEN 11 THEN 'Banffa'WHEN 12 THEN 'Banco Mercantil'WHEN 13 THEN 'Banco de Honduras'WHEN 14 THEN 'Banhcafe'WHEN 15 THEN 'Banco del País'WHEN 16 THEN 'Banco UNO'WHEN 17 THEN 'Banco Futuro'WHEN 18 THEN 'Ficensa'WHEN 19 THEN 'NO USADO'WHEN 20 THEN 'BanPro'WHEN 21 THEN 'NO USADO'WHEN 22 THEN 'NO USADO'WHEN 23 THEN 'NO USADO'WHEN 24 THEN 'Banco Credomatic'WHEN 25 THEN 'Banco Promérica'WHEN 28 THEN 'Ficohsa'WHEN 29 THEN 'NO USADO'WHEN 30 THEN 'Banco B.G.A.'WHEN 51 THEN 'Banadesa'ELSE 'N/A' END AS BANCO");
                    break;

                case "TRHRECN": //15-NUMERO DE CHEQUE
                    encabezado.add("NUMERO DE CHEQUE");
                    camposDiario.add("TRDRECN");
                    camposHistorico.add("TRHRECN");
                    break;

                case "AGECOD": //16-AGENCIA DONDE SE RECAUDO
                    encabezado.add("AGENCIA DONDE SE RECAUDO");
                    camposDiario.add("AGECOD");
                    camposHistorico.add("AGECOD");
                    break;

                case "TRHCAJ": //17-CAJA DEL PAGO     
                    encabezado.add("CAJA DEL PAGO");
                    camposDiario.add("TRDCAJ");
                    camposHistorico.add("TRHCAJ");
                    break;

                case "TRHREA": //18-CAJERO QUE RECIBIO EL PAGO    
                    encabezado.add("CAJERO QUE RECIBIO EL PAGO");
                    camposDiario.add("TRDREA");
                    camposHistorico.add("TRHREA");
                    break;

                case "TRHCAF": //19-INFO. DE SALDO FECHA DEL SALDO ***
                    encabezado.add("INFO. DE SALDO FECHA DEL SALDO");
                    if (dinamico.getFormatoFecha().equals("DDMMAAAA")) {
                        camposDiario.add("CONCAT((CONCAT((CONCAT((TRIM(SUBSTR(TRDCAF,7,8))), '/')), (CONCAT((TRIM(SUBSTR(TRDCAF,5,2))), '/')))), (TRIM(SUBSTR(TRDCAF,1,4)))) AS TRDCAF");
                        camposHistorico.add("CONCAT((CONCAT((CONCAT((TRIM(SUBSTR(TRHCAF,7,8))), '/')), (CONCAT((TRIM(SUBSTR(TRHCAF,5,2))), '/')))), (TRIM(SUBSTR(TRHCAF,1,4)))) AS TRHCAF");
                    } else {
                        camposDiario.add("CONCAT((CONCAT((CONCAT((TRIM(SUBSTR(TRDCAF,1,4))), '/')), (CONCAT((TRIM(SUBSTR(TRDCAF,5,2))), '/')))), (TRIM(SUBSTR(TRDCAF,7,8)))) AS TRDCAF");
                        camposHistorico.add("CONCAT((CONCAT((CONCAT((TRIM(SUBSTR(TRHCAF,1,4))), '/')), (CONCAT((TRIM(SUBSTR(TRHCAF,5,2))), '/')))), (TRIM(SUBSTR(TRHCAF,7,8)))) AS TRHCAF");
                    }
                    break;

                case "TRHCAH": //20-INFO. DE SALDO HORA DEL SALDO
                    encabezado.add("INFO. DE SALDO HORA DEL SALDO");
                    camposDiario.add("TRDCAH");
                    camposHistorico.add("TRHCAH");
                    break;

                case "TRHVAC": //21-INFO. DE SALDO TOTAL A COBRAR
                    encabezado.add("INFO. DE SALDO TOTAL A COBRAR");
                    camposDiario.add("TRDVAC");
                    camposHistorico.add("TRHVAC");
                    break;

                case "TRHNOA": //22-NOMBRE DE CLIENTE    
                    encabezado.add("NOMBRE DE CLIENTE");
                    camposDiario.add("TRDNOA");
                    camposHistorico.add("TRHNOA");
                    break;

                case "TRHTAC": //23-TAS DE CAMBIO
                    encabezado.add("TAS DE CAMBIO");
                    camposDiario.add("TRDTAC");
                    camposHistorico.add("TRHTAC");
                    break;

                case "TRHMOC": //24-MORA PAGADA
                    encabezado.add("MORA PAGADA");
                    camposDiario.add("TRDMOC");
                    camposHistorico.add("TRHMOC");
                    break;

                case "CAMPO ESP1": //25-CAMPO ESPECIAL1
                    encabezado.add("CAMPO ESPECIAL1");
                    camposDiario.add("\' \'");
                    camposHistorico.add("\' \'");
                    break;

                case "CAMPO ESP2": //26-CAMPO ESPACIAL2
                    encabezado.add("CAMPO ESPACIAL2");
                    camposDiario.add("\' \'");
                    camposHistorico.add("\' \'");
                    break;

                case "CAMPO ESP3": //27-CAMPO ESPACIAL3
                    encabezado.add("CAMPO ESPACIAL3");
                    camposDiario.add("\' \'");
                    camposHistorico.add("\' \'");
                    break;

                case "CAMPO ESP4": //28-CAMPO ESPACIAL4
                    encabezado.add("CAMPO ESPACIAL4");
                    camposDiario.add("\' \'");
                    camposHistorico.add("\' \'");
                    break;

                //FALTAN CAMPOS DE LAS TARJETAS DE CREDITO
                default:
                    //ERROR
                    //String error = "El campo: " + campo + " no esta comtemplado en el formato dinamico.";                            
                    //reporte.agregarInfo(error);
                    //return;
                    break;
            }
        }

        //+N CONCEPTOS
        if (dinamico.getConceptos().equals("A")) {
            if (reporte.getEmpresa().getNivel().equals("NIVELEMP")) {

                int contador = 1;
                int diferencia = reporte.getEmpresa().getCantidadConcepto() - servicio.getCantidadConcepto();

                for (Concepto concepto : servicio.getConceptos()) {
                    if (concepto.getEstado().equals("A")) {
                        encabezado.add("Concepto" + contador++);
                        camposDiario.add("COALESCE((SELECT CAEDTA.CAETRDC.TRDCMO FROM CAEDTA.CAETRDC WHERE CAEDTA.CAETRDC.TRDNUM = CAEDTA.CAETRD.TRDNUM AND TRDCNU = " + concepto.getNumero() + "), '0.00') AS CONCEPTO" + concepto.getNumero());
                        camposHistorico.add("COALESCE((SELECT CAEDTA.CAETRHC.TRHCMO FROM CAEDTA.CAETRHC WHERE CAEDTA.CAETRHC.TRHNUM = CAEDTA.CAETRH.TRHNUM AND TRHCNU = " + concepto.getNumero() + "), '0.00') AS CONCEPTO" + concepto.getNumero());
                    }
                }

                //Completacion de conceptos (valor por defecto, para completar conceptos)            
                while (diferencia != 0) {
                    encabezado.add("Concepto" + contador++);
                    camposDiario.add("\'0.00\'");
                    camposHistorico.add("\'0.00\'");
                    diferencia--;
                }

            } else {
                for (Concepto concepto : servicio.getConceptos()) {
                    if (concepto.getEstado().equals("A")) {
                        encabezado.add(concepto.getDescripcion() + "(" + concepto.getOperador() + ")");
                        camposDiario.add("COALESCE((SELECT CAEDTA.CAETRDC.TRDCMO FROM CAEDTA.CAETRDC WHERE CAEDTA.CAETRDC.TRDNUM = CAEDTA.CAETRD.TRDNUM AND TRDCNU = " + concepto.getNumero() + "), '0.00') AS CONCEPTO" + concepto.getNumero());
                        camposHistorico.add("COALESCE((SELECT CAEDTA.CAETRHC.TRHCMO FROM CAEDTA.CAETRHC WHERE CAEDTA.CAETRHC.TRHNUM = CAEDTA.CAETRH.TRHNUM AND TRHCNU = " + concepto.getNumero() + "), '0.00') AS CONCEPTO" + concepto.getNumero());
                    }
                }
            }
        }

        //*************** DIARIO ***************//
        String queryDiario = " ";

        //Generacion de campos
        for (String campo : camposDiario) {
            queryDiario += campo + ",";
        }

        if (!camposDiario.isEmpty()) {
            //Eliminando la ultima coma        
            queryDiario = "SELECT " + queryDiario.substring(0, queryDiario.length() - 1);
        } else {
            queryDiario = "SELECT *";
        }
        //Condicion de query
        queryDiario += " FROM CAEDTA.CAETRD WHERE EMPNUM = " + reporte.getEmpresa().getIdentificador() + " AND SERNUM = " + servicio.getIdentificador() + " AND TRDREF >= " + reporte.getFechaInicial() + " AND TRDREF <= " + reporte.getFechaFinal();

        data.queryDiario = queryDiario;

        //*************** HISTORICO ***************//
        String queryHistorico = " ";

        //Generacion de campos
        for (String campo : camposHistorico) {
            queryHistorico += campo + ",";
        }

        //Eliminando la ultima coma        
        if (!camposHistorico.isEmpty()) {
            //Eliminando la ultima coma        
            queryHistorico = "SELECT " + queryHistorico.substring(0, queryHistorico.length() - 1);
        } else {
            queryHistorico = "SELECT *";
        }

        //Condicion de query
        if(reporte.getGeneracion().equals("C")){
            queryHistorico += " FROM CAEDTA.CAETRH WHERE EMPNUM = " + reporte.getEmpresa().getIdentificador() + " AND SERNUM = " + servicio.getIdentificador() + " AND TRHACF >= " + reporte.getFechaInicial() + " AND TRHACF <= " + reporte.getFechaFinal();
        }else{
            queryHistorico += " FROM CAEDTA.CAETRH WHERE EMPNUM = " + reporte.getEmpresa().getIdentificador() + " AND SERNUM = " + servicio.getIdentificador() + " AND TRHREF >= " + reporte.getFechaInicial() + " AND TRHREF <= " + reporte.getFechaFinal();
        }
        

        data.queryHistorico = queryHistorico;

        //CANTIDAD DE CAMPOS
        data.campos = camposHistorico.size();

        data.estilos = null;

        return data;
    }

    /**
     * Retorna la información del proceso de construcción del objeto.
     *
     * @param reporte
     * @param servicio
     * @param data
     * @return String
     */
    public static String toString(Reporte reporte, Servicio servicio, Data data) {

        //Obteniendo Informacion
        String info = "";

        //Identificadores
        info += "Servicio " + servicio.toString() + "\r\n";
        info += "ID1: " + servicio.getDescripcionIdentificador1() + " - " + servicio.getEstadoIdentificador1() + "\r\n";
        info += "ID2: " + servicio.getDescripcionIdentificador2() + " - " + servicio.getEstadoIdentificador2() + "\r\n";
        info += "ID3: " + servicio.getDescripcionIdentificador3() + " - " + servicio.getEstadoIdentificador3() + "\r\n";

        //Conceptos                                        
        for (Concepto conceptoo : servicio.getConceptos()) {
            info += conceptoo.toString() + "\r\n";
        }

        //Queries        
        info += "Campos: " + data.getCampos() + "\r\n";
        info += "Encabezado: " + Arrays.toString(data.getEncabezado().toArray()) + "\r\n";
        switch (reporte.getContenido()) {
            case "T":
                info += "Diario: " + data.getQueryDiario() + "\r\n";
                info += "Historico: " + data.getQueryHistorico() + "\r\n";
                break;
            case "D":
                info += "Diario: " + data.getQueryDiario() + "\r\n";
                break;
            case "H":
                info += "Historico: " + data.getQueryHistorico() + "\r\n";
                break;
        }

        return info;
    }

}
