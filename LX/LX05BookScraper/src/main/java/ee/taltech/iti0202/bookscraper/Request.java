package ee.taltech.iti0202.bookscraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Request {
    public static String makeRequest(String url) throws URISyntaxException, IOException {
        URI uri = new URI(url);
        URL requestUrl = uri.toURL();
        HttpURLConnection con = (HttpURLConnection) requestUrl.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/json");

        // Read the response of the request and places it in a content String:
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

}
