package edu.neu.madcourse.puzzle_game.Model.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class EnemyStrategy implements IStrategy {

    @Override
    public boolean canMove(SquareBoard board, Cell from, Cell to) {
        if (from.getX() == to.getX() && from.getY() == to.getY()){
            return false;
        }
        if (Math.abs(to.getX() - from.getX()) > 1) {
            return false;
        }
        if (Math.abs(to.getY() - from.getY()) > 1) {
            return false;
        }
        if (board.isOutOfBound(from)) {
            return false;
        }
        if (board.isOutOfBound(to)) {
            return false;
        }
        if (board.getMap()[to.getX()][to.getY()].geType() == CellType.TREASURE) {
            return false;
        }
        if (board.getMap()[to.getX()][to.getY()].geType() == CellType.TRAP) {
            return false;
        }
        if (board.getMap()[to.getX()][to.getY()].geType() == CellType.ENEMY_SORCERER_AVATAR) {
            return false;
        }
        return true;
    }

    public double pythagoreanDist(Cell a, Cell b) {
        int x = a.getX() - b.getX();
        int y = a.getY() - b.getY();
        double pythagorean;
        pythagorean = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return pythagorean;
    }

    /**
     * Surveys surrounding cells.
     * Uses pythagoreanDist to choose best cell to greedily pursue player's demise.
     *
     * @param puzzle
     * @param enemy
     * @return
     */
    public Cell chooseNextOptimalMove(Puzzle puzzle, Enemy enemy) {
        Player player = puzzle.getPlayer();
        List<Cell> View360 = new ArrayList<>();
        SquareBoard board = puzzle.getBoard();
        /**
         * puts all cells surrounding the enemy into a list - View360
         */
        TreeMap<Double, Cell> bestOptions = new TreeMap<Double, Cell>();

        int enemyX = enemy.currentLocation.getX();
        int enemyY = enemy.currentLocation.getY();
        for (int x = enemyX - 1; x < enemyX + 2; x++) {
            for (int y = enemyY - 1; y < enemyY + 2; y++) {

                    Cell neighborCell = new Cell(x, y);
                    boolean isValidCell = canMove(board, enemy.currentLocation, neighborCell);

                    if (puzzle.getDifficulty() <3 &&
                            moveIsDiagonal(enemy.currentLocation, neighborCell)){continue;}

                    if (isValidCell) {
                        double pythagorean = pythagoreanDist(
                                neighborCell,
                                player.getPlayerLocation());
                        bestOptions.put(pythagorean, neighborCell);
                    }
            }
        }
        Cell cell = (Cell) bestOptions.get(bestOptions.firstKey());
        System.out.println("Best Move : (" +cell.getX() + " , "+ cell.getY() + ")" );
        return (Cell) bestOptions.get(bestOptions.firstKey());
    }

    private boolean moveIsDiagonal (Cell a, Cell b) {
        int ax= a.getX();
        int ay = a.getY();
        int bx = b.getX();
        int by = b.getY();
        if (Math.abs(ax-bx) == 1 && Math.abs(ay-by)==1){
            return true;
        }
        return false;
    }

}
