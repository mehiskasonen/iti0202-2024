package ee.taltech.iti0202.schools;

import ee.taltech.iti0202.location.Location;
import ee.taltech.iti0202.student.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class School implements Comparable<School> {

    private final String name;
    private final Location location;
    static List<School> schools = new ArrayList<>();
    private List<Student> students = new ArrayList<>();

    /**
     * Construct a new school with a name and Location.
     * @param name name of school
     * @param location Location of school
     */
    protected School(String name, Location location) {
        this.name = name;
        this.location = location;
        addSchool(this);
    }

    /**
     * Adds student to list of students
     * @param student Student
     */
    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    /**
     * Returns name of school
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns location of school
     * @return Location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns List of students in school
     * @return List of students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * @return amount of students in the list.
     */
    public int getStudentsAmount() {
        return getStudents().size();
    }

    /**
     * Comparing order:
     *  1. By class
     *  2. By amount of student
     *  3. By country name
     *  4. By city name
     *  5. By school name
     * @param o the object to be compared.
     * @return int -1, 0, or 1
     */
    @Override
    public int compareTo(School o) {
        int schoolClassComparison = this.compareByClass(o);
        if (schoolClassComparison != 0) {
            return schoolClassComparison;
        }

        int studentsAmountComparison = Integer.compare(o.getStudentsAmount(), this.getStudentsAmount());
        if (studentsAmountComparison != 0) {
            return studentsAmountComparison;
        }

        int countryNameComparison = this.getLocation().country().compareTo(o.getLocation().country());
        if (countryNameComparison != 0) {
            return countryNameComparison;
        }

        int cityNameComparison = this.getLocation().city().compareTo(o.getLocation().city());
        if (cityNameComparison != 0) {
            return cityNameComparison;
        }

        return this.getName().compareTo(o.getName());
    }

    /**
     * @param o school to compare to.
     * @return 1 (t > o), 0 (t = o), -1 (t < o)
     */
    public int compareByClass(School o) {
        List<Class<? extends School>> schoolTypes = Arrays.asList(
                School.class,
                University.class,
                SecondarySchool.class,
                PrimarySchool.class);
        int typeComparison = schoolTypes.indexOf(this.getClass()) - schoolTypes.indexOf(o.getClass());
        if (typeComparison != 0) {
            if (typeComparison < 0) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Adds given school to a list containing all Schools.
     * Does not add it to list if it's already added.
     * @param school School
     */
    public static void addSchool(School school) {
        if (!schools.contains(school)) {
            schools.add(school);
        }
    }

    /**
     * Clears list containing all schools
     */
    public static void clearSchools() {
        schools.clear();
    }

    /**
     * Returns sorted List of all schools.
     * @return sorted list of schools
     */
    public static List<School> getSchools() {
        schools.sort(School::compareTo);
        return schools;
    }

    @Override
    public String toString() {
        return "School{"
                + "name='" + name + '\''
                + ", " + location + '}';
    }
}
