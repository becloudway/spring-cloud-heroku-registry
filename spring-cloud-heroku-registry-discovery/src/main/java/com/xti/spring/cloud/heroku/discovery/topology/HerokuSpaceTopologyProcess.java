package com.xti.spring.cloud.heroku.discovery.topology;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HerokuSpaceTopologyProcess {

    private String id;
    private String processType;
    private List<HerokuSpaceTopologyDyno> dynos;

    public HerokuSpaceTopologyProcess() {
    }

    public HerokuSpaceTopologyProcess(String id, String processType, List<HerokuSpaceTopologyDyno> dynos) {
        this.id = id;
        this.processType = processType;
        this.dynos = dynos;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("process_type")
    public String getProcessType() {
        return processType;
    }

    @JsonProperty("dynos")
    public List<HerokuSpaceTopologyDyno> getDynos() {
        return dynos;
    }

    @Override
    public String toString() {
        return "HerokuSpaceTopologyProcess{" +
                "id='" + id + '\'' +
                ", processType='" + processType + '\'' +
                ", dynos=" + dynos +
                '}';
    }
}
