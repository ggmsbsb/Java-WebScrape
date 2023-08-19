package com.example.webscraperjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class scrapeApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Loading FXML...");
            //Create and intialize FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();
            System.out.println("FXML loaded successfully.");

            //App style
            primaryStage.setTitle("Web Scraper JavaFX Application");
            primaryStage.setScene(new Scene(root, 800, 600));

            //Main window
            primaryStage.show();

        } catch (Exception e) {
            //Exception handling
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Inicia o App
    public static void main(String[] args) {
        launch(args);
    }
}
