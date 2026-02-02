package P1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Lee números desde la entrada estándar y los ordena
 */
public class OrdenarNumeros {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> numeros = new ArrayList<>();

        // Leer mientras haya números
        while (sc.hasNextInt()) {
            numeros.add(sc.nextInt());
        }

        // Ordenar de menor a mayor
        Collections.sort(numeros);

        // Imprimir resultado
        numeros.forEach(n -> System.out.println(n));
    }
}
