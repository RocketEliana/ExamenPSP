/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio1;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Consumidor extends Thread{
    private Monitor monitor;
    private int datosTransferir;

    public Consumidor(Monitor monitor, int datosTransferir) {
        this.monitor = monitor;
        this.datosTransferir = datosTransferir;
    }

    

    @Override
    public void run() {
        for (int i = 0; i < datosTransferir; i++) {
            char letra=monitor.transferir();
            
            
        }
        
    }
    
}
