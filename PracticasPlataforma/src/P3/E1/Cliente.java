/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P3.E1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    private static final int PUERTO=2000;
    private static final String HOST="localhost";
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;

    public Cliente() throws IOException {
       try{
           socket=new Socket(HOST,PUERTO);
        dis=new DataInputStream(socket.getInputStream());
        dos=new DataOutputStream(socket.getOutputStream());
        interactuarServidor();
       }finally{
           cerrarRecursos();
       }
        
    }
    public void interactuarServidor() throws IOException {
    Scanner sc = new Scanner(System.in);
    System.out.println(dis.readUTF()); // Bienvenida

    try {
        while (true) {
            String pregunta = dis.readUTF(); // "Intentos: X. Di un numero:"
            System.out.println(pregunta);

            if (pregunta.contains("GAME OVER") || pregunta.contains("ganado")) break;

            int n = sc.nextInt();
            dos.writeInt(n);
            dos.flush();

            String respuesta = dis.readUTF(); // "Es MAYOR/MENOR" o "¡Has ganado!"
            System.out.println(respuesta);

            if (respuesta.contains("GAME OVER") || respuesta.contains("ganado")) break;
        }
    } catch (Exception e) {
        System.out.println("Fin de la partida.");
    }
}
     private void cerrarRecursos() {
        try {
            if (dis != null) dis.close();
            if (dos != null) dos.close();
            if (socket != null) socket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            Cliente c =new Cliente();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    }
    

