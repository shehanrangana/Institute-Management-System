package models;

public class PostgraduateStudent extends UndergraduateStudent{
    
    public PostgraduateStudent(String studentId, String initials, String firstName, String lastName, String addressLine1, String addressLine2, String addressLine3,
            String birthday, String gender, String email, String nic, String mobile, String fixed, String facultyName, String courseName) {
        
        super(studentId, initials, firstName, lastName, addressLine1, addressLine2, addressLine3, birthday, gender, email, nic, mobile, fixed, facultyName, courseName);
    }
    
}
