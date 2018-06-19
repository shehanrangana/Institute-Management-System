package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class CourseController implements Initializable {

    @FXML AnchorPane bachelorAnchorPane, masterAnchorPane, courseHomeAnchorPane, addNewCourseAnchorPane;
    @FXML Pane bachelorPane, masterPane;
    @FXML Text bachelorText, masterText;
    
    // Handle bachelor tab
    public void selectBachelorTab(){
        bachelorPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#2B6CB7"), CornerRadii.EMPTY, Insets.EMPTY)));
        masterPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        bachelorText.setFill(Color.valueOf("#FFFFFF"));
        masterText.setFill(Color.valueOf("#7c7474"));
        bachelorAnchorPane.setVisible(true);
        masterAnchorPane.setVisible(false);
    }
    
    // Handle master tab
    public void selectMasterTab(){
        masterPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#2B6CB7"), CornerRadii.EMPTY, Insets.EMPTY)));
        bachelorPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        masterText.setFill(Color.valueOf("#FFFFFF"));
        bachelorText.setFill(Color.valueOf("#7c7474"));
        masterAnchorPane.setVisible(true);
        bachelorAnchorPane.setVisible(false);
    }
    
    // Switch to the add new course pane
    public void addCourseButtonPressed(){
        addNewCourseAnchorPane.setVisible(true);
        courseHomeAnchorPane.setVisible(false);
    }
    
    // Back to courses pane
    public void backToCourseButtonPressed(MouseEvent event){
        courseHomeAnchorPane.setVisible(true);
        addNewCourseAnchorPane.setVisible(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
