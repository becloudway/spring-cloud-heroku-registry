package com.xti.spring.cloud.heroku.discovery.topology;

import org.springframework.context.ApplicationEventPublisher;

public abstract class HerokuSpaceTopologyListener {
    private HerokuSpaceTopologyV1 topology = null;
    private ApplicationEventPublisher publisher;

    public HerokuSpaceTopologyListener(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public synchronized HerokuSpaceTopologyV1 getTopology(){
        return topology;
    }

    protected synchronized void updateTopology(HerokuSpaceTopologyV1 topology) {
        this.topology = topology;
        publisher.publishEvent(new TopologyChangedEvent(this, topology));
    }
}
