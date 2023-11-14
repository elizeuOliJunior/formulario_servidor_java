import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.logging.Logger;

public class PacienteDAO {
    private MongoCollection<Document> pacientesCollection;
    private static final Logger LOGGER = Logger.getLogger(PacienteDAO.class.getName());

    public PacienteDAO() {
        // String de conexão para o seu banco de dados MongoDB
        String connectionString = "mongodb+srv://juliocesarreali:<password>@medicalservicedb.a74fjpv.mongodb.net/";
        // Criação do cliente MongoDB
        MongoClient mongoClient = MongoClients.create(connectionString);

        // Conecte-se ao banco de dados 
        MongoDatabase database = mongoClient.getDatabase("MedicalServiceDB");

        // Obtenha uma referência para a coleção 
        this.pacientesCollection = database.getCollection("Pacientes");

        createCollection(); // Chame este método para garantir que a coleção seja criada ao instanciar a classe
    }

    private void createCollection() {
        // Não é necessário criar explicitamente uma coleção no MongoDB
        // O MongoDB cria automaticamente a coleção quando você insere documentos nela
    }

    public void insertPaciente(Paciente paciente) {
        try {
            Document pacienteDoc = new Document()
                    .append("nome", paciente.getNome())
                    .append("idade", paciente.getIdade())
                    .append("diagnostico", paciente.getDiagnostico());

            pacientesCollection.insertOne(pacienteDoc);

            String logMessage = String.format("Paciente inserido no banco de dados: %s, %s", paciente.getNome(), paciente.getCpf());
            LOGGER.info(logMessage);
        } catch (Exception e) {
            String errorMessage = "Erro ao inserir paciente no banco de dados";
            LOGGER.severe(errorMessage);
            e.printStackTrace();
        }
    }

    public void fetchPacientes() {
        try {
            for (Document document : pacientesCollection.find()) {
                int id = document.getInteger("_id");
                String nome = document.getString("nome");
                int idade = document.getInteger("idade");
                String diagnostico = document.getString("diagnostico");

                System.out.println("ID: " + id + ", Nome: " + nome + ", Idade: " + idade + ", Diagnóstico: " + diagnostico);
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar dados da coleção 'pacientes'.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PacienteDAO pacienteDAO = new PacienteDAO();
        pacienteDAO.insertPaciente(new Paciente("Nome", 30, "Algum diagnóstico"));
        pacienteDAO.fetchPacientes();
    }
}
