
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class EjemploFirmar {
    /*Solicita el nombre del usuario que va a utilizar la aplicación. El login tiene una longitud de 8 caracteres y está compuesto únicamente por letras minúsculas.
Solicita al usuario el nombre de un fichero que quiere mostrar. El nombre del fichero es como máximo de 8 caracteres y tiene una extensión de 3 caracteres.
Visualiza en pantalla el contenido del fichero.*/
    public static void main(String[] args) {
   Scanner sc=new Scanner(System.in);
   String login=comprobarLogin(sc, "Introduce user");
   String nombreFichero=comprobarFichero(sc, "Introduce el nombre del fichero");
   File f =new File(nombreFichero);
   if(!f.exists()){
       System.out.println("El fichero no existe");
   }else{
       try {
           BufferedReader br=new BufferedReader(new FileReader(f));
           String linea="";
           while((linea=br.readLine()) !=null){
               System.out.println(linea);
           }
       } catch (FileNotFoundException ex) {
           Logger.getLogger(EjemploFirmar.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(EjemploFirmar.class.getName()).log(Level.SEVERE, null, ex);
       }
       
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
            valido = true; // O simplemente return login;
            return login;
        } else {
            // Aquí es donde informas al usuario del error, en lugar del catch
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
            valido = true; // O simplemente return login;
            return fichero;
        } else {
            // Aquí es donde informas al usuario del error, en lugar del catch
            System.out.println("Error: El archivo debe tener como maximo 8 caracteres y una extensui de unb maximo de tres caracteres.");
        }
    }
    return null;
}
}
        
        
    
    
    

