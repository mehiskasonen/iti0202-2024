import ee.taltech.iti0202.location.Location;
import ee.taltech.iti0202.schools.PrimarySchool;
import ee.taltech.iti0202.schools.School;
import ee.taltech.iti0202.schools.SecondarySchool;
import ee.taltech.iti0202.schools.University;
import ee.taltech.iti0202.student.Student;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Student student1 = new Student("student1");
        Student student2 = new Student("student2");
        Student student3 = new Student("student3");
        Student student4 = new Student("student4");

        Location tallinn = new Location("Estonia", "Tallinn");
        Location helsinki = new Location("Finland", "Helsinki");
        University taltech = new University("Taltech", tallinn);
        University a = new University("a", helsinki);
        University TTKK = new University("Tallinna Tehnikakõrgkkool", tallinn);

        PrimarySchool primSchool = new PrimarySchool("Hiiu kool", tallinn);
        SecondarySchool secSchool = new SecondarySchool("Kalamaja Põhikool", tallinn);

        System.out.println(taltech.compareTo(primSchool));

        List<School> listToCompare = new ArrayList<>();
        listToCompare.add(TTKK);
        listToCompare.add(taltech);

        taltech.addStudent(student1);
        taltech.addStudent(student2);
        a.addStudent(student1);
        a.addStudent(student2);
        TTKK.addStudent(student3);

        //System.out.println(taltech.compareTo(TTKK));
        System.out.println(taltech.compareTo(a));




    }
}