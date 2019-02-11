/*
 * ©Informática Atlántida 2018.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 * 
 */
package reporte.atlantida.fichero;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Representa un archivo Excel (.xls o .xlsx).
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 Fecha: 01-30-2018
 */
public class ArchivoExcel extends Archivo {

    /**
     * Color Rojo
     */
    private static final byte[] RGB_ROJO = {(byte) 227, 30, 48};

    /**
     * Color Blanco
     */
    private static final byte[] RGB_BLANCO = {(byte) 255, (byte) 255, (byte) 255};

    /**
     * Libro, punto de inicio
     */
    private XSSFWorkbook libro;

    /**
     * Hoja del libro
     */
    private XSSFSheet hoja;

    /**
     * Alineación de celda al centro
     */
    private XSSFCellStyle estiloCentro;

    /**
     * Alineación de celda a la derecha
     */
    private XSSFCellStyle estiloDerecha;

    /**
     * Alineación de celda a la izquierda
     */
    private XSSFCellStyle estiloIzquierda;

    /**
     * Plantilla de ayuda
     */
    private FileInputStream plantilla;

    /**
     * Archivo que se creara
     */
    private FileOutputStream archivo;

    /**
     * Posicion inicial de la hoja (fila de inicio)
     */
    private int posicionInicial;

    /**
     * Constructor.
     *
     * @param directorio
     * @param nombre
     * @param extencion
     */
    public ArchivoExcel(Directorio directorio, String nombre, String extencion) {
        super(directorio, nombre, extencion);
    }

    /**
     * Crea y prepara la plantilla excel y para su procesamiento.
     *
     * @param plantillaExcel
     * @return creacionExitosa
     */
    public boolean crear(String plantillaExcel) {
        boolean creacionExitosa = false;

        this.posicionInicial = 11;
        try {
            this.plantilla = new FileInputStream(new File(plantillaExcel));
            this.libro = new XSSFWorkbook(this.plantilla);
            this.hoja = this.libro.getSheetAt(0);

            //Estilos de celdas
            this.estiloCentro = this.libro.createCellStyle();
            this.estiloCentro.setBorderBottom(BorderStyle.THIN);
            this.estiloCentro.setBorderLeft(BorderStyle.THIN);
            this.estiloCentro.setBorderRight(BorderStyle.THIN);
            this.estiloCentro.setAlignment(HorizontalAlignment.CENTER);

            this.estiloDerecha = this.libro.createCellStyle();
            this.estiloDerecha.setBorderBottom(BorderStyle.THIN);
            this.estiloDerecha.setBorderLeft(BorderStyle.THIN);
            this.estiloDerecha.setBorderRight(BorderStyle.THIN);
            this.estiloDerecha.setAlignment(HorizontalAlignment.RIGHT);

            this.estiloIzquierda = this.libro.createCellStyle();
            this.estiloIzquierda.setBorderBottom(BorderStyle.THIN);
            this.estiloIzquierda.setBorderLeft(BorderStyle.THIN);
            this.estiloIzquierda.setBorderRight(BorderStyle.THIN);
            this.estiloIzquierda.setAlignment(HorizontalAlignment.LEFT);

            creacionExitosa = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArchivoExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArchivoExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return creacionExitosa;
    }

    /**
     * Agrega el encabezado en la plantilla.
     *
     * @param empresa
     * @param transacciones
     * @param fecha
     * @param hora
     */
    public void agregarEncabezado(String empresa, int transacciones, String fecha, String hora) {
        XSSFRow fila = this.hoja.createRow(8);
        //1-IBS EMPRESA 
        XSSFCell celda1 = fila.createCell(0); //Columna
        celda1.setCellValue(empresa); //Valor               
        celda1.setCellStyle(this.estiloCentro);

        //2-TRANSACCIONES
        XSSFCell celda2 = fila.createCell(1); //Columna
        celda2.setCellValue(transacciones); //Valor               
        celda2.setCellStyle(this.estiloCentro);

        //3-FECHA DE GENERACION
        XSSFCell celda3 = fila.createCell(2); //Columna
        celda3.setCellValue(fecha); //Valor               
        celda3.setCellStyle(this.estiloCentro);

        //4-HORA DE GENERACION
        XSSFCell celda4 = fila.createCell(3); //Columna
        celda4.setCellValue(hora); //Valor               
        celda4.setCellStyle(this.estiloCentro);
    }

    /**
     * Agrega la tabla en la plantilla.
     *
     * @param encabezado
     */
    public void agregarTabla(ArrayList<String> encabezado) {

        XSSFColor colorLetra = new XSSFColor();
        colorLetra.setRGB(RGB_BLANCO);

        XSSFColor colorFondo = new XSSFColor();
        colorFondo.setRGB(RGB_ROJO);

        XSSFFont fuente = this.libro.createFont();
        fuente.setFontHeightInPoints((short) 10);
        fuente.setFontName("Arial");
        fuente.setBold(true);
        fuente.setColor(colorLetra);

        XSSFCellStyle estilo = this.libro.createCellStyle();
        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        estilo.setFont(fuente);
        estilo.setFillForegroundColor(colorFondo);
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //Fila
        XSSFRow fila = this.hoja.createRow(10); //Fila
        for (int i = 0; i < encabezado.size(); i++) {
            XSSFCell celda = fila.createCell(i); //Columna
            celda.setCellValue(encabezado.get(i));
            celda.setCellStyle(estilo);
        }
    }

    /**
     * Agrega la trama(datos) en la plantilla.
     *
     * @param linea
     * @param estilos
     */
    public void agregarLinea(String[] linea, ArrayList<String> estilos) {
        XSSFRow fila = this.hoja.createRow(this.posicionInicial); //Fila
        for (int i = 0; i < linea.length; i++) {
            XSSFCell celda = fila.createCell(i); //Columna
            celda.setCellValue(linea[i]);
            switch (estilos.get(i)) {
                case "D":
                    celda.setCellStyle(this.estiloDerecha);
                    break;
                case "I":
                    celda.setCellStyle(this.estiloIzquierda);
                    break;
                case "C":
                    celda.setCellStyle(this.estiloCentro);
                    break;
            }
        }
        this.posicionInicial++; //Cambio de fila
    }

    /**
     * Guarda y cierra los cambios en la plantilla.
     */
    public void cerrar() {
        try {
            this.plantilla.close(); //Cierra la plantilla
            this.archivo = new FileOutputStream(super.getUbicacion()); //Referencia de archivo a retornar
            this.libro.write(archivo); //Escribe en el archiovo
            this.archivo.close(); //Guarda y cierra el archivo generado
            this.libro.close(); //Cierra la referencia de la plantillas            
        } catch (IOException ex) {
            Logger.getLogger(ArchivoExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Destructor.
     */
    @Override
    public void finalize() {
        try {
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
