/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P7;

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
public class E1 {
    public static void main(String[] args) {
        String texto = "Lorem ipsum dolor sit amet, consectetur adipiscing elit...";
        String user = "eli";
        String password = "2011alex";
        String semilla = user + password;
        
        // 1. Generar la clave de 128 bits
        SecretKey secretKey = generarClave(semilla);
        String nombreFichero = "fichero.cifrado";

        try {
            // 2. Encriptar y guardar
            byte[] encriptado = encriptar(texto, secretKey);
            escribirFichero(nombreFichero, encriptado);
            System.out.println("Fichero cifrado guardado correctamente.");

            // 3. Leer y desencriptar
            byte[] bytesFichero = leerFichero(nombreFichero);
            String textoDesencriptado = descifrarTexto(bytesFichero, secretKey);

            // 4. Mostrar resultado
            System.out.println("Contenido desencriptado:");
            System.out.println(textoDesencriptado);

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
        byte[] bytes = new byte[16]; // 16 bytes = 128 bits
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
/*public static SecretKey generarClave(String semilla, int tamanoBits) {
    // 1. Convertimos los bits a bytes (dividiendo por 8)
    int tamanoBytes = tamanoBits / 8; 
    
    SecureRandom random = new SecureRandom(semilla.getBytes(StandardCharsets.UTF_8));
    byte[] bytes = new byte[tamanoBytes]; 
    
    random.nextBytes(bytes); 
    return new SecretKeySpec(bytes, "AES");
}Para 128 bits: byte[] bytes = new byte[16]; (Como lo tienes ahora).

Para 192 bits: byte[] bytes = new byte[24];

Para 256 bits: byte[] bytes = new byte[32];

Nota importante: Para usar claves de 256 bits en algunas versiones antiguas de Java, era necesario instalar unos archivos de "jurisdicción ilimitada" (JCE), aunque en las versiones modernas de Java 8, 11 y superiores ya viene habilitado por defecto.*/
