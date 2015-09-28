package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private UserService mUserService = new UserServiceImpl();
    private ObservableList<User> mUsersList = FXCollections.observableArrayList();

    @FXML
    private Label errorLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public ObservableList<User> getUserList() {
        if (!mUsersList.isEmpty())
            mUsersList.clear();
        mUsersList = FXCollections.observableList(mUserService.userList());
        return mUsersList;
    }

    public void handleSignIn(ActionEvent actionEvent) throws IOException {
        System.out.printf("Username: %s %n" +
                        "Password: %s %n",
                usernameField.getText(), passwordField.getText());

        for (User user : getUserList()) {
            if (usernameField.getText().equals(user.getUsername()) && passwordField.getText().equals(user.getPassword())) {
                System.out.println("Valid Credentials!");
                openHomepage(actionEvent, user);
                return;
            } else {
                System.out.println("Invalid Credentials!");
                errorLabel.setText("Invalid username or password!");
            }
        }
    }

    private void openHomepage(ActionEvent actionEvent, User currentUser) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/homepage.fxml"));
        loader.load();
        Parent root = loader.getRoot();

        Scene homepageScene = new Scene(root, 960, 600);
        Stage homepageStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        homepageStage.setTitle("Home - MMORPG");
        homepageStage.setScene(homepageScene);
        homepageStage.setResizable(false);
        HomepageController homepageController = loader.getController();
        homepageController.setCurrentUser(currentUser);
        homepageStage.show();
    }

    public void openRegisterScreen(ActionEvent actionEvent) throws IOException {
        System.out.println("You clicked me!");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));

        Scene registerScene = new Scene(root, 960, 600);
        Stage registerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        registerStage.setTitle("Registration - MMORPG");
        registerStage.setScene(registerScene);
        registerStage.setResizable(false);
        registerStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }
}
