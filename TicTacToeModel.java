package Peuffier.Dokumacioglu.morpion;


import javafx.beans.binding.*;
import javafx.beans.property.*;

public class TicTacToeModel {
    /**
     * Taille du plateau de jeu (pour être extensible).
     */
    private final static  int BOARD_WIDTH = 3;
    private final static  int BOARD_HEIGHT = 3;


    /**
     * Nombre de pièces alignées pour gagner(idem).
     */
    private final static  int WINNING_COUNT = 3;

    /**
     * Joueur courant.
     */
    private final ObjectProperty<Owner> turn = new SimpleObjectProperty<>(Owner.FIRST);

    /**
     * Vainqueur du jeu, NONE si pas de vainqueur.
     */
    private final ObjectProperty<Owner> winner = new SimpleObjectProperty<>(Owner.NONE);

    /**
     * Plateau de jeu.
     */
    private final SimpleObjectProperty[][] board;

    /**
     * Position gagnantes.
     */
    private final BooleanProperty[][] winningBoard;

    /**
     * Constructeur Privé.
     */
    private TicTacToeModel() {
        board = new SimpleObjectProperty[BOARD_WIDTH][BOARD_HEIGHT];
        winningBoard = new SimpleBooleanProperty[BOARD_WIDTH][BOARD_HEIGHT];
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                board[i][j] = new SimpleObjectProperty<>(Owner.NONE);
                winningBoard[i][j] = new SimpleBooleanProperty(false);
            }
        }
    }

    /**
     * @return la seule instance possible du jeu.
     */
    public static TicTacToeModel getInstance() {
        return TicTacToeModelHolder.INSTANCE;
    }

    /**
     * Classe interne selon le pattern Singleton.
     */
    private static class TicTacToeModelHolder {
        private static final TicTacToeModel INSTANCE = new TicTacToeModel();
    }

    public void restart() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                board[i][j].set(Owner.NONE);
                winningBoard[i][j].set(false);
            }
        }
        winner.set(Owner.NONE);
        turn.set(Owner.FIRST);
    }

    public final ObjectProperty<Owner> turnProperty() {
        return turn;
    }

    public final ObjectProperty<Owner> getSquare(int row, int column) {
        return board[row][column];
    }

    public final BooleanProperty getWinningSquare(int row, int column) {
        return winningBoard[row][column];
    }

    /**
     * Cette fonction ne doit donner le bon résultat que si le jeu
     * est terminé. L'affichage peut être caché avant la fin du jeu
     *
     * @return résultat du jeu sous forme de texte.
     */
    public final StringExpression getEndOfGameMessage() {
        return Bindings.when(winner.isEqualTo(Owner.NONE))
                .then("Match nul")
                .otherwise(Bindings.concat("Gagnant: ", winner.asString()));
    }

    public void setWinner(Owner winner) {
       this.winner.set(winner);
    }

    public boolean validSquare(int row, int column) {
        return board[row][column].get() == Owner.NONE && !gameOver().get();
    }

    public void nextPlayer() {
        turn.set(turn.get().opposite());
    }

    /**
     * Jouer dans la case (row, column) quand c'est possible.
     */
    public void play(int row, int column) {
        if (validSquare(row, column)) {
            board[row][column].set(turn.get());
            nextPlayer();
        }
    }

    /**
     * @return true s'il est possible de jouer dans la case
     * c'est à dire la case est libre et le jeu n'est pas terminé.
     */
    public BooleanBinding legalMove(int row, int column) {
        return board[row][column].isEqualTo(Owner.NONE).and(gameOver().not());
    }

    public NumberExpression getScore(Owner owner){
        int score = 0;
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j].get() == owner) {
                    score++;
                }
            }
        }
        return new SimpleIntegerProperty(score);
    }

    /**
     * return true si le jeu est terminé
     * (soit un joueur a gagné, soit il n'y a plus de case libre).
     */
    public BooleanBinding gameOver() {
        return winner.isNotEqualTo(Owner.NONE).or(boardFull()).or(hasHorizontalWin()).or(hasVerticalWin()).or(hasDiagonalWin());
    }

    private BooleanBinding hasHorizontalWin() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            BooleanBinding win = board[i][0].isEqualTo(board[i][1].get())
                    .and(board[i][1].isEqualTo(board[i][2].get()))
                    .and(board[i][0].isNotEqualTo(Owner.NONE));
            if (win.get()) {
                winningBoard[i][0].set(true);
                winningBoard[i][1].set(true);
                winningBoard[i][2].set(true);
                return win;
            }
        }
        return new SimpleBooleanProperty(true).not();
    }

    private BooleanBinding hasVerticalWin() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            BooleanBinding win = board[0][i].isEqualTo(board[1][i].get())
                    .and(board[1][i].isEqualTo(board[2][i].get()))
                    .and(board[0][i].isNotEqualTo(Owner.NONE));
            if (win.get()) {
                winningBoard[0][i].set(true);
                winningBoard[1][i].set(true);
                winningBoard[2][i].set(true);
                return win;
            }
        }
        return new SimpleBooleanProperty(true).not();
    }

    private BooleanBinding hasDiagonalWin() {
        BooleanBinding win = board[0][0].isEqualTo(board[1][1].get())
                .and(board[1][1].isEqualTo(board[2][2].get()))
                .and(board[0][0].isNotEqualTo(Owner.NONE));
        if (win.get()) {
            winningBoard[0][0].set(true);
            winningBoard[1][1].set(true);
            winningBoard[2][2].set(true);
            return win;
        }
        win = board[0][2].isEqualTo(board[1][1].get())
                .and(board[1][1].isEqualTo(board[2][0].get()))
                .and(board[0][2].isNotEqualTo(Owner.NONE));
        if (win.get()) {
            winningBoard[0][2].set(true);
            winningBoard[1][1].set(true);
            winningBoard[2][0].set(true);
            return win;
        }
        return new SimpleBooleanProperty(true).not();
    }

    public BooleanBinding boardFull() {
        BooleanBinding full = board[0][0].isNotEqualTo(Owner.NONE);
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                full = full.and(board[i][j].isNotEqualTo(Owner.NONE));
            }
        }
        return full;
    }


}

