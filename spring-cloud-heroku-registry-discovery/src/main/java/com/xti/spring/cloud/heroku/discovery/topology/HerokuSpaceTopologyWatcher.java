package com.xti.spring.cloud.heroku.discovery.topology;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class HerokuSpaceTopologyWatcher {

    private ExecutorService executorService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostConstruct
    public void init() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new HerokuSpaceTopologyWatcherTask(t -> publisher.publishEvent(new HeartbeatEvent(this, t))));
    }
}
