package com.xti.spring.cloud.heroku.discovery.instance;

import com.xti.spring.cloud.heroku.discovery.metadata.LocallyMutableMetadataProvider;

import java.util.Map;

public class LocalDynoProcessServiceInstance extends AbstractDynoProcessServiceInstance {

    private LocallyMutableMetadataProvider metadataProvider;

    /**
     * When creating a transient DynoProcessServiceInstance in the HerokuPrivateSpaceDnsDiscoveryClient the following
     * constructor needs to be used. Port used and serviceId should be known by the application.
     * Host is retrieved by using nslookup
     * @param serviceId process.app
     * @param host nslookup retrieved host
     * @param port known by HerokuPrivateSpaceDnsDiscoveryClient by service string parsing.
     * @param metadataProvider provider used to get local metadata with mutable access.
     */
    public LocalDynoProcessServiceInstance(String serviceId, String host, int port, LocallyMutableMetadataProvider metadataProvider) {
        super(serviceId, host, port);
        this.metadataProvider = metadataProvider;
    }

    public Map<String, String> getMetadata() {
        return metadataProvider.getMetadata();
    }
}
