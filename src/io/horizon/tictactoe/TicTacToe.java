package io.horizon.tictactoe;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import io.horizon.tictactoe.view.TicTacToeBoard;
import io.horizon.tictactoe.view.TicTacToeListener;

public class TicTacToe extends AndroidNonvisibleComponent {
    private final Context context;
    private TicTacToeBoard board;
    private int XColor = COLOR_RED;
    private int OColor = COLOR_BLUE;
    private int boardColor = COLOR_BLACK;
    private int winningLineColor = COLOR_GREEN;

    public TicTacToe(ComponentContainer container) {
        super(container.$form());
        context = container.$context();
    }

    // Initialize View in component
    @SimpleFunction(description = "use this block to initialize tic tac toe view to a AndroidViewComponent")
    public void InitializeView(AndroidViewComponent component) {
        board = new TicTacToeBoard(context);
        board.setWinningLineColor(winningLineColor);
        board.setBoardColor(boardColor);
        board.setXColor(XColor);
        board.setOColor(OColor);
        board.setTicTacToeListener(new TicTacToeListener() {
            @Override
            public void onGameDraw() {
                OnGameDraw();
            }

            @Override
            public void onOPlayerTurn() {
                OnOTurn();
            }

            @Override
            public void onXPlayerTurn() {
                OnXTurn();
            }

            @Override
            public void gameWinner(int winner) {
                OnWinnerFound(winner);
            }
        });
        View v = component.getView();
        ViewGroup vg = (ViewGroup) v;
        vg.addView(board, -1, -1);
    }


    // Use all the functions
    @SimpleFunction(description = "Use this block to reset the game")
    public void ResetGame() {
        board.resetGame();
    }

    @SimpleEvent(description = "Event triggered after the game is draw")
    public void OnGameDraw() {
        EventDispatcher.dispatchEvent(this, "OnGameDraw");
    }

    @SimpleEvent(description = "Event triggered When its X's turn")
    public void OnXTurn() {
        EventDispatcher.dispatchEvent(this, "OnXTurn");
    }

    @SimpleEvent(description = "Event triggered when its O's turn")
    public void OnOTurn() {
        EventDispatcher.dispatchEvent(this, "OnOTurn");
    }

    @SimpleEvent(description = "If the winner is X then 0 will be returned and if the winner is O then 1 will be returned")
    public void OnWinnerFound(int winner) {
        EventDispatcher.dispatchEvent(this, "OnWinnerFound", winner);
    }

    // Edit X Color
    @DesignerProperty(
            editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
            defaultValue = "&HFFFF0000"
    )
    @SimpleProperty(userVisible = false)
    public void XColor(int xColor) {
        this.XColor = xColor;
    }

    @SimpleProperty(userVisible = false)
    public int XColor() {
        return this.XColor;
    }


    // Edit O Color
    @DesignerProperty(
            editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
            defaultValue = "&HFF0000FF"
    )
    @SimpleProperty(userVisible = false)
    public void OColor(int oColor) {
        this.OColor = oColor;
    }

    @SimpleProperty(userVisible = false)
    public int OColor() {
        return this.OColor;
    }


    // Edit Board Color
    @DesignerProperty(
            editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
            defaultValue = "&HFF000000"
    )
    @SimpleProperty(userVisible = false)
    public void BoardColor(int boardColor) {
        this.boardColor = boardColor;
    }

    @SimpleProperty(userVisible = false)
    public int BoardColor() {
        return this.boardColor;
    }


    // Edit Winning Line Color
    @DesignerProperty(
            editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
            defaultValue = "&HFF00FF00"
    )
    @SimpleProperty(userVisible = false)
    public void WinningLineColor(int color) {
        this.winningLineColor = color;
    }

    @SimpleProperty(userVisible = false)
    public int WinningLineColor() {
        return this.winningLineColor;
    }

}
