/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MiServidorHTTPConcurrente;

/**
 *
 * @author User
 */
public class Pagina {
 public static String CABECERA_HTML = "Content-Type: text/html; charset=UTF-8";
    public static String index="<html>"
            + "<head>"
            + "<title>Bienvenido al servidos</title>"
            + "</head>"
            + "<body>"
            + "<p>Has entrado al servidor</p>"
            + "</body>"
            + "</html>";
                public static String binding ="<html>"
            + "<head>"
            + "<title>Bienvenido al servidos</title>"
            + "</head>"
            + "<body>"
            + "<p>ViewBinding es una forma moderna y segura de acceder a las vistas del layout sin usar findViewById().\n" +
"\n" +
"Android genera una clase automática por cada layout.\n" +
"\n" +
"Ejemplo:\n" +
"\n" +
"Si tu layout se llama:\n" +
"\n" +
"activity_main.xml\n" +
"\n" +
"\n" +
"Android genera esta clase:\n" +
"\n" +
"ActivityMainBinding\n" +
"\n" +
"\n" +
"Esa clase contiene todas las vistas con ID del layout como propiedades.\n" +
"\n" +
"Ejemplo:\n" +
"\n" +
"Si tienes:\n" +
"\n" +
"<TextView\n" +
"    android:id=\"@+id/pantalla\"\n" +
"    ... />\n" +
"\n" +
"\n" +
"Entonces puedes usar:\n" +
"\n" +
"binding.pantalla\n" +
"\n" +
"\n" +
"➡️ Sin hacer findViewById(R.id.pantalla).</p>"
            + "</body>"
            + "</html>";
              public static String no_encuentra = "<html>"
            + "<head>"
            + "<title>NOT FOUND</title>"
            + "</head>"
            + "<body>"
            + "<h1>404 - No Encontrado</h1>"
            + "<p>Fallo al encontrar el recurso</p>"
            + "</body>"
            + "</html>";
}
