package models;

import javafx.beans.property.SimpleStringProperty;

public class Qualifications {
    private final SimpleStringProperty qualification;
    private final SimpleStringProperty institute;
    private final SimpleStringProperty compYear;
    
    public Qualifications(String qualification, String institute, String compYear){
        this.qualification = new SimpleStringProperty(qualification);
        this.institute = new SimpleStringProperty(institute);
        this.compYear = new SimpleStringProperty(compYear);
    }

    public String getQualification() {
        return qualification.get();
    }

    public String getInstitute() {
        return institute.get();
    }

    public String getCompYear() {
        return compYear.get();
    }
    
    
}
