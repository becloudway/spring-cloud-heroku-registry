package com.xti.spring.cloud.heroku.discovery.topology;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.function.Consumer;

public class HerokuSpaceTopologyWatcherTask implements Runnable {

    private Consumer<HerokuSpaceTopologyV1> eventHandler;
    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    public HerokuSpaceTopologyWatcherTask(Consumer<HerokuSpaceTopologyV1> eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void run() {

        System.out.println("Started watching /etc/heroku/space-topology.json");

        Path herokuFolder = Paths.get("/etc/heroku");
        Path spaceTopologyFile = herokuFolder.resolve("space-topology.json");

        if(Files.exists(spaceTopologyFile)){
            HerokuSpaceTopologyV1 herokuSpaceTopologyV1 = null;
            try {
                herokuSpaceTopologyV1 = objectMapper.readValue(spaceTopologyFile.toFile(), HerokuSpaceTopologyV1.class);
                eventHandler.accept(herokuSpaceTopologyV1);
                System.out.println("Emitted topology event at start: " + herokuSpaceTopologyV1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try(WatchService watchService = FileSystems.getDefault().newWatchService()){



            herokuFolder.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);

            while(true) {
                final WatchKey watchKey = watchService.take();

                for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
                    final WatchEvent.Kind<?> kind = watchEvent.kind();

                    if (kind != StandardWatchEventKinds.OVERFLOW) {
                        final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
                        Path fileName = watchEventPath.context();

                        if(fileName.endsWith("space-topology.json")) {

                            if (kind == StandardWatchEventKinds.ENTRY_CREATE || kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                                Path fullPath = herokuFolder.resolve(fileName);
                                HerokuSpaceTopologyV1 herokuSpaceTopologyV1 = objectMapper.readValue(fullPath.toFile(), HerokuSpaceTopologyV1.class);
                                eventHandler.accept(herokuSpaceTopologyV1);
                                System.out.println("Emitted topology event: " + herokuSpaceTopologyV1);

                            } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                                eventHandler.accept(null);
                            }
                        }
                    }
                }

                if (!watchKey.reset()) {
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
