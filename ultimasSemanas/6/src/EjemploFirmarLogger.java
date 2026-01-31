/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.*; // Importamos toda la librería de logging
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EjemploFirmarLogger {

    // 1. Buscamos o creamos el logger
    private static final Logger logger = Logger.getLogger("MyLog");

    public static void main(String[] args) {
        // 2. Configuramos el logger antes de empezar
        configurarLogger();

        Scanner sc = new Scanner(System.in);
        
        logger.log(Level.INFO, "Aplicación iniciada.");

        String login = comprobarLogin(sc, "Introduce user (8 letras minúsculas):");
        String nombreFichero = comprobarFichero(sc, "Introduce el nombre del fichero (máx 8 car + ext 3 car):");

        File f = new File(nombreFichero);

        if (!f.exists()) {
            System.out.println("El fichero no existe");
            // Registro de advertencia: el usuario pidió un archivo inexistente
            logger.log(Level.WARNING, "El usuario " + login + " intentó abrir un fichero inexistente: " + nombreFichero);
        } else {
            // Uso de try-with-resources para asegurar el cierre del BufferedReader
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                logger.log(Level.INFO, "Abriendo fichero: " + nombreFichero);
                String linea = "";
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }
            } catch (FileNotFoundException ex) {
                // Registro de nivel SEVERE para errores de archivo
                logger.log(Level.SEVERE, "Fichero no encontrado a pesar de la comprobación previa", ex);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Error de lectura en el fichero: " + nombreFichero, ex);
            }
        }
    }

    // Método para configurar el manejador de archivos y el formato
    private static void configurarLogger() {
        try {
            // Asociamos al fichero con modo append (true)
            FileHandler fh = new FileHandler("MyLogFile.log", true);
            logger.addHandler(fh);
            
            // Establecemos formato de texto simple
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            
            // Opcional: No ver mensajes por pantalla (solo en el archivo)
            // logger.setUseParentHandlers(false);
            
            logger.setLevel(Level.ALL);
        } catch (IOException | SecurityException e) {
            System.err.println("No se pudo inicializar el sistema de logs: " + e.getMessage());
        }
    }

    public static String comprobarLogin(Scanner sc, String mensaje) {
        boolean valido = false;
        while (!valido) {
            System.out.println(mensaje);
            String login = sc.nextLine();
            Pattern pt = Pattern.compile("^[a-z]{8}$");
            Matcher m = pt.matcher(login);

            if (m.matches()) {
                valido = true;
                logger.log(Level.INFO, "Login validado correctamente: " + login);
                return login;
            } else {
                logger.log(Level.WARNING, "Intento de login inválido: " + login);
                System.out.println("Error: El login debe tener exactamente 8 letras minúsculas.");
            }
        }
        return null;
    }

    public static String comprobarFichero(Scanner sc, String mensaje) {
        boolean valido = false;
        while (!valido) {
            System.out.println(mensaje);
            String fichero = sc.nextLine();
            Pattern pt = Pattern.compile("^[a-zA-Z0-9]{1,8}\\.[a-zA-Z0-9]{3}$");
            Matcher m = pt.matcher(fichero);

            if (m.matches()) {
                valido = true;
                return fichero;
            } else {
                logger.log(Level.WARNING, "Formato de nombre de fichero no válido: " + fichero);
                System.out.println("Error: El archivo debe tener como máximo 8 caracteres y una extensión de 3.");
            }
        }
        return null;
    }
}
