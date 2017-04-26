package com.xti.spring.cloud.heroku.discovery.metadata;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/spring-cloud-heroku-metadata", produces = MediaType.APPLICATION_JSON_VALUE)
public class MetadataController {

    private LocallyMutableMetadataProvider metadataProvider = LocallyMutableMetadataProvider.getInstance();

    @GetMapping()
    public @ResponseBody Map<String, String> getMetadata() {
        return metadataProvider.getMetadata();
    }

}
