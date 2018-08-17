package models;

import javafx.beans.property.SimpleStringProperty;

public class Undergraduate_Assesment extends Assesment{
    
    public Undergraduate_Assesment(String assesmentId, String studentId, String subjectCode, int mark, String type) {
        super(assesmentId, studentId, subjectCode, mark, type);
    }
    
}