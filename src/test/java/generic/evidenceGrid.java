/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generic;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.openqa.selenium.remote.RemoteWebDriver;
/**
 *
 * @author TestingIT
 */
public class evidenceGrid {
    
     public void capturaDriver(RemoteWebDriver driver, String rutaEvidencia, int contador, String cp, String navegador) throws InterruptedException{
        File dir = this.crea_Carpeta(rutaEvidencia, cp, contador, navegador);
        Thread.sleep(3000);
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); 
        try {
            FileUtils.copyFile(scrFile, new File(dir.getPath()+"//evidencia"+contador+".png"));
            //FileUtils.copyFile(scrFile, new File(rutaEvidencia+"//"+fechaFormato()+"//"+cp.substring(0, 3)+"//"+this.horaMin(new File(rutaEvidencia+"//"+fechaFormato()+"//"+cp.substring(0, 3)))+"//"+cp+"//evidencia"+contador+".png"));
        } catch (IOException ex) {
            Logger.getLogger(evidenceGrid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public File crea_Carpeta(String rutaEvidencia, String cp, int contador, String navegador){
        File directorio = null;
        if(contador == 1){
            directorio = new File(rutaEvidencia+"//"+fechaFormato()+"//"+navegador+"//"+cp.substring(0, 3)+"//"+this.totalArchivosMasUno(new File(rutaEvidencia+"//"+fechaFormato()+"//"+navegador+"//"+cp.substring(0, 3)))+"//"+cp);
        }else{
            directorio = new File(rutaEvidencia+"//"+fechaFormato()+"//"+navegador+"//"+cp.substring(0, 3)+"//"+this.totalArchivos(new File(rutaEvidencia+"//"+fechaFormato()+"//"+navegador+"//"+cp.substring(0, 3)))+"//"+cp);
        }
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("El directorio ya existe");
                
            }
        }
        else {
            System.out.println("El directorio ya existe");
        }
        return directorio;
    }
    
    public String fechaFormato(){
        SimpleDateFormat d = new SimpleDateFormat("dd-MM-yy");
        Date fecha = new Date();
        return d.format(fecha);
    }
    
    public String horaMinSeg(){
        Date fecha = new Date();
        String hora = ""+fecha.getHours()+"-"+fecha.getMinutes()+"-"+fecha.getSeconds();
        return hora;
    }
    
    public int totalArchivosMasUno(File directorio){
        try{
            String[] arregloArchivos = directorio.list();
            int numArchivos = arregloArchivos.length;
            if(numArchivos >= 1){
                return numArchivos+1;
            }else{
                return numArchivos;
            }
        }catch(Exception e){
            return 1;
        }
    }
    
    public int totalArchivos(File directorio){
        try{
            String[] arregloArchivos = directorio.list();
            int numArchivos = arregloArchivos.length;
            return numArchivos;
        }catch(Exception e){
            return 1;
        }
    }
    
    public void crearPDF(String CasoPrueba, String Resultado, int contador, List<String> Pasos, String rutaEvidencia, String modulo, String version, String navegador) throws FileNotFoundException, DocumentException, BadElementException, IOException{

        // Se crea el documento
        Document documento = new Document(PageSize.A4);
        // Se crea el OutputStream para el fichero donde queremos dejar el pdf.
        FileOutputStream ficheroPdf = new FileOutputStream(rutaEvidencia+"\\"+fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)+"\\"+this.totalArchivos(new File(rutaEvidencia+"\\"+fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)))+"\\"+CasoPrueba+"\\"+CasoPrueba+".pdf");
        // Se asocia el documento al OutputStream y se indica que el espaciado entre
        // lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
        PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
        // Se abre el documento.
        documento.open();
        
        Font fuenteEncabezado= new Font();
        fuenteEncabezado.setColor(Color.BLACK);
        fuenteEncabezado.setStyle(Font.BOLD);
        fuenteEncabezado.setSize(12);
        
        Font fuenteBlanco= new Font();
        fuenteBlanco.setColor(Color.WHITE);
        fuenteBlanco.setStyle(Font.BOLD);
        fuenteBlanco.setSize(8);
        
        Font fuenteVerde= new Font();
        fuenteVerde.setColor(Color.GREEN);
        fuenteVerde.setStyle(Font.BOLD);
        fuenteVerde.setSize(12);
        
        Font fuenteRojo= new Font();
        fuenteRojo.setColor(Color.RED);
        fuenteRojo.setStyle(Font.BOLD);
        fuenteRojo.setSize(12);
        
        Font fuenteAzul= new Font();
        fuenteAzul.setColor(Color.ORANGE);
        fuenteAzul.setStyle(Font.BOLD);
        fuenteAzul.setSize(12);
        
        Table encabezado = new Table(3);
        
        Image ima = Image.getInstance("GFA.png"); 
        ima.scaleToFit(90, 90);
        
        Cell celdaImagen = new Cell();
        celdaImagen.setBorder(0);
        celdaImagen.add(ima);
        encabezado.addCell(celdaImagen);
        
        Paragraph parrafo = new Paragraph("Reporte de Evidencia Pruebas Automatizadas", fuenteEncabezado); 
        Cell celda = new Cell();
        celda.setBorder(0);
        celda.add(parrafo);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        encabezado.addCell(celda);
        
        ima = Image.getInstance("TestingIT.png"); 
        ima.scaleToFit(90, 90);
        celdaImagen = new Cell();
        celdaImagen.setBorder(0);
        celdaImagen.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celdaImagen.add(ima);
        encabezado.addCell(celdaImagen);
        
        encabezado.setBorder(0);
        documento.add(encabezado);
        
        Table DatosEjec = new Table(3);
        
        Paragraph parrafo1 = new Paragraph(modulo+" "+version, fuenteBlanco); 
        parrafo1.add(Chunk.NEWLINE);
        parrafo1.add(Chunk.NEWLINE);
        Cell celda1 = new Cell();
        celda1.setBorder(0);
        celda1.setBackgroundColor(Color.BLUE);
        celda1.add(parrafo1);
        celda1.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        DatosEjec.addCell(celda1);
        
        Paragraph parrafo2 = new Paragraph(navegador, fuenteBlanco);
        parrafo2.add(Chunk.NEWLINE);
        parrafo2.add(Chunk.NEWLINE);
        Cell celda2 = new Cell();
        celda2.setBorder(0);
        celda2.setBackgroundColor(Color.BLUE);
        celda2.add(parrafo2);
        celda2.setHorizontalAlignment(Element.ALIGN_CENTER);
        DatosEjec.addCell(celda2);
        
        Paragraph parrafo3 = new Paragraph(this.fechaFormato(), fuenteBlanco); 
        parrafo3.add(Chunk.NEWLINE);
        parrafo3.add(Chunk.NEWLINE);
        Cell celda3 = new Cell();
        celda3.setBorderColor(Color.BLUE);
        celda3.setBackgroundColor(Color.BLUE);
        celda3.add(parrafo3);
        celda3.setHorizontalAlignment(Element.ALIGN_CENTER);
        DatosEjec.addCell(celda3);
        
        
        Paragraph parrafo4 = new Paragraph(CasoPrueba, fuenteBlanco); 
        parrafo4.add(Chunk.NEWLINE);
        parrafo4.add(Chunk.NEWLINE);
        Cell celda4 = new Cell();
        celda4.setBorderColor(Color.GRAY);
        celda4.setBackgroundColor(Color.GRAY);
        celda4.setColspan(3);
        celda4.add(parrafo4);
        celda4.setHorizontalAlignment(Element.ALIGN_CENTER);
        DatosEjec.addCell(celda4);
        
        
        Paragraph parrafo5 = new Paragraph(); 
        if("Exitoso".equals(Resultado)){
            parrafo5 = new Paragraph(Resultado, fuenteVerde);
        }
        if("Fallido".equals(Resultado.substring(0, 7))){
            parrafo5 = new Paragraph(Resultado, fuenteRojo);
        }
        if(Resultado.length()>10){
            if("Ejecución Fallida".equals(Resultado.substring(0, 17))){
                parrafo5 = new Paragraph(Resultado, fuenteAzul);
            }
        }
        parrafo5.add(Chunk.NEWLINE);
        parrafo5.add(Chunk.NEWLINE);
        Cell celda5 = new Cell();
        celda5.setColspan(3);
        celda5.add(parrafo5);
        celda5.setHorizontalAlignment(Element.ALIGN_CENTER);
        DatosEjec.addCell(celda5);
        DatosEjec.setBorder(0);
        documento.add(DatosEjec);
        
        
        for(int a=0; a<contador; a++){
            Table PasosEvidencia = new Table(1);
            Paragraph parrafo6 = null;
            try{
            parrafo6 = new Paragraph("Paso: "+Pasos.get(a));
            }catch(Exception e){
                parrafo6 = new Paragraph("Paso: Sin Registro");
            }
            Cell celda6 = new Cell();
            celda6.setBorder(0);
            celda6.add(parrafo6);
            celda6.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            PasosEvidencia.addCell(celda6);
            PasosEvidencia.setBorder(0);
            documento.add(PasosEvidencia);
            
            Table DatosEvidencia = new Table(1);
            Image imaEvi = null;
            Cell celdaImagenEvi = new Cell();
            try{
                imaEvi = Image.getInstance(rutaEvidencia+"\\"+this.fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)+"\\"+this.totalArchivos(new File(rutaEvidencia+"\\"+fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)))+"\\"+CasoPrueba+"\\evidencia"+(a+1)+".png"); 
                celdaImagenEvi.setBorder(0);
                celdaImagenEvi.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdaImagenEvi.add(imaEvi);
            }catch(Exception e){
                
            }
            
            
            DatosEvidencia.addCell(celdaImagenEvi);
            DatosEvidencia.setBorder(0);
            documento.add(DatosEvidencia);
        }
        
        
        documento.close();
    }
    
    public void crearXML(String CasoPrueba, String Resultado, int contador, List<String> Pasos, String rutaEvidencia, String navegador){
        try {
              DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
              DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
              //Elemento raíz
              org.w3c.dom.Document doc = docBuilder.newDocument();
              org.w3c.dom.Element suiteCasosPrueba = doc.createElement("SuiteCasosPrueba");
              doc.appendChild(suiteCasosPrueba);
              
              org.w3c.dom.Element casoPrueba = doc.createElement("CasoPrueba");
              suiteCasosPrueba.appendChild(casoPrueba);
              //Primer elemento
              org.w3c.dom.Element nombreTC = doc.createElement("Nombre");
              casoPrueba.appendChild(nombreTC);
              nombreTC.setTextContent(CasoPrueba);
              casoPrueba.appendChild(nombreTC);
              
              org.w3c.dom.Element moduloTC = doc.createElement("Módulo");
              moduloTC.setTextContent("Mantenimiento EAM");
              casoPrueba.appendChild(moduloTC);
              
              org.w3c.dom.Element nav = doc.createElement("Navegador");
              nav.setTextContent(navegador);
              casoPrueba.appendChild(nav);
              
              org.w3c.dom.Element fechaTC = doc.createElement("Fecha");
              fechaTC.setTextContent(this.fechaFormato());
              casoPrueba.appendChild(fechaTC);
              
              
              org.w3c.dom.Element resultadoTC = doc.createElement("Resultado");
              resultadoTC.setTextContent(Resultado);
              casoPrueba.appendChild(resultadoTC);
              
              org.w3c.dom.Element pasosTC = doc.createElement("PasosTC");
              resultadoTC.setTextContent(Resultado);
              casoPrueba.appendChild(pasosTC);
              
              for(int a=0; a<contador; a++){
                  org.w3c.dom.Element steps = doc.createElement("paso");
                  try{
                    steps.setTextContent("Paso: "+Pasos.get(a));
                    steps.setAttribute("ruta", rutaEvidencia+"\\"+this.fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)+"\\"+this.totalArchivos(new File(rutaEvidencia+"\\"+fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)))+"\\"+CasoPrueba+"\\evidencia"+(a+1)+".png");
                  }catch(Exception e){
                  }
                  pasosTC.appendChild(steps);
              }
              
              //Se escribe el contenido del XML en un archivo
              TransformerFactory transformerFactory = TransformerFactory.newInstance();
              Transformer transformer = transformerFactory.newTransformer();
              DOMSource source = new DOMSource(doc);
              StreamResult result = new StreamResult(new File(rutaEvidencia+"\\"+this.fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)+"\\"+this.totalArchivos(new File(rutaEvidencia+"\\"+fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)))+"\\"+CasoPrueba+"\\"+CasoPrueba+"-"+this.horaMinSeg()+".xml"));
              transformer.transform(source, result);
        } catch (ParserConfigurationException pce) {
          pce.printStackTrace();
        } catch (TransformerException tfe) {
          tfe.printStackTrace();
        }
    }
    
    public void crearHTML(String CasoPrueba, String Resultado, int contador, List<String> Pasos, String rutaEvidencia, String modulo, String version, String navegador) throws IOException{
        FileWriter filewriter = null;
        PrintWriter printw = null;
        String urlAbsoluta = rutaEvidencia+"//"+this.fechaFormato()+"//"+navegador+"//"+CasoPrueba.substring(0, 3)+"//"+this.totalArchivos(new File(rutaEvidencia+"//"+fechaFormato()+"//"+navegador+"//"+CasoPrueba.substring(0, 3)))+"//"+CasoPrueba+"//"+CasoPrueba;
        try{
            filewriter = new FileWriter(rutaEvidencia+"\\"+this.fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)+"\\"+this.totalArchivos(new File(rutaEvidencia+"\\"+fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)))+"\\"+CasoPrueba+"\\"+CasoPrueba+".html");//declarar el archivo
            printw = new PrintWriter(filewriter);//declarar un impresor

            printw.println("<html>");
            printw.println("<head><title>Reporte de Evidencia</title>");
            
            printw.println("<style>");
                printw.println(".exitosoAccordion {");
                  printw.println("background-color: #eee;");
                  printw.println("color: #444;");
                  printw.println("cursor: pointer;");
                  printw.println("padding: 18px;");
                  printw.println("width: 100%;");
                  printw.println("border: none;");
                  printw.println("-align: left;");
                  printw.println("outline: none;");
                  printw.println("font-size: 12px;");
                  printw.println("transition: 0.4s;");
                printw.println("}");
                
                printw.println(".fallaAccordion {");
                  printw.println("background-color: #f44336;");
                  printw.println("color: #FFF;");
                  printw.println("cursor: pointer;");
                  printw.println("padding: 18px;");
                  printw.println("width: 100%;");
                  printw.println("border: none;");
                  printw.println("-align: left;");
                  printw.println("outline: none;");
                  printw.println("font-size: 12px;");
                  printw.println("transition: 0.4s;");
                printw.println("}");
                
                printw.println(".ejecucionAccordion {");
                  printw.println("background-color: #ff9800;");
                  printw.println("color: #FFF;");
                  printw.println("cursor: pointer;");
                  printw.println("padding: 18px;");
                  printw.println("width: 100%;");
                  printw.println("border: none;");
                  printw.println("-align: left;");
                  printw.println("outline: none;");
                  printw.println("font-size: 12px;");
                  printw.println("transition: 0.4s;");
                printw.println("}");

                printw.println(".active, .exitosoAccordion:hover {");
                  printw.println("background-color: #ccc;");
                printw.println("}");
                
                printw.println(".activeEjecucion, .ejecucionAccordion:hover {");
                  printw.println("background-color: #e68a00;");
                printw.println("}");
                
                printw.println(".activeFalla, .fallaAccordion:hover {");
                  printw.println("background-color: #da190b;");
                printw.println("}");

                
                
                printw.println(".exitosoAccordion:after {");
                  //printw.println("content: '\002B';");
                  printw.println("color: #777;");
                  printw.println("font-weight: bold;");
                  printw.println("float: right;");
                  printw.println("margin-left: 5px;");
                printw.println("}");
                
                printw.println(".fallaAccordion:after {");
                  //printw.println("content: '\002B';");
                  printw.println("color: #da190b;");
                  printw.println("font-weight: bold;");
                  printw.println("float: right;");
                  printw.println("margin-left: 5px;");
                printw.println("}");
                
                printw.println(".ejecucionAccordion:after {");
                  //printw.println("content: '\002B';");
                  printw.println("color: #e68a00;");
                  printw.println("font-weight: bold;");
                  printw.println("float: right;");
                  printw.println("margin-left: 5px;");
                printw.println("}");

                printw.println(".active:after {");
                  //printw.println("content: \"\2212\";");
                printw.println("}");
                
                printw.println(".activeEjecucion:after {");
                  //printw.println("content: \"\2212\";");
                printw.println("}");
                
                printw.println(".activeFalla:after {");
                  //printw.println("content: \"\2212\";");
                printw.println("}");

                printw.println(".panel {");
                  printw.println("padding: 0 18px;");
                  printw.println("background-color: white;");
                  printw.println("max-height: 0;");
                  printw.println("overflow: hidden;");
                  printw.println("transition: max-height 0.2s ease-out;");
                printw.println("}");
                
                printw.println(".btn {");
                  printw.println("border: none; /* Remove borders */");
                  printw.println("color: white; /* Add a text color */");
                  printw.println("padding: 7px 14px; /* Add some padding */");
                  printw.println("cursor: pointer; /* Add a pointer cursor on mouse-over */");
                  
                printw.println("}");

                printw.println(".success {background-color: #4CAF50;} /* Green */");
                printw.println(".success:hover {background-color: #46a049;}");

                printw.println(".info {background-color: #2196F3;} /* Blue */");
                printw.println(".info:hover {background: #0b7dda;}");

                printw.println(".warning {background-color: #ff9800;} /* Orange */");
                printw.println(".warning:hover {background: #e68a00;}");

                printw.println(".danger {background-color: #f44336;} /* Red */");
                printw.println(".danger:hover {background: #da190b;}");
                
                printw.println("#tablatitulo { width: 50%; margin: 0 auto; -align: center;}");
                
                printw.println("#contenido { width: 100%; margin: 0 auto;font-size:12px;font-family: Georgia, 'Times New Roman', serif;}");
                printw.println("#encabezado { width: 100%; margin: 0 auto; background-color: #58ACFA; color: #FF0000;}");//#2196F3;#819FF7
                
                printw.println("#titulo {color: white;margin: 0;}");
                
                printw.println("a {font-size:10px;font-family: Georgia, 'Times New Roman', serif;}");
                
                printw.println("</style>");
                    
            printw.println("</head>");    
            //si queremos escribir una comilla " en el
            //archivo uzamos la diagonal invertida \"
            printw.println("<body>");

            //si quisieramos escribir una cadena que vide de una lista o
            //de una variable lo concatenamos
            //podemos añadir imagenes con codigo html
            printw.println("<div id=\"contenido\">");
            printw.println("<table id=\"encabezado\">");
            printw.println("<tr>");
            printw.println("<td>");
            printw.println("<img src=\""+rutaEvidencia+"\\gfa.png\" width=\"115\" height=\"30\">");
            printw.println("</td>");
            printw.println("<td>");
            printw.println("</td>");
            printw.println("<td>");
            printw.println("<p align=\"right\"><img src=\""+rutaEvidencia+"\\testingit.png\" width=\"115\" height=\"30\"></p>");
            printw.println("</td>");
            printw.println("</tr>");
            
//            printw.println("<tr height=\"50\">");
//            printw.println("</tr>");
            
            printw.println("<tr><center>");
            printw.println("<td colspan=\"3\">");
                printw.println("<center><table id=\"tablatitulo\">");
                printw.println("<tr>");
                printw.println("<td colspan=\"2\">");
                printw.println("<h2 class=\"titulo\">Reporte de Evidencia Pruebas Automatizadas</h2>");
                printw.println("</td>");
                printw.println("</tr>");
                printw.println("<tr>");
                printw.println("<td><a>Fecha Ejecución:</a></td>");
//                printw.println("<h5 class=\"titulo\">Fecha de Ejecución: </h3>");
//                printw.println("</td>");
                printw.println("<td><a>"+this.fechaFormato()+"</a></td>");
                //printw.println("<h5 class=\"titulo\">"+this.fechaFormato()+"</h5>");
                //printw.println("</td>");
                printw.println("</tr>");
                printw.println("<tr>");
                printw.println("<td><a>Módulo:</a></td>");
//                printw.println("<h5 class=\"titulo\">Módulo: </h5>");
//                printw.println("</td>");
                printw.println("<td><a>"+modulo+version+"</a></td>");
//                printw.println("<h5 class=\"titulo\">"+modulo+version+"</h5>");
//                printw.println("</td>");
                printw.println("</tr>");
                printw.println("<tr>");
                printw.println("<td><a>Navegador:</a></td>");
//                printw.println("<h5 class=\"titulo\">Navegador: </h5>");
//                printw.println("</td>");
                printw.println("<td><a>"+navegador+"</a></td>");
//                printw.println("<h5 class=\"titulo\">"+navegador+"</h5>");
//                printw.println("</td>");
                printw.println("</tr>");
                printw.println("<tr>");
                printw.println("<td><a>Caso de Prueba</a></td>");
//                printw.println("<h5 class=\"titulo\">Caso de Prueba: </h5>");
//                printw.println("</td>");
                printw.println("<td><a>"+CasoPrueba+"</a></td>");
//                printw.println("<h5 class=\"titulo\">"+CasoPrueba+"</h5>");
//                printw.println("</td>");
                printw.println("</tr>");
                printw.println("<tr>");
                printw.println("<td><a>Resultado:</a></td>");
//                printw.println("<h5 class=\"titulo\">Resultado: </h5>");
//                printw.println("</td>");
                printw.println("<td>");
                System.out.println(Resultado.substring(0, 7));
                int detalle=0;
                if("Fallido".equals(Resultado.substring(0, 7))){
                    printw.println("<button class=\"btn danger\">"+Resultado.substring(0, 7)+"</button>");
                    detalle++;
                    
                }
                if("Exitoso".equals(Resultado.substring(0, 7))){
                    printw.println("<button class=\"btn success\">"+Resultado.substring(0, 7)+"</button>");
                    
                }
                if(Resultado.length()>10){
                    if("Ejecución Fallida".equals(Resultado.substring(0, 17))){
                        printw.println("<button class=\"btn warning\">"+Resultado.substring(0, 17)+"</button>");
                        detalle++;
                    }
                }
                
                printw.println("</td>");
                printw.println("</tr>");
                printw.println("</table><center>");
            printw.println("</td>");
            printw.println("</center></tr>");
            printw.println("</tr>");
            printw.println("<tr height=\"25\">");
            printw.println("</tr>");
            printw.println("</table>");
            
            if(detalle>0){
                
                printw.println("<table>");
                printw.println("<tr>");
                printw.println("<td>");
                printw.println("<h4 class=\"titulo\">Detalle de la Ejecución: </h4>");
                printw.println("</td>");
                printw.println("</tr>");
                printw.println("<tr>");
                printw.println("<td>");
                printw.println("<a>"+Resultado+"</a>");
                printw.println("</td>");
                printw.println("</tr>");
                printw.println("<tr height=\"25\">");
                printw.println("</tr>");
                printw.println("</table>");
            }
            printw.println("<h3 class=\"titulo\">Pasos de Ejecución: </h3>");
            printw.println("<h5 class=\"titulo\">Presiona sobre el paso de ejecución para ver la evidencia.</h4>");
            for(int cont=0; cont<contador; cont++){
                if((cont+1)<contador){
                    try{
                        printw.println("<button class=\"exitosoAccordion\">"+Pasos.get(cont)+"</button>");
                    }catch(Exception e){
                        printw.println("<button class=\"exitosoAccordion\">Sin Paso</button>");
                    }
                    printw.println("<div class=\"panel\">");
                    try{
                        printw.println("<p><center><img src=\""+rutaEvidencia+"\\"+this.fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)+"\\"+this.totalArchivos(new File(rutaEvidencia+"\\"+fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)))+"\\"+CasoPrueba+"\\evidencia"+(cont+1)+".png\" width=\"100%\" height=\"100%\"></center></p>");
                    }catch(Exception e){
                    }
                    printw.println("</div>");
                }
                else{
                    if("Fallido".equals(Resultado.substring(0, 7))){
                        try{
                            printw.println("<button class=\"fallaAccordion\">"+Pasos.get(cont)+"</button>");
                        }catch(Exception e){
                            printw.println("<button class=\"fallaAccordion\">Sin Paso</button>");
                        }
                        printw.println("<div class=\"panel\">");
                        try{
                            printw.println("<p><center><img src=\""+rutaEvidencia+"\\"+this.fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)+"\\"+this.totalArchivos(new File(rutaEvidencia+"\\"+fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)))+"\\"+CasoPrueba+"\\evidencia"+(cont+1)+".png\" width=\"1000\" height=\"550\"></center></p>");
                        }catch(Exception e){
                        }
                        printw.println("</div>");
                    }
                    if("Exitoso".equals(Resultado.substring(0, 7))){
                        try{
                            printw.println("<button class=\"exitosoAccordion\">"+Pasos.get(cont)+"</button>");
                        }catch(Exception e){
                            printw.println("<button class=\"exitosoAccordion\">Sin Paso</button>");
                        }
                        printw.println("<div class=\"panel\">");
                        try{
                            printw.println("<p><center><img src=\""+rutaEvidencia+"\\"+this.fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)+"\\"+this.totalArchivos(new File(rutaEvidencia+"\\"+fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)))+"\\"+CasoPrueba+"\\evidencia"+(cont+1)+".png\" width=\"1000\" height=\"550\"></center></p>");
                        }catch(Exception e){
                        }
                        printw.println("</div>");
                    }
                    if(Resultado.length()>10){
                        if("Ejecución Fallida".equals(Resultado.substring(0, 17))){
                            try{
                                printw.println("<button class=\"ejecucionAccordion\">"+Pasos.get(cont)+"</button>");
                            }catch(Exception e){
                                printw.println("<button class=\"ejecucionAccordion\">Sin Paso</button>");
                            }
                            printw.println("<div class=\"panel\">");
                            try{
                                printw.println("<p><center><img src=\""+rutaEvidencia+"\\"+this.fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)+"\\"+this.totalArchivos(new File(rutaEvidencia+"\\"+fechaFormato()+"\\"+navegador+"\\"+CasoPrueba.substring(0, 3)))+"\\"+CasoPrueba+"\\evidencia"+(cont+1)+".png\" width=\"1000\" height=\"550\"></center></p>");
                            }catch(Exception e){
                            }
                            printw.println("</div>");

                        }
                    } 
                }
            }
            
            printw.println("</div>");
            
            printw.println("<script>");
            printw.println("var acc = document.getElementsByClassName(\"exitosoAccordion\");");
            printw.println("var i;");

            printw.println("for (i = 0; i < acc.length; i++) {");
              printw.println("acc[i].addEventListener(\"click\", function() {");
                printw.println("this.classList.toggle(\"active\");");
                printw.println("var panel = this.nextElementSibling;");
                printw.println("if (panel.style.maxHeight) {");
                  printw.println("panel.style.maxHeight = null;");
                printw.println("} else {");
                  printw.println("panel.style.maxHeight = panel.scrollHeight + \"px\";");
                printw.println("} ");
              printw.println("});");
            printw.println("}");
            printw.println("</script>");
            
            printw.println("<script>");
            printw.println("var acc = document.getElementsByClassName(\"ejecucionAccordion\");");
            printw.println("var i;");

            printw.println("for (i = 0; i < acc.length; i++) {");
              printw.println("acc[i].addEventListener(\"click\", function() {");
                printw.println("this.classList.toggle(\"activeEjecucion\");");
                printw.println("var panel = this.nextElementSibling;");
                printw.println("if (panel.style.maxHeight) {");
                  printw.println("panel.style.maxHeight = null;");
                printw.println("} else {");
                  printw.println("panel.style.maxHeight = panel.scrollHeight + \"px\";");
                printw.println("} ");
              printw.println("});");
            printw.println("}");
            printw.println("</script>");
            
            printw.println("<script>");
            printw.println("var acc = document.getElementsByClassName(\"fallaAccordion\");");
            printw.println("var i;");

            printw.println("for (i = 0; i < acc.length; i++) {");
              printw.println("acc[i].addEventListener(\"click\", function() {");
                printw.println("this.classList.toggle(\"activeFalla\");");
                printw.println("var panel = this.nextElementSibling;");
                printw.println("if (panel.style.maxHeight) {");
                  printw.println("panel.style.maxHeight = null;");
                printw.println("} else {");
                  printw.println("panel.style.maxHeight = panel.scrollHeight + \"px\";");
                printw.println("} ");
              printw.println("});");
            printw.println("}");
            printw.println("</script>");
            
            printw.println("</body>");
            printw.println("</html>");

            //no devemos olvidar cerrar el archivo para que su lectura sea correcta
            printw.close();//cerramos el archivo

            System.out.println("Generado exitosamente");//si todo sale bien mostramos un mensaje de guardado exitoso
            
            this.archivoTexto(CasoPrueba, Resultado, rutaEvidencia, modulo, version, navegador, urlAbsoluta);
            this.reporteGeneral(rutaEvidencia, navegador, modulo);
           }catch(IOException e){
               System.out.println("Error: "+e);
           }
    }
    
    
    public void archivoTexto(String CasoPrueba, String Resultado, String rutaEvidencia, String modulo, String version, String navegador, String EvidenciaAbsoluta){

        FileWriter filewriter = null;
        PrintWriter printw = null;
        try{
            filewriter = new FileWriter(rutaEvidencia+"//"+fechaFormato()+"//ejecucion"+navegador+fechaFormato()+".txt",true);
            printw = new PrintWriter(filewriter);//declarar un impresor            
            printw.println(CasoPrueba);
            if("Fallido".equals(Resultado.substring(0, 7))){
                printw.println(Resultado.substring(0, 7));
            }
            if("Exitoso".equals(Resultado.substring(0, 7))){
                printw.println(Resultado.substring(0, 7));
            }
            if(Resultado.length()>10){
                if("Ejecución Fallida".equals(Resultado.substring(0, 17))){
                   printw.println(Resultado.substring(0, 17)); 
                }
            } 
            printw.println(EvidenciaAbsoluta);
            printw.close();
        }catch(IOException e){
            e.printStackTrace();
        } 
    }
    
    public void reporteGeneral(String rutaEvidencia, String navegador, String modulo){
        
        FileWriter filewriter = null;
        PrintWriter printw = null;
        try{
            filewriter = new FileWriter(rutaEvidencia+"//"+fechaFormato()+"//ReporteGeneral"+fechaFormato()+".html");
            printw = new PrintWriter(filewriter);
            printw.println("<!DOCTYPE html>");
            printw.println("<html>");// lang=\"es-ES\">");
            printw.println("<head>");
            //printw.println("<meta charset=\"utf-8\">");
            printw.println("<title>FrameWork Testing iT</title>");
            printw.println("<style>");
            printw.println("* {margin:0; padding:0;}");
            printw.println("a:link, a:visited, a:hover, a:active {color:#0f0;font-size:16px;}");
            printw.println("body {background:#eee;font-family:verdana;}");
            printw.println("h1 {color:#fff;font-size:24px;}");
            printw.println("p {font-size:10px;}");
            printw.println("ul {list-style-type:none;}");
            printw.println("#cabecera {color:#fff;background-color:#000;padding:10px;}");
            printw.println("#cabeceraMenu {color:#999;background-color:#000;padding:20px;text-align:center;float:center;}");
            printw.println("#contenedor {margin:0 auto;width:100%;}");
            printw.println("#contenido {background-color:#fff;width:100%;height:450px;}");
            printw.println(".divDetalle {background-color:#fff;float:center;width:100%;border:none;height:450px;}");
            printw.println(".frameDetalle {background-color:#fff;float:center;width:100%;border:none;height:100%;}");
            printw.println("#menu {background-color:#999;float:left;height:450px;width:20%;}");
            printw.println("#pie {background-color:#bbb;clear:both;color:#fff;padding:10px;text-align:center;}");
            
            printw.println(".btn {");
            printw.println("border: none; /* Remove borders */");
            printw.println("color: white; /* Add a text color */");
            printw.println("padding: 8px 16px; /* Add some padding */");
            printw.println("cursor: pointer; /* Add a pointer cursor on mouse-over */");
            printw.println("border-radius: 35px;");
            printw.println("}");

            printw.println(".navegadores {background-color: #999;} /* Gris */");
            printw.println(".navegadores:hover {background-color: #ddd;}");
            printw.println(".navegadores:focus {outline: none;}");
            
            printw.println(".succes {background-color: #4CAF50;} /* Green */");
            printw.println(".succes:hover {background-color: #46a049;}");

            printw.println(".info {background-color: #2196F3;} /* Blue */");
            printw.println(".info:hover {background: #0b7dda;}");

            printw.println(".warning {background-color: #ff9800;width: 100%;} /* Orange */");
            printw.println(".warning:hover {background: #e68a00;}");
            printw.println(".warning:focus {outline: none;}");

            printw.println(".danger {background-color: #f44336;width: 100%;} /* Red */");
            printw.println(".danger:hover {background: #da190b;}");
            printw.println("</style>");
            printw.println("</head>");
            printw.println("<body>");
            printw.println("<div id=\"contenedor\">");
            
            
            printw.println("<div id=\"cabecera\">");
            printw.println("<center><h1>Reporte General de Ejecuciones</h1></center>");
            printw.println("</div>");
            
            printw.println("<div id=\"cabeceraMenu\">");
            
            printw.println("<button class=\"btn navegadores\" onClick=\"\"><img src=\"C:\\ambiente\\imagenes\\home.png\" width=\"25\" height=\"25\"/></button>");
            printw.println("<button class=\"btn navegadores\" onClick=\"chrome()\"><img src=\"C:\\ambiente\\imagenes\\chrome.png\" width=\"25\" height=\"25\"/></button>");
            printw.println("<button class=\"btn navegadores\" onClick=\"firefox()\"><img src=\"C:\\ambiente\\imagenes\\firefox.png\" width=\"25\" height=\"25\"/></button>");
            printw.println("<button class=\"btn navegadores\" onClick=\"\"><img src=\"C:\\ambiente\\imagenes\\edge.png\" width=\"25\" height=\"25\"/></button>");
            printw.println("<button class=\"btn navegadores\" onClick=\"\"><img src=\"C:\\ambiente\\imagenes\\safari.png\" width=\"25\" height=\"25\"/></button>");
            printw.println("<button class=\"btn navegadores\" onClick=\"\"><img src=\"C:\\ambiente\\imagenes\\ie.png\" width=\"25\" height=\"25\"/></button>");
            
            printw.println("</div>");
            
            printw.println("<div id=\"contenido\">");
            printw.println("<div id=\"contenidoChrome\" class=\"divDetalle\" style=\"display: none;\" >");
            printw.println("<iframe id=\"frameDetalle"+navegador+"\" class=\"frameDetalle\" src=\"chrome.html\"></iframe>");
            printw.println("</div>");
            printw.println("<div id=\"contenidoFirefox\" class=\"divDetalle\" style=\"display: none;\">");
            printw.println("<iframe id=\"frameDetalle"+navegador+"\" class=\"frameDetalle\" src=\"firefox.html\"></iframe>");
            printw.println("</div>");
            printw.println("</div>");
            printw.println("</div>");
            printw.println("<footer>");
            printw.println("<div id=\"pie\">");
            printw.println("<p>Testing IT Consulting S.A. de C.V. © | Todos los Derechos Reservados</p>");
            printw.println("</div>");
            printw.println("</footer>");
            
            
            printw.println("<script>");
            printw.println("function firefox() {");
            printw.println("document.getElementById('contenidoChrome').style.display='none';");
            printw.println("document.getElementById('contenidoFirefox').style.display='block';");
            printw.println("}");
            printw.println("function chrome() {");
            printw.println("document.getElementById('contenidoFirefox').style.display='none';");
            printw.println("document.getElementById('contenidoChrome').style.display='block';");
            printw.println("}");
            printw.println("</script>");
            
            printw.println("</body>");
            printw.println("</html>");
            printw.close();
            this.reportePagesNavegador(rutaEvidencia, navegador, modulo);
            this.reportePagesNavegador(rutaEvidencia, navegador, modulo);
        }catch(IOException e){
        }
    }
    
    public void reportePagesNavegador(String rutaEvidencia, String navegador, String modulo){
        
        FileWriter filewriter = null;
        PrintWriter printw = null;
        try{
            filewriter = new FileWriter(rutaEvidencia+"//"+fechaFormato()+"//"+navegador+".html");
            printw = new PrintWriter(filewriter);
            printw.println("<!DOCTYPE html>");
//            
            printw.println("<html>");
            printw.println("<head><title></title>");
            
            printw.println("<style>");
            
            printw.println("body {overflow-x:hidden;}");
            printw.println(".btn {");
            printw.println("border: none; /* Remove borders */");
            
            printw.println("color: white; /* Add a text color */");
            printw.println("cursor: pointer; /* Add a pointer cursor on mouse-over */");
            printw.println("}");
            
            printw.println(".imahtml {background-color:transparent;width:37px;height:37px;float:center;} /* transparente */");
            printw.println(".imahtml:hover {background:#fff;}");
            
            printw.println(".info {background-color: #2196F3;padding: 15px 10px;} /* Blue */");
            printw.println(".info:hover {background: #0b7dda;}");
            
            printw.println(".warning {background-color: #999;padding: 8px 16px;} /* Orange */");
            printw.println(".warning:hover {background: #ddd;}");
            
            printw.println("#cabeceraMenu {color:#999;background-color:#fff;padding:20px;text-align:center;float:center;width:98%;}");
            printw.println("#contenedor {margin:0 auto;width:100%;font-size:12px;font-family: Georgia, 'Times New Roman', serif;}");
            printw.println("#contenido {background-color:#fff;float:center;height:400px;width:60%;overflow:hidden;}");
            printw.println("#frameDetalle {background-color:#fff;float:center;height:100%;width:95%;border:none;overflow:hidden;}");
            printw.println("#menu {background-color:#fff;color:#999;float:left;height:400px;width:40%;}");
            
//            printw.println(".table-fixed {width: 97%;display: block;}");
            printw.println("tbody {height: 300px;overflow-y:auto;display: block;}");
////            printw.println(".table-fixed thead,.table-fixed tbody,.table-fixed tfoot,.table-fixed tr,.table-fixed td,.table-fixed th {display: block;}");
//            printw.println(".table-fixed tbody td,.table-fixed tfoot > tr> td{float: left;border-bottom-width: 0px;width:28%;height:100%;}");
//            printw.println(".table-fixed thead > tr> th,{float: center;border-bottom-width: 0px;}");
            //printw.println("td {width:150px;}");
            
            printw.println("</style>");
                    
            printw.println("</head>");    
            printw.println("<body>");
            
            printw.println("<div id=\"contenedor\">");
            printw.println("<div id=\"menu\">");
            Scanner input = new Scanner(new File("C:\\evidencia\\"+this.fechaFormato()+"\\ejecucion"+navegador+this.fechaFormato()+".txt"));
            if("firefox".equals(navegador)){
                printw.println("<center><img src=\"C:\\ambiente\\imagenes\\firefox.png\" width=\"30\" height=\"30\"/></center>");
            }
            if("chrome".equals(navegador)){
                printw.println("<center><img src=\"C:\\ambiente\\imagenes\\chrome.png\" width=\"30\" height=\"30\"/></center>");
            }
            printw.println("<center><h3>Lista de Ejecuciones "+navegador+"</h3>");
            printw.println("<form>Busqueda: <input id=\"txtBusqueda\" type=\"text\" onkeyup=\"Buscar();\" /></form></center>");
            printw.println("<table id=\"tblEjecuciones\">"); //class=\"table table-fixed\">");
            printw.println("<tbody>"); //height=\"300\" overflow-y=\"auto\">");//<thead><tr><th>Casos de Prueba</th><th>Estatus</th><th>HTML</th><th>PDF</th></tr></thead><tbody>");
            int a = 1;
            while (input.hasNextLine()) {
                String linea = input.nextLine();
                if(a==1){
                    printw.println("<tr>");
                    printw.println("<td width=\"320\">"+linea+"</td>");
                }
                if(a==2){
                    if("Exitoso".equals(linea)){
                        printw.println("<td width=\"100\">Exitoso</td>");//<img src=\"C:\\ambiente\\imagenes\\paloma.png\" width=\"20\" height=\"25\"/></td>");
                    }
                    if("Fallido".equals(linea.substring(0, 7))){
                        printw.println("<td width=\"100\">Fallido</td>");//<img src=\"C:\\ambiente\\imagenes\\tacha.png\" width=\"20\" height=\"25\"/></td>");
                    }
                    if(linea.length()>10){
                        if("Ejecución Fallida".equals(linea.substring(0, 17))){
                            printw.println("<td width=\"100\">Ejecución Fallida</td>");//<img src=\"C:\\ambiente\\imagenes\\warning.png\" width=\"15\" height=\"15\"/></td>");
                        }
                    }    
                }
                if(a==3){                                    
                    printw.println("<td width=\"35\"><button class=\"btn imahtml\" onClick=\"Frame('"+linea+".html')\"><img src=\"C:\\ambiente\\imagenes\\html.png\" width=\"25\" height=\"30\"/></button></td>");                                  
                    printw.println("<td width=\"35\"><button class=\"btn imahtml\" onClick=\"Frame('"+linea+".pdf')\"><img src=\"C:\\ambiente\\imagenes\\pdf.png\" width=\"25\" height=\"30\"/></button></td>");
                    printw.println("</tr>");
                    a=1;
                }else{
                    a++;
                }              
            }
            printw.println("</tbody></table>"); 
            input.close();
            printw.println("</div>");
            printw.println("<div id=\"contenido\">");
            //printw.println("<center><h2 class=\"titulo\">Resultados en "+navegador+"</h2><center>");
            printw.println("<iframe id=\"frameDetalle\"></iframe>");
            printw.println("</div>");
            printw.println("</div>");
                     
            printw.println("<script>");
            
            printw.println("function Frame(ruta){");
            printw.println("var iframe = document.getElementById('frameDetalle');");
            printw.println("iframe.src = ruta;");
//            printw.println("var menu = document.getElementById('menu');");
//            printw.println("var contenido = document.getElementById('contenido');");
//            printw.println("contenido.style.height = menu.clientHeight;");
            printw.println("}");
            
            printw.println("function Buscar() {");
            printw.println("var tabla = document.getElementById('tblEjecuciones');");
            printw.println("var busqueda = document.getElementById('txtBusqueda').value.toLowerCase();");
            printw.println("var cellsOfRow=\"\";var found=false;var compareWith=\"\";");
            printw.println("for (var i = 1; i < tabla.rows.length; i++) {");
            printw.println("cellsOfRow = tabla.rows[i].getElementsByTagName('td');");
            printw.println("found = false;");
            printw.println("for (var j = 0; j < cellsOfRow.length && !found; j++) { compareWith = cellsOfRow[j].innerHTML.toLowerCase(); if (busqueda.length == 0 || (compareWith.indexOf(busqueda) > -1))");
            printw.println("{found = true;}}");
            printw.println("if(found){tabla.rows[i].style.display = '';} else {");
            printw.println("tabla.rows[i].style.display = 'none';");
            printw.println("}}}");
            printw.println("// ]]></script>");
            
            
            printw.println("</script>");
            
            printw.println("</body>");
            printw.println("</html>");
            printw.close();
        }catch(IOException e){
        }
    }
}
