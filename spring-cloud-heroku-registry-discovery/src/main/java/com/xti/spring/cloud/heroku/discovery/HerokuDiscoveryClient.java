package com.xti.spring.cloud.heroku.discovery;

import com.xti.spring.cloud.heroku.discovery.instance.HerokuInstanceProvider;
import com.xti.spring.cloud.heroku.discovery.metadata.LocallyMutableMetadataProvider;
import com.xti.spring.cloud.heroku.discovery.process.HerokuServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

/**
 * Heroku Private Spaces Registry based Spring Cloud DiscoveryClient implementation. Polls for changes every 10 seconds.
 */
public class HerokuDiscoveryClient implements DiscoveryClient {

    private static final Logger log = LoggerFactory.getLogger(HerokuDiscoveryClient.class);

    private HerokuServiceProvider serviceProvider;
    private HerokuInstanceProvider instanceProvider;
    private LocallyMutableMetadataProvider locallyMutableMetadataProvider;

    public HerokuDiscoveryClient(HerokuServiceProvider herokuProcessServiceProvider, HerokuInstanceProvider herokuInstanceProvider, LocallyMutableMetadataProvider locallyMutableMetadataProvider) {
        this.serviceProvider = herokuProcessServiceProvider;
        this.instanceProvider = herokuInstanceProvider;
        this.locallyMutableMetadataProvider = locallyMutableMetadataProvider;
        log.info("Started HerokuDiscoveryClient.");
    }

    public String description() {
        return "Discovery Client facilitated by Heroku Private Spaces Dyno Registry.";
    }

    /**
     * Get current local service instance
     * @return service instance with mutable metadata
     */
    public ServiceInstance getLocalServiceInstance() {
        return instanceProvider.getLocalServiceInstance(locallyMutableMetadataProvider);
    }

    /**
     * getInstance returns all available host combinations for the given service.
     * @param processApp expecting the following format: processname.appname
     * @return instances which correspond to the given process and app combination.
     */
    public List<ServiceInstance> getInstances(String processApp) {
        return instanceProvider.getServiceInstances(processApp);
    }

    /**
     * Return names of Heroku processes: processname.appname
     * @return Heroku processes
     */
    public List<String> getServices() {
        return serviceProvider.getProcesses();
    }
}
