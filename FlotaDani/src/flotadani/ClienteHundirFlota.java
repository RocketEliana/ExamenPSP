/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flotadani;

/**
 *
 * @author User
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteHundirFlota {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Socket s = new Socket("localhost", 1250);
             DataInputStream dis = new DataInputStream(s.getInputStream());
             DataOutputStream dos = new DataOutputStream(s.getOutputStream())) {

            while (true) {
                String msg = dis.readUTF();
                System.out.println(msg);

                if (msg.contains("GANASTE") || msg.contains("PERDIDO")) break;

                // Si el servidor termina con ":" o "?", espera respuesta
                if (msg.contains(":") || msg.contains("?")) {
                    dos.writeUTF(sc.nextLine());
                }
            }
        } catch (IOException e) { System.out.println("Fin de la conexión."); }
    }
}