package com.xti.spring.cloud.heroku.discovery.loadbalancing;

import com.netflix.loadbalancer.Server;
import org.springframework.cloud.client.ServiceInstance;

public class HerokuServer extends Server {

    private final MetaInfo metaInfo;

    HerokuServer(ServiceInstance serviceInstance){
        super(serviceInstance.getHost(), serviceInstance.getPort());

        this.metaInfo = new HerokuServerMetaInfo(serviceInstance.getServiceId());
    }

    @Override
    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        HerokuServer that = (HerokuServer) o;

        return !(metaInfo != null ? !metaInfo.equals(that.metaInfo) : that.metaInfo != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (metaInfo != null ? metaInfo.hashCode() : 0);
        return result;
    }
}
