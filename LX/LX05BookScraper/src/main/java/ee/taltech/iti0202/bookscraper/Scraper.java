package ee.taltech.iti0202.bookscraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Scraper {
    public static void main(String[] args) throws URISyntaxException, IOException {
        /*
        Scraper scraper = new Scraper(
            url = "apollo.ee",
            bookCssSelector = "li item",
            pages = "div.pages page");


        read category()

            read pages()
                read page()
                    read books()
                        read book()

        */

        String content = Request.makeRequest("https://www.apollo.ee/raamatud");
        ApolloRequest.readCategory(content);
        System.out.println(ApolloRequest.getCategoriesList());
        //ConsoleApp.BookScraper();
    }
}
