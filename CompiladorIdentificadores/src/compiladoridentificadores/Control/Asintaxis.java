package compiladoridentificadores.Control; 

import compiladoridentificadores.Vista;
import java.util.ArrayList;

public class Asintaxis {
    private ArrayList<Lexema> lexemas;
    private int posicionActual;
    private int tok;
    private Vista sat; 
    
    // Constructor
    public Asintaxis(ArrayList<Lexema> lexemas, Vista sat) {
        this.lexemas = lexemas;
        this.posicionActual = 0;
        this.sat = sat;
    }

    // Método para imprimir en la pantalla de la interfaz
    private void imprimirMensaje(String mensaje) {
        String textoAnterior = sat.getjtexareaMensaje().getText();
        sat.getjtexareaMensaje().setText(textoAnterior + mensaje + "\n");
    }

    //Errores
    private int reportarError(String mensajeEsperado) {
        int indiceError = posicionActual - 1; 
        
        if (indiceError >= 0 && indiceError < lexemas.size()) {
            Lexema lexemaProblematico = lexemas.get(indiceError);
            
            String error = "Error sintactico cerca de '" + lexemaProblematico.getDato() + "' " +
                           "(Token " + lexemaProblematico.getToken() + ").\n" +
                           "   -> Detalle: " + mensajeEsperado;
            
            imprimirMensaje(error);
        } else {
            imprimirMensaje("Error sintactico inesperado al final del archivo.\n   -> " + mensajeEsperado);
        }
        
        return 1; // Retornamos 1 error
    }

    //siguiente token
    private int getNextToken() {
        if (posicionActual < lexemas.size()) {
            int tokenObtenido = lexemas.get(posicionActual).getToken();
            posicionActual++;
            return tokenObtenido;
        }
        return -1;
    }

    //Gramatica

    // <Programa> -> <Bloque> .
    public void programa() {
        tok = getNextToken(); //Pedimos el primer token
        
        int status = bloque(); //Análisis
        
        if (status != 0) {
            imprimirMensaje("Analisis detenido por fallos sintacticos.");
            return;
        }

        // 314 = PUNTO (.)
        if (tok != 314) {
            reportarError("Falta el punto '.' al final del codigo del programa.");
        } else if(posicionActual<lexemas.size()) {
            reportarError("hay un punto antes del final");
        }
            imprimirMensaje("Correcto: Analisis Sintactico completado sin errores.");
        }
    
    // <Bloque> -> <DIC> <DIV> <DIP> <Proposicion>
    private int bloque() {
        int status = dic();
        if (status != 0) return status;
        
        status = div();
        if (status != 0) return status;
        
        status = dip();
        if (status != 0) return status;
        
        return proposicion(); 
    }

    // <DIC> -> Const <Cicloid> ; | NULL
    private int dic() {
        if (tok == 1) { // 1 = const
            tok = getNextToken();
            int status = cicloid();
            if (status != 0) return status;
            
            if (tok != 312) { // 312 = PUNTO_Y_COMA
                return reportarError("Se esperaba ';' despues de declarar las constantes.");
            }
            tok = getNextToken();
        }
        return 0; // 
    }

    // <Cicloid> -> id = num <CLI1>
    private int cicloid() {
        if (tok == 100) { // 100 = id
            tok = getNextToken();
            if (tok == 301) { // 301 = ASIGNACION (=)
                tok = getNextToken();
                if (tok == 200) { // 200 = num
                    tok = getNextToken();
                    return cli1();
                }
                return reportarError("Se esperaba un numero para la constante.");
            }
            return reportarError("Se esperaba '=' para la asignacion de constante.");
        }
        return reportarError("Se esperaba un identificador (nombre de constante).");
    }

    // <CLI1> -> , <Cicloid> | NULL
    private int cli1() {
        if (tok == 313) { // 313 = COMA
            tok = getNextToken();
            return cicloid();
        }
        return 0;
    }

    // <DIV> -> var <Cicloid2> ; | NULL
    private int div() {
        if (tok == 5) { // 5 = var
            tok = getNextToken();
            int status = cicloid2();
            if (status != 0) return status;
            
            if (tok != 312) { // 312 = PUNTO_Y_COMA
                return reportarError("Se esperaba ';' despues de declarar las variables.");
            }
            tok = getNextToken();
        }
        return 0;
    }

    // <Cicloid2> -> id <CLI2>
    private int cicloid2() {
        if (tok == 100) { // 100 = id
            tok = getNextToken();
            return cli2();
        }
        return reportarError("Se esperaba un identificador en la declaracion de variables.");
    }

    // <CLI2> -> , <Cicloid2> | NULL
    private int cli2() {
        if (tok == 313) { // 313 = COMA
            tok = getNextToken();
            return cicloid2();
        }
        return 0;
    }

    // <DIP> -> Proced id ; <Bloque> ; <CicloProced> | NULL
    private int dip() {
        if (tok == 11) { // 11 = procedure
            tok = getNextToken();
            if (tok == 100) { // 100 = id
                tok = getNextToken();
                if (tok == 312) { // 312 = PUNTO_Y_COMA
                    tok = getNextToken();
                    int status = bloque();
                    if (status != 0) return status;
                    
                    if (tok == 312) { // 312 = PUNTO_Y_COMA
                        tok = getNextToken();
                        return dip(); 
                    }
                    return reportarError("Falta ';' al final del bloque del procedure.");
                }
                return reportarError("Falta ';' despues del nombre del procedure.");
            }
            return reportarError("Se esperaba un nombre (identificador) para el procedure.");
        }
        return 0;
    }

    // <Proposicion> -> <MP>
    private int proposicion() {
        int stat;
        switch (tok) {
            case 2: // begin <CicloP> end
                tok = getNextToken();
                stat = ciclop();
                if (stat != 0) return stat;
                if (tok == 12) { // 12 = end
                    tok = getNextToken();
                    return 0;
                }
                return reportarError("Bloque 'begin' sin cerrar, se esperaba 'end'.");

            case 100: // id = <Expresion>
                tok = getNextToken();
                if (tok == 301) { // 301 = IGUAL (=)
                    tok = getNextToken();
                    return expresion();
                }
                return reportarError("Se esperaba '=' para realizar una asignacion.");

            case 13: // write id
            case 14: // read id
            case 15: // call id
                tok = getNextToken();
                if (tok == 100) { // 100 = id
                    tok = getNextToken();
                    return 0;
                }
                return reportarError("El comando necesita ir seguido de un identificador.");

            case 6: // if <condicion> then <Proposicion>
                tok = getNextToken();
                stat = condicion();
                if (stat != 0) return stat;
                if (tok == 7) { // 7 = then
                    tok = getNextToken();
                    return proposicion();
                }
                return reportarError("Falta 'then' en la estructura if.");

            case 4: // while <condicion> do <Proposicion>
                tok = getNextToken();
                stat = condicion();
                if (stat != 0) return stat;
                if (tok == 8) { // 8 = do
                    tok = getNextToken();
                    return proposicion();
                }
                return reportarError("Falta 'do' en la estructura while.");

            case 3: // for id = <Expresion> to/downto <Expresion> do <Proposicion>
                tok = getNextToken();
                if (tok == 100) { // 100 = id
                    tok = getNextToken();
                    if (tok == 301) { // 301 = IGUAL (=)
                        tok = getNextToken();
                        stat = expresion();
                        if (stat != 0) return stat;
                        
                        if (tok == 9 || tok == 10) { // 9 = to, 10 = downto
                            tok = getNextToken();
                            stat = expresion();
                            if (stat != 0) return stat;
                            
                            if (tok == 8) { // 8 = do
                                tok = getNextToken();
                                return proposicion();
                            }
                            return reportarError("Falta 'do' al final de la declaracion del for.");
                        }
                        return reportarError("Falta 'to' o 'downto' en el ciclo for.");
                    }
                    return reportarError("Se esperaba '=' despues de la variable del ciclo for.");
                }
                return reportarError("El bucle for debe empezar con un identificador.");

            default:
                return reportarError("Proposicion invalida. Se encontro un token no esperado.");
        }
    }

    // <CicloP> -> <Proposicion> <PR>
    private int ciclop() {
        int status = proposicion();
        if (status != 0) return status;
        return pr();
    }

    // <PR> -> ; <CicloP> | NULL
    private int pr() {
        if (tok == 312) { // 312 = PUNTO_Y_COMA
            tok = getNextToken();
            return ciclop();
        }
        return 0;
    }

    // <Condicion> -> <Expresion> <MultE> <Expresion>
    private int condicion() {
        int stat = expresion();
        if (stat != 0) return stat;
        
        // 306 a 311 (==, !=, <, >, <=, >=)
        if (tok >= 306 && tok <= 311) { 
            tok = getNextToken();
            return expresion();
        }
        return reportarError("Se esperaba un operador relacional (==, !=, <, >, <=, >=).");
    }

    // <Expresion> -> <CicloT>
    private int expresion() {
        int stat = termino();
        if (stat != 0) return stat;
        return clt();
    }

    // <CLT> -> <DIM> <CicloT> | NULL
    private int clt() {
        if (tok == 302 || tok == 303) { // 302 = +, 303 = -
            tok = getNextToken();
            int stat = termino();
            if (stat != 0) return stat;
            return clt();
        }
        return 0;
    }

    // <Termino> -> <CicloF>
    private int termino() {
        int stat = factor();
        if (stat != 0) return stat;
        return clf();
    }

    // <CLF> -> <DID> <CicloF> | NULL
    private int clf() {
        if (tok == 304 || tok == 305) { // 304 = *, 305 = /
            tok = getNextToken();
            int stat = factor();
            if (stat != 0) return stat;
            return clf();
        }
        return 0;
    }

    // <Factor> -> ( <Expresion> ) | id | num
    private int factor() {
        if (tok == 100 || tok == 200) { // 100 = id, 200 = num
            tok = getNextToken(); 
            return 0; 
        } else if (tok == 315) { // 315 = '('
            tok = getNextToken(); 
            int status = expresion(); 
            if (status != 0) return status;
            
            if (tok == 316) { // 316 = ')'
                tok = getNextToken();
                return 0; 
            }
            return reportarError("Falta cerrar parentesis ')'."); 
        }
        return reportarError("En Factor: Se esperaba un numero, identificador o abrir parentesis '('."); 
    }
}