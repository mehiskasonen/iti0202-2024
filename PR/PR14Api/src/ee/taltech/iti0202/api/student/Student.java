package ee.taltech.iti0202.api.student;

import java.util.ArrayList;
import java.util.List;

public class Student {

    public static int nextId;
    private static int getAndIncrementNextId() {
        return ++nextId;
    }

    private final int id;
    private String name;
    private final List<Grade> grades = new ArrayList<>();

    /**
     * @param name of student to create.
     */
    public Student(String name) {
        this.id = getAndIncrementNextId();
        this.name = name;
    }

    /**
     * @param grade to add to grades.
     */
    public void addGrade(Grade grade) {
        this.grades.add(grade);
    }

    /**
     * @return name of student.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return id of student.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return list of grades of student.
     */
    public List<Grade> getGrades() {
        return this.grades;
    }

    /**
     * @param name to replace current name with.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name='" + name + '\'' + ", grades=" + grades + '}';
    }
}
