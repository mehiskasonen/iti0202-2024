package ee.taltech.iti0202.sportsclub.training;

import ee.taltech.iti0202.sportsclub.enums.Sports;
import ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels;
import ee.taltech.iti0202.sportsclub.enums.TrainingType;
import ee.taltech.iti0202.sportsclub.exceptions.AssignTrainerException;
import ee.taltech.iti0202.sportsclub.users.Member;
import ee.taltech.iti0202.sportsclub.users.Trainer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TrainingSession {

    private List<Member> members = new ArrayList<>();
    private String name;
    private int maxParticipants;
    private Optional<Trainer> trainer;
    private Enum<Sports> sport;
    private final List<LocalDateTime> scheduledAt = new ArrayList<>();

    private Enum<TrainingType> type;

    private Enum<TrainingSessionLevels> level;

    private LocalDateTime startTime;


    /**
     * Class constructor for a training session.
     *
     * @param name of training session.
     * @param sport training session is about.
     * @param trainer conducting the session.
     */
    public TrainingSession(String name, Enum<Sports> sport, Optional<Trainer> trainer) {
        this.name = name;
        this.sport = sport;
        this.trainer = trainer;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public Sports getSport() {
        return (Sports) sport;
    }

    public Enum<TrainingType> getType() {
        return this.type;
    }

    public Enum<TrainingSessionLevels> getLevel() {
        return level;
    }

    public void setLevel(Enum<TrainingSessionLevels> level) {
        this.level = level;
    }

    /**
     * For testing purposes only.
     * Setter for members.
     *
     * @param members to be set to session.
     */
    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    /**
     * Retrieves the maximum number of participants allowed for this training session.
     * @return The maximum number of participants allowed.
     */
    public int getMaxParticipants() {
        return maxParticipants;
    }

    /**
     * @return trainer of training session.
     */
    public Optional<Trainer> getTrainer() {
        return trainer;
    }

    /**
     * Sets the trainer leading the training session.
     *
     * @param trainer The trainer leading the training session.
     */
    public void setTrainerToSession(Optional<Trainer> trainer) {
        this.trainer = trainer;
    }

    /**
     * Checks if the given trainer can be assigned to the training session.
     * Trainer can only be assigned if training session already does not have a trainer assigned
     * and if trainer is proficient in the given sport training session is about.
     *
     * @param trainer The trainer to check.
     * @return True if the trainer can be assigned, false otherwise.
     */
    public boolean canAssignTrainer(Trainer trainer) throws AssignTrainerException {
        if (this.trainer.isPresent()) {
            throw new AssignTrainerException(AssignTrainerException.AssignTrainerReason.TRAINER_ALREADY_ASSIGNED);
        } else {
            if (!trainer.getTrainsSports().contains(this.sport)) {
                throw new AssignTrainerException(AssignTrainerException.AssignTrainerReason.TRAINER_NOT_QUALIFIED);
            }
        }
        return true;
    }

    public List<Member> getParticipants() {
        return this.members;
    }

    public int getParticipantsCount() {
        return getParticipants().size();
    }

    /**
     * Returns a string representation of this TrainingSession object.
     *
     * @return A string containing information about the training session, including its members,
     * maximum number of participants, schedule, trainer, and sport.
     */
    @Override
    public String toString() {
        return "TrainingSession{"
                + "Name=" + getName()
                + " members=" + members + ", maxParticipants=" + getMaxParticipants()
                + ", schedule=" + scheduledAt + ", trainer=" + trainer
                + ", sport=" + sport + '}';
    }


    /**
     * @param t1 training session to compare to t2.
     * @param t2 training session to compare to t1.
     * @return 1 (t1 > t2), 0 (t1 = t2), -1 (t1 < t2)
     */
    public static  int compareByTrainingSessionLevel(TrainingSession t1, TrainingSession t2) {
        List<TrainingSessionLevels> levels = Arrays.asList(
                TrainingSessionLevels.BEGINNER,
                TrainingSessionLevels.INTERMEDIATE,
                TrainingSessionLevels.ADVANCED
        );
        int levelComparison = levels.indexOf(t1.getLevel()) - levels.indexOf(t2.getLevel());
        if (levelComparison != 0) {
            if (levelComparison < 0) {
                return -1;
            } else return 1;
        }
        return 0;
    }

}
