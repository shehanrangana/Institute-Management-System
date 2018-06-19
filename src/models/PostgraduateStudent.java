package models;

import javafx.beans.property.SimpleStringProperty;

public class PostgraduateStudent {
    private SimpleStringProperty studentId;
    private SimpleStringProperty initials;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty addressLine1;
    private SimpleStringProperty addressLine2;
    private SimpleStringProperty addressLine3;
    private SimpleStringProperty birthday;
    private SimpleStringProperty gender;
    private SimpleStringProperty email;
    private SimpleStringProperty nic;
    private SimpleStringProperty mobile;
    private SimpleStringProperty fixed;
    private SimpleStringProperty facultyName;
    private SimpleStringProperty courseName;
    
    // Constructor
    public PostgraduateStudent(String studentId, String initials, String firstName, String lastName, String addressLine1, String addressLine2, String addressLine3,
            String birthday, String gender, String email, String nic, String mobile, String fixed, String facultyName, String courseName){
        
        this.studentId = new SimpleStringProperty(studentId);
        this.initials = new SimpleStringProperty(initials);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.addressLine1 = new SimpleStringProperty(addressLine1);
        this.addressLine2 = new SimpleStringProperty(addressLine2);
        this.addressLine3 = new SimpleStringProperty(addressLine3);
        this.birthday = new SimpleStringProperty(birthday);
        this.gender = new SimpleStringProperty(gender);
        this.email = new SimpleStringProperty(email);
        this.nic = new SimpleStringProperty(nic);
        this.mobile = new SimpleStringProperty(mobile);
        this.fixed = new SimpleStringProperty(fixed);
        this.facultyName = new SimpleStringProperty(facultyName);
        this.courseName = new SimpleStringProperty(courseName);
    }

    // Getters
    public String getStudentId() {
        return studentId.get();
    }

    public String getInitials() {
        return initials.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getBirthday() {
        return birthday.get();
    }

    public String getGender() {
        return gender.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getNic() {
        return nic.get();
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

    public String getMobile() {
        return mobile.get();
    }

    public String getFixed() {
        return fixed.get();
    }

    public String getFacultyName() {
        return facultyName.get();
    }

    public String getCourseName() {
        return courseName.get();
    }
    
    
}
