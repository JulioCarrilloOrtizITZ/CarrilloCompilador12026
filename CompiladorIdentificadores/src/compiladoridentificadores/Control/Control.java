/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladoridentificadores.Control;

import compiladoridentificadores.Vista;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author julyc
 */
public class Control {
    Vista sat;
    public static final int ID = 15;
    public static final int NUM = 16;
    public static final int ASIGNACION = 17;
    public static final int MAS = 18;
    public static final int MENOS = 19;
    public static final int MULTIPLICACION = 20;
    public static final int DIVISION = 21;
    public static final int IGUAL = 22;
    public static final int DIFERENTE = 23;
    public static final int MENOR_QUE = 24;
    public static final int MAYOR_QUE = 25;
    public static final int MENOR_IGUAL = 26;
    public static final int MAYOR_IGUAL = 27;
    public static final int PUNTO_Y_COMA = 28;
    public static final int COMA = 29;
    public static final int PUNTO = 30;
    public static final int PARENTESIS_ABIERTO = 31;
    public static final int PARENTESIS_CERRADO = 32;
    public static final String[] palabrasReservadas = {"conts", "beging", "for", "while"};
    public Control(Vista sat){
        this.sat=sat;
    }
    public void encontrarIdentificadores(){
        String input = sat.getjtexareaCodigo().getText();
        //\\b[A-Za-z]\\w*\\b
        String regex="[A-Za-z]\\w*|"+
        "0|[1-9]\\d*";
        Pattern pattern = Pattern.compile(regex);
        // Elimina comentarios de una línea // y multilínea /* */
        // String codigoLimpio = input.replaceAll("//.*|/\\*.*\\*/", "");
        // Matcher matcher = pattern.matcher(codigoLimpio);
        Matcher matcher = pattern.matcher(input);
        StringBuilder resultado = new StringBuilder();
        int contador =0;
        resultado.append("Identificadores\n");
      
        while (matcher.find()){
            contador++;
            resultado.append(contador).append(".").append(matcher.group()).append("\n");
        }
        resultado.append("\n-------------\n");
        resultado.append("Total encontrados:").append(contador);
        
        sat.getjtexareaMensaje().setText(resultado.toString());
    }
        public boolean encontrarLexico() {
        String input = sat.getjtexareaCodigo().getText();
        String regex = "([a-zA-Z_]\\w*)|"+
        "([1-9][0-9]*|0)|"+
        "(==|!=|<=|>=)|"+
        "([-+*/=<>;,.()])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        ArrayList<String> noIdentificados = new ArrayList<>();
        int ultimoFin = 0;
        StringBuilder resultado = new StringBuilder();
        resultado.append("Tokens:\n");
        while (matcher.find()) {
        if (matcher.start() > ultimoFin) {
            String hueco = input.substring(ultimoFin, matcher.start());
            for (char c : hueco.toCharArray()) {
                if (c != ' ' && c != '\n' && c != '\t' && c != '\r') {
                    noIdentificados.add(String.valueOf(c));
                    
                }
            }
        }
        ultimoFin = matcher.end();

        String lexema = matcher.group();
            int token = 0;
            String tipo = "";

            // Grupo 1: Identificador o Palabra Reservada
            if (matcher.group(1) != null) { 
                boolean esPR = false;
                for (String pr : palabrasReservadas) {
                    if (lexema.equals(pr)) {
                        esPR = true;
                        break;
                    }
                }
                tipo = esPR ? "PR" : "ID";
                token = ID; 
                resultado.append("[").append(lexema).append("\t").append(tipo).append("\t").append(token).append("]\n");
                continue; 
            } 

            // Grupo 2: Números
            if (matcher.group(2) != null) { 
                tipo = "NUM";
                token = NUM; 
                resultado.append("[").append(lexema).append("\t").append(tipo).append("\t").append(token).append("]\n");
                continue; 
            }

            // Grupo 3: Relacionales compuestos
            if (matcher.group(3) != null) {
                if (lexema.equals("==")) { tipo = "IGUAL"; token = IGUAL; }
                if (lexema.equals("!=")) { tipo = "DIFERENTE"; token = DIFERENTE; }
                if (lexema.equals("<=")) { tipo = "MENOR_IGUAL"; token = MENOR_IGUAL; }
                if (lexema.equals(">=")) { tipo = "MAYOR_IGUAL"; token = MAYOR_IGUAL; }
                
                resultado.append("[").append(lexema).append("\t").append(tipo).append("\t").append(token).append("]\n");
                continue;
            }

            // Grupo 4: Operadores simples y delimitadores
            if (matcher.group(4) != null) {
                if (lexema.equals("<")) { tipo = "MENOR_QUE"; token = MENOR_QUE; }
                if (lexema.equals(">")) { tipo = "MAYOR_QUE"; token = MAYOR_QUE; }
                if (lexema.equals("=")) { tipo = "ASIGNACION"; token = ASIGNACION; }
                if (lexema.equals("+")) { tipo = "MAS"; token = MAS; }
                if (lexema.equals("-")) { tipo = "MENOS"; token = MENOS; }
                if (lexema.equals("*")) { tipo = "MULTIPLICACION"; token = MULTIPLICACION; }
                if (lexema.equals("/")) { tipo = "DIVISION"; token = DIVISION; }
                if (lexema.equals(";")) { tipo = "PUNTO_Y_COMA"; token = PUNTO_Y_COMA; }
                if (lexema.equals(",")) { tipo = "COMA"; token = COMA; }
                if (lexema.equals(".")) { tipo = "PUNTO"; token = PUNTO; }
                if (lexema.equals("(")) { tipo = "PARENTESIS_ABIERTO"; token = PARENTESIS_ABIERTO; }
                if (lexema.equals(")")) { tipo = "PARENTESIS_CERRADO"; token = PARENTESIS_CERRADO; }
                
                resultado.append("[").append(lexema).append("\t").append(tipo).append("\t").append(token).append("]\n");
                continue;
            }
    }
        if (ultimoFin < input.length()) {
        String hueco = input.substring(ultimoFin);
        for (char c : hueco.toCharArray()) {
            if (c != ' ' && c != '\n' && c != '\t' && c != '\r') {
                noIdentificados.add(String.valueOf(c));
            }
        }
    }
    resultado.append("No identificados:\n");
    
    if (noIdentificados.isEmpty()) {
        resultado.append("No se encontraron errores.\n");
    } else {
        int contErrores = 0;
        for (String error : noIdentificados) {
            contErrores++;
            resultado.append(contErrores).append(".").append(error).append("\n");
        }
        resultado.append("\n-------------\n");
        resultado.append("Total no identificados: ").append(contErrores);
    }
    sat.getjtexareaMensaje().setText(resultado.toString());
    return noIdentificados.isEmpty();
    }
          
    public void abrirArchivo(){
        JFileChooser Architxt= new JFileChooser();
        File archivoPreseleccionado = new File("test/test_lengAutom.zfc");
        if (archivoPreseleccionado.exists()) {
        Architxt.setCurrentDirectory(archivoPreseleccionado.getParentFile());
        Architxt.setSelectedFile(archivoPreseleccionado);
    }   else {
        Architxt.setCurrentDirectory(new File("test")); 
    }
        Architxt.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filtro =new FileNameExtensionFilter("Archivos de texto", "txt");
        int tex = Architxt.showOpenDialog(sat);
        if(tex==JFileChooser.APPROVE_OPTION){
            File archivo = Architxt.getSelectedFile();
            if(archivo==null||archivo.getName().equals("")){
                JOptionPane.showInputDialog(sat,"Error al abrir el archivo");
            }else{
                try{
                    BufferedReader br = new BufferedReader(new FileReader(archivo));
                    StringBuilder sb = new StringBuilder();
                    String linea;
                    while((linea=br.readLine())!=null){
                        sb.append(linea).append("\n"); 
                    }
                    sat.getjtexareaCodigo().setText(sb.toString());
                }catch (IOException e){
                             JOptionPane.showMessageDialog(sat,"error al abrir el archivo");
                }
            }
        }
}
}
