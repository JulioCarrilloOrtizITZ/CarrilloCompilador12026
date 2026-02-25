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
    public Control(Vista sat){
        this.sat=sat;
    }
    public void encontrarIdentificadores(){
        String input = sat.getjtexareaCodigo().getText();
        //\\b[A-Za-z]\\w*\\b
        String regex="[A-Za-z]\\w*|[1-9]\\d*";
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

    public void abrirArchivo(){
        JFileChooser Architxt= new JFileChooser();
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