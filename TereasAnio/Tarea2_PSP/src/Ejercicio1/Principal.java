/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio1;

/**
 *
 * @author User
 */
public class Principal {

    public static void main(String[] args) {
        Monitor monitor = new Monitor(6);
         Productor productor = new Productor(monitor, 15);
        Consumidor consumidor = new Consumidor(monitor, 15);
        productor.start();
        consumidor.start();

    }

}
