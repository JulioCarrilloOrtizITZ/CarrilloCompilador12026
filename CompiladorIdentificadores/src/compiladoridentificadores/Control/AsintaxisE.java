/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladoridentificadores.Control;

import java.util.ArrayList;

/**
 *
 * @author julyc
 */
public class AsintaxisE {
    ArrayList<Lexema>lexemas;
    public AsintaxisE(ArrayList<Lexema>lexemas){
        this.lexemas=lexemas;
        
        
    }
    public void programa(){
        System.out.println("ya estamos en sintaxis");
        recorreLex();
        
    }   
    private void recorreLex(){
        for(Lexema l:lexemas){
            System.out.println("");
        }
    }
}
