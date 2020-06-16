package com.google.sps.objects;

/** A user's comment on a website */

public final class Comment {
    
    // constants used for Datastore operations
    public static final String TYPE = "Comment";

    public static final String COMMENT_TEXT = "Comment";
    public static final String NAME = "Name";
    public static final String EMAIL = "Email";

    private final String key;
    private final String text;
    private final String name;
    private final String email;

    public Comment(String key,  String text, String name, String email) {
        this.key = key;
        this.text = text;
        this.name = name;
        this.email = email;
    }
}