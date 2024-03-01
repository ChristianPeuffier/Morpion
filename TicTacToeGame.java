package Peuffier.Dokumacioglu.morpion;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TicTacToeGame extends Application {
    private Label resultLabel = new Label();
    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TicTacToeSquare square = new TicTacToeSquare(i, j);
                grid.add(square, j, i);

            }
        }
        Button restartButton = new Button("Restart");
        restartButton.setOnAction(event -> {
            TicTacToeModel.getInstance().restart();
        });
        resultLabel.setText(TicTacToeModel.getInstance().getEndOfGameMessage().get());
        BorderPane gamePane = new BorderPane();
        gamePane.setCenter(grid);
        gamePane.setBottom(restartButton);
        gamePane.setTop(resultLabel);
        gamePane.setPadding(new Insets(10));
        // Ajouter le conteneur au BorderPane principal
        root.setCenter(gamePane);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
