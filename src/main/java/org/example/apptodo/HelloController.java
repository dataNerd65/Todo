package org.example.apptodo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class HelloController {
    @FXML
    public TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Button clearButton;
    @FXML
    private TextField email;
    @FXML
    private TextField Fname;
    @FXML
    private TextField username1;
    @FXML
    private PasswordField password2;
    @FXML
    private PasswordField password3;
    @FXML
    private Button signupButton;
    @FXML
    private Button clearButton1;

    public void showAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    public void handleClearButtonAction(){
        System.out.println("Cancel Button clicked!");
        username.clear();
        password.clear();
    }
    @FXML
    public void handleLoginButtonAction(){
        if (username.getText().isEmpty() || password.getText().isEmpty()){
            System.out.println("Username or password cannot be empty!");
        }else{
            System.out.println("Username: " + username.getText() + " ,Password: " +password.getText());
        }

    }
    @FXML
    public void  handleSignUpButtonAction(){
        System.out.println("SignUp button clicked!");
        String username = username1.getText();
        String password = password2.getText();
        String confirmPassword = password3.getText();
        String email = this.email.getText();
        String fullName = Fname.getText();

        //validating the user input
        if(username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || fullName.isEmpty()) {
            showAlert("Error", "Invalid Input", "All fields must be filled out!");
            return; // return to prevent method from still running
        }
        if(!password.equals(confirmPassword)) {
            showAlert("Error", "Invalid Input" , "Passwords do not match!");
            return; // method returns immediately to prevent execution of the rest of the code
        }
        //Connecting to the database
        try{
            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");
            String dbName = System.getenv("DB_NAME");

            Connection connection = DriverManager.getConnection(dbUrl+ "/" + dbName,  dbUser, dbPassword);

            String sql = "INSERT INTO users (username, password, email, full_name) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, fullName);

            int rows = statement.executeUpdate();
            //Trying out to tell user if success or failed
            if(rows > 0) {
                showAlert("Success", "Success", "Account created successfully");
            }else{
                showAlert("Error", "Account creation Failure", "There was a problem creating your account. Please try again.");
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    public void handleClear1ButtonAction(){
        System.out.println("Cancel Button Clicked!");

    }

}

