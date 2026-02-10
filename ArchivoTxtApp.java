import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class ArchivoTxtApp extends JFrame {
//!Componentes
    private JButton btnArchivo;
    private JPanel jpanel1;
    private JTextArea jtextArea1;
    private JScrollPane jScrollPane1;
//!configuracion de la ventana
    public ArchivoTxtApp(){
        super("Archivos Txt");
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,500);
        this.setLocationRelativeTo(null);
    }
    private void initComponents(){
        jpanel1 = new JPanel(new BorderLayout());
        jtextArea1 = new JTextArea();
        jScrollPane1 = new JScrollPane();
        btnArchivo = new JButton("Selecciona Archivo txt");


    }
public static void main(String[] args) {
    
 }
}

