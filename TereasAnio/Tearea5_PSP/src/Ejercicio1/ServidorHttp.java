package Ejercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Servidor HTTP Secuencial (no usa hilos).
 * (Versión corregida).
 */
public class ServidorHttp {

    // Define el salto de línea HTTP estándar
    private static final String CRLF = "\r\n";

    public static void main(String[] args) throws IOException, Exception {
        ServerSocket socServidor = new ServerSocket(8066);
        imprimeDisponible();
        Socket socCliente;

        while (true) {
            socCliente = socServidor.accept();
            System.out.println("Atendiendo al cliente ");
            procesaPeticion(socCliente);
            //cierra la conexión entrante
            socCliente.close();
            System.out.println("cliente atendido");
        }
    }

    private static void procesaPeticion(Socket socketCliente) throws IOException {
        String html;

        // Flujo de entrada con Charset UTF-8
        InputStreamReader inSR = new InputStreamReader(
                socketCliente.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader bufLeer = new BufferedReader(inSR);

        // PrintWriter SIN autoFlush para tener control manual
        PrintWriter printWriter = new PrintWriter(
                socketCliente.getOutputStream());

        // --- LECTURA DE PETICIÓN (CORREGIDA) ---
        
        // 1. Leemos la primera línea (ej: "GET /quijote HTTP/1.1")
        String primeraLinea = bufLeer.readLine();

        // 2. Leemos el resto de cabeceras hasta la línea vacía
        String linea;
        while ((linea = bufLeer.readLine()) != null && !linea.isEmpty()) {
            // Descartamos las cabeceras, no las usamos
        }

        // 3. Validamos y parseamos la primera línea (¡ESTA ES LA CORRECCIÓN GRANDE!)
        if (primeraLinea == null || primeraLinea.isEmpty() || !primeraLinea.startsWith("GET")) {
            // Si no es GET o está vacía, es un 400 Bad Request
            html = Pagina.html_noEncontrado;
            printWriter.print(Mensaje.lineaInicial_NotFound + CRLF);
            printWriter.print("Date: " + Pagina.fechaHora() + CRLF);
            printWriter.print(Pagina.CONTENT_TYPE_HTML + CRLF);
            printWriter.print("Content-Length: " + Pagina.getLengthEnBytes(html) + CRLF);
            printWriter.print("Connection: close" + CRLF);
            printWriter.print(CRLF); // Línea vacía
            printWriter.print(html);
            printWriter.flush();
            return;
        }

        // Forma robusta de obtener la ruta: dividir por espacios
        String[] partes = primeraLinea.trim().split(" ");
        String peticion; // La ruta solicitada (ej: "/" o "/quijote")
        if (partes.length > 1) {
            peticion = partes[1];
        } else {
            peticion = "/"; // Fallback por si acaso
        }

        // --- FIN DE CORRECCIÓN DE PARSEO ---


        // 4. Seleccionamos la página y enviamos la respuesta
        if (peticion.equals("/")) {
            html = Pagina.html_index;
            printWriter.print(Mensaje.lineaInicial_OK + CRLF); // Usar print + CRLF
            printWriter.print("Date: " + Pagina.fechaHora() + CRLF); // Añadir Date
            printWriter.print(Pagina.CONTENT_TYPE_HTML + CRLF); // Constante correcta
            printWriter.print("Content-Length: " + Pagina.getLengthEnBytes(html) + CRLF); // Método correcto
            printWriter.print("Connection: close" + CRLF);
            printWriter.print(CRLF); // Línea vacía
            printWriter.print(html);
            printWriter.flush();
        } 
        else if (peticion.equals("/quijote")) {
            html = Pagina.html_quijote;
            printWriter.print(Mensaje.lineaInicial_OK + CRLF);
            printWriter.print("Date: " + Pagina.fechaHora() + CRLF);
            printWriter.print(Pagina.CONTENT_TYPE_HTML + CRLF);
            printWriter.print("Content-Length: " + Pagina.getLengthEnBytes(html) + CRLF);
            printWriter.print("Connection: close" + CRLF);
            printWriter.print(CRLF); // Línea vacía
            printWriter.print(html);
            printWriter.flush();
        } 
        else {
            // 404 Not Found para cualquier otra ruta
            html = Pagina.html_noEncontrado;
            printWriter.print(Mensaje.lineaInicial_NotFound + CRLF);
            printWriter.print("Date: " + Pagina.fechaHora() + CRLF);
            printWriter.print(Pagina.CONTENT_TYPE_HTML + CRLF);
            printWriter.print("Content-Length: " + Pagina.getLengthEnBytes(html) + CRLF);
            printWriter.print("Connection: close" + CRLF);
            printWriter.print(CRLF); // Línea vacía
            printWriter.print(html);
            printWriter.flush();
        }
    }

    private static void imprimeDisponible() {
        System.out.println("El Servidor WEB se está ejecutando y permanece a la "
                + "escucha por el puerto 8066.\nEscribe en la barra de direcciones "
                + "de tu explorador preferido:\n\nhttp://localhost:8066\npara "
                + "solicitar la página de bienvenida\n\nhttp://localhost:8066/"
                + "quijote\n para solicitar una página del Quijote");
    }
}