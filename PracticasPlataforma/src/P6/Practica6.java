/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Practica6 {

    public static void main(String[] args) {
        BufferedReader br = null;
        FileHandler fh = null;
        Scanner sc = new Scanner(System.in);
        Logger logger = Logger.getLogger("PruebaLog");

        try {
            // 1. Configuración del Logger
            String logFileName = "RegistroActividad.log";
            fh = new FileHandler(logFileName, true); // Modo append
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setUseParentHandlers(false); // No mostrar logs en consola, solo archivo
            logger.setLevel(Level.ALL);

            // 2. Solicitar Login
            System.out.println("Introduce el login (solo letras minúsculas, max 8):");
            String login = sc.nextLine();
            
            while (!comprobarNombre(login)) {
                System.out.println("Nombre incorrecto. Inténtalo de nuevo:");
                logger.log(Level.WARNING, "Intento fallido de usuario: " + login);
                login = sc.nextLine();
            }
            
            System.out.println("Login correcto: " + login);
            logger.log(Level.INFO, "Usuario '" + login + "' ha iniciado sesión correctamente.");
            System.out.println("________________________________________________________");

            // 3. Solicitar Nombre de Fichero
            System.out.println("Introduce el nombre del archivo a leer (ej. datos.txt):");
            String nombreFix = sc.nextLine();
            
            while (!nombreFix(nombreFix)) {
                System.out.println("Formato de nombre incorrecto (máx 8 car. + extensión 3 car.). Reintenta:");
                logger.log(Level.WARNING, "Nombre de fichero no válido: " + nombreFix);
                nombreFix = sc.nextLine();
            }

            // 4. Lectura del Fichero
            File f = new File(nombreFix);
            if (f.exists() && f.isFile()) {
                System.out.println("Contenido del fichero " + nombreFix + ":");
                br = new BufferedReader(new FileReader(f));
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }
                logger.log(Level.INFO, "Fichero '" + nombreFix + "' leído con éxito.");
            } else {
                System.out.println("El fichero no existe físicamente en el disco.");
                logger.log(Level.SEVERE, "Fichero no encontrado: " + nombreFix);
            }

        } catch (SecurityException | IOException e) {
            logger.log(Level.SEVERE, "Error en el sistema de E/S o seguridad", e);
        } finally {
            // 5. Cierre de recursos (CRUCIAL para evitar archivos .lck y duplicados)
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Error al cerrar el BufferedReader", ex);
            }
            
            if (fh != null) {
                fh.close(); // Esto libera el archivo .log y borra el .lck
            }
            sc.close();
        }
    }

    /**
     * Valida que el login tenga letras minúsculas y longitud de 1 a 8.
     */
    public static boolean comprobarNombre(String nombre) {
        Pattern pt = Pattern.compile("^[a-z]{1,8}$");
        Matcher m = pt.matcher(nombre);
        return m.matches();
    }

    /**
     * Valida nombre de archivo (máx 8 letras) y extensión (exactamente 3 letras).
     */
    public static boolean nombreFix(String nombreFix) {
        // Explicación regex: Inicio, 1-8 letras, un punto literal, 3 letras, fin.
        Pattern p = Pattern.compile("^[a-zA-Z0-9]{1,8}\\.[a-zA-Z]{3}$");
        Matcher m = p.matcher(nombreFix);
        return m.matches();
    }
}
/*Esto solo captura excepciones que se lancen dentro del try, es decir, errores reales de ejecución, como:

NullPointerException

InputMismatchException

etc.

No captura un “nombre incorrecto”, porque comprobarNombre(login) devuelve false, pero no lanza excepción.
     try{
         while(!comprobarNombre(login)){
             login=sc.nextLine();
         }
     }catch(Exception e){
         logger.log(Level.WARNING,"Usuario incorrecto");
         
     }*/
 /*Carácter	Regex literal
.	\\.
(	\\(
)	\\)
[	\\[
]	\\]
{	\\{
}	\\}
+	\\+
`	`
?	\\?
*	\\*
^	\\^
$	\\$
\	\\\\*/
