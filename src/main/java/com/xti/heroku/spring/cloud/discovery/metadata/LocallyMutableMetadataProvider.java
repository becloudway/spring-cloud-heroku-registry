package com.xti.heroku.spring.cloud.discovery.metadata;

import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocallyMutableMetadataProvider implements MetadataProvider {

    private Map<String, String> metadata;
    private RestTemplate restTemplate = new RestTemplate();
    private static LocallyMutableMetadataProvider instance;

    private LocallyMutableMetadataProvider(){
        metadata = new ConcurrentHashMap<String, String>();
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

    public Map<String, String> getMetadata(@NotNull URI instanceURI){
        try {
            return restTemplate.getForObject(instanceURI.resolve("/heroku-spring-cloud-discovery"), Map.class);
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
