package com.xti.heroku.spring.cloud.discovery;

import org.springframework.cloud.client.ServiceInstance;

import java.net.URI;
import java.util.Map;

public class DynoProcessServiceInstance implements ServiceInstance {

    private String serviceId;
    private String host;
    private int port;

    /**
     * When creating a transient DynoProcessServiceInstance in the HerokuPrivateSpaceDnsDiscoveryClient the following
     * constructor needs to be used. Port used and serviceId should be known by the application.
     * Host is retrieved by using nslookup
     * @param serviceId process.app
     * @param host nslookup retrieved host
     * @param port known by HerokuPrivateSpaceDnsDiscoveryClient by service string parsing.
     */
    public DynoProcessServiceInstance(String serviceId, String host, int port) {
        this.serviceId = serviceId;
        this.host = host;
        this.port = port;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isSecure() {
        return false;
    }

    public URI getUri() {
        return URI.create(host + ":" + port);
    }

    //TODO: store this in a database/cache or API exposed by instance itself? Shouldn't be that volatile on Heroku.
    public Map<String, String> getMetadata() {
        return null;
    }
}
