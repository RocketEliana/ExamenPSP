/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg3.pkg2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author User
 */
public class Servidor {
    private static final int PUERTO=1500;
    private BufferedReader br;
    private BufferedWriter  bw;
    private ServerSocket ss;

    public Servidor() throws IOException {
        ss=new ServerSocket(PUERTO);
        Socket s=ss.accept();
        bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
       br=new BufferedReader(new InputStreamReader(s.getInputStream()));
    }
    public void inicioarIntercambioFichero() throws IOException{
        bw.write("Bienvenido,cliente que fichero quieres que te transfiramos?");
        bw.newLine(); // <--- Sin esto, el cliente no sabe que la frase terminó
        bw.flush();   // <--- Sin esto, el mensaje puede quedarse en el buffer sin salir
        String nombreArchivo=br.readLine();
        File f=new File(nombreArchivo);
        if(!f.exists()){
            bw.write("El archivo que pides no existe");
            bw.newLine();
            bw.flush();
             }else{
            String linea="";
            BufferedReader fr=new BufferedReader(new FileReader(f));
            while((linea=fr.readLine())!=null){
                bw.write(linea);
                bw.newLine();
            }
            bw.write("EOF"); // "End Of File" 
            bw.newLine();
            bw.flush(); 
            fr.close(); 
            
        }        
    }

    public void cerrarTodo() {
        try {
            if (bw != null) bw.close();
            if (br != null) br.close();
            if (ss != null) ss.close();
            System.out.println("Recursos liberados.");
        } catch (IOException e) {
            System.err.println("Error al cerrar: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
    Servidor s = null;
    try {
        s = new Servidor();
        s.inicioarIntercambioFichero();
    } catch (IOException e) {
        System.err.println("Error en la ejecución: " + e.getMessage());
    } finally {
        if (s != null) {
            s.cerrarTodo(); // <--- El main decide cerrar aquí
        }
    }
}
}
        
    
    
    
    

