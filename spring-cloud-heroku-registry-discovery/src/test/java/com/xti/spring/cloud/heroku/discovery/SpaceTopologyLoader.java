package com.xti.spring.cloud.heroku.discovery;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyV1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpaceTopologyLoader {

    private static ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    public static HerokuSpaceTopologyV1 getTopology(String resource) throws URISyntaxException, IOException {
        Path spaceTopologyFile = Paths.get(SpaceTopologyLoader.class.getResource(resource).toURI());
        return objectMapper.readValue(spaceTopologyFile.toFile(), HerokuSpaceTopologyV1.class);
    }
}
