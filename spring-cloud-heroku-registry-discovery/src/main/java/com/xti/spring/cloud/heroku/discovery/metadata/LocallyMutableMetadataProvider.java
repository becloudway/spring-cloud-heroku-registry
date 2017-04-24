package com.xti.spring.cloud.heroku.discovery.metadata;

import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocallyMutableMetadataProvider {

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

    public Map<String, String> getMetadata(URI instanceURI){
        try {
            return restTemplate.getForObject(instanceURI.resolve("/spring-cloud-heroku-metadata"), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
