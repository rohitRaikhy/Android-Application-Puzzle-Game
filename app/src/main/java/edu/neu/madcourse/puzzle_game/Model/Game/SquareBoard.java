package edu.neu.madcourse.puzzle_game.Model.Game;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.Serializable;

public class SquareBoard implements Serializable {
    private Cell[][] squareBoard;
    private int sideLen;


    public SquareBoard(int sideLen) {
        this.sideLen = sideLen;
        squareBoard = new Cell[sideLen][sideLen];

        for (int y = 0; y < this.sideLen; y++) {
            for (int x = 0; x < this.sideLen; x++) {
                CellType cellType = CellType.Empty;
                Cell cell = new Cell(x, y, cellType);
                squareBoard[x][y] = cell;
            }
        }
    }

    public int getSideDim() {
        return sideLen;
    }

    public void updateCell(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        this.squareBoard[x][y].cellType = cell.cellType;
    }

    public Cell[][] getMap() {
        return this.squareBoard;
    }

    public void bindBoardToTable(Activity activity, TableLayout tableLayout ) {
        tableLayout.removeAllViews();
        for (int y = 0; y < this.sideLen; y++) {
            tableLayout.setGravity(Gravity.CENTER);
            TableRow tableRow = new TableRow(activity);
            for (int x = 0; x < this.sideLen; x++) {
                ImageView img = new ImageView(activity);
                Drawable drawable = this.squareBoard[x][y].getDrawable(activity,img);
                img.setImageDrawable(drawable);
                tableRow.setGravity(Gravity.CENTER);
                tableRow.addView(img);
            }
            tableLayout.addView(tableRow);
        }
    }


    public boolean isOutOfBound(Cell cell) {
        if (cell.getX() >= this.sideLen || cell.getX() < 0) {
            return true;
        }
        if (cell.getY() >= this.sideLen || cell.getY() < 0) {
            return true;
        }
        return false;
    }


}
