package com.xti.spring.cloud.heroku.discovery.metadata;

import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class RemoteMetadataProvider {

    private RestTemplate restTemplate = new RestTemplate();

    public Map<String, String> getMetadata(URI instanceURI) {
        try {
            return restTemplate.getForObject(instanceURI.resolve("/spring-cloud-heroku-discovery"), Map.class);
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
}
