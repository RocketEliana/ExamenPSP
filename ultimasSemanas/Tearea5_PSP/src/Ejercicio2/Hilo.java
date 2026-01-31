/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Hilo extends Thread {
    private Socket s;

    public Hilo(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
         String peticion = null;
    String html;

    //Flujo de entrada
    InputStreamReader inSR = null;
        try {
            inSR = new InputStreamReader(
                    s.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    //espacio en memoria para la entrada de peticiones
    BufferedReader bufLeer = new BufferedReader(inSR);

    //objeto de java.io que entre otras características, permite escribir 
    //'línea a línea' en un flujo de salida
    PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(
                    s.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //mensaje petición cliente (primera línea)
            peticion = bufLeer.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    // IMPORTANTE: Leer todos los headers del cliente hasta la línea en blanco
    // Los navegadores envían múltiples headers (Host, User-Agent, etc.)
    String linea;
        try {
            while ((linea = bufLeer.readLine()) != null && !linea.isEmpty()) {
                // Leer y descartar los headers adicionales
                // Esto es necesario para que el navegador no se quede esperando
            }   } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }

    //para compactar la petición y facilitar así su análisis, suprimimos todos 
    //los espacios en blanco que contenga
    peticion = peticion.replaceAll(" ", "");

    //si realmente se trata de una petición 'GET' (que es la única que vamos a
    //implementar en nuestro Servidor)
    if (peticion.startsWith("GET")) {
      //extrae la subcadena entre 'GET' y 'HTTP/1.1'
      peticion = peticion.substring(3, peticion.lastIndexOf("HTTP"));

      //si corresponde a la página de inicio
      if (peticion.length() == 0 || peticion.equals("/")) {
        //sirve la página
        html = Paginas.html_index;
        printWriter.println(Mensajes.lineaInicial_OK);
        printWriter.println(Paginas.primeraCabecera);
        printWriter.println("Content-Length: " + html.length()); // Sin el +1
        printWriter.println(); // Línea en blanco (sin \n adicional)
        printWriter.println(html);
        printWriter.flush(); // Asegurar que se envía todo
      } //si corresponde a la página del Quijote
      else if (peticion.equals("/quijote")) {
        //sirve la página
        html = Paginas.html_quijote;
        printWriter.println(Mensajes.lineaInicial_OK);
        printWriter.println(Paginas.primeraCabecera);
        printWriter.println("Content-Length: " + html.length()); // Sin el +1
        printWriter.println(); // Línea en blanco
        printWriter.println(html);
        printWriter.flush();
      } //en cualquier otro caso
      else {
        //sirve la página
        html = Paginas.html_noEncontrado;
        printWriter.println(Mensajes.lineaInicial_NotFound);
        printWriter.println(Paginas.primeraCabecera);
        printWriter.println("Content-Length: " + html.length()); // Sin el +1
        printWriter.println(); // Línea en blanco
        printWriter.println(html);
        printWriter.flush();
      }
    }

      
    }
    
    
}
