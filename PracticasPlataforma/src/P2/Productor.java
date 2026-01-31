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
public class Productor extends Thread{
    private char[] letrasProductor;
    private Monitor monitor;
    private int capacidadMaxima;

    public Productor(Monitor monitor,int capacidadMaxima) {
        this.monitor = monitor;
        this.letrasProductor=new char[capacidadMaxima];
        this.capacidadMaxima=capacidadMaxima;
    }

    @Override
    public void run() {
        for (int i = 0; i < capacidadMaxima; i++) {
            char letra=(char)('a' + Math.random()*26);
            try {
                monitor.llenarLetras(letra);
            } catch (InterruptedException ex) {
                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
   }
