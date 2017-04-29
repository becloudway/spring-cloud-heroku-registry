package com.xti.spring.cloud.heroku.discovery.process;

import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyPoller;
import com.xti.spring.cloud.heroku.discovery.SpaceTopologyLoader;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class HerokuSpaceTopologyServiceProviderTest {

    @Mock
    private HerokuSpaceTopologyPoller watcher;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testGetProcesses() throws Exception {
        when(watcher.getTopology()).thenReturn(SpaceTopologyLoader.getTopology("/space-topology.json"));

        HerokuSpaceTopologyServiceProvider serviceProvider = new HerokuSpaceTopologyServiceProvider(watcher);

        List<String> processes = serviceProvider.getProcesses();

        assertThat(processes.size()).isEqualTo(3);
        assertThat(processes).contains(
                "web.spring-cloud-heroku-registry",
                "sideprocess.spring-cloud-heroku-registry",
                "web.tinyapp");
    }
}