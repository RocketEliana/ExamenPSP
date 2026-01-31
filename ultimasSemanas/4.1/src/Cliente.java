import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    private static final int PUERTO = 2000;
    private static final String HOST = "localhost";
    private Socket s;

    public Cliente() throws IOException {
        this.s = new Socket(HOST, PUERTO);
    }

    public static void main(String[] args) {
        try {
            Cliente c = new Cliente();
            c.iniciarJuego();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "No se pudo conectar al servidor", ex);
        }
    }

    public void iniciarJuego() {
        // Usamos try-with-resources para asegurar el cierre de flujos locales
        try (Scanner sc = new Scanner(System.in);
             DataInputStream dis = new DataInputStream(s.getInputStream());
             DataOutputStream dos = new DataOutputStream(s.getOutputStream())) {

            // Leer mensaje de bienvenida
            System.out.println("SERVIDOR: " + dis.readUTF());

            int intentos = 4;
            while (intentos > 0) {
                System.out.print("\nIntentos restantes [" + intentos + "]. Introduce numero: ");
                
                if (sc.hasNextInt()) {
                    int numero = sc.nextInt();
                    sc.nextLine(); // Limpiar buffer

                    // Enviar al servidor
                    dos.writeInt(numero);
                    dos.flush();

                    // Leer respuesta del servidor
                    String respuesta = dis.readUTF();
                    System.out.println("SERVIDOR: " + respuesta);

                    if (respuesta.equals("Has ganado!")) {
                        break;
                    }

                    intentos--;
                } else {
                    System.out.println("Por favor, introduce un numero valido.");
                    sc.next(); // Descartar entrada no válida
                }

                if (intentos == 0) {
                    System.out.println("\n¡Oh no! Te has quedado sin intentos.");
                }
            }

        } catch (IOException ex) {
            System.out.println("Conexion perdida con el servidor.");
        } finally {
            try {
                if (s != null) s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}