package com.xti.heroku.spring.cloud.discovery;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

public class HerokuPrivateSpaceDnsDiscoveryClient implements DiscoveryClient {

    public String description() {
        return "Discovery Client facilitated by Heroku Private Spaces Dyno DNS Registry";
    }

    /**
     * Enough information should be retrievable by using Heroku DNS registry environment variables.
     * @return
     */
    public ServiceInstance getLocalServiceInstance() {
        return null;
    }

    /**
     * getInstance returns all available host:port combinations for the given service.
     * When service is in expected format dns nslookup can be performed using InetAddress.getAllByName(host)
     * @param service expecting the following format: processname.appname:port
     * @return
     */
    public List<ServiceInstance> getInstances(String service) {
        return null;
    }

    /**
     * Return names of Heroku processes: processname.appname
     * TODO: Use Heroku Platform API "/formation"
     * TODO: Investigate credentials to call Heroku API from Dyno's (always using api key?)
     * @return
     */
    public List<String> getServices() {
        throw new UnsupportedOperationException();
    }
}
