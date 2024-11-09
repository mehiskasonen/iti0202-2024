package ee.taltech.iti0202.json.student;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private static int nextId;
    private static int getAndIncrementNextId() {
        return ++nextId;
    }

    private final int id;
    private final String name;
    private final List<Grade> grades = new ArrayList<>();

    /**
     * Student class constructor.
     * @param name of student.
     */
    public Student(String name) {
        this.id = getAndIncrementNextId();
        this.name = name;
    }

    /**
     * @param grade to add to students grades.
     */
    public void addGrade(Grade grade) {
        this.grades.add(grade);
    }

    /**
     * @return name of a student.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return id of a student.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return students grades.
     */
    public List<Grade> getGrades() {
        return this.grades;
    }
}
