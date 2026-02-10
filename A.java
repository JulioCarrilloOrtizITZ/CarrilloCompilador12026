import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class A extends JFrame {
    private JButton btnArchivo;
    private JPanel jpanel1;
    private JTextArea jtextArea1;
    private JScrollPane jScrollPane1;

    public A() {
        super("Archivos Txt");
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        jpanel1 = new JPanel(new BorderLayout());
        jtextArea1 = new JTextArea();
        
        // CORRECCIÓN 1: Pasar el area de texto al scroll
        jScrollPane1 = new JScrollPane(jtextArea1); 
        
        btnArchivo = new JButton("Seleccionar Archivo .txt");

        // Configuración visual
        jtextArea1.setFont(new Font("Monospaced", Font.PLAIN, 13));
        jpanel1.add(jScrollPane1, BorderLayout.CENTER);

        this.setLayout(new BorderLayout(10, 10));
        this.add(jpanel1, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnArchivo);
        this.add(panelBoton, BorderLayout.SOUTH);

        // Evento
        btnArchivo.addActionListener(e -> btnArchivoActionPerformed(e));
    }

    private void btnArchivoActionPerformed(ActionEvent e) {
        JFileChooser archiTxt = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Documentos de Texto", "txt");
        archiTxt.setFileFilter(filtro);

        int resultado = archiTxt.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = archiTxt.getSelectedFile();
            
            // CORRECCIÓN 2: Uso de try-with-resources para leer eficientemente
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                jtextArea1.read(br, null); // Este método carga el archivo directamente al TextArea
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "No se pudo leer el archivo");
            }
        }
        // CORRECCIÓN 3: Se eliminó el "throw new UnsupportedOperationException"
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) { }

        EventQueue.invokeLater(() -> new A().setVisible(true));
    }
}