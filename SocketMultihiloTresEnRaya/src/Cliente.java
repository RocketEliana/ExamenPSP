
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Cliente {
    private static final int PUERTO = 1250;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        // Usamos try-with-resources para cerrar todo automáticamente al terminar
        try (Socket socket = new Socket(HOST, PUERTO);
             DataInputStream dis = new DataInputStream(socket.getInputStream());
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             Scanner sc = new Scanner(System.in)) {

            System.out.println("--- Conectado al Servidor de Juego ---");

            while (true) {
                // El cliente se queda "dormido" aquí esperando órdenes del servidor
                String mensajeServidor = dis.readUTF();
                
                // Imprimimos cualquier mensaje que venga del servidor (tablero, avisos, etc.)
                System.out.println(mensajeServidor);

                // LÓGICA DE INTERACCIÓN:
                // Si el mensaje del servidor nos pide mover, activamos el teclado
                if (mensajeServidor.contains("Tu turno") || mensajeServidor.contains("intenta de nuevo")) {
                    System.out.print("Introduce posición (0-8): ");
                    int posicion = sc.nextInt();
                    dos.writeInt(posicion); // Enviamos el número al servidor
                }

                // Si el mensaje contiene "FIN" o un "Ganador", salimos del bucle
                if (mensajeServidor.contains("FIN") || mensajeServidor.contains("Ganador")) {
                    System.out.println("\nGracias por jugar.");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error: Se ha perdido la conexión con el servidor.");
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
}
