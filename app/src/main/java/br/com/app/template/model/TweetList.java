package br.com.app.template.model;

import java.util.List;

/**
 * Created by artur on 14/08/15.
 */
public class TweetList {

    List<Tweet> statuses;

    public List<Tweet> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Tweet> statuses) {
        this.statuses = statuses;
    }
}
