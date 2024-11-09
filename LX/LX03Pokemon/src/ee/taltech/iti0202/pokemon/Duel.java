package ee.taltech.iti0202.pokemon;

import java.util.*;
import java.util.stream.Collectors;

public class Duel {

    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    List<Pokemon> pokemonList;

    public Map<String, Integer> getScoreTable() {
        return scoreTable;
    }

    Map<String, Integer> scoreTable = new HashMap<>();

    public Duel() {
        this.pokemonList = new ArrayList<>();
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public void addPokemon(Pokemon poke) {
        if (!pokemonList.contains(poke)) {
            pokemonList.add(poke);
        }
    }

    public void fight(Pokemon pokemon1, Pokemon pokemon2) {
        int turnCounter = 0;
        while (turnCounter < 100) {
            boolean firstFightBoolean = makeAttack(pokemon1, pokemon2, turnCounter);
            if (!firstFightBoolean) {
                break;
            } else if (firstFightBoolean) {
                boolean secondFightBoolean = makeAttack(pokemon2, pokemon1, turnCounter);
                if (!secondFightBoolean) {
                    break;
                }
            }
            turnCounter++;
        }
        pokemon1.setFightHp(pokemon1.getHp());
        pokemon2.setFightHp(pokemon2.getHp());
    }

    public boolean makeAttack(Pokemon attacker, Pokemon defender, int moveNr) {
        double totalAttack = attacker.getAttack(moveNr) * attacker.getAttackMultiplier(defender.getTypes()) - defender.getDefense(moveNr);
        if (totalAttack > 0) {
            defender.setFightHp(defender.getFightHp() - totalAttack);
            if (defender.getFightHp() < 1) {
                scoreTable.put(attacker.getName(), scoreTable.getOrDefault(attacker.getName(), 0) + 1);
                if (!scoreTable.containsKey(defender.getName())) {
                    scoreTable.put(defender.getName(), 0);
                }
                attacker.setFightHp(attacker.getHp());
                defender.setFightHp(defender.getHp());
                return false;
            }
        }
        return true;
    }

    public LinkedHashMap<String, Integer> simulateFightRotation() {
        for (int i = 0; i < pokemonList.size(); i++) {
            Pokemon attackingPoke = pokemonList.get(i);
            for (int j = i + 1; j < pokemonList.size(); j++) {
                if (i != j) {
                    Pokemon defendingPoke = pokemonList.get(j);
                    List<Pokemon> initiativeOrder = determineHighestInitiative(attackingPoke, defendingPoke);
                    if (initiativeOrder.isEmpty()) {
                        throw new RuntimeException("Two pokemon are the same. Initiative order returned 0.");
                    } else {
                        fight(initiativeOrder.get(0), initiativeOrder.get(1));
                    }
                }
            }
        }
        return scoreTable.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, NewValue) -> oldValue,
                        LinkedHashMap::new));
    }

    public List<Pokemon> determineHighestInitiative(Pokemon poke1, Pokemon poke2) {
        LinkedList<Pokemon> resultList = new LinkedList<>();
        int comparisonResult = compareTo(poke1, poke2);
        if (comparisonResult == 0) {
            return resultList;
        } else if (comparisonResult == 1) {
            resultList.add(poke1);
            resultList.add(poke2);
        } else {
            resultList.add(poke2);
            resultList.add(poke1);
        }
        return resultList;
    }

    /**
     * Comparing order:
     *  Pokemon with higher speed attacks first.
     *  If speed1 == speed2, one with smaller weight attacks first.
     *  if weight1 == weight2, one with lower height attacks first.
     *  if height1 == height2, one with more abilities attacks first.
     *  if abilityCount1 == abilityCount2, one with more moves elements attacks first.
     *  if moveElementCount1 == moveElementCount2, one with higher base_experience attacks first.
     *  if base_experience1 == base_experience2, it is the same pokemon and no fighting will happen.
     * @param o the object to be compared.
     * @return int -1, 0, or 1
     */
    public int compareTo(Pokemon o, Pokemon p) {
        int attackSpeedComp = Integer.compare(o.getSpeed(), p.getSpeed());
        if (attackSpeedComp != 0) {
            return attackSpeedComp;
        }

        int weightComp = Integer.compare(o.getWeight(), p.getWeight());
        if (weightComp != 0) {
            if (weightComp < 0) {
                return 1;
            } else return -1;
        }

        int heightComparison = Integer.compare(o.getHeight(), p.getHeight());
        if (heightComparison != 0) {
            if (heightComparison < 0) {
                return 1;
            } else return -1;
        }

        int abilitiesCountComp = Integer.compare(o.getAbilities().size(), p.getAbilities().size());
        if (abilitiesCountComp != 0) {
            return abilitiesCountComp;
        }

        int movesCountComp = Integer.compare(o.getMoves().size(), p.getMoves().size());
        if (movesCountComp != 0) {
            return movesCountComp;
        }

        int baseExpComp = Integer.compare(o.getBaseExperience(), p.getBaseExperience());
        if (baseExpComp != 0) {
            return baseExpComp;
        }
        return 0;
    }
}
