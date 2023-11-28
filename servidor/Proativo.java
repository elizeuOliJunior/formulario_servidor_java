import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Proativo {

    public static void main(String[] args) {
        try {
            Socket servidor = new Socket("localhost", 8000);
            BufferedReader receptor = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
            PrintWriter transmissor = new PrintWriter(servidor.getOutputStream());
            Scanner sc = new Scanner(System.in);

            while (true) {
                String texto = sc.nextLine();
                if (texto != null) {
                    HandleConexao.handleProativo(texto, receptor, transmissor);
                } else {
                    System.out.println("DIGITE ALGUMA COISA");
                }
            }

        } catch (IOException error) {
            System.err.println(error.getMessage());
        }
    }
}
