package br.com.app.template.model;

/**
 * Created by artur on 13/08/15.
 */
public class Tweet {

    private User user;

    private String text;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
