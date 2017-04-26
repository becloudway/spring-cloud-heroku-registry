package com.xti.spring.cloud.heroku.discovery.loadbalancing;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.ArrayList;
import java.util.List;

public class HerokuServerList extends AbstractServerList<HerokuServer> {

    protected String serviceId;

    DiscoveryClient discoveryClient;

    public HerokuServerList(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        this.serviceId = iClientConfig.getClientName();
    }

    @Override
    public List<HerokuServer> getInitialListOfServers() {
        return getListOfServers();
    }

    @Override
    public List<HerokuServer> getUpdatedListOfServers() {
        return getListOfServers();
    }

    private List<HerokuServer> getListOfServers(){
        List<HerokuServer> herokuServers = new ArrayList<>();

        for (String service : discoveryClient.getServices()) {
            for (ServiceInstance serviceInstance : discoveryClient.getInstances(service)) {
                herokuServers.add(new HerokuServer(serviceInstance));
            }
        }

        return herokuServers;
    }
}
