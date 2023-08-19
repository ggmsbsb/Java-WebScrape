package com.example.webscraperjavafx;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.List;
import java.util.Map;

public class scrapeController {
    @FXML
    private TextArea gameInfoTextArea;

    public void onextractButtonClick() {
        int pageCount = 1; //Set page count to scrape (set 1 for testing, otherwise it will take too long to scrape)

        Task<Void> scrapeTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                GameScraper gameScraper = new GameScraper();
                gameScraper.multiScrape(1, pageCount); // Scrape multiple pages method

                List<Map<String, String>> allGames = gameScraper.getAllGames();

                StringBuilder gameInfo = new StringBuilder();
                for (Map<String, String> game : allGames) {
                    gameInfo.append("Game: ").append(game.get("Nome")).append("\n");
                    gameInfo.append("Valor: ").append(game.get("Valor")).append("\n");
                    gameInfo.append("Categoria: ").append(game.get("Categoria")).append("\n\n");
                }

                //Updates textArea in fxml to display scraped games.
                Platform.runLater(() -> gameInfoTextArea.setText(gameInfo.toString()));
                return null;
            }
        };

        //Intialize task in new thread
        Thread thread = new Thread(scrapeTask);
        thread.setDaemon(true);
        thread.start();
    }
}
