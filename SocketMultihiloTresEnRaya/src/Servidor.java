//REVISA CIERRE DE CONEXIONES!!!!!!!!!!!!!!!!!
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class Servidor extends Thread{
    private static final int PUERTO=1250;
    private DataInputStream dis;
    private DataOutput dos;
    private Socket s;

    public Servidor(Socket s) throws IOException {
        this.s = s;
        dis=new DataInputStream(s.getInputStream());
        dos=new DataOutputStream(s.getOutputStream());
     
    }

    @Override
public void run() {
    try {
        // 1. Usamos tu método para empezar
        char[] tablero = LogicaTresEnRaya.crearTablero();
        
        while (true) {
            // 2. Mostramos el tablero con tu método
            dos.writeUTF(LogicaTresEnRaya.obtenerVisual(tablero));
            dos.writeUTF("Tu turno (0-8):");

            // 3. Turno Humano
            int pos = dis.readInt();
            // Usamos tu método marcar
            if (LogicaTresEnRaya.marcar(tablero, pos, 'X')) {
                
                // 4. ¿Ganó el humano? Usamos tus dos métodos de chequeo
                char g = LogicaTresEnRaya.verificarGanador(tablero);
                if (g == 'X' || LogicaTresEnRaya.tableroLleno(tablero)) {
                    dos.writeUTF(LogicaTresEnRaya.obtenerVisual(tablero) + " FIN. Ganador: " + g);
                    break; 
                }

                // 5. Turno IA (Busca el primer hueco usando tu método marcar)
                for (int i = 0; i < 9; i++) {
                    if (LogicaTresEnRaya.marcar(tablero, i, 'O')) {
                        dos.writeUTF("El servidor movió a la posición " + i);
                        break;
                    }
                }

                // 6. ¿Ganó la IA?
                g = LogicaTresEnRaya.verificarGanador(tablero);
                if (g == 'O' || LogicaTresEnRaya.tableroLleno(tablero)) {
                    dos.writeUTF(LogicaTresEnRaya.obtenerVisual(tablero) + " FIN. Ganador: " + g);
                    break;
                }
            } else {
                dos.writeUTF("Casilla ocupada, intenta otra.");
            }
        }
    } catch (IOException e) { /* Manejo de error */ }

       
       
    }
   public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            while (true) {
                // IMPORTANTE: Guardamos el socket que devuelve accept()
                Socket clienteConectado = ss.accept();
                System.out.println("Nuevo cliente conectado!");
                
                // Creamos un hilo para ese cliente específico
                Servidor hilo = new Servidor(clienteConectado);
                hilo.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
