package edu.neu.madcourse.puzzle_game.Model.Game;

public class OrthogonalStrategy implements IStrategy {
    @Override
    public boolean canMove(SquareBoard board, Cell from, Cell to) {
        if (Math.abs(to.getX() - from.getX())>1){
            return false;
        }
        if (Math.abs(to.getY() - from.getY())>1){
            return false;
        }
        if (board.isOutOfBound(from)) {
            return false;
        }
        if (board.isOutOfBound(to)){
            return false;
        }
        return true;
    }
}
