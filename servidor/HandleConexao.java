import java.io.*;
import java.net.*;

public class HandleConexao {

    public static void handleReativo(Socket clienteSocket) {
        try {
            BufferedReader receptor = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            PrintWriter transmissor = new PrintWriter(clienteSocket.getOutputStream(), true);

            String texto;
            while ((texto = receptor.readLine()) != null) {
                System.out.println("Dados recebidos do formulário: " + texto);

                // Lógica reativa: Manipular os dados recebidos do formulário
                String respostaReativa = manipularDadosRecebidos(texto);
                transmissor.println(respostaReativa);

                // Exemplo: Simular interação com o banco de dados
                String respostaBanco = simularIntegracaoBanco(texto);
                transmissor.println(respostaBanco);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                clienteSocket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void handleProativo(String texto, BufferedReader receptor, PrintWriter transmissor) {
        try {
            // Lógica proativa: Interagir proativamente com o servidor
            if (texto.equalsIgnoreCase("SOMA") || texto.equalsIgnoreCase("SUBTRACAO")) {
                transmissor.println(texto);
                transmissor.flush();
                int numeroRecebido = Integer.parseInt(receptor.readLine());
                System.out.println("Número recebido do servidor: " + numeroRecebido);
            } else if (texto.equalsIgnoreCase("FIM")) {
                System.out.println("O programa está sendo encerrado...");
                System.exit(0);
            } else {
                System.out.println("INSIRA UM VALOR VÁLIDO");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static String manipularDadosRecebidos(String dadosFormulario) {
        // Aqui você pode realizar operações com os dados recebidos do formulário
        // Por exemplo, processar os dados, validar, etc.
        return "Dados do formulário manipulados com sucesso!";
    }

    private static String simularIntegracaoBanco(String dadosFormulario) {
        // Aqui você pode simular a interação com um banco de dados
        // Por exemplo, salvar os dados do formulário no banco
        // ou consultar informações no banco com base nos dados do formulário
        return "Integração com o banco de dados simulada com sucesso!";
    }
}
