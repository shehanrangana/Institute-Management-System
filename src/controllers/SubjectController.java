package controllers;

import com.jfoenix.controls.JFXRadioButton;
import database.dbConnection;
import models.Subjects;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import static nsbm.NSBM.changeTabColors;

public class SubjectController implements Initializable {
    
    // Initialize variable for
    Connection con;
    
    // These variables for check the faculty and courses types
    char school = 'b';
    String toogleValue = "Bachelor";

    @FXML AnchorPane businessAnchorPane, computingAnchorPane, engineeringAnchorPane, subjectHomeAnchorPane, addNewSubjectAnchorPane, timeAllocationAnchorPane;
    @FXML Pane businessPane, computingPane, engineeringPane;
    @FXML ImageView backFromAddSubject, backFromTimeAllocation;
    @FXML Text businessText, computingText, engineeringText;
    
    @FXML ToggleGroup courseType;
    
    // Business table components
    @FXML TableView<Subjects> subjectTable;
    @FXML TableColumn<Subjects, String> subjectCodeColumn;
    @FXML TableColumn<Subjects, String> subjectNameColumn;
    @FXML TableColumn<Subjects, Double> alloTimeColumn;
    @FXML TableColumn<Subjects, Double> feeColumn;
    @FXML TableColumn<Subjects, Integer> creditColumn;
    @FXML TableColumn<Subjects, Double> durationColumn;
    @FXML TableColumn<Subjects, String> locationColumn;
    @FXML TableColumn<Subjects, String> courseColumn;
    @FXML TableColumn<Subjects, String> lecturerIdColumn;
    
    // This method will return an ObservableList lecturers
    public ObservableList<Subjects> getSubjectList(){
        String query = null;
        ObservableList<Subjects> subjectList = FXCollections.observableArrayList();

        switch (school) {
            case 'b':
                if(toogleValue.equals("Bachelor")){
                    query = "SELECT * FROM subject INNER JOIN bachelor ON subject.b_course_name = bachelor.course_name WHERE bachelor.faculty = 'School of Business'";
                }else if(toogleValue.equals("Master")){
                    query = "SELECT * FROM subject INNER JOIN master ON subject.m_course_name = master.course_name WHERE master.faculty = 'School of Business'";
                }   
                break;
            case 'c':
                if(toogleValue.equals("Bachelor")){
                    query = "SELECT * FROM subject INNER JOIN bachelor ON subject.b_course_name = bachelor.course_name WHERE bachelor.faculty = 'School of Computing'";
                }else if(toogleValue.equals("Master")){
                    query = "SELECT * FROM subject INNER JOIN master ON subject.m_course_name = master.course_name WHERE master.faculty = 'School of Computing'";
                }   
                break;
            case 'e':
                if(toogleValue.equals("Bachelor")){
                    query = "SELECT * FROM subject INNER JOIN bachelor ON subject.b_course_name = bachelor.course_name WHERE bachelor.faculty = 'School of Engineering'";
                }else if(toogleValue.equals("Master")){
                    query = "SELECT * FROM subject INNER JOIN master ON subject.m_course_name = master.course_name WHERE master.faculty = 'School of Engineering'";
                }   
                break;
            default:
                break;
        }
        
        
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Subjects subject;
            
            while(rs.next()){
                subject = new Subjects(rs.getString("subject_code"), rs.getString("subject_name"), rs.getDouble("allocated_time"), rs.getDouble("fee"),
                rs.getInt("credit"), rs.getDouble("duration"), rs.getString("location"), rs.getString("b_course_name"), rs.getString("lecturer_id")); 
                subjectList.add(subject);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return subjectList;
    }
    
    // Setup columns and fill the subject table
    public void fillColumns(){
        subjectCodeColumn.setCellValueFactory(new PropertyValueFactory<Subjects, String> ("subjectCode"));
        subjectNameColumn.setCellValueFactory(new PropertyValueFactory<Subjects, String> ("subjectName"));
        alloTimeColumn.setCellValueFactory(new PropertyValueFactory<Subjects, Double> ("allocatedTime"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<Subjects, Double> ("fee"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<Subjects, Integer> ("credit"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<Subjects, Double> ("duration"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Subjects, String> ("location"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<Subjects, String> ("courseName"));
        lecturerIdColumn.setCellValueFactory(new PropertyValueFactory<Subjects, String> ("lecturerId"));
        
        // load the data into the subject table
        subjectTable.setItems(getSubjectList());
    }
    
    // Handle school of business tab
    public void selectBusinessTab(){
        school = 'b';
        fillColumns();
        
        changeTabColors(businessPane, computingPane, engineeringPane, businessText, computingText, engineeringText, businessAnchorPane, computingAnchorPane, engineeringAnchorPane);
    }
    
    // Handle school of computing tab
    public void selectComputingTab(){
        school = 'c';
        fillColumns();
        
        changeTabColors(computingPane, businessPane, engineeringPane, computingText, businessText, engineeringText, computingAnchorPane, businessAnchorPane, engineeringAnchorPane);
    }
    
    // Handle school of engineering tab
    public void selectEngineeringTab(){
        school = 'e';
        fillColumns();

        changeTabColors(engineeringPane, businessPane, computingPane, engineeringText, businessText, computingText, engineeringAnchorPane, businessAnchorPane, computingAnchorPane);
    }
    
    // Switch to the add new subject pane
    public void addNewSubjectButtonPressed(){
        addNewSubjectAnchorPane.setVisible(true);
        subjectHomeAnchorPane.setVisible(false);
    }
    
    // Switch to the time allocate pane
    public void timeAllocateButtonPressed(){
        timeAllocationAnchorPane.setVisible(true);
        subjectHomeAnchorPane.setVisible(false);
    }
    
    // Back to subjects pane
    public void backToSubjectButtonPressed(MouseEvent event){
        subjectHomeAnchorPane.setVisible(true);
        
        // Identify which pane going to be invisible
        if(event.getTarget() == backFromAddSubject){
            addNewSubjectAnchorPane.setVisible(false);
        }else if(event.getTarget() == backFromTimeAllocation){
            timeAllocationAnchorPane.setVisible(false);
        } 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Default view is School of Business
        selectBusinessTab();
        
        // Dynamically select bachelor/master courses
        courseType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (courseType.getSelectedToggle() != null) {
                    JFXRadioButton selectedRadioButton = (JFXRadioButton) courseType.getSelectedToggle();
                    toogleValue = selectedRadioButton.getText();
                    
                    fillColumns();
                }
            }
        });
        
    }    
    
}
