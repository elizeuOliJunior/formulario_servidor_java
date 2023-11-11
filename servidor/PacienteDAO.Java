import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PacienteDAO {
    private Connection connection;
    private static final Logger LOGGER = Logger.getLogger(PacienteDAO.class.getName());

    public PacienteDAO(Connection connection) {
        this.connection = connection;
        createTable(); // Chame este método para garantir que a tabela seja criada ao instanciar a classe
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS pacientes (\n"
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "nome TEXT,\n"
                    + "idade INTEGER,\n"
                    + "diagnostico TEXT\n"
                    + ");";
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Erro ao criar a tabela 'pacientes'.");
            e.printStackTrace();
        }
    }

    public void insertPaciente(Paciente paciente) {
        try {
            String insertSQL = "INSERT INTO pacientes (nome, idade, diagnostico) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, paciente.getNome());
                preparedStatement.setInt(2, paciente.getIdade());
                preparedStatement.setString(3, paciente.getDiagnostico());
                preparedStatement.executeUpdate();
                String logMessage = String.format("Paciente inserido no banco de dados: %s, %s", paciente.getNome(), paciente.getCpf());
                LOGGER.info(logMessage);
                
            }
        } catch (SQLException e) {
            
            String errorMessage = "Erro ao inserir paciente no banco de dados";
            LOGGER.severe(errorMessage);
            e.printStackTrace();

        }
    }

    public void fetchPacientes() {
        try {
            String selectSQL = "SELECT * FROM pacientes";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSQL)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nome = resultSet.getString("nome");
                    int idade = resultSet.getInt("idade");
                    String diagnostico = resultSet.getString("diagnostico");

                    System.out.println("ID: " + id + ", Nome: " + nome + ", Idade: " + idade + ", Diagnóstico: " + diagnostico);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar dados da tabela 'pacientes'.");
            e.printStackTrace();
        }
    }
}
