package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
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
import javafx.event.ActionEvent;
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
import models.Postgraduate_Subjects;
import models.Undergraduate_Assesment;
import models.Undergraduate_Subjects;
import static nsbm.NSBM.alerts;
import static nsbm.NSBM.changeTabColors;

public class ResultController implements Initializable {
    
    Connection con;
    char faculty = 'b';
    String subjectCode;

    @FXML private Pane businessResultPane, computingResultPane, engineeringResultPane;
    @FXML private Text businessResultText, computingResultText, engineeringResultText;
    @FXML private ListView assignmentListView;
    @FXML private JFXButton gradeButton;
    @FXML private JFXComboBox goToComboBox, courseComboBox, subjectComboBox;
    
    // Undergraduate assesments result table components
    @FXML TableView<Undergraduate_Assesment> ugResultTable;
    @FXML TableColumn<Undergraduate_Assesment, String> ugStudentIdColumn;
    @FXML TableColumn<Undergraduate_Assesment, Integer> ugMarkColumn;
    @FXML TableColumn<Undergraduate_Assesment, String> ugATypeColumn;
    
    // Postgraduate assesments result table components
    @FXML TableView<Postgraduate_Assesment> pgResultTable;
    @FXML TableColumn<Postgraduate_Assesment, String> pgStudentIdColumn;
    @FXML TableColumn<Postgraduate_Assesment, Integer> pgMarkColumn;
    @FXML TableColumn<Postgraduate_Assesment, String> pgATypeColumn;
    
    // Undergraduate final result table components
    @FXML TableView<Undergraduate_Subjects> ugGradeTable;
    @FXML TableColumn<Undergraduate_Subjects, String> ugStudentIdColumn2;
    @FXML TableColumn<Undergraduate_Subjects, String> ugGradeColumn;
    
    // Postgraduate final table components
    @FXML TableView<Postgraduate_Subjects> pgGradeTable;
    @FXML TableColumn<Postgraduate_Subjects, String> pgStudentIdColumn2;
    @FXML TableColumn<Postgraduate_Subjects, String> pgGradeColumn;
    
    // This method will return an ObservableList of results(undergraduate)
    public ObservableList<Undergraduate_Assesment> getUgResults(String assignmentId){
        ObservableList<Undergraduate_Assesment> results = FXCollections.observableArrayList();
        String query;
        try{
            if(assignmentId.equals("All")){
                query = "SELECT * FROM undergraduate_assesment";
            }else{
                query = "SELECT * FROM undergraduate_assesment WHERE assesment_id = '"+ assignmentId +"' AND subject_code = '"+ subjectCode+"'";
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
    public ObservableList<Postgraduate_Assesment> getPgResults(String assignmentId){
        ObservableList<Postgraduate_Assesment> results = FXCollections.observableArrayList();
        String query;
        try{
            if(assignmentId.equals("All")){
                query = "SELECT * FROM postgraduate_assesment";
            }else{
                query = "SELECT * FROM postgraduate_assesment WHERE assesment_id = '"+ assignmentId +"' AND subject_code = '"+ subjectCode+"'";
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
    
    // This method will return an ObservableList of grades(undergraduate)
    public ObservableList<Undergraduate_Subjects> getUgGrades(String subjectCode){
        ObservableList<Undergraduate_Subjects> gradeList = FXCollections.observableArrayList();
        try{
            String query = "SELECT student_id, grade FROM undergraduate_subject WHERE subject_code= '"+ subjectCode +"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            Undergraduate_Subjects grade;
            
            while(rs.next()){
                grade = new Undergraduate_Subjects(rs.getString("student_id"), rs.getString("grade")); 
                gradeList.add(grade);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ResultController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gradeList;
    }
    
    // This method will return an ObservableList of grades(undergraduate)
    public ObservableList<Postgraduate_Subjects> getPgGrades(String subjectCode){
        ObservableList<Postgraduate_Subjects> gradeList = FXCollections.observableArrayList();
        try{
            String query = "SELECT student_id, grade FROM postgraduate_subject WHERE subject_code= '"+ subjectCode +"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            Postgraduate_Subjects grade;
            
            while(rs.next()){
                grade = new Postgraduate_Subjects(rs.getString("student_id"), rs.getString("grade")); 
                gradeList.add(grade);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ResultController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gradeList;
    }
    
    // Handle results tab of school of business
    public void selectBusinessResultTab(){
        faculty = 'b';
        goToComboBox.getSelectionModel().clearSelection();
        ugGradeTable.getItems().clear();
        pgGradeTable.getItems().clear();
        changeTabColors(businessResultPane, computingResultPane, engineeringResultPane, businessResultText, computingResultText, engineeringResultText);
    }
    
    // Handle results tab of school of computing
    public void selectComputingResultTab(){
        faculty = 'c';
        goToComboBox.getSelectionModel().clearSelection();
        ugGradeTable.getItems().clear();
        pgGradeTable.getItems().clear();
        changeTabColors(computingResultPane, engineeringResultPane, businessResultPane, computingResultText, engineeringResultText, businessResultText);
    }
    
    // Handle results tab of school of engineering
    public void selectEngineeringResultTab(){
        faculty = 'e';
        goToComboBox.getSelectionModel().clearSelection();
        ugGradeTable.getItems().clear();
        pgGradeTable.getItems().clear();
        changeTabColors(engineeringResultPane, businessResultPane, computingResultPane, engineeringResultText, businessResultText, computingResultText);
    }
    
    // This method gets subject codes related to subject names
    public void getSubjectCode() throws SQLException{
        PreparedStatement getSubCode = con.prepareStatement("SELECT subject_code FROM subject WHERE subject_name=?");
        getSubCode.setString(1, subjectComboBox.getSelectionModel().getSelectedItem().toString());
        ResultSet subjectCodes = getSubCode.executeQuery();
            
        while(subjectCodes.next()){
            subjectCode = subjectCodes.getString("subject_code");
        }
    }
    
    // Assignment button
    public void loadAssesmentList() throws SQLException{
        assignmentListView.getItems().clear();
        try{
            getSubjectCode();
            PreparedStatement ps = null;
            
            if(goToComboBox.getValue() == "UNDERGRADUATE RESULT CENTER"){
                ps = con.prepareStatement("SELECT DISTINCT assesment_id FROM undergraduate_assesment WHERE subject_code=?");
            }else if(goToComboBox.getValue() == "POSTGRADUATE RESULT CENTER"){
                ps = con.prepareStatement("SELECT DISTINCT assesment_id FROM postgraduate_assesment WHERE subject_code=?");
            }
            
            ps.setString(1, subjectCode);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String assignmentId = rs.getString("assesment_id");
                assignmentListView.getItems().add(assignmentId);
            }
        }catch(Exception ex){
            System.out.println("Assesment list empty");         
        }
    }
    
    // This method updates assesment tables
    public void updateAssesmentTables(String newValue, char table){
        if(table == 'u'){
            // setup columns in the undergraduate result table
            ugStudentIdColumn.setCellValueFactory(new PropertyValueFactory<Undergraduate_Assesment, String> ("studentId"));
            ugMarkColumn.setCellValueFactory(new PropertyValueFactory<Undergraduate_Assesment, Integer> ("mark"));
            ugATypeColumn.setCellValueFactory(new PropertyValueFactory<Undergraduate_Assesment, String> ("type"));
        
            // load the data into the undergraduate result table
            ugResultTable.setItems(getUgResults(newValue));
        }else if(table == 'p'){
            // setup columns in the postgraduate result table
            pgStudentIdColumn.setCellValueFactory(new PropertyValueFactory<Postgraduate_Assesment, String> ("studentId"));
            pgMarkColumn.setCellValueFactory(new PropertyValueFactory<Postgraduate_Assesment, Integer> ("mark"));
            pgATypeColumn.setCellValueFactory(new PropertyValueFactory<Postgraduate_Assesment, String> ("type"));
        
            // load the data into the postgraduate result table
            pgResultTable.setItems(getPgResults(newValue));
        } 
    }
    
    public void updateGradeTables(char table){
        if(table == 'u'){
        // Setup columns in the undergraduate grades table
            ugStudentIdColumn2.setCellValueFactory(new PropertyValueFactory<Undergraduate_Subjects, String> ("subjectCode"));
            ugGradeColumn.setCellValueFactory(new PropertyValueFactory<Undergraduate_Subjects, String> ("grade"));
        
            // load the data into the undergraduate result table
            ugGradeTable.setItems(getUgGrades(subjectCode));
        }else if(table == 'p'){
            // Setup columns in the postgraduate grades table
            pgStudentIdColumn2.setCellValueFactory(new PropertyValueFactory<Postgraduate_Subjects, String> ("subjectCode"));
            pgGradeColumn.setCellValueFactory(new PropertyValueFactory<Postgraduate_Subjects, String> ("grade"));
        
            // load the data into the postgraduate result table
            pgGradeTable.setItems(getPgGrades(subjectCode));
        }     
    }
    
    // Update students' grades using temporary table
    public int updateGrades(String thisTable, String filePath) throws SQLException{
        PreparedStatement ps1 = con.prepareStatement("CREATE TABLE temp_table LIKE "+ thisTable +" ");
        PreparedStatement ps2 = con.prepareStatement("LOAD DATA INFILE '"+ filePath +"' INTO TABLE temp_table FIELDS TERMINATED BY ',' LINES TERMINATED BY '\\n'");
        PreparedStatement ps3 = con.prepareStatement("UPDATE "+ thisTable +" INNER JOIN temp_table ON temp_table.student_id = "+ thisTable +".student_id AND temp_table.subject_code="+ thisTable +".subject_code SET "+ thisTable +".grade=temp_table.grade");
        PreparedStatement ps4 = con.prepareStatement("DROP TABLE temp_table");
        
        ps1.executeUpdate();
        ps2.executeQuery();
        int returnCode = ps3.executeUpdate();
        ps4.executeUpdate();

        return returnCode;
    }
    
    // Upload new result sheet into the database
    public void uploadButtonPressed(ActionEvent event) {
        PreparedStatement ps = null;
        String filePath = null;
        
        if(goToComboBox.getValue() == null){
            alerts('I', "Message", null, "Before upload a new result sheet, you must select the related Result Center");
        }else{
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
                }else{
                    System.out.println("No file selected");
                    return;
                }

                if(goToComboBox.getValue() == "UNDERGRADUATE RESULT CENTER"){
                    if(event.getTarget() == gradeButton){
                        if(updateGrades("undergraduate_subject", filePath) == 0){
                            alerts('E', "Message", null, "Wrong file");
                            return;
                        }
                    }else{
                        ps = con.prepareStatement("LOAD DATA INFILE ? INTO TABLE undergraduate_assesment FIELDS TERMINATED BY ',' LINES TERMINATED BY '\\n'");
                        ps.setString(1, filePath);
                        ps.executeQuery();
                    }
                }else if(goToComboBox.getValue() == "POSTGRADUATE RESULT CENTER"){
                    if(event.getTarget() == gradeButton){
                        if(updateGrades("postgraduate_subject", filePath) == 0){
                            alerts('E', "Message", null, "Wrong file");
                            return;
                        }
                    }else{
                        ps = con.prepareStatement("LOAD DATA INFILE ? INTO TABLE postgraduate_assesment FIELDS TERMINATED BY ',' LINES TERMINATED BY '\\n'");
                        ps.setString(1, filePath);
                        ps.executeQuery();
                    } 
                }
                
                alerts('I', "Message", null, "New Result Sheet Uploaded");
//                updateTable("All", 'u');
//                updateTable("All", 'p');
  
            }catch(SQLException ex){
                ex.printStackTrace();
                System.out.println(ex.getErrorCode());
                if(ex.getErrorCode() == 1062){
                    alerts('E', "Message", null, "This result sheet consist with already uploaded marks");
                }else if(ex.getErrorCode() == 1452){
                    alerts('E', "Message", null, "Selected result sheet not compatible with selected result center");
                } 
            }
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
                    //System.out.println(newValue);
                    if(goToComboBox.getSelectionModel().getSelectedItem() == "UNDERGRADUATE RESULT CENTER"){
                        updateAssesmentTables(newValue, 'u');
                    }else if(goToComboBox.getSelectionModel().getSelectedItem() == "POSTGRADUATE RESULT CENTER"){
                        updateAssesmentTables(newValue, 'p');
                    }           
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
                        
                        ugResultTable.getItems().clear();
                        ugResultTable.setVisible(true);
                        pgResultTable.setVisible(false);
                        
                        ugGradeTable.getItems().clear();
                        ugGradeTable.setVisible(true);
                        pgGradeTable.setVisible(false);
                        
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
                        
                        pgResultTable.getItems().clear();
                        pgResultTable.setVisible(true);
                        ugResultTable.setVisible(false);
                        
                        pgGradeTable.getItems().clear();
                        pgGradeTable.setVisible(true);
                        ugGradeTable.setVisible(false);
                    }
                }catch(SQLException ex){
                    //ex.printStackTrace();
                }
            }
        });
        
        // Listening to Course Combo Box
        courseComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                subjectComboBox.getItems().clear();
                try {
                    PreparedStatement ps = con.prepareStatement("SELECT subject_name FROM subject WHERE b_course_name = ? OR m_course_name = ?");
                    ps.setString(1, newValue.toString());
                    ps.setString(2, newValue.toString());
                    
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String subjectName = rs.getString("subject_name");
                        subjectComboBox.getItems().addAll(subjectName);
                    } 
                } catch (Exception ex) {
                    System.out.println("Error: Caused by null value");
                    //Logger.getLogger(ResultController.class.getName()).log(Level.SEVERE, null, ex);
                } 
            } 
        });
        
        // Listening to Subejct Combo Box
        subjectComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    assignmentListView.getItems().clear();
                    loadAssesmentList();

                    if(goToComboBox.getSelectionModel().getSelectedItem() == "UNDERGRADUATE RESULT CENTER"){
                        updateGradeTables('u');
                    }else if(goToComboBox.getSelectionModel().getSelectedItem() == "POSTGRADUATE RESULT CENTER"){
                        updateGradeTables('p');
                    }           
                    
                    ugResultTable.getItems().clear();
                    pgResultTable.getItems().clear();
                } catch (SQLException ex) {
                    Logger.getLogger(ResultController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             
        });

    } 
}
