/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.io.*;
import java.net.*;

public class ServidorBarcos extends Thread {
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    
    // Puerto solicitado
    private static final int PUERTO = 1250;

    public ServidorBarcos(Socket s) throws IOException {
        this.s = s;
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {
        try {
            // Inicializamos datos del juego
            char[] vistaCliente = LogicaBarcos.crearTablero();
            int[] flotaCliente = {-1, -1, -1, -1}; // Tus barcos
            int[] flotaServidor = {2, 7, 8, 13};    // Barcos ocultos de la IA

            dos.writeUTF("--- BIENVENIDO A HUNDIR LA FLOTA (4x4) ---");

            // FASE 1: Colocación de barcos
            for (int i = 0; i < 4; i++) {
                dos.writeUTF("Coloca tu barco " + (i + 1) + " (elige posición 0-15):");
                int pos = dis.readInt();
                
                if (!LogicaBarcos.colocarBarco(flotaCliente, pos)) {
                    dos.writeUTF("Error: Posición inválida. Intenta otra.");
                    i--; // Repetir este barco
                }
            }

            dos.writeUTF("¡Todos tus barcos están listos! EMPIEZA LA BATALLA.");

            // FASE 2: Combate
            while (true) {
                // Enviamos el tablero visual al cliente
                dos.writeUTF(LogicaBarcos.obtenerVisual(vistaCliente));
                dos.writeUTF("Tu turno de disparo. Introduce posición (0-15):");

                // Recibir disparo del cliente
                int disparo = dis.readInt();
                char resultado = LogicaBarcos.verificarDisparo(flotaServidor, vistaCliente, disparo);

                if (resultado == 'X') dos.writeUTF("¡SÍ! Has impactado en un barco.");
                else dos.writeUTF("Agua... No había nada ahí.");

                // Comprobar si el cliente ganó
                if (LogicaBarcos.todosHundidos(flotaServidor)) {
                    dos.writeUTF(LogicaBarcos.obtenerVisual(vistaCliente));
                    dos.writeUTF("FIN: ¡VICTORIA! Has hundido toda la flota enemiga.");
                    break;
                }

                // Turno de la IA (Simple: dispara a la siguiente casilla disponible)
                dos.writeUTF("El Servidor está calculando su disparo...");
                Thread.sleep(1000); // Pausa dramática
                dos.writeUTF("El servidor disparó. ¡Sigue tú!");
            }

        } catch (Exception e) {
            System.out.println("El cliente se ha desconectado.");
        }
    }

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(PUERTO)) {
            System.out.println("Servidor de Batalla Naval listo en puerto " + PUERTO);
            while (true) {
                Socket cliente = ss.accept();
                System.out.println("Nuevo contendiente conectado!");
                new ServidorBarcos(cliente).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
