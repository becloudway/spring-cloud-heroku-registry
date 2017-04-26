package com.xti.spring.cloud.heroku.discovery;

import com.xti.spring.cloud.heroku.discovery.instance.HerokuSpaceTopologyInstanceProvider;
import com.xti.spring.cloud.heroku.discovery.instance.port.DefaultPortSelectorChain;
import com.xti.spring.cloud.heroku.discovery.process.HerokuSpaceTopologyServiceProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.ApplicationEventPublisher;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class HerokuDiscoveryClientTest {

    @Mock
    private HerokuSpaceTopologyWatcher watcher;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    private HerokuDiscoveryClient discoveryClient;

    @Before
    public void initObjects() throws IOException, URISyntaxException {
        environmentVariables.set("SPRING_CLOUD_HEROKU_PORT", "1111");
        environmentVariables.set("HEROKU_DNS_FORMATION_NAME", "web.spring-cloud-heroku-registry.app.localspace");
        environmentVariables.set("HEROKU_PRIVATE_IP", "10.0.151.218");
        when(watcher.getTopology()).thenReturn(SpaceTopologyLoader.getTopology("/space-topology.json"));
        discoveryClient = new HerokuDiscoveryClient(
                applicationEventPublisher,
                new HerokuSpaceTopologyServiceProvider(watcher),
                new HerokuSpaceTopologyInstanceProvider(new DefaultPortSelectorChain(), watcher));
    }

    @Test
    public void testDescription() throws Exception {
        assertThat(discoveryClient.description()).isEqualTo("Discovery Client facilitated by Heroku Private Spaces Dyno Registry.");
    }

    @Test
    public void testGetLocalServiceInstance() throws Exception {
        ServiceInstance localServiceInstance = discoveryClient.getLocalServiceInstance();

        assertThat(localServiceInstance.getServiceId()).isEqualTo("web.spring-cloud-heroku-registry");
        assertThat(localServiceInstance.getHost()).isEqualTo("10.0.151.218");
        assertThat(localServiceInstance.getPort()).isEqualTo(1111);
        assertThat(localServiceInstance.getUri()).isEqualTo(URI.create("http://10.0.151.218:1111"));
        assertThat(localServiceInstance.isSecure()).isFalse();
    }

    @Test
    public void testGetInstances() throws Exception {
        List<ServiceInstance> instances = discoveryClient.getInstances("web.spring-cloud-heroku-registry");

        assertThat(instances).extracting(ServiceInstance::getHost).containsExactlyInAnyOrder("10.0.130.56", "10.0.151.218");
        assertThat(instances).extracting(ServiceInstance::getPort).containsOnly(1111);
        assertThat(instances).extracting(ServiceInstance::getUri).containsExactlyInAnyOrder(URI.create("http://10.0.151.218:1111"), URI.create("http://10.0.130.56:1111"));
        assertThat(instances).extracting(ServiceInstance::getServiceId).containsOnly("web.spring-cloud-heroku-registry");
        assertThat(instances).extracting(ServiceInstance::isSecure).containsOnly(false);
    }

    @Test
    public void testGetServices() throws Exception {
        List<String> services = discoveryClient.getServices();

        assertThat(services).containsExactlyInAnyOrder(
                "web.spring-cloud-heroku-registry",
                "sideprocess.spring-cloud-heroku-registry",
                "web.tinyapp");
    }
}