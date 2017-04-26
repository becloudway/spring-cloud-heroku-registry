package com.xti.spring.cloud.heroku.discovery.instance;

import org.springframework.cloud.client.ServiceInstance;

import java.net.URI;

public abstract class AbstractDynoProcessServiceInstance implements ServiceInstance {

    private String serviceId;
    private String host;
    private int port;

    public AbstractDynoProcessServiceInstance(String serviceId, String host, int port) {
        this.serviceId = serviceId;
        this.host = host;
        this.port = port;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    public URI getUri() {
        return URI.create("http://" + host + ":" + port);
    }


}
