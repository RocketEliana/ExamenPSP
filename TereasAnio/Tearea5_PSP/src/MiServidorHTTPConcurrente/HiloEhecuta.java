package MiServidorHTTPConcurrente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Maneja la petición HTTP de un cliente en un hilo separado.
 * (Versión adaptada a tu Pagina.java sin métodos de ayuda).
 */
public class HiloEhecuta extends Thread {

    private Socket s;
    // Define el salto de línea HTTP estándar (Obligatorio)
    private static final String CRLF = "\r\n";

    public HiloEhecuta(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        // Usamos try-with-resources para que los streams se cierren solos
        try (InputStreamReader isr = new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr);
             PrintWriter pr = new PrintWriter(s.getOutputStream())) {

            // 1. LEER LA PETICIÓN
            String primeraLinea = br.readLine();

            // Bucle de lectura de cabeceras (¡CORREGIDO!)
            String headerLine;
            while ((headerLine = br.readLine()) != null && !headerLine.isEmpty()) {
                // Leyendo cabeceras...
            }

            // 2. VALIDAR LA PETICIÓN
            if (primeraLinea == null || !primeraLinea.startsWith("GET")) {

                // --- Respuesta 400 Bad Request (en línea) ---
                String htmlError = Pagina.no_encuentra;
                // Calculamos la longitud en bytes aquí mismo
                int longitud = htmlError.getBytes(StandardCharsets.UTF_8).length;
                
                pr.print(Mensaje.BAD_REQUEST + CRLF);
                pr.print(Pagina.CABECERA_HTML + CRLF); // De tu Pagina.java
                pr.print("Content-Length: " + longitud + CRLF);
                pr.print("Connection: close" + CRLF);
                pr.print(CRLF); // Línea vacía
                pr.print(htmlError);
                pr.flush();
                // ---------------------------------------------
                return; // Salir del método run()
            }

            // Obtenemos la ruta (ej: "/" o "/binding")
            String[] rutas = primeraLinea.trim().split(" ");
            
            // Añadimos una comprobación para evitar ArrayIndexOutOfBounds
            if (rutas.length < 2) {
                 // Reenviamos como 400 Bad Request
                String htmlError = Pagina.no_encuentra;
                int longitud = htmlError.getBytes(StandardCharsets.UTF_8).length;
                pr.print(Mensaje.BAD_REQUEST + CRLF);
                pr.print(Pagina.CABECERA_HTML + CRLF);
                pr.print("Content-Length: " + longitud + CRLF);
                pr.print("Connection: close" + CRLF);
                pr.print(CRLF);
                pr.print(htmlError);
                pr.flush();
                return;
            }
            String caso = rutas[1];

            // 3. ENVIAR RESPUESTA SEGÚN LA RUTA
            switch (caso) {
                case "/" -> {
                    // --- Respuesta 200 OK para "/" ---
                    String html = Pagina.index;
                    int longitud = html.getBytes(StandardCharsets.UTF_8).length;

                    pr.print(Mensaje.OK + CRLF);
                    pr.print(Pagina.CABECERA_HTML + CRLF);
                    pr.print("Content-Length: " + longitud + CRLF);
                    pr.print("Connection: close" + CRLF);
                    pr.print(CRLF); // Línea vacía
                    pr.print(html);
                    pr.flush();
                    // -----------------------------------
                }

                case "/binding" -> { // <-- Actualizado a tu Pagina.java
                    // --- Respuesta 200 OK para "/binding" ---
                    String html = Pagina.binding;
                    int longitud = html.getBytes(StandardCharsets.UTF_8).length;

                    pr.print(Mensaje.OK + CRLF);
                    pr.print(Pagina.CABECERA_HTML + CRLF);
                    pr.print("Content-Length: " + longitud + CRLF);
                    pr.print("Connection: close" + CRLF);
                    pr.print(CRLF); // Línea vacía
                    pr.print(html);
                    pr.flush();
                    // ---------------------------------------
                }

                default -> {
                    // --- Respuesta 404 Not Found ---
                    String htmlError = Pagina.no_encuentra;
                    int longitud = htmlError.getBytes(StandardCharsets.UTF_8).length;

                    pr.print(Mensaje.NOT_FOUND + CRLF);
                    pr.print(Pagina.CABECERA_HTML + CRLF);
                    pr.print("Content-Length: " + longitud + CRLF);
                    pr.print("Connection: close" + CRLF);
                    pr.print(CRLF); // Línea vacía
                    pr.print(htmlError);
                    pr.flush();
                    // ---------------------------------
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(HiloEhecuta.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Aseguramos que el socket se cierra al final
            try {
                if (s != null) {
                    s.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}