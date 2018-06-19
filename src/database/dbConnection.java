package database;

import controllers.StudentController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

public class dbConnection {
    
    static Connection con = null;
    
    // MySQL and Java Connection
    public static Connection getConnection() throws ClassNotFoundException {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/nsbm_demo", "root", ""); 
            
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Message");
//            alert.setHeaderText(null);
//            alert.setContentText("Connected");
//            alert.showAndWait();
            
            return (Connection) con;
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Not Connected");
            alert.showAndWait();
            
            return null;
        }
    }  
}
