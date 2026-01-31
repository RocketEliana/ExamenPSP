/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class Colaborar {
    /*Segunda parte: implementa una aplicación, llamada 'colaborar', que lance al menos 10 instancias de la aplicación "lenguaje". 
    Haciendo que todas ellas, colaboren en generar un gran fichero de palabras. Cada instancia generará un número creciente de palabras de 10, 20, 30, … 
    Por supuesto, cada proceso seguirá escribiendo su palabra en una línea independiente de las otras.
    Es decir, si lanzamos 10 instancias de "lenguaje", al final, debemos tener en el fichero 10 + 20 + 30 + … + 100 = 550 líneas.*/
    public static void main(String[] args) {
         String nombreFichero = "gran_fichero.txt";
        int numeroInstancias = 10;
        List<Process> procesos = new ArrayList<>();

        System.out.println("Lanzando instancias de 'lenguaje'...");

        for (int i = 1; i <= numeroInstancias; i++) {
            int palabrasAGenerar = i * 10; // 10, 20, 30...
            
            // Preparamos el comando: java lenguaje <num> <fichero>
            ProcessBuilder pb = new ProcessBuilder(
                "java", 
                "lenguaje", 
                String.valueOf(palabrasAGenerar), 
                nombreFichero
            );

            try {
                // Lanzamos el proceso y lo añadimos a la lista para controlarlos
                procesos.add(pb.start());
                System.out.println("Instancia " + i + " lanzada (" + palabrasAGenerar + " palabras).");
            } catch (IOException e) {
                System.err.println("Error al lanzar la instancia " + i + ": " + e.getMessage());
            }
        }

        // Esperamos a que todos los procesos terminen para asegurar que el fichero esté completo
        for (Process p : procesos) {
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Proceso finalizado. Verifica el fichero: " + nombreFichero);
    }

        
    }
    
    

