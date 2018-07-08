package models;

import javafx.beans.property.SimpleStringProperty;

public class Undergraduate_Semester {
    private SimpleStringProperty semesterId;
    private SimpleStringProperty studentId;
    private double amount;
    private SimpleStringProperty status;
    private SimpleStringProperty payDate;
    
    public Undergraduate_Semester(String semesterId, String studentId, double amount, String status, String payDate){
        this.semesterId = new SimpleStringProperty(semesterId);
        this.studentId = new SimpleStringProperty(studentId);
        this.amount = amount;
        this.status = new SimpleStringProperty(status);
        this.payDate = new SimpleStringProperty(payDate);
    }

    // Getters
    public String getSemesterId() {
        return semesterId.get();
    }

    public String getStudentId() {
        return studentId.get();
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status.get();
    }

    public String getPayDate() {
        return payDate.get();
    }
    
    
}
