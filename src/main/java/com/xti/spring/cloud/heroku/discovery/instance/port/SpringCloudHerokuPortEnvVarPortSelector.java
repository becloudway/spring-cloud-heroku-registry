package com.xti.spring.cloud.heroku.discovery.instance.port;

public class SpringCloudHerokuPortEnvVarPortSelector implements PortSelector {

    @Override
    public Integer getPort() {
        String portString = System.getenv("SPRING_CLOUD_HEROKU_PORT");
        try {
            return Integer.valueOf(portString);
        } catch (NumberFormatException e){
            return null;
        }
    }
}
