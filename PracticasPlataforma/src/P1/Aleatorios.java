/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P1;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Aleatorios {
    /*Segunda parte: implementa una aplicación, llamada 'aleatorios', que genere al menos 40 números aleatorios (entre 0 y 100), y que los escriba en su salida estándar.*/
    //(int)(Math.random*(max-min))+1-->101
    public static void main(String[] args) {
        ArrayList numeros=new ArrayList();
        for (int i = 0; i < 100; i++) {
            int aleatorio=(int)(Math.random()*101);
            numeros.add(aleatorio);
            
        }
        numeros.forEach(o->{
            System.out.println(o + "\n");
        });
    }
    
}
