package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import java.io.IOException;
import java.sql.Timestamp;

public class RegistrationController {
    public static final double BALANCE = 0.0;
    public static final int CHARACTER_SLOTS = 5;
    public static final Timestamp LAST_PAYMENT = null;
    public static final int MONTHS_PAYED = 0;
    public static final boolean BANNED = false;
    private UserService mUserService = new UserServiceImpl();
    private ObservableList<User> mUsersList = FXCollections.observableArrayList();

    @FXML
    private Label errorLabel;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField ibanField;
    @FXML
    private Button registerButton;

    public boolean addUser(User user) {
        return mUserService.addUser(user);
    }

    public ObservableList<User> getUserList() {
        if (!mUsersList.isEmpty())
            mUsersList.clear();
        mUsersList = FXCollections.observableList(mUserService.UserList());
        return mUsersList;
    }

    public void handleRegister(ActionEvent actionEvent) throws IOException {
        System.out.printf("First name: %s %n" +
                        "Last name: %s %n" +
                        "Username: %s %n" +
                        "Password: %s %n" +
                        "IBAN: %s %n",
                firstNameField.getText(), lastNameField.getText(), usernameField.getText(),
                passwordField.getText(), ibanField.getText());

        if (getUserList().size() <= 0) {
            registerUser(actionEvent);
        } else {
            for (User user : getUserList()) {
                if (!usernameField.getText().equals(user.getUsername())) {
                    registerUser(actionEvent);
                    return;
                } else {
                    System.out.println("Duplicate Username!");
                    errorLabel.setText("Sorry, but "
                            + usernameField.getText()
                            + " already  exist. Try another!");
                }
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

    private void registerUser(ActionEvent actionEvent) throws IOException {

        if (!firstNameField.getText().isEmpty() &&
                !lastNameField.getText().isEmpty() &&
                !usernameField.getText().isEmpty() &&
                !passwordField.getText().isEmpty() &&
                !ibanField.getText().isEmpty()) {

            User newUser = new User(
                    usernameField.getText(),
                    BALANCE,
                    firstNameField.getText(),
                    lastNameField.getText(),
                    ibanField.getText(),
                    CHARACTER_SLOTS,
                    LAST_PAYMENT,
                    MONTHS_PAYED,
                    passwordField.getText(),
                    BANNED
            );

            boolean isAdded = addUser(newUser);

            if (isAdded) {
                openHomepage(actionEvent, newUser);
            } else {
                System.out.println("Something went wrong!");
            }
        } else {
            errorLabel.setText("All fields are required!");
        }
    }

    public void handleBackButton(Event event) throws IOException {
        System.out.println("You clicked me!");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

        Scene rootScene = new Scene(root, 960, 600);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        primaryStage.setTitle("Login - MMORPG");
        primaryStage.setScene(rootScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
