package com.example.webscraperjavafx;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameScraper {
    private final String baseUrl = "https://eshop-prices.com/games?currency=BRL";
    private final Map<String, String> headers = new HashMap<>();
    private final List<Map<String, String>> allGames;

    public GameScraper() {
        //HTTP Header
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
        allGames = new ArrayList<>();
    }

    public void scrape(int pageNumber) throws IOException {
        String url;
        if (pageNumber > 1) {
            url = baseUrl + "&page=" + pageNumber;
        } else {
            url = baseUrl;
        }

        //Connects to website and extract game elements
        Document document = Jsoup.connect(url).headers(headers).get();
        Elements gameList = document.select(".games-list-item-content");

        //Extract desired info
        for (Element game : gameList) {
            String gameName = game.select(".games-list-item-title").text().trim();
            String gamePrice = game.select(".price").text().trim();

            //Data enrichment
            //Uses metacritic URL to fetch each game genre (not available in baseUrl)
            String metacriticUrl = getMetacriticUrlFromSearch(gameName);
            String gameCategory = getCategoryFromMetacritic(metacriticUrl);

            //Create an info map and adds it to a list
            Map<String, String> gameInfo = new HashMap<>();
            gameInfo.put("Nome", gameName);
            gameInfo.put("Valor", gamePrice);
            gameInfo.put("Categoria", gameCategory);
            allGames.add(gameInfo);
        }
    }

    public void multiScrape(int startPage, int endPage) throws IOException {
        //execute multi scraping
        for (int pageNumber = startPage; pageNumber <= endPage; pageNumber++) {
            scrape(pageNumber);
        }
    }


    public List<Map<String, String>> getAllGames() {
        //return game info list
        return allGames;
    }

    private String encodeGameName(String gameName) throws UnsupportedEncodingException {
        //Handles the different URL and removes any special character
        return gameName
                .replace(" ", "-")
                .replace("-™-", "-")
                .toLowerCase();
    }

    private String getMetacriticUrlFromSearch(String gameName) throws UnsupportedEncodingException, IOException {
        //Get metacritic URL
        String searchUrl = "https://www.google.com/search?q=" + URLEncoder.encode(gameName + " Metacritic", "UTF-8");
        Document searchResults = Jsoup.connect(searchUrl).get();
        Element metacriticLink = searchResults.select(".tF2Cxc a").first();
        if (metacriticLink != null) {
            return metacriticLink.attr("href");
        }
        return null;
    }

    private String getCategoryFromMetacritic(String metacriticUrl) throws IOException {
        //extract game category
        if (metacriticUrl == null) {
            return "Categoria não encontrada";
        }

        Document document = Jsoup.connect(metacriticUrl).get();
        Element categoryElement = document.select(".summary_detail.product_genre").first();
        if (categoryElement != null) {
            return categoryElement.text();
        }
        return "Categoria não encontrada";
    }
}
