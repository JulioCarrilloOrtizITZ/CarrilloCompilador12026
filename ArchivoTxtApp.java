import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.omg.CORBA.Principal;

import java.awt.*;
import java.awt.event.ActionEvent;
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
//!configracion area de texto
        jtextArea1.setColumns(20);
        jtextArea1.setRows(5);
        jpanel1.add(jScrollPane1, BorderLayout.CENTER);
//!evento del boton
        btnArchivo.addActionListener(e -> btnArchivoActionPerformed(e));
        


        jtextArea1.setColumns(20);
        jtextArea1.setRows(5);
        jpanel1.add(jScrollPane1, BorderLayout.CENTER);

        btnArchivo.addActionListener(e -> btnArchivoActionPerfomed(e));

        this.setLayout(new BorderLayout(10, 10));
        this.add(jpanel1, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnArchivo);
        this.add(panelBoton, BorderLayout.SOUTH);

    }
private Object btnArchivoActionPerfomed(ActionEvent e) {
        JFileChooser archiTxt = new JFileChooser();
        archiTxt.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de texto", "txt");
        archiTxt.setFileFilter(filtro);
       
        int resultado = archiTxt.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = archiTxt.getSelectedFile();
            if (archivo == null || archivo.getName().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error al abrir archivo");
            } else {
                try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                    StringBuilder sb = new StringBuilder();
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        sb.append(linea).append("\n");
                    }
                    jtextArea1.setText(sb.toString());
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(this, "Error al leer el archivo");
                }
            }
        }

        throw new UnsupportedOperationException("Unimplemented method 'btnArchivoActionPerfomed'");
    }
public static void main(String[] args) {
     try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        EventQueue.invokeLater(() -> new ArchivoTxtApp().setVisible(true));

 }
}

