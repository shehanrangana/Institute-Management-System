package controllers;

import database.dbConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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
import models.BachelorCourses;
import models.Instructor;
import models.Lecturer;
import models.MasterCourses;
import static nsbm.NSBM.alerts;
import static nsbm.NSBM.changeTabColors;

public class StaffController implements Initializable {
    
    // Initialize variable for connection
    Connection con;
    
    @FXML AnchorPane staffHomeAnchorPane, addNewStaffAnchorPane, detailsAndUpdateAnchorPane, lecturerAnchorPane, instructorAnchorPane;
    @FXML Pane lecturerPane, instructorPane;
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
    
    // This method will return an ObservableList lecturers
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
    
    // Handle lecturer tab
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
    
    // Handle instructor tab
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
                    ps.setString(1, memberId);
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
    
    // Switch to add new staff member pane
    public void newStaffMemberButtonPressed(){
        addNewStaffAnchorPane.setVisible(true);
        staffHomeAnchorPane.setVisible(false);
    }
    
    // Switch to the more details/update pane
    public void moreDetailsButtonPressed(){
        detailsAndUpdateAnchorPane.setVisible(true);
        staffHomeAnchorPane.setVisible(false);
    }
    
    // Back to staff pane
    public void backToStaffButtonPressed(MouseEvent event){
        staffHomeAnchorPane.setVisible(true);
        
        // Identify which pane going to be invisible
        if(event.getTarget() == backFromStaffReg){
            addNewStaffAnchorPane.setVisible(false);
        }else if(event.getTarget() == backFromDetails){
            detailsAndUpdateAnchorPane.setVisible(false);
        } 
    }
    
    // Update staff members' details
    public void updateDetailsButtonPressed(){
        // data update process code here
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
