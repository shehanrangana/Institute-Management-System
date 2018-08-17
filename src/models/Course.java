package models;

import javafx.beans.property.SimpleStringProperty;

public class Course {
    private final SimpleStringProperty courseName;
    private final int duration;
    private final int creditLimit;
    private final SimpleStringProperty facultyName;

    public Course(String courseName, int duration, int creditLimit, String facultyName) {
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
