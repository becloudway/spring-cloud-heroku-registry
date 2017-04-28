package com.xti.spring.cloud.heroku.discovery.loadbalancing;

import com.netflix.loadbalancer.Server;

public class HerokuServerMetaInfo implements Server.MetaInfo {

    private String serviceId;

    public HerokuServerMetaInfo(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getAppName() {
        return serviceId;
    }

    @Override
    public String getServerGroup() {
        return null;
    }

    @Override
    public String getServiceIdForDiscovery() {
        return serviceId;
    }

    @Override
    public String getInstanceId() {
        return serviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HerokuServerMetaInfo that = (HerokuServerMetaInfo) o;

        return !(serviceId != null ? !serviceId.equals(that.serviceId) : that.serviceId != null);

    }

    @Override
    public int hashCode() {
        return serviceId != null ? serviceId.hashCode() : 0;
    }
}
