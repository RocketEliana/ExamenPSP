package P5.e1;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {
        String servidor = "localhost";
        int puerto = 8066;

        try (Socket socket = new Socket(servidor, puerto);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // 1. Enviamos la petición exacta (Línea de solicitud + Host)
            out.println("GET / HTTP/1.1");
            out.println("Host: " + servidor);
            out.println("Connection: close");
            out.println(); // Línea en blanco para finalizar la petición

            // 2. Leemos la respuesta del servidor
            System.out.println("--- RESPUESTA DEL SERVIDOR ---");
            String linea;
            while ((linea = in.readLine()) != null) {
                System.out.println(linea);
            }

        } catch (Exception e) {
            System.err.println("Error: No se pudo conectar. ¿Está el servidor encendido?");
            e.printStackTrace();
        }
    }
}