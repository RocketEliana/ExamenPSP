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

public class ServidorHundirFlota {
    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(1250)) {
            System.out.println("Servidor a la espera de jugadores en puerto 1250...");
            while (true) {
                Socket s1 = ss.accept();
                System.out.println("Jugador 1 conectado...");
                Socket s2 = ss.accept();
                System.out.println("Jugador 2 conectado. Iniciando partida.");
                new HiloPartida(s1, s2).start();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}

class HiloPartida extends Thread {
    private Socket s1, s2;
    private DataOutputStream out1, out2;
    private DataInputStream in1, in2;
    private LogicaJuego j1 = new LogicaJuego(), j2 = new LogicaJuego();

    public HiloPartida(Socket s1, Socket s2) { this.s1 = s1; this.s2 = s2; }

    @Override
    public void run() {
        try {
            out1 = new DataOutputStream(s1.getOutputStream()); in1 = new DataInputStream(s1.getInputStream());
            out2 = new DataOutputStream(s2.getOutputStream()); in2 = new DataInputStream(s2.getInputStream());

            faseColocacion(out1, in1, j1, "J1");
            faseColocacion(out2, in2, j2, "J2");

            while (true) {
                if (realizarTurno(out1, in1, out2, j2, "J1")) break;
                if (realizarTurno(out2, in2, out1, j1, "J2")) break;
            }
        } catch (Exception e) { System.out.println("Desconexión en una partida."); }
    }

    private void faseColocacion(DataOutputStream out, DataInputStream in, LogicaJuego juego, String id) throws IOException {
        out.writeUTF("--- Bienvenido " + id + ". Coloca 4 barcos (f,c) ---");
        for (int i = 0; i < 4; i++) {
            out.writeUTF("Barco " + (i + 1) + ":");
            String[] c = in.readUTF().split(",");
            if (!juego.colocarBarco(Integer.parseInt(c[0]), Integer.parseInt(c[1]))) {
                out.writeUTF("Error: Posición inválida o ocupada."); i--;
            }
        }
        out.writeUTF("Esperando al rival...");
    }

    private boolean realizarTurno(DataOutputStream atacaOut, DataInputStream atacaIn, DataOutputStream defOut, LogicaJuego rival, String id) throws IOException {
        atacaOut.writeUTF("TU TURNO. Mis disparos:\n" + rival.dibujarTablero(false) + "Dispara (f,c):");
        defOut.writeUTF("Turno del rival...");
        
        String[] c = atacaIn.readUTF().split(",");
        int f = Integer.parseInt(c[0]), col = Integer.parseInt(c[1]);
        String res = rival.registrarDisparo(f, col);

        atacaOut.writeUTF("Resultado: " + res);
        defOut.writeUTF("El rival disparó en " + f + "," + col + ". Tu tablero:\n" + rival.dibujarTablero(true));

        if (rival.getBarcosRestantes() == 0) {
            atacaOut.writeUTF("¡GANASTE!");
            defOut.writeUTF("HAS PERDIDO.");
            return true;
        }
        return false;
    }
}
