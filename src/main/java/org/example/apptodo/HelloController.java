package org.example.apptodo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;




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
        }
        if(password != confirmPassword) {
            showAlert("Error", "Invalid Input" , "Passwords do not match!");
        }

    }
    @FXML
    public void handleClear1ButtonAction(){
        System.out.println("Cancel Button Clicked!");

    }

}

