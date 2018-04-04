package com.conference.business;

import java.time.LocalTime;

public class Schedule {

    private LocalTime beginTime;
    private Talk talk;

    public LocalTime getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public Talk getTalk() {
        return this.talk;
    }

    public void setTalk(Talk talk) {
        this.talk = talk;
    }

    @Override
    public String toString() {
        return beginTime + " - " + talk;
    }
}
