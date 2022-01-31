package sample.Controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.datamodel.Game;
import sample.datamodel.Player;
import sample.datamodel.Team;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamsController {

    @FXML
    private Label enterTeamNameLabel;
    @FXML
    private Label enterPlayerNameLabel;

    @FXML
    private Button backButton;
    @FXML
    private Button playButton;
    @FXML
    private Button addTeamButton;
    @FXML
    private Button addPlayerButton;
    @FXML
    private Button newTeamButton;

    @FXML
    private TextField enterTeamTextField;
    @FXML
    private TextField enterPlayerTextField;

    @FXML
    private StackPane stackPane;

    private List<Team> teams = new ArrayList<>();;
    private Team currentTeam;
    private static Game game;

    private int playerPlace;
    private int teamPlace;

    private ObjectProperty<Font> smallFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
    private ObjectProperty<Font> bigFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

    public void initialize(){
        playerPlace = -1;
        teamPlace = -1;
        bindButtonSizes();
        bindFontSizes();

        stackPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
                bigFontTracking.set(Font.font(newHeight.doubleValue()/23));
                smallFontTracking.set(Font.font(newHeight.doubleValue()/40));
            }
        });
        eventHandlers();
    }

    private void eventHandlers() {
        enterPlayerTextField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                addPlayer();
            }
        });
        enterTeamTextField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                addTeam();
            }
        });
    }

    private void bindButtonSizes(){
        backButton.prefHeightProperty().bind(stackPane.heightProperty());
        backButton.prefWidthProperty().bind(stackPane.widthProperty());
        playButton.prefHeightProperty().bind(stackPane.heightProperty());
        playButton.prefWidthProperty().bind(stackPane.widthProperty());
        addTeamButton.prefHeightProperty().bind(stackPane.heightProperty());
        addTeamButton.prefWidthProperty().bind(stackPane.widthProperty());
        addPlayerButton.prefHeightProperty().bind(stackPane.heightProperty());
        addPlayerButton.prefWidthProperty().bind(stackPane.widthProperty());
        newTeamButton.prefHeightProperty().bind(stackPane.heightProperty());
        newTeamButton.prefWidthProperty().bind(stackPane.widthProperty());
    }

    private void bindFontSizes(){
        enterTeamNameLabel.fontProperty().bind(bigFontTracking);
        enterPlayerNameLabel.fontProperty().bind(bigFontTracking);
        addTeamButton.fontProperty().bind(smallFontTracking);
        addPlayerButton.fontProperty().bind(smallFontTracking);
        newTeamButton.fontProperty().bind(smallFontTracking);

        enterTeamTextField.fontProperty().bind(smallFontTracking);
        enterPlayerTextField.fontProperty().bind(smallFontTracking);

        backButton.fontProperty().bind(smallFontTracking);
        playButton.fontProperty().bind(smallFontTracking);
    }

    public void openEnterWordsScene() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("..\\Views\\enterWords.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Enter Words");
            stage.setScene(new Scene(root,300,500));
            stage.show();
        } catch (IOException e) {
            System.out.println("Couldn't open next window");
        }
    }

    public void openRoundScene() {
        createGame();
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("..\\Views\\round.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Game");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Couldn't open next window");
            e.printStackTrace();
        }
    }

    public void addTeam(){
        String teamName = enterTeamTextField.getText();
        if(teamName.trim().isEmpty()){
            String title = "No team entered";
            String description = "Please enter a team into the textfield.";
            showErrorAlert(enterTeamTextField, title, description);
        } else {
            currentTeam = new Team(enterTeamTextField.getText(), teamPlace + 1);
            teams.add(currentTeam);

            addTeamButton.setDisable(true);
            enterTeamTextField.clear();
            enterTeamTextField.setDisable(true);

            enterPlayerTextField.setDisable(false);
            addPlayerButton.setDisable(false);
        }
    }

    public void addPlayer(){
        String playerName = enterPlayerTextField.getText();
        if(playerName.trim().isEmpty()){
            String title = "No player entered";
            String description = "Please enter a player into the textfield.";
            showErrorAlert(enterPlayerTextField, title, description);
        } else{
            if(teams.size() == 2){
                playButton.setDisable(false);
            }
            playerPlace++;
            currentTeam.addPlayer(new Player(enterPlayerTextField.getText(), playerPlace));
            enterPlayerTextField.clear();
            if(newTeamButton.isDisabled()) {
                newTeamButton.setDisable(false);
            }
        }
    }

    public void newTeam(){
        playerPlace = -1;
        newTeamButton.setDisable(true);
        addPlayerButton.setDisable(true);
        enterPlayerTextField.setDisable(true);
        addTeamButton.setDisable(false);
        enterTeamTextField.setDisable(false);
        enterTeamTextField.requestFocus();
        System.out.println(currentTeam.toString());
    }

    private void createGame(){
        String pName = enterPlayerTextField.getText();
        if(pName.trim().isEmpty()){
            currentTeam.addPlayer(new Player(pName, playerPlace));
        }
        game = new Game(teams, EnterWordsController.getWords(),OptionsController.getSecondsSliderValue());
    }

    private void showErrorAlert(TextField textField, String title, String description){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(description);
        alert.showAndWait();
        textField.requestFocus();
    }

    public static Game getGame(){
        return game;
    }

}
