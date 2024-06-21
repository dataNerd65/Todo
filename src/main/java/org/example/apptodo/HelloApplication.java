package org.example.apptodo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 680);

        //Load the CSS file
        String css = HelloApplication.class.getResource("styles.css").toExternalForm();

        //Adding the CSS file  to the scenes stylesheets
        scene.getStylesheets().add(css);
        stage.setTitle("FlipList");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}