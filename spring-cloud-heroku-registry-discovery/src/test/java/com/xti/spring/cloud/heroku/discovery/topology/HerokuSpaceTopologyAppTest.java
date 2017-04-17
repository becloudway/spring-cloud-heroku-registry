package com.xti.spring.cloud.heroku.discovery.topology;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class HerokuSpaceTopologyAppTest {

    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    @Test
    public void testGetAppName() throws Exception {
        Path spaceTopologyFile = Paths.get(getClass().getResource("/space-topology.json").toURI());
        HerokuSpaceTopologyV1 herokuSpaceTopologyV1 = objectMapper.readValue(spaceTopologyFile.toFile(), HerokuSpaceTopologyV1.class);

        assertThat(herokuSpaceTopologyV1.getApps().get(0).getAppName()).isEqualTo("spring-cloud-heroku-registry");
    }
}