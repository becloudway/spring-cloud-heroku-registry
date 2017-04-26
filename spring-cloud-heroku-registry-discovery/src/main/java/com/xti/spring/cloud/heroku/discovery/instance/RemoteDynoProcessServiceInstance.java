package com.xti.spring.cloud.heroku.discovery.instance;

import com.xti.spring.cloud.heroku.discovery.metadata.RemoteMetadataProvider;

import java.util.Map;

public class RemoteDynoProcessServiceInstance extends AbstractDynoProcessServiceInstance {

    private RemoteMetadataProvider metadataProvider;

    /**
     * When creating a transient DynoProcessServiceInstance in the HerokuPrivateSpaceDnsDiscoveryClient the following
     * constructor needs to be used. Port used and serviceId should be known by the application.
     * Host is retrieved by using nslookup
     * @param serviceId process.app
     * @param host nslookup retrieved host
     * @param port known by HerokuPrivateSpaceDnsDiscoveryClient by service string parsing.
     * @param metadataProvider provider used to get remote metadata with read only access.
     */
    public RemoteDynoProcessServiceInstance(String serviceId, String host, int port, RemoteMetadataProvider metadataProvider) {
        super(serviceId, host, port);
        this.metadataProvider = metadataProvider;
    }

    public Map<String, String> getMetadata() {
        return metadataProvider.getMetadata(getUri());
    }
}
