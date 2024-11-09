package ee.taltech.iti0202.computerstore.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ee.taltech.iti0202.computerstore.components.Component;
import ee.taltech.iti0202.computerstore.exceptions.OutOfStockException;
import ee.taltech.iti0202.computerstore.exceptions.ProductAlreadyExistsException;
import ee.taltech.iti0202.computerstore.exceptions.ProductNotFoundException;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Database {
    private final Map<Integer, Component> components = new HashMap<>();

    private static final Database DB_INSTANCE = new Database();
    private Database() { }


    public static Database getInstance() {
        return DB_INSTANCE;
    }

    public void saveComponent(Component component) throws ProductAlreadyExistsException {
        if (components.containsValue(component)) {
            throw new ProductAlreadyExistsException();
        }
        components.put(component.getId(), component);
    }

    public void deleteComponent(int id) throws ProductNotFoundException {
        if (!components.containsKey(id)) {
            throw new ProductNotFoundException();
        }
        components.remove(id, components.get(id));
    }

    public void increaseComponentStock(int id, int amount) throws ProductNotFoundException {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
        if (!components.containsKey(id)) {
            throw new ProductNotFoundException();
        }
        components.get(id).setAmount(components.get(id).getAmount() + amount);
    }

    public void decreaseComponentStock(int id, int amount) throws OutOfStockException, ProductNotFoundException {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
        if (!components.containsKey(id)) {
            throw new ProductNotFoundException();
        }
        if ((components.get(id).getAmount() - amount) < 0) {
            throw new OutOfStockException();
        }
        components.get(id).setAmount(components.get(id).getAmount() - amount);
    }

    public Map<Integer, Component> getComponents() {
      return components;
    }

    public void resetEntireDatabase() {
        components.clear();
        Component.setNextId(-1);
    }

    /**
     * Use Gson to convert current objects to string and save it to file.
     * @param location is the file's path.
     */
    public void saveToFile(String location) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject componentsJson = new JsonObject();
        for (Map.Entry<Integer, Component> entry : components.entrySet()) {
            int id = entry.getKey();
            Component comp = entry.getValue();
            JsonObject compJson = new JsonObject();
            compJson.addProperty("id", id);
            compJson.addProperty("name", comp.getName());
            compJson.addProperty("type", comp.getType().toString());
            compJson.addProperty("price", comp.getPrice());
            compJson.addProperty("amount", comp.getAmount());
            compJson.addProperty("manufacturer", comp.getManufacturer());
            compJson.addProperty("performancePoints", comp.getPerformancePoints());
            compJson.addProperty("powerConsumption", comp.getPowerConsumption());
            componentsJson.add(String.valueOf(id), compJson);
        }
        JsonObject fileJson = new JsonObject();
        fileJson.add("components", componentsJson);
        String jsonString = gson.toJson(fileJson);

        String filepath = Paths.get(location).toString();
        try (FileWriter writer = new FileWriter(filepath)) {
                writer.write(jsonString);
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Read the file from disk to memory and then replace the current singleton instance with new value from Gson.
     *
     * @param location of file.
     * @return string loaded from file.
     */
    public String loadFromFile(String location) {
        List<String> lines = new ArrayList<>();

        String prettyJsonString;
        try (BufferedReader reader = Files.newBufferedReader(Path.of(location))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    lines.add("");
                } else {
                    lines.add(line);
                }
            }

        } catch (IOException e) {
            System.out.println("An exception was encountered.");
            throw new RuntimeException(e);
        }
        StringBuilder jsonBuilder = new StringBuilder();
        for (String arrayLine : lines) {
            jsonBuilder.append(arrayLine);
        }
        String jsonString = jsonBuilder.toString();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        prettyJsonString = gson.toJson(gson.fromJson(jsonString, Object.class));

        JsonElement element = JsonParser.parseString(jsonString);
        JsonObject jsonObject = element.getAsJsonObject();
        JsonObject componentsObject = jsonObject.getAsJsonObject("components");

        for (Map.Entry<String, JsonElement> entry : componentsObject.entrySet()) {
            JsonObject componentObject = entry.getValue().getAsJsonObject();
            int id = componentObject.get("id").getAsInt();

            Component component = new Component(
                    componentObject.get("name").getAsString(),
                    Component.Type.valueOf(componentObject.get("type").getAsString()),
                    new BigDecimal(componentObject.get("price").getAsString()),
                    componentObject.get("manufacturer").getAsString(),
                    componentObject.get("performancePoints").getAsInt(),
                    componentObject.get("powerConsumption").getAsInt()
            );

            component.setAmount(componentObject.get("amount").getAsInt());
            component.setId(id);
            if (components.containsKey(id)) {
                components.replace(id, component);
            }
        }
        return prettyJsonString;
    }

}
