/*
Regex oficial basada en RFC 5322 para validación completa de email:

(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|
"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|
\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")
@
(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+
[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|
\[(?:(?:25[0-5]\.|2[0-4][0-9]\.|[01]?[0-9][0-9]?\.){3}
(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|
[a-zA-Z0-9-]*[a-zA-Z0-9]:
(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|
\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])

Basada en el estándar RFC 5322 (IETF)
*/

import java.util.Scanner;
public class RegexCorreo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String regex= "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        System.out.print("introduce tu correo");
        String entrada = scanner.nextLine();
        if(entrada.matches(regex)){
            System.out.print(entrada+" correo valido");
        }else{
            System.out.print(entrada+ " correo no valido");
        }
    }
}
