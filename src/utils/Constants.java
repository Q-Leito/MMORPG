package utils;

public class Constants {

    public final static double APP_WIDTH = 960.0d;
    public final static double APP_HEIGHT = 600.0d;
    public final static String APP_NAME = "MMORPG";

    private final static String FXML_PATH = "/fxml";
    public final static String LOGIN_FXML_PATH = String.format("%s/login.fxml", FXML_PATH);
    public final static String PROFILE_FXML_PATH = String.format("%s/profile.fxml", FXML_PATH);
    public final static String CHARACTER_FXML_PATH = String.format("%s/character.fxml", FXML_PATH);
    public final static String HOME_FXML_PATH = String.format("%s/main.fxml", FXML_PATH);
    public final static String REGISTER_FXML_PATH = String.format("%s/register.fxml", FXML_PATH);

    private final static String IMAGE_PATH = "/images";
    public final static String BACK_IMAGE_PATH = String.format("%s/back_button.png", IMAGE_PATH);
    public final static String ADD_BUTTON_IMAGE_PATH = String.format("%s/add.png", IMAGE_PATH);

    public final static String IMAGE_AVATAR_PATH = String.format("%s/avatars", IMAGE_PATH);

    private final static String FONT_PATH = "/fonts";
    public final static String DEFAULT_TEXT_FONT_PATH = String.format("%s/Audiowide-Regular.ttf", FONT_PATH);

    public final static double DEFAULT_FONT_TEXT_SIZE = 12.0d;

    public final static String LOGIN_SCENE_HEADER = "Login";
    public final static String PROFILE_SCENE_HEADER = "Profile";
    public final static String CHARACTER_SCENE_HEADER = "Characters";
    public final static String HOME_SCENE_HEADER = "Home";
    public final static String REGISTRATION_SCENE_HEADER = "Registration";

    public final static double BACK_IMAGE_WIDTH = 25.0d;
    public final static double BACK_IMAGE_HEIGHT = 25.0d;
    public final static double ADD_IMAGE_WIDTH = 15.0d;
    public final static double ADD_IMAGE_HEIGHT = 15.0d;
    public final static double AVATAR_IMAGE_WIDTH = 75.0d;
    public final static double AVATAR_IMAGE_HEIGHT = 75.0d;

    public static final String EMPTY_STRING = "";
}