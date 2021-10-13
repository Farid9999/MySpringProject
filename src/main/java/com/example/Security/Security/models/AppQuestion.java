package com.example.Security.Security.models;

import javax.persistence.*;

@Entity
public class AppQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AppQuestion_Id", nullable = false)
    private Long id;

    public AppQuestion() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "question='" + question ;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public AppQuestion(String question) {
        this.question = question;
    }

    private String question;
}
