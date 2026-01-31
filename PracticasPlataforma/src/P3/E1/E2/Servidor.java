/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P3.E1.E2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author User
 */
//OJO CON PRINTWRITER,AUTOFLUS EN PARAMETRO Y EL METODO EL PRINTLN
public class Servidor extends Thread{//Lo hago directamente multihilo
    //Actividad 3.2. El objetivo del ejercicio es crear una aplicación cliente/servidor que permita el envío de
    //ficheros al cliente. Para ello, el cliente se conectará al servidor por el puerto 1500 y le solicitará el nombre de un fichero del servidor.
    //Si el fichero existe, el servidor, le enviará el fichero al cliente y éste lo mostrará por pantalla. Si el fichero no existe, el servidor le
    //enviará al cliente un mensaje de error. Una vez que el cliente ha mostrado el fichero se finalizará la conexión.
   private Socket socket;
   private static final int PUERTO= 1250;

    // El constructor recibe el socket individual
    public Servidor(Socket s) {
        this.socket = s;
    }

@Override
public void run() {

    long inicioTotal = System.currentTimeMillis(); // desde que entra el cliente

    try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         PrintWriter pw = new PrintWriter(socket.getOutputStream(), true)) {

        pw.println("Cliente conectado,bienvenido,incluya un nombre de archivo");
        String nombreFichero = br.readLine();

        File f = new File(nombreFichero);
        if (!f.exists()) {
            pw.println("El fichero no existe,saliendo del servidor");
            return;
        } else {
            pw.println("Preparando transferencia");

            long inicioProceso = System.nanoTime(); // empieza CPU real

            BufferedReader brF = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = brF.readLine()) != null) {
                pw.println(linea);
            }
            brF.close();

            long finProceso = System.nanoTime(); // termina CPU

            socket.shutdownOutput(); // fin envío

            String mensajeFinal = br.readLine();
            System.out.println(mensajeFinal);

            long finTotal = System.currentTimeMillis();

            System.out.println("\n--- ESTADÍSTICAS SERVIDOR ---");
            System.out.println("CPU (Procesamiento): " + (finProceso - inicioProceso) + " ns");
            System.out.println("TOTAL (Cliente conectado): " + (finTotal - inicioTotal) + " ms");
        }

    } catch (IOException e) {
        e.printStackTrace();
    }
}


    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(PUERTO  )) {
            while (true) {
                Socket cliente = ss.accept(); // Acepta conexión
                // Crea e inicia un hilo nuevo para ese cliente
                new Servidor(cliente).start(); 
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    }
/*OTRA OPCION
while((linea=br.readLine())!=null){
    if(linea.equals("EOF")) break;
    bwF.write(linea + "\n");
}
pw.println("EOF.......->Archivo rECIBIDO");
Qué es procesamiento (CPU)

En este tipo de ejercicio se considera procesamiento:

El tiempo que el programa está trabajando activamente:

leyendo el fichero del disco

copiando datos a memoria

escribiendo al socket

No se considera procesamiento:

esperar a que el cliente responda

latencia de red

bloqueos de readLine()

Por qué el inicio está “después de comunicarse”

Tienes razón en esto:

pw.println("Preparando transferencia");
long inicioProceso = System.nanoTime();


Ya ha habido comunicación antes.
Pero eso es intencionado.

Porque esa comunicación es:

protocolo

sincronización

latencia

Y eso pertenece a tiempo de transacción, no a CPU.
*/
    
    

