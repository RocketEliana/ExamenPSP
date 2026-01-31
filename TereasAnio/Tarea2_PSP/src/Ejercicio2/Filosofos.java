/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio2;

/**
 *
 * @author User
 */
import java.util.concurrent.Semaphore;

class Filosofo extends Thread {
    private Semaphore palilloIzq;
    private Semaphore palilloDer;
    private int id;

    public Filosofo(int id, Semaphore palilloIzq, Semaphore palilloDer) {
        this.id = id;
        this.palilloIzq = palilloIzq;
        this.palilloDer = palilloDer;
    }

    @Override
    public void run() {
        try {
            while(true) {
                pensar();
                // Para romper deadlock, el último filósofo cambia el orden
                if(id == 4) {
                    palilloDer.acquire();
                    palilloIzq.acquire();
                } else {
                    palilloIzq.acquire();
                    palilloDer.acquire();
                }
                comer();
                palilloIzq.release();
                palilloDer.release();
            }
        } catch (InterruptedException e) { }
    }

    private void pensar() throws InterruptedException {
        System.out.println("Filosofo " + id + " pensando");
        Thread.sleep((long)(Math.random()*1000));
    }

    private void comer() throws InterruptedException {
        System.out.println("Filosofo " + id + " comiendo");
        Thread.sleep((long)(Math.random()*1000));
    }
}

public class Filosofos {
    public static void main(String[] args) {
        int N = 5;
        Semaphore[] palillos = new Semaphore[N];
        for(int i=0; i<N; i++)
            palillos[i] = new Semaphore(1);

        Filosofo[] filosofos = new Filosofo[N];
        for(int i=0; i<N; i++) {
            filosofos[i] = new Filosofo(i, palillos[i], palillos[(i+1)%N]);//palillos[4]	(4+1)%5 = palillos[0] 	tiene el 4 y el 0 (da la vuelta)
            filosofos[i].start();
        }
    }
}

