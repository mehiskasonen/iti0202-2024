package ee.taltech.iti0202.sportsclub.training;

import ee.taltech.iti0202.sportsclub.enums.Sports;
import ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels;
import ee.taltech.iti0202.sportsclub.enums.TrainingType;
import ee.taltech.iti0202.sportsclub.users.Trainer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static ee.taltech.iti0202.sportsclub.enums.TrainingType.WEB_TRAINING;

public class WebTrainingSession extends TrainingSession {

    private BigDecimal defaultPrice = BigDecimal.valueOf(5);
    private LocalDateTime startTime;

    @Override
    public Enum<TrainingType> getType() {
        return type;
    }

    private final Enum<TrainingType> type = WEB_TRAINING;

    /**
     * Class constructor for a training session.
     *
     * @param name    of training session.
     * @param sport   training session is about.
     * @param trainer conducting the session.
     */
    public WebTrainingSession(String name, Enum<Sports> sport, Optional<Trainer> trainer,
                              LocalDateTime startTime, Enum<TrainingSessionLevels> level) {
        super(name, sport, trainer);
        setPrice(defaultPrice);
        setSessionStartTime(startTime);
        setLevel(level);
    }

    private void setPrice(BigDecimal dfltPrice) {
        defaultPrice = dfltPrice;
    }

    private void setSessionStartTime(LocalDateTime strtTime) {
        startTime = strtTime;
        //startTime = LocalDateTime.from(LocalTime.from(strtTime));
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public BigDecimal getDefaultPrice() {
        return defaultPrice;
    }
}
