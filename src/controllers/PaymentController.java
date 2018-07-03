package controllers;

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import models.Undergraduate_Semester;

public class PaymentController implements Initializable {
    
    Connection con;
    
    @FXML JFXTextField searchBox;
    
    // This method will return an ObservableList of payment details
    public ObservableList<Undergraduate_Semester> getPaymentDetails(){
        ObservableList<Undergraduate_Semester> paymentList = FXCollections.observableArrayList();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = con.prepareStatement("SELECT semester_id, student_id, amount FROM undergraduate_semester WHERE student_id = ?");
            ps.setString(1, searchBox.getText());
            rs = ps.executeQuery();
            Undergraduate_Semester payment;
            
            while(rs.next()){
                payment = new Undergraduate_Semester(rs.getString("semester_id"), rs.getString("student_id"), rs.getDouble("amount"));
                paymentList.add(payment);
            }
            
            System.out.println(paymentList.get(0).getAmount());
            
        } catch (SQLException ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paymentList;
    }
    
    public void searchButtonPressed(){
        getPaymentDetails();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }    
    
}
