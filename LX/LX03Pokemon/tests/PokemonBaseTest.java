import ee.taltech.iti0202.pokemon.Duel;
import ee.taltech.iti0202.pokemon.Request;
import ee.taltech.iti0202.pokemon.Updater;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileNotFoundException;

public class PokemonBaseTest {
    Duel duel = new Duel();
    Request pokeRequest = new Request(duel);
    Updater updater = new Updater();

    @BeforeEach
    void setUp() throws FileNotFoundException {
        updater.readAttackMultipliers();
    }

}
