package controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import database.dbConnection;
import models.Subjects;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.Alert;
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
import static nsbm.NSBM.alerts;
import static nsbm.NSBM.changeTabColors;

public class SubjectController implements Initializable {
    
    // Initialize variable for
    Connection con;
    
    // These variables for check the faculty and courses types
    char school = 'b';
    String toogleValue = "Bachelor";

    @FXML AnchorPane subjectHomeAnchorPane, addNewSubjectAnchorPane, timeAllocationAnchorPane;
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
    
    // Add new subject components
    @FXML JFXTextField subCodeTextField, subNameTextField, alloTimeTextField, feeTextField, creditTextField, durationTextField, locationTextField;
    @FXML JFXComboBox subTypeComboBox, semIdComboBox, courseComboBox, lecturerComboBox;
    @FXML JFXCheckBox compulsoryCheckBox;
    
    // This method will return an ObservableList lecturers
    public ObservableList<Subjects> getSubjectList(){
        String query = null;
        ObservableList<Subjects> subjectList = FXCollections.observableArrayList();

        switch (school) {
            case 'b':
                if(toogleValue.equals("Bachelor")){
                    query = "SELECT *, CONCAT('', b_course_name, m_course_name) AS course_name FROM subject INNER JOIN bachelor ON subject.b_course_name = bachelor.course_name WHERE bachelor.faculty = 'School of Business'";
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
                rs.getInt("credit"), rs.getDouble("duration"), rs.getString("location"), rs.getString("course_name"), rs.getString("lecturer_id")); 
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
        
        changeTabColors(businessPane, computingPane, engineeringPane, businessText, computingText, engineeringText);
    }
    
    // Handle school of computing tab
    public void selectComputingTab(){
        school = 'c';
        fillColumns();
        
        changeTabColors(computingPane, businessPane, engineeringPane, computingText, businessText, engineeringText);
    }
    
    // Handle school of engineering tab
    public void selectEngineeringTab(){
        school = 'e';
        fillColumns();

        changeTabColors(engineeringPane, businessPane, computingPane, engineeringText, businessText, computingText);
    }
    
    // Fill course ComboBox with database values
    public void fillComboBoxWithCoursesNames(){
        // Check the subject type using subTypeComboBox
        subTypeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            String query = "";
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newStdValue) {
                try {
                    courseComboBox.getItems().clear();
                    if(newStdValue.equals("Bachelor Subject")){
                        query = "SELECT course_name FROM bachelor";
                    }else if(newStdValue.equals("Master Subject")){
                        query = "SELECT course_name FROM master";
                    }
                    PreparedStatement ps = con.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
                    
                    while (rs.next()) {
                        String courseName = rs.getString("course_name");
                        courseComboBox.getItems().addAll(courseName);
                    }   
                } catch (SQLException ex) {
                    Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }); 
    }
    
    // Fill semester ComboBox with database values
    public void fillComboBoxWithSemId(){
        semIdComboBox.getItems().clear();
        try {
            String query = "SELECT semester_id FROM semester";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String semester = rs.getString("semester_id");
                semIdComboBox.getItems().addAll(semester); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Fill lecture ComboBox with database values
    public void fillComboBoxWithLecturerId(){
        lecturerComboBox.getItems().clear();
        try {
            String query = "SELECT lecturer_id FROM lecturer";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String lectuter = rs.getString("lecturer_id");
                lecturerComboBox.getItems().addAll(lectuter); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Switch to the add new subject pane
    public void addNewSubjectButtonPressed(){
        fillComboBoxWithSemId();
        fillComboBoxWithLecturerId();
        
        addNewSubjectAnchorPane.setVisible(true);
        subjectHomeAnchorPane.setVisible(false);
    }
    
    // Insert new subject to the database
    public void addButtonPressed(){
        byte compulsory = 0;
        if(compulsoryCheckBox.isSelected()){
            compulsory = 1;
        }
        
        PreparedStatement ps = null;
        try{
            if(subTypeComboBox.getValue().toString() == "Bachelor Subject"){
                ps = con.prepareStatement("INSERT INTO subject(subject_code, subject_name, compulsory, allocated_time, fee, credit, duration, location, semester_id, b_course_name, lecturer_id)" + "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            }else if(subTypeComboBox.getValue().toString() == "Master Subject"){
                ps = con.prepareStatement("INSERT INTO subject(subject_code, subject_name, compulsory, allocated_time, fee, credit, duration, location, semester_id, m_course_name, lecturer_id)" + "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            }
            // Get values for subject table
            ps.setString(1, subCodeTextField.getText());
            ps.setString(2, subNameTextField.getText());
            ps.setByte(3, compulsory);
            ps.setDouble(4, Double.parseDouble(alloTimeTextField.getText()));
            ps.setDouble(5, Double.parseDouble(feeTextField.getText()));
            ps.setInt(6, Integer.parseInt(creditTextField.getText()));
            ps.setDouble(7, Double.parseDouble(durationTextField.getText()));
            ps.setString(8, locationTextField.getText());
            ps.setString(9, semIdComboBox.getValue().toString());
            ps.setString(10, courseComboBox.getValue().toString());
            ps.setString(11, lecturerComboBox.getValue().toString());
            
            ps.executeUpdate();
            
            // Back to courses view
            fillColumns();
            subjectHomeAnchorPane.setVisible(true);
            addNewSubjectAnchorPane.setVisible(false);
            
        }catch(NumberFormatException | SQLException e){
            // Error message
            e.printStackTrace();
            alerts('E', "Message", null, "Please fill all the fields");
        }
    }
    
    // Switch to the time allocate pane
    public void timeAllocateButtonPressed(){
        timeAllocationAnchorPane.setVisible(true);
        subjectHomeAnchorPane.setVisible(false);
    }
    
    // Remove a subject from the system
    public void subjectRemoveButtonPressed(){
        PreparedStatement ps = null;
        String subjectCode = null;
        try{
            // Check whether subject which is going to be deleted belong to 
            ps = con.prepareStatement("DELETE FROM subject WHERE subject_code = ?");
            subjectCode = subjectCodeColumn.getCellData(subjectTable.getSelectionModel().getSelectedItem());

            if(subjectCode == null){
                // Inform message
                alerts('I', "Message", null, "Please select a record");
            }else{
                 // Confirmation message
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Are You Sure?");
                alert.showAndWait();
                
                if(alert.getResult().getText().equals("OK")){
                    ps.setString(1, subjectCode);
                    ps.executeUpdate();
                    fillColumns();
                }else{
                    ps.setString(1, null);
                    ps.executeUpdate();
                }
            }

        }catch(SQLException e){
            alerts('E', "Message", null, "Cannot remove selected subject.\nSome students are currently studying this subject.");
        }
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
        
        // Initialize items for combo boxes
        subTypeComboBox.getItems().addAll("Bachelor Subject", "Master Subject");
        fillComboBoxWithCoursesNames();
        
    }    
    
}
