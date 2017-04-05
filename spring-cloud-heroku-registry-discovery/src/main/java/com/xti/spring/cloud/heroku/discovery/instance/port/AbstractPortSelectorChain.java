package com.xti.spring.cloud.heroku.discovery.instance.port;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractPortSelectorChain implements PortSelectorChain {

    private List<PortSelector> portSelectors;

    protected AbstractPortSelectorChain(List<PortSelector> portSelectors) {
        this.portSelectors = portSelectors;
    }

    protected AbstractPortSelectorChain(PortSelector... portSelectors) {
        this.portSelectors = Arrays.asList(portSelectors);
    }

    @Override
    public int getPort() throws ClusterPortNotFoundException {
        for (PortSelector portSelector : portSelectors) {
            Integer port = portSelector.getPort();
            if(port != null){
                return port;
            }
        }
        throw new ClusterPortNotFoundException("no port found in PortSelectorChain");
    }
}
