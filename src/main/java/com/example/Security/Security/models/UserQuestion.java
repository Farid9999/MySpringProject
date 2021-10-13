package com.example.Security.Security.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id", nullable = false)
    private AppUser appUser;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Worker_Id", nullable = true)
    private AppUser workerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public AppQuestion getAppQuestion() {
        return appQuestion;
    }

    public void setAppQuestion(AppQuestion appQuestion) {
        this.appQuestion = appQuestion;
    }

    public Date getDateOfReguest() {
        return dateOfReguest;
    }

    public void setDateOfReguest(Date dateOfReguest) {
        this.dateOfReguest = dateOfReguest;
    }

    public Boolean getAnswered() {
        return isAnswered;
    }

    public void setAnswered(Boolean answered) {
        isAnswered = answered;
    }


    public Long Mark;

    public Long getMark() {
        return Mark;
    }

    public void setMark(Long mark) {
        Mark = mark;
    }

    public UserQuestion(AppUser appUser, AppQuestion appQuestion, Date dateOfReguest, Boolean isAnswered, AppUser workerId) {
        this.workerId = null;
        this.appUser = appUser;
        this.appQuestion = appQuestion;
        this.dateOfReguest = dateOfReguest;
        this.isAnswered = isAnswered;
    }

    public AppUser getWorkerId() {
        return workerId;
    }

    public void setWorkerId(AppUser workerId) {
        this.workerId = workerId;
    }

    public UserQuestion() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AppQuestion_Id", nullable = false)
    private AppQuestion appQuestion;

    private Date dateOfReguest;

    private Boolean isAnswered;
}
