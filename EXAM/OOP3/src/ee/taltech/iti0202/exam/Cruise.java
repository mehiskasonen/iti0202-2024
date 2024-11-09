package ee.taltech.iti0202.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cruise {
    private final Integer capacity;

    private Integer currentCapacity = 0;
    private List<Session> sessionList = new ArrayList<>();

    public Cruise(Integer capacity) {
        this.capacity = capacity;
    }
    public Integer getCapacity() {
        return capacity;
    }

    public Integer getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(Integer currentCapacity) {
        this.currentCapacity = currentCapacity;
    }



}
