package com.xti.spring.cloud.heroku.discovery.instance;

import com.xti.spring.cloud.heroku.discovery.metadata.LocallyMutableMetadataProvider;
import org.springframework.cloud.client.ServiceInstance;

import java.net.URI;
import java.util.Map;

public class LocalDynoProcessServiceInstance implements ServiceInstance {

    private String serviceId;
    private String host;
    private int port;
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
        return URI.create("http://" + host + ":" + port);
    }

    public Map<String, String> getMetadata() {
        return metadataProvider.getMetadata();
    }
}
