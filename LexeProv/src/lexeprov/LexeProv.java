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
        String EjemploLex ="var x @ y = 42; if x >= 10 then write(x) for i down to 5 do begin end";
        String regex="[([a-zA-Z]\\w*)([1-9]\\d*)0|(==|!=|<=|>=)|([-+*/=<>;,.()@])\"]";
        Pattern pattern=Pattern.compile(regex);
        Pattern matcher = pattern.compile(EjemploLex);
           ArrayList<String> lex= new ArrayList<>();
           while (matcher.find()) {
            lex.add(matcher.group(0));
        }
          System.out.println("Tokens encontrados (" + lex.size() + "):");
        System.out.println(lex);
       
        // Bonus: también lo mostramos bonito
        System.out.println("\nLista ordenada:");
        for (String t : lex) {
            System.out.println(t);
        }
    }
}

