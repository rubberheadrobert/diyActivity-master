package sample.Controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.EnterWordsModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnterWordsController {


    @FXML
    private StackPane stackPane;
    @FXML
    private Button backButton;
    @FXML
    private CheckBox saveCheckBox;
    @FXML
    private Button nextButton;
    @FXML
    private Button enterWordButton;
    @FXML
    private Button nextPlayerButton;

    @FXML
    private TextField wordsTextField;

    @FXML
    private Label enterWordsLabel;
    @FXML
    private Label yourWordsDescriptionLabel;
    @FXML
    private Label allWordsDescriptionLabel;
    @FXML
    private Label yourWordsLabel;
    @FXML
    private Label allWordsLabel;

    private ObjectProperty<Font> titleFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
    private ObjectProperty<Font> gridPaneFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

    private static List<String> words;

    public EnterWordsModel enterWordsModel = new EnterWordsModel();


    public void initialize(){
        if(enterWordsModel.isDbConnected()){
            System.out.println("Connected to DB");
        } else{
            System.out.println("Not connected to DB");
        }
        words = new ArrayList<>();
        yourWordsLabel.setText("0");
        allWordsLabel.setText("0");

        bindButtonSizes();
        bindFontSizes();

        stackPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
                titleFontTracking.set(Font.font(newHeight.doubleValue()/22));
                gridPaneFontTracking.set(Font.font(newHeight.doubleValue()/40));
            }
        });
        wordsTextField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                addWordToList();
            }
        });
    }

    private void bindFontSizes() {
        backButton.prefHeightProperty().bind(stackPane.heightProperty());
        backButton.prefWidthProperty().bind(stackPane.widthProperty());
        nextButton.prefHeightProperty().bind(stackPane.heightProperty());
        nextButton.prefWidthProperty().bind(stackPane.widthProperty());
        enterWordButton.prefHeightProperty().bind(stackPane.heightProperty());
        enterWordButton.prefWidthProperty().bind(stackPane.widthProperty());
        nextPlayerButton.prefHeightProperty().bind(stackPane.heightProperty());
        nextPlayerButton.prefWidthProperty().bind(stackPane.widthProperty());

        yourWordsLabel.fontProperty().bind(gridPaneFontTracking);
        allWordsLabel.fontProperty().bind(gridPaneFontTracking);
        yourWordsDescriptionLabel.fontProperty().bind(gridPaneFontTracking);
        allWordsDescriptionLabel.fontProperty().bind(gridPaneFontTracking);
        wordsTextField.fontProperty().bind(gridPaneFontTracking);
    }

    private void bindButtonSizes() {
        enterWordButton.fontProperty().bind((titleFontTracking));
        nextPlayerButton.fontProperty().bind(titleFontTracking);
        enterWordsLabel.fontProperty().bind(titleFontTracking);
        backButton.fontProperty().bind(gridPaneFontTracking);
        nextButton.fontProperty().bind(gridPaneFontTracking);
    }

    public void moveToNextPlayer() {

        if (nextButton.isDisabled()){
            nextButton.setDisable(false);
        }
        yourWordsLabel.setText("0");
    }

    public void addWordToList() {
        if (nextPlayerButton.isDisabled()) {
            nextPlayerButton.setDisable(false);
        }
        String word = wordsTextField.getText();
        if (!word.trim().isEmpty()) {
            words.add(word);

            if(saveCheckBox.isSelected()){
                try{
                    enterWordsModel.addWord(word);
                } catch (SQLException e){
                    System.out.println("Couldn't add word");
                }
            }

            Integer numberOfYourWords = 1 + Integer.parseInt(yourWordsLabel.getText());
            yourWordsLabel.setText(numberOfYourWords.toString());

            Integer numberOfAllWords = words.size();
            allWordsLabel.setText(numberOfAllWords.toString());

            wordsTextField.clear();
        } else {
            String title = "No word entered";
            String description = "Please enter a word into the textfield.";
            showErrorAlert(title, description);
        }
    }



    public void openHomeScene() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("..\\Views\\home.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Paraszt Activity");
            stage.setScene(new Scene(root,300,500));
            stage.show();
        } catch (IOException e) {
            System.out.println("Couldn't open next window");
        }
    }

    public void openTeamsScene() {
        for (String word : words) {
            System.out.println(word);
        }
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("..\\Views\\teams.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Enter Teams And Players");
            stage.setScene(new Scene(root,300,500));
            stage.show();
        } catch (IOException e) {
            System.out.println("Couldn't open next window");
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String title, String description){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(description);
        alert.showAndWait();
        wordsTextField.requestFocus();
    }

    public static List<String> getWords(){
        return words;
    }
}
