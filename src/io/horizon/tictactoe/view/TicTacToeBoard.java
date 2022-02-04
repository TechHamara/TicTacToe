package io.horizon.tictactoe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class TicTacToeBoard extends View {
    private final GameLogic game = new GameLogic();
    private final Paint paint = new Paint();
    private int boardColor;
    private int XColor;
    private int OColor;
    private int winningLineColor;
    public boolean winningLine = false;
    private TicTacToeListener mlistener;
    private int cellSize;

    public TicTacToeBoard(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        int dimension = Math.min(getMeasuredHeight(), getMeasuredWidth());
        setMeasuredDimension(dimension, dimension);
        cellSize = dimension / 3;
        
        if (mlistener != null) {
            mlistener.onXPlayerTurn();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if (winningLine == true && game.checkDraw == false) {
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        
            if(mlistener != null){
                mlistener.gameWinner(game.getWinner());
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            int row = (int) Math.ceil(y / cellSize);
            int col = (int) Math.ceil(x / cellSize);
            if (!winningLine) {
                if (game.updateGameBoard(row, col)) {

                    if (game.winnerCheck()) {
                        winningLine = true;
                        invalidate();
                    } else if (game.gameDraw()) {
                        if (mlistener != null) {
                            mlistener.onGameDraw();
                        }
                        winningLine = true;
                        game.checkDraw = true;
                    }
                     if (!winningLine) {
                        if (game.getPlayer() % 2 == 0) {
                            game.setPlayer(game.getPlayer() - 1);
                            if (mlistener != null) {
                                mlistener.onXPlayerTurn();
                            }
                        } else {
                            game.setPlayer(game.getPlayer() + 1);
                            if (mlistener != null) {
                                mlistener.onOPlayerTurn();
                            }
                        }
                        invalidate(); }
                }
            }

            invalidate();

            return true;
        }

        return false;
    }

    private void drawGameBoard(Canvas canvas) {
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

        for (int c = 1; c < 3; c++) {
            canvas.drawLine(cellSize * c, 0, cellSize * c, canvas.getWidth(), paint);
        }
        for (int r = 1; r < 3; r++) {
            canvas.drawLine(0, cellSize * r, canvas.getWidth(), cellSize * r, paint);
        }
    }

    private void drawMarkers(Canvas canvas) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (game.getGameBoard()[r][c] != 0) {
                    if (game.getGameBoard()[r][c] == 1) {
                        drawX(canvas, r, c);
                    } else {
                        drawO(canvas, r, c);
                    }
                }
            }
        }
    }

    private void drawX(Canvas canvas, int row, int col) {
        paint.setColor(XColor);

        canvas.drawLine((float) ((col + 1) * cellSize - cellSize * 0.2),
                (float) (row * cellSize + cellSize * 0.2),
                (float) (col * cellSize + cellSize * 0.2),
                (float) ((row + 1) * cellSize - cellSize * 0.2),
                paint);

        canvas.drawLine((float) (col * cellSize + cellSize * 0.2),
                (float) (row * cellSize + cellSize * 0.2),
                (float) ((col + 1) * cellSize - cellSize * 0.2),
                (float) ((row + 1) * cellSize - cellSize * 0.2),
                paint);

    }

    private void drawO(Canvas canvas, int row, int col) {
        paint.setColor(OColor);

        canvas.drawOval((float) (col * cellSize + cellSize * 0.2),
                (float) (row * cellSize + cellSize * 0.2),
                (float) ((col * cellSize + cellSize) - cellSize * 0.2),
                (float) ((row * cellSize + cellSize) - cellSize * 0.2),
                paint);

    }

    private void drawHorizontalLine(Canvas canvas, int row, int col) {
        canvas.drawLine(col, row * cellSize + (float) cellSize / 2,
                cellSize * 3, row * cellSize + (float) cellSize / 2,
                paint);
    }

    private void drawVerticalLine(Canvas canvas, int row, int col) {
        canvas.drawLine(col * cellSize + (float) cellSize / 2, row,
                col * cellSize + (float) cellSize / 2, cellSize * 3,
                paint);
    }

    private void drawDiagonalLinePos(Canvas canvas) {
        canvas.drawLine(0, cellSize * 3,
                cellSize * 3, 0, paint);
    }

    private void drawDiagonalLineNeg(Canvas canvas) {
        canvas.drawLine(0, 0,
                cellSize * 3, cellSize * 3, paint);
    }

    private void drawWinningLine(Canvas canvas) {
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];
        if(!game.gameDraw()){

        switch (game.getWinType()[2]) {
            case 1:
                drawHorizontalLine(canvas, row, col);
                break;
            case 2:
                drawVerticalLine(canvas, row, col);
                break;
            case 3:
                drawDiagonalLineNeg(canvas);
                break;
            case 4:
                drawDiagonalLinePos(canvas);
                break;
        }
    }
    }

    public void setBoardColor(int color) {
        boardColor = color;
    }

    public void setXColor(int color) {
        XColor = color;
    }

    public void setOColor(int color) {
        OColor = color;
    }

    public void resetGame() {
        game.resetGame();
        winningLine = false;
        invalidate();
    }

    public void setTicTacToeListener(TicTacToeListener listener) {
        this.mlistener = listener;
    }

    public void setWinningLineColor(int winningLineColor) {
        this.winningLineColor = winningLineColor;
    }

    public boolean getWinningLine(){
        if(winningLine){
            return true;
        }
        else{
            return false;
        }
    }


}
