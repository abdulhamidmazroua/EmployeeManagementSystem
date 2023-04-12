package models;

public class UserData {
    private String username;
    private String image_uri;

    public UserData() {}

    public UserData(String username, String image_uri) {
        this.username = username;
        this.image_uri = image_uri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }
}
