package sample.Controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import sample.datamodel.Game;
import sample.datamodel.Player;
import sample.datamodel.Team;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;
import java.util.*;

public class RoundController implements Initializable{

    @FXML
    private Button pausePlayButton;
    @FXML
    private Button skipButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button startButton;


    @FXML
    private Label timerLabel;
    @FXML
    private Label wordLabel;
    @FXML
    private Label wordsLeftLabel;
    @FXML
    private Label wordsLeftDescriptionLabel;
    @FXML
    private Label currentTeamLabel;
    @FXML
    private Label currentPlayerLabel;
    @FXML
    private Label roundLabel;

    @FXML
    private StackPane stackPane;

    private Game game = TeamsController.getGame();
    private List<Team> teams = game.getTeams();
    private List<String> words = game.getWords();
    private List<String> usedWords = new ArrayList<>();
    private List<String> nextWords = new ArrayList<>();


    private int startTime = game.getRoundLength();
    private Timeline timeline;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(startTime);
    private int seconds;

    private String currentWord = "";
    private Player currentPlayer;
    private Team currentTeam;

    private int firstPlayerId = 0;
    private int firstTeamId = 0;
    private int randomNumber;
    private int currentRound = 1;

    private ObjectProperty<Font> smallFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
    private ObjectProperty<Font> bigFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

    public void initialize(URL url, ResourceBundle rb) {
        bindButtonSizes();
        bindFontSizes();
        stackPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
                bigFontTracking.set(Font.font(newHeight.doubleValue()/22));
                smallFontTracking.set(Font.font(newHeight.doubleValue()/40));
            }
        });
        currentTeam = teams.get(firstTeamId);
        currentTeamLabel.setText(currentTeam.getName());
        currentPlayer = currentTeam.getPlayer(firstPlayerId);
        currentPlayerLabel.setText(currentPlayer.getName());
        roundLabel.setText("Round " + currentRound);
        wordsLeftLabel.setText(words.size() + "");
        timerLabel.textProperty().bind(timeSeconds.asString());

        timerLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(timerLabel.getText().equals("0")){
                    pausePlayButton.setDisable(true);
                    timeline.stop();
                    openAlerts();
                }
            }
        });
    }

    private void bindButtonSizes(){
        pausePlayButton.prefHeightProperty().bind(stackPane.heightProperty());
        pausePlayButton.prefWidthProperty().bind(stackPane.widthProperty());
        startButton.prefHeightProperty().bind(stackPane.heightProperty());
        startButton.prefWidthProperty().bind(stackPane.widthProperty());
        skipButton.prefHeightProperty().bind(stackPane.heightProperty());
        skipButton.prefWidthProperty().bind(stackPane.widthProperty());
        nextButton.prefHeightProperty().bind(stackPane.heightProperty());
        nextButton.prefWidthProperty().bind(stackPane.widthProperty());
    }

    private void bindFontSizes(){
        pausePlayButton.fontProperty().bind(smallFontTracking);
        currentPlayerLabel.fontProperty().bind(smallFontTracking);
        currentTeamLabel.fontProperty().bind(smallFontTracking);
        wordsLeftDescriptionLabel.fontProperty().bind(smallFontTracking);
        wordsLeftLabel.fontProperty().bind(smallFontTracking);
        roundLabel.fontProperty().bind(smallFontTracking);
        timerLabel.fontProperty().bind(bigFontTracking);
        wordLabel.fontProperty().bind(bigFontTracking);
        startButton.fontProperty().bind(bigFontTracking);
        skipButton.fontProperty().bind(bigFontTracking);
        nextButton.fontProperty().bind(bigFontTracking);
    }

    private void openAlerts(){
        nextButton.setDisable(true);
        skipButton.setDisable(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                playAlarmSound("src/sample/sounds/alarmAudio.wav");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if(nextWords.size() == 0){
                    alert.setTitle("Time up");
                    alert.setHeaderText(null);
                    alert.setContentText("Your time is up. You did not guess any words.");
                    alert.showAndWait();
                } else {
                    alert.setTitle("Time up");
                    alert.setHeaderText(null);
                    alert.setContentText("Your time is up. Now it's time to double check \n if you clicked next and skip correctly.");
                    alert.showAndWait();
                    String alertDescription = "Did you Guess the next word? ";
                    String alertTitle = "Skip or Next:  ";
                    System.out.println("Words size: " + words.size());
                    System.out.println("Next Words size: " + nextWords.size());
                    System.out.println("Used Words size: " + usedWords.size());
                    for (int i = 0; i < nextWords.size(); i++){
                        wordAlert(alertTitle, alertDescription, nextWords.get(i));
                        wordsLeftLabel.setText(words.size() + "");
                    }
                    nextWords.clear();
                }
                System.out.println("Words size: " + words.size());
                System.out.println("Next Words size: " + nextWords.size());
                System.out.println("Used Words size: " + usedWords.size());
                printCurrentTeamPlayerInfo(currentTeam,currentPlayer);
                nextTurn();
            }
        });

    }

    public void wordAlert(String title, String description, String word){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(description + word);
        Optional<ButtonType> choose = alert.showAndWait();
        if(choose.get() == ButtonType.OK){
            usedWords.add(word);
            currentPlayer.addPoint();
        } else {
            words.add(word);
        }
    }

    private void nextTurn(){
        if(currentRound == 3 && words.size() == 0){
            for(int i = teams.size(); i > 0; i--){
                String title = ordinal(i) + " place";
                String text = "Team name: " + teams.get(i-1).getName();
                text += "\nPoints: " + teams.get(i-1).getTeamPoints();
                informationAlert(title, text);
            }
            endGame();
        } else {
            if (words.size() == 0) {
                for (String w : usedWords) {
                    words.add(w);
                }
                wordLabel.setText("");
                wordLabel.setTextFill(Color.BLACK);
                wordsLeftLabel.setText(words.size() + "");
                nextWords.clear();
                usedWords.clear();
                currentRound++;
                roundLabel.setText("Round " + currentRound);
            }
            if(game.allTeamsHavePlayed()){
                for (Team t : teams) {
                    t.incrementPlayerPlace();
                }
                game.setTeamPlace(0);
                currentTeam = teams.get(firstTeamId);
                currentPlayer = currentTeam.getPlayer(currentTeam.getPlayerPlace());
            } else {
                game.incrementTeamPlace();
                currentTeam = teams.get(game.getTeamPlace());
                currentPlayer = currentTeam.getPlayer(currentTeam.getPlayerPlace());
            }
            currentTeamLabel.setText(currentTeam.getName());
            currentPlayerLabel.setText(currentPlayer.getName());
            startButton.setDisable(false);
        }

    }

    private void endGame(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thank you for playing!");
        alert.setHeaderText(null);
        alert.setContentText("Game Finished! The application will now close.");
        alert.showAndWait();
        Platform.exit();
    }

    private void informationAlert(String title, String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private void updateTime() {
        if(!isTimeUp()) {
            int seconds = timeSeconds.get();
            timeSeconds.set(seconds - 1);
        }
    }

    public void startTimer(ActionEvent event) {
        skipButton.setDisable(false);
        nextButton.setDisable(false);
        startButton.setDisable(true); // prevent starting multiple times
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> updateTime()));
        timeline.setCycleCount(Animation.INDEFINITE); // repeat over and over again
        timeSeconds.set(startTime);
        timeline.play();
        generateRandomWord();
        pausePlayButton.setDisable(false);
    }

    public boolean isTimeUp(){
        if(timeSeconds.get() <= 0){
            return true;
        }
        return false;
    }

    public void generateRandomWord() {
        if(words.size() > 0){
            Random rand = new Random();
            randomNumber = rand.nextInt(words.size());
            currentWord = words.get(randomNumber);
            wordLabel.setText(currentWord);
        } else {
            wordLabel.setText("NO WORDS LEFT");
            wordLabel.setTextFill(Color.RED);
            nextButton.setDisable(true);
            skipButton.setDisable(true);
        }
    }

    public void nextWord() {
        if(words.size() > 0){
            nextWords.add(currentWord);
            words.remove(randomNumber);
            wordsLeftLabel.setText(words.size() + "");
            generateRandomWord();
        } else {
            nextButton.setDisable(true);
            skipButton.setDisable(true);
        }
    }

    public void pausePlay() {
        if(pausePlayButton.getText().equals("PAUSE")){
            timeline.pause();
            pausePlayButton.setText("PLAY");
        } else {
            timeline.play();
            pausePlayButton.setText("PAUSE");
        }
    }

    private void printCurrentTeamPlayerInfo(Team team, Player player){
        System.out.println("Current team: " + team.getName() + ", Current player: " + player.getName());
        System.out.println("Team points: " + team.getTeamPoints() + " Player points: " + player.getPoints());
    }

    private String ordinal(int i) {
        String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];

        }
    }

    private void playAlarmSound(String filepath){
        try{
            File musicPath = new File(filepath);
            if(musicPath.exists()){
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } else{
                System.out.println("Can't find file");
            }
        } catch ( Exception e){
            e.printStackTrace();
        }
    }
}
