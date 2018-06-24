package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nsbm.NSBM;

public class LoginController implements Initializable {
    
    @FXML Pane closeButtonPane;
    @FXML ImageView closeButton;
    @FXML JFXTextField userNameText;
    @FXML JFXPasswordField password;
    @FXML JFXButton logInButton;
    @FXML Text errorMessage;

    // Authentication process and open home window
    public void logIn() throws IOException{
        //if(userNameText.getText().equals("shehan") && password.getText().equals("1234")){  
            //errorMessage.setVisible(false);
            Parent root = FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            
            nsbm.NSBM nsbm = new NSBM();
            nsbm.paneMove(root, stage);
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            // Close login window
            Stage oldStage = (Stage) logInButton.getScene().getWindow();
            oldStage.close();
        /*}else{
            errorMessage.setVisible(true);
        }*/
    }
    
    // Exit from the login panel
    public void exitFromLoginPanel(){
        System.exit(0);
    }
    
    // Create NSBM object
    NSBM nsbm = new NSBM();
    // This function called when mouse entered to the pane
    public void onMouseEntered(MouseEvent event){
        nsbm.setColor((Pane) event.getTarget());
    }
    
    // This function called after mouse exited from the pane
    public void onMouseExited(MouseEvent event){
        nsbm.resetColor((Pane) event.getTarget());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
