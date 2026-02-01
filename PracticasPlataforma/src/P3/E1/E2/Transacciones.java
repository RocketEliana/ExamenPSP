/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P3.E1.E2;

import java.util.Scanner;

/**
 *
 * @author User
 */
import java.util.Scanner;

public class Transacciones {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Estado 0: Inicio (según tu imagen)
        int estado = 0; 
        String comando = "";

        System.out.println("--- Sistema Iniciado (Estado 0) ---");
        estado = 1; // Transición automática al estado 1

        do {
            switch (estado) {
                case 1: // "Introduce el comando"
                    System.out.println("\n[ESTADO 1] Introduce comando (ls, get, exit):");
                    comando = sc.nextLine();

                    if (comando.equals("ls")) {
                        estado = 2; // Flecha hacia el estado 2
                    } else if (comando.equals("get")) {
                        estado = 3; // Flecha hacia el estado 3
                    } else if (comando.equals("exit")) {
                        estado = -1; // Flecha hacia FIN
                    } else {
                        System.out.println("Comando no reconocido.");
                        estado = 1; // El bucle '*' de la imagen (volver a sí mismo)
                    }
                    break;

                case 2: // "Muestra el comando" (ls)
                    System.out.println("[ESTADO 2] Listando archivos... \n- archivo1.txt\n- foto.jpg");
                    estado = 1; // La flecha vuelve al estado 1
                    break;

                case 3: // "Nombre del archivo"
                    System.out.println("[ESTADO 3] ¿Qué archivo quieres descargar?");
                    String nombreFichero = sc.nextLine(); 
                    // Una vez tenemos el nombre, pasamos al siguiente estado
                    estado = 4; 
                    break;

                case 4: // Procesar el archivo
                    System.out.println("[ESTADO 4] Procesando descarga de archivo...");
                    System.out.println("¡Descarga completada!");
                    estado = 1; // La flecha vuelve al estado 1
                    break;
            }

        } while (estado != -1);

        System.out.println("\n[ESTADO -1] Programa finalizado. ¡Adiós!");
        sc.close();
    }
}
/*El flujo de la "Máquina de Estados"
Como estás viendo diagramas de transiciones, lo que ese código intenta representar es esto:

Estado 1: El programa está "escuchando".

Transición: Si escribes get, el programa cambia de estado.

Estado 3: El programa ahora tiene una "misión" específica (pedir el archivo). No volverá al menú hasta que no termines esa tarea.*/