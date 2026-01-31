package pkg3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private static final int PUERTO = 2000;
    private static final String HOST = "localhost";
    private DataInputStream dis;
    private DataOutputStream dos;
    private Scanner sc;
    private Socket s;

    public Cliente() throws IOException {
        sc = new Scanner(System.in);
        s = new Socket(HOST, PUERTO);
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());
    }

    public void iniciarJuego() throws IOException {
        // 1. Leer bienvenida
        System.out.println("SERVIDOR: " + dis.readUTF());
        
        int contador = 4;
        while (contador > 0) {
            System.out.print("\nIntentos restantes (" + contador + "). Introduce número: ");
            
            if (sc.hasNextInt()) {
                int intento = sc.nextInt();
                sc.nextLine(); // Limpiar buffer

                // Enviamos al servidor
                dos.writeInt(intento);
                dos.flush();

                // RECIBIMOS LA RESPUESTA (Pista o victoria)
                String respuesta = dis.readUTF();
                System.out.println("SERVIDOR: " + respuesta);

                if (respuesta.contains("ganado")) {
                    break; // Salimos si hemos acertado
                }
                
                contador--;
            } else {
                System.out.println("Error: Introduce un número entero válido.");
                sc.nextLine(); // Limpiar el texto incorrecto
            }
        }
        
        if (contador == 0) {
            System.out.println("\nTe has quedado sin intentos. ¡Más suerte la próxima vez!");
        }
        
        s.close();
        System.out.println("Conexión cerrada.");
    }

    public static void main(String[] args) {
        try {
            Cliente c = new Cliente();
            c.iniciarJuego();
        } catch (IOException ex) {
            System.err.println("No se pudo conectar con el servidor. ¿Está encendido?");
        }
    }
}