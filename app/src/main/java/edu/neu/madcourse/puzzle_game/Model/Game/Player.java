package edu.neu.madcourse.puzzle_game.Model.Game;

import java.io.Serializable;

public class Player implements Serializable {
    int stemina;
    Cell currentLocation;

    public Player(Cell initialLocation) {
        this.currentLocation = initialLocation;
        this.stemina = 100;
    }

    public int getStemina (){
        return this.stemina;
    }

    public void setPlayerLocation(Cell newLocation) {
        this.currentLocation = newLocation;
    }

    public Cell getPlayerLocation() {
        return this.currentLocation;
    }

}
