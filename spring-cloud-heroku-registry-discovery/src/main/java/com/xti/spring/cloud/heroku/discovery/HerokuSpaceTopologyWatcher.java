package com.xti.spring.cloud.heroku.discovery;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyV1;
import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyWatcherTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class HerokuSpaceTopologyWatcher {

    private ExecutorService executorService;

    private HerokuSpaceTopologyV1 topology = null;

    @Autowired
    private ApplicationEventPublisher publisher;

    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    @PostConstruct
    public void init() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new HerokuSpaceTopologyWatcherTask(1000, this::updateTopology));
    }

    @PreDestroy
    public void destroy(){
        executorService.shutdownNow();
    }


    public synchronized HerokuSpaceTopologyV1 getTopology() {
        return topology;
    }

    private synchronized void updateTopology(HerokuSpaceTopologyV1 topology) {
        this.topology = topology;
    }
}
