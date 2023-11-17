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
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class MedicalServer{
    private static Connection connection;
    private static MongoCollection<Paciente> pacientesCollection;
    public static void main(String[] args) throws Exception {
        connectToDatabase();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/submit", new MyHandler());
        server.setExecutor(null); // cria um executor padrão
        server.start();
        System.out.println("Servidor iniciado na porta 8000");

        // Conectar ao MongoDB
        MongoDatabase database = connectToDatabase();

        // Obter a coleção de pacientes
        pacientesCollection = database.getCollection("pacientes", Paciente.class);
        }

    }

    static class MyHandler implements HttpHandler {
        //private static final MongoDatabase database = connectToDatabase();
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

            // Convertendo a String JSON para um documento BSON
            Document pacienteDocument = Document.parse(requestBody);


        // Lógica de processamento dos dados do formulário
            Paciente paciente = new Paciente();
            paciente.setNome(pacienteDocument.getString("nome"));
            paciente.setCpf(pacienteDocument.getString("cpf"));
            paciente.setTelefone(pacienteDocument.getString("telefone"));
            paciente.setEmail(pacienteDocument.getString("email"));
            paciente.setPais(pacienteDocument.getString("pais"));
            paciente.setEstado(pacienteDocument.getString("estado"));
            paciente.setCep(pacienteDocument.getString("cep"));
            paciente.setRua(pacienteDocument.getString("rua"));
            paciente.setBairro(pacienteDocument.getString("bairro"));
            paciente.setComplemento(pacienteDocument.getString("complemento"));
            paciente.setEspecialidadeMedica(pacienteDocument.getString("especialidadeMedica"));
            paciente.setUnidadeClinica(pacienteDocument.getString("unidadeClinica"));
            paciente.setConvenio(pacienteDocument.getString("convenio"));
            paciente.setDataConsulta(pacienteDocument.getString("dataConsulta"));
            paciente.setHorarioConsulta(pacienteDocument.getString("horarioConsulta"));

            pacientesCollection.insertOne(paciente);


            MongoCollection<Document> pacientesCollection = database.getCollection("pacientes");

            // Enviando a resposta
            String response = "Sua solicitação foi recebida com sucesso!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private static MongoDatabase connectToDatabase() {
        try {
            // Configurar a URI de conexão do MongoDB
            String uri = "mongodb://localhost:27017"; // Atualize conforme necessário
    
            // Criar um cliente MongoDB
            MongoClientURI clientURI = new MongoClientURI(uri);
            MongoClient mongoClient = new MongoClient(clientURI);
    
            // Obter o banco de dados
            MongoDatabase database = mongoClient.getDatabase("ClinicalDb"); // Nome do banco de dados
    
            System.out.println("Conectado ao banco de dados MongoDB.");
            return database;
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados MongoDB.");
            e.printStackTrace();
            return null;
        }

}
