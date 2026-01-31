import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread {
    private static final int PUERTO = 2000;
    private Socket s;

    public Servidor(Socket s) {
        this.s = s;
    }

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado. Esperando clientes...");
            while (true) {
                Socket clienteSocket = ss.accept();
                System.out.println("Cliente conectado desde: " + clienteSocket.getInetAddress());
                
                // IMPORTANTE: .start() para que sea multihilo real
                Servidor hiloServidor = new Servidor(clienteSocket);
                hiloServidor.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, "Error en el servidor", ex);
        }
    }

    @Override
    public void run() {
        // El bloque try-with-resources cierra automáticamente dis, dos y el socket s
        try (DataInputStream dis = new DataInputStream(s.getInputStream());
             DataOutputStream dos = new DataOutputStream(s.getOutputStream())) {

            dos.writeUTF("Bienvenido al juego! Adivina el numero del 0 al 9.");
            dos.flush();

            int aleatorio = (int) (Math.random() * 10);
            boolean juegoTerminado = false;

            while (!juegoTerminado) {
                // El servidor se queda pausado aquí hasta que el cliente envía el Int
                int intento = dis.readInt();
                
                if (intento == aleatorio) {
                    dos.writeUTF("Has ganado!");
                    juegoTerminado = true;
                } else if (aleatorio > intento) {
                    dos.writeUTF("El numero es mayor");
                } else {
                    dos.writeUTF("El numero es menor");
                }
                dos.flush();
            }

        } catch (IOException ex) {
            System.out.println("Cliente desconectado.");
        } finally {
            try {
                if (s != null) s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}