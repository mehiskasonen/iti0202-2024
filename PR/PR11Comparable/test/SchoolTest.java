import ee.taltech.iti0202.location.Location;
import ee.taltech.iti0202.schools.PrimarySchool;
import ee.taltech.iti0202.schools.School;
import ee.taltech.iti0202.schools.University;
import ee.taltech.iti0202.student.Student;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SchoolTest {

    @Test
    void compareTo() {
    }

    @Test
    void testSameCityDifferentSchoolName() {
        Location tallinn = new Location("Estonia", "Tallinn");
        School schoolA = new PrimarySchool("A", tallinn);
        School schoolB = new PrimarySchool("B", tallinn);

        List<School> sorted = School.getSchools();
        assertEquals(Arrays.asList(schoolA, schoolB), sorted);
    }

    @Test
    void testSameCountryDifferentCity() {
        Location tallinn = new Location("Estonia", "Tallinn");
        Location tartu = new Location("Estonia", "Tartu");
        School schoolB = new PrimarySchool("B", tallinn);
        School schoolA = new PrimarySchool("A", tartu);

        List<School> sorted = School.getSchools();
        assertEquals(Arrays.asList(schoolB, schoolA), sorted);
    }

    @Test
    void testDifferentSchoolClasses() {
        Location tallinn = new Location("Estonia", "Tallinn");
        Location helsinki = new Location("Finland", "Helsinki");
        School schoolUni = new University("Uni", tallinn);
        School schoolPri = new PrimarySchool("Pri", helsinki);

        List<School> sorted = School.getSchools();
        assertEquals(schoolUni, sorted.get(0));
        assertEquals(Arrays.asList(schoolUni, schoolPri), sorted);
    }

    @Test
    void testSameClassDifferentStudentAmount() {
        Location tallinn = new Location("Estonia", "Tallinn");
        Location tartu = new Location("Estonia", "Tartu");
        School schoolUni = new University("Uni", tallinn);
        School schoolUni2 = new University("Uni2", tartu);

        schoolUni.addStudent(new Student("John"));
        schoolUni2.addStudent(new Student("Alice"));
        schoolUni2.addStudent(new Student("Marcus"));

        List<School> sorted = School.getSchools();
        assertEquals(Arrays.asList(schoolUni2, schoolUni), sorted);
    }

    @Test
    void testCorrectOrderPriority() {
        Location tallinn = new Location("Estonia", "Tallinn");
        Location helsinki = new Location("Finland", "Helsinki");
        School schoolUni = new University("Uni", tallinn);
        School schoolA = new PrimarySchool("a", helsinki);

        List<School> sorted = School.getSchools();
        assertEquals(Arrays.asList(schoolUni, schoolA), sorted);
    }

    @Test
    void testSameStudentAmountDifferentCountry() {
        Location tallinn = new Location("Estonia", "Tallinn");
        Location helsinki = new Location("Finland", "Helsinki");
        School schoolB = new PrimarySchool("B", tallinn);
        School schoolA = new PrimarySchool("A", helsinki);

        schoolA.addStudent(new Student("John"));
        schoolB.addStudent(new Student("Alice"));

        List<School> sorted = School.getSchools();
        assertEquals(Arrays.asList(schoolB, schoolA), sorted);
    }


}