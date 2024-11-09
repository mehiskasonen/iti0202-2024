package ee.taltech.iti0202.sportsclub.training;

import ee.taltech.iti0202.sportsclub.enums.Sports;
import ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels;
import ee.taltech.iti0202.sportsclub.enums.TrainingType;
import ee.taltech.iti0202.sportsclub.users.Trainer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static ee.taltech.iti0202.sportsclub.enums.TrainingType.PERSONAL_TRAINING;

public class PersonalTrainingSession extends TrainingSession {

    private static final double DEFAULT_PRICE_30 = 30;
    private static final int LOCAL_TIME_14 = 14;
    private static final int LOCAL_TIME_18 = 18;
    private final int maxParticipants = 1;
    private LocalDateTime startTime;

    private BigDecimal defaultPrice = BigDecimal.valueOf(DEFAULT_PRICE_30);

    @Override
    public Enum<TrainingType> getType() {
        return type;
    }

    private final Enum<TrainingType> type = PERSONAL_TRAINING;
    /**
     * Class constructor for a training session.
     * There is no price, it is contained in members fee.
     *
     * @param name    of training session.
     * @param sport   training session is about.
     * @param trainer conducting the session.
     */
    public PersonalTrainingSession(String name, Enum<Sports> sport, Optional<Trainer> trainer,
                                   LocalDateTime startTime, Enum<TrainingSessionLevels> level) {
        super(name, sport, trainer);
        validateTime(startTime);
        setMaxParticipantsPersonalTraining();
        setPrice(defaultPrice);
        setLevel(level);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public BigDecimal getDefaultPrice() {
        return defaultPrice;
    }

    private void setSessionStartTime(LocalDateTime strtTime) {
        startTime = strtTime;
    }

    private void setPrice(BigDecimal dfltPrice) {
        defaultPrice = dfltPrice;
    }

    private void setMaxParticipantsPersonalTraining() {
        setMaxParticipants(maxParticipants);
    }

    private boolean validateTime(LocalDateTime startTime) {
        LocalTime startLocalTime = startTime.toLocalTime();
        if (startLocalTime.isBefore(LocalTime.of(LOCAL_TIME_14, 0))
                || !startLocalTime.isBefore(LocalTime.of(LOCAL_TIME_18, 0))) {
            throw new IllegalArgumentException("Start time must be between 14:00 and 18:00");
        }
        setSessionStartTime(startTime);
        //setSessionStartTime(LocalDateTime.from(LocalTime.from(startTime)));
        return true;
    }

}
