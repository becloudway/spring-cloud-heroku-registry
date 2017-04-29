package com.xti.spring.cloud.heroku.discovery.topology;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;

public class HerokuSpaceTopologyPollerTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(HerokuSpaceTopologyPollerTask.class);

    private Consumer<HerokuSpaceTopologyV1> eventHandler;
    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    private Path targetFile;
    private int delay;

    HerokuSpaceTopologyV1 storedSpaceTopology = null;

    public HerokuSpaceTopologyPollerTask(Path targetFile, int delay, Consumer<HerokuSpaceTopologyV1> eventHandler) {
        this.targetFile = targetFile;
        this.delay = delay;
        this.eventHandler = eventHandler;
    }

    @Override
    public void run() {
        log.info("Starting polling of {}.", targetFile.toString());
        while (true){
            if(targetFile.toFile().exists()){
                try {
                    HerokuSpaceTopologyV1 newSpaceTopology = objectMapper.readValue(targetFile.toFile(), HerokuSpaceTopologyV1.class);
                    if(!Objects.equals(storedSpaceTopology, newSpaceTopology)){
                        eventHandler.accept(newSpaceTopology);
                        log.debug("Emitted topology event:\n{}", newSpaceTopology);
                    }
                    storedSpaceTopology = newSpaceTopology;
                } catch (IOException e) {
                    log.warn("Could not read " + targetFile.toString(), e);
                }
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                log.warn("Space topology poller interrupted.", e);
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
