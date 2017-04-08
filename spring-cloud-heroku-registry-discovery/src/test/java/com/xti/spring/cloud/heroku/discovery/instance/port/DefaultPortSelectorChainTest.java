package com.xti.spring.cloud.heroku.discovery.instance.port;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ClearSystemProperties;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DefaultPortSelectorChainTest {

    @Rule
    public final ClearSystemProperties clearedServerPort = new ClearSystemProperties("server.port");

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Test
    public void springCloudHerokuPortEnvTest() throws ClusterPortNotFoundException {
        environmentVariables.set("SPRING_CLOUD_HEROKU_PORT", "1111");
        PortSelectorChain portSelectorChain = new DefaultPortSelectorChain();
        assertThat(portSelectorChain.getPort()).isEqualTo(1111);
    }

    @Test
    public void serverPortSysPropPortTest() throws ClusterPortNotFoundException {
        System.setProperty("server.port", "1112");
        PortSelectorChain portSelectorChain = new DefaultPortSelectorChain();
        assertThat(portSelectorChain.getPort()).isEqualTo(1112);
    }

    @Test
    public void portEnvVarPortTest() throws ClusterPortNotFoundException {
        environmentVariables.set("PORT", "1113");
        PortSelectorChain portSelectorChain = new DefaultPortSelectorChain();
        assertThat(portSelectorChain.getPort()).isEqualTo(1113);
    }

    @Test
    public void PortOrderPrioTest() throws ClusterPortNotFoundException {
        environmentVariables.set("SPRING_CLOUD_HEROKU_PORT", "1111");
        System.setProperty("server.port", "1112");
        environmentVariables.set("PORT", "1113");
        PortSelectorChain portSelectorChain = new DefaultPortSelectorChain();
        assertThat(portSelectorChain.getPort()).isEqualTo(1111);
    }

    @Test
    public void portOrderPrio2Test() throws ClusterPortNotFoundException {
        System.setProperty("server.port", "1112");
        environmentVariables.set("PORT", "1113");
        PortSelectorChain portSelectorChain = new DefaultPortSelectorChain();
        assertThat(portSelectorChain.getPort()).isEqualTo(1112);
    }

    @Test
    public void noPortTest() throws ClusterPortNotFoundException {
        PortSelectorChain portSelectorChain = new DefaultPortSelectorChain();
        assertThatThrownBy(portSelectorChain::getPort)
                .isInstanceOf(ClusterPortNotFoundException.class)
                .hasMessage("no port found in PortSelectorChain");
    }
}
