import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static final Object lock = new Object();

    private String hostname;
    private String username;
    private String password;
    private String databaseName;

    private DatabaseConnection() {
        // Load database connection information from configuration file
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/database_config.json"));
            JSONObject jsonObject = (JSONObject) obj;
            this.hostname = (String) jsonObject.get("hostname");
            this.username = (String) jsonObject.get("username");
            this.password = (String) jsonObject.get("password");
            this.databaseName = (String) jsonObject.get("database_name");

            // Establish database connection here
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public void executeQuery(String query) {
        // Execute SQL query and retrieve results
    }

    public JSONObject getConnectionInfo() {
        JSONObject connectionInfo = new JSONObject();
        connectionInfo.put("hostname", this.hostname);
        connectionInfo.put("username", this.username);
        connectionInfo.put("password", this.password);
        connectionInfo.put("database_name", this.databaseName);
        return connectionInfo;
    }

    public static void main(String[] args) {
        // Test program
        // Get the singleton instance
        DatabaseConnection dbConnection1 = DatabaseConnection.getInstance();
        DatabaseConnection dbConnection2 = DatabaseConnection.getInstance();

        // Verify that both instances are the same
        System.out.println("Are both instances the same? " + (dbConnection1 == dbConnection2));

        // Test accessing connection information
        System.out.println("Connection information: " + dbConnection1.getConnectionInfo());
    }
}
