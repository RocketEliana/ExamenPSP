/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
          new Servidor(cliente).start();;
      }      
            } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try ( DataInputStream dis=new DataInputStream(s.getInputStream());
                DataOutputStream dos=new DataOutputStream(s.getOutputStream()) ){
            dos.writeUTF("Dame el nombre del fichero");
            String path=dis.readUTF();
            System.out.println("Tu ruta es: " + path + "...........comprobando");
           File f=new  File(path+".txt");
           String respuesta="";//m2
          
           if(!f.exists()){
               respuesta="El archivo no existe";
               dos.writeUTF(respuesta);
               return;
                }else{
               respuesta="El archivo se enviara.....";
               dos.writeUTF(respuesta);
                BufferedReader br=new BufferedReader(new FileReader(f));
                String linea="";
                while((linea=br.readLine()) !=null){
                    dos.writeUTF(linea);
                    
                }
                br.close();
                dos.writeUTF("EOF");
                
           }
           
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    
}
