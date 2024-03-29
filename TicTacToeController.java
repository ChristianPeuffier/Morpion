package Peuffier.Dokumacioglu.morpion;

public class TicTacToeController {
    TicTacToeModel model;
    TicTacToeView view;
    public TicTacToeController(TicTacToeModel model, TicTacToeView view) {
        this.model = model;
        this.view = view;
        restartListener();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TicTacToeSquare square = new TicTacToeSquare(i, j);
                view.grid.add(square, j, i);
                squareListener(square, i, j);
            }
        }
    }

    public void squareListener(TicTacToeSquare square, int row, int column){
        square.setOnMouseClicked(event -> {
            if(square.ownerProperty().get()==Owner.NONE){
                model.play(row, column);
                view.nbrCasesJ1.setText(model.getScore(Owner.FIRST).intValue()+" cases pour X");
                view.nbrCasesJ2.setText(model.getScore(Owner.SECOND).intValue()+" cases pour O");
                view.nbrCasesLibres.setText("Cases libres : " + model.getScore(Owner.NONE).intValue());
                if(model.turnProperty().get()==Owner.FIRST){
                    view.nbrCasesJ1.setStyle("-fx-background-color: cyan");
                    view.nbrCasesJ2.setStyle("-fx-background-color: red");
                }
                else if(model.turnProperty().get()==Owner.SECOND){
                    view.nbrCasesJ1.setStyle("-fx-background-color: red");
                    view.nbrCasesJ2.setStyle("-fx-background-color: cyan");
                }
            }
            if(model.gameOver().get()){
                square.setStyle("-fx-background-color: cyan;");
                if (model.boardFull().get() && !square.winnerProperty().get()){
                    view.resultat.setText("Match nul !");
                }
                else if (square.winnerProperty().get()){
                    if (square.ownerProperty().get() == Owner.FIRST){
                        model.setWinner(Owner.FIRST);
                        view.resultat.setText(model.getEndOfGameMessage().get());
                    }
                    else if (square.ownerProperty().get() == Owner.SECOND){
                        view.resultat.setText("Joueur 2 gagne !");
                    }
                }
                view.nbrCasesJ1.setStyle("-fx-background-color: red");
                view.nbrCasesJ2.setStyle("-fx-background-color: red");
            }
        });

        square.setOnMouseEntered(event -> {
            if(!model.gameOver().get()) {
                if (model.legalMove(row, column).get()) {
                    square.setStyle("-fx-background-color: green;");
                }
                else {
                    square.setStyle("-fx-background-color: red;");
                }
            }
            else {
                square.setStyle("-fx-background-color: red;");
            }
        });

        square.setOnMouseExited(event -> {
            if (square.winnerProperty().get()) {
                square.setStyle("-fx-background-color: cyan;");
            } else {
                square.setStyle("");
            }
        });
    }
    public void restartListener() {
        view.getRestartButton().setOnAction(event -> {
            model.restart();
            view.resultat.setText("Partie en cours ...");
            view.nbrCasesJ1.setText("Joueur 1 : " + model.getScore(Owner.FIRST).intValue());
            view.nbrCasesJ2.setText("Joueur 2 : " + model.getScore(Owner.SECOND).intValue());
            view.nbrCasesLibres.setText("Cases libres : " + model.getScore(Owner.NONE).intValue());
        });
    }

}
