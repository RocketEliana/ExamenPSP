/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class LogicaBarcos {
    // Tablero de 4x4 = 16 casillas
    public static char[] crearTablero() {
        char[] t = new char[16];
        for(int i=0; i<16; i++) t[i] = '-'; // '-' es agua/desconocido
        return t;
    }

    // El servidor usará este para guardar dónde están los barcos ocultos
    public static boolean colocarBarco(int[] flota, int pos) {
        // Validación extra: que la posición sea válida
        if (pos < 0 || pos > 15) return false;
        
        for(int i=0; i < flota.length; i++) {
            if(flota[i] == -1) { 
                flota[i] = pos;
                return true;
            }
        }
        return false;
    }

    // Corregido el nombre de la variable 'flotaEnemiga'
    public static char verificarDisparo(int[] flotaEnemiga, char[] tableroVisual, int pos) {
        if (pos < 0 || pos > 15) return ' '; // Error de rango

        for(int i=0; i < flotaEnemiga.length; i++) {
            if(flotaEnemiga[i] == pos) {
                tableroVisual[pos] = 'X'; // Impacto
                flotaEnemiga[i] = -2;     // Barco marcado como hundido
                return 'X';
            }
        }
        tableroVisual[pos] = '0'; // Fallo (puedes usar '0' o 'O' como quieras)
        return '0';
    }

    public static boolean todosHundidos(int[] flota) {
        for(int b : flota) {
            if(b >= 0) return false; // Si hay algún número >= 0, queda barco vivo
        }
        return true;
    }

    public static String obtenerVisual(char[] t) {
        StringBuilder sb = new StringBuilder("\n    0 1 2 3\n");
        sb.append("  ----------\n");
        for(int i=0; i<4; i++) {
            sb.append(i).append(" | "); // Índice de fila
            for(int j=0; j<4; j++) {
                sb.append(t[i*4 + j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}