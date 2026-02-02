/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P7;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DES {//solo compatible para 56/64 bits,si es diferente tiene que ser AES

    public static void main(String[] args) {
        String texto = "Este es el texto que vamos a cifrar con el algoritmo DES.";
        String user = "eli";
        String password = "2011alex";
        String semilla = user + password;
        
        // 1. Generar la clave de 64 bits (Estándar para DES)
        SecretKey secretKey = generarClaveDES(semilla);
        String nombreFichero = "fichero_des.cifrado";

        try {
            // 2. Encriptar y guardar
            byte[] encriptado = encriptarDES(texto, secretKey);
            escribirFichero(nombreFichero, encriptado);
            System.out.println("Fichero cifrado con DES guardado.");

            // 3. Leer y desencriptar
            byte[] bytesFichero = leerFichero(nombreFichero);
            String textoDesencriptado = descifrarDES(bytesFichero, secretKey);

            System.out.println("Contenido desencriptado: " + textoDesencriptado);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static SecretKey generarClaveDES(String semilla) {
        SecureRandom random = new SecureRandom(semilla.getBytes(StandardCharsets.UTF_8));
        // DES usa claves de 64 bits = 8 bytes
        byte[] bytes = new byte[8]; 
        random.nextBytes(bytes);
        // Indicamos "DES" como algoritmo
        return new SecretKeySpec(bytes, "DES");
    }

    public static byte[] encriptarDES(String texto, SecretKey secretkey) throws Exception {
        // Modo ECB y Relleno PKCS5 igual que en AES
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretkey);
        return cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
    }

    public static String descifrarDES(byte[] bytes, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(bytes), StandardCharsets.UTF_8);
    }

    public static void escribirFichero(String fichero, byte[] bytes) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fichero)) {
            fos.write(bytes);
        }
    }

    public static byte[] leerFichero(String fichero) throws IOException {
        try (FileInputStream fis = new FileInputStream(fichero)) {
            return fis.readAllBytes();
        }
    }
}
    /*Detalles de la tarea de esta unidad.
Enunciado.
Ejercicio 1.

De igual manera a lo visto en el tema, ahora te proponemos un ejercicio que genere una cadena de texto y la deje almacenada en un fichero encriptado, en la raíz del proyecto hayas creado, con el nombre fichero.cifrado.

Para encriptar el fichero, utilizarás el algoritmo Rijndael o AES, con las especificaciones de modo y relleno siguientes: Rijndael/ECB/PKCS5Padding.

La clave, la debes generar de la siguiente forma:

A partir de un número aleatorio con semilla la cadena del nombre de usuario + password.
Con una longitud o tamaño 128 bits.
Para probar el funcionamiento, el mismo programa debe acceder al fichero encriptado para desencriptarlo e imprimir su contenido.

Criterios de puntuación. Total 10 puntos.
Total 10 puntos.

Se tendrá en cuenta:

El funcionamiento correcto del programa.
El uso adecuado del API criptográfico.
Tratamiento adecuado de posibles excepciones.*/

