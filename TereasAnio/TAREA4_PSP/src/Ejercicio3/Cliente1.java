/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Cliente1 {
     private static final String HOST = "localhost";
    private static final int PUERTO = 1500;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(HOST, PUERTO);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        ) {
            System.out.println(dis.readUTF()); // Mensaje de bienvenida

            // Login
            System.out.print(dis.readUTF() + " ");
            String usuario = teclado.readLine();
            dos.writeUTF(usuario);

            System.out.print(dis.readUTF() + " ");
            String contrasenia = teclado.readLine();
            dos.writeUTF(contrasenia);

            String respuesta = dis.readUTF();
            System.out.println(respuesta);

            if (respuesta.startsWith("Credenciales incorrectas")) {
                return; // termina si no pasa login
            }

            boolean salir = false;
            while (!salir) {
                System.out.println(dis.readUTF()); // Menú
                String opcion = teclado.readLine();
                dos.writeUTF(opcion);

                switch (opcion) {
                    case "1":
                    case "2":
                        String respuestaServidor = dis.readUTF();
                        if (respuestaServidor.startsWith("Introduce")) {
                            System.out.print(respuestaServidor + " ");
                            String nombreArchivo = teclado.readLine();
                            dos.writeUTF(nombreArchivo);
                            System.out.println(dis.readUTF());
                        } else {
                            System.out.println(respuestaServidor);
                        }
                        break;
                    case "3":
                        System.out.println(dis.readUTF());
                        salir = true;
                        break;
                    default:
                        System.out.println(dis.readUTF());
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
