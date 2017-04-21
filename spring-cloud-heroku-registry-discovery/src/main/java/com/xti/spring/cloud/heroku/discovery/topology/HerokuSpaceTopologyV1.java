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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HerokuSpaceTopologyV1 that = (HerokuSpaceTopologyV1) o;

        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        return !(apps != null ? !apps.equals(that.apps) : that.apps != null);

    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (apps != null ? apps.hashCode() : 0);
        return result;
    }
}
