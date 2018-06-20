package models;

import javafx.beans.property.SimpleStringProperty;

public class Lecturer {
    private SimpleStringProperty staffId;
    private SimpleStringProperty name;
    private SimpleStringProperty mobile;
    private SimpleStringProperty email;
    private SimpleStringProperty room;
    private SimpleStringProperty addressLine1;
    private SimpleStringProperty addressLine2;
    private SimpleStringProperty addressLine3;
    
    public Lecturer(String staffId, String name, String mobile, String email, String room, String addressLine1, String addressLine2, String addressLine3){
        this.staffId = new SimpleStringProperty(staffId);
        this.name = new SimpleStringProperty(name);
        this.mobile = new SimpleStringProperty(mobile);
        this.email = new SimpleStringProperty(email);
        this.room = new SimpleStringProperty(room);
        this.addressLine1 = new SimpleStringProperty(addressLine1);
        this.addressLine2 = new SimpleStringProperty(addressLine2);
        this.addressLine3 = new SimpleStringProperty(addressLine3);
    }
    
    // Getters
    public String getStaffId() {
        return staffId.get();
    }

    public String getName() {
        return name.get();
    }

    public String getMobile() {
        return mobile.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getRoom() {
        return room.get();
    }

    public String getAddressLine1() {
        return addressLine1.get();
    }

    public String getAddressLine2() {
        return addressLine2.get();
    }

    public String getAddressLine3() {
        return addressLine3.get();
    }
    
}
