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

public class SubjectController implements Initializable {

    @FXML AnchorPane businessAnchorPane, computingAnchorPane, engineeringAnchorPane, subjectHomeAnchorPane, addNewSubjectAnchorPane, timeAllocationAnchorPane;
    @FXML Pane businessPane, computingPane, engineeringPane;
    @FXML ImageView backFromAddSubject, backFromTimeAllocation;
    @FXML Text businessText, computingText, engineeringText;
    
    // Handle school of business tab
    public void selectBusinessTab(){
        businessPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#2B6CB7"), CornerRadii.EMPTY, Insets.EMPTY)));
        computingPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        engineeringPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        businessText.setFill(Color.valueOf("#FFFFFF"));
        computingText.setFill(Color.valueOf("#7c7474"));
        engineeringText.setFill(Color.valueOf("#7c7474"));
        businessAnchorPane.setVisible(true);
        computingAnchorPane.setVisible(false);
        engineeringAnchorPane.setVisible(false);
    }
    
    // Handle school of computing tab
    public void selectComputingTab(){
        computingPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#2B6CB7"), CornerRadii.EMPTY, Insets.EMPTY)));
        businessPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        engineeringPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        computingText.setFill(Color.valueOf("#FFFFFF"));
        businessText.setFill(Color.valueOf("#7c7474"));
        engineeringText.setFill(Color.valueOf("#7c7474"));
        computingAnchorPane.setVisible(true);
        businessAnchorPane.setVisible(false);
        engineeringAnchorPane.setVisible(false);
    }
    
    // Handle school of engineering tab
    public void selectEngineeringTab(){
        engineeringPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#2B6CB7"), CornerRadii.EMPTY, Insets.EMPTY)));
        businessPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        computingPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        engineeringText.setFill(Color.valueOf("#FFFFFF"));
        businessText.setFill(Color.valueOf("#7c7474"));
        computingText.setFill(Color.valueOf("#7c7474"));
        engineeringAnchorPane.setVisible(true);
        businessAnchorPane.setVisible(false);
        computingAnchorPane.setVisible(false);
    }
    
    // Switch to the add new subject pane
    public void addNewSubjectButtonPressed(){
        addNewSubjectAnchorPane.setVisible(true);
        subjectHomeAnchorPane.setVisible(false);
    }
    
    // Switch to the time allocate pane
    public void timeAllocateButtonPressed(){
        timeAllocationAnchorPane.setVisible(true);
        subjectHomeAnchorPane.setVisible(false);
    }
    
    // Back to subjects pane
    public void backToSubjectButtonPressed(MouseEvent event){
        subjectHomeAnchorPane.setVisible(true);
        
        // Identify which pane going to be invisible
        if(event.getTarget() == backFromAddSubject){
            addNewSubjectAnchorPane.setVisible(false);
        }else if(event.getTarget() == backFromTimeAllocation){
            timeAllocationAnchorPane.setVisible(false);
        } 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
