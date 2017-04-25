package com.xti.spring.cloud.heroku.discovery.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocallyMutableMetadataProvider {

    private static final Logger log = LoggerFactory.getLogger(LocallyMutableMetadataProvider.class);

    private Map<String, String> metadata;
    private RestTemplate restTemplate = new RestTemplate();
    private static LocallyMutableMetadataProvider instance;

    private LocallyMutableMetadataProvider(){
        metadata = new ConcurrentHashMap<>();
    }

    public static LocallyMutableMetadataProvider getInstance(){
        if(instance == null){
            instance = new LocallyMutableMetadataProvider();
        }
        return instance;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Map<String, String> getMetadata(URI instanceURI){
        try {
            ResponseEntity<Map<String, String>> exchange = restTemplate.exchange(
                    instanceURI.resolve("/spring-cloud-heroku-metadata"),
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<Map<String, String>>() {});
            return exchange.getBody();
        } catch (Exception e) {
            log.warn("Could not get metadata from " + instanceURI.getHost(), e);
            return new HashMap<>();
        }
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
