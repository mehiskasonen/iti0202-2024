import ee.taltech.iti0202.pokemon.Duel;
import ee.taltech.iti0202.pokemon.Request;
import ee.taltech.iti0202.pokemon.Updater;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {

        Duel duel = new Duel();
        Request pokeRequest = new Request(duel);
        //pokeRequest.makeRequest("https://pokeapi.co/api/v2/pokemon?offset=0&limit=1000");
        //pokeRequest.makeRequestForPokemon();

        Updater updater = new Updater();
        updater.readAttackMultipliers();

        pokeRequest.readPokemonFromFile(69, 100);
        //System.out.println(duel.getPokemonList().size());
        System.out.println(duel.simulateFightRotation());
        //System.out.println(pokeRequest.getResults(0, 4));
    }
}