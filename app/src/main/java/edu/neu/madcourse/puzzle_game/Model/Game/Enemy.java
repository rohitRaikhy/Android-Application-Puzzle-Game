package edu.neu.madcourse.puzzle_game.Model.Game;

import java.io.Serializable;

public class Enemy implements Serializable {
    Cell currentLocation;

    public Enemy(Cell initialLocation) {
        currentLocation = initialLocation;
    }

    public void setEnemyLocation(Cell newLocation) {
        this.currentLocation = newLocation;
    }

    public Cell getEnemyLocation() {
        return this.currentLocation;
    }


}
