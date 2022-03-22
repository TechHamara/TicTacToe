package io.horizon.tictactoe.view;

public interface TicTacToeListener {
    void onGameDraw();

    void onOPlayerTurn();

    void onXPlayerTurn();

    void gameWinner(int winner);

    void onOPlaced(int row, int col);

    void onXPlaced(int row, int col);
}