/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P3.E1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author User
 */
public class Servidor {
    private static final int PUERTO = 2000;
    // 1. Declaramos todo como atributos para que todos los métodos los vean
    private ServerSocket ss;
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;

    public Servidor() throws IOException {
        try {
            ss = new ServerSocket(PUERTO);
            System.out.println("Esperando....");
            s = ss.accept();
            
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            interactuarCliente();
            
        } finally {
            // 2. El finally se pone AQUÍ, en el constructor o donde se gestiona la conexión
            cerrarRecursos();
        }
    }
public void interactuarCliente() throws IOException {
    dos.writeUTF("Good morning Vietnam!");
    int aleatorio = (int) (Math.random() * 101);
    int intentosRestantes = 3;

    while (intentosRestantes > 0) {
        dos.writeUTF("Dime un numero (Intentos: " + intentosRestantes + ")");
        
        // 1. Marca de tiempo TOTAL (espera al cliente)
        long inicioTotal = System.currentTimeMillis();

        // AQUÍ ES DONDE SE SUELE QUEDAR PILLADO SI EL CLIENTE NO ENVÍA NADA
        int intento = dis.readInt(); 
        
        // 2. Marca de tiempo PROCESO (CPU)
        long inicioProceso = System.nanoTime();

        String respuesta = "";
        boolean acertado = (intento == aleatorio);

        if (acertado) {
            respuesta = "¡Has ganado!";
        } else {
            intentosRestantes--;
            if (intentosRestantes == 0) {
                respuesta = "GAME OVER. Era el " + aleatorio;
            } else {
                respuesta = (intento < aleatorio) ? "Es MAYOR" : "Es MENOR";
            }
        }

        // 3. Cálculo de tiempos ANTES de enviar la respuesta
        long finProceso = System.nanoTime();
        long finTotal = System.currentTimeMillis();

        // ESTO TIENE QUE SALIR POR CONSOLA SÍ O SÍ
        System.out.println("\n--- ESTADÍSTICAS INTENTO ---");
        System.out.println("CPU (Procesamiento): " + (finProceso - inicioProceso) + " ns");
        System.out.println("RED + ESPERA (Total): " + (finTotal - inicioTotal) + " ms");
        System.out.flush(); // <--- ESTO FUERZA A LA CONSOLA A MOSTRAR EL TEXTO

        // 4. Enviamos al cliente
        dos.writeUTF(respuesta);
        dos.flush();

        if (acertado) break;
    }
}

    // 3. Método auxiliar para no ensuciar el código,EL QUE ABRE,CIERRA
    private void cerrarRecursos() {
        try {
            if (dis != null) dis.close();
            if (dos != null) dos.close();
            if (s != null) s.close();
            if (ss != null) ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new Servidor();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
