package ee.taltech.iti0202.sportsclub.training;

import ee.taltech.iti0202.sportsclub.enums.Sports;
import ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels;
import ee.taltech.iti0202.sportsclub.enums.TrainingType;
import ee.taltech.iti0202.sportsclub.users.Trainer;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static ee.taltech.iti0202.sportsclub.enums.TrainingType.GROUP_TRAINING;

public class GroupTrainingSession extends TrainingSession {

    private static final int LOCAL_TIME_9 = 9;
    private static final int LOCAL_TIME_15 = 15;
    private LocalDateTime startTime;
    private final int maxParticipants = 25;

    @Override
    public Enum<TrainingType> getType() {
        return type;
    }

    private final Enum<TrainingType> type = GROUP_TRAINING;


    /**
     * Class constructor for a training session.
     *
     * @param name    of training session.
     * @param sport   training session is about.
     * @param trainer conducting the session.
     */
    public GroupTrainingSession(String name, Enum<Sports> sport, Optional<Trainer> trainer,
                                LocalDateTime startTime, Enum<TrainingSessionLevels> level) {
        super(name, sport, trainer);
        validateTime(startTime);
        setMaxParticipantsGroupTraining();
        setLevel(level);
    }

    private void setMaxParticipantsGroupTraining() {
        setMaxParticipants(maxParticipants);
    }

    private void setSessionStartTime(LocalDateTime strtTime) {
        startTime = strtTime;
    }

    private boolean validateTime(LocalDateTime startTime) {
        LocalTime startLocalTime = startTime.toLocalTime();
        if (startLocalTime.isBefore(LocalTime.of(LOCAL_TIME_9, 0))
                || !startLocalTime.isBefore(LocalTime.of(LOCAL_TIME_15, 0))) {
            throw new IllegalArgumentException("Start time must be between 09:00 and 15:00");
        }
        setSessionStartTime(startTime);
        //setSessionStartTime(LocalDateTime.from(LocalTime.from(startTime)));
        return true;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
