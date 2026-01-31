import java.io.*;
import java.util.logging.*;
import java.util.regex.*;

public class Tarea6 {

    public static void main(String[] args) {
        Logger log = Logger.getLogger("mylog");

        try {
            // Configurar el logger
            FileHandler fh = new FileHandler("C:\\Users\\User\\Documents\\NetBeansProjectse\\PSP_T6\\src\\mylog.log", true);
            fh.setFormatter(new SimpleFormatter());
            log.addHandler(fh);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String usuario;
            Pattern usuarioP = Pattern.compile("^[a-z]{1,8}$"); // 1 a 8 letras minúsculas
            while (true) {
                System.out.print("Introduce usuario (1-8 letras minúsculas): ");
                usuario = br.readLine();

                if (!usuarioP.matcher(usuario).matches()) {
                    System.out.println("Formato de usuario incorrecto");
                    log.warning("Formato de usuario inválido: " + usuario);
                } else {
                    System.out.println("Usuario válido: " + usuario);
                    break;
                }
            }


            String fichero;
            File file;
            Pattern ficheroP = Pattern.compile("^[a-zA-Z]{1,8}\\.[a-zA-Z]{3}$"); // hasta 8 letras + extensión 3 letras

            while (true) {
                System.out.print("Introduce el nombre del fichero (máx 8 letras + extensión 3 letras): ");
                fichero = br.readLine();

                Matcher m = ficheroP.matcher(fichero);

                if (!m.matches()) {
                    System.out.println("Formato de fichero inválido");
                    log.warning("Formato de fichero inválido: " + fichero);
                } else {
                    file = new File(fichero);

                    if (!file.exists()) {
                        System.out.println("El fichero no existe");
                        log.warning("Fichero no encontrado: " + fichero);
                    } else {
                        System.out.println("Fichero seleccionado: " + fichero);

                        String extension = fichero.split("\\.")[1].toLowerCase();

                        if (extension.equals("txt") || extension.equals("csv") || extension.equals("xml") || extension.equals("log") || extension.equals("bat")) {
                            // Fichero de texto
                            try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                                String linea;
                                System.out.println("\n--- Contenido del fichero ---");
                                while ((linea = fileReader.readLine()) != null) {
                                    System.out.println(linea);
                                }
                            }
                        } else if (extension.equals("bin") || extension.equals("exe") || extension.equals("jpg")) {
                            // Fichero binario (mostrar en hexadecimal)
                            try (FileInputStream fis = new FileInputStream(file)) {
                                int b;
                                System.out.println("\n--- Contenido en hexadecimal ---");
                                while ((b = fis.read()) != -1) {
                                    System.out.print(String.format("%02X ", b));
                                }
                                System.out.println();
                            }
                        } else {
                            System.out.println("Extensión no reconocida por el programa");
                            log.warning("Extensión no reconocida: " + extension);
                        }

                        break; // Salir del bucle
                    }
                }
            }

        } catch (IOException | SecurityException e) {
            log.severe("Error del programa: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
