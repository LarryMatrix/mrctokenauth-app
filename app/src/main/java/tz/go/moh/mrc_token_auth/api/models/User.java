package tz.go.moh.mrc_token_auth.api.models;


public class User {

    private String key;

    public User(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
