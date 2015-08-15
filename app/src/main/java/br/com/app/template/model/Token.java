package br.com.app.template.model;

/**
 * Created by artur on 14/08/15.
 */
public class Token {

    private String token_type;

    private String access_token;

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
