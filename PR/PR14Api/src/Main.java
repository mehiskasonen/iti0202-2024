import ee.taltech.iti0202.api.SchoolDatabase;

import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hello world!");
        String jsonContent = "[\n" +
                "    {\n" +
                "        \"students\": [\n" +
                "            {\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Dominik Vettik\",\n" +
                "                \"grades\": [\n" +
                "                    {\n" +
                "                        \"grade\": 5,\n" +
                "                        \"assignment\": \"Aine1\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"grade\": 3,\n" +
                "                        \"assignment\": \"Aine2\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"Ago Luberg\",\n" +
                "                \"grades\": [\n" +
                "                    {\n" +
                "                        \"grade\": 5,\n" +
                "                        \"assignment\": \"Aine3\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"grade\": 5,\n" +
                "                        \"assignment\": \"Aine4\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"name\": \"TalTech\"\n" +
                "    }\n" +
                "]";
        SchoolDatabase database = new SchoolDatabase(jsonContent);
        System.out.println(database.getSchools());
        System.out.println(database.get("/school/students?schoolName=TalTech"));
        database.post("/student/grade?studentId=1&grade=2&gradeAssignment=Aine3");
        System.out.println(database.getSchools());

        database.put("/student/name?studentId=1&name=Dominik Wettik");
        System.out.println(database.getSchools());

        System.out.println(database.get("/student/grades?studentId=1"));
        //expected [[{"grade":5,"assignment":"Aine2"},{"grade":1,"assignment":"Aine3"}]]
        // found   [[Grade{grade=5, assignment='Aine2'}, Grade{grade=1, assignment='Aine3'}]]


        database.post("/school/student?schoolName=TalTech&studentName=Mehis");
        System.out.println(database.getSchools());
        //expected [[{"id":1,"name":"Dominik Vettik"},{"id":2,"name":"Ago Luberg"},{"id":11,"name":"Random_6WdMZK"}]]
        // found   [[{"id":1,"name":"Dominik Vettik"},{"id":2,"name":"Ago Luberg"},{"id":12,"name":"Random_6WdMZK"}]]

        //SchoolDatabase db = new SchoolDatabase("/Users/mehiskasonen/IdeaProjects/iti0202-2024/PR/PR14Api/src/ee/taltech/iti0202/api/school_database.json");
        ///Users/mehiskasonen/IdeaProjects/iti0202-2024/PR/PR14Api/src/ee/taltech/iti0202/api/school_database.json
        ///Users/mehiskasonen/IdeaProjects/iti0202-2024/PR/PR14Api/src/ee/taltech/iti0202/api/SchoolDatabase.java
        ///Users/mehiskasonen/IdeaProjects/iti0202-2024/PR/PR14Api/src/Main.java
//        db.get("/student/grades?studentId=studentId");
//        db.parseParameters("/school/student?schoolName=schoolName&studentName=studentName");
//        db.parseParameters("/student/grade?studentId=studentId&grade=grade&gradeAssignment=assignment");
//        db.delete("/student/{studentId}");
        int a = 10;
        var b = 3;
        var x = (a / b);
        System.out.println(x);


    }

}
