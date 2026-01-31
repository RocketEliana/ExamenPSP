/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio3;

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
public class Servidor extends Thread{
    private static final int PUERTO=1500;
    private   Socket s;
    

    public Servidor(Socket s) {
        this.s = s;
    }
    public static void main(String[] args) {
        try(ServerSocket sS=new ServerSocket(PUERTO)){
      while(true){
          System.out.println("Ersperando conexion");
          Socket cliente=sS.accept();
          System.out.println("Conexion establecida");
          new Servidor(cliente).start();
      }      
            } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try ( DataInputStream dis=new DataInputStream(s.getInputStream());
                DataOutputStream dos=new DataOutputStream(s.getOutputStream()) ){
            dos.writeUTF("Ingrese nombre");
            String nombre=dis.readUTF();
            dos.writeUTF("Escriba la contraseña");
            String contrasenia=dis.readUTF();
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    
}
