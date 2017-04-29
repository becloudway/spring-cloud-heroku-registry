package com.xti.spring.cloud.heroku.discovery.topology;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.function.Consumer;

public class HerokuSpaceTopologyWatcherTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(HerokuSpaceTopologyWatcherTask.class);

    private Path targetFile;
    private Consumer<HerokuSpaceTopologyV1> eventHandler;
    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    public HerokuSpaceTopologyWatcherTask(Path targetFile, Consumer<HerokuSpaceTopologyV1> eventHandler) {
        this.targetFile = targetFile;
        this.eventHandler = eventHandler;
    }

    @Override
    public void run() {

        log.info("Started watching {}.", targetFile.toString());

        Path targetFolder = targetFile.getParent();

        //initial load of space-topology.v1
        if(targetFile.toFile().exists()){
            processTopology();
        }

        try(WatchService watchService = FileSystems.getDefault().newWatchService()) {
            targetFolder.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);

            while (true){
                final WatchKey key = watchService.take();

                if (key != null) {
                    for (WatchEvent<?> watchEvent : key.pollEvents()) {
                        final WatchEvent.Kind<?> kind = watchEvent.kind();

                        if (kind != StandardWatchEventKinds.OVERFLOW) {
                            final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
                            Path fileName = watchEventPath.context();

                            if(fileName.endsWith(targetFile.getFileName().toString())) {

                                if (kind == StandardWatchEventKinds.ENTRY_CREATE || kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                                    processTopology();

                                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                                    eventHandler.accept(null);
                                    log.debug("Emitted topology event:\n{}", (HerokuSpaceTopologyV1) null);
                                }
                            }
                        }
                    }

                    if (!key.reset()) {
                        System.out.println("Could not reset the watch key.");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            log.error("Could not create watch service.", e);
        } catch (InterruptedException e){
            log.warn("Space topology watcher interrupted.", e);
            Thread.currentThread().interrupt();
        }
    }

    private void processTopology() {
        try {
            HerokuSpaceTopologyV1 spaceTopology = objectMapper.readValue(targetFile.toFile(), HerokuSpaceTopologyV1.class);
            eventHandler.accept(spaceTopology);
            log.debug("Emitted topology event:\n{}", spaceTopology);
        } catch (IOException e) {
            log.warn("Could not read " + targetFile.toString(), e);
        }
    }
}
