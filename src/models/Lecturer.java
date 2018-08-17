package models;

import javafx.beans.property.SimpleStringProperty;

public class Lecturer extends StaffMember{
       
    public Lecturer(String staffId, String name, String mobile, String email, String room, String addressLine1, String addressLine2, String addressLine3) {
        super(staffId, name, mobile, email, room, addressLine1, addressLine2, addressLine3);
    }
       
}