import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MedicalServer {
    private static Connection connection;
    public static void main(String[] args) throws Exception {
        connectToDatabase();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/submit", new MyHandler());
        server.setExecutor(null); // cria um executor padrão
        server.start();
        System.out.println("Servidor iniciado na porta 8000");
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            // Configurando os cabeçalhos CORS
            Headers headers = exchange.getResponseHeaders();
            headers.set("Access-Control-Allow-Origin", "*");
            headers.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            headers.set("Access-Control-Allow-Headers", "Content-Type, Authorization");

            // Processando a solicitação
            // Aqui você pode adicionar lógica para processar os dados do formulário

            // Exemplo de leitura do corpo da solicitação
            String requestBody = new String(exchange.getRequestBody().readAllBytes());

            // Exemplo de log dos dados recebidos
            System.out.println("Dados recebidos do formulário: " + requestBody);

            // Enviando a resposta
            String response = "Sua solicitação foi recebida com sucesso!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private static void connectToDatabase() {
        try {
            // Carregando o driver JDBC do SQLite
            Class.forName("org.sqlite.JDBC");

            // Criando uma conexão com o banco de dados SQLite 
            connection = DriverManager.getConnection("jdbc:sqlite:medical_data.db");

            System.out.println("Conectado ao banco de dados SQLite.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados SQLite.");
            e.printStackTrace();
        }
    }

}
