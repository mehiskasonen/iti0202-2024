package ee.taltech.iti0202.json.student;

public class Grade {
    private int grade;
    private String assignment;

    /**
     * @param grade value of int.
     * @param assignment string.
     */
    public Grade(int grade, String assignment) {
        this.grade = grade;
        this.assignment = assignment;
    }

    /**
     * @return grade.
     */
    public int getGrade() {
        return grade;
    }

    /**
     * @return graded assignment.
     */
    public String getAssignment() {
        return assignment;
    }

}
