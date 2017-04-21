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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HerokuSpaceTopologyProcess that = (HerokuSpaceTopologyProcess) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (processType != null ? !processType.equals(that.processType) : that.processType != null) return false;
        return !(dynos != null ? !dynos.equals(that.dynos) : that.dynos != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (processType != null ? processType.hashCode() : 0);
        result = 31 * result + (dynos != null ? dynos.hashCode() : 0);
        return result;
    }
}
