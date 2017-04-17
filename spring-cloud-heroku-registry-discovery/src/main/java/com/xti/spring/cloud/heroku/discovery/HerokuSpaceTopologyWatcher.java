package com.xti.spring.cloud.heroku.discovery;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyV1;
import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyWatcherTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        executorService.execute(new HerokuSpaceTopologyWatcherTask(t -> {
            updateTopology(t);
            publisher.publishEvent(new HeartbeatEvent(this, t));
        }));
    }

    @PreDestroy
    public void destroy(){
        executorService.shutdownNow();
    }


    public synchronized HerokuSpaceTopologyV1 getTopology() {
        Path herokuFolder = Paths.get("/etc/heroku");
        Path spaceTopologyFile = herokuFolder.resolve("space-topology.json");
        HerokuSpaceTopologyV1 herokuSpaceTopologyV1 = null;

        if(Files.exists(spaceTopologyFile)){
            try {
                herokuSpaceTopologyV1 = objectMapper.readValue(spaceTopologyFile.toFile(), HerokuSpaceTopologyV1.class);
                System.out.println("Emitted topology event by force: " + herokuSpaceTopologyV1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return herokuSpaceTopologyV1;
    }

    private synchronized void updateTopology(HerokuSpaceTopologyV1 topology) {
        this.topology = topology;
    }
}
