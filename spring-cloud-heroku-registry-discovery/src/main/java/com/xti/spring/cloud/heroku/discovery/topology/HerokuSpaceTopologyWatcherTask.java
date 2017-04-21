package com.xti.spring.cloud.heroku.discovery.topology;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Consumer;

public class HerokuSpaceTopologyWatcherTask implements Runnable {

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

        System.out.println("Started watching /etc/heroku/space-topology.json");

        Path herokuFolder = Paths.get("/etc/heroku");
        Path spaceTopologyFile = herokuFolder.resolve("space-topology.json");

        while (true){
            if(Files.exists(spaceTopologyFile)){
                try {
                    HerokuSpaceTopologyV1 newSpaceTopology = objectMapper.readValue(spaceTopologyFile.toFile(), HerokuSpaceTopologyV1.class);
                    if(!Objects.equals(storedSpaceTopology, newSpaceTopology)){
                        eventHandler.accept(newSpaceTopology);
                        System.out.println("Emitted topology event : " + newSpaceTopology);
                    }
                    storedSpaceTopology = newSpaceTopology;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
