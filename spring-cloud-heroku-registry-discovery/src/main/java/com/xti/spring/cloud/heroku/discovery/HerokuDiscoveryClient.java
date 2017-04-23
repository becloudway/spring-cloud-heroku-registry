package com.xti.spring.cloud.heroku.discovery;

import com.xti.spring.cloud.heroku.discovery.instance.HerokuInstanceProvider;
import com.xti.spring.cloud.heroku.discovery.process.HerokuServiceProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Timer;

/**
 * Heroku Private Spaces DNS Registry based Spring Cloud DiscoveryClient implementation. Polls for changes every 10 seconds.
 */
public class HerokuDiscoveryClient implements DiscoveryClient {

    private HerokuServiceProvider serviceProvider;
    private HerokuInstanceProvider instanceProvider;
    private Timer heartbeatTimer = new Timer();

    public HerokuDiscoveryClient(ApplicationEventPublisher publisher, HerokuServiceProvider herokuProcessServiceProvider, HerokuInstanceProvider herokuInstanceProvider) {
        java.security.Security.setProperty("networkaddress.cache.ttl", "0");
        this.serviceProvider = herokuProcessServiceProvider;
        this.instanceProvider = herokuInstanceProvider;
        this.heartbeatTimer.schedule(new HeartBeatTimerTask(publisher), 0, 10000);
    }

    public String description() {
        return "Discovery Client facilitated by Heroku Private Spaces Dyno DNS Registry";
    }

    /**
     * Enough information should be retrievable by using Heroku DNS registry environment variables.
     * @return
     */
    public ServiceInstance getLocalServiceInstance() {
        return instanceProvider.getLocalServiceInstance();
    }

    /**
     * getInstance returns all available host:port combinations for the given service.
     * When service is in expected format dns nslookup can be performed using InetAddress.getAllByName(host)
     * @param processApp expecting the following format: processname.appname:port
     * @return
     */
    public List<ServiceInstance> getInstances(String processApp) {
        return instanceProvider.getServiceInstances(processApp);
    }

    /**
     * Return names of Heroku processes: processname.appname
     * TODO: Use Heroku Platform API "/formation"
     * TODO: Investigate credentials to call Heroku API from Dyno's (always using api key?)
     * Limit to same process and app combination for now.
     * @return
     */
    public List<String> getServices() {
        return serviceProvider.getProcesses();
    }
}
