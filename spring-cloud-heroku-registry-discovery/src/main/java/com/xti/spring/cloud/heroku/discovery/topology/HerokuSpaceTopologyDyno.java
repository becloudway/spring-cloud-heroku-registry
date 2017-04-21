package com.xti.spring.cloud.heroku.discovery.topology;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HerokuSpaceTopologyDyno {

    private String id;
    private Integer number;
    private String privateIp;
    private String hostname;
    private String hostEntry;

    public HerokuSpaceTopologyDyno() {
    }

    public HerokuSpaceTopologyDyno(String id, Integer number, String privateIp, String hostname, String hostEntry) {
        this.id = id;
        this.number = number;
        this.privateIp = privateIp;
        this.hostname = hostname;
        this.hostEntry = hostEntry;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("number")
    public Integer getNumber() {
        return number;
    }

    @JsonProperty("private_ip")
    public String getPrivateIp() {
        return privateIp;
    }

    @JsonProperty("hostname")
    public String getHostname() {
        return hostname;
    }

    @JsonProperty("host_entry")
    public String getHostEntry() {
        return hostEntry;
    }

    @Override
    public String toString() {
        return "HerokuSpaceTopologyDyno{" +
                "id='" + id + '\'' +
                ", number=" + number +
                ", privateIp='" + privateIp + '\'' +
                ", hostname='" + hostname + '\'' +
                ", hostEntry='" + hostEntry + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HerokuSpaceTopologyDyno that = (HerokuSpaceTopologyDyno) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (privateIp != null ? !privateIp.equals(that.privateIp) : that.privateIp != null) return false;
        if (hostname != null ? !hostname.equals(that.hostname) : that.hostname != null) return false;
        return !(hostEntry != null ? !hostEntry.equals(that.hostEntry) : that.hostEntry != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (privateIp != null ? privateIp.hashCode() : 0);
        result = 31 * result + (hostname != null ? hostname.hashCode() : 0);
        result = 31 * result + (hostEntry != null ? hostEntry.hashCode() : 0);
        return result;
    }
}
