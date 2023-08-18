import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        GameScraper scraper = new GameScraper();

        try {
            scraper.multiScrape(1, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        scraper.printGames();
    }
}

class GameScraper {
    private final String baseUrl = "https://eshop-prices.com/games?currency=BRL";
    private final Map<String, String> headers = new HashMap<>();

    private List<Map<String, String>> allGames;

    public GameScraper() {
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

        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);
            Elements gameList = document.select(".games-list-item-content");

            for (Element game : gameList) {
                String gameName = game.select(".games-list-item-title").text().trim();
                String gamePrice = game.select(".price").text().trim();

                Map<String, String> gameInfo = new HashMap<>();
                gameInfo.put("Nome", gameName);
                gameInfo.put("Valor", gamePrice);
                allGames.add(gameInfo);
            }
        } else {
            System.out.println("Página não encontrada, verifique a URL");
        }

        connection.disconnect();
    }

    public void multiScrape(int start, int end) throws IOException {
        for (int pageNumber = start; pageNumber <= end; pageNumber++) {
            scrape(pageNumber);
        }
    }

    public void printGames() {
        System.out.println("Jogos detectados: " + allGames.size());
        for (Map<String, String> game : allGames) {
            System.out.println("Game:    " + game.get("Nome"));
            System.out.println("Valor:   " + game.get("Valor") + "\n");
        }
    }
}
