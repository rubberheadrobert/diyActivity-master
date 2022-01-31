package sample.Controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class RulesController {
    @FXML
    private Label rulesLabel;
    @FXML
    private Button backButton;
    @FXML
    private StackPane stackPane;

    private ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

    public void initialize(){
        String rulesText =  "Upon pressing the play button,\n" +
                            "each player must enter the same\n" +
                            "amount of words. After entering\n" +
                            "the desired words, each team\n" +
                            "must enter their team name, and\n" +
                            "the players that are part of\n" +
                            "the team. After pressing the\n" +
                            "start button, the player's turn\n" +
                            "starts. A turn is 30 seconds\n" +
                            "long by default(can be changed\n" +
                            "in the options menu). The\n" +
                            "player's teammates must try \n" +
                            "guess the word or phrase that\n" +
                            "the player is trying to describe.\n" +
                            "Each game is 3 rounds.\n" +
                            "Round 1: Players must describe\n" +
                            "the given word without saying\n" +
                            "it in any form.\n" +
                            "Round 2: Players can only say\n" +
                            "one word to help their\n" +
                            "teammates.\n" +
                            "Round 3: Players can only make\n" +
                            "hand gestures and sounds to\n" +
                            "help their teammates.";

        rulesLabel.setText(rulesText);

        backButton.prefHeightProperty().bind(stackPane.heightProperty());
        backButton.prefWidthProperty().bind(stackPane.widthProperty());
        rulesLabel.fontProperty().bind(fontTracking);
        backButton.fontProperty().bind(fontTracking);

        stackPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
                fontTracking.set(Font.font(newHeight.doubleValue()/44));

            }
        });
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
}
