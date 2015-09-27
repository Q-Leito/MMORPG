package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import java.io.IOException;

public class HomepageController {
    private UserService mUserService = new UserServiceImpl();
    private ObservableList<User> mUsersList = FXCollections.observableArrayList();
    private StringProperty mFirstName;
    private User mCurrentUser;

    public HomepageController() {
        mFirstName = new SimpleStringProperty();
    }

    public String getFirstName() {
        return mFirstName.get();
    }

    public StringProperty firstNameProperty() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName.set(firstName);
    }

    public void setCurrentUser(User currentUser) {
        mCurrentUser = currentUser;
        setFirstName("Welcome " + currentUser.getFirstName() + "!");
    }

    public void handleLogout(ActionEvent actionEvent) throws IOException {
        System.out.println("You clicked me!");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

        Scene rootScene = new Scene(root, 960, 600);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        primaryStage.setScene(rootScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
