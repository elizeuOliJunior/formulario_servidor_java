import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MedicalServer {
    private static final String JDBC_URL = "jdbc:sqlite:medicalservice.db";
    private static final String INSERT_PACIENTE_SQL = "INSERT INTO pacientes (nome, cpf, telefone, /* ... outros campos ... */) VALUES (?, ?, ?)";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/submit", new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor iniciado na porta 8000");
    }

    static class MyHandler implements HttpHandler {
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

        private void inserirPacienteNoBancoDeDados(Paciente paciente) throws SQLException {
            try (Connection connection = DriverManager.getConnection(JDBC_URL);
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PACIENTE_SQL)) {
                preparedStatement.setString(1, paciente.getNome());
                preparedStatement.setString(2, paciente.getCpf());
                preparedStatement.setString(3, paciente.getTelefone());
                // Defina outros parâmetros conforme necessário

                preparedStatement.executeUpdate();
            }
        }

        private void enviarRespostaAoCliente(HttpExchange exchange, String mensagem) throws IOException {
            exchange.sendResponseHeaders(200, mensagem.length());
            OutputStream os = exchange.getResponseBody();
            os.write(mensagem.getBytes());
            os.close();
        }
    }
}
