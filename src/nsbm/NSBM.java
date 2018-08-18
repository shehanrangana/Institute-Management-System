package nsbm;

import controllers.CourseController;
import database.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    
    private static Connection con;
    private double xOffset = 0;
    private double yOffset = 0;
    
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
        pane1.setBackground(new Background(new BackgroundFill(Color.valueOf("#468AC9"), CornerRadii.EMPTY, Insets.EMPTY)));
        pane2.setBackground(new Background(new BackgroundFill(Color.valueOf("#E2E6EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        text1.setFill(Color.valueOf("#FFFFFF"));
        text2.setFill(Color.valueOf("#7c7474"));
        anchorPane1.setVisible(true);
        anchorPane2.setVisible(false);
    }
    
    // Change color of 3 tabs when pressed mouse
    public static void changeTabColors(Pane pane1, Pane pane2, Pane pane3, Text text1, Text text2, Text text3){
        pane1.setBackground(new Background(new BackgroundFill(Color.valueOf("#468AC9"), CornerRadii.EMPTY, Insets.EMPTY)));
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
    
    // Alert messeges 
    public static void alerts(char alertType, String message, String header, String content){
        Alert alert = null;
        switch (alertType) {
            case 'C':
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                break;
            case 'E':
                alert = new Alert(Alert.AlertType.ERROR);
                break;
            case 'I':
                alert = new Alert(Alert.AlertType.INFORMATION);
                break;
            case 'N': 
                alert = new Alert(Alert.AlertType.NONE);
                break;
            case 'W':
                alert = new Alert(Alert.AlertType.WARNING);
                break;
            default:
                break;
        }
        alert.setTitle(message);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    // Insert faculties into the faculty table if not exists
    private static void createFacultyTable(){
        try{
            PreparedStatement ps1 = con.prepareStatement("INSERT INTO faculty VALUES('School of Business'), ('School of Computing'), ('School of Engineering')");
            ps1.executeUpdate();
            
        }catch(SQLException e){
            //e.printStackTrace();
            //System.out.println("Faculties already exists");
        }
    }
    
    // Add two semesters when new year arrived 
    private static void createSemesterTable(){
        LocalDateTime localDate = LocalDateTime.now();
        String newSemester1 = localDate.getYear() + "S01";
        String newSemester2 = localDate.getYear() + "S02";

        try{
            PreparedStatement ps1 = con.prepareStatement("INSERT INTO semester VALUES('"+ newSemester1 +"'), ('"+ newSemester2 +"')");  
            ps1.executeUpdate();

        }catch(SQLException e){
            //e.printStackTrace();
            //System.out.println("Semesters already added");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        
        try {
            con = dbConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        createFacultyTable();
        createSemesterTable();
        launch(args);
    }
    
}
