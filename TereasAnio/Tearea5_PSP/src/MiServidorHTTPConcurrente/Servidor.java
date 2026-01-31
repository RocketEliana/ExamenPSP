/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MiServidorHTTPConcurrente;

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
    public static void main(String[] args) {
        try {
            ServerSocket sS=new ServerSocket(8066);
            while(true){
                Socket cliente=sS.accept();
                 System.out.println("Servidor HTTP Concurrente iniciado en puerto ");
                HiloEhecuta h=new HiloEhecuta(cliente);
                h.start();
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
