package models;

import javafx.beans.property.SimpleStringProperty;

public class Semester {
    private final SimpleStringProperty semesterId;
    private final SimpleStringProperty studentId;
    private final double amount;
    private final SimpleStringProperty status;
    private final SimpleStringProperty payDate;

    public Semester(String semesterId, String studentId, double amount, String status, String payDate) {
        this.semesterId = new SimpleStringProperty(semesterId);
        this.studentId = new SimpleStringProperty(studentId);
        this.amount = amount;
        this.status = new SimpleStringProperty(status);
        this.payDate = new SimpleStringProperty(payDate);
    }

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
