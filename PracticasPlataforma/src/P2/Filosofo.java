/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P2;

import java.util.concurrent.Semaphore;

/**
 *
 * @author User
 */
//en este ejercicio hay dos ambitos,los semafores del filosofo que son los palillos, y los de la mesa que son los filosofos
public class Filosofo extends Thread {
    private int id;
    private Semaphore izquierdo, derecho;//manos

    public Filosofo(int id, Semaphore izq, Semaphore der) {
        this.id = id;
        this.izquierdo = izq;
        this.derecho = der;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Filósofo " + id + " pensando...");
                Thread.sleep(1000); // Tiempo pensando

                // Intentar comer
                izquierdo.acquire();
                derecho.acquire();

                System.out.println("Filósofo " + id + " COMIENDO ");
                Thread.sleep(1000); // Tiempo comiendo

                derecho.release();
                izquierdo.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
    
    

