
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class lenguaje {
    public static void main(String[] args) {
        BufferedWriter bw = null;
        try {
            if (args.length < 2) {
                System.out.println("Argumentos insuficientes");
                return;
            }

            int numeroCadenas = Integer.valueOf(args[0]);
            String nombreFichero = args[1];
            File f = new File(nombreFichero);

            bw = new BufferedWriter(new FileWriter(f));

            for (int i = 0; i < numeroCadenas; i++) { // nº de conjuntos
                StringBuilder sb = new StringBuilder();

                for (int j = 0; j < 10; j++) { // 10 letras por línea
                    char letra = (char) ('a' + (Math.random() * 26));
                    sb.append(letra);
                }

                bw.write(sb.toString());
                bw.newLine(); // línea independiente
            }

        } catch (IOException ex) {
            Logger.getLogger(lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(lenguaje.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
