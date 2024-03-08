package Peuffier.Dokumacioglu.morpion;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class TicTacToeGame extends Application {
    static Label resultat = new Label();
    static Label nbrCasesJ1 = new Label();
    static Label nbrCasesJ2 = new Label();
    static Label nbrCasesLibres = new Label();
    static TicTacToeView view;
    static TicTacToeModel model;

    static Button restartButton = new Button("Restart");
    @Override
    public void start(Stage primaryStage) {
        model = TicTacToeModel.getInstance();
        view = new TicTacToeView();
        TicTacToeController controller = new TicTacToeController(model, view);
        primaryStage.setScene(view.getScene());
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
