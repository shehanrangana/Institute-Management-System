package models;

import javafx.beans.property.SimpleStringProperty;

public class Faculty {
    
    private SimpleStringProperty facultyName;
    
    // Constructor
    public Faculty(String facultyName){
        this.facultyName = new SimpleStringProperty(facultyName);
    }

    // Getters
    public String getFacultyName() {
        return facultyName.get();
    }

}
