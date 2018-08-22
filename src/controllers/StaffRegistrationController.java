package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import database.dbConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import static nsbm.NSBM.alerts;

public class StaffRegistrationController implements Initializable {
    
    private Connection con;
    
    @FXML AnchorPane addMemberAnchorPane;    
    @FXML JFXComboBox memberTypeComboBox;
    @FXML JFXTextField staffIdTextField, nameTextField, mobileTextField, emailTextField, addressLine1TextField, addressLine2TextField, addressLine3TextField, roomTextField;
            
    // Check inputs
    public boolean checkInputs(){
        if(memberTypeComboBox.getValue() != null){
            if(staffIdTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || mobileTextField.getText().isEmpty() || emailTextField.getText().isEmpty()
                || addressLine1TextField.getText().isEmpty() || addressLine2TextField.getText().isEmpty() || roomTextField.getText().isEmpty()){
                return false; 
            }else{
                return true;
            }
        }else{
            return false;
        }  
    }
    
    // Register a staff member
    public void registerButtonPressed() throws IOException{
        if(checkInputs()){
            try {
                PreparedStatement ps = null;
                
                // Check that registering member is whether a lecturer or an instructor
                if(memberTypeComboBox.getValue().toString() == "Lecturer"){
                    ps = con.prepareStatement("INSERT INTO lecturer(lecturer_id, name, mobile, email, address_line_1, address_line_2, address_line_3, room)" + "VALUES(?,?,?,?,?,?,?,?)");   
                }else if(memberTypeComboBox.getValue().toString() == "Instructor"){
                    ps = con.prepareStatement("INSERT INTO instructor(instructor_id, name, mobile, email, address_line_1, address_line_2, address_line_3, room)" + "VALUES(?,?,?,?,?,?,?,?)");  
                }
                
                ps.setString(1, staffIdTextField.getText());
                ps.setString(2, nameTextField.getText());
                ps.setString(3, mobileTextField.getText());
                ps.setString(4, emailTextField.getText());
                ps.setString(5, addressLine1TextField.getText());
                ps.setString(6, addressLine2TextField.getText());
                ps.setString(7, addressLine3TextField.getText());
                ps.setString(8, roomTextField.getText());

                ps.executeUpdate();
                
                // Information message
                alerts('I', "Message", null, "Data inserted");   
                backToStaffButtonPressed();
                
            } catch (SQLException ex) {
                // Information message
                alerts('I', "Message", null, "Entered ID already in database");   
            }      
        }else{
            // Information message
            alerts('I', "Message", null, "One or more fields are empty");
        }
    }
    
    // Back to student view
    public void backToStaffButtonPressed() throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/Staff.fxml"));
        addMemberAnchorPane.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Initialize items for member type combo box
        memberTypeComboBox.getItems().addAll("Lecturer", "Instructor"); 
        
        // Listening to member type to determine ID's first letter 
        memberTypeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals("Lecturer")){
                    staffIdTextField.setText("L");
                }else if(newValue.equals("Instructor")){
                    staffIdTextField.setText("I");
                }
            } 
        });
    }    
    
}
