package org.example.apptodo;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class HelloController {
    @FXML
    public TextField username;
    @FXML
    private PasswordField password;
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
    private RadioButton Radio1;
    @FXML
    private RadioButton Radio2;
    @FXML
    private RadioButton Radio3;
    @FXML
    private TextField visiblePassword;
    @FXML
    private TextField visiblePassword2;
    @FXML
    private TextField visiblePassword3;
    @FXML
    private ChangeListener<Boolean> focusChangeListener;

    public void showAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();

        //Creating a pauseTransition that lasts 3 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> alert.close()); //close the alert when the pause is finished
        delay.play(); //starting the pause

    }

    private Map<TextField, RadioButton> fieldToButtonMap = new HashMap<>();

    public void initialize(){
        //Setting the initial visibility of the PasswordField and TextField
        password.setVisible(true);
        visiblePassword.setVisible(false);
        password2.setVisible(true);
        visiblePassword2.setVisible(false);
        password3.setVisible(true);
        visiblePassword3.setVisible(false);

        handleRadioButtonAction(Radio1, password, visiblePassword);
        handleRadioButtonAction(Radio2, password2, visiblePassword2);
        handleRadioButtonAction(Radio3, password3, visiblePassword3);
    }
    @FXML
    public void handleRadioButtonAction(RadioButton radioButton, PasswordField passwordField, TextField visiblePasswordField){
        if(visiblePasswordField != null && passwordField != null){
            if(radioButton.isSelected()){
                visiblePasswordField.setText(passwordField.getText());
                visiblePasswordField.setVisible(true);
                passwordField.setVisible(false);

                //remove all listeners from the focusedProperty
                if(focusChangeListener != null) {
                    visiblePasswordField.focusedProperty().removeListener(focusChangeListener);
                }

                //Add focusProperty listener to TextField
                visiblePasswordField.focusedProperty().addListener(focusChangeListener = (observable, oldValue, newValue)-> {
                    if(newValue == false) {//if focus is lost
                        RadioButton associatedButton = fieldToButtonMap.get(visiblePasswordField);
                        //radioButton.setSelected(false);
                        if (associatedButton != null){
                            associatedButton.setSelected(false);
                            visiblePasswordField.setVisible(false);
                            passwordField.setVisible(true);
                        }
                    }
                });
                //Store the RadioButton associated with the TextField
                fieldToButtonMap.put(visiblePasswordField, radioButton);
            }else{
                passwordField.setText(visiblePasswordField.getText());
                visiblePasswordField.setVisible(false);
                passwordField.setVisible(true);
            }
        }
    }
    @FXML
    public void handleRadio1Action(){
        handleRadioButtonAction(Radio1, password, visiblePassword);
    }
    @FXML
    public void handleRadio2Action(){
        handleRadioButtonAction(Radio2, password2, visiblePassword2);
    } @FXML
    public void handleRadio3Action(){
        handleRadioButtonAction(Radio3, password3, visiblePassword3);
    }

    @FXML
    public void handleClearButtonAction(){
        System.out.println("Cancel Button clicked!");
        username.clear();
        password.clear();
    }
    private Connection getConnection() throws SQLException{
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");
        String dbName = System.getenv("DB_NAME");
        return DriverManager.getConnection(dbUrl + "/" +dbName, dbUser, dbPassword);
    }
    @FXML
    public void handleLoginButtonAction(){
        String inputUsername = username.getText();
        String inputPassword = password.getText();


        if (inputUsername.isEmpty() || inputPassword.isEmpty()){
            System.out.println("Username or password cannot be empty!");
            return;
        }
        try{
            Connection connection = getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, inputUsername);
            statement.setString(2, inputPassword);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                showAlert("Login Success", "Welcome", "You have successfully logged in.");
            }else{
                showAlert("Login Failed", "Error! Invalid Credentials", "Username or password is incorrect.");
            }
        }catch(Exception e){
            e.printStackTrace();
            showAlert("Database Error", "Connection Failure", "Could not connect to the database. Please try again later.");
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
            Connection connection = getConnection();

            //Checking if username or email already exists
            String checkSql = "SELECT * FROM users WHERE username = ? OR email = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setString(1, username);
            checkStatement.setString(2, email);
            ResultSet resultset = checkStatement.executeQuery();

            if (resultset.next()) {
                showAlert("Error", "Registration Failed", "Username or Password already exists!");
                return;
            }

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
        Fname.clear();
        email.clear();
        username1.clear();
        password2.clear();
        password3.clear();

    }

}

