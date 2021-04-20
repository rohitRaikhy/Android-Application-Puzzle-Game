package edu.neu.madcourse.puzzle_game.Model;

public class GameUser {

    private String username;
    private String email;
    private String id;
    private String imageUrl;

    /**
     * Constructor to make the GameUser.
     *
     * @param imageUrl String imageUrl profile photo.
     * @param id       String id of the user.
     * @param email    String email of the user.
     * @param username String username of the user.
     */
    public GameUser(String imageUrl, String id, String email, String username) {
        this.imageUrl = imageUrl;
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public GameUser() {
    }

    /**
     * Gets the username of the user.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email of the user.
     *
     * @return the email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the image url of the user profile photo.
     *
     * @return image url profile photo of user.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * gets the id of the user.
     *
     * @return the id of the user.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the user.
     *
     * @param id the id of the user.
     */
    public void setUid(String id) {
        this.id = id;
    }

    /**
     * sets the image url of the user.
     */
    public void setImageUrl() {
        this.imageUrl = imageUrl;
    }
}
