package models;

import javafx.beans.property.SimpleStringProperty;

public class Undergraduate_Assesment {
    private final SimpleStringProperty assesmentId;
    private final SimpleStringProperty studentId;
    private final SimpleStringProperty subjectCode;
    private final int mark;
    private final SimpleStringProperty type;

    public Undergraduate_Assesment(String assesmentId, String studentId, String subjectCode, int mark, String type) {
        this.assesmentId = new SimpleStringProperty(assesmentId);
        this.studentId = new SimpleStringProperty(studentId);
        this.subjectCode = new SimpleStringProperty(subjectCode);
        this.mark = mark;
        this.type = new SimpleStringProperty(type);
    }
    
    // Getters
    public String getAssesmentId() {
        return assesmentId.get();
    }

    public String getStudentId() {
        return studentId.get();
    }

    public String getSubjectCode() {
        return subjectCode.get();
    }

    public int getMark() {
        return mark;
    }

    public String getType() {
        return type.get();
    } 
}