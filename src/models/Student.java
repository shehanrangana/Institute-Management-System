package models;

import javafx.beans.property.SimpleStringProperty;

public class Student {
    private final SimpleStringProperty studentId;
    private final SimpleStringProperty initials;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty birthday;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty email;
    private final SimpleStringProperty nic;
    private final SimpleStringProperty addressLine1;
    private final SimpleStringProperty addressLine2;
    private final SimpleStringProperty addressLine3;
    private final SimpleStringProperty mobile;
    private final SimpleStringProperty fixed;
    private final SimpleStringProperty facultyName;
    private final SimpleStringProperty courseName; 

    public Student(String studentId, String initials, String firstName, String lastName, String birthday, String gender, String email, String nic, String addressLine1, String addressLine2, String addressLine3, String mobile, String fixed, String facultyName, String courseName) {
        this.studentId = new SimpleStringProperty(studentId);
        this.initials =  new SimpleStringProperty(initials);
        this.firstName =  new SimpleStringProperty(firstName);
        this.lastName =  new SimpleStringProperty(lastName);
        this.birthday =  new SimpleStringProperty(birthday);
        this.gender =  new SimpleStringProperty(gender);
        this.email =  new SimpleStringProperty(email);
        this.nic =  new SimpleStringProperty(nic);
        this.addressLine1 =  new SimpleStringProperty(addressLine1);
        this.addressLine2 =  new SimpleStringProperty(addressLine2);
        this.addressLine3 =  new SimpleStringProperty(addressLine3);
        this.mobile =  new SimpleStringProperty(mobile);
        this.fixed =  new SimpleStringProperty(fixed);
        this.facultyName =  new SimpleStringProperty(facultyName);
        this.courseName =  new SimpleStringProperty(courseName);
    }

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