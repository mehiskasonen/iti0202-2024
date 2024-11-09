package ee.taltech.iti0202.pokemon;

import com.google.gson.*;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Request {
    private final Duel duel;

    public Request(Duel duel) {
        this.duel = duel;
    }

    public void makeRequest(String url) throws IOException, URISyntaxException {
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

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject response = gson.fromJson(content.toString(), JsonObject.class);
        JsonArray results = response.getAsJsonArray("results");
        writeUrlsToFile(results);
    }

    public void writeUrlsToFile(JsonArray resultArray) {
        try (FileWriter fw = new FileWriter("pokemon urls.txt")) {
            for (JsonElement resultElement : resultArray) {
                JsonObject resultObject = resultElement.getAsJsonObject();
                String urlStr = resultObject.get("url").getAsString();
                fw.write(urlStr + "\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void writeContentToFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pokemon content.txt", true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeRequestForPokemon() throws IOException, URISyntaxException {
        FileReader fr = new FileReader("pokemon urls.txt");
        BufferedReader br = new BufferedReader(fr);

        String link;

        while ((link = br.readLine()) != null) {
            URI uri = new URI(link);
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
            con.disconnect();
            writeContentToFile(content.toString());
        }

    }

    public void readPokemonFromFile(int offset, int limit) throws IOException {
        FileReader fr = new FileReader("pokemon content.txt");
        BufferedReader br = new BufferedReader(fr);

        String inputLine;
        int currentLine = 0;
        int processedCount = 0;

        while ((inputLine = br.readLine()) != null) {
            if (currentLine < offset) {
                currentLine++;
                continue;
            }

            if (processedCount >= limit) {
                break;
            }

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Pokemon.class, new PokemonDeserializer());
            Gson gson = builder.create();
            Pokemon pokemon = gson.fromJson(inputLine, Pokemon.class);
            duel.addPokemon(pokemon);
            currentLine++;
            processedCount++;
        }
        br.close();
    }


    public LinkedHashMap<String, Integer> getResults(Integer offset, Integer limit) {
        LinkedHashMap<String, Integer> resultMap = new LinkedHashMap<>();
        try {
            readPokemonFromFile(offset, limit);
            resultMap = duel.simulateFightRotation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

}
