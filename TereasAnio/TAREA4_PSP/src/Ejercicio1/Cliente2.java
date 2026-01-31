/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


/**
 *
 * @author User
 */
public class Cliente2 {
    private static final int PUERTO=2000;
    private  static final String HOST="localhost";

    public Cliente2() {
        Scanner sc=new Scanner(System.in);
        try (Socket socket=new Socket(HOST, PUERTO);
                DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
                DataInputStream  dis=new DataInputStream(socket.getInputStream())){
            String m1=dis.readUTF();
            System.out.println(m1);//"ack"
            boolean salir=false;
            while(!salir){
                String m2=dis.readUTF();
                System.out.println(m2);
                int numero=sc.nextInt();
                sc.nextLine();
                dos.writeInt(numero);   
                String result=dis.readUTF();
                System.out.println(result);
                if(result.equals("Has ganado! fin del juego")){
                    salir=true;
                }
                
            }
                           
        } catch (IOException ex) {
     
        }
        
        
    }
    public static void main(String[] args) {
        new Cliente();
    
    
    
}
}
