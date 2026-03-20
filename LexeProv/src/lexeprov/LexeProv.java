/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lexeprov;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author julyc
 */
public class LexeProv {
    public static void main(String[] args) {
        String EjemploLex ="const x=100,y=10.21;var a___2,b_c_2_t,059\n" +
                           "a__2<<===!==xy-yx==>=>>100000000066\n" +
                           "if-while+for*then/do%to|downto\n" +
                           "(x_nueva(y_vieja))(b_c_2_t.\n" +
                           "fin_2";
   String regex = "[a-zA-Z_]\\w*|[1-9][0-9]*|0|==|!=|<=|>=|[-+*/=<>;,.()]";
   Pattern pattern=Pattern.compile(regex);
        Matcher matcher = pattern.matcher(EjemploLex);
           ArrayList<String> lex= new ArrayList<>();
           ArrayList<String> noIdentificados = new ArrayList<>();
           int ultimoFin = 0;
           while (matcher.find()) {
           if (matcher.start() > ultimoFin) {
                String hueco = EjemploLex.substring(ultimoFin, matcher.start());
                for (char c : hueco.toCharArray()) {
                    if (c != ' ' && c != '\n' && c != '\t') {
                        noIdentificados.add(String.valueOf(c));
                    }
                }
            }
            lex.add(matcher.group(0));
            ultimoFin = matcher.end(); 
        }
        
        System.out.println("Encontrados (" + lex.size() + "):");
        System.out.println(lex);
        System.out.println("\nLista ordenada:");
        for (String t : lex) {
            System.out.println(t);
        }
        System.out.println("\nNo identificados / Errores Lexicos (" + noIdentificados.size() + "):");
        for (String error : noIdentificados) {
            System.out.println("-> " + error);
        }
    }
}

