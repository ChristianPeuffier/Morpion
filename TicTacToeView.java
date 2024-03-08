package Peuffier.Dokumacioglu.morpion;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.geometry.Insets;

public class TicTacToeView {
    public final BorderPane root;
    public GridPane grid;
    public Button restartButton;
    public Label resultat;
    public Label nbrCasesJ1;
    public Label nbrCasesJ2;
    public Label nbrCasesLibres;

    public TicTacToeView() {
        // Initialisation des éléments graphiques
        resultat = new Label("Partie en cours ...");
        nbrCasesJ1 = new Label();
        nbrCasesJ2 = new Label();
        nbrCasesLibres = new Label();

        grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        restartButton = new Button("Restart");

        BorderPane gamePane = new BorderPane();
        BorderPane centerPane = new BorderPane();
        BorderPane bottomPane = new BorderPane();

        nbrCasesLibres.setText("Cases libres : " + TicTacToeModel.getInstance().getScore(Owner.NONE).intValue());
        nbrCasesJ1.setText("Joueur 1 : " + TicTacToeModel.getInstance().getScore(Owner.FIRST).intValue());
        nbrCasesJ2.setText("Joueur 2 : " + TicTacToeModel.getInstance().getScore(Owner.SECOND).intValue());

        nbrCasesJ1.setStyle("-fx-background-color: cyan");
        nbrCasesJ2.setStyle("-fx-background-color: red");

        bottomPane.setLeft(nbrCasesJ1);
        bottomPane.setCenter(nbrCasesLibres);
        bottomPane.setRight(nbrCasesJ2);

        gamePane.setTop(grid);

        centerPane.setLeft(restartButton);
        centerPane.setCenter(resultat);
        BorderPane.setAlignment(resultat, Pos.CENTER);

        gamePane.setCenter(centerPane);
        gamePane.setBottom(bottomPane);
        gamePane.setPadding(new Insets(10));

        root = new BorderPane();
        root.setCenter(gamePane);
    }

    public Scene getScene() {
        return new Scene(root);
    }

    public ButtonBase getRestartButton() {
        return restartButton;
    }

    public TicTacToeSquare getSquare(int i, int j) {
        return (TicTacToeSquare) grid.getChildren().get(i * 3 + j);
    }
}
