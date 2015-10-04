package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.User;
import utils.Constants;
import utils.Helpers;

import java.sql.Timestamp;
import java.util.Date;

public class ProfileController extends Controller {

    //region UI controls

    @FXML
    public Label messageLabel;
    @FXML
    public Button backButton;

    @FXML
    public Label usernameLabel;
    @FXML
    public Label userBalanceLabel;
    @FXML
    public Label userIbanLabel;
    @FXML
    public Label userCharacterSlotsLabel;
    @FXML
    public Label userMonthsPayedLabel;

    @FXML
    public TextField balanceField;
    @FXML
    public ChoiceBox characterSlotBox;
    @FXML
    public ChoiceBox subscriptionBox;

    //endregion

    //region Methods

    @FXML
    public void initialize() {

        ImageView backImgBtnLayout = createImageBtnLayout(Constants.BACK_IMAGE_PATH, Constants.BACK_IMAGE_WIDTH, Constants.BACK_IMAGE_HEIGHT);
        backButton.setGraphic(backImgBtnLayout);
    }

    @Override
    public void load() {

        String username = getUser().getUsername();
        String userFirstName = getUser().getFirstName();
        String userLastName = getUser().getLastName();
        String iban = getUser().getIban();
        String balance = String.valueOf(getUser().getBalance());
        String userCharacterSlots = String.valueOf(getUser().getCharacterSlots());
        String monthsPayed = String.valueOf(getUser().getMonthsPayed());

        usernameLabel.setText(username);
        userBalanceLabel.setText(balance);
        userIbanLabel.setText(iban);
        userCharacterSlotsLabel.setText(userCharacterSlots);
        userMonthsPayedLabel.setText(monthsPayed);

        String header = String.format("Welcome %s %s!", userFirstName, userLastName);
        setTitle(header);
    }

    public void handleBackBtn_Click(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        showScene(node, Constants.HOME_FXML_PATH, Constants.HOME_SCENE_HEADER, getUser());
    }

    public void addMoneyBtn_Click(ActionEvent actionEvent) {

        String amountInput = balanceField.getText();
        String warning = checkBalanceInput(amountInput);

        if (warning == null) {
            double userBalance = getUser().getBalance();
            double amount = Double.parseDouble(amountInput);
            double newBalance = ((userBalance + amount) * 100.0) / 100.0;

            getUser().setBalance(newBalance);

            boolean isUpdated = updateUser(getUser());
            if (isUpdated) {
                userBalanceLabel.setText(String.valueOf(newBalance));
            }
        }

        messageLabel.setText(warning != null ? warning : Constants.EMPTY_STRING);
        balanceField.clear();
    }

    public void addSlotBtn_Click(ActionEvent actionEvent) {

        String selectedItem = (String) characterSlotBox.getSelectionModel().getSelectedItem();
        int amount;

        switch (selectedItem) {
            case "1 Slot":
            default:
                amount = 1;
                break;
            case "2 Slots":
                amount = 2;
                break;
            case "3 Slots":
                amount = 3;
                break;
            case "4 Slots":
                amount = 4;
                break;
            case "5 Slots":
                amount = 5;
                break;
        }

        if (getUser().getBalance() >= amount) {
            int newCharacterSlots = getUser().getCharacterSlots() + amount;

            Double newBalance = getUser().getBalance() - amount;
            getUser().setCharacterSlots(newCharacterSlots);
            getUser().setBalance(newBalance);

            boolean isUpdated = updateUser(getUser());

            if (isUpdated) {
                userBalanceLabel.setText(String.valueOf(newBalance));
                userCharacterSlotsLabel.setText(String.valueOf(newCharacterSlots));
            }
        }

        messageLabel.setText(getUser().getBalance() < amount ? "Please add money to your account!" : Constants.EMPTY_STRING);
    }

    public boolean updateUser(User user) {
        return getUserService().updateUser(user);
    }

    private void addAmount(int months, int balance) {
        if (getUser().getBalance() >= balance) {
            int monthsPayed = getUser().getMonthsPayed() + months;
            double newBalance = getUser().getBalance() - balance;
            Timestamp lastPayment = new Timestamp(new Date().getTime());

            getUser().setMonthsPayed(monthsPayed);
            getUser().setLastPayment(lastPayment);
            getUser().setBalance(newBalance);

            boolean isUpdated = updateUser(getUser());
            if (isUpdated) {
                userMonthsPayedLabel.setText(String.valueOf(monthsPayed));
                userBalanceLabel.setText(String.valueOf(newBalance));
            }
        }

        messageLabel.setText(getUser().getBalance() < balance ? "Please add money to your account!" : Constants.EMPTY_STRING);
    }

    public void addSubscriptionBtn_Click() {
        String selectedItem = (String) subscriptionBox.getSelectionModel().getSelectedItem();

        int months;
        int balance;

        switch (selectedItem) {
            case "1 Month":
            default:
                months = 1;
                balance = 5;
                break;
            case "2 Months":
                months = 2;
                balance = 8;
                break;
            case "3 Months":
                months = 3;
                balance = 10;
                break;
            case "1 Year":
                months = 12;
                balance = 35;
                break;
        }

        addAmount(months, balance);
    }

    private String checkBalanceInput(String value) {
        if (value == null || value.isEmpty()) {
            return "Add amount to your bank account!";
        } else if (!Helpers.isDouble(value)) {
            return "Please enter a number!";
        } else if (Double.parseDouble(value) <= 0) {
            return "Please enter a number greater than zero!";
        }

        return null;
    }

    //endregion
}