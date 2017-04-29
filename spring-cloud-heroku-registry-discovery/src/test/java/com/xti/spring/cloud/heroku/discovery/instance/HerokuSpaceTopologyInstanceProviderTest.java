package com.xti.spring.cloud.heroku.discovery.instance;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyPoller;
import com.xti.spring.cloud.heroku.discovery.SpaceTopologyLoader;
import com.xti.spring.cloud.heroku.discovery.instance.port.PortSelectorChain;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class HerokuSpaceTopologyInstanceProviderTest {

    @Mock
    PortSelectorChain portSelectorChain;

    @Mock
    HerokuSpaceTopologyPoller watcher;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    @Test
    public void testGetServiceInstances() throws Exception {
        when(watcher.getTopology()).thenReturn(SpaceTopologyLoader.getTopology("/space-topology.json"));
        when(portSelectorChain.getPort()).thenReturn(8080);

        HerokuSpaceTopologyInstanceProvider instanceProvider = new HerokuSpaceTopologyInstanceProvider(portSelectorChain, watcher);

        List<ServiceInstance> serviceInstances = instanceProvider.getServiceInstances("web.spring-cloud-heroku-registry");

        assertThat(serviceInstances.size()).isEqualTo(2);
        assertThat(serviceInstances.get(0).getServiceId()).isEqualTo("web.spring-cloud-heroku-registry");
        assertThat(serviceInstances.get(0).getPort()).isEqualTo(8080);
        assertThat(serviceInstances.get(1).getServiceId()).isEqualTo("web.spring-cloud-heroku-registry");
        assertThat(serviceInstances.get(1).getPort()).isEqualTo(8080);
    }
}