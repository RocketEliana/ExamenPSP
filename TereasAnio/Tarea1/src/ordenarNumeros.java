
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class ordenarNumeros {
    public static void main(String[] args) {
        ArrayList<Integer> numeros= new ArrayList<>();
        Scanner sc=new Scanner(System.in);
        System.out.println("Introduce numeros,para salir mete -1");
        int num=0;
        while(num != -1){
            num=entero(sc,"Añade numero a la coleccion");
            numeros.add(num);
        }
        Collections.sort(numeros);
        numeros.forEach(n -> {
    System.out.println(n);
});

            
        }
        
        
    
    public  static int entero(Scanner sc, String mensaje){
        boolean valido=false;
        int numero;
        while(!valido){
        try{
            System.out.println(mensaje);
            numero=sc.nextInt();
            sc.nextLine();
            return numero;
           }catch(InputMismatchException e){
            System.out.println("Introduce uhn numero valido");
            sc.nextLine();
        }
    }
        return 0;
    }}
    
    

