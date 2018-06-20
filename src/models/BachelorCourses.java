package models;

import javafx.beans.property.SimpleStringProperty;

public class BachelorCourses {
    private SimpleStringProperty courseName;
    private int duration;
    private int creditLimit;
    private SimpleStringProperty facultyName;
    
    // Constructor
    public BachelorCourses(String courseName, int duration, int creditLimit, String facultyName){
        this.courseName = new SimpleStringProperty(courseName);
        this.duration = duration;
        this.creditLimit = creditLimit;
        this.facultyName = new SimpleStringProperty(facultyName);
    }
    
    // Getters
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
