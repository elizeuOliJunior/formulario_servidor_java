import java.sql.*;

public class PacienteDAO {
    private Connection connection;

    public PacienteDAO() {
        try {
            // Conectar ao banco de dados SQLite (o arquivo será criado se não existir)
            connection = DriverManager.getConnection("jdbc:sqlite:minhabasededados.db");

            // Criar a tabela se não existir
            String createTableSQL = "CREATE TABLE IF NOT EXISTS pacientes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT," +
                    "cpf TEXT," +
                    "telefone TEXT" +
                    // ... outros campos ...
                    ")";
            connection.createStatement().execute(createTableSQL);

            System.out.println("Conexão com o banco de dados SQLite estabelecida!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPaciente(Paciente paciente) {
        try {
            String insertSQL = "INSERT INTO pacientes (nome, cpf, telefone) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, paciente.getNome());
                preparedStatement.setString(2, paciente.getCpf());
                preparedStatement.setString(3, paciente.getTelefone());
                // ... outros campos ...

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            paciente.setId(generatedKeys.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
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
                    String cpf = resultSet.getString("cpf");
                    String telefone = resultSet.getString("telefone");
                    // ... outros campos ...

                    System.out.println("ID: " + id + ", Nome: " + nome + ", CPF: " + cpf + ", Telefone: " + telefone);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PacienteDAO pacienteDAO = new PacienteDAO();
        pacienteDAO.insertPaciente(new Paciente("Nome", "123456789", "987654321"));
        pacienteDAO.fetchPacientes();
    }
}
