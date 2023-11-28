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
}
