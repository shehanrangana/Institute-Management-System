package models;

import javafx.beans.property.SimpleStringProperty;

public class Undergraduate_Subjects {
    private SimpleStringProperty subjectCode;
    
    public Undergraduate_Subjects(String subjectCode){
        this.subjectCode = new SimpleStringProperty(subjectCode);
    }

    // Getters
    public String getSubjectCode() {
        return subjectCode.get();
    }
    
    
}
