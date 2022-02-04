package io.horizon.tictactoe.view;

public interface TicTacToeListener {
    void onGameDraw();

    void onOPlayerTurn();

    void onXPlayerTurn();

    void gameWinner(int winner);
}