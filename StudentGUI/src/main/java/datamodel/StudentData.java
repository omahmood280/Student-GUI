package datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class StudentData {


    private static StudentData instance = null;
    private static String filename = "student-data.txt";
    private ObservableList<Student> students;

    public static StudentData getInstance() {

        if(instance == null) {
            instance = new StudentData();
        }
        return instance;
    }



    public ObservableList<Student> getStudents() {

        return students;
    }

    public void loadStudentData() throws IOException {
        students = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);
        String input;

        try {
            while ((input = br.readLine()) != null) {
                //  splitting each input line using a tab


                String[] studentItem;

                /* using the String to create each piece of data so that all the instance variables
                         have a value accordingly
                 */
                if(input.equals("")){
                    break;
                }
                else {
                    studentItem = input.split("\t");

                    String studId = studentItem[0];
                    String yearStudy = studentItem[1];
                    String mod1 = studentItem[2];
                    String mod2 = studentItem[3];
                    String mod3 = studentItem[4];


                    Student studDataItem = new Student(studId, yearStudy, mod1, mod2, mod3);
                    //adding the studentDataItem to the students array

                    students.add(studDataItem);
                }

            }


        } finally {

            if (br != null) {
                br.close();
            }
        }

    }

    public void storeStudentData() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {

            Iterator<Student> it = students.iterator();
            while (it.hasNext()) {
                // accepting the next item from the iterated list

                Student item = it.next();


                bw.write(String.format("%s\t%s\t%s\t%s\t%s",
                        item.getStudentId(),
                        item.getYearOfStudy(),
                        item.getModule1(),
                        item.getModule2(),
                        item.getModule3()));


                /*  once a student item is written to the file,
                         the next item is stored on a new line
                 */
                bw.newLine();

            }
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    public void addStudentData(Student studentToAdd){
        //  adding the student to add to students

        students.add(studentToAdd);
    }

    public void deleteStudent(Student stu){
        /* completing the addStudentData so that a student can be removed
                 from students
         */
        students.remove(stu);
    }
}
