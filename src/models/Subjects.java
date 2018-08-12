package models;

import javafx.beans.property.SimpleStringProperty;

public class Subjects {
    private final SimpleStringProperty subjectCode;
    private final SimpleStringProperty subjectName;
    private final double allocatedTime;
    private final double fee;
    private final int credit;
    private final double duration;
    private final SimpleStringProperty location;
    private final SimpleStringProperty courseName;
    private final SimpleStringProperty lecturerId;

    public Subjects(String subjectCode, String subjectName, double allocatedTime, double fee, int credit, double duration, String location, String courseName, String lecturerId) {
        this.subjectCode = new SimpleStringProperty(subjectCode);
        this.subjectName = new SimpleStringProperty(subjectName);
        this.allocatedTime = allocatedTime;
        this.fee = fee;
        this.credit = credit;
        this.duration = duration;
        this.location = new SimpleStringProperty(location);
        this.courseName = new SimpleStringProperty(courseName);
        this.lecturerId = new SimpleStringProperty(lecturerId);
    }
    
    // Getters
    public String getSubjectCode() {
        return subjectCode.get();
    }

    public String getSubjectName() {
        return subjectName.get();
    }

    public double getAllocatedTime() {
        return allocatedTime;
    }

    public double getFee() {
        return fee;
    }

    public int getCredit() {
        return credit;
    }

    public double getDuration() {
        return duration;
    }

    public String getLocation() {
        return location.get();
    }

    public String getCourseName() {
        return courseName.get();
    }

    public String getLecturerId() {
        return lecturerId.get();
    }   
}