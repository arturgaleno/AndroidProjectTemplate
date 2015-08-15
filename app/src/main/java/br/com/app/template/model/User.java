package br.com.app.template.model;

/**
 * Created by artur on 13/08/15.
 */
public class User {

    private String screen_name;

    private String profile_image_url;

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }
}
