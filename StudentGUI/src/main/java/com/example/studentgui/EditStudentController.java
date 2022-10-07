package com.example.studentgui;

import datamodel.Student;
import datamodel.StudentData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;


public class EditStudentController {
    //all of the variables declared below correspond with the edit-students.fxml
    @FXML
    private Label yearStudyDisplay;

    @FXML
    private Label mod1Edit;

    @FXML
    private Label mod2Edit;

    @FXML
    private Label mod3Edit;

    @FXML
    private ChoiceBox<String> mod1ChoiceEdit;

    @FXML
    private ChoiceBox<String> mod2ChoiceEdit;

    @FXML
    private ChoiceBox<String> mod3ChoiceEdit;

    //the modChoices variables correspond to the []
    private String mod1S, mod2S, mod3S;

    private String modChoices[] = {"OOP", "Data Algo", "DS", "Maths", "AI",
            "Adv Programming", "Project"};

    public void initialize() {

        //adding all the modChoices to each ChoiceBox

        mod1ChoiceEdit.getItems().addAll(modChoices);
        mod2ChoiceEdit.getItems().addAll(modChoices);
        mod3ChoiceEdit.getItems().addAll(modChoices);

        //setOnAction if a ChoiceBox is selected
        mod1ChoiceEdit.setOnAction(this::getChoiceEdit);
        mod2ChoiceEdit.setOnAction(this::getChoiceEdit);
        mod3ChoiceEdit.setOnAction(this::getChoiceEdit);
    }

    //to ensure that detail pops up to edit

    public void setToEdit(Student stu) {
        // displays the student to be edited details




        yearStudyDisplay.setText(stu.getYearOfStudy());
        mod1Edit.setText(stu.getModule1());
        mod2Edit.setText(stu.getModule2());
        mod3Edit.setText(stu.getModule3());

        //gets the new module choices using mod1S, mod2S and mod3S



        mod1S = stu.getModule1();
        mod2S = stu.getModule2();
        mod3S = stu.getModule3();
    }

    public Student processEdit(Student stu) {
        /* adding the observableIst statement by getting all students from StudentData
                 to be collected in allStudents
         */
        ObservableList<Student> allStudents = FXCollections.observableArrayList(StudentData.getInstance().getStudents());
        //getting the studId and year of study

        String studIdS = stu.getStudentId();
        String yearStudyS = stu.getYearOfStudy();
        //removing the student to be edited from allStudents

        allStudents.remove(stu);
        StudentData.getInstance().deleteStudent(stu);
        //adding the student back by creating a new object reference and calling the addStudentData()


        Student changedStu = new Student(studIdS,yearStudyS,mod1S,mod2S,mod3S);
        StudentData.getInstance().addStudentData(changedStu);
        //calling the addStudentData()


        // returning the newly edited student back to the Controller class / editStudent()

        return changedStu;
    }

    public void getChoiceEdit(ActionEvent event) {

        if(!(mod1ChoiceEdit.getSelectionModel().isEmpty())){
            mod1S = mod1ChoiceEdit.getSelectionModel().getSelectedItem();

        }

        if(!(mod2ChoiceEdit.getSelectionModel().isEmpty())){
            mod2S = mod2ChoiceEdit.getSelectionModel().getSelectedItem();

        }

        if(!(mod3ChoiceEdit.getSelectionModel().isEmpty())){
            mod3S = mod3ChoiceEdit.getSelectionModel().getSelectedItem();

        }


    }
}
