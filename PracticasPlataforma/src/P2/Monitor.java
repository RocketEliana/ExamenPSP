package P2;

public class Monitor {

    private char[] buffer;
    private int capacidad;
    private int contador;

    public Monitor(int capacidad) {
        this.capacidad = capacidad;
        this.buffer = new char[capacidad];
        this.contador = 0;
    }

    public synchronized void llenarLetras(char letra) throws InterruptedException {
        while (contador == capacidad) {
            System.out.println(" Buffer lleno → Productor esperando...");
            wait();
        }

        buffer[contador] = letra;
        contador++;

        System.out.println("Producido: " + letra + " | En buffer: " + contador);

        notifyAll();
    }

    public synchronized char sacarLetra() throws InterruptedException {
        while (contador == 0) {
            System.out.println(" Buffer vacío → Consumidor esperando...");
            wait();
        }

        contador--;
        char letra = buffer[contador];

        System.out.println("Consumido: " + letra + " | En buffer: " + contador);

        notifyAll();
        return letra;
    }
}
