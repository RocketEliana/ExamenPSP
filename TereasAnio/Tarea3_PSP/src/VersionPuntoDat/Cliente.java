/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VersionPuntoDat;

/**
 *
 * @author User
 */

    import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream; // --- CAMBIO ---
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    private static final int PUERTO = 1500;
    private static final String HOST = "localhost";

    public Cliente() {
        Socket s = null;
        FileOutputStream fos = null; // --- CAMBIO ---
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
            
            // --- CAMBIO ---
            // Solo continuamos si el servidor nos dio el "OK"
            if (ficheroSN.equals("OK")) {
                
                // Usamos FileOutputStream para escribir bytes crudos
                fos = new FileOutputStream(nombreArchivo);
                
                // Creamos el mismo "cubo" o buffer
                byte[] buffer = new byte[4096];
                int bytesLeidos;

                // Bucle de recepción de bytes
                // dis.read() lee bytes del socket y los guarda en 'buffer'
                // Devuelve -1 cuando el servidor cierra la conexión (fin del archivo)
                while ((bytesLeidos = dis.read(buffer)) != -1) {
                    // Escribimos los bytes leídos en nuestro nuevo archivo
                    fos.write(buffer, 0, bytesLeidos);
                }
                
                System.out.println("Archivo '" + nombreArchivo + "' recibido y guardado.");
            }

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Cerramos todos los recursos
            try {
                if (fos != null) fos.close();
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
    

