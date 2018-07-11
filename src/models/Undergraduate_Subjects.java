package models;

import javafx.beans.property.SimpleStringProperty;

public class Undergraduate_Subjects {
    private SimpleStringProperty subjectCode;
    private SimpleStringProperty grade;
    
    public Undergraduate_Subjects(String subjectCode, String grade){
        this.subjectCode = new SimpleStringProperty(subjectCode);
        this.grade = new SimpleStringProperty(grade);
    }

    // Getters
    public String getSubjectCode() {
        return subjectCode.get();
    }

    public String getGrade() {
        return grade.get();
    }
}
