import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect{
    public static void main(String[] args){
        String jdbcUrl = "jdbc:sqlite:/C:\\Users\\Usuario\\Documents\\SQLite\\sqlite-tools-win-x64-3440200";
        try{
            Connection connection = DriverManager.getConnection(jdbcUrl);
            String sql = "SELECT * FROM users";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while(result.next()){
                String name = result.getString("nome");
                String email = result.getString("email");

                System.out.println(name +" | " + email);
            }

        }catch(SQLException e){
            System.out.println("Erro ao conectar");
            e.printStackTrace();
        }
    }
}