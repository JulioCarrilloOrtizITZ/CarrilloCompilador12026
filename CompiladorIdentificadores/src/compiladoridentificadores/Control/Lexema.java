/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladoridentificadores.Control;

public class Lexema {
    private String dato;  
    private String tipo;
    private int token; 

    public static final String[] palabrasReservadas = {
        "const", "begin", "for", "while", "var", 
        "if", "then", "do", "to", "downto", 
        "procedure", "end", "write", "read", "call"
    };

    public Lexema(String dato, String tipo, int token) {
        this.dato = dato;
        this.tipo = tipo;
        this.token = token;
    }

    // Devuelve 100 si es ID normal, o 1-10 si es palabra reservada
    public static int esReservada(String dato) {
        for (int i = 0; i < palabrasReservadas.length; i++) {
            if (dato.equals(palabrasReservadas[i])) {
                return i + 1; // const=1, begin=2, for=3...
            }
        }
        return 100; // Es ID normal
    }

    public String getDato() { return dato; }
    public void setDato(String dato) { this.dato = dato; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public int getToken() { return token; }
    public void setToken(int token) { this.token = token; }

    @Override
    public String toString() {
        return "[" + dato + "\t" + tipo + "\t" + token + "]";
    }
}