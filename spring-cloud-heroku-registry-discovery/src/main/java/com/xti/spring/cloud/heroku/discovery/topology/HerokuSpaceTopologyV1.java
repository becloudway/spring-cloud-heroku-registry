package com.xti.spring.cloud.heroku.discovery.topology;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HerokuSpaceTopologyV1 {

    private String version;
    private List<HerokuSpaceTopologyApp> apps;

    public HerokuSpaceTopologyV1() {
    }

    public HerokuSpaceTopologyV1(String version, List<HerokuSpaceTopologyApp> apps) {
        this.version = version;
        this.apps = apps;
    }

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
