package ee.taltech.iti0202.sportsclub.training;

import ee.taltech.iti0202.sportsclub.enums.Sports;
import ee.taltech.iti0202.sportsclub.users.Trainer;

import java.util.ArrayList;
import java.util.List;

public class Training {
    private final String name;
    private final Enum<Sports> sport;
    private final Trainer trainer;
    private List<TrainingSession> trainingSessionList = new ArrayList<>();

    @Override
    public String toString() {
        return "Training{"
                + "name='" + name + '\'' + ", sport=" + sport + ", trainer=" + trainer
                + ", trainingSessionList=" + trainingSessionList + '}';
    }

    /**
     * Training class constructor.
     * @param name of training.
     * @param sport the training is about.
     * @param trainer for training.
     */
    public Training(String name, Enum<Sports> sport, Trainer trainer) {
        this.name = name;
        this.sport = sport;
        this.trainer = trainer;
    }

    /**
     * @return name of training.
     */
    public String getName() {
        return name;
    }

    /**
     * @return trainer of training.
     */
    public Trainer getTrainer() {
        return trainer;
    }

    /**
     * @return list of trainings all sessions.
     */
    public List<TrainingSession> getTrainingSessionList() {
        return trainingSessionList;
    }

    /**
     * @param trainingSession to be added to training.
     * @return true if adding was successful, else return false.
     */
    public boolean addTrainingSession(TrainingSession trainingSession) {
        if (!trainingSessionList.contains(trainingSession)) {
            trainingSessionList.add(trainingSession);
            return true;
        }
        return false;
    }

}
