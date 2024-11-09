package ee.taltech.iti0202.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ee.taltech.iti0202.json.school.School;
import ee.taltech.iti0202.json.student.Grade;
import ee.taltech.iti0202.json.student.Student;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SchoolDatabase {

    /*** DO NOT CHANGE */
    private final List<School> schools = new ArrayList<>();

    /**
     * DO NOT CHANGE
     * @param school school to add
     */
    public void addSchool(School school) {
        this.schools.add(school);
    }

    /**
     * DO NOT CHANGE
     * @return schools in the db
     */
    public List<School> getSchools() {
        return this.schools;
    }
    /**
     * Get all students in all schools in the database
     * @return all students json, if it's empty, return empty json {}
     */
    public String getAllStudents() {
        List<Map<String, Object>> allStudents = new ArrayList<>();
        for (School school : schools) {
            for (Student student : school.getStudents()) {
                Map<String, Object> studentMap = new LinkedHashMap<>();
                studentMap.put("id", student.getId());
                studentMap.put("name", student.getName());
                studentMap.put("grades", student.getGrades());
                allStudents.add(studentMap);
            }
        }
        if (allStudents.isEmpty()) {
            return new JsonObject().toString();
        }
        return new Gson().toJson(allStudents);
    }

    /**
     * Get all students in specific school
     * @param school school's students to get
     * @return school's students json, if it's empty, return empty json {}
     */
    public String getAllStudents(School school) {
        List<Map<String, Object>> allStudentsInSchool = new ArrayList<>();
        if (!this.getSchools().contains(school) || school.getStudents().isEmpty()) {
            return new JsonObject().toString();
        }
        for (Student student : school.getStudents()) {
            Map<String, Object> studentMap = new LinkedHashMap<>();
            studentMap.put("id", student.getId());
            studentMap.put("name", student.getName());
            studentMap.put("grades", student.getGrades());
            allStudentsInSchool.add(studentMap);
        }
        return new Gson().toJson(allStudentsInSchool);
    }

    /**
     * Get student by id, check all schools that are in the database
     * @param id student's id
     * @return student class's json, if student is not found, return empty json {}
     */
    public String getStudent(int id) {
        List<Student> matchingStudents = new ArrayList<>();
        for (School school : schools) {
            for (Student student : school.getStudents()) {
                if (student.getId() == id) {
                    matchingStudents.add(student);
                }
            }
        }
        if (matchingStudents.isEmpty()) {
            return new JsonObject().toString();
        }
        if (matchingStudents.size() > 1) {
            return new Gson().toJson(matchingStudents);
        }
        return new Gson().toJson(matchingStudents.get(0));
    }

    /**
     * Get student's grades by id
     * @param id student's id
     * @return student's name with key "name", and array of grades (Grade class) with key "grades" in json,
     * if student is not found, return empty json {}
     */
    public String getStudentGrades(int id) {
        for (School school : schools) {
            for (Student student : school.getStudents()) {
                if (student.getId() == id) {
                    JsonObject studentObject = new JsonObject();
                    studentObject.addProperty("name", student.getName());
                    JsonArray gradesArray = new JsonArray();
                    for (Grade grade : student.getGrades()) {
                        JsonObject gradeObject = new JsonObject();
                        gradeObject.addProperty("grade", grade.getGrade());
                        gradeObject.addProperty("assignment", grade.getAssignment());
                        gradesArray.add(gradeObject);
                    }
                    studentObject.add("grades", gradesArray);
                    return new Gson().toJson(studentObject);
                }
            }
        }
        return new JsonObject().toString();
    }

    /**
     * Get student's average grade by id
     * @param id student's id
     * @return student's name with key "name", and average grade with key "averageGrade" in json,
     * if student is not found, return empty json {}
     */
    public String getStudentAverageGrade(int id) {
        for (School school : schools) {
            for (Student student : school.getStudents()) {
                if (student.getId() == id) {
                    JsonObject studentObject = new JsonObject();
                    studentObject.addProperty("name", student.getName());
                    double total = 0;
                    for (Grade grade : student.getGrades()) {
                        total = total + grade.getGrade();
                    }
                    total = total / student.getGrades().size();
                    DecimalFormat df = new DecimalFormat("#.###");
                    Double formattedGradeTotal = Double.valueOf(df.format(total));
                    studentObject.addProperty("averageGrade", formattedGradeTotal);
                    return new Gson().toJson(studentObject);
                }
            }
        }
        return new JsonObject().toString();
    }

    /**
     * Get average grade in each school in the database
     * @return json array of [{"school": "school's name", "averageGrade": averageGrade double}, ...],
     * if no schools are in the db, return empty json {}
     */
    public String getAverageGradeInEachSchool() {
        if (schools.isEmpty()) {
            return new JsonObject().toString();
        }
        JsonArray allSchoolsAverageGradesArray = new JsonArray();
        for (School school : schools) {
            JsonObject averageGradeObject = new JsonObject();
            averageGradeObject.addProperty("school", school.getName());
            double gradeTotal = 0.0;
            for (Student student : school.getStudents()) {
                String studentAverageGrade = getStudentAverageGrade(student.getId());
                JsonElement element = JsonParser.parseString(studentAverageGrade);
                JsonObject studentObject = element.getAsJsonObject();
                double averageGrade = studentObject.get("averageGrade").getAsDouble();
                gradeTotal += averageGrade;
            }
            gradeTotal = gradeTotal / school.getStudents().size();
            DecimalFormat df = new DecimalFormat("#.###");
            String formattedGradeTotal = df.format(gradeTotal);
            averageGradeObject.addProperty("averageGrade", Double.parseDouble(formattedGradeTotal));
            allSchoolsAverageGradesArray.add(averageGradeObject);
        }
        return new Gson().toJson(allSchoolsAverageGradesArray);
    }

    /**
     * Get average grade for each student in each school in the database
     * @return json array of
     * [{"school": "school's name",
     *      "grades": [{"student": "student's name","averageGrade": averageGrade double}]},
     *      ...],
     * if no schools are in the db, return empty json {}
     */
    public String getAllStudentsInEachSchoolAndTheirAverageGrades() {
        if (schools.isEmpty()) {
            return new JsonObject().toString();
        }
        List<Map<String, Object>> allSchoolsAverageGrades = new ArrayList<>();
        for (School school : schools) {
            Map<String, Object> schoolAverageGrades = new LinkedHashMap<>();
            schoolAverageGrades.put("school", school.getName());

            JsonArray studentsAndTheirAverage = new JsonArray();
            for (Student student : school.getStudents()) {
                JsonObject studentAverageGrade = new JsonObject();
                studentAverageGrade.addProperty("student", student.getName());
                String studentsAverageGrade = getStudentAverageGrade(student.getId());
                JsonElement gradeElement = JsonParser.parseString(studentsAverageGrade);
                JsonObject gradeObject = gradeElement.getAsJsonObject();
                double averageGrade = gradeObject.get("averageGrade").getAsDouble();
                studentAverageGrade.addProperty("averageGrade", averageGrade);
                studentsAndTheirAverage.add(studentAverageGrade);
            }
            schoolAverageGrades.put("grades", studentsAndTheirAverage);
            allSchoolsAverageGrades.add(schoolAverageGrades);
        }
        return new Gson().toJson(allSchoolsAverageGrades);
    }

    /**
     * Get all student's names in each school
     * @return json array of [{"school": "school's name", "students": ["student1", "student2", ...]}, ...],
     * if no schools are in the db, return empty json {}
     */
    public String getAllStudentsNamesInEachSchool() {
        if (schools.isEmpty()) {
            return new JsonObject().toString();
        }
        JsonArray allStudentsNames = new JsonArray();
        for (School school : schools) {
            JsonObject schoolAndStudentsObject = new JsonObject();
            schoolAndStudentsObject.addProperty("school", school.getName());
            JsonArray students = new JsonArray();
            for (Student student : school.getStudents()) {
                students.add(student.getName());
            }
            schoolAndStudentsObject.add("students", students);
            allStudentsNames.add(schoolAndStudentsObject);
        }
        return new Gson().toJson(allStudentsNames);
    }

    /**
     * Get average grade and all given grades count from all schools in the database
     * @return json of {"averageGrade": averageGradeDouble, "gradesTotal": gradesTotalInt}
     */
    public String getAverageGradeAndGradesCountGlobally() {
        List<Map<String, String>> averageGradeAndGradeCount = new ArrayList<>();
        double allSchoolsTotal = 0;
        int gradesTotal = 0;
        int allSchoolsAllStudentsGradesTotal = getGradesTotal();
        String averageGradesInEachSchool = getAverageGradeInEachSchool();  //add entries together
        JsonArray averageGradesArray = JsonParser.parseString(averageGradesInEachSchool).getAsJsonArray();

        for (JsonElement element : averageGradesArray) {
            JsonObject schoolAverageGrade = element.getAsJsonObject();
            double averageGrade = schoolAverageGrade.get("averageGrade").getAsDouble();
            allSchoolsTotal += averageGrade;
            gradesTotal++;
        }

        double globalAverageGrade = allSchoolsTotal / gradesTotal;
        JsonObject result = new JsonObject();
        result.addProperty("averageGrade", globalAverageGrade);
        result.addProperty("gradesTotal", allSchoolsAllStudentsGradesTotal);
        return new Gson().toJson(result);
    }

    /**
     * @return all schools all students nr of assigned grades.
     */
    public int getGradesTotal() {
        int gradesTotal = 0;
        for (School school : schools) {
            for (Student student : school.getStudents()) {
                String studentGrades = getStudentGrades(student.getId());
                JsonElement element = JsonParser.parseString(studentGrades);
                JsonObject obj = element.getAsJsonObject();
                JsonArray grades = obj.get("grades").getAsJsonArray();

                JsonArray studentGradesArray = JsonParser.parseString(String.valueOf(grades)).getAsJsonArray();
                for (JsonElement elem : studentGradesArray) {
                    JsonObject gradeObject = elem.getAsJsonObject();
                    String grade = gradeObject.get("grade").getAsString();
                    if (!Objects.equals(grade, "")) {
                        gradesTotal++;
                    }
                }
            }
        }
        return gradesTotal;
    }
}
