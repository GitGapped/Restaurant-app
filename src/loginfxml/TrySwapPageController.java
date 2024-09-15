

package loginfxml;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXML;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.BarChart;
import javafx.fxml.FXML;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
public class TrySwapPageController implements Initializable {
     @FXML
    private BarChart<String, Number> foodBarChart;

    @FXML
    private TextField textExample;
    @FXML
    private AnchorPane Home_page;

    @FXML
    private Button home_btn;

    @FXML
    private Button option2_btn;

    @FXML
    private AnchorPane option2_page;
    
    @FXML
    private Label usename_display;
  
    @FXML
    private Button booking_btn;
    @FXML
    private AnchorPane bookings_page;
     @FXML
    private Button chart_btn;
      @FXML
    private AnchorPane chart_page;
       @FXML
    private PieChart foodPieChart;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productStockColumn;
    
    @FXML
    private Button order_btn;
   
    @FXML
    private TableColumn<Order, Integer> orderIdColumn;
    
    
    
    @FXML
    private TableColumn<Order, Integer> productIdColumn;
    
    @FXML
    private TableView<Order> ordersTable;
    

   @FXML
    private Label bookingInfoLabel;
   
    @FXML
    
private GridPane tableGrid;
@FXML
private TextField nameField;
@FXML
private TextField phoneField;
 @FXML
    private Button table1;
    @FXML
    private Button table2;
    @FXML
    private Button table3;
    @FXML
    private Button table4;
    @FXML
    private Button table5;
    @FXML
    private Button table6;
    @FXML
    private Button table7;
    @FXML
    private Button table8;
    @FXML
    private Button table9;
      @FXML
    private Button deleteOrderButton;
     private Table selectedTable;

    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/test3";
    private final String DATABASE_USERNAME = "root";
    private final String DATABASE_PASSWORD = "";
    private Connection connection;

    @FXML
    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {
            Home_page.setVisible(true);
            option2_page.setVisible(false);
            bookings_page.setVisible(false);
            chart_page.setVisible(false);
        } else if (event.getSource() == option2_btn) {
            Home_page.setVisible(false);
            option2_page.setVisible(true);
            bookings_page.setVisible(false);
            chart_page.setVisible(false);
        } else if (event.getSource() == booking_btn) {
            bookings_page.setVisible(true);
            Home_page.setVisible(false);
            option2_page.setVisible(false);
            chart_page.setVisible(false);
        }
        else if (event.getSource() == chart_btn) {
            chart_page.setVisible(true);
            Home_page.setVisible(false);
            option2_page.setVisible(false);
            bookings_page.setVisible(false);
        }
    }

    public void displayUsername() {
        String user = data.username;
        usename_display.setText(user);
    }
private void loadPieChartData() {
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {
        String query = "SELECT products.product_name, COUNT(orders.product_id) AS order_count " +
                "FROM orders " +
                "JOIN products ON orders.product_id = products.product_id " +
                "GROUP BY orders.product_id";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String productName = resultSet.getString("product_name");
                int orderCount = resultSet.getInt("order_count");
                pieChartData.add(new PieChart.Data(productName, orderCount));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(AlertType.ERROR, "Database Error", "An error occurred while fetching chart data.");
    }

    foodPieChart.setData(pieChartData);
}
    public void displayAllProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            String sql = "SELECT product_id, product_name, product_stock FROM products";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                int productStock = resultSet.getInt("product_stock");
                products.add(new Product(productId, productName, productStock));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors that occur during connection or query execution
        }

        productTable.setItems(products);
    }

    private void updateProduct(Product product) {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            String sql = "UPDATE products SET product_name = ?, product_stock = ? WHERE product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getProductStock());
            statement.setInt(3, product.getProductId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Product Updated", "Product was updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Update Failed", "Failed to update product.");
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Exception: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the product.");
        }
    }

    @FXML
    private void addProduct(ActionEvent event) {
        // Create a dialog to get input from the user for the new product
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Product");
        dialog.setHeaderText("Enter the name of the new product:");
        dialog.setContentText("Product Name:");

        // Show the dialog and wait for user input
        Optional<String> result = dialog.showAndWait();
        
        // Process the user input if available
        result.ifPresent(productName -> {
            // Get the product name from the input
            String newProductName = result.get();
            
            // Prompt the user to enter the stock quantity
            TextInputDialog stockDialog = new TextInputDialog();
            stockDialog.setTitle("Add New Product");
            stockDialog.setHeaderText("Enter the stock quantity for the new product:");
            stockDialog.setContentText("Stock Quantity:");
            
            // Show the stock quantity dialog and wait for user input
            Optional<String> stockResult = stockDialog.showAndWait();
            
            // Process the stock quantity input if available
            stockResult.ifPresent(stock -> {
                // Parse the stock quantity as an integer
                int productStock = Integer.parseInt(stockResult.get());
                
                // Insert the new product into the database
                insertNewProduct(newProductName, productStock);
                
                // Refresh the product list to display the newly added product
                displayAllProducts();
            });
        });
    }

    private void insertNewProduct(String productName, int productStock) {
        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            
            // Prepare the SQL statement to insert the new product
            String sql = "INSERT INTO products (product_name, product_stock) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            // Set the parameters for the SQL statement
            statement.setString(1, productName);
            statement.setInt(2, productStock);
            
            // Execute the SQL statement to insert the new product
            int affectedRows = statement.executeUpdate();
            
            // Check if the product was successfully inserted
            if (affectedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Product Added", "New product was added successfully.");
                
            } else {
                showAlert(Alert.AlertType.ERROR, "Insert Failed", "Failed to add new product.");
            }
            
            // Close the statement and connection
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while adding the new product.");
        }
    }
    
    @FXML
private void deleteProduct(ActionEvent event) {
    // Get the selected product from the table
    Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
    
    // Check if a product is selected
    if (selectedProduct != null) {
        // Remove the product from the table view
        productTable.getItems().remove(selectedProduct);
        
        // Delete the product from the database
        deleteProductFromDatabase(selectedProduct);
    } else {
        // If no product is selected, display an error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("No Product Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select a product to delete.");
        alert.showAndWait();
    }
}
  private void deleteProductFromDatabase(Product product) {
    try {
        // Establish connection to the database
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        
        // Prepare the SQL statement to delete the product
        String sql = "DELETE FROM products WHERE product_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        
        // Set the parameter for the SQL statement
        statement.setInt(1, product.getProductId());
        
        // Execute the SQL statement to delete the product
        int affectedRows = statement.executeUpdate();
        
        // Check if the product was successfully deleted
        if (affectedRows > 0) {
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Failed to delete product.");
        }
        
        // Close the statement and connection
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle any errors that occur during connection or query execution
    }
}
  @FXML


    @Override
    public void initialize(URL url, ResourceBundle rb) {
      ////// 
       loadPieChartData();
   
    /////////
   updateTableStatuses();

        displayUsername();
        
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        
        productTable.setEditable(true);
        productNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        productStockColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        productNameColumn.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setProductName(event.getNewValue());
            updateProduct(product);
        });

        productStockColumn.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setProductStock(event.getNewValue());
            updateProduct(product);
        });
          orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        
        // Populate table with orders data
        

        displayAllProducts();
        displayAllOrders();
     // displayAvailableTables();
     displayBookingInfo();
    }
     private void updateTableStatuses() {
        // Assume you have an array or list of buttons representing the tables
        Button[] tables = {table1, table2, table3, table4, table5, table6, table7, table8, table9};
    
        try {
            // Establish connection to the database
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        
            // Prepare the SQL statement to fetch the table statuses
            String selectStatusSQL = "SELECT table_id, status FROM tables";
            PreparedStatement selectStatement = connection.prepareStatement(selectStatusSQL);
            ResultSet resultSet = selectStatement.executeQuery();
        
            // Iterate through the result set to update the button styles
            while (resultSet.next()) {
                String tableId = resultSet.getString("table_id");
                String status = resultSet.getString("status");
            
                // Update the button style based on the table status
                for (Button table : tables) {
                    if (table.getText().equals(tableId)) {
                        if (status.equals("unavailable")) {
                            table.setStyle("-fx-background-color: red;");
                        } else {
                            table.setStyle("-fx-background-color: green;");
                        }
                        break;
                    }
                }
            }
        
            // Close the result set, statement, and connection
            resultSet.close();
            selectStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "An error occurred while fetching table statuses.");
        }
         for (Button table : tables) {
            if (!table.getStyle().contains("red")) {
                table.setStyle("-fx-background-color: green;");
            }
        }
    }
     
     public void displayBookingInfo() {
        String query = "SELECT tables.table_id, tables.name, tables.phone_number, users.uname " +
                       "FROM tables " +
                       "JOIN users ON tables.user_id = users.user_id " +
                       "WHERE tables.status = 'unavailable'";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            StringBuilder bookingInfo = new StringBuilder();

            while (resultSet.next()) {
                String tableId = resultSet.getString("table_id");
                String name = resultSet.getString("name");
                String phoneNumber = resultSet.getString("phone_number");
                String userName = resultSet.getString("uname");

                bookingInfo.append("Table ID: ").append(tableId).append("\n");
                bookingInfo.append("Customer: ").append(name).append("\n");
                bookingInfo.append("Phone Number: ").append(phoneNumber).append("\n");
                bookingInfo.append("User: ").append(userName).append("\n\n");
            }

            bookingInfoLabel.setText(bookingInfo.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "An error occurred while fetching booking information.");
        }
     }
private void displayAllOrders() {
    ObservableList<Order> orders = FXCollections.observableArrayList();
    try {
        // Establish connection to the database
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        
        // Prepare the SQL statement to select all orders with user names and product names
        String sql = "SELECT orders.order_id, users.uname, products.product_name, orders.product_id " +
                     "FROM orders " +
                     "INNER JOIN users ON orders.user_id = users.user_id " +
                     "INNER JOIN products ON orders.product_id = products.product_id";
        PreparedStatement statement = connection.prepareStatement(sql);
        
        // Execute the SQL statement to retrieve orders data
        ResultSet resultSet = statement.executeQuery();
        
        // Iterate through the result set and add orders to the list
        while (resultSet.next()) {
            int orderId = resultSet.getInt("order_id");
            String userName = resultSet.getString("uname");
            String productName = resultSet.getString("product_name");
            int productId = resultSet.getInt("product_id");
            orders.add(new Order(orderId, productId, userName, productName));
        }
        
        // Close the result set, statement, and connection
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while retrieving orders data.");
    }
    
    // Set the items in the ordersTable
    ordersTable.setItems(orders);
}
    @FXML
    private void orderProduct(ActionEvent event) {
        // Get the selected product from the table
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        
        // Check if a product is selected
        if (selectedProduct != null) {
            // Register the order in the database
            registerOrder(selectedProduct);
        } else {
            // If no product is selected, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Product Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to order.");
            alert.showAndWait();
        }
    }

    private void registerOrder(Product product) {
        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            
            // Prepare the SQL statement to insert a new order
            String sql = "INSERT INTO orders (user_id, product_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            // Set the parameters for the SQL statement
            statement.setInt(1, getCurrentUserId()); // Assuming you have a method to get the current user's ID
            statement.setInt(2, product.getProductId());
            
            // Execute the SQL statement to insert the new order
            int affectedRows = statement.executeUpdate();
            
            // Check if the order was successfully inserted
            if (affectedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Order Placed", "Your order has been placed successfully.");
                displayAllOrders();
                loadPieChartData();
            } else {
                showAlert(Alert.AlertType.ERROR, "Order Failed", "Failed to place the order.");
            }
            
            // Close the statement and connection
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while placing the order.");
        }
    }

@FXML
private void bookTable(ActionEvent event) {
    Button sourceButton = (Button) event.getSource();
    String tableName = sourceButton.getText(); // Assuming the table text is the table name

    try {
        // Establish connection to the database
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        
        // Check if the table is already booked (marked as red)
        if (sourceButton.getStyle().contains("-fx-background-color: red;")) {
            // Prepare the SQL statement to delete booking details
            String deleteBookingSQL = "DELETE FROM tables WHERE table_id = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteBookingSQL);
            deleteStatement.setString(1, tableName);
            
            // Execute the delete statement to cancel the booking
            int rowsAffected = deleteStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted."); // Debugging
            
            showAlert(AlertType.INFORMATION, "Booking Canceled", "Booking for " + tableName + " canceled successfully.");
            displayBookingInfo();
            // Close the delete statement
            deleteStatement.close();
        } else {
            // Get the phone number from the user
            String phoneNumber = getPhoneNumberFromUser();
            
            // Get the name from the user
            String name = getNameFromUser();

            // Prepare the SQL statement to update the table status and insert the booking details
            String updateStatusSQL = "UPDATE tables SET status = 'unavailable' WHERE name = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateStatusSQL);
            updateStatement.setString(1, tableName);

            String insertBookingSQL = "INSERT INTO tables (table_id, status, name, phone_number, user_id) VALUES (?, 'unavailable', ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertBookingSQL);
            insertStatement.setString(1, tableName); // Assuming the table name is also the table ID
            insertStatement.setString(2, name); // Name obtained from user input
            insertStatement.setString(3, phoneNumber); // Phone number obtained from user input
            // Assuming you have a method to get the user ID
            insertStatement.setInt(4, getCurrentUserId());

            // Execute the update statement to change the table status
            updateStatement.executeUpdate();
            // Execute the insert statement to book the table
            insertStatement.executeUpdate();

            showAlert(AlertType.INFORMATION, "Booking Successful", "You have booked " + tableName);
displayBookingInfo();
            // Close the statements
            updateStatement.close();
            insertStatement.close();
            
        }
        
        // Close the connection
        connection.close();
        
        // Update the button style regardless of booking or canceling
        if (!sourceButton.getStyle().contains("-fx-background-color: red;")) {
            sourceButton.setStyle("-fx-background-color: red;");
        } else {
            sourceButton.setStyle("-fx-background-color: green;");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(AlertType.ERROR, "Database Error", "An error occurred while booking or canceling the table.");
    }
}

// Method to get phone number from the user
private String getPhoneNumberFromUser() {
    // Use a JavaFX TextInputDialog to prompt the user for their phone number
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Enter Phone Number");
    dialog.setHeaderText(null);
    dialog.setContentText("Please enter your phone number:");

    Optional<String> result = dialog.showAndWait();
    return result.orElse(""); // Return the entered phone number or an empty string if nothing is entered
}

// Method to get name from the user
private String getNameFromUser() {
    // Use a JavaFX TextInputDialog to prompt the user for their name
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Enter Name");
    dialog.setHeaderText(null);
    dialog.setContentText("Please enter Customer name:");

    Optional<String> result = dialog.showAndWait();
    return result.orElse(""); // Return the entered name or an empty string if nothing is entered
}

// Method to get the user ID

    

   



    private int getCurrentUserId() {
        // Implement this method to return the current logged-in user's ID
        // For example:
        return data.userId;
    }
     @FXML
    private void deleteOrder(ActionEvent event) {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert(Alert.AlertType.WARNING, "No Order Selected", "Please select an order to delete.");
            return;
        }

        // Delete the selected order from the database
        boolean success = deleteOrderFromDatabase(selectedOrder.getOrderId());
        if (success) {
            ordersTable.getItems().remove(selectedOrder);
            showAlert(Alert.AlertType.INFORMATION, "Order Deleted", "The selected order has been deleted.");
            loadPieChartData();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the order from the database.");
        }
    }

    private boolean deleteOrderFromDatabase(int orderId) {
        String deleteQuery = "DELETE FROM orders WHERE order_id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, orderId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}