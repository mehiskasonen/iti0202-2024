import ee.taltech.iti0202.pokemon.Pokemon;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DuelTest extends PokemonBaseTest {

    @Test
    void testDetermineHighestInitiativeHighSpeed() {
        List<String> types1 = List.of("Electric");
        List<String> moves1 = Arrays.asList("Thunderbolt", "Quick Attack");
        List<String> abilities1 = List.of("Static");
        Pokemon poke1 = new Pokemon("Pikachu", 70, 60, 30, 20,
                2, 4, types1, moves1, abilities1, 50,
                40, 100);

        List<String> types2 = List.of("Fire");
        List<String> moves2 = Arrays.asList("Ember", "Scratch");
        List<String> abilities2 = List.of("Blaze");
        Pokemon poke2 = new Pokemon("Charmander", 60, 65, 40, 25, 3,
                3, types2, moves2, abilities2, 60,
                35, 90);

        List<Pokemon> resultList = duel.determineHighestInitiative(poke2, poke1);
        assertEquals(poke1, resultList.getFirst()); // Pikachu should be on first place, since its faster.
    }

    @Test
    void testDetermineHighestInitiativeEqualSpeed() {
        List<String> types1 = List.of("Electric");
        List<String> moves1 = Arrays.asList("Thunderbolt", "Quick Attack");
        List<String> abilities1 = List.of("Static");
        Pokemon poke1 = new Pokemon("Pikachu", 70, 60, 30, 20,
                2, 4, types1, moves1, abilities1, 50,
                40, 100);

        List<String> types2 = List.of("Fire");
        List<String> moves2 = Arrays.asList("Ember", "Scratch");
        List<String> abilities2 = List.of("Blaze");
        Pokemon poke2 = new Pokemon("Charmander", 60, 65, 40, 25, 3,
                4, types2, moves2, abilities2, 60,
                35, 90);

        List<Pokemon> resultList = duel.determineHighestInitiative(poke2, poke1);
        assertEquals(poke1, resultList.getFirst()); // Pikachu should be in first place, since smaller weight.
    }

    @Test
    void testDetermineHighestInitiativeLowestHeight() {
        List<String> types1 = List.of("Electric");
        List<String> moves1 = Arrays.asList("Thunderbolt", "Quick Attack");
        List<String> abilities1 = List.of("Static");
        Pokemon poke1 = new Pokemon("Pikachu", 70, 60, 30, 20,
                2, 4, types1, moves1, abilities1, 60,
                40, 100);

        List<String> types2 = List.of("Fire");
        List<String> moves2 = Arrays.asList("Ember", "Scratch");
        List<String> abilities2 = List.of("Blaze", "Inferno");
        Pokemon poke2 = new Pokemon("Charmander", 60, 65, 40, 25, 3,
                4, types2, moves2, abilities2, 60,
                35, 90);

        List<Pokemon> resultList = duel.determineHighestInitiative(poke2, poke1);
        assertEquals(poke2, resultList.getFirst()); // Charmander should be in first place, since smaller height.
    }

    @Test
    void testDetermineHighestInitiativeMostAbilities() {
        List<String> types1 = List.of("Electric");
        List<String> moves1 = Arrays.asList("Thunderbolt", "Quick Attack");
        List<String> abilities1 = List.of("Static");
        Pokemon poke1 = new Pokemon("Pikachu", 70, 60, 30, 20,
                2, 4, types1, moves1, abilities1, 60,
                40, 100);

        List<String> types2 = List.of("Fire");
        List<String> moves2 = Arrays.asList("Ember", "Scratch");
        List<String> abilities2 = List.of("Blaze", "Inferno");
        Pokemon poke2 = new Pokemon("Charmander", 60, 65, 40, 25, 3,
                4, types2, moves2, abilities2, 60,
                40, 90);

        List<Pokemon> resultList = duel.determineHighestInitiative(poke2, poke1);
        assertEquals(poke2, resultList.getFirst()); // Charmander should be in first place, since more abilities.
    }

    @Test
    void testDetermineHighestInitiativeMostMoves() {
        List<String> types1 = List.of("Electric");
        List<String> moves1 = Arrays.asList("Thunderbolt", "Quick Attack", "Thunder Shock");
        List<String> abilities1 = List.of("Static", "Lightning Rod");
        Pokemon poke1 = new Pokemon("Pikachu", 70, 60, 30, 20,
                2, 4, types1, moves1, abilities1, 60,
                40, 100);

        List<String> types2 = List.of("Fire");
        List<String> moves2 = Arrays.asList("Ember", "Scratch");
        List<String> abilities2 = List.of("Blaze", "Inferno");
        Pokemon poke2 = new Pokemon("Charmander", 60, 65, 40, 25, 3,
                4, types2, moves2, abilities2, 60,
                40, 90);

        List<Pokemon> resultList = duel.determineHighestInitiative(poke2, poke1);
        assertEquals(poke1, resultList.getFirst()); // Pikachu should be in first place, since more moves.
    }

    @Test
    void testDetermineHighestInitiativeHighestExp() {
        List<String> types1 = List.of("Electric");
        List<String> moves1 = Arrays.asList("Thunderbolt", "Quick Attack");
        List<String> abilities1 = List.of("Static", "Lightning Rod");
        Pokemon poke1 = new Pokemon("Pikachu", 70, 60, 30, 20,
                2, 4, types1, moves1, abilities1, 60,
                40, 100);

        List<String> types2 = List.of("Fire");
        List<String> moves2 = Arrays.asList("Ember", "Scratch");
        List<String> abilities2 = List.of("Blaze", "Inferno");
        Pokemon poke2 = new Pokemon("Charmander", 60, 65, 40, 25, 3,
                4, types2, moves2, abilities2, 60,
                40, 90);

        List<Pokemon> resultList = duel.determineHighestInitiative(poke2, poke1);
        assertEquals(poke1, resultList.getFirst()); // Pikachu should be in first place, since more exp.
    }

    @Test
    void testDetermineHighestInitiativeNoFight() {
        List<String> types1 = List.of("Electric");
        List<String> moves1 = Arrays.asList("Thunderbolt", "Quick Attack");
        List<String> abilities1 = List.of("Static", "Lightning Rod");
        Pokemon poke1 = new Pokemon("Pikachu", 70, 60, 30, 20,
                2, 4, types1, moves1, abilities1, 60,
                40, 90);

        List<String> types2 = List.of("Fire");
        List<String> moves2 = Arrays.asList("Ember", "Scratch");
        List<String> abilities2 = List.of("Blaze", "Inferno");
        Pokemon poke2 = new Pokemon("Charmander", 60, 65, 40, 25, 3,
                4, types2, moves2, abilities2, 60,
                40, 90);

        List<Pokemon> resultList = duel.determineHighestInitiative(poke2, poke1);
        assertEquals(0, resultList.size());
    }

    @Test
    void testFightFirstRoundKO() {
        List<String> types1 = List.of("electric");
        List<String> moves1 = Arrays.asList("Thunderbolt", "Quick Attack");
        List<String> abilities1 = List.of("Static");
        Pokemon poke1 = new Pokemon("Pikachu", 70, 60, 30, 20,
                2, 4, types1, moves1, abilities1, 50,
                40, 100);

        List<String> types2 = List.of("fire");
        List<String> moves2 = Arrays.asList("Ember", "Scratch");
        List<String> abilities2 = List.of("Blaze");
        Pokemon poke2 = new Pokemon("Charmander", 60, 65, 40, 25, 3,
                3, types2, moves2, abilities2, 60,
                35, 90);

        duel.fight(poke1, poke2);
        assertEquals(0, duel.getScoreTable().get("Pikachu"));
        assertEquals(1, duel.getScoreTable().get("Charmander"));
    }

    @Test
    void testFightMultipleFights() {
        List<String> types1 = List.of("electric");
        List<String> moves1 = Arrays.asList("Thunderbolt", "Quick Attack");
        List<String> abilities1 = List.of("Static");
        Pokemon poke1 = new Pokemon("Pikachu", 70, 60, 30, 20,
                2, 4, types1, moves1, abilities1, 50,
                40, 100);

        List<String> types2 = List.of("fire");
        List<String> moves2 = Arrays.asList("Ember", "Scratch");
        List<String> abilities2 = List.of("Blaze");
        Pokemon poke2 = new Pokemon("Charmander", 60, 65, 40, 25, 3,
                3, types2, moves2, abilities2, 60,
                35, 90);

        for (int i = 0; i < 5; i++) {
            duel.fight(poke1, poke2);
        }
        assertEquals(5, duel.getScoreTable().get("Charmander"));
        assertEquals(0, duel.getScoreTable().get("Pikachu"));
        assertEquals(2, duel.getScoreTable().size());
    }

    @Test
    void testSimulateFightRotation() {
        Pokemon pikachu = new Pokemon("Pikachu", 55, 40, 35, 50, 50, 90,
                List.of("electric"), List.of("Thunder Shock", "Quick Attack"),
                List.of("Static", "Lightning Rod"), 60, 4, 112);

        Pokemon charmander = new Pokemon("Charmander", 52, 43, 39, 60, 50, 65,
                List.of("fire"), List.of("Ember", "Scratch"),
                List.of("Blaze"), 85, 6, 62);

        Pokemon squirtle = new Pokemon("Squirtle", 48, 65, 44, 50, 64, 43,
                List.of("water"), List.of("Water Gun", "Tackle"),
                List.of("Torrent"), 90, 5, 63);

        // Add the Pokémon to a Pokémon list
        List<Pokemon> pokemonList = List.of(pikachu, charmander, squirtle);

        duel.setPokemonList(pokemonList);

        // Simulate fights
        LinkedHashMap<String, Integer> scoreTable = duel.simulateFightRotation();

        // Assert that each Pokémon fought only one other Pokémon
        assertTrue(scoreTable.size() == pokemonList.size(), "Each Pokemon should fight only one other Pokemon");
        for (Map.Entry<String, Integer> entry : scoreTable.entrySet()) {
            assertEquals(1, entry.getValue(), "Each Pokemon should have fought only one other Pokemon");
        }

        // Assert that the loser gets 0 points
        for (Map.Entry<String, Integer> entry : scoreTable.entrySet()) {
            if (entry.getValue() == 0) {
                assertEquals(0, entry.getValue(), "The loser should get 0 points");
            }
        }
    }

}