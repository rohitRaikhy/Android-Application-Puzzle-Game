package edu.neu.madcourse.puzzle_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.neu.madcourse.puzzle_game.Model.Game.Puzzle;

public class PlayAgainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_again);
        Puzzle puzzle = (Puzzle) getIntent().getSerializableExtra("puzzle");
        boolean gameWon = puzzle.getGameResult();
        TextView resutlView  = findViewById(R.id.txt_result);
        if (gameWon) {resutlView.setText("You Won! Play Again And Try A Different Puzzle.");}
        else {resutlView.setText("You Lost! Sorry, Better Luck Next Time.");}
    }

    public void goToSelectPuzzleActivity(View view) {
        Intent intent = new Intent(this, SelectPuzzleActivity.class);
        startActivity(intent);
    }
}