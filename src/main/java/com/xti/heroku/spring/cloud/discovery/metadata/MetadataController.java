package com.xti.heroku.spring.cloud.discovery.metadata;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/heroku-spring-cloud-discovery")
public class MetadataController {

    private MetadataProvider metadataProvider = LocallyMutableMetadataProvider.getInstance();

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping()
    public @ResponseBody Map<String, String> getMetadata() {
        return metadataProvider.getMetadata();
    }

}
