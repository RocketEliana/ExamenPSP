/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio1;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Monitor {
    private int capacidad;
    private ArrayList letras;

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public ArrayList getLetras() {
        return letras;
    }
    
    

    public Monitor(int capacidad) {
        this.capacidad = capacidad;
        letras=new ArrayList();
    }
  public synchronized void producir(char letra){
      while(letras.size()>=capacidad){
          try {
              wait();
              System.out.println("Almacen lleno");
          } catch (InterruptedException ex) {
              Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
          }
}
      letras.add(letra);
          notify();
      
      
  }
public synchronized char transferir() {
    while (letras.isEmpty()) {
        try {
            System.out.println("🔵 Esperando datos. Consumidor bloqueado...");
            wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ⚠️ doble comprobación de seguridad
    if (letras.isEmpty()) return '\0'; 

    char letra = (char) letras.remove(0);
    System.out.println("🔴 Consumido: " + letra + " (tamaño actual: " + letras.size() + ")");
    notify();
    return letra;
}

   
    
}
