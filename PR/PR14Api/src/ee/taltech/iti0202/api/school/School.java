package ee.taltech.iti0202.api.school;
import ee.taltech.iti0202.api.student.Student;

import java.util.ArrayList;
import java.util.List;

public class School {

    private final List<Student> students = new ArrayList<>();
    private final String name;

    /**
     * @param name of school.
     */
    public School(String name) {
        this.name = name;
    }

    /**
     * @param student to add.
     */
    public void addStudent(Student student) {
        this.students.add(student);
    }

    /**
     * @param student to remove.
     */
    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    /**
     * @return students in school.
     */
    public List<Student> getStudents() {
        return this.students;
    }

    /**
     * @return name of school.
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "School{" + "students=" + students + ", name='" + name + '\'' + '}';
    }
}
