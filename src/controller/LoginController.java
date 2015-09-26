package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    public void handleSignIn(ActionEvent actionEvent) {
        System.out.printf("Username: %s %n" +
                        "Password: %s %n",
                usernameField.getText(), passwordField.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }

    public void handleRegisterScreen(ActionEvent actionEvent) throws IOException {
        System.out.println("You clicked me!");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));

        Scene registerScene = new Scene(root, 960, 600);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        primaryStage.setScene(registerScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
