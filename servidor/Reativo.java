import java.io.*;
import java.net.*;

public class Reativo {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            while (true) {
                try {
                    Socket clienteSocket = serverSocket.accept();
                    HandleConexao.handleReativo(clienteSocket);
                } catch (IOException error) {
                    System.err.println(error.getMessage());
                }
            }
        } catch (IOException error) {
            System.err.println(error.getMessage());
        }
    }
    
    public void handle(HttpExchange exchange) throws IOException {
        Headers headers = exchange.getResponseHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        headers.set("Access-Control-Allow-Headers", "Content-Type, Authorization");

        try {
            // Ler os dados do formulário do corpo da solicitação
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            System.out.println("Dados recebidos do formulário: " + requestBody);

            // Converter JSON para objeto Paciente
            Paciente paciente = Paciente.fromJson(requestBody);

            // Processar dados do formulário
            inserirPacienteNoBancoDeDados(paciente);

            // Responder ao cliente
            enviarRespostaAoCliente(exchange, "Sua solicitação foi recebida com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            enviarRespostaAoCliente(exchange, "Erro ao processar a solicitação");
        }
    }
}
