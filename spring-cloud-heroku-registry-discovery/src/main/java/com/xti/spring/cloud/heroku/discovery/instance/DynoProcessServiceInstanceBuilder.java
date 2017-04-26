package com.xti.spring.cloud.heroku.discovery.instance;

import com.xti.spring.cloud.heroku.discovery.instance.port.PortSelectorChain;
import com.xti.spring.cloud.heroku.discovery.metadata.LocallyMutableMetadataProvider;
import com.xti.spring.cloud.heroku.discovery.metadata.RemoteMetadataProvider;
import org.springframework.cloud.client.ServiceInstance;

public class DynoProcessServiceInstanceBuilder {

    private boolean isLocal = false;
    private String host;
    private PortSelectorChain portSelectorChain;
    private String process;
    private String app;

    public DynoProcessServiceInstanceBuilder local(boolean isLocal){
        this.isLocal = isLocal;
        return this;
    }

    public DynoProcessServiceInstanceBuilder host(String host){
        this.host = host;
        return this;
    }

    public DynoProcessServiceInstanceBuilder portSelectorChain(PortSelectorChain portSelectorChain){
        this.portSelectorChain = portSelectorChain;
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
     * @param appProcess processName.appName
     * @return DynoProcessServiceInstanceBuilder instance
     */
    public DynoProcessServiceInstanceBuilder appProcess(String appProcess){
        final String[] herokuParts = appProcess.split("\\.");
        process(herokuParts[0]);
        app(herokuParts[1]);
        return this;
    }

    public ServiceInstance build(){
        if(isLocal){
            final String herokuDnsFormationName = System.getenv("HEROKU_DNS_FORMATION_NAME");
            final String[] herokuParts = herokuDnsFormationName.split("\\.");
            final String localProcess = herokuParts[0];
            final String localApp = herokuParts[1];
            final String localHost = System.getenv("HEROKU_PRIVATE_IP");

            return new LocalDynoProcessServiceInstance(localProcess + "." + localApp, localHost, portSelectorChain.getPort(), LocallyMutableMetadataProvider.getInstance());

        } else {
            return new RemoteDynoProcessServiceInstance(process + "." + app, host, portSelectorChain.getPort(), new RemoteMetadataProvider());
        }
    }
}
