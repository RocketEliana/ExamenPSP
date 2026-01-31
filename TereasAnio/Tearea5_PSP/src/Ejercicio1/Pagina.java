/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio1;

import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author User
 */public class Pagina {

 // 1. Constante SÓLO para el Content-Type (esto sí es estático)
    public static final String CONTENT_TYPE_HTML = "Content-Type: text/html; charset=UTF-8";

    // 2. Método estático para la Fecha (¡tu código es perfecto!)
    public static String fechaHora() {
        ZonedDateTime ahora = ZonedDateTime.now(ZoneOffset.UTC);
        return DateTimeFormatter.RFC_1123_DATE_TIME.format(ahora);
    }
    
    // 3. ¡NUEVO! Método estático para la Longitud
    public static int getLengthEnBytes(String html) {
        return html.getBytes(StandardCharsets.UTF_8).length;
    }
  //contenido index
  public static final String html_index = "<html>"
          + "<head><title>index</title></head>"
          + "<body>"
          + "<h1>¡Enhorabuena!</h1>"
          + "<p>Tu servidor HTTP mínimo funciona correctamente</p>"
          + "</body>"
          + "</html>";
  //contenido quijote
  public static final String html_quijote = "<html>"
          + "<head><title>quijote</title></head>"
          + "<body>"
          + "<h1>Así comienza el Quijote</h1>"
          + "<p>En un lugar de la Mancha, de cuyo nombre no quiero "
          + "acordarme, no ha mucho tiempo que vivía un hidalgo de los "
          + "de lanza en astillero, adarga antigua, rocín flaco y galgo "
          + "corredor. Una olla de algo más vaca que carnero, salpicón "
          + "las más noches, duelos y quebrantos (huevos con tocino) los "
          + "sábados, lentejas los viernes, algún palomino de añadidura "
          + "los domingos, consumían las tres partes de su hacienda. El "
          + "resto della concluían sayo de velarte (traje de paño fino), "
          + "calzas de velludo (terciopelo) para las fiestas con sus "
          + "pantuflos de lo mismo, y los días de entresemana se honraba "
          + "con su vellorí (pardo de paño) de lo más fino. Tenía en su "
          + "casa una ama que pasaba de los cuarenta, y una sobrina que "
          + "no llegaba a los veinte, y un mozo de campo y plaza, que "
          + "así ensillaba el rocín como tomaba la podadera...</p>"
          + "</body>"
          + "</html>";
  //contenido noEncontrado
  public static final String html_noEncontrado = "<html>"
          + "<head><title>noEncontrado</title></head>"
          + "<body>"
          + "<h1>¡ERROR! Página no encontrada</h1>"
          + "<p>La página que solicitaste no existe en nuestro "
          + "servidor</p>"
          + "</body>"
          + "</html>";

   /* private static LocalDate  fechaHora() {
    LocalDate actual=LocalDate.now();
    return actual;Fecha y hora: no es una cabecera HTTP válida.
Debe ser exactamente:

Date: <fecha en formato RFC-1123>La función fechaHora() devuelve LocalDate, pero HTTP requiere fecha + hora + zona en un formato concreto.ChatGPT Plus

El formato RFC-1123 es el formato oficial de fecha y hora que deben usar los servidores HTTP según el estándar HTTP/1.1.*/
  
    
    
}