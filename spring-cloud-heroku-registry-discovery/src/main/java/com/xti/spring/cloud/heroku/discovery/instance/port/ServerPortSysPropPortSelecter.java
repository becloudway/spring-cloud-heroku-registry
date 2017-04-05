package com.xti.spring.cloud.heroku.discovery.instance.port;

public class ServerPortSysPropPortSelecter implements PortSelector {

    @Override
    public Integer getPort() {
        String portString = System.getProperty("server.port");
        try {
            return Integer.valueOf(portString);
        } catch (NumberFormatException e){
            return null;
        }
    }
}
