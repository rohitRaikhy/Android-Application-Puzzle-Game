package edu.neu.madcourse.puzzle_game.Model.Game;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import java.io.Serializable;

import edu.neu.madcourse.puzzle_game.R;

public class Cell implements Serializable {
    private int x;
    private int y;
    public CellType cellType;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.cellType = cellType;
    }

    public Cell(int x, int y, CellType cellType) {
        this.x = x;
        this.y = y;
        this.cellType = cellType;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellType geType() {
        return this.cellType;
    }

    public Drawable getDrawable(Activity activity, ImageView img) {
        if (this.cellType == CellType.PLAYER_AVATAR) {
            return ContextCompat.getDrawable(activity, R.drawable.player_avatar);
        } else if (this.cellType == CellType.ENEMY_SORCERER_AVATAR) {
            return ContextCompat.getDrawable(activity, R.drawable.enemy_sorcerer);
        } else if (this.cellType == CellType.TREASURE) {
            return ContextCompat.getDrawable(activity, R.drawable.treasure);
        } else if (this.cellType == CellType.TRAP) {
            return ContextCompat.getDrawable(activity, R.drawable.bomb);
        }
        return ContextCompat.getDrawable(activity, R.drawable.cell);

    }

}
