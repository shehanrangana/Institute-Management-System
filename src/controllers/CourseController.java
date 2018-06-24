package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.BachelorCourses;
import models.MasterCourses;
import static nsbm.NSBM.alerts;
import static nsbm.NSBM.changeTabColors;

public class CourseController implements Initializable {
    
    // Initialize variable for connection
    Connection con;

    @FXML AnchorPane bachelorAnchorPane, masterAnchorPane, courseHomeAnchorPane, addNewCourseAnchorPane;
    @FXML Pane bachelorPane, masterPane;
    @FXML Text bachelorText, masterText;
    
    // Bachelor table components
    @FXML TableView<BachelorCourses> bachelorTable;
    @FXML TableColumn<BachelorCourses, String> bCourseNameColumn;
    @FXML TableColumn<BachelorCourses, Integer> bDurationColumn;
    @FXML TableColumn<BachelorCourses, Integer> bCreditLimitColumn;
    @FXML TableColumn<BachelorCourses, String> bFacultyColumn;
    
    // Master table components
    @FXML TableView<MasterCourses> masterTable;
    @FXML TableColumn<MasterCourses, String> mCourseNameColumn;
    @FXML TableColumn<MasterCourses, Integer> mDurationColumn;
    @FXML TableColumn<MasterCourses, Integer> mCreditLimitColumn;
    @FXML TableColumn<MasterCourses, String> mFacultyColumn;
    
    // Add new course components
    @FXML JFXTextField courseNameTextField, durationTextField, creditLimitTextField;
    @FXML JFXComboBox facultyComboBox;
    @FXML JFXButton saveCourseButton;
    @FXML ToggleGroup courseType;

    // This method will return an ObservableList of bachelor courses
    public ObservableList<BachelorCourses> getBachelorCourses(){
        ObservableList<BachelorCourses> coursesList = FXCollections.observableArrayList();
        String query = "SELECT course_name, duration, credit_limit, faculty FROM bachelor";
        
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            BachelorCourses course;
            
            while(rs.next()){
                course = new BachelorCourses(rs.getString("course_name"), rs.getInt("duration"), rs.getInt("credit_limit"), rs.getString("faculty")); 
                coursesList.add(course);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CourseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return coursesList;
    }
    
    // This method will return an ObservableList of master courses
    public ObservableList<MasterCourses> getMasterCourses(){
        ObservableList<MasterCourses> coursesList = FXCollections.observableArrayList();
        String query = "SELECT course_name, duration, credit_limit, faculty FROM master";
        
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            MasterCourses course;
            
            while(rs.next()){
                course = new MasterCourses(rs.getString("course_name"), rs.getInt("duration"), rs.getInt("credit_limit"), rs.getString("faculty")); 
                coursesList.add(course);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CourseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return coursesList;
    }

    // Handle bachelor tab
    public void selectBachelorTab(){
        // setup columns in the postgraduate table
        bCourseNameColumn.setCellValueFactory(new PropertyValueFactory<BachelorCourses, String> ("courseName"));
        bDurationColumn.setCellValueFactory(new PropertyValueFactory<BachelorCourses, Integer> ("duration"));
        bCreditLimitColumn.setCellValueFactory(new PropertyValueFactory<BachelorCourses, Integer> ("creditLimit"));
        bFacultyColumn.setCellValueFactory(new PropertyValueFactory<BachelorCourses, String> ("facultyName"));
        
        // load the data into the postgraduate table
        bachelorTable.setItems(getBachelorCourses());
        
        changeTabColors(bachelorPane, masterPane, bachelorText, masterText, bachelorAnchorPane, masterAnchorPane);
    }
    
    // Handle master tab
    public void selectMasterTab(){
        // setup columns in the postgraduate table
        mCourseNameColumn.setCellValueFactory(new PropertyValueFactory<MasterCourses, String> ("courseName"));
        mDurationColumn.setCellValueFactory(new PropertyValueFactory<MasterCourses, Integer> ("duration"));
        mCreditLimitColumn.setCellValueFactory(new PropertyValueFactory<MasterCourses, Integer> ("creditLimit"));
        mFacultyColumn.setCellValueFactory(new PropertyValueFactory<MasterCourses, String> ("facultyName"));
        
        // load the data into the postgraduate table
        masterTable.setItems(getMasterCourses());
        
        changeTabColors(masterPane, bachelorPane, masterText, bachelorText, masterAnchorPane, bachelorAnchorPane);
    }
    
    // Fill faculty ComboBox with database values
    public void fillComboBoxWithFacultyNames(){
        //facultyComboBox.getItems().clear();
        try {
            String query = "SELECT faculty_name FROM faculty";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String fName = rs.getString("faculty_name");
                facultyComboBox.getItems().addAll(fName);
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // Switch to the add new course pane
    public void addCourseButtonPressed(){
        courseNameTextField.clear();
        durationTextField.clear();
        creditLimitTextField.clear();
        facultyComboBox.getItems().clear();
        
        addNewCourseAnchorPane.setVisible(true);
        courseHomeAnchorPane.setVisible(false);
        fillComboBoxWithFacultyNames();
        
        // Fill faculty combo box
//        StudentRegistrationController src = new StudentRegistrationController();
//        src.fillComboBoxWithFacultyNames(facultyComboBox);
    }
    
    // Insert new course to the database
    public void saveCourseButtonPressed(ActionEvent event){
        
        PreparedStatement ps = null;
        
        JFXRadioButton selectedRadioButton = (JFXRadioButton) courseType.getSelectedToggle();
        String toogleValue = selectedRadioButton.getText();
        
        try{
            if(toogleValue.equals("Bachelor")){
                ps = con.prepareStatement("INSERT INTO bachelor(course_name, duration, credit_limit, faculty)" + "VALUES(?,?,?,?)");
            }else if(toogleValue.equals("Master")){
                ps = con.prepareStatement("INSERT INTO master(course_name, duration, credit_limit, faculty)" + "VALUES(?,?,?,?)");
            }
            // Get values for graduate table
            ps.setString(1, courseNameTextField.getText());
            ps.setInt(2, Integer.parseInt(durationTextField.getText()));
            ps.setInt(3, Integer.parseInt(creditLimitTextField.getText()));
            ps.setString(4, facultyComboBox.getValue().toString());
            
            ps.executeUpdate();
            
            // Back to courses view
            backToCourseButtonPressed();
        }catch(Exception e){
             // Error message
            alerts('E', "Message", null, "Please fill all the fields");
        } 
    }
    
    // Delete a record
    public void removeCourseButtonPressed() throws SQLException{
        PreparedStatement ps = null;
        String course_name = null;
        try{
            // Check whether course which is going to be deleted is a bachelor or master
            if(bachelorAnchorPane.isVisible()){
                ps = con.prepareStatement("DELETE FROM bachelor WHERE course_name = ?");
                course_name = bCourseNameColumn.getCellData(bachelorTable.getSelectionModel().getSelectedItem());
            }else if(masterAnchorPane.isVisible()){
                ps = con.prepareStatement("DELETE FROM master WHERE course_name = ?");
                course_name = mCourseNameColumn.getCellData(masterTable.getSelectionModel().getSelectedItem());
            }
            
            if(course_name == null){
                // Inform message
                alerts('I', "Message", null, "Please select a record");
            }else{
                 // Confirmation message
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("If there are some subjects related to this course will be deleted.\nDo you proceed ?");
                alert.showAndWait();
                
                if(alert.getResult().getText().equals("OK")){
                    ps.setString(1, course_name);
                    ps.executeUpdate();
                }else{
                    ps.setString(1, null);
                    ps.executeUpdate();
                }
            }

        }catch(SQLException e){
            alerts('E', "Message", null, "Cannot remove selected course.\nSome students are currently following this course.");
        }
    }
    
    // Back to courses pane
    public void backToCourseButtonPressed(){
        courseHomeAnchorPane.setVisible(true);
        addNewCourseAnchorPane.setVisible(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Default view is undergraduate
        selectBachelorTab();
    }    
}