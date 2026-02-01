/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package barbero;

import java.util.concurrent.Semaphore;

/**
 *
 * @author User
 */
public class Barbero {
private static final int N_SILLAS = 3;
    private static Semaphore clientes = new Semaphore(0); // Clientes esperando
    private static Semaphore barberos = new Semaphore(0); // Barbero listo
    private static Semaphore mutex = new Semaphore(1);    // Exclusión mutua para las sillas
    private static int sillasLibres = N_SILLAS;

    public static void main(String[] args) {
        Thread b = new Thread(new BarberoD());
        b.start();

        // Simulamos la llegada de 10 clientes con intervalos aleatorios
        for (int i = 1; i <= 10; i++) {
            Thread c = new Thread(new Cliente(i));
            c.start();
            try { Thread.sleep((long) (Math.random() * 2000)); } catch (InterruptedException e) {}
        }
    }

    static class BarberoD implements Runnable {
        public void run() {
            try {
                while (true) {
                    clientes.acquire(); // Espera a que llegue un cliente (se duerme)
                    
                    mutex.acquire();    // Accede a la zona de sillas
                    sillasLibres++;     // El cliente deja la silla de espera
                    barberos.release(); // El barbero avisa que está listo
                    mutex.release();    // Suelta el acceso a las sillas
                    
                    // Cortando el pelo (fuera de la zona crítica)
                    System.out.println("El barbero está CORTANDO el pelo...");
                    Thread.sleep(3000); 
                    System.out.println("El barbero ha TERMINADO el corte.");
                }
            } catch (InterruptedException e) {}
        }
    }
  
    static class Cliente implements Runnable {
        int id;
        public Cliente(int id) { this.id = id; }

        public void run() {
            try {
                mutex.acquire(); // Mira si hay sillas libres
                if (sillasLibres > 0) {
                    sillasLibres--;
                    System.out.println("Cliente " + id + " se sienta. Sillas libres: " + sillasLibres);
                    
                    clientes.release(); // Avisa al barbero que hay alguien
                    mutex.release();    // Suelta el acceso a las sillas
                    
                    barberos.acquire(); // Espera a que el barbero esté libre
                    System.out.println("Cliente " + id + " está recibiendo su corte.");
                } else {
                    System.out.println("Cliente " + id + " se va: Barbería LLENA.");
                    mutex.release();
                }
            } catch (InterruptedException e) {}
        }
    }
    
}
/*El Enunciado
El problema consiste en una barbería con un barbero, una silla de barbero y un número limitado de sillas de espera.

Si no hay clientes, el barbero se duerme en su silla.

Si llega un cliente y todas las sillas de espera están ocupadas, el cliente se va.

Si hay sillas libres pero el barbero está ocupado, el cliente se sienta a esperar.

Si el barbero está dormido, el cliente lo despierta.*/
  /*

1. El Semáforo clientes (Inicia en 0)
Este semáforo representa "clientes esperando ser atendidos".

Al inicio (0): No hay clientes. Cuando el Barbero hace clientes.acquire(), intenta restar 1 a 0. Como no puede, el hilo se bloquea. Así es como simulamos que el barbero se queda dormido.

Cuando llega un cliente: El cliente hace clientes.release(), subiendo el semáforo a 1. Eso "despierta" al barbero.

2. El Semáforo barberos (Inicia en 0)
Este representa "barberos disponibles para empezar a cortar ahora mismo".

Al inicio (0): El barbero no está listo (está durmiendo o preparando las tijeras).

El Cliente llega: Hace un barberos.acquire(). Como está en 0, el cliente se queda bloqueado en la silla de espera.

El Barbero se despierta: Cuando el barbero está listo para trabajar, hace barberos.release(). En ese momento, el cliente que estaba bloqueado puede pasar a la silla de cortar.

Resumen visual:
Si pones un 1 en clientes: El barbero pensaría que ya hay alguien esperando nada más abrir la tienda y se pondría a cortar el aire.

Si pones un 1 en barberos: El primer cliente que llegara entraría directo a la silla sin esperar a que el barbero se despierte o esté listo.

En cambio, el mutex SÍ empieza en 1
El mutex (exclusión mutua) es el único que empieza en 1 porque representa una puerta:

1: La puerta está abierta, puedes entrar a mirar si hay sillas.

0: Alguien está contando las sillas en este momento, espera tu turno.

[Image showing semaphores as signaling flags between threads]

¿Lo ves más claro así? El 0 no significa "permiso denegado para siempre", significa "párate aquí hasta que otro hilo te dé permiso con un release". Es como un semáforo de tráfico en rojo que solo se pone en verde cuando ocurre un evento.*/
