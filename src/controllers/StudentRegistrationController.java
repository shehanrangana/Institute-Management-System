package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import database.dbConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class StudentRegistrationController implements Initializable {
    
    Connection con;

    @FXML AnchorPane stdRegAnchorPane;
    @FXML ImageView backFromStdRegistration;
    
    // Student Registration's components
    @FXML Pane ugExternalPane, pgExternalPane;
    @FXML JFXButton registerButton;
    
    @FXML JFXTextField studentIdTextField;
    @FXML JFXTextField initialsTextField;
    @FXML JFXTextField firstNameTextField;
    @FXML JFXTextField lastNameTextField;
    @FXML JFXTextField addressLine1TextField;
    @FXML JFXTextField addressLine2TextField;
    @FXML JFXTextField addressLine3TextField;
    @FXML JFXDatePicker birthdayDatePicker;
    @FXML JFXComboBox genderComboBox;
    @FXML JFXTextField emailTextField;
    @FXML JFXTextField nicTextField;
    @FXML JFXTextField mobileTextField;
    @FXML JFXTextField fixedTextField;
    @FXML JFXComboBox stdTypeComboBox;
    @FXML JFXComboBox facultyComboBox;
    @FXML JFXComboBox courseComboBox;
    
    // AL Result table Components
    @FXML JFXComboBox streamComboBox;
    @FXML JFXTextField subject1TextField;
    @FXML JFXTextField subject2TextField;
    @FXML JFXTextField subject3TextField;
    @FXML JFXTextField result1TextField;
    @FXML JFXTextField result2TextField;
    @FXML JFXTextField result3TextField;
    @FXML JFXTextField rankTextField;
    @FXML JFXTextField zScoreTextField;
    
    // Qualifications table Components
    @FXML JFXTextField qualificationTextField;
    @FXML JFXTextField instituteTextField;
    @FXML JFXTextField compYearTextField;

    // Expand external pane (Undergraduate/Postgraduate)
    public void expandExternalPane(){
        if(stdTypeComboBox.getValue() == "Undergraduate"){
            pgExternalPane.setVisible(false);
            ugExternalPane.setVisible(true);
            registerButton.setVisible(true);
        }else if(stdTypeComboBox.getValue() == "Postgraduate"){
            ugExternalPane.setVisible(false);
            pgExternalPane.setVisible(true);
            registerButton.setVisible(true);
        }
    }
    
    // check the inputs
    public boolean checkInputs(){
        if(studentIdTextField.getText().isEmpty() || initialsTextField.getText().isEmpty() || firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty()
                || addressLine1TextField.getText().isEmpty() || addressLine2TextField.getText().isEmpty() || birthdayDatePicker == null || genderComboBox == null || emailTextField.getText().isEmpty()
                || nicTextField.getText().isEmpty() || mobileTextField.getText().isEmpty() || facultyComboBox == null || courseComboBox == null){
            
            return false; 
        }else{
            if(stdTypeComboBox.getValue().toString() == "Undergraduate"){
                if(streamComboBox == null || subject1TextField.getText().isEmpty() || result1TextField.getText().isEmpty() || subject2TextField.getText().isEmpty()
                        || result2TextField.getText().isEmpty() || subject3TextField.getText().isEmpty() || result3TextField.getText().isEmpty() 
                        || rankTextField.getText().isEmpty() || zScoreTextField.getText().isEmpty()){
                    return false;
                }
            }else if(stdTypeComboBox.getValue().toString() == "Postgraduate"){
                if(qualificationTextField.getText().isEmpty() || instituteTextField.getText().isEmpty() || compYearTextField.getText().isEmpty()){
                    return false;
                }
            }
            return true;
        }
    }
    
    // Upload newly student's details into the database
    public void registerButtonPressed(ActionEvent event) throws IOException{
        if(checkInputs()){
            try {
                PreparedStatement ps = null;
                PreparedStatement alResult_ps = null;
                PreparedStatement qualifications_ps = null;
                
                // Check whether registering student is an undergraduate or postgraduate
                if(stdTypeComboBox.getValue().toString() == "Undergraduate"){
                    
                    // Insert data into the undergraduate table
                    ps = con.prepareStatement("INSERT INTO undergraduate(student_id, initials, first_name, last_name, address_line_1, address_line_2, address_line_3, "
                        + "birthday, gender, email, nic, mobile, fixed, faculty_name, course_name)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    
                    // Insert data into the a/l result table
                    alResult_ps = con.prepareStatement("INSERT INTO al_results(student_id, stream, subject_1, result_1, subject_2, result_2, subject_3, result_3, rank, z_score)" + 
                            "VALUES(?,?,?,?,?,?,?,?,?,?)");
                    
                }else{
                    ps = con.prepareStatement("INSERT INTO postgraduate(student_id, initials, first_name, last_name, address_line_1, address_line_2, address_line_3, "
                        + "birthday, gender, email, nic, mobile, fixed, faculty_name, course_name)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    
                    qualifications_ps = con.prepareStatement("INSERT INTO qualifications(student_id, qualification_type, institute, completion_year)"
                            + "VALUES(?,?,?,?)");
                }
                
                // Get values for graduate table
                ps.setString(1, studentIdTextField.getText());
                ps.setString(2, initialsTextField.getText());
                ps.setString(3, firstNameTextField.getText());
                ps.setString(4, lastNameTextField.getText());
                ps.setString(5, addressLine1TextField.getText());
                ps.setString(6, addressLine2TextField.getText());
                ps.setString(7, addressLine3TextField.getText());
                
                LocalDate date = birthdayDatePicker.getValue();
                ps.setString(8, date.toString());
                
                ps.setString(9, genderComboBox.getValue().toString());
                ps.setString(10, emailTextField.getText());
                ps.setString(11, nicTextField.getText());
                ps.setString(12, mobileTextField.getText());
                ps.setString(13, fixedTextField.getText());
                ps.setString(14, facultyComboBox.getValue().toString());
                ps.setString(15, courseComboBox.getValue().toString());

                ps.executeUpdate();
                
                // Get values for a/l result table
                if(stdTypeComboBox.getValue().toString() == "Undergraduate"){
                alResult_ps.setString(1, studentIdTextField.getText());
                alResult_ps.setString(2, streamComboBox.getValue().toString());
                alResult_ps.setString(3, subject1TextField.getText());
                alResult_ps.setString(4, result1TextField.getText());
                alResult_ps.setString(5, subject2TextField.getText());
                alResult_ps.setString(6, result2TextField.getText());
                alResult_ps.setString(7, subject3TextField.getText());
                alResult_ps.setString(8, result3TextField.getText());
                alResult_ps.setString(9, rankTextField.getText());
                alResult_ps.setString(10, zScoreTextField.getText());
                    
                alResult_ps.executeUpdate();
                }
                
                // Get values for qualifications table
                if(stdTypeComboBox.getValue().toString() == "Postgraduate"){
                qualifications_ps.setString(1, studentIdTextField.getText());
                qualifications_ps.setString(2, qualificationTextField.getText());
                qualifications_ps.setString(3, instituteTextField.getText());
                qualifications_ps.setString(4, compYearTextField.getText());
                
                qualifications_ps.executeUpdate();
                }
                
                // Successfully data entered message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Data Inserted");
                alert.showAndWait();
                
                backToStudent();
                
            } catch (SQLException ex) {
                Logger.getLogger(StudentRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Entered ID already in database");
                alert.showAndWait();    
            }
            
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("One or more fields are empty");
            alert.showAndWait();
        }
    }
    
    // Back to student pane
    public void backToStudent() throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/Student.fxml"));
        stdRegAnchorPane.getChildren().setAll(pane);
    }
    
    // Fill faculty ComboBox with database values
    public void fillComboBoxWithFacultyNames(){
        //facultyComboBox.getItems().clear();
        try {
            String query = "SELECT faculty_name FROM faculty";
            PreparedStatement ps = (PreparedStatement)con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String fName = rs.getString("faculty_name");
                facultyComboBox.getItems().addAll(fName);
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // Fill courses ComboBox with database values
    String coursesQuery = "";
    int stdTypeIndex = 0;
    public void fillComboBoxWithCourseNames(){
        
        // Check the student type using stdTypeComboBox
        stdTypeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newStdValue) {
                facultyComboBox.setDisable(false);
                //facultyComboBox.valueProperty().set(null);
                courseComboBox.getItems().clear();
                if(newStdValue.equals("Undergraduate")){
                    stdTypeIndex = 0;
                }else if(newStdValue.equals("Postgraduate")){
                    stdTypeIndex = 1;
                }
            }
            
        });

        // Check the faculty using facultyComboBox
        facultyComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newFacultyValue) {
                courseComboBox.getItems().clear();
                
                // If student is an undergraduate student...
                if(stdTypeIndex==0){
                    try {
                        if(newFacultyValue.equals("School of Business")){
                            coursesQuery = "SELECT course_name FROM bachelor WHERE faculty = 'School of Business'";
                        }else if(newFacultyValue.equals("School of Computing")){
                            coursesQuery = "SELECT course_name FROM bachelor WHERE faculty = 'School of Computing'";
                        }else if(newFacultyValue.equals("School of Engineering")){
                            coursesQuery = "SELECT course_name FROM bachelor WHERE faculty = 'School of Engineering'";
                        }
                        
                        PreparedStatement ps = (PreparedStatement)con.prepareStatement(coursesQuery);
                        ResultSet rs = ps.executeQuery();
                        
                        while (rs.next()) {
                            String cName = rs.getString("course_name");
                            courseComboBox.getItems().addAll(cName);
                        }       
                    }catch (SQLException ex) {
                        Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
                // If student is an postgraduate student...
                if(stdTypeIndex==1){
                    try {
                        if(newFacultyValue.equals("School of Business")){
                            coursesQuery = "SELECT course_name FROM master WHERE faculty = 'School of Business'";
                        }else if(newFacultyValue.equals("School of Computing")){
                            coursesQuery = "SELECT course_name FROM master WHERE faculty = 'School of Computing'";
                        }else if(newFacultyValue.equals("School of Engineering")){
                            coursesQuery = "SELECT course_name FROM master WHERE faculty = 'School of Engineering'";
                        }
                        
                        PreparedStatement ps = (PreparedStatement)con.prepareStatement(coursesQuery);
                        ResultSet rs = ps.executeQuery();
                        
                        while (rs.next()) {
                            String cName = rs.getString("course_name");
                            courseComboBox.getItems().addAll(cName);
                        }       
                    }catch (SQLException ex) {
                        Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
            }
                
        });
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Initialize items for gender combo box
        genderComboBox.getItems().addAll("Male", "Female");  
        
        // Initialize items for student type combo box
        stdTypeComboBox.getItems().addAll("Undergraduate", "Postgraduate"); 
        
        // Initialize items for student type combo box
        streamComboBox.getItems().addAll("Maths", "Bio", "Commerce", "Art");
        
        // Call two functions for fill the combo boxes
        fillComboBoxWithFacultyNames();
        fillComboBoxWithCourseNames();
    }     
}
