package com.xti.heroku.spring.cloud.discovery.metadata;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/heroku-spring-cloud-discovery")
public class MetadataResource implements MetadataProvider {

    public Map<String, String> metadata = new ConcurrentHashMap<String, String>();

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping()
    public @ResponseBody Map<String, String> getMetadata() {
        return metadata;
    }

    public Map<String, String> getMetadata(@NotNull URI instanceURI){
        return restTemplate.getForObject(instanceURI.resolve("/heroku-spring-cloud-discovery"), Map.class);
    }
}
