package pkg3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private final int PUERTO = 2000;
    private ServerSocket ss;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket s;

    public Servidor() throws IOException {
        ss = new ServerSocket(PUERTO);
        System.out.println("Servidor iniciado. Esperando conexión en el puerto " + PUERTO + "...");
        s = ss.accept(); // Se queda bloqueado aquí hasta que alguien se conecta
        System.out.println("Cliente conectado desde: " + s.getInetAddress());
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());
    }

    public void bucleJuego() throws IOException {
        // 1. Enviamos bienvenida
        dos.writeUTF("Bienvenido al juego. Tienes 4 intentos para adivinar un número entre 0 y 10.");
        
        boolean acierto = false;
        int aleatorio = (int) (Math.random() * 11); // Número entre 0 y 10
        System.out.println("(Trampa para el programador: El número es " + aleatorio + ")");

        try {
            // El servidor sigue escuchando mientras no acierten
            while (!acierto) {
                int intento = dis.readInt(); // El servidor se para aquí a esperar el número
                System.out.println("El cliente ha dicho: " + intento);

                if (intento == aleatorio) {
                    dos.writeUTF("¡Has ganado! El número era " + aleatorio);
                    acierto = true; 
                } else if (intento > aleatorio) {
                    dos.writeUTF("El número es menor.");
                } else {
                    dos.writeUTF("El número es mayor.");
                }
            }
        } catch (IOException e) {
            System.out.println("Fin de la conexión (el cliente probablemente cerró).");
        } finally {
            s.close();
            ss.close();
        }
    }

    public static void main(String[] args) {
        try {
            Servidor serv = new Servidor();
            serv.bucleJuego();
        } catch (IOException ex) {
            System.err.println("Error en el servidor: " + ex.getMessage());
        }
    }
}