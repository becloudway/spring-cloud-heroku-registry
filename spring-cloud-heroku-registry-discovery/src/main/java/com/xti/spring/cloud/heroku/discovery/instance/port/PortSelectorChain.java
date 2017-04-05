package com.xti.spring.cloud.heroku.discovery.instance.port;

public interface PortSelectorChain {

    int getPort() throws ClusterPortNotFoundException;
}
