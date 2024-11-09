package ee.taltech.iti0202.pokemon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Updater {
    private static double[][] attackMultipliers;
    private static final Map<String, Integer> typeIndexMap = new HashMap<>();

    public static double[][] getAttackMultipliers() {
        return attackMultipliers;
    }

    public static Map<String, Integer> getTypeIndexMap() {
        return typeIndexMap;
    }

    public String updatePokemonData(int id) {
        return updatePokemonData(id, false);
    }

    public String updatePokemonData(int id, boolean force) {
        String fileName = "poke" + id + ".json";
        if (!force && Files.exists(Path.of(fileName))) {
            //TODO read file contents and return contents
        } else {
            //TODO read poke data from web
            //TODO write data to file
            //TODO return data
        }
    return "";
    }

    public void readAttackMultipliers() throws FileNotFoundException {
        FileReader fr = new FileReader("attack multipliers.txt");
        Scanner sc = new Scanner(fr);
        int dimentsion = 0;

        if (sc.hasNextLine()) {
            String headerLine = sc.nextLine();
            String[] typeValues = headerLine.trim().split("\\s+");
            dimentsion = (int) Arrays.stream(typeValues).sequential().count();

            attackMultipliers = new double[dimentsion][dimentsion];

            // Mapping each type to it's value
            for (int i = 0; i < dimentsion; i++) {
                typeIndexMap.put(typeValues[i], i);
            }

            int rowIndex = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] values = line.trim().split("\\s+");

                // Convert line to double array
                double[] row = new double[values.length - 1];
                for (int i = 1; i < values.length; i++) {
                    row[i - 1] = Double.parseDouble(values[i]);
                }
                attackMultipliers[rowIndex] = row;
                rowIndex++;
        }

            /*// Print array
            for (int i = 0; i < dimentsion; i++) {
                for (int j = 0; j < dimentsion; j++) {
                    System.out.print(attackMultipliers[i][j] + " ");
                }
                System.out.println();
            }*/

        }

    }
}
