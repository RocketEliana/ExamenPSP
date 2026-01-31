/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Consumidor extends Thread{
    private char[] letras;
    private Monitor monitor;
    private int maxCapacidad;

    public Consumidor(Monitor monitor, int maxCapacidad) {
        this.monitor = monitor;
        this.maxCapacidad = maxCapacidad;
        this.letras=new char[maxCapacidad];
    }

    @Override
    public void run() {
        for (int i = 0; i < maxCapacidad; i++) {
            char a;
            try {
                a = monitor.sacarLetra();
                  letras[i]=a;
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
            }
          
            
        }
    }
    
    
    
    
}
