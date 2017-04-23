package com.xti.spring.cloud.heroku.discovery.loadbalancing;

import com.netflix.loadbalancer.Server;
import org.springframework.cloud.client.ServiceInstance;

public class HerokuServer extends Server {

    private final MetaInfo metaInfo;

    HerokuServer(ServiceInstance serviceInstance){
        super(serviceInstance.getHost(), serviceInstance.getPort());

        this.metaInfo = new MetaInfo() {
            @Override
            public String getAppName() {
                return serviceInstance.getServiceId();
            }

            @Override
            public String getServerGroup() {
                return null;
            }

            @Override
            public String getServiceIdForDiscovery() {
                return serviceInstance.getServiceId();
            }

            @Override
            public String getInstanceId() {
                return serviceInstance.getServiceId();
            }
        };
    }

    @Override
    public MetaInfo getMetaInfo() {
        return metaInfo;
    }
}
