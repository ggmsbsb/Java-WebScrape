package com.example.webscraperjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class scrapeApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Loading FXML...");
            // Create and initialize FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();
            System.out.println("FXML loaded successfully.");

            // Access the root element of the loaded FXML
            AnchorPane anchorPane = (AnchorPane) root;

            // Create a stage with no decorations (borderless)
            primaryStage.initStyle(StageStyle.UNDECORATED);

            // Get the preferred width and height from the root element
            double preferredWidth = anchorPane.getPrefWidth();
            double preferredHeight = anchorPane.getPrefHeight();

            // Set the scene with the preferred width and height
            Scene scene = new Scene(root, preferredWidth, preferredHeight);
            primaryStage.setScene(scene);

            // Title is not displayed in a borderless window, but you can set it as you wish
            primaryStage.setTitle("Web Scraper JavaFX Application");

            // Main window
            primaryStage.show();

        } catch (Exception e) {
            // Exception handling
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Inicia o App
    public static void main(String[] args) {
        launch(args);
    }
}
