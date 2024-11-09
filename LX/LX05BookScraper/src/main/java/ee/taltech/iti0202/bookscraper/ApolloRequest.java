package ee.taltech.iti0202.bookscraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ApolloRequest {

    static String categoryString = "";



    static List<String> categoriesList = new ArrayList<>();

    public static String getCategoryString() {
        return categoryString;
    }

    public static List<String> getCategoriesList() {
        return categoriesList;
    }

    public static void addCategory(String category) {
        if (!categoriesList.contains(category)) {
            categoriesList.add(category);
        }
    }

    public static String categoryRequest() throws URISyntaxException, IOException {
        categoryString = Request.makeRequest("https://www.apollo.ee/raamatud");
        return Request.makeRequest("https://www.apollo.ee/raamatud");
    }

    public static boolean writeCategoryContent(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/files/Apollo Category Content.txt", true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static void readCategory(String catString) throws URISyntaxException, IOException {
        Document document =  Jsoup.parse(catString);
        Elements categories = document.select("div.category-link");
        for (Element category : categories) {
            categoriesList.add(category.text());
        }
    }

    public static String scrapeCategory(String category) {
        //booksContent = Request.makeRequest("")
        // ajalugu: https://www.apollo.ee/raamatud/eestikeelsed-raamatud/ajalugu
        // arhitektuur ja sisekujundus: https://www.apollo.ee/raamatud/eestikeelsed-raamatud/arhitektuur-ja-sisekujundus
        // arvuti ja internet: https://www.apollo.ee/raamatud/eestikeelsed-raamatud/arvuti-ja-internet
        return null;
    }
}
