package TestCases;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import com.inflectra.spiratest.addons.junitextension.*;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebDriver;
import steps.steps_Facturas_Facturacion;

@SpiraTestConfiguration(
	    url="https://testing-it.spiraservice.net",
	    login="automationQA",
	    password="test1234", 
	    projectId=67,
	    testSetId=981
	)

public class Test_Facturas_Facturacion extends steps_Facturas_Facturacion{
    public Properties Config = null;
    public Properties Datos = null;
    public Properties Elementos = null;
    private RemoteWebDriver driver = null;
    public List<String> Pasos = new ArrayList<String>();
    public int contador = 0;
    public String Resultado = "";
    public String Escenario = "";
    public String RutaEvidencia = "";
    public String Navegador="";
    
    
    @Before
    public void PrepararEjecucion() throws FileNotFoundException, MalformedURLException, InterruptedException{
        Config = this.getPropetiesFile("configuracion\\configuracion.properties");
        Datos = this.getPropetiesFile("configuracion\\datosFacturarFacturacion.properties");
        Elementos = this.getPropetiesFile("configuracion\\pageFacturarFacturacion.properties");
        contador = 1;
        RutaEvidencia = Config.getProperty("rutaEvidencia");
        Resultado = "Fallido";
        Navegador = Config.getProperty("Navegador");
        driver = this.openGridBrowser(Navegador, Config);
        
    }
    
    @Test
    @SpiraTestCase(testCaseId=9163)
    public void Test_Facturar_Token_No_Valido() throws InterruptedException, DocumentException, BadElementException, IOException, Exception {
        try{
            Escenario = "FAC_Facturar_Facturar_Boletos.";
            Navegador = this.navegador(driver.toString());
            
            //Paso 1
            Pasos.add(contador+".- Abrir navegador en la URL: "+Config.getProperty("urlAppFacturas"));
            this.ingresar_A_URL(driver, contador, Config, Escenario, Navegador);
            
            //Paso 2
            contador++;
            Pasos.add(contador+".- Presionar el botón: Facturación.");
            this.presionar_Facturacion(driver, contador, Config, Elementos, Escenario, Navegador);
            
            //paso 3
            contador++;
            Pasos.add(contador+".- Seleccionar servicio: FACTURACIÓN DE BOLETOS.");
            this.seleccionar_combo_servicios(driver, Resultado, contador, Config, Elementos, Escenario, Navegador);
            
            //paso 4
            contador++;
            Pasos.add(contador+".- Presionar el botón: Continuar.");
            this.presionar_Continuar(driver, contador, Config, Elementos, Escenario, Navegador);
            
            //paso 5
            contador++;
            Pasos.add(contador+".- Presionar el botón: Agregar Token.");
            this.presionar_Facturacion(driver, contador, Config, Elementos, Escenario, Navegador);
            
            //paso 6
            contador++;
            Pasos.add(contador+".- Validar el mensaje: "+Datos.getProperty("mensajeAssert"));
            Resultado = this.validar_Mensaje(driver, Datos, Config, Elementos, contador, Escenario, Navegador);
            
        }catch(NoSuchElementException s){
            Resultado = "Ejecución Fallida, No se encontró elemento: "+s;
            this.capturarEvidencia(driver, Config, contador, Escenario, Navegador);
        }catch(InterruptedException e){
            Resultado = "Ejecución Fallida: "+e;
            this.capturarEvidencia(driver, Config, contador, Escenario, Navegador);
        }finally{
            this.finalizarTestCase(driver, Escenario, Resultado, contador, Pasos, RutaEvidencia, Config.getProperty("Modulo"), Config.getProperty("Version"), Navegador);
            if(!"Exitoso".equals(Resultado.substring(0, 7))){
                throw new Exception("Navegador: "+Navegador + "\n Resultado: " + Resultado);
            }
        }
    }
    
    
    @After
    public void cerrarTest(){
        this.cerrar_Navegador(driver);
    }

    

    
}
