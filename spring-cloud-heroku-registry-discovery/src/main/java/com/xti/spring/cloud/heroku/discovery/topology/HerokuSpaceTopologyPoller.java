package com.xti.spring.cloud.heroku.discovery.topology;

import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HerokuSpaceTopologyPoller extends HerokuSpaceTopologyListener {

    private ExecutorService executorService;
    private String targetFilePath;

    public HerokuSpaceTopologyPoller(String targetFilePath, ApplicationEventPublisher publisher) {
        super(publisher);
        this.targetFilePath = targetFilePath;
    }


    @PostConstruct
    public void init() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new HerokuSpaceTopologyPollerTask(Paths.get(targetFilePath) ,1000, this::updateTopology));
    }

    @PreDestroy
    public void destroy(){
        executorService.shutdownNow();
    }
}
