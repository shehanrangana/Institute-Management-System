package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.dbConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.Postgraduate_Semester;
import models.Undergraduate_Semester;
import static nsbm.NSBM.alerts;

public class PaymentController implements Initializable {
    
    private Connection con;
    private char table = 'u';
    
    @FXML AnchorPane paymentAnchorPane;
    
    // Undergraduate_Semester table components
    @FXML TableView<Undergraduate_Semester> uTable;
    @FXML TableColumn<Undergraduate_Semester, String> uSemIdColumn;
    @FXML TableColumn<Undergraduate_Semester, String> uStudentIdColumn;
    @FXML TableColumn<Undergraduate_Semester, Double> uAmountColumn;
    @FXML TableColumn<Undergraduate_Semester, String> uStatusColumn;
    @FXML TableColumn<Undergraduate_Semester, String> uPayDateColumn;
    
    // Postgraduate_Semester table components
    @FXML TableView<Postgraduate_Semester> pTable;
    @FXML TableColumn<Postgraduate_Semester, String> pSemIdColumn;
    @FXML TableColumn<Postgraduate_Semester, String> pStudentIdColumn;
    @FXML TableColumn<Postgraduate_Semester, Double> pAmountColumn;
    @FXML TableColumn<Postgraduate_Semester, String> pStatusColumn;
    @FXML TableColumn<Postgraduate_Semester, String> pPayDateColumn;
    
    @FXML JFXTextField searchBar;
    @FXML JFXButton payButton;
    @FXML Text studentIdText, semIdText, amountText;
   
    
    // This method will return an ObservableList of payment details
    public ObservableList<Undergraduate_Semester> getUgPaymentDetails(){
        ObservableList<Undergraduate_Semester> pendingList = FXCollections.observableArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = con.prepareStatement("SELECT semester_id, student_id, amount, status, pay_date FROM undergraduate_semester");
            rs = ps.executeQuery();
            Undergraduate_Semester payment;
            
            while(rs.next()){
                payment = new Undergraduate_Semester(rs.getString("semester_id"), rs.getString("student_id"), rs.getDouble("amount"), rs.getString("status"), rs.getString("pay_date"));
                pendingList.add(payment);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pendingList;
    }
    
    // This method will return an ObservableList of payment details
    public ObservableList<Postgraduate_Semester> getPgPaymentDetails(){
        ObservableList<Postgraduate_Semester> pendingList = FXCollections.observableArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = con.prepareStatement("SELECT semester_id, student_id, amount, status, pay_date FROM postgraduate_semester");
            rs = ps.executeQuery();
            Postgraduate_Semester payment;
            
            while(rs.next()){
                payment = new Postgraduate_Semester(rs.getString("semester_id"), rs.getString("student_id"), rs.getDouble("amount"), rs.getString("status"), rs.getString("pay_date"));
                pendingList.add(payment);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pendingList;
    }
    
    public void fillUgTable(){
        // setup columns in the uTable
        uSemIdColumn.setCellValueFactory(new PropertyValueFactory<Undergraduate_Semester, String> ("semesterId"));
        uStudentIdColumn.setCellValueFactory(new PropertyValueFactory<Undergraduate_Semester, String> ("studentId"));
        uAmountColumn.setCellValueFactory(new PropertyValueFactory<Undergraduate_Semester, Double> ("amount"));
        uStatusColumn.setCellValueFactory(new PropertyValueFactory<Undergraduate_Semester, String> ("status"));
        uPayDateColumn.setCellValueFactory(new PropertyValueFactory<Undergraduate_Semester, String> ("payDate"));
        
        // load the data into the uTable
        uTable.setItems(getUgPaymentDetails());
    }
    
    public void fillPgTable(){
        // setup columns in the uTable
        pSemIdColumn.setCellValueFactory(new PropertyValueFactory<Postgraduate_Semester, String> ("semesterId"));
        pStudentIdColumn.setCellValueFactory(new PropertyValueFactory<Postgraduate_Semester, String> ("studentId"));
        pAmountColumn.setCellValueFactory(new PropertyValueFactory<Postgraduate_Semester, Double> ("amount"));
        pStatusColumn.setCellValueFactory(new PropertyValueFactory<Postgraduate_Semester, String> ("status"));
        pPayDateColumn.setCellValueFactory(new PropertyValueFactory<Postgraduate_Semester, String> ("payDate"));
        
        // load the data into the uTable
        pTable.setItems(getPgPaymentDetails());
    }
    
    // Show payment details of selected row
    public void clickRowActionPerformed(MouseEvent event){
        try{
            if(event.getSource() == uTable){
                table = 'u';
                studentIdText.setText(uTable.getSelectionModel().getSelectedItem().getStudentId());
                semIdText.setText(uTable.getSelectionModel().getSelectedItem().getSemesterId());
                amountText.setText(Double.toString(uTable.getSelectionModel().getSelectedItem().getAmount()));

                if(uTable.getSelectionModel().getSelectedItem().getStatus().equals("Paid")){
                    payButton.setText("PAID");
                    payButton.setDisable(true);
                }else{
                    payButton.setText("PAY");
                    payButton.setDisable(false);
                }
            }else if(event.getSource() == pTable){
                table = 'p';
                studentIdText.setText(pTable.getSelectionModel().getSelectedItem().getStudentId());
                semIdText.setText(pTable.getSelectionModel().getSelectedItem().getSemesterId());
                amountText.setText(Double.toString(pTable.getSelectionModel().getSelectedItem().getAmount()));

                if(pTable.getSelectionModel().getSelectedItem().getStatus().equals("Paid")){
                    payButton.setText("PAID");
                    payButton.setDisable(true);
                }else{
                    payButton.setText("PAY");
                    payButton.setDisable(false);
                }
            }
        }catch(Exception ex){}
    }
    
    // Doing payments
    public void payButtonPressed() throws SQLException{
        PreparedStatement ps = null;
        if(table == 'u'){
            ps = con.prepareStatement("UPDATE undergraduate_semester SET status = 'Paid', pay_date = ? WHERE semester_id = ? AND student_id = ?");
        }else if(table == 'p'){
            ps = con.prepareStatement("UPDATE postgraduate_semester SET status = 'Paid', pay_date = ?  WHERE semester_id = ? AND student_id = ?");
        }
        
        LocalDateTime localDate = LocalDateTime.now();
        
        ps.setString(1, localDate.toLocalDate().toString());
        ps.setString(2, semIdText.getText());
        ps.setString(3, studentIdText.getText());
        ps.executeUpdate();
        
        alerts('I', "Message", null, "Database Updated");
        fillUgTable();
        fillPgTable();
        
        payButton.setText("PAID");
        payButton.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fillUgTable();
        fillPgTable();
        
        // Wrap the ObservableList in a FilteredList (in default, display all data).
        FilteredList<Undergraduate_Semester> uFilteredData = new FilteredList<>(getUgPaymentDetails(), p -> true);
        FilteredList<Postgraduate_Semester> pFilteredData = new FilteredList<>(getPgPaymentDetails(), p -> true);
        
        // Set the filter Predicate whenever the filter changes.
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            uFilteredData.setPredicate(student -> {
                // If filter text is empty, display all students.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare student id of every undergraduate student with filter text.
                String upperCaseFilter = newValue.toUpperCase();

                if (student.getStudentId().contains(upperCaseFilter)) {
                    return true;
                }
                return false;
            });
            
            pFilteredData.setPredicate(student -> {
                // If filter text is empty, display all students.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare student id of every undergraduate student with filter text.
                String upperCaseFilter = newValue.toUpperCase();

                if (student.getStudentId().contains(upperCaseFilter)) {
                    return true;
                }
                return false;
            });
            
            // Wrap the FilteredList in a SortedList. 
            SortedList<Undergraduate_Semester> uSortedData = new SortedList<>(uFilteredData);
            SortedList<Postgraduate_Semester> pSortedData = new SortedList<>(pFilteredData);

            // Bind the SortedList comparator to the TableView comparator.
            uSortedData.comparatorProperty().bind(uTable.comparatorProperty());
            pSortedData.comparatorProperty().bind(pTable.comparatorProperty());

            // Add sorted data to the table.
            uTable.setItems(uSortedData);
            pTable.setItems(pSortedData);
        });
    }    
}