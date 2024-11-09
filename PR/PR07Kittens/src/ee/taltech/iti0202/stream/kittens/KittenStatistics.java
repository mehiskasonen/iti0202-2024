package ee.taltech.iti0202.stream.kittens;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class KittenStatistics {

    private List<Kitten> kittens;

/*    public KittenStatistics(List<Kitten> kittens) {
        this.kittens = kittens;
    }*/

    public void setKittens(final List<Kitten> kittens) {
        this.kittens = kittens;
    }

    /**
     * @return average age of all kittens.
     */
    public OptionalDouble findKittensAverageAge() {
        return kittens.stream()
                .mapToDouble(Kitten::getAge)
                .average();
    }

    /**
     * @return the oldest kitten.
     */
    public Optional<Kitten> findOldestKitten() {
        return kittens.stream()
                .max(Comparator.comparing(Kitten::getAge));
    }

    /**
     * @return the youngest kittens.
     */
    public List<Kitten> findYoungestKittens() {
        int minAge = kittens.stream()
                .mapToInt(Kitten::getAge)
                .min()
                .orElse(Integer.MAX_VALUE);

        return kittens.stream()
                .filter(kitten -> kitten.getAge() == minAge)
                .collect(Collectors.toList());
    }

    /**
     * @param gender of kittens to find.
     * @return list of kittens of given gender.
     */
    public List<Kitten> findKittensAccordingToGender(final Kitten.Gender gender) {
        return kittens.stream()
                .filter(kitten -> kitten.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    /**
     * @param minAge searchable kitten can be.
     * @param maxAge searchable kitten can be.
     * @return list of kittens between the ages of.
     */
    public List<Kitten> findKittensBetweenAges(final int minAge, final int maxAge) {
        return kittens.stream()
                .filter(kitten -> kitten.getAge() >= minAge && kitten.getAge() <= maxAge)
                .collect(Collectors.toList());
    }

    /**
     * @param givenName of kitten to search.
     * @return first Optional of kitten matching the search name.
     */
    public Optional<Kitten> findFirstKittenWithGivenName(final String givenName) {
        return kittens.stream()
                /*.map(kitten -> kitten.getName().equals(givenName)
                        ? Optional.of(kitten) : Optional.<Kitten>empty())
                .flatMap(Optional::stream)*/
                .filter(kitten -> kitten.getName().equalsIgnoreCase(givenName))
                .findFirst();
    }

    /**
     * @return sorted list of kittens with youngest first.
     */
    public List<Kitten> kittensSortedByAgeYoungerFirst() {
        return kittens.stream()
                .sorted(Comparator.comparing(Kitten::getAge))
                .collect(Collectors.toList());
    }

    /**
     * @return sorted list of kittens with oldest first.
     */
    public List<Kitten> kittensSortedByAgeOlderFirst() {
        return kittens.stream()
                .sorted(Comparator.comparing(Kitten::getAge, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
