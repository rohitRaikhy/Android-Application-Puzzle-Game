package edu.neu.madcourse.puzzle_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.puzzle_game.Model.Game.Cell;
import edu.neu.madcourse.puzzle_game.Model.Game.Enemy;
import edu.neu.madcourse.puzzle_game.Model.Game.EnemyStrategy;
import edu.neu.madcourse.puzzle_game.Model.Game.Player;
import edu.neu.madcourse.puzzle_game.Model.Game.Puzzle;
import edu.neu.madcourse.puzzle_game.Model.Game.SquareBoard;

public class PuzzleActivity extends AppCompatActivity {
    private Player player;
    private List<Enemy> enemies;
    private Puzzle puzzle;
    private SquareBoard board;
    private EnemyStrategy enemyStrategy;
    private TableLayout tableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_view);
        tableLayout = findViewById(R.id.game);
        puzzle = (Puzzle) getIntent().getSerializableExtra("puzzle");
        this.player = puzzle.getPlayer();
        this.enemies = puzzle.getEnemies();
        this.board = puzzle.getBoard();
        Cell[][] cells = board.getMap();
        enemyStrategy = (EnemyStrategy) puzzle.getEnemyStrategy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        board.bindBoardToTable(PuzzleActivity.this, tableLayout);


        setOnClickListener(R.id.btn_up, 0, -1, "Up");
        setOnClickListener(R.id.btn_down, 0, +1, "Down");
        setOnClickListener(R.id.btn_left, -1, 0, "Left");
        setOnClickListener(R.id.btn_right, +1, 0, "Right");

    }
    public void setOnClickListener(int arrow, int xChange, int yChange, String warning) {
        findViewById(arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!puzzle.isGameOver()) {
                    boolean playerDidMoveSuccessfully = puzzle.movePlayer(
                            player.getPlayerLocation(),
                            new Cell(player.getPlayerLocation().getX()+ xChange,
                                    player.getPlayerLocation().getY() + yChange
                            ));
                    for (Enemy enemy : enemies) {
                        Cell optimalMove = enemyStrategy.chooseNextOptimalMove(puzzle, enemy);
                        puzzle.moveEnemy(enemy, enemy.getEnemyLocation(), optimalMove);
                    }
                    if (playerDidMoveSuccessfully) {
                        board.bindBoardToTable(PuzzleActivity.this, tableLayout);
                    } else {
                        displayWarning(warning);
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