package com.xti.spring.cloud.heroku.discovery.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(value = "/spring-cloud-heroku-metadata", produces = MediaType.APPLICATION_JSON_VALUE)
public class MetadataController {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private LocallyMutableMetadataProvider metadataProvider;

    private static final Logger log = LoggerFactory.getLogger(MetadataController.class);

    @GetMapping()
    public @ResponseBody Map<String, String> getMetadata() {
        log.trace("received call for metadata");
        return metadataProvider.getMetadata();
    }

    @GetMapping(path = "/notify", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, String> forceHeartBeat() {
        log.trace("notified of metadata changes");
        applicationEventPublisher.publishEvent(new RemoteMetadataChangedEvent(this, null));
        return Collections.singletonMap("status", "ok");
    }
}
