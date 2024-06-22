package org.example.apptodo;

import javafx.fxml.FXML;
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
    private Button cancelButton;
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
    private Button cancel1;
    @FXML
    public void handleCancelButtonAction(){
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

    }
    @FXML
    public void handleCancel1ButtonAction(){
        System.out.println("Cancel Button Clicked!");

    }

}

