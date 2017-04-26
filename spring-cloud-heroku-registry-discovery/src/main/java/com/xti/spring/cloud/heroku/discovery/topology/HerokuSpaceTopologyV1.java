package com.xti.spring.cloud.heroku.discovery.topology;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HerokuSpaceTopologyV1 {

    private String version;
    private List<HerokuSpaceTopologyApp> apps;

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("apps")
    public List<HerokuSpaceTopologyApp> getApps() {
        return apps;
    }

    @Override
    public String toString() {
        return "HerokuSpaceTopologyV1{" +
                "version='" + version + '\'' +
                ", apps=" + apps +
                '}';
    }
}
