/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class LogicaTresEnRaya {
    
    
// Crea un tablero vacío
    public static char[] crearTablero() {
        return "         ".toCharArray();
    }

    // Marca una posición. Recibe el tablero, la posición y el jugador.
    public static boolean marcar(char[] tablero, int pos, char jugador) {
        if (pos >= 0 && pos < 9 && tablero[pos] == ' ') {
            tablero[pos] = jugador;
            return true;
        }
        return false;
    }

    // Verifica si hay ganador en el tablero que le pases
    public static char verificarGanador(char[] tablero) {
        int[][] combinaciones = {
            {0,1,2}, {3,4,5}, {6,7,8}, // Horizontales
            {0,3,6}, {1,4,7}, {2,5,8}, // Verticales
            {0,4,8}, {2,4,6}           // Diagonales
        };

        for (int[] c : combinaciones) {
            if (tablero[c[0]] != ' ' && 
                tablero[c[0]] == tablero[c[1]] && 
                tablero[c[0]] == tablero[c[2]]) {
                return tablero[c[0]];
            }
        }
        return ' ';
    }

    // Verifica si el tablero está lleno
    public static boolean tableroLleno(char[] tablero) {
        for (char casilla : tablero) {
            if (casilla == ' ') return false;
        }
        return true;
    }

    // Devuelve el dibujo del tablero
    public static String obtenerVisual(char[] tablero) {
        return String.format(
            "\n %c | %c | %c \n-----------\n %c | %c | %c \n-----------\n %c | %c | %c \n",
            tablero[0], tablero[1], tablero[2], 
            tablero[3], tablero[4], tablero[5], 
            tablero[6], tablero[7], tablero[8]
        );
    }
}
