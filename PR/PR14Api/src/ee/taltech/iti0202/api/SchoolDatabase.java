package ee.taltech.iti0202.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import ee.taltech.iti0202.api.school.School;
import ee.taltech.iti0202.api.student.Grade;
import ee.taltech.iti0202.api.student.Student;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SchoolDatabase {

    //DO NOT CHANGE
    public List<School> schools = new ArrayList<>();

    /**
     * @param jsonContent string of db content.
     */
    public SchoolDatabase(String jsonContent) {
        //DO NOT CHANGE
        loadDatabase(jsonContent);
    }

    /**
     * For debugging.
     * @return list of schools in db
     */
    public List<School> getSchools() {
        return this.schools;
    }

    /**
     * @param jsonContent string of db content.
     */
    public void loadDatabase(String jsonContent) {
        Gson gson = new Gson();
        Type schoolListType = new TypeToken<List<School>>() { } .getType();
        List<School> parsed = gson.fromJson(jsonContent, schoolListType);

        int studentCount = 0;
        for (School school : parsed) {
            schools.add(school);
            for (Student student : school.getStudents()) {
                if (student.getId() > studentCount) {
                    studentCount++;
                }
            }
        }
        Student.nextId = studentCount;
    }

    /**
     * Endpoints (note, all results should be in json except the 404)
     * - /student/grades?studentId=studentId - get student's grades by id (return json array of grade classes)
     * - /school/students?schoolName=schoolName - get all students in school
     *      by schoolNam (return all fields except grades in Student class)
     *  - /schools - return all school names (return json array of just school's names)
     *  If school name or student's id doesn't exist, return string 404
     * @param path - endpoint path
     * @return - result, if there is no result, return 404 in string
     */
    public String get(String path) {
        JsonArray resultArray = new JsonArray();

        if (Objects.equals(path, "/schools")) {
            JsonArray schoolNames = new JsonArray();
            for (School school : schools) {
                schoolNames.add(school.getName());
            }
            return schoolNames.toString();
        }

        Map<String, String> parameters = parseParameters(path);
        String endpoint = extractEndpoint(path);

        switch (endpoint) {
            case "/student/grades":
                String studentId = parameters.get("studentId");
                if (validateStudentId(studentId) != -1) {
                    int schoolIndex = validateStudentId(studentId);
                    if (schoolIndex == -1) {
                        break;
                    }
                    for (Student student : schools.get(schoolIndex).getStudents()) {
                        int id = Integer.parseInt(studentId);
                        if (student.getId() == id) {
                            for (Grade grade : student.getGrades()) {
                                JsonObject gradeObject = new JsonObject();
                                gradeObject.addProperty("grade", grade.getGrade());
                                gradeObject.addProperty("assignment", grade.getAssignment());
                                resultArray.add(gradeObject);
                            }
                            return resultArray.toString();
                        }
                    }
                }
                break;
            case "/school/students":
                String schoolName = parameters.get("schoolName");
                if (validateSchoolName(schoolName)) {
                    for (School school : schools) {
                        if (Objects.equals(school.getName(), schoolName)) {
                            for (Student student : school.getStudents()) {
                                JsonObject studentObject = new JsonObject();
                                studentObject.addProperty("id", student.getId());
                                studentObject.addProperty("name", student.getName());
                                resultArray.add(studentObject);
                            }
                        }
                    }
                    return resultArray.toString();
                }
                break;
            default:
                return "404";

        }
        return "404";
    }

    /**
     * @param schoolName to validate.
     * @return true if school with given name exists.
     */
    public boolean validateSchoolName(String schoolName) {
        for (School school : schools) {
            if (school.getName().equals(schoolName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param studentId to validate.
     * @return index of school student is in.
     */
    public int validateStudentId(String studentId) {
        for (int i = 0; i < schools.size(); i++) {
            for (Student student : schools.get(i).getStudents()) {
                int id = Integer.parseInt(studentId);
                if (student.getId() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Endpoints
     * - /school/student?schoolName=schoolName&studentName=studentName - add new student to school
     * - /student/grade?studentId=studentId&grade=grade&gradeAssignment=assignment - add new grade to student
     * @param path - endpoint path
     * @return result, if post was successful or not, for example if school or student doesn't exist,
     * should return false
     */
    public boolean post(String path) {
        Map<String, String> parameters = parseParameters(path);
        String endpoint = extractEndpoint(path);

        switch (endpoint) {
            case "/school/student":
                String studentName = parameters.get("studentName");
                String schoolName = parameters.get("schoolName");
                int schoolIndex1 = getSchoolIndexByName(schoolName);
                if (schoolIndex1 == -1) {
                    return false;
                }
                schools.get(schoolIndex1).addStudent(new Student(studentName));
                break;
            case "/student/grade":
                String studentId = parameters.get("studentId");
                String grade = parameters.get("grade");
                String gradeAssignment = parameters.get("gradeAssignment");
                int schoolIndex = validateStudentId(studentId);
                if (schoolIndex == -1) {
                    return false;
                }
                for (Student student : schools.get(schoolIndex).getStudents()) {
                    int id = Integer.parseInt(studentId);
                    if (student.getId() == id) {
                        Grade gradeToAdd = new Grade(Integer.parseInt(grade), gradeAssignment);
                        student.addGrade(gradeToAdd);
                    }
                }
                break;
            default:
                return false;
        }
        return true;
    }

    /**
     * @param path URL with query parameters
     * @return endpoint portion.
     */
    public String extractEndpoint(String path) {
        int endPointIndex = path.indexOf("?");
        if (endPointIndex != -1) {
            return path.substring(0, endPointIndex);
        }
        return path;
    }

    /**
     * @param path URL with query parameters.
     * @return map of parameters.
     */
    public Map<String, String> parseParameters(String path) {
        Map<String, String> parameters = new HashMap<>();
        int requestIndex = path.indexOf("?");
        if (requestIndex != -1) {
            String paramString = path.substring(requestIndex + 1);
            String[] paramPairs = paramString.split("&");
            for (String pair : paramPairs) {
                String[] keyValue = pair.split("=");
                parameters.put(keyValue[0], keyValue[1]);
            }
        }
        return parameters;
    }

    /**
     * Endpoints
     * - /student/name?studentId=studentId&name=newName - change student's name
     * @param path - endpoint path
     * @return result, if put was successful or not, for example if student doesn't exist, should return false
     */
    public boolean put(String path) {
        Map<String, String> parameters = parseParameters(path);
        String endpoint = extractEndpoint(path);

        if (Objects.equals(endpoint, "/student/name")) {
            String studentId = parameters.get("studentId");
            String name = parameters.get("name");
            int schoolIndex = validateStudentId(studentId);
            if (schoolIndex == -1) {
                return false;
            }
            for (Student student : schools.get(schoolIndex).getStudents()) {
                int id = Integer.parseInt(studentId);
                if (student.getId() == id) {
                    student.setName(name);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * @param schoolName of school.
     * @return index of school with schoolName.
     */
    public int getSchoolIndexByName(String schoolName) {
        for (int i = 0; i < schools.size(); i++) {
            if (Objects.equals(schools.get(i).getName(), schoolName)) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Endpoints
     * - /student/{studentId} (for example /student/10 ) - delete student from the database
     * @param path - endpoint path
     * @return result, if delete was successful or not, for example if student doesn't exist, should return false
     */
    public boolean delete(String path) {
        String[] splitPath = path.split("/", 3);
        String endpoint = splitPath[1];
        String studentId = splitPath[2];
        if (Objects.equals(endpoint, "student")) {
            int id = Integer.parseInt(studentId);
            for (School school : schools) {
                for (int i = 0; i < school.getStudents().size(); i++) {
                    if (school.getStudents().get(i).getId() == id) {
                        school.removeStudent(school.getStudents().get(i));
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
