package Ejercicio1;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    private static final int PUERTO = 2000;
    private static final String HOST = "localhost";

    public Cliente() {
        try (Socket s = new Socket(HOST, PUERTO)) {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            Scanner sc = new Scanner(System.in);

            // 1️⃣ Leer bienvenida
            String mensajeS = dis.readUTF();
            System.out.println(mensajeS);

            boolean acertado = false;

            // 2️⃣ Bucle del juego
            while (!acertado) {
                // Leer el mensaje "Elige un número..."
                String mensajeJ = dis.readUTF();
                System.out.println(mensajeJ);

                // Escribir número
                System.out.print("Tu número: ");
                int numero = sc.nextInt();
                dos.writeInt(numero);
                dos.flush();

                // Leer respuesta (mayor/menor/ganaste)
                String mensajeR = dis.readUTF();
                System.out.println(mensajeR);

                if (mensajeR.equals("Has ganado")) {
                    acertado = true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new Cliente();
    }
}
