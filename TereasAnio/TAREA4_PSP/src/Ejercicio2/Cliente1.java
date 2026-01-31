/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio2;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Cliente1 {

    private static final int PUERTO = 1500;
    private static final String HOST = "localhost";

    public Cliente1() {
        Scanner sc = new Scanner(System.in);
        try (
                Socket c = new Socket(HOST, PUERTO); DataInputStream dis = new DataInputStream(c.getInputStream()); DataOutputStream dos = new DataOutputStream(c.getOutputStream())) {
            String m1 = dis.readUTF();
            System.out.println(m1);
            String path = sc.nextLine();
            dos.writeUTF(path);
            String m2 = dis.readUTF();
            System.out.println(m2);
            if (m2.equals("El archivo no existe")) {
                // No hay más datos, termina aquí
                return;
            }
            
              System.out.println("Informacion recibida,se pasara a convertir en file");
File f = new File("HolaCopia");
if (!f.exists()) {
    f.createNewFile();
}
BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));

String linea;
while (!(linea = dis.readUTF()).equals("EOF")) {
    bw.write(linea);
    bw.newLine(); // preserva formato
}

bw.flush();
bw.close();
System.out.println("Archivo transferido");



        } catch (IOException ex) {
            Logger.getLogger(Cliente1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        new Cliente1();
    }

}
