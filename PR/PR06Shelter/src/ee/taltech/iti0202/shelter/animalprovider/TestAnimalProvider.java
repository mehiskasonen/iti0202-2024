package ee.taltech.iti0202.shelter.animalprovider;

import ee.taltech.iti0202.shelter.animal.Animal;

import java.util.List;

public class TestAnimalProvider implements AnimalProvider {
    private int pageSize = 10;
    private int start = 0;
    private List<Animal> animals;

    /**
     * Constructor for testing AnimalProvider interface.
     * @param animals
     * @param pageSize
     */
    public TestAnimalProvider(List<Animal> animals, int pageSize) {
        this.animals = animals;
        this.pageSize = pageSize;
    }
    /**
     *
     * @param type Which animals are asked.
     * @return list of animal objects.
     */
    @Override
    public List<Animal> provide(Animal.Type type) {
        List<Animal> result = animals.subList(start, start + pageSize);
        start += pageSize;
        return result;
        //return List.of(new Wombat("blue"), new Wombat("red"), new Wombat("white"));
    }
}
