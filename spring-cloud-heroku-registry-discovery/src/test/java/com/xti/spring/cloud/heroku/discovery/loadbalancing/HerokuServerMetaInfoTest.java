package com.xti.spring.cloud.heroku.discovery.loadbalancing;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HerokuServerMetaInfoTest {

    private String serviceId = "web.app";
    private HerokuServerMetaInfo metaInfo = new HerokuServerMetaInfo(serviceId);

    @Test
    public void testGetAppName() throws Exception {
        assertThat(metaInfo.getAppName()).isEqualTo(serviceId);
    }

    @Test
    public void testGetServerGroup() throws Exception {
        assertThat(metaInfo.getServerGroup()).isNull();
    }

    @Test
    public void testGetServiceIdForDiscovery() throws Exception {
        assertThat(metaInfo.getServiceIdForDiscovery()).isEqualTo(serviceId);
    }

    @Test
    public void testGetInstanceId() throws Exception {
        assertThat(metaInfo.getInstanceId()).isEqualTo(serviceId);
    }
}