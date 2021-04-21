package edu.neu.madcourse.puzzle_game.Model.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Puzzle implements Serializable {
    private int difficulty;
    private SquareBoard board;
    private Player player;
    private List<Enemy> enemies;
    private Cell treasure;
    List<Cell> traps;
    private List<IStrategy> gameRules = new ArrayList<>();
    IStrategy enemyStrategy;
    public boolean gameOver;
    public boolean gameWon;
    int score;


    public Puzzle(int difficulty, SquareBoard board, Player player, List<Enemy> enemies) {
        this.difficulty = difficulty;
        this.board = board;
        this.player = player;
        this.enemies = enemies;
        traps = new ArrayList<>();
        bindPlayerToBoard();
        IStrategy orthoMove = new OrthogonalStrategy();
        enemyStrategy = new EnemyStrategy();
        gameRules.add(orthoMove);
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public SquareBoard getBoard() {
        return board;
    }

    public void setTraps(int totalNumTraps) {
        for (int i = 0; i < totalNumTraps; i++) {
            Random rand = new Random(); //instance of random class
            int upperBound = board.getSideDim() - 2;
            int randX = rand.nextInt(upperBound) + 1;
            int randY = rand.nextInt(upperBound) + 1;
            Cell trap = new Cell(randX, randY, CellType.TRAP);
            traps.add(trap);
            board.updateCell(trap);
        }
    }

    public void setTreasureRandomly() {
        Random rand = new Random(); //instance of random class
        int upperBound = Math.round(board.getSideDim() / 3);
        int randX = Math.round(board.getSideDim() / 2) + rand.nextInt(upperBound) - 1;
        int randY = Math.round(board.getSideDim() / 2) + rand.nextInt(upperBound) - 1;
        Cell treasure = new Cell(randX, randY, CellType.TREASURE);
        this.treasure = treasure;
        board.updateCell(treasure);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    private void bindPlayerToBoard() {
        Cell playerLocation = player.currentLocation;
        board.updateCell(
                new Cell(
                        playerLocation.getX(),
                        playerLocation.getY(),
                        CellType.PLAYER_AVATAR
                ));
    }


    public boolean movePlayer(Cell from, Cell to) {
        for (IStrategy rule : gameRules) {
            if (rule.canMove(board, from, to)) {
                player.setPlayerLocation(to);
                bindPlayerToBoard();

                board.updateCell(
                        new Cell(
                                from.getX(),
                                from.getY(),
                                CellType.Empty
                        ));
                return true;
            }
        }
        return false;
    }

    private void bindEnemyToBoard(Enemy enemy) {
        Cell enemyLocation = enemy.currentLocation;
        board.updateCell(
                new Cell(
                        enemyLocation.getX(),
                        enemyLocation.getY(),
                        CellType.ENEMY_SORCERER_AVATAR
                ));
    }

    public boolean moveEnemy(Enemy enemy, Cell from, Cell to) {
        if (enemyStrategy.canMove(board, from, to)) {
            enemy.setEnemyLocation(to);
            bindEnemyToBoard(enemy);
            board.updateCell(
                    new Cell(
                            from.getX(),
                            from.getY(),
                            CellType.Empty
                    ));
        }
        return false;
    }

    public IStrategy getEnemyStrategy() {
        return this.enemyStrategy;
    }

    public boolean isGameOver() {
        for (Enemy enemy : enemies) {
            int playerX = player.getPlayerLocation().getX();
            int playerY = player.getPlayerLocation().getY();
            int enemyX = enemy.getEnemyLocation().getX();
            int enemyY = enemy.getEnemyLocation().getY();
            int treasureX = treasure.getX();
            int treasureY = treasure.getY();
            if (Math.abs(playerX - enemyX) == 0 && Math.abs(playerY - enemyY) == 0) {
                this.gameWon = false;
                this.gameOver = true;
                return true;
            }
            if (player.getStemina() <= 0) {
                this.gameWon = false;
                this.gameOver = true;
                return true;
            }
            if (Math.abs(playerX - treasureX) == 0 && Math.abs(playerY - treasureY) == 0) {
                this.gameWon = true;
                this.gameOver = true;
                return true;
            }
        }
        return false;
    }

    public boolean getGameResult() {
        return gameWon;
    }

}
