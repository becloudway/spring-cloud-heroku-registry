package com.xti.spring.cloud.heroku.discovery.loadbalancing;

import com.netflix.client.config.IClientConfig;
import com.xti.spring.cloud.heroku.discovery.instance.RemoteDynoProcessServiceInstance;
import com.xti.spring.cloud.heroku.discovery.metadata.RemoteMetadataProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class HerokuServerListTest {

    @Mock
    private DiscoveryClient discoveryClient;

    @Mock
    private IClientConfig iClientConfig;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private HerokuServerList herokuServerList;

    @Before
    public void initObjects(){
        List<ServiceInstance> serviceInstances = new ArrayList<>();
        serviceInstances.add(new RemoteDynoProcessServiceInstance("web.app", "10.10.10.11", 8080, new RemoteMetadataProvider()));
        serviceInstances.add(new RemoteDynoProcessServiceInstance("web.app", "10.10.10.12", 8080, new RemoteMetadataProvider()));
        when(discoveryClient.getServices()).thenReturn(Collections.singletonList("web.app"));
        when(discoveryClient.getInstances("web.app")).thenReturn(serviceInstances);
        when(iClientConfig.getClientName()).thenReturn("web.app");
        herokuServerList = new HerokuServerList(discoveryClient);
    }

    @Test
    public void testGetInitialListOfServers() throws Exception {
        assertListOfServers(herokuServerList.getInitialListOfServers());
    }

    @Test
    public void testGetUpdatedListOfServers() throws Exception {
        assertListOfServers(herokuServerList.getUpdatedListOfServers());
    }

    @Test
    public void testInitWithNiwsConfig() throws Exception {
        herokuServerList.initWithNiwsConfig(iClientConfig);
        assertThat(herokuServerList.serviceId).isEqualTo("web.app");
    }

    private void assertListOfServers(List<HerokuServer> servers){
        assertThat(servers).extracting(HerokuServer::getHost).containsExactlyInAnyOrder("10.10.10.11", "10.10.10.12");
        assertThat(servers).extracting(HerokuServer::getPort).containsOnly(8080);
    }
}