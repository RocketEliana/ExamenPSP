/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio1;

/**
 *
 * @author User
 */
public class Productor extends Thread{
    private Monitor monitor;
    private int datosProducir;

    public Productor(Monitor monitor, int datosProducir) {
        this.monitor = monitor;
        this.datosProducir = datosProducir;
    }

   

    @Override
    public void run() {
        for (int i = 0; i < datosProducir; i++) {
            char letra=(char) ((int)'a' + (Math.random()*26));
            monitor.getLetras().add(letra);
            monitor.producir(letra);
            
        }
    }
    

    
}
