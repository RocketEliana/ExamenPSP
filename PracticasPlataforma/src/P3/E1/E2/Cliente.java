/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P3.E1.E2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Cliente {
    private static final int PUERTO=1250;
    private static final String HOST="localhost";
    private Socket socket;

public Cliente() {//try catch resources en constructores no!!!!!!!!!!!!!!!!!!!
        try {
            // ASIGNAMOS AL ATRIBUTO (sin poner 'Socket' delante)
            this.socket = new Socket(HOST, PUERTO);
            interactuarServidor();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if(socket != null) socket.close(); } catch (IOException e) {}
        }
    }
        
        
    
    public void interactuarServidor() {
        try(BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw=new PrintWriter(socket.getOutputStream(),true)
                ) {
                 long inicioTotal = System.currentTimeMillis();
                   long inicioProceso = System.nanoTime();
                String mensaje=br.readLine();
                System.out.println(mensaje);
                pw.println("practica.txt");
                String respuesta=br.readLine();
                System.out.println(respuesta);
                File f =new File("practicaCliente.txt");
                String linea=" ";
                BufferedWriter bwF=new  BufferedWriter(new FileWriter(f));
                while((linea=br.readLine())!=null){
                    bwF.write(linea +"\n");
                      bwF.flush();
                   }
                
                bwF.close();
              
                pw.println("EOF.......->Archivo rECIBIDO");
                 long finProceso = System.nanoTime();
        long finTotal = System.currentTimeMillis();
               System.out.println("\n--- ESTADÍSTICAS INTENTO ---");
        System.out.println("CPU (Procesamiento): " + (finProceso - inicioProceso) + " ns");
        System.out.println("RED + ESPERA (Total): " + (finTotal - inicioTotal) + " ms");
        System.out.flush(); // <--- ESTO FUERZA A LA CONSOLA A MOSTRAR EL TEXTO

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
}
   
    
    public static void main(String[] args) {
        Cliente c=new Cliente();
    }
}
        
    
    

