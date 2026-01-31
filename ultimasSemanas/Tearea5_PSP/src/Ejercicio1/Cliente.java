/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Cliente {
    private static final int  PUERTO=8066;
    private static final String HOST="localhost";
   private Socket s;
   private BufferedReader br;
   private BufferedWriter bw;
    public Cliente() throws IOException {
        s=new Socket(HOST, PUERTO);
        bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        br=new BufferedReader(new  InputStreamReader(s.getInputStream()));
    }
    public static void main(String[] args) {
        Cliente c = null;
        try {
            c = new Cliente();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.interactuar();
        
    }
public void interactuar() {
    try {
        // ENVIAR PETICIÓN
        bw.write("GET /quijote HTTP/1.1\r\n");
        bw.write("Host: " + HOST + "\r\n");
        bw.write("Connection: close\r\n"); // Avisamos que cerraremos la conexión
        bw.write("\r\n"); 
        bw.flush();

        // RECIBIR RESPUESTA
        String linea;
        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }

    } catch (IOException ex) {
        ex.printStackTrace();
    } finally {
        // CIERRE DE RECURSOS (Imprescindible en PSP)
        try {
            if (bw != null) bw.close(); // Al cerrar bw, se cierra también el flujo de salida
            if (br != null) br.close(); // Al cerrar br, se cierra el flujo de entrada
            if (s != null) s.close();   // Finalmente cerramos el socket 
            System.out.println("\nConexión cerrada.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
    
    
    
}
