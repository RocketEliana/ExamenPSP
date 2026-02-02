/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flotadani;

/**
 *
 * @author User
 */
import java.util.Arrays;

public class LogicaJuego {
    private char[][] tablero; // Contiene los barcos ('B')
    private char[][] disparos; // Contiene lo que ve el atacante ('X', '0', '-')
    private int barcosRestantes;

    public LogicaJuego() {
        tablero = new char[4][4];
        disparos = new char[4][4];
        for (int i = 0; i < 4; i++) {
            Arrays.fill(tablero[i], '-');
            Arrays.fill(disparos[i], '-');
        }
        this.barcosRestantes = 4;
    }

    public boolean colocarBarco(int f, int c) {
        if (f < 0 || f >= 4 || c < 0 || c >= 4 || tablero[f][c] == 'B') return false;
        tablero[f][c] = 'B';
        return true;
    }

    public String registrarDisparo(int f, int c) {
        if (tablero[f][c] == 'B') {
            tablero[f][c] = 'X';
            disparos[f][c] = 'X';
            barcosRestantes--;
            return "IMPACTO";
        } else {
            disparos[f][c] = '0';
            return "AGUA";
        }
    }

    public String dibujarTablero(boolean esDefensa) {
        char[][] t = esDefensa ? tablero : disparos;
        StringBuilder sb = new StringBuilder("  0 1 2 3\n");
        for (int i = 0; i < 4; i++) {
            sb.append(i).append(" ");
            for (int j = 0; j < 4; j++) sb.append(t[i][j]).append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }

    public int getBarcosRestantes() { return barcosRestantes; }
}