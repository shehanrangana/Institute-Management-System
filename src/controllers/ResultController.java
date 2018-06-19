package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ResultController implements Initializable {

    @FXML AnchorPane businessResultAnchorPane, computingResultAnchorPane, engineeringResultAnchorPane;
    @FXML Pane businessResultPane, computingResultPane, engineeringResultPane;
    @FXML Text businessResultText, computingResultText, engineeringResultText;
    
    // Handle results tab of school of business
    public void selectBusinessResultTab(){
        businessResultPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#2B6CB7"), CornerRadii.EMPTY, Insets.EMPTY)));
        computingResultPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        engineeringResultPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        businessResultText.setFill(Color.valueOf("#FFFFFF"));
        computingResultText.setFill(Color.valueOf("#7c7474"));
        engineeringResultText.setFill(Color.valueOf("#7c7474"));
        businessResultAnchorPane.setVisible(true);
        computingResultAnchorPane.setVisible(false);
        engineeringResultAnchorPane.setVisible(false);
    }
    
    // Handle results tab of school of computing
    public void selectComputingResultTab(){
        computingResultPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#2B6CB7"), CornerRadii.EMPTY, Insets.EMPTY)));
        businessResultPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        engineeringResultPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        computingResultText.setFill(Color.valueOf("#FFFFFF"));
        businessResultText.setFill(Color.valueOf("#7c7474"));
        engineeringResultText.setFill(Color.valueOf("#7c7474"));
        computingResultAnchorPane.setVisible(true);
        businessResultAnchorPane.setVisible(false);
        engineeringResultAnchorPane.setVisible(false);
    }
    
    // Handle results tab of school of engineering
    public void selectEngineeringResultTab(){
        engineeringResultPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#2B6CB7"), CornerRadii.EMPTY, Insets.EMPTY)));
        businessResultPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        computingResultPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        engineeringResultText.setFill(Color.valueOf("#FFFFFF"));
        businessResultText.setFill(Color.valueOf("#7c7474"));
        computingResultText.setFill(Color.valueOf("#7c7474"));
        engineeringResultAnchorPane.setVisible(true);
        businessResultAnchorPane.setVisible(false);
        computingResultAnchorPane.setVisible(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
