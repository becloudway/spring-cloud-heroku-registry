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
}
