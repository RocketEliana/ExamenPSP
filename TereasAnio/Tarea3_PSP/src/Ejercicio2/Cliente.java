/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio2;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Cliente {
    private static final int PUERTO = 1500;
    private static final String HOST = "localhost";

    public Cliente() {
        Socket s = null; // Declarar fuera para 'finally'
        BufferedWriter bw = null; // Declarar fuera para 'finally'
        Scanner sc = new Scanner(System.in);
        try {
            s = new Socket(HOST, PUERTO);
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            DataInputStream dis = new DataInputStream(s.getInputStream());
            System.out.println(dis.readUTF());
            System.out.println(dis.readUTF());
            String nombreArchivo = sc.nextLine();
            dos.writeUTF(nombreArchivo);
            String ficheroSN = dis.readUTF();
            System.out.println(ficheroSN);
            
            if (ficheroSN.equals("El archivo no existe")) {
                // No hacer nada, el 'finally' cerrará el socket
            } else {
                File f = new File(nombreArchivo);
                f.createNewFile();
                bw = new BufferedWriter(new FileWriter(f));
                
                // --- ¡SOLUCIÓN AQUÍ! ---
                String lineaRecibida;
                // Bucle para leer todas las líneas
                while (true) {
                    lineaRecibida = dis.readUTF(); // Lee una línea
                    
                    // Comprueba si es la señal de fin
                    if (lineaRecibida.equals("FIN_DE_FICHERO")) {
                        break; // Salir del bucle
                        /*¡No lo queremos! En realidad, es una técnica de programación.

Creamos un bucle que parece infinito, pero planeamos romperlo (detenerlo) desde dentro usando una condición y la palabra clave break;.*/
                    }
                    
                    bw.write(lineaRecibida);
                    bw.newLine(); // Añade el salto de línea que readLine() quitó
                }
                System.out.println("Archivo '" + nombreArchivo + "' recibido y guardado.");
                // --- FIN DE LA SOLUCIÓN ---
            }

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Cierra todos los recursos en el 'finally'
            try {
                if (bw != null) bw.close();
                if (s != null) s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new Cliente();
    }
}
