/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author User
 */
public class Servidor {
    private static final int PUERTO=1500;

    public Servidor() {
        ServerSocket sS;
        try {
             sS=new ServerSocket(PUERTO);
             System.out.println("Esperando conexion");
             Socket s=sS.accept();
             DataOutputStream dos=new DataOutputStream(s.getOutputStream());
             DataInputStream dis=new DataInputStream(s.getInputStream());
                 dos.writeUTF("Conexion Establecida");
                 dos.writeUTF("Escriba nombre del fichero");
                 String path=dis.readUTF();
                 File f=new File(path);
                 String mensaje;
                 if(!f.exists()){
                     mensaje="El archivo no existe";
                 }else{
                     mensaje="Ahora le enviaremos el archivo,espere";
                 }
                 dos.writeUTF(mensaje);
                 BufferedReader br= new BufferedReader(new FileReader(f));
                 String linea="";
                 while((linea=br.readLine())!= null){
                     dos.writeUTF(linea);
                     }
                 br.close();
                 dos.writeUTF("FIN_DE_FICHERO");
             
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public static void main(String[] args) {
        new Servidor();
    }
    
    
}
