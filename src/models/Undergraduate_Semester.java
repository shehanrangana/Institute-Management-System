package models;

import javafx.beans.property.SimpleStringProperty;

public class Undergraduate_Semester {
    private SimpleStringProperty semesterId;
    private SimpleStringProperty studentId;
    private double amount;
    
    public Undergraduate_Semester(String semesterId, String studentId, double amount){
        this.semesterId = new SimpleStringProperty(semesterId);
        this.studentId = new SimpleStringProperty(studentId);
        this.amount = amount;
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
    
    
}
