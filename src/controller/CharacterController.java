package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Character;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class CharacterController implements Initializable {

    //region UI controls

    @FXML
    private Button backBtn;
    @FXML
    private Label slotLabel;
    @FXML
    private FlowPane characterList;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox addCharacterBox;
    @FXML
    private Button addBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private ComboBox classBox;
    @FXML
    private ComboBox raceBox;
    @FXML
    private TextField levelField;
    @FXML
    private TextField nameField;
    @FXML
    private Label title;

    //endregion

    //region Fields

    private User mUser;
    private final UserService mUserService = new UserServiceImpl();

    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        final String backBtnImagePath = "/images/back_button.png";
        Image backImg = new Image(getClass().getResourceAsStream(backBtnImagePath));
        ImageView imageView = new ImageView(backImg);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        backBtn.setGraphic(imageView);

        addBtn.setOnAction(event -> {

            addCharacter();

            showWindow(true, false);
            showTitle("Characters".toUpperCase());
        });

        cancelBtn.setOnAction(event -> {
            showWindow(true, false);
            showTitle("Characters".toUpperCase());
        });

        levelField.setEditable(false);

        classBox.setItems(FXCollections.observableArrayList("Guardin", "Assassin", "Archmage", "Necromancer", "Prophet", "Shaman", "Druid", "Ranger"));
        raceBox.setItems(FXCollections.observableArrayList("Human", "Gnome", "Dwarf", "Elf", "Eladin", "Tiefling", "Deva", "Goliath"));
    }

    public void loadData(User user) {

        mUser = user;

        Integer characterSlotsAvailable = mUser.getCharacterSlots();
        Integer characterSlotsUsed = mUser.getCharacters() == null ? 0 : mUser.getCharacters().size();
        slotLabel.setText(String.format("SLOTS USED %s/%s", characterSlotsUsed, characterSlotsAvailable));
        loadCharacterList(characterSlotsUsed, characterSlotsAvailable);
    }

    private void addCharacter() {

        String name = nameField.getText();
        Object raceSelectedItem = raceBox.getSelectionModel().getSelectedItem();
        String race = raceSelectedItem != null ? raceSelectedItem.toString() : "";
        Object classSelectedItem = classBox.getSelectionModel().getSelectedItem();
        String characterClass = classSelectedItem != null ? classSelectedItem.toString() : "";
        Integer level = Integer.getInteger(levelField.getText());

        Character character = new Character(name, characterClass, race, level);

        mUser.setCharacter(character);
        boolean isAdded = mUserService.updateUser(mUser);

        if (isAdded) {
            updateValues(character);
        }
    }

    private void updateValues(Character character) {

        Button btn = constructCharacterBtn(getRandomAvatarPath());
        btn.setText(character.getCharacterName());

        Integer maxSlots = mUser.getCharacterSlots();
        Integer slotsUsed = mUser.getCharacters().size();

        slotLabel.setText(String.format("SLOTS USED %s/%s", slotsUsed, maxSlots));

        characterList.getChildren().add(0, btn);
    }

    private void loadCharacterList(int slotsUsed, int maxSlots) {

        int slotsAvailable = maxSlots - slotsUsed;

        for (Character character : mUser.getCharacters()) {

            Button btn = constructCharacterBtn(getRandomAvatarPath());
            btn.setText(character.getCharacterName());

            characterList.getChildren().add(btn);
        }

        for (int i = 0; i < slotsAvailable; i++) {

            final String backBtnImagePath = "/images/add.png";
            Button btn = constructCharacterBtn(backBtnImagePath);

            btn.setOnAction(event -> {
                showAddCharacterMenu();
            });

            characterList.getChildren().add(btn);
        }
    }

    private void showAddCharacterMenu() {
        showTitle("Add character".toUpperCase());
        cleanFieldValues();
        showWindow(false, true);
    }

    private void cleanFieldValues() {
        Random random = new Random();
        String level = String.valueOf(1 + random.nextInt(99));
        levelField.setText(level);

        nameField.setText("");
        classBox.getSelectionModel().clearSelection();
        raceBox.getSelectionModel().clearSelection();
    }

    public void handleBackBtn(ActionEvent actionEvent) {

        System.out.println("Go to main scene");

        final String fxmlPath = "/fxml/homepage.fxml";
        final String title = "Home";

        goToScene(actionEvent, fxmlPath, title);
    }

    private Button constructCharacterBtn(String imagePath) {
        Button btn = new Button();
        btn.setPrefWidth(125);
        btn.setPrefHeight(125);
        btn.setContentDisplay(ContentDisplay.TOP);
        btn.setTextAlignment(TextAlignment.JUSTIFY);
        btn.setStyle("-fx-alignment: center;");

        Image backImg = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(backImg);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        btn.setGraphic(imageView);

        return btn;
    }

    private void showWindow(boolean showCharacterList, boolean showAddCharacterPanel) {
        scrollPane.setVisible(showCharacterList);
        addCharacterBox.setVisible(showAddCharacterPanel);
    }

    private void showTitle(String header) {
        title.setText(header);
    }

    private void goToScene(ActionEvent actionEvent, String fxmlPath, String title) {
        Parent fxml = null;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/character.fxml"));
            loader.load();
            fxml = loader.getRoot();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        if (fxml != null) {
            Scene rootScene = new Scene(fxml, 960, 600);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            primaryStage.setTitle(String.format("%s - MMORPG", title));
            primaryStage.setScene(rootScene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } else {
            System.out.printf("Cannot navigate to the %s scene", title);
        }
    }

    private String getRandomAvatarPath() {
        Random randomAvatar = new Random();
        int avatarNr = 1 + randomAvatar.nextInt(9);

        return String.format("/images/avatars/%s.jpg", avatarNr);
    }
}