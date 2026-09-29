package ue.edu.co.volleyapi;

import org.json.JSONObject;

public class Post {

    private int userId;
    private int id;
    private String title;
    private String body;

    public Post(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public static Post fromJson(JSONObject json) {
        int userId = json.optInt("userId");
        int id = json.optInt("id");
        String title = json.optString("title");
        String body = json.optString("body");
        return new Post(userId, id, title, body);
    }
}