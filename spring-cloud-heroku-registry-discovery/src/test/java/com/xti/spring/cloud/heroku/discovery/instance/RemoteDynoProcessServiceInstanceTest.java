package com.xti.spring.cloud.heroku.discovery.instance;

import com.xti.spring.cloud.heroku.discovery.metadata.RemoteMetadataProvider;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class RemoteDynoProcessServiceInstanceTest {

    @Test
    public void testGetUri() throws Exception {
        final String serviceId = "web.app";
        final String host = "10.10.10.12";
        final int port = 8080;
        RemoteDynoProcessServiceInstance serviceInstance = new RemoteDynoProcessServiceInstance(serviceId, host, port, new RemoteMetadataProvider(new RestTemplate()));

        assertThat(serviceInstance.getUri()).isEqualTo(new URI("http://10.10.10.12:8080"));
    }
}