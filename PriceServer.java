import java.net.*;
import java.io.*;
import java.sql.*;

public class PriceServer {
    public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");  // connect to its local database
        Connection connection = DriverManager.getConnection("jdbc:sqlite:store.db");
        PreparedStatement stmt = connection.prepareStatement("SELECT price FROM product WHERE productID = ?");

        System.out.println("Waiting for connection at port 7777.....");
        ServerSocket serverSocket = new ServerSocket(7777);
        Socket clientSocket = serverSocket.accept();

        System.out.println("Connection successful");
        System.out.println("Waiting for input.....");

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        while (true) {
            String inputLine = in.readLine();
            if (inputLine == null || inputLine.toLowerCase().equals("quit")) break;

            System.out.println("Client asks for product ID: " + inputLine);
            int id = Integer.parseInt(inputLine);
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            if (!res.next())
                out.println(-1);         // No product with that id
            else
                out.println(res.getDouble(1));
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
} 
