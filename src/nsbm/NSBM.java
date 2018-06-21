package nsbm;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class NSBM extends Application {
    
    public double xOffset = 0;
    public double yOffset = 0;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        
        // Remove default decoration
        stage.initStyle(StageStyle.UNDECORATED);
        
        paneMove(root, stage);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
    
    // Set color when mouse entered
    public void setColor(Pane pane){
        if(pane.getId().equals("minimizeButton")){
            pane.setBackground(new Background(new BackgroundFill(Color.valueOf("#63B6D9"), CornerRadii.EMPTY, Insets.EMPTY)));
        }else if(pane.getId().equals("logoutButton")){
            pane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E81123"), CornerRadii.EMPTY, Insets.EMPTY)));
        }else if(pane.getId().equals("closeButtonPane")){
            pane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E81123"), CornerRadii.EMPTY, Insets.EMPTY)));
        }else{
            pane.setBackground(new Background(new BackgroundFill(Color.valueOf("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
        } 
    }
    
    // Reset color when mouse exited
    public void resetColor(Pane pane){
        if(pane.getId().equals("minimizeButton") || pane.getId().equals("logoutButton")){
            pane.setBackground(new Background(new BackgroundFill(Color.valueOf("E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        }else if(pane.getId().equals("closeButtonPane")){
            pane.setBackground(new Background(new BackgroundFill(Color.valueOf("#383738"), CornerRadii.EMPTY, Insets.EMPTY)));
        }else{
            pane.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        } 
    }
    
    // Change color of 2 tabs when pressed mouse
    public static void changeTabColors(Pane pane1, Pane pane2, Text text1, Text text2, AnchorPane anchorPane1, AnchorPane anchorPane2){
        pane1.setBackground(new Background(new BackgroundFill(Color.valueOf("#2B6CB7"), CornerRadii.EMPTY, Insets.EMPTY)));
        pane2.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        text1.setFill(Color.valueOf("#FFFFFF"));
        text2.setFill(Color.valueOf("#7c7474"));
        anchorPane1.setVisible(true);
        anchorPane2.setVisible(false);
    }
    
    // Change color of 3 tabs when pressed mouse
    public static void changeTabColors(Pane pane1, Pane pane2, Pane pane3, Text text1, Text text2, Text text3){
        pane1.setBackground(new Background(new BackgroundFill(Color.valueOf("#2B6CB7"), CornerRadii.EMPTY, Insets.EMPTY)));
        pane2.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        pane3.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        text1.setFill(Color.valueOf("#FFFFFF"));
        text2.setFill(Color.valueOf("#7c7474"));
        text3.setFill(Color.valueOf("#7c7474"));
    }
    
    // This function for move undecorated windows
    public void paneMove(Parent root, Stage stage){
        // Set mouse pressed
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        
        // Set mouse drag
        root.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    public static void main(String[] args) throws ClassNotFoundException {
        launch(args);
        
    }
    
}
