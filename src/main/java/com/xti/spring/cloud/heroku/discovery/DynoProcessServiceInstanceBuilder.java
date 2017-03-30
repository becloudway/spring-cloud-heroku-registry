package com.xti.spring.cloud.heroku.discovery;

import com.xti.spring.cloud.heroku.discovery.instance.LocalDynoProcessServiceInstance;
import com.xti.spring.cloud.heroku.discovery.instance.RemoteDynoProcessServiceInstance;
import com.xti.spring.cloud.heroku.discovery.metadata.LocallyMutableMetadataProvider;
import org.springframework.cloud.client.ServiceInstance;

public class DynoProcessServiceInstanceBuilder {

    private boolean isLocal = false;
    private String host;
    private int port = 8080;
    private String process;
    private String app;

    public DynoProcessServiceInstanceBuilder() {
    }

    public DynoProcessServiceInstanceBuilder local(boolean isLocal){
        this.isLocal = isLocal;
        return this;
    }

    public DynoProcessServiceInstanceBuilder host(String host){
        this.host = host;
        return this;
    }

    public DynoProcessServiceInstanceBuilder port(int port){
        this.port = port;
        return this;
    }

    public DynoProcessServiceInstanceBuilder process(String process){
        this.process = process;
        return this;
    }

    public DynoProcessServiceInstanceBuilder app(String app){
        this.app = app;
        return this;
    }

    /**
     * Accepts combination of process and app
     * @param processApp processName.appName
     * @return DynoProcessServiceInstanceBuilder instance
     */
    public DynoProcessServiceInstanceBuilder processApp(String processApp){
        final String[] herokuParts = processApp.split("\\.");
        this.process = herokuParts[0];
        this.app = herokuParts[1];
        return this;
    }

    public ServiceInstance build(){
        if(isLocal){
            final String herokuDnsFormationName = System.getenv("HEROKU_DNS_FORMATION_NAME");
            final String[] herokuParts = herokuDnsFormationName.split("\\.");
            final String process = herokuParts[0];
            final String app = herokuParts[1];
            final String portString = System.getenv("CLUSTER_PORT");
            final int port = Integer.parseInt(portString);
            final String host = System.getenv("HEROKU_PRIVATE_IP");

            return new LocalDynoProcessServiceInstance(process + "." + app, host, port, LocallyMutableMetadataProvider.getInstance());
        } else {
            return new RemoteDynoProcessServiceInstance(process + "." + app, host, port, LocallyMutableMetadataProvider.getInstance());
        }
    }
}
