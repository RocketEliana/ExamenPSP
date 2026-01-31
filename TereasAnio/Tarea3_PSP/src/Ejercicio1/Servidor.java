package Ejercicio1;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    private static final int PUERTO = 2000;

    public Servidor() {
        try (ServerSocket sS = new ServerSocket(PUERTO)) {
            System.out.println("Esperando conexión...");
            Socket s = sS.accept();
            System.out.println("Conexión establecida");

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // 1️⃣ Enviar bienvenida
            dos.writeUTF("¡Bienvenido al servidor!");

            // Número aleatorio entre 1 y 10
            int aleatorio = (int) (Math.random() * 10) + 1;
            int intentoCliente;

            // 2️⃣ Bucle principal
            do {
                // Enviar mensaje de petición
                dos.writeUTF("Elige un número del 1 al 10:");

                // Leer número del cliente
                intentoCliente = dis.readInt();

                // Comparar y responder
                if (intentoCliente == aleatorio) {
                    dos.writeUTF("Has ganado");
                } else if (intentoCliente > aleatorio) {
                    dos.writeUTF("El número es menor");
                } else {
                    dos.writeUTF("El número es mayor");
                }
            } while (intentoCliente != aleatorio);

            s.close();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new Servidor();
    }
}
