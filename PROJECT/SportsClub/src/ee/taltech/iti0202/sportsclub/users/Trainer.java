package ee.taltech.iti0202.sportsclub.users;

import ee.taltech.iti0202.sportsclub.enums.Sports;

import java.util.List;

/**
 * Represents a trainer in the sports club system.
 */
public class Trainer extends User {

    private final List<Enum<Sports>> trainsSports;

    /**
     * Constructs a new trainer with the given name and list of sports they train in.
     *
     * @param name         The name of the trainer.
     * @param trainsSports The list of sports the trainer can provide sessions for.
     */
    public Trainer(String name, List<Enum<Sports>> trainsSports) {
        super(name);
        this.trainsSports = trainsSports;
    }

    /**
     * Retrieves the list of sports the trainer can provide sessions for.
     *
     * @return The list of sports the trainer is qualified to train in.
     */
    public List<Enum<Sports>> getTrainsSports() {
        return trainsSports;
    }

    /**
     * @return count of specializations trainer has.
     */
    public int getSpecializationCount() {
        return getTrainsSports().size();
    }

    @Override
    public String toString() {
        return "Trainer{" + getName();
    }
}
