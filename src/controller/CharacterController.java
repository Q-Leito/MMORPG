package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import model.Character;
import utils.Constants;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

public class CharacterController extends Controller implements Initializable {

    //region UI controls
    @FXML
    private Label messageLabel;
    @FXML
    private Button backBtn;
    @FXML
    private Label slotLabel;
    @FXML
    private FlowPane characterList;
    @FXML
    private GridPane addPanel;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox addCharacterBox;
    @FXML
    private VBox characterInfoBox;

    @FXML
    private Button cancelBtn;
    @FXML
    private ComboBox classBox;
    @FXML
    private ComboBox characterRaceBox;
    @FXML
    private TextField levelField;
    @FXML
    private TextField characterNameField;

    @FXML
    private Label characterName;
    @FXML
    private Label characterClass;
    @FXML
    private Label characterLevel;
    @FXML
    private Label characterRace;
    @FXML
    private ImageView avatarImg;
    @FXML
    private Button addBtn;
    @FXML
    private ComboBox orderBox;
    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ImageView backImgBtnLayout = createImageBtnLayout(Constants.BACK_IMAGE_PATH, Constants.BACK_IMAGE_WIDTH, Constants.BACK_IMAGE_HEIGHT);
        backBtn.setGraphic(backImgBtnLayout);
        ImageView addImgBtnLayout = createImageBtnLayout(Constants.ADD_BUTTON_IMAGE_PATH, Constants.ADD_IMAGE_WIDTH, Constants.ADD_IMAGE_HEIGHT);
        addBtn.setGraphic(addImgBtnLayout);

        cancelBtn.setOnAction(event ->
        {
            showWindow(true, false, false);
            setTitle(Constants.CHARACTER_SCENE_HEADER.toUpperCase());
        });

        levelField.setEditable(false);

        orderBox.setItems(FXCollections.observableArrayList("Last added", "Level"));
        classBox.setItems(FXCollections.observableArrayList("Guardin", "Assassin", "Archmage", "Necromancer", "Prophet", "Shaman", "Druid", "Ranger"));
        characterRaceBox.setItems(FXCollections.observableArrayList("Human", "Gnome", "Dwarf", "Elf", "Eladin", "Tiefling", "Deva", "Goliath"));
    }

    @Override
    public void initializeData() {

        int slotsAvailable = getUser().getCharacterSlots();

        Set<Character> characters = getUser().getCharacters();
        int slotsUsed = characters == null ? 0 : characters.size();

        String slotLabelText = String.format("%s/%s", slotsUsed, slotsAvailable);
        slotLabel.setText(slotLabelText);

        loadCharacters();
    }

    public void addCharacterBtn_Click(ActionEvent actionEvent) {

        Integer maxSlots = getUser().getCharacterSlots();
        Integer slotsUsed = getUser().getCharacters().size();

        boolean isValidated = false;

        if (slotsUsed <= maxSlots) {
            String characterName = characterNameField.getText();
            String characterRace = (String) characterRaceBox.getSelectionModel().getSelectedItem();
            String characterClass = (String) classBox.getSelectionModel().getSelectedItem();
            String characterLevel = levelField.getText();

            isValidated = validateText(characterName, characterRace, characterClass, characterLevel);

            if (isValidated) {

                setTitle(Constants.CHARACTER_SCENE_HEADER.toUpperCase());
                showWindow(true, false, false);

                Character character = new Character(characterName, characterClass, characterRace, Integer.parseInt(characterLevel));

                getUser().setCharacter(character);

                boolean isAdded = getUserService().updateUser(getUser());

                if (isAdded) {
                    createCharacter(character);
                }
            }
        }

        messageLabel.setText(maxSlots < slotsUsed ? "All empty slots are used!" : !isValidated ? "All fields are required!" : Constants.EMPTY_STRING);
    }

    private void createCharacter(Character newCharacter) {

        Button btn = createCharacterBtn(getRandomAvatarPath(), Constants.AVATAR_IMAGE_WIDTH, Constants.AVATAR_IMAGE_HEIGHT);
        btn.setText(newCharacter.getCharacterName());

        Integer maxSlots = getUser().getCharacterSlots();
        Integer slotsUsed = getUser().getCharacters().size();
        slotLabel.setText(String.format("%s/%s", slotsUsed, maxSlots));

        characterList.getChildren().add(0, btn);
    }

    private void loadCharacters() {

        for (Character character : getUser().getCharacters()) {

            Button btn = createCharacterBtn(getRandomAvatarPath(), Constants.AVATAR_IMAGE_WIDTH, Constants.AVATAR_IMAGE_HEIGHT);
            btn.setText(String.format("%s\nLevel: %s\nClass: %s \nRace: %s",
                    character.getCharacterName(),
                    character.getCharacterLevel(),
                    character.getCharacterClass(),
                    character.getCharacterRace()));

            characterList.getChildren().add(btn);
        }
    }

    public void newCharacterBtn_Click(ActionEvent actionEvent) {

        setTitle("Add character".toUpperCase().toUpperCase());

        cleanFieldValues();
        showWindow(false, true, false);
    }

    private void cleanFieldValues() {
        Random random = new Random();
        String level = String.valueOf(1 + random.nextInt(99));
        levelField.setText(level);

        characterNameField.setText("");
        classBox.getSelectionModel().clearSelection();
        characterRaceBox.getSelectionModel().clearSelection();
    }

    public void handleBackBtn_Click(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        showScene(node, Constants.HOME_FXML_PATH, Constants.HOME_SCENE_HEADER, getUser());
    }

    private void showWindow(boolean showCharacterList, boolean showAddCharacterPanel, boolean showCharacterInfoBox) {
        characterInfoBox.setVisible(showCharacterInfoBox);
        scrollPane.setVisible(showCharacterList);
        addCharacterBox.setVisible(showAddCharacterPanel);
        addPanel.setVisible(!showCharacterInfoBox);
    }

    private Button createCharacterBtn(String imgPath, double imgWidth, double imgHeight) {
        Button characterBtn = new Button();
        characterBtn.setMaxWidth(475.0d);
        characterBtn.setMaxHeight(100.0d);
        characterBtn.setMinWidth(475.0d);
        characterBtn.setMinHeight(100.0d);
          characterBtn.setTextAlignment(TextAlignment.LEFT);

        ImageView avatarImgBtnLayout = createImageBtnLayout(imgPath, imgWidth, imgHeight);
        characterBtn.setGraphic(avatarImgBtnLayout);

        return characterBtn;
    }
}