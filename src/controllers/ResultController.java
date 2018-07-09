package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import database.dbConnection;
import java.io.File;
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
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import models.Postgraduate_Assesment;
import models.Undergraduate_Assesment;
import static nsbm.NSBM.alerts;
import static nsbm.NSBM.changeTabColors;

public class ResultController implements Initializable {
    
    Connection con;
    char faculty = 'b';

    @FXML private Pane businessResultPane, computingResultPane, engineeringResultPane;
    @FXML private Text businessResultText, computingResultText, engineeringResultText;
    @FXML private ListView assignmentListView;
    @FXML private JFXButton assignmentsButton;
    @FXML private JFXComboBox goToComboBox, courseComboBox, subjectComboBox;
    
    // Undergraduate result table components
    @FXML TableView<Undergraduate_Assesment> ugResultTable;
    @FXML TableColumn<Undergraduate_Assesment, String> ugStudentIdColumn;
    @FXML TableColumn<Undergraduate_Assesment, Integer> ugMarkColumn;
    
    // Postgraduate result table components
    @FXML TableView<Postgraduate_Assesment> pgResultTable;
    @FXML TableColumn<Postgraduate_Assesment, String> pgStudentIdColumn;
    @FXML TableColumn<Postgraduate_Assesment, Integer> pgMarkColumn;
    
    // This method will return an ObservableList of results(undergraduate)
    public ObservableList<Undergraduate_Assesment> getUgResults(String selectedItem){
        ObservableList<Undergraduate_Assesment> results = FXCollections.observableArrayList();
        String query;
        try{
            if(selectedItem.equals("All")){
                query = "SELECT * FROM undergraduate_assesment";
            }else{
                query = "SELECT * FROM undergraduate_assesment WHERE assesment_id = '"+ selectedItem +"'";
            }
        
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            Undergraduate_Assesment lecturer;
            
            while(rs.next()){
                lecturer = new Undergraduate_Assesment(rs.getString("assesment_id"), rs.getString("student_id"), rs.getString("subject_code"), rs.getInt("mark"),rs.getString("type")); 
                results.add(lecturer);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ResultController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }
    
    // This method will return an ObservableList of results(postgraduate)
    public ObservableList<Postgraduate_Assesment> getPgResults(String selectedItem){
        ObservableList<Postgraduate_Assesment> results = FXCollections.observableArrayList();
        String query;
        try{
            if(selectedItem.equals("All")){
                query = "SELECT * FROM postgraduate_assesment";
            }else{
                query = "SELECT * FROM postgraduate_assesment WHERE assesment_id = '"+ selectedItem +"'";
            }
        
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            Postgraduate_Assesment lecturer;
            
            while(rs.next()){
                lecturer = new Postgraduate_Assesment(rs.getString("assesment_id"), rs.getString("student_id"), rs.getString("subject_code"), rs.getInt("mark"),rs.getString("type")); 
                results.add(lecturer);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ResultController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }
    
    // Handle results tab of school of business
    public void selectBusinessResultTab(){
        faculty = 'b';
        goToComboBox.getSelectionModel().clearSelection();
        changeTabColors(businessResultPane, computingResultPane, engineeringResultPane, businessResultText, computingResultText, engineeringResultText);
    }
    
    // Handle results tab of school of computing
    public void selectComputingResultTab(){
        faculty = 'c';
        goToComboBox.getSelectionModel().clearSelection();
        changeTabColors(computingResultPane, engineeringResultPane, businessResultPane, computingResultText, engineeringResultText, businessResultText);
    }
    
    // Handle results tab of school of engineering
    public void selectEngineeringResultTab(){
        faculty = 'e';
        goToComboBox.getSelectionModel().clearSelection();
        changeTabColors(engineeringResultPane, businessResultPane, computingResultPane, engineeringResultText, businessResultText, computingResultText);
    }
    
    // Assignment button
    public void assignmentsButtonPressed(){
        assignmentListView.getItems().clear();
        for(int i=0; i<getUgResults("All").size(); i++){
            assignmentListView.getItems().add(getUgResults("All").get(i).getAssesmentId());
        }
    }
    
    // Item selected
    public void updateTable(String newValue){
        // setup columns in the lecturer table
        ugStudentIdColumn.setCellValueFactory(new PropertyValueFactory<Undergraduate_Assesment, String> ("studentId"));
        ugMarkColumn.setCellValueFactory(new PropertyValueFactory<Undergraduate_Assesment, Integer> ("mark"));
        
        // load the data into the lecturer table
        ugResultTable.setItems(getUgResults(newValue));
    }
    
    // Upload new result sheet into the database
    public void uploadButtonPressed() {
        String filePath = null;
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Result File...");

            // set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("result set (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().addAll(extFilter);

            // show open file dialog
            File file = fileChooser.showOpenDialog(null);
            if(file != null){
                filePath = file.getAbsolutePath().replace("\\", "/");
                //System.out.println(file.getAbsolutePath().replace("\\", "/"));
            }
            
            PreparedStatement ps = con.prepareStatement("LOAD DATA INFILE ? INTO TABLE undergraduate_assesment FIELDS TERMINATED BY ',' LINES TERMINATED BY '\\n'");
            ps.setString(1, filePath);
            ps.executeQuery();

            alerts('I', "Message", null, "New Result Sheet Uploaded");
            updateTable("All");
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ResultController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Listening to assignment list view
        assignmentListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null){
                    updateTable(newValue);
                } 
            }
        });
        
        // Initialize items for Go to Combo Box
        goToComboBox.getItems().addAll("UNDERGRADUATE RESULT CENTER", "POSTGRADUATE RESULT CENTER");
        
        // Listening to Go to Combo Box
        goToComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                PreparedStatement ps = null;
                ResultSet rs = null;
                courseComboBox.getItems().clear();
                
                try{
                    if(newValue == "UNDERGRADUATE RESULT CENTER"){
                        switch (faculty) {
                            case 'b':
                                ps = con.prepareStatement("SELECT course_name FROM bachelor WHERE faculty='School of Business'");
                                break;
                            case 'c':
                                ps = con.prepareStatement("SELECT course_name FROM bachelor WHERE faculty='School of Computing'");
                                break;
                            case 'e':
                                ps = con.prepareStatement("SELECT course_name FROM bachelor WHERE faculty='School of Engineering'");
                                break;
                            default:
                                break;
                        }
                        rs = ps.executeQuery();
                        
                        while (rs.next()) {
                            String cName = rs.getString("course_name");
                            courseComboBox.getItems().addAll(cName);
                        } 
                        
                        ugResultTable.setVisible(true);
                        pgResultTable.setVisible(false);
                        
                    }else if(newValue == "POSTGRADUATE RESULT CENTER"){
                        switch (faculty) {
                            case 'b':
                                ps = con.prepareStatement("SELECT course_name FROM master WHERE faculty='School of Business'");
                                break;
                            case 'c':
                                ps = con.prepareStatement("SELECT course_name FROM master WHERE faculty='School of Computing'");
                                break;
                            case 'e':
                                ps = con.prepareStatement("SELECT course_name FROM master WHERE faculty='School of Engineering'");
                                break;
                            default:
                                break;
                        }
                        rs = ps.executeQuery();
                        
                        while (rs.next()) {
                            String cName = rs.getString("course_name");
                            courseComboBox.getItems().addAll(cName);
                        } 
                        
                        pgResultTable.setVisible(true);
                        ugResultTable.setVisible(false);
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        
        
    }    
    
}
