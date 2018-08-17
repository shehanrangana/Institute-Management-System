package models;

import javafx.beans.property.SimpleStringProperty;

public class Undergraduate_Semester extends Semester{
    
    public Undergraduate_Semester(String semesterId, String studentId, double amount, String status, String payDate) {
        super(semesterId, studentId, amount, status, payDate);
    }
    
}