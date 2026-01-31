/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VersionPuntoDat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream; // --- CAMBIO ---
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    private static final int PUERTO = 1500;

    public Servidor() {
        ServerSocket sS;
        Socket s = null;
        try {
            sS = new ServerSocket(PUERTO);
            System.out.println("Esperando conexion");
            s = sS.accept();
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            DataInputStream dis = new DataInputStream(s.getInputStream());
            
            dos.writeUTF("Conexion Establecida");
            dos.writeUTF("Escriba nombre del fichero");
            
            String path = dis.readUTF();
            // Mantenemos la lógica de la ruta, puedes ajustarla
            path = "src/Ejercicio2/" + path; 
            
            File f = new File(path);
            String mensaje;
            
            if (!f.exists()) {
                mensaje = "El archivo no existe";
                dos.writeUTF(mensaje);
            } else {
                // --- CAMBIO ---
                // Le decimos al cliente que está todo OK para enviar
                mensaje = "OK"; 
                dos.writeUTF(mensaje);
                
                // Usamos FileInputStream para leer bytes crudos
                FileInputStream fis = new FileInputStream(f);
                
                // Creamos un "cubo" o buffer para mover los bytes
                byte[] buffer = new byte[4096]; 
                int bytesLeidos;
                
                // Bucle de envío de bytes
                // fis.read() lee bytes y los guarda en 'buffer'
                // Devuelve -1 cuando ya no hay más bytes que leer
                while ((bytesLeidos = fis.read(buffer)) != -1) {
                    // Escribimos los bytes leídos en el socket
                    // OJO: write(buffer, 0, bytesLeidos)
                    // Esto asegura que solo enviamos los bytes que se leyeron,
                    // no el buffer entero si no se llenó.
                    dos.write(buffer, 0, bytesLeidos);
                }
                
                fis.close(); // Cerramos el lector de archivo
                // No necesitamos una señal de "FIN", el cliente detectará
                // el fin del stream cuando cerremos el socket.
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Cerramos el socket del cliente al finalizar
            try {
                if (s != null) s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new Servidor();
    }
}
