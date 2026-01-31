/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteBarcos {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 1250;

        try (Socket socket = new Socket(host, puerto);
             DataInputStream dis = new DataInputStream(socket.getInputStream());
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                // Leemos lo que el servidor nos dice
                String mensaje = dis.readUTF();
                System.out.println(mensaje);

                // Si el servidor nos pide un dato, habilitamos el teclado
                if (mensaje.contains("posición") || mensaje.contains("Introduce")) {
                    System.out.print("> ");
                    int valor = sc.nextInt();
                    dos.writeInt(valor);
                }

                // Si el juego termina, salimos
                if (mensaje.contains("FIN") || mensaje.contains("VICTORIA")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Conexión finalizada.");
        }
    }
}
