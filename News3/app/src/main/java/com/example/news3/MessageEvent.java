package com.example.news3;

public class MessageEvent {
    public final String message;
    public int p_index;

    public MessageEvent(int i) {
        this.message = "get";
        this.p_index=i;
    }
}