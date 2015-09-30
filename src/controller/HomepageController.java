package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class HomepageController {
    private User mUser;

    @FXML
    public Label userFirstNameLabel;
    @FXML
    public Label userLastNameLabel;

    public void setCurrentUser(User currentUser) {
        mUser = currentUser;

        userFirstNameLabel.setText(mUser.getFirstName());
        userLastNameLabel.setText(mUser.getLastName());
    }

    public void handleLogout(ActionEvent actionEvent) throws IOException {
        System.out.println("You clicked me!");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

        Scene rootScene = new Scene(root, 960, 600);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        primaryStage.setTitle("Login - MMORPG");
        primaryStage.setScene(rootScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void handleProfile(ActionEvent actionEvent) throws IOException {
        openProfile(actionEvent, mUser);
    }

    public void openProfile(ActionEvent actionEvent, User currentUser) throws IOException {
        System.out.println("You clicked me!");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/profile.fxml"));
        loader.load();
        Parent root = loader.getRoot();

        Scene profileScene = new Scene(root, 960, 600);
        Stage profileStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        profileStage.setTitle("Profile - MMORPG");
        profileStage.setScene(profileScene);
        profileStage.setResizable(false);
        ProfileController profileController = loader.getController();
        profileController.setCurrentUser(currentUser);
        profileStage.show();
    }

    public void openCharacter(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/character.fxml"));
        loader.load();
        Parent root = loader.getRoot();

        Scene characterScene = new Scene(root, 960, 600);
        Stage characterStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        characterStage.setTitle("Characters - MMORPG");
        characterStage.setScene(characterScene);
        characterStage.setResizable(false);

        CharacterController characterController = loader.getController();
        characterController.loadData(mUser);
        characterStage.show();
    }
}
