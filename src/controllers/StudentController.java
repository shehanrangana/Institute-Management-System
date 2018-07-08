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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.AlResult;
import models.PostgraduateStudent;
import models.Qualifications;
import models.UndergraduateStudent;
import static nsbm.NSBM.changeTabColors;
import static nsbm.NSBM.alerts;

public class StudentController implements Initializable {
    
    // Initialize variable for connection
    Connection con;
    
    char student = 'u';
    
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
    
    // Subject select view componenets
    @FXML JFXTextField s01CompSubject1, s01CompSubject2, s01CompSubject3, s02CompSubject1, s02CompSubject2, s02CompSubject3;
    @FXML JFXComboBox s01OpSubject1, s01OpSubject2, s01OpSubject3, s01OpSubject4, semIdComboBox1;
    @FXML JFXComboBox s02OpSubject1, s02OpSubject2, s02OpSubject3, s02OpSubject4, semIdComboBox2;
    @FXML Text studentIdText, s01TotalCreditText, s02TotalCreditText, s01Amount, s02Amount;
    @FXML JFXButton s01ConfirmButton, s02ConfirmButton;
    int s01TotalCredit, s02TotalCredit = 0;
    double s01TotalFee, s02TotalFee = 0;
    
    // More details components
    @FXML Label studentIdLabel;
    @FXML JFXTextField nameTextField, addressLine1TextField, addressLine2TextField, addressLine3TextField, birthdayTextField, genderTextField,
                       emailTextField, nicTextField, mobileTextField, fixedTextField, facultyTextField, courseTextField, streamTextField, result1TextField,
                       result2TextField, result3TextField, rankTextField, zScoreTextField, qualificationTextField, instituteTextField, compYearTextField;
    @FXML Pane alResultPane, qualificationsPane;
    
    // This method will return an ObservableList of undergraduate students 
    public ObservableList<UndergraduateStudent> getUgStudents(){
        
        ObservableList<UndergraduateStudent> ugStudentList = FXCollections.observableArrayList();
        
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
    
    // Load postgraduates' details
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
    
    // Enable combo boxes for selections
    public void enableComboBoxes(boolean x){
        semIdComboBox1.setMouseTransparent(x);
        semIdComboBox2.setMouseTransparent(x);
        s01OpSubject1.setMouseTransparent(x);
        s01OpSubject2.setMouseTransparent(x);
        s01OpSubject3.setMouseTransparent(x);
        s01OpSubject4.setMouseTransparent(x);
        s02OpSubject1.setMouseTransparent(x);
        s02OpSubject2.setMouseTransparent(x);
        s02OpSubject3.setMouseTransparent(x);
        s02OpSubject4.setMouseTransparent(x);
    }

    // Switch to the choose/change subject pane
    public void chooseSubjects(){
        s01TotalCredit=0; 
        s02TotalCredit = 0;
        s01TotalFee = 0; 
        s02TotalFee = 0;
        s01TotalCreditText.setText("0"); 
        s02TotalCreditText.setText("0");
        s01Amount.setText("00.00"); 
        s02Amount.setText("00.00");
        
        try{
            if(student == 'u'){
                index = undergraduateTable.getSelectionModel().selectedIndexProperty().get();
                studentIdText.setText(getUgStudents().get(index).getStudentId());
            }else if(student == 'p'){
                index = postgraduateTable.getSelectionModel().selectedIndexProperty().get();
                studentIdText.setText(getPgStudents().get(index).getStudentId());
            }
            fillSemester();
            fillCompulsory();
            fillOptional();
            showMySubjects();
            chooseSubjectsAnchorPane.setVisible(true);
            studentHomeAnchorPane.setVisible(false);
        }catch(ArrayIndexOutOfBoundsException e){
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
            PreparedStatement ps = con.prepareStatement("SELECT semester_id FROM semester");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                semIdComboBox1.getItems().addAll(rs.getString("semester_id"));
                semIdComboBox2.getItems().addAll(rs.getString("semester_id"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    // Fill compulsory subjects with database values
    PreparedStatement psS01, psS02;
    ResultSet rsS01, rsS02;
    String subject;
    public void fillCompulsory(){
        s01CompSubject1.clear();
        s01CompSubject2.clear();
        s01CompSubject3.clear();
        s02CompSubject1.clear();
        s02CompSubject2.clear();
        s02CompSubject3.clear();
        
        ArrayList<String> s01CompSubList = new ArrayList<String>();
        ArrayList<String> s02CompSubList = new ArrayList<String>();
        try {
            if(student == 'u'){
                psS01 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN undergraduate ON subject.b_course_name = undergraduate.course_name WHERE subject.compulsory=1 AND semester_id LIKE '%S01' AND student_id=?");
                psS02 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN undergraduate ON subject.b_course_name = undergraduate.course_name WHERE subject.compulsory=1 AND semester_id LIKE '%S02' AND student_id=?");
                psS01.setString(1, getUgStudents().get(index).getStudentId());
                psS02.setString(1, getUgStudents().get(index).getStudentId());
                
                rsS01 = psS01.executeQuery();
                rsS02 = psS02.executeQuery();
                while (rsS01.next()) {
                    s01CompSubList.add(rsS01.getString("subject_name"));
                    creditListenerBody(rsS01.getString("subject_name"), 1);
                }
                while (rsS02.next()) {
                    s02CompSubList.add(rsS02.getString("subject_name"));
                    creditListenerBody(rsS02.getString("subject_name"), 2);
                }
                
                s01CompSubject1.setText(s01CompSubList.get(0));
                s01CompSubject2.setText(s01CompSubList.get(1));
                s01CompSubject3.setText(s01CompSubList.get(2));
                s02CompSubject1.setText(s02CompSubList.get(0));
                s02CompSubject2.setText(s02CompSubList.get(1));
                s02CompSubject3.setText(s02CompSubList.get(2));
                
            }else if(student == 'p'){
                psS01 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN postgraduate ON subject.m_course_name = postgraduate.course_name WHERE subject.compulsory=1 AND semester_id LIKE '%S01' AND student_id=?");
                psS02 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN postgraduate ON subject.m_course_name = postgraduate.course_name WHERE subject.compulsory=1 AND semester_id LIKE '%S02' AND student_id=?");
                psS01.setString(1, getPgStudents().get(index).getStudentId());
                psS02.setString(1, getPgStudents().get(index).getStudentId());

                rsS01 = psS01.executeQuery();
                rsS02 = psS02.executeQuery();
                while (rsS01.next()) {
                    s01CompSubList.add(rsS01.getString("subject_name"));
                    creditListenerBody(rsS01.getString("subject_name"), 1);
                }
                while (rsS02.next()) {
                    s02CompSubList.add(rsS02.getString("subject_name"));
                    creditListenerBody(rsS02.getString("subject_name"), 2);
                }
                
                s01CompSubject1.setText(s01CompSubList.get(0));
                s01CompSubject2.setText(s01CompSubList.get(1));
                s01CompSubject3.setText(s01CompSubList.get(2));
                s02CompSubject1.setText(s02CompSubList.get(0));
                s02CompSubject2.setText(s02CompSubList.get(1));
                s02CompSubject3.setText(s02CompSubList.get(2));
            } 
        }catch (Exception ex) {
            //ex.printStackTrace();
            alerts('E', "Message", null, "Compulsory subjects are not found");
        }
    }
    
    // Fill optional subjects with database values
    public void fillOptional(){
        s01OpSubject1.getItems().clear();
        s01OpSubject2.getItems().clear();
        s01OpSubject3.getItems().clear();
        s01OpSubject4.getItems().clear();
        s02OpSubject1.getItems().clear();
        s02OpSubject2.getItems().clear();
        s02OpSubject3.getItems().clear();
        s02OpSubject4.getItems().clear();
        
        try {
            if(student == 'u'){
                psS01 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN undergraduate ON subject.b_course_name = undergraduate.course_name WHERE subject.compulsory=0 AND semester_id LIKE '%S01' AND student_id=?");
                psS02 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN undergraduate ON subject.b_course_name = undergraduate.course_name WHERE subject.compulsory=0 AND semester_id LIKE '%S02' AND student_id=?");
                psS01.setString(1, getUgStudents().get(index).getStudentId());
                psS02.setString(1, getUgStudents().get(index).getStudentId());
                
                rsS01 = psS01.executeQuery();
                rsS02 = psS02.executeQuery();
                while (rsS01.next()) {
                    subject = rsS01.getString("subject_name");
                    s01OpSubject1.getItems().addAll(subject);
                    s01OpSubject2.getItems().addAll(subject);
                    s01OpSubject3.getItems().addAll(subject);
                    s01OpSubject4.getItems().addAll(subject);
                }
                while (rsS02.next()) {
                    subject = rsS02.getString("subject_name");
                    s02OpSubject1.getItems().addAll(subject);
                    s02OpSubject2.getItems().addAll(subject);
                    s02OpSubject3.getItems().addAll(subject);
                    s02OpSubject4.getItems().addAll(subject);
                }
            }else if(student == 'p'){
                psS01 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN postgraduate ON subject.m_course_name = postgraduate.course_name WHERE subject.compulsory=0 AND semester_id LIKE '%S01' AND student_id=?");
                psS02 = con.prepareStatement("SELECT DISTINCT subject_name FROM subject INNER JOIN postgraduate ON subject.m_course_name = postgraduate.course_name WHERE subject.compulsory=0 AND semester_id LIKE '%S02' AND student_id=?");
                psS01.setString(1, getPgStudents().get(index).getStudentId());
                psS02.setString(1, getPgStudents().get(index).getStudentId());
                
                rsS01 = psS01.executeQuery();
                rsS02 = psS02.executeQuery();
                while (rsS01.next()) {
                    subject = rsS01.getString("subject_name");
                    s01OpSubject1.getItems().addAll(subject);
                    s01OpSubject2.getItems().addAll(subject);
                    s01OpSubject3.getItems().addAll(subject);
                    s01OpSubject4.getItems().addAll(subject);
                }
                while (rsS02.next()) {
                    subject = rsS02.getString("subject_name");
                    s02OpSubject1.getItems().addAll(subject);
                    s02OpSubject2.getItems().addAll(subject);
                    s02OpSubject3.getItems().addAll(subject);
                    s02OpSubject4.getItems().addAll(subject);
                }
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("optional");
        }
    }
    
    // Listening for total credits and fees
    public void creditListenerBody(String newValue, int semester){ 
        
        PreparedStatement psSubCode = null;
        ResultSet rsSubCode = null;
        ResultSet rs = null;
            try { 
                psSubCode = con.prepareStatement("SELECT subject_code FROM subject WHERE subject_name=?");
                psSubCode.setString(1, newValue);
                rsSubCode = psSubCode.executeQuery();
                
                while(rsSubCode.next()){
                    //System.out.println(rsSubCode.getString("subject_code"));
                        
                    PreparedStatement psCredit = con.prepareStatement("SELECT credit, fee FROM subject WHERE subject_code=?");
                    psCredit.setString(1, rsSubCode.getString("subject_code"));
                    
                    rs = psCredit.executeQuery();
                    
                    while (rs.next()) {
                        int credit = rs.getInt("credit");
                        int fee = rs.getInt("fee");
                        if(semester == 1){
                            s01TotalCredit += credit;
                            s01TotalFee += fee;
                            s01TotalCreditText.setText(Integer.toString(s01TotalCredit));
                            s01Amount.setText(Double.toString(s01TotalFee));
                        }else if(semester == 2){
                            s02TotalCredit += credit;
                            s02TotalFee += fee;
                            s02TotalCreditText.setText(Integer.toString(s02TotalCredit));
                            s02Amount.setText(Double.toString(s02TotalFee));
                        }  
                    }       
                }   
                
            }catch (SQLException ex) {
                Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    // Reset subject selection form
    public void s01UpdateButtonPressed() throws SQLException{
        
//        chooseSubjects();   
//        enableComboBoxes();

//        List<Object> objects = new ArrayList<Object>();
//        objects.add(s01OpSubject1);
//        objects.add(s01OpSubject2);
//        for(Object ob : objects){
//            JFXComboBox comboBox = (JFXComboBox) ob;
//            comboBox.setMouseTransparent(false);
//        }
    }

    // Assign subjects
    public void confirmButtonPressed(ActionEvent event){
        String subjectCode = null;
        List subjects = new ArrayList();
        List semesters = new ArrayList();
        List fees = new ArrayList();
        
        PreparedStatement psSubCode = null;
        PreparedStatement psInsert1 = null;
        PreparedStatement psInsert2 = null;
        
        try{
            // Store selected subject names in a list
            if(event.getTarget() == s01ConfirmButton){
                if(semIdComboBox1.getValue() != null){
                    semesters.add(semIdComboBox1.getValue().toString());
                    fees.add(s01Amount.getText());
                    subjects.add(s01CompSubject1.getText());
                    subjects.add(s01CompSubject2.getText());
                    subjects.add(s01CompSubject3.getText());
                    subjects.add(s01OpSubject1.getValue().toString());
                    subjects.add(s01OpSubject2.getValue().toString());
                    subjects.add(s01OpSubject3.getValue().toString());
                    subjects.add(s01OpSubject4.getValue().toString());
                }else{
                    alerts('E', "Message", null, "Please select a semester");
                }
            }else if(event.getTarget() == s02ConfirmButton){  
                if(semIdComboBox2.getValue() != null){
                    semesters.add(semIdComboBox2.getValue().toString());
                    fees.add(s02Amount.getText());
                    subjects.add(s02CompSubject1.getText());
                    subjects.add(s02CompSubject2.getText());
                    subjects.add(s02CompSubject3.getText());
                    subjects.add(s02OpSubject1.getValue().toString());
                    subjects.add(s02OpSubject2.getValue().toString());
                    subjects.add(s02OpSubject3.getValue().toString());
                    subjects.add(s02OpSubject4.getValue().toString());
                }else{
                    alerts('E', "Message", null, "Please select a semester");
                }
            }

            psSubCode = con.prepareStatement("SELECT subject_code FROM subject WHERE subject_name=?");
            for(int i=0; i<subjects.size(); i++){
                psSubCode.setString(1, subjects.get(i).toString());
                ResultSet rs = psSubCode.executeQuery();

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
                
                alerts('I', "Messsage", null, "Successfully updated");
            }
        } catch (NullPointerException ex) {
             // Error message
            ex.printStackTrace();
            alerts('E', "Message", null, "Please fill all the fields correctly");
        } catch (SQLException ex) {
            //ex.printStackTrace();
            alerts('E', "Message", null, "Subjects are already selected for this student");
        }
    }
    
    // Show details about choosed subjects 
    public void showMySubjects() throws SQLException{
        ArrayList<String> subjectCodes = new ArrayList<String>();
        ArrayList<String> semesters = new ArrayList<String>();
        String studentId;
        studentId = studentIdText.getText();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        if(student == 'u'){
            ps1 = con.prepareStatement("SELECT subject_name FROM subject INNER JOIN undergraduate_subject ON subject.subject_code = undergraduate_subject.subject_code WHERE undergraduate_subject.student_id=?");
            ps2 = con.prepareStatement("SELECT semester_id FROM undergraduate_semester WHERE student_id=?");
            ps1.setString(1, studentId);
            ps2.setString(1, studentId);
        }else if(student == 'p'){
            ps1 = con.prepareStatement("SELECT subject_name FROM subject INNER JOIN postgraduate_subject ON subject.subject_code = postgraduate_subject.subject_code WHERE postgraduate_subject.student_id=?");
            ps2 = con.prepareStatement("SELECT semester_id FROM postgraduate_semester WHERE student_id=?");
            ps1.setString(1, studentId);
            ps2.setString(1, studentId);
        }

        ResultSet rs1 = ps1.executeQuery();
        ResultSet rs2 = ps2.executeQuery();
        
        while(rs1.next()){
            subjectCodes.add(rs1.getString("subject_name"));
        }
        while(rs2.next()){
            semesters.add(rs2.getString("semester_id"));
            System.out.println(rs2.getString("semester_id"));
        }
        try{
            semIdComboBox1.setValue(semesters.get(0));
            s01OpSubject1.setValue(subjectCodes.get(3));
            s01OpSubject2.setValue(subjectCodes.get(4));
            s01OpSubject3.setValue(subjectCodes.get(5));
            s01OpSubject4.setValue(subjectCodes.get(6));
        }catch(Exception ex){
            //ex.printStackTrace();
        }
        try{
            semIdComboBox2.setValue(semesters.get(1));
            s02OpSubject1.setValue(subjectCodes.get(10));
            s02OpSubject2.setValue(subjectCodes.get(11));
            s02OpSubject3.setValue(subjectCodes.get(12));
            s02OpSubject4.setValue(subjectCodes.get(13));
        }catch(Exception ex){
            //ex.printStackTrace();
        }
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
                alerts('E', "Message", null, "Please select a student");
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
                alerts('E', "Message", null, "Please select a student");
            }
        }  
    }
  
    // Fill text fields
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
    byte editbuttonClicked = 0;
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
            ps.setString(7, studentIdLabel.getText());
            ps.executeUpdate();

            editbuttonClicked = 0;
            alerts('I', "Message", null, "Details updated successfully");
        }
        studentHomeAnchorPane.setVisible(true);
        detailsAndUpdateAnchorPane.setVisible(false);
        enableOrDisableTextFields(false);  
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Default view is undergraduate
        selectTabU();

        s01OpSubject1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 1);
                s01OpSubject1.setMouseTransparent(true);
            } 
        });
        s01OpSubject2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 1);
                s01OpSubject2.setMouseTransparent(true);
            } 
        });
        s01OpSubject3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 1);
                s01OpSubject3.setMouseTransparent(true);
            } 
        });
        s01OpSubject4.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 1);
                s01OpSubject4.setMouseTransparent(true);
            } 
        });
        
        s02OpSubject1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 2);
                s02OpSubject1.setMouseTransparent(true);
            } 
        });
        s02OpSubject2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 2);
                s02OpSubject2.setMouseTransparent(true);
            } 
        });
        s02OpSubject3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 2);
                s02OpSubject3.setMouseTransparent(true);
            } 
        });
        s02OpSubject4.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditListenerBody(newValue, 2);
                s02OpSubject4.setMouseTransparent(true);
            } 
        });
        
    }
}
