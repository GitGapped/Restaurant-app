/*      @FXML
    private Button login_btn;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_username;
    
    private Connection connect;
    PreparedStatement statement = connect.prepareStatement(sql);
    private ResultSet result;
    private Statement statement;
 */
package loginfxml;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.ResultSet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_username;

    @FXML
    private TextField login_passwordField;

    @FXML
    private CheckBox login_selectShowPassword;

    @FXML
    private CheckBox login_showPassword;

    final String DATABASE_URL = "jdbc:mysql://localhost:3306/test3";
    final String DATABASE_USERNAME = "root";
    final String DATABASE_PASSWORD = "";
    private Connection connection;

    @FXML
    private void handleButtonAction2(ActionEvent event) throws IOException {
        try {
            // Establish the connection
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            // Query to check if the username and password match
            String sql = "SELECT * FROM users WHERE uname=? AND pword=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login_username.getText());
            statement.setString(2, login_password.getText());

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if the result set has any rows (meaning username and password are correct)
            if (resultSet.next()) {

                //for showing text from database
                data.username = login_username.getText();
                int userId = resultSet.getInt("user_id");
                data.setUserId(userId);
            /*   
                ////////////////////////////////////////////hereeeeeeeeeeeee
                
                // Fetch product information
               String productSql = "SELECT product_name, product_stock FROM products WHERE product_id=?";
                PreparedStatement productStatement = connection.prepareStatement(productSql);

                // Print the column names for debugging
                ResultSetMetaData rsmd = resultSet.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    System.out.println("Column: " + rsmd.getColumnName(i));
                }

                // Ensure you are querying the correct column
                int productId = resultSet.getInt("product_id");
                productStatement.setInt(1, productId);

                ResultSet productResultSet = productStatement.executeQuery();

                if (productResultSet.next()) {
                    data.product = productResultSet.getString("product_name");
                    data.stock = productResultSet.getInt("product_stock");
                }
                ///////////////////////////////////////till hereeeeeeeeeeeeeeeeee
*/
                // Display a success message
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setHeaderText(null);
                alert.setContentText("Welcome, " + login_username.getText() + "!");
                alert.showAndWait();

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // hide the login form
                login_btn.getScene().getWindow().hide();
                // show the new form
                Parent root = FXMLLoader.load(getClass().getResource("TrySwapPage.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            } else {
                // Display an error message
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password.");
                alert.showAndWait();
            }

            // Close the result set and statement
            resultSet.close();
            statement.close();

            // Don't forget to close the connection when done
            connection.close();
        } catch (SQLException e) {
            // Handle any errors that occur during connection or query execution
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void ShowPassword() {
        if (login_showPassword.isSelected()) {
            login_passwordField.setText(login_password.getText()); // This line is unnecessary, remove it
            login_passwordField.setVisible(true);
            login_password.setVisible(false);
        } else {
            login_password.setText(login_passwordField.getText()); // This line is unnecessary, remove it
            login_passwordField.setVisible(false);
            login_password.setVisible(true);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
            return;
        }

        // Initialize DriverManager
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}
