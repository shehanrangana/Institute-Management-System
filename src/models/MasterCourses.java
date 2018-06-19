package models;

import javafx.beans.property.SimpleStringProperty;

public class MasterCourses {
    private SimpleStringProperty courseName;
    int duration;
    int creditLimit;
    private SimpleStringProperty facultyName;
    
    // Constructor
    public MasterCourses(String courseName, int duration, int creditLimit, String facultyName){
        this.courseName = new SimpleStringProperty(courseName);
        this.duration = duration;
        this.creditLimit = creditLimit;
        this.facultyName = new SimpleStringProperty(facultyName);
    }

    public String getCourseName() {
        return courseName.get();
    }

    public int getDuration() {
        return duration;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public String getFacultyName() {
        return facultyName.get();
    }
    
    
}
