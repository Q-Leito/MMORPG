package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomepageController {
    public static final Timestamp LAST_PAYMENT = null;
    private UserService mUserService = new UserServiceImpl();
    private ObservableList<User> mUsersList = FXCollections.observableArrayList();
    private StringProperty mUserName;
    private StringProperty mBalance;
    private StringProperty mFirstName;
    private StringProperty mLastName;
    private StringProperty mIban;
    private StringProperty mCharacterSlots;
    private StringProperty mLastPayment;
    private StringProperty mMonthsPayed;
    private StringProperty mPassword;
    private StringProperty mBanned;

    @FXML
    private Label errorLabel;
    @FXML
    private TextField balanceField;
    @FXML
    private ChoiceBox subscriptionChoice;

    public HomepageController() {
        mUserName = new SimpleStringProperty();
        mBalance = new SimpleStringProperty();
        mFirstName = new SimpleStringProperty();
        mLastName = new SimpleStringProperty();
        mIban = new SimpleStringProperty();
        mCharacterSlots = new SimpleStringProperty();
        mLastPayment = new SimpleStringProperty();
        mMonthsPayed = new SimpleStringProperty();
        mPassword = new SimpleStringProperty();
        mBanned = new SimpleStringProperty();
    }

    public boolean updateUser(User user) {
        return mUserService.updateUser(user);
    }

    public String getUserName() {
        return mUserName.get();
    }

    public StringProperty userNameProperty() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName.set(userName);
    }

    public String getBalance() {
        return mBalance.get();
    }

    public StringProperty balanceProperty() {
        return mBalance;
    }

    public void setBalance(String balance) {
        this.mBalance.set(balance);
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

    public String getLastName() {
        return mLastName.get();
    }

    public StringProperty lastNameProperty() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName.set(lastName);
    }

    public String getIban() {
        return mIban.get();
    }

    public StringProperty ibanProperty() {
        return mIban;
    }

    public void setIban(String iban) {
        this.mIban.set(iban);
    }

    public String getCharacterSlots() {
        return mCharacterSlots.get();
    }

    public StringProperty characterSlotsProperty() {
        return mCharacterSlots;
    }

    public void setCharacterSlots(String characterSlots) {
        this.mCharacterSlots.set(characterSlots);
    }

    public String getLastPayment() {
        return mLastPayment.get();
    }

    public StringProperty lastPaymentProperty() {
        return mLastPayment;
    }

    public void setLastPayment(String lastPayment) {
        this.mLastPayment.set(lastPayment);
    }

    public String getMonthsPayed() {
        return mMonthsPayed.get();
    }

    public StringProperty monthsPayedProperty() {
        return mMonthsPayed;
    }

    public void setMonthsPayed(String monthsPayed) {
        this.mMonthsPayed.set(monthsPayed);
    }

    public String getPassword() {
        return mPassword.get();
    }

    public StringProperty passwordProperty() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword.set(password);
    }

    public String getBanned() {
        return mBanned.get();
    }

    public StringProperty bannedProperty() {
        return mBanned;
    }

    public void setBanned(String banned) {
        this.mBanned.set(banned);
    }

    public void setCurrentUser(User currentUser) {
        setUserName(currentUser.getUsername());
        setBalance(currentUser.getBalance().toString());
        setFirstName(currentUser.getFirstName());
        setLastName(currentUser.getLastName());
        setIban(currentUser.getIban());
        setCharacterSlots(currentUser.getCharacterSlots().toString());
        if (currentUser.getLastPayment() != null) {
            setLastPayment(currentUser.getLastPayment().toString());
        }
        setMonthsPayed(currentUser.getMonthsPayed().toString());
        setPassword(currentUser.getPassword());
        setBanned(currentUser.getBanned().toString());
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

    public void addMoney(ActionEvent actionEvent) {
        if (!balanceField.getText().isEmpty()) {
            if (isDouble(balanceField.getText())) {
                if (Double.parseDouble(balanceField.getText()) > 0) {
                    double balance = Math.round((Double.parseDouble(getBalance()) +
                            Double.parseDouble(balanceField.getText())) * 100.0) / 100.0;

                    User updatedUser = new User(
                            getUserName(),
                            balance,
                            getFirstName(),
                            getLastName(),
                            getIban(),
                            Integer.parseInt(getCharacterSlots()),
                            lastPayment(getLastPayment()),
                            Integer.parseInt(getMonthsPayed()),
                            getPassword(),
                            Boolean.parseBoolean(getBanned())
                    );

                    updateUser(updatedUser);
                    setBalance(String.valueOf(balance));
                    balanceField.clear();
                } else {
                    errorLabel.setText("Please enter a number greater than zero!");
                }
            } else {
                errorLabel.setText("Please enter a number!");
            }

        } else {
            errorLabel.setText("Add amount to your bank account!");
        }
    }

    public void addSubscription(ActionEvent actionEvent) {
        if (subscriptionChoice.getValue() != null) {
            if (subscriptionChoice.getValue().equals("1 Month")) {
                if (Double.parseDouble(getBalance()) >= 5) {
                    subscriptionChoice(1, 5);
                    setBalance(String.valueOf(Double.parseDouble(getBalance()) - 5));
                    errorLabel.setText("");
                } else {
                    errorLabel.setText("Please add money to your account!");
                }
            } else if (subscriptionChoice.getValue().equals("2 Months")) {
                if (Double.parseDouble(getBalance()) >= 8) {
                    subscriptionChoice(2, 8);
                    setBalance(String.valueOf(Double.parseDouble(getBalance()) - 8));
                    errorLabel.setText("");
                } else {
                    errorLabel.setText("Please add money to your account!");
                }
            } else if (subscriptionChoice.getValue().equals("3 Months")) {
                if (Double.parseDouble(getBalance()) >= 10) {
                    subscriptionChoice(3, 10);
                    setBalance(String.valueOf(Double.parseDouble(getBalance()) - 10));
                    errorLabel.setText("");
                } else {
                    errorLabel.setText("Please add money to your account!");
                }
            } else if (subscriptionChoice.getValue().equals("1 Year")) {
                if (Double.parseDouble(getBalance()) >= 35) {
                    subscriptionChoice(12, 35);
                    setBalance(String.valueOf(Double.parseDouble(getBalance()) - 35));
                    errorLabel.setText("");
                } else {
                    errorLabel.setText("Please add money to your account!");
                }
            }
        }
    }

    private void subscriptionChoice(int subscriptionChoice, double balance) {
        int monthsPayed = Integer.parseInt(getMonthsPayed()) + subscriptionChoice;
        User updatedUser = new User(
                getUserName(),
                Double.parseDouble(getBalance()) - balance,
                getFirstName(),
                getLastName(),
                getIban(),
                Integer.parseInt(getCharacterSlots()),
                new Timestamp(new Date().getTime()),
                monthsPayed,
                getPassword(),
                Boolean.parseBoolean(getBanned())
        );

        updateUser(updatedUser);
        setMonthsPayed(String.valueOf(monthsPayed));
    }

    private Timestamp lastPayment(String lastPayment) {
        try {
            if (lastPayment != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                Date parsedDate = dateFormat.parse(lastPayment);
                return new Timestamp(parsedDate.getTime());
            } else {
                return LAST_PAYMENT;
            }
        } catch(ParseException e) {
            return LAST_PAYMENT;
        }
    }

    private boolean isDouble(String amount) {
        try {
            Double balance = Double.parseDouble(amount);
            System.out.println(balance + " is a number.");
            return true;
        } catch (NumberFormatException e) {
            System.out.println(amount + " is not a number.");
            return false;
        }
    }
}
