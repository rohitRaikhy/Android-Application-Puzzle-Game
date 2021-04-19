package edu.neu.madcourse.puzzle_game.Model;

public class GameUser {

    private String username;
    private String email;
    private String id;
    private String imageUrl;

    public GameUser(String imageUrl, String id, String email, String username) {
        this.imageUrl = imageUrl;
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public GameUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String id) {
        this.id = id;
    }

    public void setImageUrl() {
        this.imageUrl = imageUrl;
    }
}
