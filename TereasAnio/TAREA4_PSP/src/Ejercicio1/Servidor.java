/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
//acceso concurrente
public class Servidor extends Thread {

    private static final int PUERTO = 2000;
    private Socket socket;
    private  static int clientes=0;

    public Servidor(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) {
        try (ServerSocket sS = new ServerSocket(PUERTO)) {//con resource cierra automaticamente
            System.out.println("Esperendo clientes");
            while (true) {
                //creo bucle infinito,(tecnica de programacion)para romperlo cuando me interese.En switch return,si no break,aqui cerrando socket
                Socket cliente = sS.accept();
                System.out.println("Conexion establecida");
                clientes++;
                new Servidor(cliente).start();

            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        try(DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
        DataInputStream dis=new DataInputStream(socket.getInputStream())){
            dos.writeUTF("Hola Cliente!" + clientes);
            int aleatorio=(int)((Math.random()*10)+1);
            while(true){
                dos.writeUTF("Escribe un numero del uno al diez");
                int intentoNumero=dis.readInt();
                System.out.println("Tu numero es : "+ intentoNumero);
                String resul="";
                if(aleatorio==intentoNumero){
                    resul="Has ganado! fin del juego";
                    dos.writeUTF(resul);
                    break;
                }else if(aleatorio>intentoNumero){
                    resul="El numero es mayor";
                }else{
                    resul="el numero es menor";
                        
                        }
                dos.writeUTF(resul);
                }
            
      
   
        }catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
