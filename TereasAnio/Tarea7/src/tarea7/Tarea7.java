/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tarea7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Tarea7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String texto="\"Lorem ipsum dolor sit amet,"
                + " consectetur adipiscing elit"
                + ", sed do eiusmod tempor incididunt"
                + " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud "
                + "exercitation ullamco laboris nisi ut aliquip"
                + " ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse"
                + " cillum dolore eu fugiat nulla pariatur."
                + " Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim anim id est laborum.\"";
        String user="eli";
        String password="2011alex";
        String semilla=user + password;
        SecretKey secretKey=generarClave(semilla);
        try {
            byte[] encriptado=encriptar(texto, secretKey);
            String cifrado="fichero.certificado";
            escribir5Fichero(cifrado, encriptado);
            byte[] bytesFichero=leerFichero(cifrado);
            String textoDesencriptado=descifrarTexto(bytesFichero, secretKey);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Tarea7.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Tarea7.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Tarea7.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Tarea7.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Tarea7.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Tarea7.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //clave, encriptar,escribirFichero,leerFicheri,desencriptar
    public static SecretKey generarClave(String semilla){
        SecureRandom random=new SecureRandom(semilla.getBytes(StandardCharsets.UTF_8));
        byte[] bytes =new byte[16];//128/8
       return new SecretKeySpec(bytes,"AES");//METER EL ALGORITMO
    }
    public static byte[] encriptar(String texto,SecretKey secretkey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretkey);
        return cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
    }
    public static void escribir5Fichero(String fichero,byte[] bytes) throws FileNotFoundException, IOException{
        FileOutputStream fos =new FileOutputStream(fichero);
        fos.write(bytes);
    }
    public static byte[] leerFichero(String fichero) throws FileNotFoundException, IOException{
        FileInputStream fis=new FileInputStream(fichero);
        return fis.readAllBytes();
        
    }
    public static String descifrarTexto(byte[] bytes,SecretKey secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        return new String(cipher.doFinal(bytes),StandardCharsets.UTF_8);
    }
    
    
}
