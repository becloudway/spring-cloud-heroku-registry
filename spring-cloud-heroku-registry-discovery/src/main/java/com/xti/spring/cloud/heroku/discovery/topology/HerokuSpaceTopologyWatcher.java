package com.xti.spring.cloud.heroku.discovery.topology;

import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HerokuSpaceTopologyWatcher extends HerokuSpaceTopologyListener {

    private ExecutorService executorService;
    private String targetFilePath;

    public HerokuSpaceTopologyWatcher(String targetFilePath, ApplicationEventPublisher publisher) {
        super(publisher);
        this.targetFilePath = targetFilePath;
    }

    @PostConstruct
    public void init() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new HerokuSpaceTopologyWatcherTask(Paths.get(targetFilePath), this::updateTopology));
    }

    @PreDestroy
    public void destroy(){
        executorService.shutdownNow();
    }
}
