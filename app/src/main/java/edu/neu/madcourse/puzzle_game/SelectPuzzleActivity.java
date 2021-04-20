package edu.neu.madcourse.puzzle_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import edu.neu.madcourse.puzzle_game.Model.Game.Cell;
import edu.neu.madcourse.puzzle_game.Model.Game.CellType;
import edu.neu.madcourse.puzzle_game.Model.Game.Enemy;
import edu.neu.madcourse.puzzle_game.Model.Game.Player;
import edu.neu.madcourse.puzzle_game.Model.Game.Puzzle;
import edu.neu.madcourse.puzzle_game.Model.Game.SquareBoard;

public class SelectPuzzleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_puzzles_view);
    }


    @Override
    protected  void  onStart() {
        super.onStart();

        Player player = new Player(new Cell(0,0, CellType.PLAYER_AVATAR));


        findViewById(R.id.btn_puzzle_lvl_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SquareBoard board = new SquareBoard( 8);
                Enemy enemy = new Enemy(new Cell(6,6, CellType.PLAYER_AVATAR));
                Puzzle puzzle = new Puzzle(1, board, player,enemy);
                puzzle.setTraps(10);
                puzzle.setTreasureRandomly();
                goToPuzzleActivity(v, puzzle);
            }
        });
        findViewById(R.id.btn_puzzle_lvl_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SquareBoard board = new SquareBoard( 10);
                Enemy enemy = new Enemy(new Cell(8,8, CellType.PLAYER_AVATAR));
                Puzzle puzzle = new Puzzle(2, board, player,enemy);
                puzzle.setTraps(10);
                puzzle.setTreasureRandomly();
                goToPuzzleActivity(v, puzzle);
            }
        });
        findViewById(R.id.btn_puzzle_lvl_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SquareBoard board = new SquareBoard(12);
                Enemy enemy = new Enemy(new Cell(10,10, CellType.PLAYER_AVATAR));
                Puzzle puzzle = new Puzzle(3, board, player,enemy);
                puzzle.setTraps(45);
                puzzle.setTreasureRandomly();
                goToPuzzleActivity(v, puzzle);
            }
        });

    }

    public void goToPuzzleActivity(View view, Puzzle puzzle) {
        Intent intent = new Intent(this, PuzzleActivity.class);
        intent.putExtra("puzzle", puzzle);
        startActivity(intent);
    }


}