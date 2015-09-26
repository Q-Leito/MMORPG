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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class UserRegistrationController {
    public static final double BALANCE = 0.0;
    public static final int CHARACTER_SLOTS = 5;
    public static final Timestamp LAST_PAYMENT = null;
    public static final int MONTHS_PAYED = 0;
    public static final boolean BANNED = false;
    private UserService mUserService = new UserServiceImpl();
    private ObservableList<User> mUsersList = FXCollections.observableArrayList();


    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField ibanField;
    @FXML private Button registerButton;

    public void addUser(User user) {
        mUserService.addUser(user);
    }

    public ObservableList<User> getUserList() {
        if (!mUsersList.isEmpty())
            mUsersList.clear();
        mUsersList = FXCollections.observableList((List<User>) mUserService.UserList());
        return mUsersList;
    }

    public void deleteUser(String userName) {
        mUserService.deleteUser(userName);
    }

    public void updateUser(User user) {
        mUserService.updateUser(user);
    }

    public void handleRegister(ActionEvent actionEvent) {
        System.out.printf("First name: %s %n" +
                        "Last name: %s %n" +
                        "Username: %s %n" +
                        "Password: %s %n" +
                        "IBAN: %s %n",
                firstNameField.getText(), lastNameField.getText(), usernameField.getText(),
                passwordField.getText(), ibanField.getText());

//        User user = new User();
//        user.setFirstName(firstNameField.getText());
//        user.setLastName(lastNameField.getText());
//        user.setUsername(usernameField.getText());
//        user.setPassword(passwordField.getText());
//        user.setIban(ibanField.getText());
//        user.setBalance(BALANCE);
//        user.setCharacterSlots(CHARACTER_SLOTS);
//        user.setLastPayment(LAST_PAYMENT);
//        user.setMonthsPayed(MONTHS_PAYED);
//        user.setBanned(BANNED);
//
//        addUser(user);

        if (actionEvent.getSource().equals(registerButton)) {
            User user = new User(
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
            addUser(user);
        }

//        addUser(new User(
//                usernameField.getText(),
//                BALANCE,
//                firstNameField.getText(),
//                lastNameField.getText(),
//                ibanField.getText(),
//                CHARACTER_SLOTS,
//                LAST_PAYMENT,
//                MONTHS_PAYED,
//                passwordField.getText(),
//                BANNED
//        ));
    }

    public void handleBackButton(Event event) throws IOException {
        System.out.println("You clicked me!");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

        Scene rootScene = new Scene(root, 960, 600);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        primaryStage.setScene(rootScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
