package models;

public class UndergraduateStudent extends Student{
    
    public UndergraduateStudent(String studentId, String initials, String firstName, String lastName, String birthday, String gender, String email, String nic, String addressLine1, String addressLine2, String addressLine3, String mobile, String fixed, String facultyName, String courseName) {
        super(studentId, initials, firstName, lastName, birthday, gender, email, nic, addressLine1, addressLine2, addressLine3, mobile, fixed, facultyName, courseName);
    }
    
}