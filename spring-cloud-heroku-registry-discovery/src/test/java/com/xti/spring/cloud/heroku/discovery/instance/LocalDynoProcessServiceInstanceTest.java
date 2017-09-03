package com.xti.spring.cloud.heroku.discovery.instance;

import org.junit.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDynoProcessServiceInstanceTest {

    @Test
    public void testGetUri() throws Exception {
        final String serviceId = "web.app";
        final String host = "10.10.10.12";
        final int port = 8080;
        LocalDynoProcessServiceInstance serviceInstance = new LocalDynoProcessServiceInstance(serviceId, host, port, null);

        assertThat(serviceInstance.getUri()).isEqualTo(new URI("http://10.10.10.12:8080"));
    }
}