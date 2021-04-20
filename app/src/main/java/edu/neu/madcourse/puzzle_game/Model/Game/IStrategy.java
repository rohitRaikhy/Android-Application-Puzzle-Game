package edu.neu.madcourse.puzzle_game.Model.Game;

import java.io.Serializable;

public interface IStrategy extends Serializable {
    public boolean canMove(SquareBoard board, Cell from, Cell to );
}
