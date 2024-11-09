package ee.taltech.iti0202.pokemon;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PokemonDeserializer implements JsonDeserializer<Pokemon> {

    @Override
    public Pokemon deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        //System.out.println("deserialize");
        //System.out.println(jsonElement);
        JsonObject pokemon = jsonElement.getAsJsonObject();
        String name = pokemon.get("name").getAsString();
        JsonArray stats = pokemon.get("stats").getAsJsonArray();
        JsonArray abilities = pokemon.get("abilities").getAsJsonArray();

        int attack = -1;
        int hp = -1;
        int defense = -1;
        int specialAttack = -1;
        int specialDefense = -1;
        int speed = -1;
        List<String> typesArray = new ArrayList<>();
        List<String> movesArray = new ArrayList<>();
        List<String> abilitiesArray = new ArrayList<>();

        // Get abilities from abilities->ability
        for (JsonElement elem : abilities) {
            JsonObject ability = elem.getAsJsonObject().get("ability").getAsJsonObject();
            String abilityName = ability.get("name").getAsString();
            abilitiesArray.add(abilityName);
        }

        // Get integers from stats->stat
        for (JsonElement elem : stats) {
            JsonObject stat = elem.getAsJsonObject().get("stat").getAsJsonObject();
            String statName = stat.get("name").getAsString();
            int baseStat = elem.getAsJsonObject().get("base_stat").getAsInt();

            switch (statName) {
                case "hp" -> hp = baseStat;
                case "attack" -> attack = baseStat;
                case "defense" -> defense = baseStat;
                case "special-attack" -> specialAttack = baseStat;
                case "special-defense" -> specialDefense = baseStat;
                case "speed" -> speed = baseStat;
            }
        }

        JsonArray types = pokemon.get("types").getAsJsonArray();
        JsonArray moves = pokemon.get("moves").getAsJsonArray();

        for(JsonElement elem : moves) {
            JsonObject move = elem.getAsJsonObject().get("move").getAsJsonObject();
            String moveName = move.get("name").getAsString();
            movesArray.add(moveName);
        }

        for (JsonElement elem : types) {
            JsonObject typ = elem.getAsJsonObject().get("type").getAsJsonObject();
            String typeName = typ.get("name").getAsString();
            typesArray.add(typeName);
        }

        int baseExperience = pokemon.get("base_experience").getAsInt();
        int height = pokemon.get("height").getAsInt();
        int weight = pokemon.get("weight").getAsInt();

        return new Pokemon(name, attack, defense, hp, specialAttack, specialDefense, speed, typesArray, movesArray, abilitiesArray, weight, height, baseExperience);
        }
    }
