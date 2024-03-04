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
    static Label label;
    @Override
    public void start(Stage primaryStage) {

        label = new Label();
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
            label.setText("Partie en cours ...");
                });
        BorderPane gamePane = new BorderPane();
        BorderPane bottomPane = new BorderPane();
        gamePane.setCenter(grid);
        bottomPane.setLeft(restartButton);
        label.setText("Partie en cours ...");
        bottomPane.setCenter(label);
        BorderPane.setAlignment(label, Pos.CENTER);
        gamePane.setBottom(bottomPane);
        gamePane.setPadding(new Insets(10));
        root.setCenter(gamePane);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);

    }



}
