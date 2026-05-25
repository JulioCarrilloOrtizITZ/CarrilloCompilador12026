package compiladoridentificadores.Control; 

import compiladoridentificadores.Vista;
import java.util.ArrayList;

public class Asintaxis {
    private ArrayList<Lexema> lexemas;
    private int posicionActual;
    private int tok;
    private Vista sat; 
    
    public Asintaxis(ArrayList<Lexema> lexemas, Vista sat) {
        this.lexemas = lexemas;
        this.posicionActual = 0;
        this.sat = sat;
    }

    private void imprimirMensaje(String mensaje) {
        String textoAnterior = sat.getjtexareaMensaje().getText();
        sat.getjtexareaMensaje().setText(textoAnterior + mensaje + "\n");
    }

    private int getNextToken() {
        if (posicionActual < lexemas.size()) {
            int tokenObtenido = lexemas.get(posicionActual).getToken();
            posicionActual++;
            return tokenObtenido;
        }
        return -1; // Fin de archivo
    }

    public void programa() {
        tok = getNextToken();
        int status = expresion();
        
        if (status != 0) {
            imprimirMensaje("Error: Analisis detenido por fallos sintacticos.");
            return;
        }

        // 314 = PUNTO (.)
        if (tok != 314) {
            imprimirMensaje("Error: Falta el punto '.' al final del codigo.");
        } else {
            imprimirMensaje("Analisis sintactico completado correctamente.");
        }
    }

    private int expresion() {
        return factor(); 
    }

    private int factor() {
        switch (tok) {
            case 100: // ID 
            case 200: // NUM 
                tok = getNextToken(); 
                return 0; 
                
            case 315: // PARENTESIS_ABIERTO '('
                tok = getNextToken(); 
                
                int status = expresion(); 
                if (status != 0) return status;
                
                if (tok != 316) { // PARENTESIS_CERRADO ')'
                    imprimirMensaje("Error: Se abrio parentesis pero falta cerrarlo con ')'.");
                    return 1; 
                }
                
                tok = getNextToken(); 
                return 0; 
                
            default:
                imprimirMensaje("Error en Factor: Se esperaba ID, NUM o '(' pero llego el token " + tok);
                return 1; 
        }
    }
}