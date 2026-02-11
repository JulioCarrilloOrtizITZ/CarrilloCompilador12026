import java.util.Scanner;

public class RegHexa {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String regex = "[0-9A-Fa-f]+";
    System.out.print("Introduce una cadena hexadecimal :");
        String entrada = scanner.nextLine();
    if(entrada.matches(regex)){
        System.out.print(entrada+ "Coincide con la exprecion");
    }else{
        System.out.print("no coincide con la cadena "+regex);
    }
    scanner.close();
    }
}
