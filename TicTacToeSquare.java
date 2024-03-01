package Peuffier.Dokumacioglu.morpion;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class TicTacToeSquare extends TextField {
    private static final TicTacToeModel model = TicTacToeModel.getInstance();

    private final ObjectProperty<Owner> ownerProperty = new SimpleObjectProperty<>(Owner.NONE);
    private final BooleanProperty winnerProperty = new SimpleBooleanProperty(false);

    public ObjectProperty<Owner> ownerProperty() {
        return ownerProperty;
    }

    public BooleanProperty winnerProperty() {
        return winnerProperty;
    }

    public TicTacToeSquare(final int row, final int column) {
        ownerProperty.bind(model.getSquare(row, column));
        winnerProperty.bind(model.getWinningSquare(row, column));

        setPrefSize(200, 200);
        setEditable(false);

        ownerProperty.addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case FIRST:
                    setText("X");
                    break;
                case SECOND:
                    setText("O");
                    break;
                case NONE:
                    setText("");
                    break;
            }
            setEditable(newValue == Owner.NONE);
            setFont(Font.font(Math.min(getHeight(), getWidth()) * 0.4)); // Taille de la police adaptative
            setAlignment(Pos.CENTER);

        });


        winnerProperty.addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                setStyle("-fx-background-color: cyan;");
                setEditable(false);
            }
            else {
                setStyle("");
            }
        });

        setOnMouseClicked(event -> {
            if (ownerProperty.get() == Owner.NONE) {
                model.play(row, column);
            }
            if (model.gameOver().get()) {
                setStyle("-fx-background-color: cyan;");
            }
        });

        setOnMouseEntered(event -> {
            if(!model.gameOver().get()) {
                if (model.legalMove(row, column).get()) {
                    setStyle("-fx-background-color: green;");
                }
                else {
                    setStyle("-fx-background-color: red;");
                }
            }

        });

        setOnMouseExited(event -> {
            if (winnerProperty.get()) {
                setStyle("-fx-background-color: cyan;");
            } else {
                setStyle("");
            }
        });
    }
}
