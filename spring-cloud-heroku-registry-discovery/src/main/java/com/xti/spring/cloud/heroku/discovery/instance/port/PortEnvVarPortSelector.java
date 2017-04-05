package com.xti.spring.cloud.heroku.discovery.instance.port;

public class PortEnvVarPortSelector implements PortSelector {

    @Override
    public Integer getPort() {
        String portString = System.getenv("PORT");
        try {
            return Integer.valueOf(portString);
        } catch (NumberFormatException e){
            return null;
        }
    }
}
