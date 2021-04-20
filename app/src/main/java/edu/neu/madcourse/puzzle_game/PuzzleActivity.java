package edu.neu.madcourse.puzzle_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.neu.madcourse.puzzle_game.Model.Game.Cell;
import edu.neu.madcourse.puzzle_game.Model.Game.Enemy;
import edu.neu.madcourse.puzzle_game.Model.Game.EnemyStrategy;
import edu.neu.madcourse.puzzle_game.Model.Game.Player;
import edu.neu.madcourse.puzzle_game.Model.Game.Puzzle;
import edu.neu.madcourse.puzzle_game.Model.Game.SquareBoard;

public class PuzzleActivity extends AppCompatActivity {
    private Player player;
    private Enemy enemy;
    private Puzzle puzzle;
    private SquareBoard board;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_view);

    }

    @Override
    protected void onStart() {
        super.onStart();

        puzzle = (Puzzle) getIntent().getSerializableExtra("puzzle");
        this.player = puzzle.getPlayer();
        this.enemy = puzzle.getEnemy();
        this.board = puzzle.getBoard();
        Cell[][] cells = board.getMap();
        TableLayout tableLayout = findViewById(R.id.game);
        EnemyStrategy enemyStrategy = (EnemyStrategy) puzzle.getEnemyStrategy();


        board.bindBoardToTable(PuzzleActivity.this, tableLayout);


        findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!puzzle.isGameOver()) {
                    boolean playerDidMoveSuccessfully = puzzle.movePlayer(
                            player.getPlayerLocation(),
                            new Cell(player.getPlayerLocation().getX() - 1,
                                    player.getPlayerLocation().getY()
                            ));
                    Cell optimalMove = enemyStrategy.chooseNextOptimalMove(puzzle, enemy);
                    puzzle.moveEnemy(enemy.getEnemyLocation(), optimalMove);
                    if (playerDidMoveSuccessfully) {
                        board.bindBoardToTable(PuzzleActivity.this, tableLayout);
                    } else {
                        displayWarning("Oops, Moving Left Is Not Allowed.");
                    }
                } else {
                    goToGameOverActivity();
                }
            }

        });


        findViewById(R.id.btn_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!puzzle.isGameOver()) {
                    boolean playerDidMoveSuccessfully = puzzle.movePlayer(
                            player.getPlayerLocation(),
                            new Cell(player.getPlayerLocation().getX() + 1,
                                    player.getPlayerLocation().getY()
                            ));
                    Cell optimalMove = enemyStrategy.chooseNextOptimalMove(puzzle, enemy);
                    puzzle.moveEnemy(enemy.getEnemyLocation(), optimalMove);
                    if (playerDidMoveSuccessfully) {
                        board.bindBoardToTable(PuzzleActivity.this, tableLayout);
                    } else {
                        displayWarning("Oops, Moving Right Is Not Allowed.");
                    }
                } else {
                    goToGameOverActivity();
                }
            }
        });


        findViewById(R.id.btn_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!puzzle.isGameOver()) {
                    boolean playerDidMoveSuccessfully = puzzle.movePlayer(
                            player.getPlayerLocation(),
                            new Cell(player.getPlayerLocation().getX(),
                                    player.getPlayerLocation().getY() - 1
                            ));

                    Cell optimalMove = enemyStrategy.chooseNextOptimalMove(puzzle, enemy);
                    puzzle.moveEnemy(enemy.getEnemyLocation(), optimalMove);
                    if (playerDidMoveSuccessfully) {
                        board.bindBoardToTable(PuzzleActivity.this, tableLayout);
                    } else {
                        displayWarning("Oops, Moving Up Is Not Allowed.");
                    }
                } else {
                    goToGameOverActivity();
                }
            }
        });


        findViewById(R.id.btn_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!puzzle.isGameOver()) {
                    boolean playerDidMoveSuccessfully = puzzle.movePlayer(
                            player.getPlayerLocation(),
                            new Cell(player.getPlayerLocation().getX(),
                                    player.getPlayerLocation().getY() + 1
                            ));

                    Cell optimalMove = enemyStrategy.chooseNextOptimalMove(puzzle, enemy);
                    puzzle.moveEnemy(enemy.getEnemyLocation(), optimalMove);
                    if (playerDidMoveSuccessfully) {
                        board.bindBoardToTable(PuzzleActivity.this, tableLayout );
                    } else {
                        displayWarning("Oops, Moving Down Is Not Allowed.");
                    }
                } else {
                    goToGameOverActivity();
                }
            }
        });


    }

    public void goToGameOverActivity() {
        Intent intent = new Intent(this, PlayAgainActivity.class);
        intent.putExtra("puzzle", puzzle);
        startActivity(intent);
    }

    public void displayWarning(String warning) {
        Toast toast = Toast.makeText(
                PuzzleActivity.this,
                warning,
                Toast.LENGTH_SHORT);
        toast.show();
    }
}