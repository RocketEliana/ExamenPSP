/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Lenguaje {

    /*Primera parte: implementa una aplicación que escriba
    en un fichero indicado por el usuario conjuntos de letras generadas de forma aleatoria (sin sentido real). 
    Escribiendo cada conjunto de letras en una línea distinta. El número de conjuntos de letras a generar por el proceso, 
    también será dado por el usuario en el momento de su ejecución. Esta aplicación se llamará "lenguaje" y como ejemplo, podrá ser invocada así:
   java -jar lenguaje 40 miFicheroDeLenguaje.txt*/
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Argumentos insuficientes");
            return;
        }

        int numeroConjuntos = Integer.parseInt(args[0]);  // Número de conjuntos (líneas)
        String nombreFichero = args[1];
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(nombreFichero));

            // Para CADA conjunto (línea)
            for (int k = 0; k < numeroConjuntos; k++) {
                StringBuilder conjunto = new StringBuilder();

                // Cada conjunto tendrá 5 letras (o el número que quieras)
                int letrasPorConjunto = 5;  // ← ¡FIJO! No "numeroConjuntos"

                // Añadir letras a ESTE conjunto
                for (int i = 0; i < letrasPorConjunto; i++) {
                    char letra = (char) ('a' + (int) (Math.random() * 26));
                    conjunto.append(letra);
                }

                // Escribir este conjunto (una línea)
                bw.write(conjunto.toString());
                bw.newLine();
                // NO necesitas conjunto.delete() - se crea uno nuevo cada vez
            }

            System.out.println("Creados " + numeroConjuntos + " conjuntos en '" + nombreFichero + "'");

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ex) {
                    System.out.println("Error al cerrar: " + ex.getMessage());
                }
            }
        }
    }
}
