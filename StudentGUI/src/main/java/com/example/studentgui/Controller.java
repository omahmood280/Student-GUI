package com.example.studentgui;

import datamodel.Student;
import datamodel.StudentData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Controller {

    //these variables correspond to the <top> of main-view.fxml
    @FXML
    private TextField studId;

    @FXML
    private TextField yearStudy;

    @FXML
    private ChoiceBox<String> mod1Choice;

    @FXML
    private ChoiceBox<String> mod2Choice;

    @FXML
    private ChoiceBox<String> mod3Choice;

    private String choice1, choice2, choice3;

    private String modChoices[] = {"OOP", "Data Algo", "DS", "Maths", "AI",
            "Adv Programming", "Project"};

    @FXML
    private Label validateStudent; // this is the Label that you only see when there is an invalid "add"

    //validateStudent is the last element corresponding to <top>

    //these variables correspond to the <left> i.e. the studentListView
    @FXML
    private ListView<Student> studentListView;

    //these variables correspond to the <bottom> part of the border
    @FXML
    private Label yearStudyView;

    @FXML
    private Label mod1View;

    @FXML
    private Label mod2View;

    @FXML
    private Label mod3View;

    //mod3View is the last element for the bottom part of the border

    //the contextMenu is used for the right-click regarding Edit / Delete
    @FXML
    private ContextMenu listContextMenu;

    //this variable is used when switching windows from add to edit
    @FXML
    private BorderPane mainWindow;

    //used to add a student to the ArrayList for addStudentData()
    public Student studentToAdd;


    public void initialize() {

        studentListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                /*  if a studId is selected then the changed()
                          ensures that the other fields related to the selected item appear at the bottom of the window
                */

                if(newValue != null) {
                    mod1View.setText(newValue.getModule1());
                    mod2View.setText(newValue.getModule2());
                    mod3View.setText(newValue.getModule3());
                    yearStudyView.setText(newValue.getYearOfStudy());
                }

            }
        });
        //the setOnAction ensures that when a ChoiceBox is selected the getChoice() grabs the selected choice
        mod1Choice.setOnAction(this::getChoice);
        mod2Choice.setOnAction(this::getChoice);
        mod3Choice.setOnAction(this::getChoice);

        // the array declared above for modChoices is added to each Choicebox


        mod1Choice.getItems().addAll(modChoices);
        mod2Choice.getItems().addAll(modChoices);
        mod3Choice.getItems().addAll(modChoices);

        //addAll() modChoices [] to each ChoiceBox

       /*deleting a student
         creating a new listContextMenu -> defined above in the variables
         the contextMenu is used for the right-click regarding Edit / Delete
        */
        listContextMenu = new ContextMenu();


        // creating a MenuItem object so that when the user right-clicks a studId, Delete? appears


        MenuItem deleteStudent = new MenuItem("Delete?");

        deleteStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // getting the item to be deleted and calling deleteStudent()


                deleteStudent(studentListView.getSelectionModel().getSelectedItem());

            }
        });

        //editing a student
        //creating a new listContextMenu -> defined above in the variables

        listContextMenu = new ContextMenu();

        // creating a MenuItem object so that when the user right-clicks a studId, Edit?? appears

        MenuItem editStudent = new MenuItem("Edit?");

        editStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //getting the item to be edited and calling the editStudent()

                editStudent(studentListView.getSelectionModel().getSelectedItem());


            }
        });

        listContextMenu.getItems().add(deleteStudent);
        listContextMenu.getItems().add(editStudent);

        //to ensure access to a particular cell in the studentListView
        studentListView.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            public ListCell<Student> call(ListView<Student> param) {
                ListCell<Student> cell = new ListCell<Student>() {
                    @Override
                    protected void updateItem(Student stu, boolean empty) {
                        /* ensuring that the studentListView has studId's or not when
                                 the delete a student takes place
                                and also that studId’s are either in the ListView or not;

                         */
                        super.updateItem(stu, empty);

                        if (empty || stu == null) {
                            setText("");
                        } else {
                            setText(stu.getStudentId());
                        }
                    }
                };//end of update()


                //part of the deleteActionEvent event
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        });
                return cell;
            }
        }); //end of setting the cell factory

        // ensuring that the studId's are sorted according to year of study in ascending order

        SortedList<Student> sortedByYear = new SortedList<Student>(StudentData.getInstance().getStudents(),
                new Comparator<Student>() {
                    @Override
                    public int compare(Student o1, Student o2) {
                        return o1.getYearOfStudy().compareTo(o2.getYearOfStudy());
                    }
                });

        /* setting items using the sorted list
                 ensuring that only one studId can be selected at one time
                 ensuring that the first studId is highlighted when the application commences
         */

        studentListView.setItems(sortedByYear);
        studentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        studentListView.getSelectionModel().selectFirst();




    }

    public void getChoice(ActionEvent event) {
        /* making use of event to determine each choice and assigning each module choice to
                 choice1, choice2 and choice3
         */
        choice1 = mod1Choice.getSelectionModel().getSelectedItem();
        choice2 = mod2Choice.getSelectionModel().getSelectedItem();
        choice3 = mod3Choice.getSelectionModel().getSelectedItem();




    }



    @FXML
    public void addStudentData() {
        //getting the values from the textfields


        String studIdS = studId.getText();
        String yearStudyS = yearStudy.getText();

        /*     validating whether the studIdS and yearStudyS are occupied as both have to be occupied
                 for the add to take place, if one or both are unoccupied then the following message
                 in the validateStudent label is printed
                 "Error: cannot add student if studId or year of study not filled in"
                 If both are occupied then we go ahead with the addStudentData()
         */

        if(!((studIdS.isEmpty())) && !(yearStudyS.isEmpty())){
            //ensuring that the validateStudent label is clear of any text
            //using the getInstance() to addStudentData()
            //selecting the student that has been added so that it is highlighted on the list

            validateStudent.setText("");
            studentToAdd = new Student(studIdS, yearStudyS, choice1, choice2, choice3);
            StudentData.getInstance().addStudentData(studentToAdd);
            studentListView.getSelectionModel().select(studentToAdd);


        }
        else{
            validateStudent.setVisible(true);

            validateStudent.setText("Error: cannot add student if studId or year of study not filled in");
        }


    }

    public void deleteStudent(Student stu) {
        //creating an alert object to confirm that a user wants to delete

        //setting the title with "Delete a student from the list"

        //setting the header text with "Deleting student " xxx - where xxx is the studId

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete a student from the list");
        alert.setHeaderText("Deleting student " + stu.getStudentId());
        alert.setContentText("Are you sure you want to delete the student?");



        Optional<ButtonType> result = alert.showAndWait();
        /* including a test to verify if the result is present and whether the OK button was
                 pressed, if so we go ahead and call the deleteStudent()
         */
        if (result.isPresent() && result.get()  == ButtonType.OK){
            StudentData.getInstance().deleteStudent(stu);
        }
    }

    public void editStudent(Student stu) {
        Dialog<ButtonType> dialog = new Dialog<>();
        /*  using the dialog object to set the owner, the title and the header text
                 the title states, Edit a student's details
                 the header text states, Editing student Id: xxx - where xxx is the studId
         */
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Edit a student's details");
        dialog.setHeaderText("Editing student Id: " + stu.getStudentId());

        FXMLLoader fxmlLoader = new FXMLLoader();
        // using the fxmlLoader to set the edit-students.fxml

        fxmlLoader.setLocation(getClass().getResource("edit-students.fxml"));

        try {
            // loading the fxml

            dialog.getDialogPane().setContent(fxmlLoader.load());

        }
        catch (IOException event) {
            // printing a message if it cannot be loaded and also printing the stackTrace

            System.out.println("Could not load the dialog");
            event.printStackTrace();

            return;
        }

        EditStudentController ec = fxmlLoader.getController();

        // using the ec object to call setToEdit()

        ec.setToEdit(stu);
        //creating the OK and CANCEL buttons using dialog

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

         /* verifying if there is an edit to complete, completing the editStudent call processEdit()
                 and ensuring that the student edited is selected
         */


        if (result.isPresent() && result.get() == ButtonType.OK) {
            Student editStudent =   ec.processEdit(stu);
            studentListView.getSelectionModel().select(editStudent);
            //selecting the edited studId
        }
    }



}


