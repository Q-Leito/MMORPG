package controller;

import com.google.common.base.Stopwatch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import model.Character;
import service.CharacterService;
import service.CharacterServiceImpl;
import utils.Constants;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    private ScrollPane scrollPane;
    @FXML
    private VBox addCharacterBox;
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
    private Button addButton;
    @FXML
    public Button createCharactersBtn;

    //endregion

    private ObservableList<Character> mCharacterList;
    private CharacterService mCharacterService;

    public CharacterController() {

        setCharacterService(new CharacterServiceImpl());
    }

    public CharacterService getCharacterService() {
        return mCharacterService;
    }

    public void setCharacterService(CharacterService characterService) {
        mCharacterService = characterService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ImageView backImgBtnLayout = createImageBtnLayout(Constants.BACK_IMAGE_PATH, Constants.BACK_IMAGE_WIDTH, Constants.BACK_IMAGE_HEIGHT);
        backBtn.setGraphic(backImgBtnLayout);
        ImageView addImgBtnLayout = createImageBtnLayout(Constants.ADD_BUTTON_IMAGE_PATH, Constants.ADD_IMAGE_WIDTH, Constants.ADD_IMAGE_HEIGHT);
        addButton.setGraphic(addImgBtnLayout);

        cancelBtn.setOnAction(event ->
        {
            showWindow(true, false);
            setTitle(String.format("%s - %s %s!", Constants.CHARACTER_SCENE_HEADER, getUser().getFirstName(), getUser().getLastName()));
        });

        levelField.setEditable(false);

        classBox.setItems(FXCollections.observableArrayList("Guardin", "Assassin", "Archmage", "Necromancer", "Prophet", "Shaman", "Druid", "Ranger"));
        characterRaceBox.setItems(FXCollections.observableArrayList("Human", "Gnome", "Dwarf", "Elf", "Eladin", "Tiefling", "Deva", "Goliath"));

        long characterListSize = getCharacterService().count();
        createCharactersBtn.setVisible(!(characterListSize > 1000));
    }

    public void createCharactersBtn_Click(ActionEvent actionEvent) {
        Integer slotsAvailable = getUser().getCharacterSlots();
        Integer slotsUsed = getUser().getCharacters().size();

        for (int i = 0; i < 1000; i++) {
            String characterName = String.format("Roman%s", i);
            String characterClass = "Assassin";
            String characterRace = "Human";
            int characterLevel = 100;

            Character character = new Character(characterName, characterClass, characterRace, characterLevel);
            boolean isAdded = getCharacterService().addCharacter(character);
            getUser().setCharacter(character);

            boolean isUpdated = getUserService().updateUser(getUser());
            slotsAvailable++;

            if (isUpdated) {
                createCharacter(character);
                String slotLabelText = String.valueOf((slotsAvailable - slotsUsed));
                slotLabel.setText(slotLabelText);
            }
            System.out.printf("Character '%s' is added: %s \n", characterName, isAdded);
        }
    }

    @Override
    public void load() {

        Integer characterSlots = getUser().getCharacterSlots();
        int slotsAvailable = characterSlots == null ? 0 : characterSlots;

        Set<Character> characters = getUser().getCharacters();
        int slotsUsed = characters == null ? 0 : characters.size();

        String slotLabelText = String.valueOf(slotsAvailable - slotsUsed);
        slotLabel.setText(slotLabelText);

        String header = String.format("%s - %s %s!", Constants.CHARACTER_SCENE_HEADER, getUser().getFirstName(),
                getUser().getLastName());
        setTitle(header);

        loadCharacters();
    }

    public void addCharacterBtn_Click(ActionEvent actionEvent) {

        Integer slotsAvailable = getUser().getCharacterSlots();
        Integer slotsUsed = getUser().getCharacters().size();

        boolean isValidated;
        boolean characterNameExists;

        String characterName = characterNameField.getText();

        String characterRace = (String) characterRaceBox.getSelectionModel().getSelectedItem();
        String characterClass = (String) classBox.getSelectionModel().getSelectedItem();
        String characterLevel = levelField.getText();

        isValidated = validateText(characterName, characterRace, characterClass, characterLevel);
        characterNameExists = findCharacter(characterName);

        if (isValidated && !characterNameExists) {

            setTitle(Constants.CHARACTER_SCENE_HEADER.toUpperCase());
            showWindow(true, false);

            Character character = new Character(characterName, characterClass, characterRace, Integer.parseInt(characterLevel));

            getUser().setCharacter(character);

            boolean isAdded = getUserService().updateUser(getUser());

            if (isAdded) {
                createCharacter(character);

                String slotLabelText = String.valueOf(slotsAvailable - slotsUsed - 1);
                slotLabel.setText(slotLabelText);
            }
        }

        messageLabel.setText(slotsAvailable < slotsUsed ? "All  slots are in use!" : !isValidated ? "All fields are required!" : characterNameExists ? String.format("Sorry, but %s already exist. Try another!", characterName) : Constants.EMPTY_STRING);
    }

    private void createCharacter(Character newCharacter) {

        Button btn = createCharacterBtn(getRandomAvatarPath(), Constants.AVATAR_IMAGE_WIDTH, Constants.AVATAR_IMAGE_HEIGHT);
        btn.setText(String.format("%s\nLevel: %s\nClass: %s \nRace: %s",
                newCharacter.getCharacterName(),
                newCharacter.getCharacterLevel(),
                newCharacter.getCharacterClass(),
                newCharacter.getCharacterRace()));

        characterList.getChildren().add(0, btn);
    }

    private void loadCharacters() {

        getUser().getCharacters().forEach(this::createCharacter);
    }

    public void newCharacterBtn_Click(ActionEvent actionEvent) {

        Integer maxSlots = getUser().getCharacterSlots();
        Integer slotsUsed = getUser().getCharacters().size();

        if (maxSlots == slotsUsed) {
            return;
        }

        setTitle("Add Character");

        cleanFieldValues();
        showWindow(false, true);
    }

    private void cleanFieldValues() {
        Random random = new Random();
        String level = String.valueOf(1 + random.nextInt(99));
        levelField.setText(level);

        messageLabel.setText(Constants.EMPTY_STRING);
        characterNameField.setText(Constants.EMPTY_STRING);
        classBox.getSelectionModel().clearSelection();
        characterRaceBox.getSelectionModel().clearSelection();
    }

    public void handleBackBtn_Click(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        showScene(node, Constants.HOME_FXML_PATH, Constants.HOME_SCENE_HEADER, getUser());
    }

    private void showWindow(boolean showCharacterList, boolean showAddCharacterPanel) {
        scrollPane.setVisible(showCharacterList);
        addCharacterBox.setVisible(showAddCharacterPanel);
    }

    private boolean findCharacter(String characterNameInput) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String name = getCharacterService().checkCharacterName(characterNameInput);
        stopwatch.stop();

        stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("Execute time: " + stopwatch);

        return name != null && !name.isEmpty();
    }

    private Button createCharacterBtn(String imgPath, double imgWidth, double imgHeight) {
        Button characterBtn = new Button();
        characterBtn.setMaxWidth(400.0d);
        characterBtn.setMaxHeight(100.0d);
        characterBtn.setMinWidth(400.0d);
        characterBtn.setMinHeight(100.0d);
        characterBtn.setTextAlignment(TextAlignment.LEFT);

        ImageView avatarImgBtnLayout = createImageBtnLayout(imgPath, imgWidth, imgHeight);
        characterBtn.setGraphic(avatarImgBtnLayout);

        return characterBtn;
    }
}