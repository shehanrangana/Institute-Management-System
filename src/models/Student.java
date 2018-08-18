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

    public Student(String studentId, String initials, String firstName, String lastName, String addressLine1, String addressLine2, String addressLine3, String birthday, String gender, String email, String nic, String mobile, String fixed, String facultyName, String courseName) {
        this.studentId = new SimpleStringProperty(studentId);
        this.initials =  new SimpleStringProperty(initials);
        this.firstName =  new SimpleStringProperty(firstName);
        this.lastName =  new SimpleStringProperty(lastName);
        this.addressLine1 =  new SimpleStringProperty(addressLine1);
        this.addressLine2 =  new SimpleStringProperty(addressLine2);
        this.addressLine3 =  new SimpleStringProperty(addressLine3);
        this.birthday =  new SimpleStringProperty(birthday);
        this.gender =  new SimpleStringProperty(gender);
        this.email =  new SimpleStringProperty(email);
        this.nic =  new SimpleStringProperty(nic);
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
        //System.out.println(firstName.get());
        return firstName.get();   
    }

    public String getLastName() {
        //System.out.println(lastName.get());
        return lastName.get();
    }
    
    public String getAddressLine1() {
        //System.out.println(addressLine1.get());
        return addressLine1.get();
    }

    public String getAddressLine2() {
        return addressLine2.get();
    }

    public String getAddressLine3() {
        return addressLine3.get();
    }

    public String getBirthday() {
        //System.out.println(birthday.get());
        return birthday.get();
    }

    public String getGender() {
        //System.out.println(gender.get());
        return gender.get();
    }

    public String getEmail() {
        //System.out.println(email.get());
        return email.get();
    }

    public String getNic() {
        //System.out.println(nic.get());
        return nic.get();
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
