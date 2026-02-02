/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package desencriptar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author User
 */
public class Desencriptar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String user = "dani";
        String password = "PSP";
        String semilla = user + password;
        String userRespuest = "eli";
        String semillaRespuesta = userRespuest + password;
        SecretKey secretKey = generarClave(semilla);
        SecretKey secretKeyRespuesta = generarClaveRespuesta(semillaRespuesta);
        String nombreFichero = "pregunta.cifrada";
        
        String texto="Quiero un tres";
                String nombreFicheroRespuesta= "respuesta.cifrada";
        

        try {
            // 2. Encriptar y guardar
            //  byte[] encriptado = encriptar(texto, secretKey);
            //escribirFichero(nombreFichero, encriptado);
            // System.out.println("Fichero cifrado guardado correctamente.");

            // 3. Leer y desencriptar
            byte[] bytesFichero = leerFichero(nombreFichero);
            String textoDesencriptado = descifrarTexto(bytesFichero, secretKey);

            // 4. Mostrar resultado
            System.out.println("Contenido desencriptado:");
            System.out.println(textoDesencriptado);
            byte[]  encriptado=encriptar(texto, secretKeyRespuesta);
            escribirFichero(nombreFicheroRespuesta, encriptado);
                byte[] bytesFicheroRespuesta = leerFichero(nombreFicheroRespuesta);
            String textoDesencriptadoRespuesta = descifrarTexto(bytesFicheroRespuesta, secretKeyRespuesta);

            // 4. Mostrar resultado
            System.out.println("Contenido desencriptado:");
            System.out.println(textoDesencriptadoRespuesta);
            
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Genera una clave AES de 128 bits a partir de una semilla.
     */
    public static SecretKey generarClave(String semilla) {
        // Importante: Usar la semilla para inicializar el generador
        SecureRandom random = new SecureRandom(semilla.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = new byte[32]; // 16 bytes = 128 bits 32 256
        random.nextBytes(bytes);    // Llenamos el array con valores aleatorios basados en la semilla
        return new SecretKeySpec(bytes, "AES");
    }

    public static SecretKey generarClaveRespuesta(String semilla) {
        // Importante: Usar la semilla para inicializar el generador
        SecureRandom random = new SecureRandom(semilla.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = new byte[16]; // 16 bytes = 128 bits 32 256
        random.nextBytes(bytes);    // Llenamos el array con valores aleatorios basados en la semilla
        return new SecretKeySpec(bytes, "AES");
    }

    public static byte[] encriptar(String texto, SecretKey secretkey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Especificaciones: AES(Rijndael) / Modo ECB / Relleno PKCS5
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretkey);
        return cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
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

    public static String descifrarTexto(byte[] bytes, SecretKey secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(bytes), StandardCharsets.UTF_8);
    }

}
