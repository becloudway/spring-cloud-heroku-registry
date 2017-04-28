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

public class RemoteMetadataProvider {

    private static final Logger log = LoggerFactory.getLogger(RemoteMetadataProvider.class);

    private RestTemplate restTemplate;

    public RemoteMetadataProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, String> getMetadata(URI instanceURI){
        try {
            ResponseEntity<Map<String, String>> exchange = restTemplate.exchange(
                    instanceURI.resolve("/spring-cloud-heroku-metadata"),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, String>>() {});
            return exchange.getBody();
        } catch (Exception e) {
            log.warn("Could not get metadata from " + instanceURI.getHost(), e);
            return new HashMap<>();
        }
    }
}
