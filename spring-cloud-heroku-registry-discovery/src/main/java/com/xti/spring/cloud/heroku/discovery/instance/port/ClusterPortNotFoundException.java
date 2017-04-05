package com.xti.spring.cloud.heroku.discovery.instance.port;

public class ClusterPortNotFoundException extends Exception {

    public ClusterPortNotFoundException() {
    }

    public ClusterPortNotFoundException(String message) {
        super(message);
    }

    public ClusterPortNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClusterPortNotFoundException(Throwable cause) {
        super(cause);
    }

    public ClusterPortNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
