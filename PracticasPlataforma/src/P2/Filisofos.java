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
public class Filisofos {//controlador,mesa
/*El problema es el siguiente: Cinco filósofos se sientan alrededor de una mesa y pasan su vida comiendo y pensando. 
    Cada filósofo tiene un plato de arroz chino y un palillo a la izquierda de su plato. Cuando un filósofo quiere comer arroz, 
    cogerá los dos palillos de cada lado del plato y comerá. El problema es el siguiente: establecer un ritual (algoritmo) que permita comer a los filósofos. El
    algoritmo debe satisfacer la exclusión mutua (dos filósofos no pueden emplear el mismo palillo a la vez), además de evitar el interbloqueo y la inanición.
*/
    //un semaforo para cinco filosfos otro para dos palillos
  
    public static void main(String[] args) {
        Semaphore[] palillos = new Semaphore[5];//recursos reales,palillos,array de semaforos con un permiso,cinco palillos que solo puede cojer uno
        for (int i = 0; i < 5; i++) {palillos[i] = new Semaphore(1);}

        Filosofo[] filosofos = new Filosofo[5];//array de hilos!!!!!!!!!!!!!!!!
        for (int i = 0; i < 5; i++) {
            // El último filósofo rompe la simetría para evitar deadlock
            if (i == 4) {
                filosofos[i] = new Filosofo(i, palillos[0], palillos[i]); 
            } else {
                filosofos[i] = new Filosofo(i, palillos[i], palillos[i + 1]);
            }
            filosofos[i].start();
        }
    }
}
    
    
    
    

