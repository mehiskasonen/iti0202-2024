import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest extends PokemonBaseTest {

    @org.junit.jupiter.api.Test
    void testGetCorrectResults0Offset4Limit() throws IOException, URISyntaxException {
        File file = new File("pokemon content.txt");
        file.delete();

        String url = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=4";
        pokeRequest.makeRequest(url);
        pokeRequest.makeRequestForPokemon();

        LinkedHashMap<String, Integer> expectedMap = new LinkedHashMap<>();
        expectedMap.put("venusaur", 3);
        expectedMap.put("charmander", 2);
        expectedMap.put("ivysaur", 1);
        expectedMap.put("bulbasaur", 0);

        LinkedHashMap<String, Integer> actualMap = pokeRequest.getResults(0, 4);

        for (Map.Entry<String, Integer> entry : expectedMap.entrySet()) {
            String key = entry.getKey();
            Integer expectedValue = entry.getValue();
            Integer actualValue = actualMap.get(key);
            assertEquals(expectedValue, actualValue, "Value for key " + key + " doesn't match");
        }
    }

    @Test
    void testGetCorrectResults0Offset10Limit() {
        LinkedHashMap<String, Integer> expectedMap = new LinkedHashMap<>();
        expectedMap.put("charizard", 7);
        expectedMap.put("venusaur", 7);
        expectedMap.put("blastoise", 6);
        expectedMap.put("charmeleon", 5);
        expectedMap.put("ivysaur", 5);
        expectedMap.put("bulbasaur", 4);
        expectedMap.put("wartortle", 4);
        expectedMap.put("charmander", 3);
        expectedMap.put("squirtle", 3);
        expectedMap.put("caterpie", 0);

        LinkedHashMap<String, Integer> actualMap = pokeRequest.getResults(0, 10);
        for (Map.Entry<String, Integer> entry : expectedMap.entrySet()) {
            String key = entry.getKey();
            Integer expectedValue = entry.getValue();
            Integer actualValue = actualMap.get(key);
            assertEquals(expectedValue, actualValue, "Value for key " + key + " doesn't match");
        }
    }

    @Test
    void testGetCorrectResults69Offset100Limit() throws IOException {
        HashMap<String, Integer> initialMap = new LinkedHashMap<>();
        try (FileReader fr = new FileReader("tests/offset69limit100")) {
            Scanner sc = new Scanner(fr);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(" ");
                String pokeName = parts[0];
                int score = Integer.parseInt(parts[1]);
                initialMap.put(pokeName, score);
            }
        }
        initialMap = initialMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, NewValue) -> oldValue,
                        LinkedHashMap::new));

        LinkedHashMap<String, Integer> actualMap = pokeRequest.getResults(69, 100);

        for (Map.Entry<String, Integer> entry : initialMap.entrySet()) {
            String key = entry.getKey();
            Integer expectedValue = entry.getValue();
            Integer actualValue = actualMap.get(key);
            assertEquals(expectedValue, actualValue, "Value for key " + key + " doesn't match");
        }

    }

    @Test
    void testMakeRequestWritesLinks() throws IOException, URISyntaxException {
        File file = new File("pokemon urls.txt");
        file.delete();

        String url = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=2";
        pokeRequest.makeRequest(url);

        FileReader fr = new FileReader("pokemon urls.txt");
        Scanner sc = new Scanner(fr);
        StringBuilder content = new StringBuilder();
        while (sc.hasNextLine()) {
            content.append(sc.nextLine());
        }
        String expected = "https://pokeapi.co/api/v2/pokemon/1/https://pokeapi.co/api/v2/pokemon/2/";
        assertEquals(expected, content.toString());
    }

    @Test
    void testWriteContentToFileWritesToFile() throws IOException {
        File file = new File("pokemon content.txt");
        file.delete();

        String content = "{\"abilities\":[{\"ability\":{\"name\":\"overgrow\",\"url\":\"https://pokeapi.co/api/v2/ability/65/\"},\"is_hidden\":false,\"slot\":1},{\"ability\":{\"name\":\"chlorophyll\",\"url\":\"https://pokeapi.co/api/v2/ability/34/\"},\"is_hidden\":true,\"slot\":3}],\"base_experience\":64,\"cries\":{\"latest\":\"https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/1.ogg,";
        pokeRequest.writeContentToFile(content);
        String actualContent = new String(Files.readAllBytes(Paths.get("pokemon content.txt"))).trim();
        assertEquals(content, actualContent);
    }

    @Test
    void testMakeRequestForPokemon() throws IOException, URISyntaxException {
        pokeRequest.makeRequestForPokemon();
        pokeRequest.readPokemonFromFile(0, 2);
        assertEquals(2, duel.getPokemonList().size());
    }
}