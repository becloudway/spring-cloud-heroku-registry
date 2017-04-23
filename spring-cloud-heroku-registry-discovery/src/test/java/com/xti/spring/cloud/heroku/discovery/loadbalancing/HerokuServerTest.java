package com.xti.spring.cloud.heroku.discovery.loadbalancing;

import com.xti.spring.cloud.heroku.discovery.instance.RemoteDynoProcessServiceInstance;
import com.xti.spring.cloud.heroku.discovery.metadata.RemoteMetadataProvider;
import org.junit.Test;
import org.springframework.cloud.client.ServiceInstance;

import static org.assertj.core.api.Assertions.assertThat;

public class HerokuServerTest {

    @Test
    public void testConstructor(){
        final String host = "10.10.10.12";
        final int port = 8080;
        final String serviceId = "web.app";
        ServiceInstance serviceInstance = new RemoteDynoProcessServiceInstance(serviceId, host, port, new RemoteMetadataProvider());
        HerokuServer herokuServer = new HerokuServer(serviceInstance);

        assertThat(herokuServer.getHost()).isEqualTo(host);
        assertThat(herokuServer.getPort()).isEqualTo(port);
        assertThat(herokuServer.getMetaInfo().getAppName()).isEqualTo(serviceId);
        assertThat(herokuServer.getMetaInfo().getInstanceId()).isEqualTo(serviceId);
        assertThat(herokuServer.getMetaInfo().getServiceIdForDiscovery()).isEqualTo(serviceId);
    }
}