package com.xti.spring.cloud.heroku.discovery.topology;

import org.springframework.cloud.client.discovery.event.HeartbeatEvent;

public class TopologyChangedEvent extends HeartbeatEvent {
    /**
     * Create a new event with a source (for example a discovery client) and a value.
     * Neither parameter should be relied on to have specific content or format.
     *
     * @param source the source of the event
     * @param state  the value indicating state of the catalog
     */
    public TopologyChangedEvent(Object source, Object state) {
        super(source, state);
    }
}
