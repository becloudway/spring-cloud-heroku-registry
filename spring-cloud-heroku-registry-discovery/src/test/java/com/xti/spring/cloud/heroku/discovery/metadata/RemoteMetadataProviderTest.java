package com.xti.spring.cloud.heroku.discovery.metadata;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class RemoteMetadataProviderTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ResponseEntity<Map<String, String>> responseEntity;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testGetMetadataOk() throws Exception {
        Map<String, String> fixture = new HashMap<>();
        fixture.put("key", "value");

        when(responseEntity.getBody()).thenReturn(fixture);

        when(restTemplate.exchange(
                any(URI.class),
                any(HttpMethod.class),
                eq((HttpEntity<?>) null),
                eq(new ParameterizedTypeReference<Map<String, String>>() {})))
                .thenReturn(responseEntity);

        RemoteMetadataProvider remoteMetadataProvider = new RemoteMetadataProvider(restTemplate);

        Map<String, String> metadata = remoteMetadataProvider.getMetadata(URI.create("http://localhost:80"));

        assertThat(metadata).containsEntry("key", "value");
    }

    @Test
    public void testGetMetadataError() throws Exception {
        when(restTemplate.exchange(
                any(URI.class),
                any(HttpMethod.class),
                eq((HttpEntity<?>) null),
                eq(new ParameterizedTypeReference<Map<String, String>>() {
                })))
                .thenThrow(new RestClientException("dummy"));

        RemoteMetadataProvider remoteMetadataProvider = new RemoteMetadataProvider(restTemplate);

        Map<String, String> metadata = remoteMetadataProvider.getMetadata(URI.create("http://localhost:80"));

        assertThat(metadata).isEmpty();
    }
}