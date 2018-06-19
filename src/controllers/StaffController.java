package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class StaffController implements Initializable {
    
    @FXML AnchorPane staffHomeAnchorPane, addNewStaffAnchorPane, detailsAndUpdateAnchorPane, lecturerAnchorPane, instructorAnchorPane;
    @FXML Pane lecturerPane, instructorPane;
    @FXML ImageView backFromStaffReg, backFromDetails;
    @FXML Text lecturerText, instructorText;
    
    // Handle lecturer tab
    public void selectLecturerTab(){
        lecturerPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#2B6CB7"), CornerRadii.EMPTY, Insets.EMPTY)));
        instructorPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        lecturerText.setFill(Color.valueOf("#FFFFFF"));
        instructorText.setFill(Color.valueOf("#7c7474"));
        lecturerAnchorPane.setVisible(true);
        instructorAnchorPane.setVisible(false);
    }
    
    // Handle instructor tab
    public void selectInstructorTab(){
        instructorPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#2B6CB7"), CornerRadii.EMPTY, Insets.EMPTY)));
        lecturerPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        instructorText.setFill(Color.valueOf("#FFFFFF"));
        lecturerText.setFill(Color.valueOf("#7c7474"));
        instructorAnchorPane.setVisible(true);
        lecturerAnchorPane.setVisible(false);
    }
    
    // Switch to add new staff member pane
    public void newStaffMemberButtonPressed(){
        addNewStaffAnchorPane.setVisible(true);
        staffHomeAnchorPane.setVisible(false);
    }
    
    // Switch to the more details/update pane
    public void moreDetailsButtonPressed(){
        detailsAndUpdateAnchorPane.setVisible(true);
        staffHomeAnchorPane.setVisible(false);
    }
    
    // Back to staff pane
    public void backToStaffButtonPressed(MouseEvent event){
        staffHomeAnchorPane.setVisible(true);
        
        // Identify which pane going to be invisible
        if(event.getTarget() == backFromStaffReg){
            addNewStaffAnchorPane.setVisible(false);
        }else if(event.getTarget() == backFromDetails){
            detailsAndUpdateAnchorPane.setVisible(false);
        } 
    }
    
    // Update staff members' details
    public void updateDetailsButtonPressed(){
        // data update process code here
        staffHomeAnchorPane.setVisible(true);
        detailsAndUpdateAnchorPane.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
