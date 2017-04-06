package com.xti.spring.cloud.heroku.discovery.example;

public class CreateNoteResponse {
    private String id;

    public CreateNoteResponse() {
    }

    public CreateNoteResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
