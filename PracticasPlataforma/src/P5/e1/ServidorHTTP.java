package P5.e1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class ServidorHTTP extends Thread{

private Socket s;
//ARRANCA EL SERVIDOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!xDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
    public ServidorHTTP(Socket s) {
        this.s = s;
    }

     public static void main(String[] args) throws IOException, Exception {

    //Asociamos al servidor el puerto 8066
    ServerSocket socServidor = new ServerSocket(8066);
    String arranque=imprimeDisponible();
         System.out.println(arranque);

    //ante una petición entrante, procesa la petición por el socket cliente
    //por donde la recibe
    while (true) {
      Socket socket =socServidor.accept();
      ServidorHTTP servidor=new ServidorHTTP(socket);
      servidor.start();
    }
  }

    @Override
    public void run() {
         String peticion;
         String html;
        String fechaHTTP = ZonedDateTime.now().format(
    DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
);
    try ( //Flujo de entrada
        BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter printWriter = new PrintWriter(s.getOutputStream(), true);
            ){
         peticion = br.readLine();
         if (peticion == null || !peticion.startsWith("GET")) {
            return;
        }

  String[] peticiones = peticion.split("\\s+");
     if (peticiones.length < 2) return; // Seguridad extra
        String ruta = peticiones[1];

        String lineaEstado;
        if (ruta.equals("/")) {
            html = Paginas.html_index;
            lineaEstado = Mensajes.lineaInicial_OK;
        } else if (ruta.equals("/quijote")) {
            html = Paginas.html_quijote;
            lineaEstado = Mensajes.lineaInicial_OK;
        } else {
            html = Paginas.html_noEncontrado;
            lineaEstado = Mensajes.lineaInicial_NotFound;
        }

        // Respuesta HTTP exacta
        printWriter.println(lineaEstado); 
        printWriter.println(Paginas.primeraCabecera);
        printWriter.println("Date: " + fechaHTTP);
        printWriter.println("Content-Length: " + html.length());
        printWriter.println(); // Línea en blanco obligatoria
        printWriter.print(html);
       //linea estado,cabecera,salto linea,html HTTP/1.1 404 Not Found HTTP/1.1 200 OK
        
       
    
    } catch (IOException ex) {
        Logger.getLogger(ServidorHTTP.class.getName()).log(Level.SEVERE, null, ex);
    
    } finally {
            try {
                if (s != null) s.close(); // ESTA ES LA LÍNEA CLAVE
                System.out.println("Conexión cerrada.");
            } catch (IOException ex) {
                System.err.println("Error al cerrar socket: " + ex.getMessage());
            }
    }    
    }
     
  private static String imprimeDisponible() {

   String dfisponible= "El Servidor WEB se está ejecutando y permanece a la "
            + "escucha por el puerto 8066.\nEscribe en la barra de direcciones "
            + "de tu explorador preferido:\n\nhttp://localhost:8066\npara "
            + "solicitar la página de bienvenida\n\nhttp://localhost:8066/"
            + "quijote\n para solicitar una página del Quijote,\n\nhttp://"
            + "localhost:8066/q\n para simular un error";
   return dfisponible;
  }

    
}


    //si realmente se trata de una petición 'GET' (que es la única que vamos a
    //implementar en nuestro Servidor)
    //separador
       
        
         /*GET /pagina-ejemplo.html HTTP/1.1
            Host: www.servidor.com
            User-Agent: Mozilla/5.0
            Accept: text/html
            Connection: keep-alive*/
          //extrae la subcadena entre 'GET' y 'HTTP/1.1'
    //  peticion = peticion.substring(3, peticion.lastIndexOf("HTTP"));
    /*HTTP/1.1 200 OK 
     Content-Type: text/html; charset=UTF-8 
     Content-Length: 1024 
     Date: Sat, 31 Jan 2026 15:30:00 GMT 
 
<html><head>...</head><body>...</body></html> 
 */
    /*Característica,""" "" (Espacio simple)","""\\s+"" (Expresión regular)"
¿Qué busca?,Exactamente un carácter de espacio (ASCII 32).,"Uno o más caracteres de espacio (espacios, tabs \t, saltos de línea \n)."
Espacios múltiples,"Crea elementos vacíos en el array (ej: ""  "" → ["""", """"]).",Los colapsa todos y los trata como un solo separador.
Tabulaciones,No las reconoce como separadores.,Las reconoce y divide la cadena por ellas.*/
    
