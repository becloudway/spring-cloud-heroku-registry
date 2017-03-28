package com.xti.heroku.spring.cloud.discovery.instance;

import com.xti.heroku.spring.cloud.discovery.metadata.MetadataProvider;
import org.springframework.cloud.client.ServiceInstance;

import java.net.URI;
import java.util.Map;

public class RemoteDynoProcessServiceInstance implements ServiceInstance {

    private String serviceId;
    private String host;
    private int port;
    private MetadataProvider metadataProvider;

    /**
     * When creating a transient DynoProcessServiceInstance in the HerokuPrivateSpaceDnsDiscoveryClient the following
     * constructor needs to be used. Port used and serviceId should be known by the application.
     * Host is retrieved by using nslookup
     * @param serviceId process.app
     * @param host nslookup retrieved host
     * @param port known by HerokuPrivateSpaceDnsDiscoveryClient by service string parsing.
     * @param metadataProvider provider used to get remote metadata with read only access.
     */
    public RemoteDynoProcessServiceInstance(String serviceId, String host, int port, MetadataProvider metadataProvider) {
        this.serviceId = serviceId;
        this.host = host;
        this.port = port;
        this.metadataProvider = metadataProvider;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isSecure() {
        return false;
    }

    public URI getUri() {
        return URI.create(host + ":" + port);
    }

    //TODO: store this in a database/cache or API exposed by instance itself? Shouldn't be that volatile on Heroku.
    public Map<String, String> getMetadata() {
        return metadataProvider.getMetadata(getUri());
    }
}
