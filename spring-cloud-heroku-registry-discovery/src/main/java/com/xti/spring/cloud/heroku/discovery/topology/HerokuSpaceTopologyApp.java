package com.xti.spring.cloud.heroku.discovery.topology;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HerokuSpaceTopologyApp {

    private String id;
    private List<String> domains;
    private List<HerokuSpaceTopologyProcess> formation;

    public HerokuSpaceTopologyApp() {
    }

    public HerokuSpaceTopologyApp(String id, List<String> domains, List<HerokuSpaceTopologyProcess> formation) {
        this.id = id;
        this.domains = domains;
        this.formation = formation;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("domains")
    public List<String> getDomains() {
        return domains;
    }

    @JsonProperty("formation")
    public List<HerokuSpaceTopologyProcess> getFormation() {
        return formation;
    }

    @Override
    public String toString() {
        return "HerokuSpaceTopologyApp{" +
                "id='" + id + '\'' +
                ", domains=" + domains +
                ", formation=" + formation +
                '}';
    }
}
