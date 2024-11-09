import ee.taltech.iti0202.json.SchoolDatabase;
import ee.taltech.iti0202.json.school.School;
import ee.taltech.iti0202.json.student.Grade;
import ee.taltech.iti0202.json.student.Student;

public class Main {
    public static void main(String[] args) {

        SchoolDatabase db = new SchoolDatabase();
        School school1 = new School("School1");
        School utopia = new School("Utopia University");
        School nonexistent = new School("Non Existent");

        Student goodStudent = new Student("Good Student");
        Student badStudent = new Student("Bad Student");
        Grade grade1 = new Grade(5, "Mathematics");
        Grade grade2 = new Grade(5, "Chemistry");
        Grade grade3 = new Grade(4, "History");
        Grade grade4 = new Grade(2, "Mathematics");
        Grade grade5 = new Grade(3, "Chemistry");
        Grade grade6 = new Grade(4, "History");
        goodStudent.addGrade(grade1);
        goodStudent.addGrade(grade2);
        goodStudent.addGrade(grade3);
        badStudent.addGrade(grade4);
        badStudent.addGrade(grade5);
        badStudent.addGrade(grade6);

//        System.out.println(db.getAllStudents()); // {}
        school1.addStudent(goodStudent);
        school1.addStudent(badStudent);
        db.addSchool(school1);
//        db.addSchool(utopia);

        /*System.out.println(db.getAllStudents()); // [{"name":"Good Student","id":1,"grades":[]},{"name":"Bad Student","id":2,"grades":[]}]

        System.out.println(db.getAllStudents(utopia)); // {}  - contains no students
        System.out.println(db.getAllStudents(nonexistent)); // {} - not in db
        System.out.println(db.getAllStudents(school1)); // [{"name":"Good Student","id":1,"grades":[]},{"name":"Bad Student","id":2,"grades":[]}]

        System.out.println(db.getAllStudentsInEachSchoolAndTheirAverageGrades());*/
//        System.out.println(db.getStudentAverageGrade(1)); //4.667
//        System.out.println(db.getAverageGradeInEachSchool());
        System.out.println(db.getAverageGradeAndGradesCountGlobally());
        // (good avg = 4.667 | bad avg = 3) / 2 = 3.833 OK
        //

        /*System.out.println(db.getStudent(1)); // {"name":"Good Student"
        System.out.println(db.getStudentGrades(1)); // {"name":"Good Student","grades":[{"grade":5,"assignment":"Mathematics"},{"grade":5,"assignment":"Chemistry"},{"grade":4,"assignment":"History"}]}
        System.out.println(db.getAverageGradeAndGradesCountGlobally());

        System.out.println("-----------------");
        System.out.println(db.getAllStudentsNamesInEachSchool());*/

        //testGetAllStudentsInJson
        //expected | but found
        // [[{"id":1,"name":"Ago Luberg","grades":[{"grade":4,"assignment":"hHuxz"},{"grade":2,"assignment":"ewCTj"},{"grade":2,"assignment":"AdI2N"},{"grade":2,"assignment":"iD6LJ"}]},{"id":2,"name":"Gert Tamm","grades":[{"grade":1,"assignment":"Va43F"},{"grade":2,"assignment":"69xke"},{"grade":2,"assignment":"1rc6s"},{"grade":4,"assignment":"lGsHd"}]},{"id":3,"name":"Mait Villem","grades":[{"grade":2,"assignment":"uPl0c"},{"grade":1,"assignment":"y2rlv"},{"grade":3,"assignment":"KiHQ7"},{"grade":1,"assignment":"vxnO1"}]},{"id":4,"name":"Ingo Kask","grades":[{"grade":3,"assignment":"Ctfig"},{"grade":1,"assignment":"ILQ8V"},{"grade":4,"assignment":"yYnV4"},{"grade":2,"assignment":"tSSYG"}]}]]
        // [[{"name":"Ago Luberg","id":1,"grades":[{"grade":4,"assignment":"hHuxz"},{"grade":2,"assignment":"ewCTj"},{"grade":2,"assignment":"AdI2N"},{"grade":2,"assignment":"iD6LJ"}]},{"name":"Gert Tamm","id":2,"grades":[{"grade":1,"assignment":"Va43F"},{"grade":2,"assignment":"69xke"},{"grade":2,"assignment":"1rc6s"},{"grade":4,"assignment":"lGsHd"}]},{"name":"Mait Villem","id":3,"grades":[{"grade":2,"assignment":"uPl0c"},{"grade":1,"assignment":"y2rlv"},{"grade":3,"assignment":"KiHQ7"},{"grade":1,"assignment":"vxnO1"}]},{"name":"Ingo Kask","id":4,"grades":[{"grade":3,"assignment":"Ctfig"},{"grade":1,"assignment":"ILQ8V"},{"grade":4,"assignment":"yYnV4"},{"grade":2,"assignment":"tSSYG"}]}]]

        //testGetStudentById
        //expected | but found
        // [{"id":1,"name":"Ago Luberg","grades":[{"grade":4,"assignment":"hHuxz"},{"grade":2,"assignment":"ewCTj"},{"grade":2,"assignment":"AdI2N"},{"grade":2,"assignment":"iD6LJ"}]}]
        // [[{"id":1,"name":"Ago Luberg","grades":[{"grade":4,"assignment":"hHuxz"},{"grade":2,"assignment":"ewCTj"},{"grade":2,"assignment":"AdI2N"},{"grade":2,"assignment":"iD6LJ"}]}]]

        //testGetAllStudentsInSpecificSchool
        //expected | but found
        // [[{"id":3,"name":"Mait Villem","grades":[{"grade":2,"assignment":"uPl0c"},{"grade":1,"assignment":"y2rlv"},{"grade":3,"assignment":"KiHQ7"},{"grade":1,"assignment":"vxnO1"}]},{"id":4,"name":"Ingo Kask","grades":[{"grade":3,"assignment":"Ctfig"},{"grade":1,"assignment":"ILQ8V"},{"grade":4,"assignment":"yYnV4"},{"grade":2,"assignment":"tSSYG"}]}]]
        // [[{"name":"Mait Villem","id":3,"grades":[{"grade":2,"assignment":"uPl0c"},{"grade":1,"assignment":"y2rlv"},{"grade":3,"assignment":"KiHQ7"},{"grade":1,"assignment":"vxnO1"}]},{"name":"Ingo Kask","id":4,"grades":[{"grade":3,"assignment":"Ctfig"},{"grade":1,"assignment":"ILQ8V"},{"grade":4,"assignment":"yYnV4"},{"grade":2,"assignment":"tSSYG"}]}]]


        //testGetAllStudentsInEachSchoolAndTheirAvgGrade
        //expected | but found
        // [[{"school":"TalTech","grades":[{"student":"Ago Luberg","averageGrade":3.25},{"student":"Gert Tamm","averageGrade":2.5}]},{"school":"Cambridge","grades":[{"student":"Mait Villem","averageGrade":3.0},{"student":"Ingo Kask","averageGrade":2.0}]}]]
        // [[{"school":"TalTech","grades":[{"student":"Ago Luberg","averageGrade":"{\"name\":\"Ago Luberg\",\"averageGrade\":3.25}"},{"student":"Gert Tamm","averageGrade":"{\"name\":\"Gert Tamm\",\"averageGrade\":2.5}"}]},{"school":"Cambridge","grades":[{"student":"Mait Villem","averageGrade":"{\"name\":\"Mait Villem\",\"averageGrade\":3.0}"},{"student":"Ingo Kask","averageGrade":"{\"name\":\"Ingo Kask\",\"averageGrade\":2.0}"}]}]]



        //testGetGlobalAverageGradeAndGradesCount
        //expected | but found
        // [{"averageGrade":2.25,"gradesTotal":16}]
        // [{"averageGrade":2.25,"gradesTotal":2}]

        //testGetAverageGradeInEachSchool
        //expected | but found
        // [[{"school":"TalTech","averageGrade":2.875},{"school":"Cambridge","averageGrade":2.5}]]
        // [[{"school":"TalTech","averageGrade":"2.875"},{"school":"Cambridge","averageGrade":"2.5"}]]

        //testGetAllStudentsNamesInEachSchool
        //expected | but found
        // [[{"school":"TalTech","students":["Ago Luberg","Gert Tamm"]},{"school":"Cambridge","students":["Mait Villem","Ingo Kask"]}]]
        // [[{"school":"TalTech","students":"[{\"id\":1,\"name\":\"Ago Luberg\",\"grades\":[{\"grade\":4,\"assignment\":\"V5Ufg\"},{\"grade\":1,\"assignment\":\"l8eaq\"},{\"grade\":4,\"assignment\":\"n0SNH\"},{\"grade\":4,\"assignment\":\"HQpk4\"}]},{\"id\":2,\"name\":\"Gert Tamm\",\"grades\":[{\"grade\":4,\"assignment\":\"vJ3Kx\"},{\"grade\":3,\"assignment\":\"CqCAU\"},{\"grade\":2,\"assignment\":\"ECBUN\"},{\"grade\":1,\"assignment\":\"JtC24\"}]}]"},{"school":"Cambridge","students":"[{\"id\":3,\"name\":\"Mait Villem\",\"grades\":[{\"grade\":3,\"assignment\":\"6CWkb\"},{\"grade\":4,\"assignment\":\"Tt8jj\"},{\"grade\":4,\"assignment\":\"BH6g2\"},{\"grade\":1,\"assignment\":\"I2cej\"}]},{\"id\":4,\"name\":\"Ingo Kask\",\"grades\":[{\"grade\":2,\"assignment\":\"RN1wi\"},{\"grade\":4,\"assignment\":\"OiXIa\"},{\"grade\":1,\"assignment\":\"iM9Le\"},{\"grade\":1,\"assignment\":\"bfHjn\"}]}]"}]]

        //testGetGlobalAverageGradeAndGradesCount
        //expected | but found
        // [{"averageGrade":2.9375,"gradesTotal":16}]
        // [{"averageGrade":2.9375,"gradesTotal":2}]
    }

}