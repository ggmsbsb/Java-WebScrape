package com.example.webscraperjavafx;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Map;

public class HelloController {
    @FXML
    private TextArea gameInfoTextArea;

    public void onHelloButtonClick() {
        int pageCount = 1; // Set the desired page count here

        Task<Void> scrapeTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                GameScraper gameScraper = new GameScraper();
                gameScraper.multiScrape(1, pageCount); // Scrape multiple pages

                List<Map<String, String>> allGames = gameScraper.getAllGames();

                StringBuilder gameInfo = new StringBuilder();
                for (Map<String, String> game : allGames) {
                    gameInfo.append("Game: ").append(game.get("Nome")).append("\n");
                    gameInfo.append("Valor: ").append(game.get("Valor")).append("\n");
                    gameInfo.append("Categoria: ").append(game.get("Categoria")).append("\n\n");
                }

                Platform.runLater(() -> gameInfoTextArea.setText(gameInfo.toString()));
                return null;
            }
        };

        Thread thread = new Thread(scrapeTask);
        thread.setDaemon(true);
        thread.start();
    }
}
