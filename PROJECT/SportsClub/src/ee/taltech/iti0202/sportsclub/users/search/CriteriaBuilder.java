package ee.taltech.iti0202.sportsclub.users.search;

import ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels;
import ee.taltech.iti0202.sportsclub.enums.TrainingType;
import ee.taltech.iti0202.sportsclub.training.TrainingSession;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * CriteriaBuilder class is responsible for building search criteria for training sessions.
 * Users can specify the level, start time, and type of training session they are interested in.
 */
public class CriteriaBuilder {
    private TrainingSessionLevels level;
    private LocalTime startTime;
    private TrainingType type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<DayOfWeek> daysOfWeek;

    /**
     * CriteriaBuilder class constructor.
     * Initialises day of week as a hash set.
     */
    public CriteriaBuilder() {
        this.daysOfWeek = new HashSet<>();
    }

    /**
     * Sets the level criteria for the training session.
     *
     * @param level The desired level of the training session.
     * @return This CriteriaBuilder object with the level criteria set.
     */
    public CriteriaBuilder level(TrainingSessionLevels level) {
        this.level = level;
        return this;
    }

    /**
     * Sets the start time criteria for the training session.
     *
     * @param startTime The desired start time of the training session.
     * @return This CriteriaBuilder object with the start time criteria set.
     */
    public CriteriaBuilder startTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Sets the type criteria for the training session.
     *
     * @param type The desired type of the training session.
     * @return This CriteriaBuilder object with the type criteria set.
     */
    public CriteriaBuilder type(TrainingType type) {
        this.type = type;
        return this;
    }

    /**
     * Adds a day of the week criteria for the training session.
     *
     * @param day The desired day of the week for the training session.
     * @return This CriteriaBuilder object with the day of the week criteria added.
     */
    public CriteriaBuilder dayOfWeek(DayOfWeek day) {
        this.daysOfWeek.add(day);
        return this;
    }

    /**
     * Sets the date range criteria for the training session.
     *
     * @param startDate The start date of the desired date range.
     * @param endDate   The end date of the desired date range.
     * @return This CriteriaBuilder object with the date range criteria set.
     */
    public CriteriaBuilder dateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start is after end");
        }
        this.startDate = startDate;
        this.endDate = endDate;
        return this;
    }

    /**
     * Checks if the given training session meets the specified criteria.
     *
     * @param session The training session to be checked against the criteria.
     * @return True if the training session meets all specified criteria, otherwise false.
     */
    public boolean meetsCriteria(TrainingSession session) {
        boolean meetsCriteria = true;

        if (level != null && session.getLevel() != level) {
            meetsCriteria = false;
        }
        if (startTime != null && !session.getStartTime().toLocalTime().equals(startTime)) {
            meetsCriteria = false;
        }
        if (type != null && session.getType() != type) {
            meetsCriteria = false;
        }
        if (!daysOfWeek.isEmpty() && !daysOfWeek.contains(session.getStartTime().getDayOfWeek())) {
            meetsCriteria = false;
        }
        if (startDate != null && endDate != null && (session.getStartTime().toLocalDate().isBefore(startDate)
        || session.getStartTime().toLocalDate().isAfter(endDate))) {
            meetsCriteria = false;
        }
        return meetsCriteria;
    }

}
