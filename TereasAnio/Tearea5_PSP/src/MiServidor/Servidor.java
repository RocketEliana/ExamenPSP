package MiServidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static void main(String[] args) {
        System.out.println("Servidor iniciado en puerto 8066...");

        try (ServerSocket sS = new ServerSocket(8066)) {

            while (true) {
                imprimir();
                Socket s = sS.accept();  // Acepta un cliente
                procesar(s);
                s.close(); // cerramos el socket del cliente después de procesarlo
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void imprimir() {
        System.out.println("Escuchando: escribe http://localhost:8066 en tu navegador");
    }

    private static void procesar(Socket s) {
        try (
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true)
        ) {

            // 1) Leer la primera línea
            String primeraLinea = br.readLine();
            if (primeraLinea == null) return;
            System.out.println("Petición recibida: " + primeraLinea);

            // 2) Descartar el resto de cabeceras hasta línea vacía
            String linea;
            while ((linea = br.readLine()) != null && !linea.isEmpty()) { }

            // 3) Verificar GET
            if (!primeraLinea.startsWith("GET")) {
                pw.println(Mensajes.BAD_REQUEST);
                pw.println(Paginas.HTML_CABECERA);
                pw.println();
                pw.println("<h1>400 BAD REQUEST</h1>");
                return;
            }

            // 4) Extraer ruta
            String[] partes = primeraLinea.split(" ");
            String ruta = partes[1];
            System.out.println("Ruta solicitada: " + ruta);

            // 5) Enviar respuesta según ruta
            switch (ruta) {
                case "/":
                    pw.println(Mensajes.OK);
                    pw.println(Paginas.HTML_CABECERA);
                    pw.println();
                    pw.println(Paginas.HTML_PRESENTA);
                    break;

                case "/lorem":
                    pw.println(Mensajes.OK);
                    pw.println(Paginas.HTML_CABECERA);
                    pw.println();
                    pw.println(Paginas.HTML_LORE_IPSUM);
                    break;

                default:
                    pw.println(Mensajes.NOT_FONUND);
                    pw.println(Paginas.HTML_CABECERA);
                    pw.println();
                    pw.println(Paginas.HTML_FALLO);
                    break;
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
