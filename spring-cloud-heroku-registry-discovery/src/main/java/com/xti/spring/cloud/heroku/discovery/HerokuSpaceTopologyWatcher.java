package com.xti.spring.cloud.heroku.discovery;

import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyV1;
import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyWatcherTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HerokuSpaceTopologyWatcher {

    private ExecutorService executorService;

    private HerokuSpaceTopologyV1 topology = null;

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
