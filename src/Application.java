import java.sql.*;

public class Application {

    private static Application instance;   // Singleton pattern

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }
    // Main components of this application

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    private DataAdapter dataAdapter;

    private User currentUser = null;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Create the Product View and Controller here!

    private ProductView productView = new ProductView();

    private CheckoutScreen checkoutScreen = new CheckoutScreen();

    private MainScreen mainScreen = new MainScreen();

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public ProductView getProductView() {
        return productView;
    }

    public CheckoutScreen getCheckoutScreen() {
        return checkoutScreen;
    }

    public LoginScreen loginScreen = new LoginScreen();

    public LoginScreen getLoginScreen() {
        return loginScreen;
    }

    public LoginController loginController; // = new LoginController(loginScreen, dataAdapter);

    private ProductController productController;

    public ProductController getProductController() {
        return productController;
    }

    private CheckoutController checkoutController;

    public CheckoutController getCheckoutController() {
        return checkoutController;
    }

    public DataAdapter getDataAdapter() {
        return dataAdapter;
    }

    private void initializeDatabase(Statement stmt) throws SQLException {
        // create the tables and insert sample data here!

        stmt.execute("create table Product (ProductID int PRIMARY KEY, ProductName char(30), Price double, Quantity double)");
        //    stmt.execute("create table Order (ProductID int PRIMARY KEY, ProductName char(30), Price double, Quantity double)");


    }

    private Application() {
        // create SQLite database connection here!
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:store.db");
            Statement stmt = connection.createStatement();
            if (!stmt.executeQuery("select * from product").next()) // product table do not exist
                initializeDatabase(stmt);


        }
        catch (ClassNotFoundException ex) {
            System.out.println("SQLite is not installed. System exits with error!");
            System.exit(1);
        }

        catch (SQLException ex) {
            System.out.println("SQLite database is not ready. System exits with error!" + ex.getMessage());

            System.exit(2);
        }

        // Create data adapter here!
        dataAdapter = new DataAdapter(connection);

        productController = new ProductController(productView, dataAdapter);

        checkoutController = new CheckoutController(checkoutScreen, dataAdapter);

        loginController = new LoginController(loginScreen, dataAdapter);
    }


    public static void main(String[] args) {
        Application.getInstance().getLoginScreen().setVisible(true);
    }
}
