package ee.taltech.iti0202.api.student;

public class Grade {
    private int grade;
    private String assignment;

    @Override
    public String toString() {
        return "Grade{" + "grade=" + grade + ", assignment='" + assignment + '\'' + '}';
    }

    /**
     * @param grade value from -int to MAX of int?
     * @param assignment grade is about.
     */
    public Grade(int grade, String assignment) {
        this.grade = grade;
        this.assignment = assignment;
    }

    /**
     * @return grade object.
     */
    public int getGrade() {
        return grade;
    }

    /**
     * @return assignment as string.
     */
    public String getAssignment() {
        return assignment;
    }

}
