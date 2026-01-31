package pkg3.pkg2;

import java.io.*;
import java.net.Socket;

public class Cliente {
    private static final int PUERTO = 1500;
    private static final String HOST = "localhost";
    private Socket s;
    private BufferedReader br;
    private BufferedWriter bw;

    public Cliente() throws IOException {
        s = new Socket(HOST, PUERTO);
        bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    public void inicioIntercambio() throws IOException {
        // 1. Recibir saludo inicial
        String saludo = br.readLine();
        System.out.println("SERVIDOR: " + saludo);

        // 2. Pedir el archivo
        bw.write("HolMundo.txt");
        bw.newLine();
        bw.flush();

        // 3. Bucle para sacar el contenido por consola
        System.out.println("--- CONTENIDO DEL ARCHIVO ---");
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.equals("EOF")) { // Si leemos la marca, paramos
                break;
            }
            if (linea.startsWith("Error:")) { // Si el archivo no existe
                System.out.println(linea);
                break;
            }
            System.out.println(linea); // Imprime el contenido real
        }
        System.out.println("-----------------------------");
    }

    public void cerrar() {
        try {
            if (bw != null) bw.close();
            if (br != null) br.close();
            if (s != null) s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Cliente cli = null;
        try {
            cli = new Cliente();
            cli.inicioIntercambio();
        } catch (IOException e) {
            System.err.println("Error: No se pudo conectar.");
        } finally {
            if (cli != null) cli.cerrar();
        }
    }
}