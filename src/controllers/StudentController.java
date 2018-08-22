package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import database.dbConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.PostgraduateStudent;
import models.UndergraduateStudent;
import static nsbm.NSBM.changeTabColors;
import static nsbm.NSBM.alerts;

public class StudentController implements Initializable {

    private Connection con;
    private char student = 'u';
    
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
    
    // Subject selection view componenets
    @FXML JFXTextField sem1CompSubject1, sem1CompSubject2, sem1CompSubject3, sem2CompSubject1, sem2CompSubject2, sem2CompSubject3;
    @FXML JFXComboBox sem1OpSubject1, sem1OpSubject2, sem1OpSubject3, sem1OpSubject4, semIdComboBox1;
    @FXML JFXComboBox sem2OpSubject1, sem2OpSubject2, sem2OpSubject3, sem2OpSubject4, semIdComboBox2;
    @FXML Text studentIdText, sem1TotalCreditText, sem2TotalCreditText, sem1Amount, sem2Amount;
    @FXML JFXButton sem1ConfirmButton, sem2ConfirmButton;
    @FXML Button resetButton;
    private int sem1TotalCredit, sem2TotalCredit = 0;
    private double sem1TotalFee, sem2TotalFee = 0;
    
    // fillCompulsory, fillOptional methods' variables
    private PreparedStatement psSemester1, psSemester2;
    private ResultSet rsSemester1, rsSemester2;
    private String subject;
    
    // Subject change variables
    private PreparedStatement psSubject;
    private PreparedStatement psSemester;
    
    // More details components
    @FXML Text subject1Text, subject2Text, subject3Text, studentIdText2;
    @FXML JFXTextField nameTextField, addressLine1TextField, addressLine2TextField, addressLine3TextField, birthdayTextField, genderTextField,
                       emailTextField, nicTextField, mobileTextField, fixedTextField, facultyTextField, courseTextField, streamTextField, result1TextField,
                       result2TextField, result3TextField, rankTextField, zScoreTextField, qualificationTextField, instituteTextField, compYearTextField;
    @FXML AnchorPane alResultAnchorPane, qualificationsAnchorPane;
    
    // Variables for more details view
    private String id, name, addressLine1, addressLine2, addressLine3, birthday, gender, email, nic, mobile, fixed, faculty, course, stream, subject1, subject2, subject3, result1, result2, result3,
           qualification, institute, compYear;
    private int rank;
    private double zScore;
    
    // This method will return an ObservableList of undergraduate students 
    public ObservableList<UndergraduateStudent> getUgStudents(){
        ObservableList<UndergraduateStudent> ugStudentList = FXCollections.observableArrayList();
        String query = "SELECT * FROM undergraduate";
        
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
        String query = "SELECT * FROM postgraduate";
        
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
    
    // Update tables
    public void updateTables(){
        undergraduateTable.setItems(null);
        undergraduateTable.setItems(getUgStudents());
        
        postgraduateTable.setItems(null);
        postgraduateTable.setItems(getPgStudents());
    }
    
    // View undergraduates' details
    public void selectTabU(){
        student = 'u';
        
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
        
        changeTabColors(undergraduatePane, postgraduatePane, undergraduateText, postgraduateText, ugAnchorPane, pgAnchorPane); 
    }
    
    // View postgraduates' details
    public void selectTabP(){
        student = 'p';
        
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
        
        changeTabColors(postgraduatePane, undergraduatePane, postgraduateText, undergraduateText, pgAnchorPane, ugAnchorPane);
    }
    
    // Go to student registration form
    public void stdRegisterButtonPressed(ActionEvent event) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/StudentRegistration.fxml"));
        studentAnchorPane.getChildren().setAll(pane);
    }
    
    // Enable/Disable combo boxes for selections
    public void enableComboBoxes(boolean x){
        semIdComboBox1.setMouseTransparent(x);
        semIdComboBox2.setMouseTransparent(x);
        sem1OpSubject1.setMouseTransparent(x);
        sem1OpSubject2.setMouseTransparent(x);
        sem1OpSubject3.setMouseTransparent(x);
        sem1OpSubject4.setMouseTransparent(x);
        sem2OpSubject1.setMouseTransparent(x);
        sem2OpSubject2.setMouseTransparent(x);
        sem2OpSubject3.setMouseTransparent(x);
        sem2OpSubject4.setMouseTransparent(x);
    }

    // Switch to the choose/change subject view
    public void chooseSubjects(){
        // Reset button's tooltip
        Tooltip tooltip = new Tooltip("Reset");
        resetButton.setTooltip(tooltip);
        
        sem1TotalCredit=0; 
        sem2TotalCredit = 0;
        sem1TotalFee = 0; 
        sem2TotalFee = 0;
        sem1TotalCreditText.setText("0"); 
        sem2TotalCreditText.setText("0");
        sem1Amount.setText("00.00"); 
        sem2Amount.setText("00.00");
        
        try{
            if(student == 'u'){
                studentIdText.setText(undergraduateTable.getSelectionModel().getSelectedItem().getStudentId());
            }else if(student == 'p'){
                studentIdText.setText(postgraduateTable.getSelectionModel().getSelectedItem().getStudentId());
            }
            fillSemester();
            fillCompulsory();
            fillOptional();
            showMySubjects();
            chooseSubjectsAnchorPane.setVisible(true);
            studentHomeAnchorPane.setVisible(false);
        }catch(NullPointerException e){
            // Error message
            alerts('E', "Message", null, "Please select a student");
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        enableComboBoxes(false);
    }
    
    // Fill semester combo boxes
    public void fillSemester(){
        semIdComboBox1.getItems().clear();
        semIdComboBox2.getItems().clear();
        
        try{
            PreparedStatement ps1 = con.prepareStatement("SELECT semester_id FROM semester WHERE semester_id LIKE '%S01'");
            PreparedStatement ps2 = con.prepareStatement("SELECT semester_id FROM semester WHERE semester_id LIKE '%S02'");
            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            
            while(rs1.next()){
                semIdComboBox1.getItems().addAll(rs1.getString("semester_id"));
            }
            while(rs2.next()){
                semIdComboBox2.getItems().addAll(rs2.getString("semester_id"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    // Add compulsory subjects to combo box
    public void fillCompulsory(){
        sem1CompSubject1.clear();
        sem1CompSubject2.clear();
        sem1CompSubject3.clear();
        sem2CompSubject1.clear();
        sem2CompSubject2.clear();
        sem2CompSubject3.clear();
        
        ArrayList<String> sem1CompSubjectList = new ArrayList<String>();
        ArrayList<String> sem2CompSubjectList = new ArrayList<String>();
        try {
            if(student == 'u'){
                psSemester1 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN undergraduate ON subject.b_course_name = undergraduate.course_name WHERE subject.compulsory=1 AND year_semester LIKE '%S1' AND student_id=?");
                psSemester2 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN undergraduate ON subject.b_course_name = undergraduate.course_name WHERE subject.compulsory=1 AND year_semester LIKE '%S2' AND student_id=?");
                psSemester1.setString(1, studentIdText.getText());
                psSemester2.setString(1, studentIdText.getText());
                
                rsSemester1 = psSemester1.executeQuery();
                rsSemester2 = psSemester2.executeQuery();
                while (rsSemester1.next()) {
                    sem1CompSubjectList.add(rsSemester1.getString("subject_name"));
                    creditListenerBody(rsSemester1.getString("subject_name"), 1);
                }
                while (rsSemester2.next()) {
                    sem2CompSubjectList.add(rsSemester2.getString("subject_name"));
                    creditListenerBody(rsSemester2.getString("subject_name"), 2);
                }
                
                sem1CompSubject1.setText(sem1CompSubjectList.get(0));
                sem1CompSubject2.setText(sem1CompSubjectList.get(1));
                sem1CompSubject3.setText(sem1CompSubjectList.get(2));
                sem2CompSubject1.setText(sem2CompSubjectList.get(0));
                sem2CompSubject2.setText(sem2CompSubjectList.get(1));
                sem2CompSubject3.setText(sem2CompSubjectList.get(2));
                
            }else if(student == 'p'){
                psSemester1 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN postgraduate ON subject.m_course_name = postgraduate.course_name WHERE subject.compulsory=1 AND year_semester LIKE '%S1' AND student_id=?");
                psSemester2 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN postgraduate ON subject.m_course_name = postgraduate.course_name WHERE subject.compulsory=1 AND year_semester LIKE '%S2' AND student_id=?");
                psSemester1.setString(1, studentIdText.getText());
                psSemester2.setString(1, studentIdText.getText());

                rsSemester1 = psSemester1.executeQuery();
                rsSemester2 = psSemester2.executeQuery();
                while (rsSemester1.next()) {
                    sem1CompSubjectList.add(rsSemester1.getString("subject_name"));
                    creditListenerBody(rsSemester1.getString("subject_name"), 1);
                }
                while (rsSemester2.next()) {
                    sem2CompSubjectList.add(rsSemester2.getString("subject_name"));
                    creditListenerBody(rsSemester2.getString("subject_name"), 2);
                }
                
                sem1CompSubject1.setText(sem1CompSubjectList.get(0));
                sem1CompSubject2.setText(sem1CompSubjectList.get(1));
                sem1CompSubject3.setText(sem1CompSubjectList.get(2));
                sem2CompSubject1.setText(sem2CompSubjectList.get(0));
                sem2CompSubject2.setText(sem2CompSubjectList.get(1));
                sem2CompSubject3.setText(sem2CompSubjectList.get(2));
            } 
        }catch (Exception ex) {
            ex.printStackTrace();
            // Error message
            alerts('E', "Message", null, "Compulsory subjects are not found");
        }
    }
    
    // Add optional subjects to combo box
    public void fillOptional(){
        sem1OpSubject1.getItems().clear();
        sem1OpSubject2.getItems().clear();
        sem1OpSubject3.getItems().clear();
        sem1OpSubject4.getItems().clear();
        sem2OpSubject1.getItems().clear();
        sem2OpSubject2.getItems().clear();
        sem2OpSubject3.getItems().clear();
        sem2OpSubject4.getItems().clear();
        
        try {
            if(student == 'u'){
                psSemester1 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN undergraduate ON subject.b_course_name = undergraduate.course_name WHERE subject.compulsory=0 AND year_semester LIKE '%S1' AND student_id=?");
                psSemester2 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN undergraduate ON subject.b_course_name = undergraduate.course_name WHERE subject.compulsory=0 AND year_semester LIKE '%S2' AND student_id=?");
                psSemester1.setString(1, studentIdText.getText());
                psSemester2.setString(1, studentIdText.getText());
                
                rsSemester1 = psSemester1.executeQuery();
                rsSemester2 = psSemester2.executeQuery();
                while (rsSemester1.next()) {
                    subject = rsSemester1.getString("subject_name");
                    sem1OpSubject1.getItems().addAll(subject);
                    sem1OpSubject2.getItems().addAll(subject);
                    sem1OpSubject3.getItems().addAll(subject);
                    sem1OpSubject4.getItems().addAll(subject);
                }
                while (rsSemester2.next()) {
                    subject = rsSemester2.getString("subject_name");
                    sem2OpSubject1.getItems().addAll(subject);
                    sem2OpSubject2.getItems().addAll(subject);
                    sem2OpSubject3.getItems().addAll(subject);
                    sem2OpSubject4.getItems().addAll(subject);
                }
            }else if(student == 'p'){
                psSemester1 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN postgraduate ON subject.m_course_name = postgraduate.course_name WHERE subject.compulsory=0 AND year_semester LIKE '%S1' AND student_id=?");
                psSemester2 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN postgraduate ON subject.m_course_name = postgraduate.course_name WHERE subject.compulsory=0 AND year_semester LIKE '%S2' AND student_id=?");
                psSemester1.setString(1, studentIdText.getText());
                psSemester2.setString(1, studentIdText.getText());
                
                rsSemester1 = psSemester1.executeQuery();
                rsSemester2 = psSemester2.executeQuery();
                while (rsSemester1.next()) {
                    subject = rsSemester1.getString("subject_name");
                    sem1OpSubject1.getItems().addAll(subject);
                    sem1OpSubject2.getItems().addAll(subject);
                    sem1OpSubject3.getItems().addAll(subject);
                    sem1OpSubject4.getItems().addAll(subject);
                }
                while (rsSemester2.next()) {
                    subject = rsSemester2.getString("subject_name");
                    sem2OpSubject1.getItems().addAll(subject);
                    sem2OpSubject2.getItems().addAll(subject);
                    sem2OpSubject3.getItems().addAll(subject);
                    sem2OpSubject4.getItems().addAll(subject);
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Listening for total credits and fees
    public void creditListenerBody(String newValue, int semester){ 
        PreparedStatement psSubjectCode = null;
        ResultSet rsSubCode = null;
        ResultSet rs = null;
            try { 
                psSubjectCode = con.prepareStatement("SELECT subject_code FROM subject WHERE subject_name=?");
                psSubjectCode.setString(1, newValue);
                rsSubCode = psSubjectCode.executeQuery();
                
                while(rsSubCode.next()){  
                    PreparedStatement psCredit = con.prepareStatement("SELECT credit, fee FROM subject WHERE subject_code=?");
                    psCredit.setString(1, rsSubCode.getString("subject_code"));
                    rs = psCredit.executeQuery();
                    
                    while (rs.next()) {
                        int credit = rs.getInt("credit");
                        int fee = rs.getInt("fee");
                        if(semester == 1){
                            sem1TotalCredit += credit;
                            sem1TotalFee += fee;
                            sem1TotalCreditText.setText(Integer.toString(sem1TotalCredit));
                            sem1Amount.setText(Double.toString(sem1TotalFee));
                        }else if(semester == 2){
                            sem2TotalCredit += credit;
                            sem2TotalFee += fee;
                            sem2TotalCreditText.setText(Integer.toString(sem2TotalCredit));
                            sem2Amount.setText(Double.toString(sem2TotalFee));
                        }  
                    }       
                }     
            }catch (SQLException ex) {
                Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    // Reset subject selection form
    public void resetButtonPressed(){
        chooseSubjects();
    }
    
    // Change selected subjects [semseter 1]
    public void sem1ChangeButtonPressed() throws SQLException{
//        if(student == 'u'){
//            psSubject = con.prepareStatement("DELETE FROM undergraduate_subject WHERE student_id=?");
//            psSemester = con.prepareStatement("DELETE FROM undergraduate_semester WHERE student_id=?");
//        }else if(student == 'p'){
//            psSubject = con.prepareStatement("DELETE FROM undergraduate_subject WHERE student_id=?");
//        }
//        psSemester1.setString(1, studentIdText.getText());
//        psSemester1.executeUpdate();
    }
    
    // Change selected subjects [semseter 2]
    public void sem2ChangeButtonPressed() throws SQLException{

    }
    
    // Get minimum credit for a semster
    public int getMinimumCredit(int duration){
        switch (duration) {
            case 2:
                return 12;
            case 3:
                return 15;
            case 4:
                return 16;
            default:
                break;
        }
        return 0;
    }

    // Assign subjects
    public void confirmButtonPressed(ActionEvent event){
        String subjectCode = null;
        int courseDuration = 0;
        List subjects = new ArrayList();
        List semesters = new ArrayList();
        List fees = new ArrayList();
        
        PreparedStatement psSubjectCode = null;
        PreparedStatement psInsert1 = null;
        PreparedStatement psInsert2 = null;
        PreparedStatement psDuration = null;
        ResultSet rsDuration = null;
        
        try{
            if(ugAnchorPane.isVisible()){
                course = undergraduateTable.getSelectionModel().getSelectedItem().getCourseName();
                psDuration = con.prepareStatement("SELECT duration FROM bachelor WHERE course_name='"+ course +"'");
            }else if(pgAnchorPane.isVisible()){
                course = postgraduateTable.getSelectionModel().getSelectedItem().getCourseName();
                psDuration = con.prepareStatement("SELECT duration FROM master WHERE course_name='"+ course +"'");
            }
            rsDuration = psDuration.executeQuery();
            while(rsDuration.next()){
                courseDuration = rsDuration.getInt("duration");
            }
            
            // Store selected subject names in a list
            if(event.getTarget() == sem1ConfirmButton && sem1TotalCredit >= getMinimumCredit(courseDuration)){
                if(semIdComboBox1.getValue() != null){
                    semesters.add(semIdComboBox1.getValue().toString());
                    fees.add(sem1Amount.getText());
                    subjects.add(sem1CompSubject1.getText());
                    subjects.add(sem1CompSubject2.getText());
                    subjects.add(sem1CompSubject3.getText());
                    subjects.add(sem1OpSubject1.getValue().toString());
                    subjects.add(sem1OpSubject2.getValue().toString());
                    subjects.add(sem1OpSubject3.getValue().toString());
                    try{
                        subjects.add(sem1OpSubject4.getValue().toString());
                    }catch(NullPointerException ex){}
                }else{
                    // Error message
                    alerts('E', "Message", null, "Please select a semester");
                }
            }else if(event.getTarget() == sem2ConfirmButton && sem2TotalCredit >= getMinimumCredit(courseDuration)){  
                if(semIdComboBox2.getValue() != null){
                    semesters.add(semIdComboBox2.getValue().toString());
                    fees.add(sem2Amount.getText());
                    subjects.add(sem2CompSubject1.getText());
                    subjects.add(sem2CompSubject2.getText());
                    subjects.add(sem2CompSubject3.getText());
                    subjects.add(sem2OpSubject1.getValue().toString());
                    subjects.add(sem2OpSubject2.getValue().toString());
                    subjects.add(sem2OpSubject3.getValue().toString());
                    try{
                        subjects.add(sem2OpSubject4.getValue().toString());
                    }catch(NullPointerException ex){}
                }else{
                    // Error message
                    alerts('E', "Message", null, "Please select a semester");
                }
            }else{
                // Error message
                alerts('I', "Message", null, "You must select at least " + getMinimumCredit(courseDuration) + " credits");
            }

            psSubjectCode = con.prepareStatement("SELECT subject_code FROM subject WHERE subject_name=?");
            for(int i=0; i<subjects.size(); i++){
                psSubjectCode.setString(1, subjects.get(i).toString());
                ResultSet rs = psSubjectCode.executeQuery();

                while(rs.next()){
                    subjectCode = rs.getString("subject_code");
                }

                if(student == 'u'){
                    psInsert1 = con.prepareStatement("INSERT INTO undergraduate_subject(student_id, subject_code)" + "VALUES(?,?)");    
                }else if(student == 'p'){
                    psInsert1 = con.prepareStatement("INSERT INTO postgraduate_subject(student_id, subject_code)" + "VALUES(?,?)");             
                }
                psInsert1.setString(1, studentIdText.getText());
                psInsert1.setString(2, subjectCode);
                psInsert1.executeUpdate();
            }
            for(int i=0; i<semesters.size(); i++){
                if(student == 'u'){
                    psInsert2 = con.prepareStatement("INSERT INTO undergraduate_semester(semester_id, student_id, amount)" + "VALUES(?,?,?)");
                }else if(student == 'p'){
                    psInsert2 = con.prepareStatement("INSERT INTO postgraduate_semester(semester_id, student_id, amount)" + "VALUES(?,?,?)");
                }
                psInsert2.setString(1, semesters.get(i).toString());
                psInsert2.setString(2, studentIdText.getText());
                psInsert2.setDouble(3, Double.parseDouble(fees.get(i).toString()));
                psInsert2.executeUpdate();
                
                // Information message
                alerts('I', "Messsage", null, "Successfully updated");
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
             // Error message
            alerts('E', "Message", null, "Please fill all the fields correctly");
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Error message
            alerts('E', "Message", null, "Subjects are already selected for this student");
        }
    }
    
    // Show details about choosed subjects 
    public void showMySubjects() throws SQLException{
        ArrayList<String> subjectCodes1 = new ArrayList<String>();
        ArrayList<String> subjectCodes2 = new ArrayList<String>();
        ArrayList<String> semesters = new ArrayList<String>();
        String studentId = studentIdText.getText();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        
        if(student == 'u'){
            ps1 = con.prepareStatement("SELECT subject_name FROM subject INNER JOIN undergraduate_subject ON subject.subject_code = undergraduate_subject.subject_code WHERE subject.compulsory=0 AND year_semester LIKE '%S1' AND undergraduate_subject.student_id=?");
            ps2 = con.prepareStatement("SELECT subject_name FROM subject INNER JOIN undergraduate_subject ON subject.subject_code = undergraduate_subject.subject_code WHERE subject.compulsory=0 AND year_semester LIKE '%S2' AND undergraduate_subject.student_id=?");
            ps3 = con.prepareStatement("SELECT semester_id FROM undergraduate_semester WHERE student_id=?");
            ps1.setString(1, studentId);
            ps2.setString(1, studentId);
            ps3.setString(1, studentId);
        }else if(student == 'p'){
            ps1 = con.prepareStatement("SELECT subject_name FROM subject INNER JOIN postgraduate_subject ON subject.subject_code = postgraduate_subject.subject_code WHERE subject.compulsory=0 AND year_semester LIKE '%S1' AND postgraduate_subject.student_id=?");
            ps2 = con.prepareStatement("SELECT subject_name FROM subject INNER JOIN postgraduate_subject ON subject.subject_code = postgraduate_subject.subject_code WHERE subject.compulsory=0 AND year_semester LIKE '%S2' AND postgraduate_subject.student_id=?");
            ps3 = con.prepareStatement("SELECT semester_id FROM postgraduate_semester WHERE student_id=?");
            ps1.setString(1, studentId);
            ps2.setString(1, studentId);
            ps3.setString(1, studentId);
        }

        ResultSet rs1 = ps1.executeQuery();
        ResultSet rs2 = ps2.executeQuery();
        ResultSet rs3 = ps3.executeQuery();
        
        while(rs1.next()){
            subjectCodes1.add(rs1.getString("subject_name"));
        }
        while(rs2.next()){
            subjectCodes2.add(rs2.getString("subject_name"));
        }
        while(rs3.next()){
            semesters.add(rs3.getString("semester_id"));
        }
        try{
            semIdComboBox1.setValue(semesters.get(0));
            sem1OpSubject1.setValue(subjectCodes1.get(0));
            sem1OpSubject2.setValue(subjectCodes1.get(1));
            sem1OpSubject3.setValue(subjectCodes1.get(2));
            sem1OpSubject4.setValue(subjectCodes1.get(3));
        }catch(Exception ex){
            //ex.printStackTrace();
        }
        try{
            semIdComboBox2.setValue(semesters.get(1));
            sem2OpSubject1.setValue(subjectCodes2.get(0));
            sem2OpSubject2.setValue(subjectCodes2.get(1));
            sem2OpSubject3.setValue(subjectCodes2.get(2));
            sem2OpSubject4.setValue(subjectCodes2.get(3));
        }catch(Exception ex){
            //ex.printStackTrace();
        }
    }
    
    // Remove student from the system
    public void deleteButtonPressed(ActionEvent event) throws SQLException{
        PreparedStatement ps = null;
        String studentId = null;
        try{
            // Check whether student who going to be deleted is an undergraduate or postgraduate
            if(ugAnchorPane.isVisible()){
                ps = con.prepareStatement("DELETE FROM undergraduate WHERE student_id = ?");
                studentId = idColumn.getCellData(undergraduateTable.getSelectionModel().getSelectedItem());
            }else if(pgAnchorPane.isVisible()){
                ps = con.prepareStatement("DELETE FROM postgraduate WHERE student_id = ?");
                studentId = pIdColumn.getCellData(postgraduateTable.getSelectionModel().getSelectedItem());
            }
            
            if(studentId == null){
                // Information message
                alerts('I', "Message", null, "Please Select a Record");
            }else{
                 // Confirmation message
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Are You Sure?");
                alert.showAndWait();
                
                if(alert.getResult().getText().equals("OK")){
                    ps.setString(1, studentId);
                    ps.executeUpdate();
                }else{
                    ps.setString(1, null);
                    ps.executeUpdate();
                } 
            } 
            updateTables();
            
        }catch(SQLException e){
            e.printStackTrace();       
        }
    }
    
    // Switch to the more details/update view
    public void moreDetails(){
        PreparedStatement ps;
        ResultSet rs;
        // full details about the selected student (this check that student is an undergraduate or a postgraduate)
        if(ugAnchorPane.isVisible()){
            try{  
                id = undergraduateTable.getSelectionModel().getSelectedItem().getStudentId();
                name = undergraduateTable.getSelectionModel().getSelectedItem().getInitials()+" "+ undergraduateTable.getSelectionModel().getSelectedItem().getFirstName()+" "+ undergraduateTable.getSelectionModel().getSelectedItem().getLastName();
                addressLine1 = undergraduateTable.getSelectionModel().getSelectedItem().getAddressLine1();
                addressLine2 = undergraduateTable.getSelectionModel().getSelectedItem().getAddressLine2();
                addressLine3 = undergraduateTable.getSelectionModel().getSelectedItem().getAddressLine3();
                birthday = undergraduateTable.getSelectionModel().getSelectedItem().getBirthday();
                gender = undergraduateTable.getSelectionModel().getSelectedItem().getGender();
                email = undergraduateTable.getSelectionModel().getSelectedItem().getEmail();
                nic = undergraduateTable.getSelectionModel().getSelectedItem().getNic();
                mobile = undergraduateTable.getSelectionModel().getSelectedItem().getMobile();
                fixed = undergraduateTable.getSelectionModel().getSelectedItem().getFixed();
                faculty = undergraduateTable.getSelectionModel().getSelectedItem().getFacultyName();
                course = undergraduateTable.getSelectionModel().getSelectedItem().getCourseName();
                
                ps = con.prepareStatement("SELECT * FROM al_results WHERE student_id= '"+ id +"' ");
                rs = ps.executeQuery();
                
                while(rs.next()){
                    stream = rs.getString("stream");
                    subject1 = rs.getString("subject_1");
                    subject2 = rs.getString("subject_2");
                    subject3 = rs.getString("subject_3");
                    result1 = rs.getString("result_1");
                    result2 = rs.getString("result_2");
                    result3 = rs.getString("result_3");
                    rank = rs.getInt("rank");
                    zScore = rs.getDouble("z_score");
                }
                
                detailsAndUpdateAnchorPane.setVisible(true);
                studentHomeAnchorPane.setVisible(false);
                alResultAnchorPane.setVisible(true);
                qualificationsAnchorPane.setVisible(false);
            
                fillTextFields();
            }catch(SQLException e){
                // Error message
                alerts('E', "Message", null, "Please select a student");
            }
            
        }else if(pgAnchorPane.isVisible()){ 
            try{
                id = postgraduateTable.getSelectionModel().getSelectedItem().getStudentId();
                name = postgraduateTable.getSelectionModel().getSelectedItem().getInitials()+" "+ postgraduateTable.getSelectionModel().getSelectedItem().getFirstName()+" "+ postgraduateTable.getSelectionModel().getSelectedItem().getLastName();
                addressLine1 = postgraduateTable.getSelectionModel().getSelectedItem().getAddressLine1();
                addressLine2 = postgraduateTable.getSelectionModel().getSelectedItem().getAddressLine2();
                addressLine3 = postgraduateTable.getSelectionModel().getSelectedItem().getAddressLine3();
                birthday = postgraduateTable.getSelectionModel().getSelectedItem().getBirthday();
                gender = postgraduateTable.getSelectionModel().getSelectedItem().getGender();
                email = postgraduateTable.getSelectionModel().getSelectedItem().getEmail();
                nic = postgraduateTable.getSelectionModel().getSelectedItem().getNic();
                mobile = postgraduateTable.getSelectionModel().getSelectedItem().getMobile();
                fixed = postgraduateTable.getSelectionModel().getSelectedItem().getFixed();
                faculty = postgraduateTable.getSelectionModel().getSelectedItem().getFacultyName();
                course = postgraduateTable.getSelectionModel().getSelectedItem().getCourseName();
                
                ps = con.prepareStatement("SELECT * FROM qualifications WHERE student_id= '"+ id +"' ");
                rs = ps.executeQuery();
                
                while(rs.next()){
                    qualification = rs.getString("qualification_type");
                    institute = rs.getString("institute");
                    compYear = rs.getString("completion_year");
                }
                
                detailsAndUpdateAnchorPane.setVisible(true);
                studentHomeAnchorPane.setVisible(false);
                qualificationsAnchorPane.setVisible(true);
                alResultAnchorPane.setVisible(false);

                fillTextFields();
            }catch(SQLException e){
                // Error message
                alerts('E', "Message", null, "Please select a student");
            }
        }  
    }
  
    // Fill text fields
    public void fillTextFields(){
        studentIdText2.setText(id);
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
        subject1Text.setText(subject1);
        subject2Text.setText(subject2);
        subject3Text.setText(subject3);
        result1TextField.setText(result1);
        result2TextField.setText(result2);
        result3TextField.setText(result3);
        rankTextField.setText(Integer.toString(rank));
        zScoreTextField.setText(Double.toString(zScore));

        qualificationTextField.setText(qualification);
        instituteTextField.setText(institute);
        compYearTextField.setText(compYear);
    }
    
    // Allow/Not Allow to edit text fields
    public void enableOrDisableTextFields(boolean value){
        addressLine1TextField.setEditable(value);
        addressLine2TextField.setEditable(value);
        addressLine3TextField.setEditable(value);
        emailTextField.setEditable(value);
        mobileTextField.setEditable(value);
        fixedTextField.setEditable(value);
    }
    
    // Enable text fields for edit details
    private byte editbuttonClicked = 0;
    public void updateDetails(){
        enableOrDisableTextFields(true);
        editbuttonClicked = 1;
    }

    // Update students' details
    public void updateStudentsDetails() throws SQLException{
        PreparedStatement ps;
        String query = null;
        
        if(editbuttonClicked == 1){
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
            ps.setString(7, studentIdText2.getText());
            ps.executeUpdate();

            editbuttonClicked = 0;
            // Information message
            alerts('I', "Message", null, "Details updated successfully");
        }
        studentHomeAnchorPane.setVisible(true);
        detailsAndUpdateAnchorPane.setVisible(false);
        enableOrDisableTextFields(false);  
    }
    
    // Back to student view
    public void backToStudent(MouseEvent event){
        studentHomeAnchorPane.setVisible(true);
        
        // Identify which pane going to be invisible
        if(event.getTarget() == backFromSubjects){
            chooseSubjectsAnchorPane.setVisible(false);
        }else if(event.getTarget() == backFromMoreDetails){
            detailsAndUpdateAnchorPane.setVisible(false);
        }  
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Default view is undergraduate
        selectTabU();

        sem1OpSubject1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 1);
                sem1OpSubject1.setMouseTransparent(true);
            } 
        });
        sem1OpSubject2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 1);
                sem1OpSubject2.setMouseTransparent(true);
            } 
        });
        sem1OpSubject3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 1);
                sem1OpSubject3.setMouseTransparent(true);
            } 
        });
        sem1OpSubject4.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 1);
                sem1OpSubject4.setMouseTransparent(true);
            } 
        });
        
        sem2OpSubject1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 2);
                sem2OpSubject1.setMouseTransparent(true);
            } 
        });
        sem2OpSubject2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 2);
                sem2OpSubject2.setMouseTransparent(true);
            } 
        });
        sem2OpSubject3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 2);
                sem2OpSubject3.setMouseTransparent(true);
            } 
        });
        sem2OpSubject4.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 2);
                sem2OpSubject4.setMouseTransparent(true);
            } 
        }); 
    }
}