/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class OrdenarNumeros {
    /*Primera parte: implementa una aplicación que ordena un conjunto indeterminado de números que recibe a través de su entrada estándar; y muestra el resultado de la ordenación en su salida estándar. 
    La aplicación se llamará 'ordenarNumeros'.*/
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        ArrayList numeros=new ArrayList();
       int n=comprobarInt(sc, "Cuantos numeros introduciras");
        for (int i = 0; i < n; i++) {
           int numeroOrdenar= comprobarInt(sc, "Introduce un entero");
           numeros.add(numeroOrdenar);
            }
        sc.nextLine();
        Collections.sort(numeros);
        numeros.forEach(o->{System.out.println(
                o + "\n");
        }
        );
    }
    public  static int comprobarInt(Scanner sc,String mensaje){
        int numero=0;
        boolean correcto=false;
        while(!correcto){
        try{
            System.out.println(mensaje);
            numero=sc.nextInt();
            sc.nextLine();
            correcto=true;
            return numero;
            
            
        }catch(InputMismatchException e){
            System.out.println("Debes introducir un entero");
            sc.nextLine();
        }
        }
        return -1;
    }
    
}
