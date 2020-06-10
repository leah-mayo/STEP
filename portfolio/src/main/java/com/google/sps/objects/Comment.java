package com.google.sps.objects;

/** A user's comment on a website */

public final class Comment {
    
    // constants used for Datastore operations
    public static final String TYPE = "Comment";

    public static final String COMMENT_TEXT = "Comment";

    private final String key;
    private final String text;

    public Comment(String key,  String text) {
        this.key = key;
        this.text = text;
    }
}