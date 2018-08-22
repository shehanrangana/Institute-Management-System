package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nsbm.NSBM;

public class HomeController implements Initializable {
    
    @FXML Pane minimizeButtonPane;
    
    NSBM nsbm = new NSBM();
    // This function called when mouse entered to the pane
    public void onMouseEntered(MouseEvent event){
        nsbm.setColor((Pane) event.getTarget());
    }
    
    // This function called after mouse exited from the pane
    public void onMouseExited(MouseEvent event){
        nsbm.resetColor((Pane) event.getTarget());
    }

    // Log out from the system
    public void logoutFromSystem(){
        System.exit(0);
    }
    
    // Minimize the window
    public void minimizeWindow(MouseEvent event){
        Stage stage = (Stage)minimizeButtonPane.getScene().getWindow();
        stage.setIconified(true);
    }
    
    // Go to the student view
    public void goToStudentInterface() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/views/Student.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    // Go to the staff view
    public void goToStaffInterface() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/views/Staff.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    // Go to the course view
    public void goToCourseInterface() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/views/Course.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    // Go to the subject view
    public void goToSubjectInterface() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/views/Subject.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    // Go to the result view
    public void goToResultInterface() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/views/Result.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    // Go to the payment view
    public void goToPaymentInterface() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/views/Payment.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
