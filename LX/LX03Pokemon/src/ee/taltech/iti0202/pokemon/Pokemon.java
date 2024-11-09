package ee.taltech.iti0202.pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pokemon {
    private String name;
    private int attack;

    public int getBaseExperience() {
        return baseExperience;
    }

    private int baseExperience;
    private int hp;
    private double fightHp;
    private int defense;
    private int specialAttack;
    private int specialDefense;

    public int getSpeed() {
        return speed;
    }

    private int speed;

    public int getWeight() {
        return weight;
    }

    private int weight;

    public int getHeight() {
        return height;
    }

    private int height;
    private List<String> types = new ArrayList<>();

    public List<String> getMoves() {
        return moves;
    }

    private List<String> moves = new ArrayList<>();

    public List<String> getAbilities() {
        return abilities;
    }

    private List<String> abilities = new ArrayList<>();

    public String getName() {
        return name;
    }

    public int getAttack(int counter) {
        if (counter % 3 == 0) {
            return attack;
        } else return specialAttack;
    }

    public int getDefense(int counter) {
        //TODO Kui kaitsval pokemonil on mitu tüüpi, siis tuleb vastavad kordajad korrutada.
        if (counter % 2 == 0) {
            return defense / 2;
        }
        return specialDefense / 2;
    }

    public Double getAttackMultiplier(List<String> types) {
        List<String> pokemon1types = getTypes();
        double cumulativeMultiplier = 1.0;

        double highestMultiplier = 0;
        for (String attackerType : pokemon1types) {
            for (String defenderType : types) {
                double effectiveness = getEffectiveness(Updater.getAttackMultipliers(), Updater.getTypeIndexMap(), attackerType, defenderType);
                if (effectiveness > highestMultiplier) {
                    highestMultiplier = effectiveness;
                }
            }
            cumulativeMultiplier *= highestMultiplier;
        }
        return cumulativeMultiplier;
    }

    public List<String> getTypes() {
        return types;
    }

    public int getHp() {
        return hp;
    }
    public double getFightHp() {
        return fightHp;
    }

    public void setFightHp(double fightHp) {
        this.fightHp = fightHp;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                ", baseExperience=" + baseExperience +
                ", hp=" + hp +
                ", defense=" + defense +
                ", specialAttack=" + specialAttack +
                ", specialDefense=" + specialDefense +
                ", speed=" + speed +
                ", weight=" + weight +
                ", types=" + types +
                '}';
    }

    public Pokemon(String name, int attack, int defense, int hp, int specialAttack, int specialDefense,
                   int speed, List<String> types, List<String> moves, List<String> abilities,
                   int weight, int height, int baseExperience) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.hp = hp;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
        this.types = types;
        this.moves = moves;
        this.abilities = abilities;
        this.weight = weight;
        this.height = height;
        this.baseExperience = baseExperience;
        setFightHp(getHp());
    }

    private double getEffectiveness(double[][] typeChart, Map<String, Integer> typeIndexMap, String attackType, String defenseType) {
        Integer attackIndex = typeIndexMap.get(attackType);
        Integer defenseIndex = typeIndexMap.get(defenseType);
        if (attackIndex != null && defenseIndex != null) {
            return typeChart[attackIndex][defenseIndex];
        } else {
            throw new IllegalArgumentException("Invalid type name");
        }
    }

}
