package com.xti.spring.cloud.heroku.discovery.instance.port;

public class ClusterPortNotFoundException extends RuntimeException {

    public ClusterPortNotFoundException(String message) {
        super(message);
    }

}
