package controllers;

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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.Instructor;
import models.Lecturer;
import static nsbm.NSBM.alerts;
import static nsbm.NSBM.changeTabColors;

public class StaffController implements Initializable {

    private Connection con;
    
    @FXML Pane lecturerPane, instructorPane;
    @FXML AnchorPane addNewStaffAnchorPane, staffAnchorPane, staffHomeAnchorPane, detailsAndUpdateAnchorPane, lecturerAnchorPane, instructorAnchorPane;
    @FXML ImageView backFromStaffReg, backFromDetails;
    @FXML Text lecturerText, instructorText;
    
    // Lecturer table components
    @FXML TableView<Lecturer> lecturerTable;
    @FXML TableColumn<Lecturer, String> lecturerIdColumn;
    @FXML TableColumn<Lecturer, String> lNameColumn;
    @FXML TableColumn<Lecturer, String> lMobileColumn;
    @FXML TableColumn<Lecturer, String> lEmailColumn;
    @FXML TableColumn<Lecturer, String> lRoomColumn;
    @FXML TableColumn<Lecturer, String> lAddressLine1Column;
    @FXML TableColumn<Lecturer, String> lAddressLine2Column;
    @FXML TableColumn<Lecturer, String> lAddressLine3Column;
    
    // Instructor table components
    @FXML TableView<Instructor> instructorTable;
    @FXML TableColumn<Instructor, String> instructorIdColumn;
    @FXML TableColumn<Instructor, String> iNameColumn;
    @FXML TableColumn<Instructor, String> iMobileColumn;
    @FXML TableColumn<Instructor, String> iEmailColumn;
    @FXML TableColumn<Instructor, String> iRoomColumn;
    @FXML TableColumn<Instructor, String> iAddressLine1Column;
    @FXML TableColumn<Instructor, String> iAddressLine2Column;
    @FXML TableColumn<Instructor, String> iAddressLine3Column;
    
    // More details view components
    @FXML AnchorPane assignAnchorPane;
    @FXML JFXComboBox courseComboBox, subjectComboBox;
    @FXML JFXTextField idTextField, nameTextField, mobileTextField, emailTextField, addressLine1TextField, addressLine2TextField, addressLine3TextField, roomTextField;
    @FXML ListView subjectListView;
    private String id, name, mobile, email, addressLine1, addressLine2, addressLine3, room;
    
    // This method will return an ObservableList of lecturers
    public ObservableList<Lecturer> getLecturerList(){
        ObservableList<Lecturer> lecturerList = FXCollections.observableArrayList();
        String query = "SELECT lecturer_id, name, mobile, email, room, address_line_1, address_line_2, address_line_3 FROM lecturer";
        
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Lecturer lecturer;
            
            while(rs.next()){
                lecturer = new Lecturer(rs.getString("lecturer_id"), rs.getString("name"), rs.getString("mobile"), rs.getString("email"),
                rs.getString("room"), rs.getString("address_line_1"), rs.getString("address_line_2"), rs.getString("address_line_3")); 
                lecturerList.add(lecturer);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lecturerList;
    }
    
    // This method will return an ObservableList of instrutors
    public ObservableList<Instructor> getInstructorList(){
        ObservableList<Instructor> instructorList = FXCollections.observableArrayList();
        String query = "SELECT instructor_id, name, mobile, email, room, address_line_1, address_line_2, address_line_3 FROM instructor";
        
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Instructor instructor;
            
            while(rs.next()){
                instructor = new Instructor(rs.getString("instructor_id"), rs.getString("name"), rs.getString("mobile"), rs.getString("email"),
                rs.getString("room"), rs.getString("address_line_1"), rs.getString("address_line_2"), rs.getString("address_line_3")); 
                instructorList.add(instructor);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return instructorList;
    }
    
    // View lecturer table
    public void selectLecturerTab(){  
        // setup columns in the lecturer table
        lecturerIdColumn.setCellValueFactory(new PropertyValueFactory<Lecturer, String> ("staffId"));
        lNameColumn.setCellValueFactory(new PropertyValueFactory<Lecturer, String> ("name"));
        lMobileColumn.setCellValueFactory(new PropertyValueFactory<Lecturer, String> ("mobile"));
        lEmailColumn.setCellValueFactory(new PropertyValueFactory<Lecturer, String> ("email"));
        lRoomColumn.setCellValueFactory(new PropertyValueFactory<Lecturer, String> ("room"));
        lAddressLine1Column.setCellValueFactory(new PropertyValueFactory<Lecturer, String> ("addressLine1"));
        lAddressLine2Column.setCellValueFactory(new PropertyValueFactory<Lecturer, String> ("addressLine2"));
        lAddressLine3Column.setCellValueFactory(new PropertyValueFactory<Lecturer, String> ("addressLine3"));
        
        // load the data into the lecturer table
        lecturerTable.setItems(getLecturerList());
        
        changeTabColors(lecturerPane, instructorPane, lecturerText, instructorText, lecturerAnchorPane, instructorAnchorPane);
        
    }
    
    // View instructor table
    public void selectInstructorTab(){
        // setup columns in the instructor table
        instructorIdColumn.setCellValueFactory(new PropertyValueFactory<Instructor, String> ("staffId"));
        iNameColumn.setCellValueFactory(new PropertyValueFactory<Instructor, String> ("name"));
        iMobileColumn.setCellValueFactory(new PropertyValueFactory<Instructor, String> ("mobile"));
        iEmailColumn.setCellValueFactory(new PropertyValueFactory<Instructor, String> ("email"));
        iRoomColumn.setCellValueFactory(new PropertyValueFactory<Instructor, String> ("room"));
        iAddressLine1Column.setCellValueFactory(new PropertyValueFactory<Instructor, String> ("addressLine1"));
        iAddressLine2Column.setCellValueFactory(new PropertyValueFactory<Instructor, String> ("addressLine2"));
        iAddressLine3Column.setCellValueFactory(new PropertyValueFactory<Instructor, String> ("addressLine3"));
        
        // load the data into the instructor table
        instructorTable.setItems(getInstructorList());
        
        changeTabColors(instructorPane, lecturerPane, instructorText, lecturerText, instructorAnchorPane, lecturerAnchorPane);
    }
    
    // Switch to add new staff member view
    public void addMemberButtonPressed() throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/StaffRegistration.fxml"));
        staffAnchorPane.getChildren().setAll(pane); 
    }
    
    // Update tables
    public void updateTables(){
        lecturerTable.setItems(null);
        lecturerTable.setItems(getLecturerList());
        
        instructorTable.setItems(null);
        instructorTable.setItems(getInstructorList());
    }
    
    // Remove staff member from system
    public void removeMemberButtonPressed(){
        PreparedStatement ps = null;
        String memberId = null;
        try{
            // Check whether course which is going to be deleted is a bachelor or master
            if(lecturerAnchorPane.isVisible()){
                ps = con.prepareStatement("DELETE FROM lecturer WHERE lecturer_id = ?");
                memberId = lecturerIdColumn.getCellData(lecturerTable.getSelectionModel().getSelectedItem());
            }else if(instructorAnchorPane.isVisible()){
                ps = con.prepareStatement("DELETE FROM instructor WHERE instructor_id = ?");
                memberId = instructorIdColumn.getCellData(instructorTable.getSelectionModel().getSelectedItem());
            }
            
            if(memberId == null){
                // Information message
                alerts('I', "Message", null, "Please select a member");
            }else{
                 // Confirmation message
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Are You Sure?");
                alert.showAndWait();
                
                if(alert.getResult().getText().equals("OK")){
                    ps.setString(1, memberId);
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
    public void moreDetailsButtonPressed(){
        subjectListView.getItems().clear();   
        PreparedStatement ps = null;
        ResultSet rs;
        
        try{
            if(lecturerAnchorPane.isVisible()){
                id = lecturerTable.getSelectionModel().getSelectedItem().getStaffId();
                name = lecturerTable.getSelectionModel().getSelectedItem().getName();
                mobile = lecturerTable.getSelectionModel().getSelectedItem().getMobile();
                email = lecturerTable.getSelectionModel().getSelectedItem().getEmail();
                addressLine1 = lecturerTable.getSelectionModel().getSelectedItem().getAddressLine1();
                addressLine2 = lecturerTable.getSelectionModel().getSelectedItem().getAddressLine2();
                addressLine3 = lecturerTable.getSelectionModel().getSelectedItem().getAddressLine3();
                room = lecturerTable.getSelectionModel().getSelectedItem().getRoom();

                ps = con.prepareStatement("SELECT subject_name FROM subject WHERE lecturer_id=?");
            }else if(instructorAnchorPane.isVisible()){
                id = instructorTable.getSelectionModel().getSelectedItem().getStaffId();
                name = instructorTable.getSelectionModel().getSelectedItem().getName();
                mobile = instructorTable.getSelectionModel().getSelectedItem().getMobile();
                email = instructorTable.getSelectionModel().getSelectedItem().getEmail();
                addressLine1 = instructorTable.getSelectionModel().getSelectedItem().getAddressLine1();
                addressLine2 = instructorTable.getSelectionModel().getSelectedItem().getAddressLine2();
                addressLine3 = instructorTable.getSelectionModel().getSelectedItem().getAddressLine3();
                room = instructorTable.getSelectionModel().getSelectedItem().getRoom();

                ps = con.prepareStatement("SELECT subject_name FROM subject INNER JOIN instructor_subject ON subject.subject_code=instructor_subject.subject_code WHERE instructor_id=?");
            }
            ps.setString(1, id);

            rs = ps.executeQuery();
            while(rs.next()){
                subjectListView.getItems().add(rs.getString("subject_name"));
            }
            // Fill text fields
            fillTextFields();

            detailsAndUpdateAnchorPane.setVisible(true);
            staffHomeAnchorPane.setVisible(false);
        }catch(SQLException ex){
            ex.printStackTrace();
            // Error message
            alerts('E', "Message", null, "Please select a member");
        }
    }

    // Fill text fields
    public void fillTextFields(){
        idTextField.setText(id);
        nameTextField.setText(name);
        mobileTextField.setText(mobile);
        emailTextField.setText(email);
        addressLine1TextField.setText(addressLine1);
        addressLine2TextField.setText(addressLine2);
        addressLine3TextField.setText(addressLine3);
        roomTextField.setText(room);
    }
    
    // Allow/Not Allow to edit text fields
    public void enableOrDisableTextFields(boolean value){
        mobileTextField.setEditable(value);
        emailTextField.setEditable(value);
        addressLine1TextField.setEditable(value);
        addressLine2TextField.setEditable(value);
        addressLine3TextField.setEditable(value);
        roomTextField.setEditable(value);
    }
    
    // Add courses names to combo box
    public void getSubjectList(){
        ArrayList<String> courseList = new ArrayList<String>();
        
        courseComboBox.getItems().clear();
        try {
            PreparedStatement ps1 = con.prepareStatement("SELECT course_name FROM bachelor");
            PreparedStatement ps2 = con.prepareStatement("SELECT course_name FROM master");
            
            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            
            while(rs1.next()){
                courseList.add(rs1.getString("course_name"));
            }
            while(rs2.next()){
                courseList.add(rs2.getString("course_name"));
            }
            courseComboBox.getItems().addAll(courseList);
            
            courseComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    PreparedStatement ps3 = null;
                    subjectComboBox.getItems().clear();
                    try {
                        if(lecturerAnchorPane.isVisible()){
                            ps3 = con.prepareStatement("SELECT subject_name FROM subject WHERE lecturer_id IS NULL AND b_course_name='"+ newValue +"' OR m_course_name='"+ newValue +"'");
                        }else if(instructorAnchorPane.isVisible()){
                            ps3 = con.prepareStatement("SELECT subject_name FROM subject WHERE b_course_name='"+ newValue +"' OR m_course_name='"+ newValue +"'");
                        }
                        
                        ResultSet rs3 = ps3.executeQuery();
                        while(rs3.next()){
                            subjectComboBox.getItems().add(rs3.getString("subject_name"));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }); 
            
        } catch (SQLException ex) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Visible subject assign pane
    public void assignSubjectButtonPressed(){
        assignAnchorPane.setVisible(true);
        getSubjectList();
    }
    
    // Confirm selected subject
    public void confirmButtonPressed(){
        String selectedSubject = subjectComboBox.getSelectionModel().getSelectedItem().toString();
        String subjectCode = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        try{
            ps1 = con.prepareStatement("SELECT subject_code FROM subject WHERE subject_name=?");
            ps1.setString(1, selectedSubject);
            ResultSet rs = ps1.executeQuery();
            while(rs.next()){
                subjectCode = rs.getString("subject_code");
            }
            
            if(lecturerAnchorPane.isVisible()){
                ps2 = con.prepareStatement("UPDATE subject SET lecturer_id = '"+ id +"' WHERE subject_code='"+ subjectCode +"'");
            }else if(instructorAnchorPane.isVisible()){
                ps2 = con.prepareStatement("INSERT INTO instructor_subject(instructor_id, subject_code) VALUES('"+ id +"', '"+ subjectCode +"')");
            }
            ps2.executeUpdate();
            
            moreDetailsButtonPressed();
            assignAnchorPane.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Cancel assigning process
    public void cancelButtonPressed(){
        assignAnchorPane.setVisible(false);
    }
    
    
    // Back to staff view
    public void backToStaffButtonPressed(){
        enableOrDisableTextFields(false);
        assignAnchorPane.setVisible(false);
        staffHomeAnchorPane.setVisible(true);
        detailsAndUpdateAnchorPane.setVisible(false);
    }
    
    // Update details of staff members
    byte editbuttonClicked = 0;
    public void editButtonPressed(){
        enableOrDisableTextFields(true);
        editbuttonClicked = 1;
    }
    
    // Update staff members' details
    public void updateButtonPressed() throws SQLException{
        PreparedStatement ps;
        String query = null;
        
        if(editbuttonClicked == 1){
            if(lecturerAnchorPane.isVisible()){
                query = "UPDATE lecturer SET mobile = ?, email = ?, address_line_1 = ?, address_line_2 = ?, address_line_3 = ?, room = ? WHERE lecturer_id = ?";
            }else if(instructorAnchorPane.isVisible()){
                query = "UPDATE instructor SET mobile = ?, email = ?, address_line_1 = ?, address_line_2 = ?, address_line_3 = ?, room = ? WHERE instructor_id = ?";   
            }
            ps = con.prepareStatement(query);

            ps.setString(1, mobileTextField.getText());
            ps.setString(2, emailTextField.getText());
            ps.setString(3, addressLine1TextField.getText());
            ps.setString(4, addressLine2TextField.getText());
            ps.setString(5, addressLine3TextField.getText());
            ps.setString(6, roomTextField.getText());
            ps.setString(7, idTextField.getText());
            ps.executeUpdate();
            
            editbuttonClicked = 0;
            // Information message
            alerts('I', "Message", null, "Details updated successfully");
        }
        enableOrDisableTextFields(false);
        staffHomeAnchorPane.setVisible(true);
        detailsAndUpdateAnchorPane.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Default view is undergraduate
        selectLecturerTab();
    }    
    
}
