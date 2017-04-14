package com.xti.spring.cloud.heroku.discovery;

import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyV1;
import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyWatcherTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
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

    @PostConstruct
    public void init() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new HerokuSpaceTopologyWatcherTask(t -> {
            topology = t;
            publisher.publishEvent(new HeartbeatEvent(this, t));
        }));
    }

    @PreDestroy
    public void destroy(){
        executorService.shutdownNow();
    }


    public HerokuSpaceTopologyV1 getTopology() {
        return topology;
    }
}
