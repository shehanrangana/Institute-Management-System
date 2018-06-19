package controllers;

import com.jfoenix.controls.JFXTextField;
import database.dbConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;
import models.AlResult;
import models.PostgraduateStudent;
import models.Qualifications;
import models.UndergraduateStudent;

public class StudentController implements Initializable {
    
    Connection con;
    
    @FXML Pane undergraduatePane, postgraduatePane;
    @FXML AnchorPane ugAnchorPane, pgAnchorPane, stdRegAnchorPane, chooseSubjectsAnchorPane, studentHomeAnchorPane, detailsAndUpdateAnchorPane, studentAnchorPane;
    @FXML ImageView backFromSubjects, backFromMoreDetails, backFromStdRegistration;
    @FXML Text undergraduateText, postgraduateText;
    
    // Undergraduate view components
    @FXML TableView<UndergraduateStudent> undergraduateTable;
    @FXML TableColumn<UndergraduateStudent, String> idColumn;
    @FXML TableColumn<UndergraduateStudent, String> fNameColumn;
    @FXML TableColumn<UndergraduateStudent, String> birthdayColumn;
    @FXML TableColumn<UndergraduateStudent, String> genderColumn;
    @FXML TableColumn<UndergraduateStudent, String> emailColumn;
    @FXML TableColumn<UndergraduateStudent, String> nicColumn;
    @FXML TableColumn<UndergraduateStudent, String> mobileColumn;
    
    // Postgraduate view components
    @FXML TableView<PostgraduateStudent> postgraduateTable;
    @FXML TableColumn<PostgraduateStudent, String> pIdColumn;
    @FXML TableColumn<PostgraduateStudent, String> pfNameColumn;
    @FXML TableColumn<PostgraduateStudent, String> pBirthdayColumn;
    @FXML TableColumn<PostgraduateStudent, String> pGenderColumn;
    @FXML TableColumn<PostgraduateStudent, String> pEmailColumn;
    @FXML TableColumn<PostgraduateStudent, String> pNicColumn;
    @FXML TableColumn<PostgraduateStudent, String> pMobileColumn;
    
    // More details components
    @FXML Label studentIdLabel;
    @FXML JFXTextField nameTextField, addressLine1TextField, addressLine2TextField, addressLine3TextField, birthdayTextField, genderTextField,
                       emailTextField, nicTextField, mobileTextField, fixedTextField, facultyTextField, courseTextField, streamTextField, result1TextField,
                       result2TextField, result3TextField, rankTextField, zScoreTextField, qualificationTextField, instituteTextField, compYearTextField;
    @FXML Pane alResultPane, qualificationsPane;
    
    // This method will return an ObservableList of undergraduate students 
    public ObservableList<UndergraduateStudent> getUgStudents(){
        
        ObservableList<UndergraduateStudent> ugStudentList = FXCollections.observableArrayList();
        //Connection con = null;
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = "SELECT student_id, initials, first_name, last_name, address_line_1, address_line_2, address_line_3, birthday, gender, email, nic,"
                       + "mobile, fixed, faculty_name, course_name FROM undergraduate";
        
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            UndergraduateStudent ugStudents;
            
            while(rs.next()){
                ugStudents = new UndergraduateStudent(rs.getString("student_id"), rs.getString("initials"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("address_line_1"), rs.getString("address_line_2"), rs.getString("address_line_3"), rs.getString("birthday"), rs.getString("gender"),
                        rs.getString("email"), rs.getString("nic"), rs.getString("mobile"), rs.getString("fixed"), rs.getString("faculty_name"), rs.getString("course_name"));
                
                ugStudentList.add(ugStudents);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ugStudentList;
    }
    
    // This method will return an ObservableList of postgraduate students 
    public ObservableList<PostgraduateStudent> getPgStudents(){
        
        ObservableList<PostgraduateStudent> pgStudentList = FXCollections.observableArrayList();
        //Connection con = null;
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = "SELECT student_id, initials, first_name, last_name, address_line_1, address_line_2, address_line_3, birthday, gender, email, nic,"
                       + "mobile, fixed, faculty_name, course_name FROM postgraduate";
        
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            PostgraduateStudent pgStudents;
            
            while(rs.next()){
                pgStudents = new PostgraduateStudent(rs.getString("student_id"), rs.getString("initials"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("address_line_1"), rs.getString("address_line_2"), rs.getString("address_line_3"), rs.getString("birthday"), rs.getString("gender"),
                        rs.getString("email"), rs.getString("nic"), rs.getString("mobile"), rs.getString("fixed"), rs.getString("faculty_name"), rs.getString("course_name"));
                
                pgStudentList.add(pgStudents);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pgStudentList;
    }
    
    // This method will return an ObservableList of A/L results 
    public ObservableList<AlResult> getAlResult(){
        
        ObservableList<AlResult> alResultList = FXCollections.observableArrayList();
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = "SELECT stream, subject_1, result_1, subject_2, result_2, subject_3, result_3, rank, z_score FROM al_results";
        
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            AlResult resultForm;
            
            while(rs.next()){
                resultForm = new AlResult(rs.getString("stream"), rs.getString("subject_1"), rs.getString("result_1"), rs.getString("subject_2"),
                        rs.getString("result_2"), rs.getString("subject_3"), rs.getString("result_3"), rs.getInt("rank"), rs.getDouble("z_score"));
                
                alResultList.add(resultForm);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return alResultList;
    }
    
    // This method will return an ObservableList of qualification details
    public ObservableList<Qualifications> getQualifcationDetails(){
        
        ObservableList<Qualifications> qualificationDetails = FXCollections.observableArrayList();
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = "SELECT qualification_type, institute, completion_year FROM qualifications";
        
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Qualifications qualifications;
            
            while(rs.next()){
                qualifications = new Qualifications(rs.getString("qualification_type"), rs.getString("institute"), rs.getString("completion_year"));
                
                qualificationDetails.add(qualifications);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return qualificationDetails;
    }
    
    
//    // Update tables
//    public void updateTables(ObservableList oList){
//        // setup columns in the undergarduate table
//        idColumn.setCellValueFactory(new PropertyValueFactory<UndergraduateStudent, String> ("studentId"));
//        fNameColumn.setCellValueFactory(new PropertyValueFactory<UndergraduateStudent, String> ("firstName"));
//        birthdayColumn.setCellValueFactory(new PropertyValueFactory<UndergraduateStudent, String> ("birthday"));
//        genderColumn.setCellValueFactory(new PropertyValueFactory<UndergraduateStudent, String> ("gender"));
//        emailColumn.setCellValueFactory(new PropertyValueFactory<UndergraduateStudent, String> ("email"));
//        nicColumn.setCellValueFactory(new PropertyValueFactory<UndergraduateStudent, String> ("nic"));
//        mobileColumn.setCellValueFactory(new PropertyValueFactory<UndergraduateStudent, String> ("mobile"));
//        
//        // load the data into the undergraduate table
//        undergraduateTable.setItems(oList);
//    }
    
    // Load undergraduates' details
    public void selectTabU(){
        
        // setup columns in the undergarduate table
        idColumn.setCellValueFactory(new PropertyValueFactory<UndergraduateStudent, String> ("studentId"));
        fNameColumn.setCellValueFactory(new PropertyValueFactory<UndergraduateStudent, String> ("firstName"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<UndergraduateStudent, String> ("birthday"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<UndergraduateStudent, String> ("gender"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<UndergraduateStudent, String> ("email"));
        nicColumn.setCellValueFactory(new PropertyValueFactory<UndergraduateStudent, String> ("nic"));
        mobileColumn.setCellValueFactory(new PropertyValueFactory<UndergraduateStudent, String> ("mobile"));
        
        // load the data into the undergraduate table
        undergraduateTable.setItems(getUgStudents());
        
        undergraduatePane.setBackground(new Background(new BackgroundFill(Color.valueOf("#2B6CB7"), CornerRadii.EMPTY, Insets.EMPTY)));
        postgraduatePane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        undergraduateText.setFill(Color.valueOf("#FFFFFF"));
        postgraduateText.setFill(Color.valueOf("#7c7474"));
        ugAnchorPane.setVisible(true);
        pgAnchorPane.setVisible(false);
    }
    
    // Load postgraduates' details
    public void selectTabP(){
        
        // setup columns in the postgraduate table
        pIdColumn.setCellValueFactory(new PropertyValueFactory<PostgraduateStudent, String> ("studentId"));
        pfNameColumn.setCellValueFactory(new PropertyValueFactory<PostgraduateStudent, String> ("firstName"));
        pBirthdayColumn.setCellValueFactory(new PropertyValueFactory<PostgraduateStudent, String> ("birthday"));
        pGenderColumn.setCellValueFactory(new PropertyValueFactory<PostgraduateStudent, String> ("gender"));
        pEmailColumn.setCellValueFactory(new PropertyValueFactory<PostgraduateStudent, String> ("email"));
        pNicColumn.setCellValueFactory(new PropertyValueFactory<PostgraduateStudent, String> ("nic"));
        pMobileColumn.setCellValueFactory(new PropertyValueFactory<PostgraduateStudent, String> ("mobile"));
        
        // load the data into the postgraduate table
        postgraduateTable.setItems(getPgStudents());
        
        postgraduatePane.setBackground(new Background(new BackgroundFill(Color.valueOf("#2B6CB7"), CornerRadii.EMPTY, Insets.EMPTY)));
        undergraduatePane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        postgraduateText.setFill(Color.valueOf("#FFFFFF"));
        undergraduateText.setFill(Color.valueOf("#7c7474"));
        pgAnchorPane.setVisible(true);
        ugAnchorPane.setVisible(false);
    }
    
    // Go to student registration form
    public void stdRegisterButtonPressed(ActionEvent event) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/StudentRegistration.fxml"));
        studentAnchorPane.getChildren().setAll(pane);
    }

    // Switch to the choose/change subject pane
    public void chooseSubjects(){
        chooseSubjectsAnchorPane.setVisible(true);
        studentHomeAnchorPane.setVisible(false);
    }
    
    // Delete a record
    public void deleteButtonPressed(ActionEvent event) throws SQLException{
        PreparedStatement ps = null;
        String student_id = null;
        try{
            // Check whether student who going to be deleted is an undergraduate or postgraduate
            if(ugAnchorPane.isVisible()){
                ps = con.prepareStatement("DELETE FROM undergraduate WHERE student_id = ?");
                student_id = idColumn.getCellData(undergraduateTable.getSelectionModel().getSelectedItem());
            }else if(pgAnchorPane.isVisible()){
                ps = con.prepareStatement("DELETE FROM postgraduate WHERE student_id = ?");
                student_id = pIdColumn.getCellData(postgraduateTable.getSelectionModel().getSelectedItem());
            }
            
            if(student_id == null){
                // Inform message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a row");
                alert.showAndWait();
            }else{
                 // Confirmation message
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                alert.showAndWait();
                
                if(alert.getResult().getText().equals("OK")){
                    ps.setString(1, student_id);
                    ps.executeUpdate();
                }else{
                    ps.setString(1, null);
                    ps.executeUpdate();
                } 
            
            }

        }catch(SQLException e){
            e.printStackTrace();       
        }
        
    }
    
    // Variables for more details view
    int index;
    String id, name, addressLine1, addressLine2, addressLine3, birthday, gender, email, nic, mobile, fixed, faculty, course, stream, result1, result2, result3,
           qualification, institute, compYear;
    int rank;
    double zScore;
    // Switch to the more details/update pane
    public void moreDetails(){

        // full details about the selected student (this check that student is an undergraduate or a postgraduate)
        if(ugAnchorPane.isVisible()){
            index = undergraduateTable.getSelectionModel().selectedIndexProperty().get();
            
            try{  
                id = getUgStudents().get(index).getStudentId();
                name = getUgStudents().get(index).getInitials()+" "+ getUgStudents().get(index).getFirstName()+" "+ getUgStudents().get(index).getLastName();
                addressLine1 = getUgStudents().get(index).getAddressLine1();
                addressLine2 = getUgStudents().get(index).getAddressLine2();
                addressLine3 = getUgStudents().get(index).getAddressLine3();
                birthday = getUgStudents().get(index).getBirthday();
                gender = getUgStudents().get(index).getGender();
                email = getUgStudents().get(index).getEmail();
                nic = getUgStudents().get(index).getNic();
                mobile = getUgStudents().get(index).getMobile();
                fixed = getUgStudents().get(index).getFixed();
                faculty = getUgStudents().get(index).getFacultyName();
                course = getUgStudents().get(index).getCourseName();
            
                stream = getAlResult().get(index).getStream();
                result1 = getAlResult().get(index).getResult1();
                result2 = getAlResult().get(index).getResult2();
                result3 = getAlResult().get(index).getResult3();
                rank = getAlResult().get(index).getRank();
                zScore = getAlResult().get(index).getzScore();
                
                detailsAndUpdateAnchorPane.setVisible(true);
                studentHomeAnchorPane.setVisible(false);
                alResultPane.setVisible(true);
                qualificationsPane.setVisible(false);
            
                fillTextFields();
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a student");
                alert.showAndWait();
            }
            
        }else if(pgAnchorPane.isVisible()){
            index = postgraduateTable.getSelectionModel().selectedIndexProperty().get();
            
            try{
                id = getPgStudents().get(index).getStudentId();
                name = getPgStudents().get(index).getInitials()+" "+ getPgStudents().get(index).getFirstName()+" "+ getPgStudents().get(index).getLastName();
                addressLine1 = getPgStudents().get(index).getAddressLine1();
                addressLine2 = getPgStudents().get(index).getAddressLine2();
                addressLine3 = getPgStudents().get(index).getAddressLine3();
                birthday = getPgStudents().get(index).getBirthday();
                gender = getPgStudents().get(index).getGender();
                email = getPgStudents().get(index).getEmail();
                nic = getPgStudents().get(index).getNic();
                mobile = getPgStudents().get(index).getMobile();
                fixed = getPgStudents().get(index).getFixed();
                faculty = getPgStudents().get(index).getFacultyName();
                course = getPgStudents().get(index).getCourseName();
                
                qualification = getQualifcationDetails().get(index).getQualification();
                institute = getQualifcationDetails().get(index).getInstitute();
                compYear = getQualifcationDetails().get(index).getCompYear();
                
                detailsAndUpdateAnchorPane.setVisible(true);
                studentHomeAnchorPane.setVisible(false);
                qualificationsPane.setVisible(true);
                alResultPane.setVisible(false);

                fillTextFields();
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a student");
                alert.showAndWait();
            }
        }  
    }
  
    // Fill text fields with data
    public void fillTextFields(){

        studentIdLabel.setText(id);
        nameTextField.setText(name);
        addressLine1TextField.setText(addressLine1);
        addressLine2TextField.setText(addressLine2);
        addressLine3TextField.setText(addressLine3);
        birthdayTextField.setText(birthday);
        genderTextField.setText(gender);
        emailTextField.setText(email);
        nicTextField.setText(nic);
        mobileTextField.setText(mobile);
        fixedTextField.setText(fixed);
        facultyTextField.setText(faculty);
        courseTextField.setText(course);
        
        streamTextField.setText(stream);
        result1TextField.setText(result1);
        result2TextField.setText(result2);
        result3TextField.setText(result3);
        rankTextField.setText(Integer.toString(rank));
        zScoreTextField.setText(Double.toString(zScore));

        qualificationTextField.setText(qualification);
        instituteTextField.setText(institute);
        compYearTextField.setText(compYear);
    }
    
    // Allow to edit text fields
    public void enableOrDisableTextFields(boolean value){
        addressLine1TextField.setEditable(value);
        addressLine2TextField.setEditable(value);
        addressLine3TextField.setEditable(value);
        emailTextField.setEditable(value);
        mobileTextField.setEditable(value);
        fixedTextField.setEditable(value);
    }
    
    // Enable text fields for edit details
    byte editbuttonClicked = 0;
    public void updateDetails(){
        enableOrDisableTextFields(true);
        editbuttonClicked = 1;
    }

    // Update students' details
    public void updateStudentsDetails() throws SQLException{
        
        PreparedStatement ps;
        String query = null;
        
        if(ugAnchorPane.isVisible()){
            query = "UPDATE undergraduate SET address_line_1 = ?, address_line_2 = ?, address_line_3 = ?, email = ?, mobile = ?, fixed = ? WHERE student_id = ?";
        }else if(pgAnchorPane.isVisible()){
            query = "UPDATE postgraduate SET address_line_1 = ?, address_line_2 = ?, address_line_3 = ?, email = ?, mobile = ?, fixed = ? WHERE student_id = ?";   
        }
        ps = con.prepareStatement(query);
        
        ps.setString(1, addressLine1TextField.getText());
        ps.setString(2, addressLine2TextField.getText());
        ps.setString(3, addressLine3TextField.getText());
        ps.setString(4, emailTextField.getText());
        ps.setString(5, mobileTextField.getText());
        ps.setString(6, fixedTextField.getText());
        ps.setString(7, studentIdLabel.getText());
        
        ps.executeUpdate();
        
        studentHomeAnchorPane.setVisible(true);
        detailsAndUpdateAnchorPane.setVisible(false);
        enableOrDisableTextFields(false);
        
        if(editbuttonClicked == 1){
            editbuttonClicked = 0;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Details Updated Successfully");
            alert.showAndWait();
        }   
    }
    
    // Back to student pane
    public void backToStudent(MouseEvent event){
        studentHomeAnchorPane.setVisible(true);
        
        // Identify which pane going to be invisible
        if(event.getTarget() == backFromSubjects){
            chooseSubjectsAnchorPane.setVisible(false);
        }else if(event.getTarget() == backFromMoreDetails){
            detailsAndUpdateAnchorPane.setVisible(false);
        }  
    }
    
    // Save choosed subjects in the database
    public void saveSubjects(){
        // data save process code here
        studentHomeAnchorPane.setVisible(true);
        chooseSubjectsAnchorPane.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Default view is undergraduate
        selectTabU();
    }
}
