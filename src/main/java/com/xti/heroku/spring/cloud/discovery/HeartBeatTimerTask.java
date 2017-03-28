package com.xti.heroku.spring.cloud.discovery;

import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.TimerTask;

public class HeartBeatTimerTask extends TimerTask{

    private ApplicationEventPublisher publisher;

    public HeartBeatTimerTask(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void run() {
        publisher.publishEvent(new HeartbeatEvent(this, null));
    }
}
