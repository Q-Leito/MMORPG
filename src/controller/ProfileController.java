package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    private UserService mUserService = new UserServiceImpl();
    private User mUser;

    @FXML
    public Button backToHomepage;
    @FXML
    public Label usernameLabel;
    @FXML
    public Label userBalanceLabel;
    @FXML
    public Label userFirstNameLabel;
    @FXML
    public Label userLastNameLabel;
    @FXML
    public Label userIbanLabel;
    @FXML
    public Label userCharacterSlotsLabel;
    @FXML
    public Label userMonthsPayedLabel;
    @FXML
    public Label errorLabel;
    @FXML
    public TextField balanceField;
    @FXML
    public ChoiceBox characterSlots;
    @FXML
    public ChoiceBox subscriptionChoice;

    public ProfileController() {

    }

    public boolean updateUser(User user) {
        return mUserService.updateUser(user);
    }

    public void setCurrentUser(User currentUser) {
        mUser = currentUser;
        usernameLabel.setText(mUser.getUsername());
        userBalanceLabel.setText(String.valueOf(mUser.getBalance()));
        userFirstNameLabel.setText(mUser.getFirstName());
        userLastNameLabel.setText(mUser.getLastName());
        userIbanLabel.setText(mUser.getIban());
        userCharacterSlotsLabel.setText(String.valueOf(mUser.getCharacterSlots()));
        userMonthsPayedLabel.setText(String.valueOf(mUser.getMonthsPayed()));
    }

    public void openHomepage(ActionEvent actionEvent) throws IOException {
        System.out.println("You clicked me!");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/homepage.fxml"));
        loader.load();
        Parent root = loader.getRoot();

        Scene homepageScene = new Scene(root, 960, 600);
        Stage homepageStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        homepageStage.setTitle("Homepage - MMORPG");
        homepageStage.setScene(homepageScene);
        homepageStage.setResizable(false);
        HomepageController homepageController = loader.getController();
        homepageController.setCurrentUser(mUser);
        homepageStage.show();
    }

    public void addMoney() {
        String errorMessage = checkInput(balanceField);
        if (errorMessage != null) {
            errorLabel.setText(errorMessage);
            balanceField.clear();
            return;
        }
        double balance = Math.round((mUser.getBalance() +
                Double.parseDouble(balanceField.getText())) * 100.0) / 100.0;

        mUser.setBalance(balance);
        boolean isUpdated = updateUser(mUser);

        if (isUpdated) {
            userBalanceLabel.setText(String.valueOf(balance));
        }
        balanceField.clear();
    }

    private String checkInput(TextField textField) {
        String id = textField.getId();

        switch (id) {
            case "balanceField":
                String content = textField.getText();
                if (content.isEmpty())
                    return "Add amount to your bank account!";

                if (!isDouble(content))
                    return "Please enter a number!";

                if (Double.parseDouble(content) <= 0)
                    return "Please enter a number greater than zero!";
                break;
        }


        return null;
    }

    public void addSlot() {
        int index = characterSlots.getSelectionModel().getSelectedIndex() + 1;
        checkBalance(index);
    }

    private int addCharacterSlot(int characterSlot) {
        return mUser.getCharacterSlots() + characterSlot;
    }

    private void checkBalance(int balance) {
        if (mUser.getBalance() >= balance) {
            int newCharacterSlots = addCharacterSlot(balance);

            Double newBalance = mUser.getBalance() - balance;
            mUser.setCharacterSlots(newCharacterSlots);
            mUser.setBalance(newBalance);

            boolean isUpdated = updateUser(mUser);

            if (isUpdated) {
                userBalanceLabel.setText(String.valueOf(newBalance));
                userCharacterSlotsLabel.setText(String.valueOf(newCharacterSlots));
            }

            errorLabel.setText("");
        } else {
            errorLabel.setText("Please add money to your account!");
        }
    }

    private void checkBalance(int months, int balance) {
        if (mUser.getBalance() >= balance) {
            int monthsPayed = mUser.getMonthsPayed() + months;
            double newBalance = mUser.getBalance() - balance;

            mUser.setMonthsPayed(monthsPayed);
            mUser.setLastPayment(new Timestamp(new Date().getTime()));
            mUser.setBalance(newBalance);

            boolean isUpdated = updateUser(mUser);
            if (isUpdated) {
                userMonthsPayedLabel.setText(String.valueOf(monthsPayed));
                userBalanceLabel.setText(String.valueOf(newBalance));
            }

            errorLabel.setText("");
        } else {
            errorLabel.setText("Please add money to your account!");
        }
    }


    public void addSubscription() {
        int index = subscriptionChoice.getSelectionModel().getSelectedIndex() + 1;
        switch (index) {
            case 1:
                checkBalance(1, 5);
                break;
            case 2:
                checkBalance(2, 8);
                break;
            case 3:
                checkBalance(3, 10);
                break;
            case 4:
                checkBalance(12, 35);
                break;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final String backBtnImagePath = "/images/back_button.png";
        Image backImg = new Image(getClass().getResourceAsStream(backBtnImagePath));
        ImageView imageView = new ImageView(backImg);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        backToHomepage.setGraphic(imageView);
    }
}
