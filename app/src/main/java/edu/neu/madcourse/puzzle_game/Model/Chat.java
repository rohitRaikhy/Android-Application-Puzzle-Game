package edu.neu.madcourse.puzzle_game.Model;

public class Chat {

    private String sender;
    private String receiver;
    private String message;

    /**
     * Constructor to make the chat.
     *
     * @param sender   String the sender of message.
     * @param receiver String the receiver of message.
     * @param message  String the message.
     */
    public Chat(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public Chat() {

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
