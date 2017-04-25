package com.xti.spring.cloud.heroku.discovery.topology;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Consumer;

public class HerokuSpaceTopologyWatcherTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(HerokuSpaceTopologyWatcherTask.class);

    private Consumer<HerokuSpaceTopologyV1> eventHandler;
    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    private int delay;

    HerokuSpaceTopologyV1 storedSpaceTopology = null;

    public HerokuSpaceTopologyWatcherTask(int delay, Consumer<HerokuSpaceTopologyV1> eventHandler) {
        this.delay = delay;
        this.eventHandler = eventHandler;
    }

    @Override
    public void run() {

        log.info("Started watching /etc/heroku/space-topology.json");

        Path herokuFolder = Paths.get("/etc/heroku");
        Path spaceTopologyFile = herokuFolder.resolve("space-topology.json");

        while (true){
            if(Files.exists(spaceTopologyFile)){
                try {
                    HerokuSpaceTopologyV1 newSpaceTopology = objectMapper.readValue(spaceTopologyFile.toFile(), HerokuSpaceTopologyV1.class);
                    if(!Objects.equals(storedSpaceTopology, newSpaceTopology)){
                        eventHandler.accept(newSpaceTopology);
                        log.debug("Emitted topology event : " + newSpaceTopology);
                    }
                    storedSpaceTopology = newSpaceTopology;
                } catch (IOException e) {
                    log.warn("Could not read space-topology.json file.", e);
                }
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                log.warn("Space topology watcher interrupted.", e);
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }
}
