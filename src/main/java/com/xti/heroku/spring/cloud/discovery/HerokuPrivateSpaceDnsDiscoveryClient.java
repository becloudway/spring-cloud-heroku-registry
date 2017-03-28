package com.xti.heroku.spring.cloud.discovery;

import com.xti.heroku.spring.cloud.discovery.process.HerokuFormationNameServiceProvider;
import com.xti.heroku.spring.cloud.discovery.process.HerokuProcessServiceProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Heroku Private Spaces DNS Registry based Spring Cloud DiscoveryClient implementation. Polls for changes every 10 seconds.
 */
public class HerokuPrivateSpaceDnsDiscoveryClient implements DiscoveryClient {

    private HerokuProcessServiceProvider serviceProvider;
    private Timer heartbeatTimer = new Timer();

    public HerokuPrivateSpaceDnsDiscoveryClient(ApplicationEventPublisher publisher) {
        java.security.Security.setProperty("networkaddress.cache.ttl", "0");
        serviceProvider = new HerokuFormationNameServiceProvider();
        heartbeatTimer.schedule(new HeartBeatTimerTask(publisher), 0, 10000);
    }

    public String description() {
        return "Discovery Client facilitated by Heroku Private Spaces Dyno DNS Registry";
    }

    /**
     * Enough information should be retrievable by using Heroku DNS registry environment variables.
     * @return
     */
    public ServiceInstance getLocalServiceInstance() {
        return new DynoProcessServiceInstanceBuilder().local(true).build();
    }

    /**
     * getInstance returns all available host:port combinations for the given service.
     * When service is in expected format dns nslookup can be performed using InetAddress.getAllByName(host)
     * @param processApp expecting the following format: processname.appname:port
     * @return
     */
    public List<ServiceInstance> getInstances(String processApp) {
        List<ServiceInstance> serviceInstances = new ArrayList<ServiceInstance>();
        String roundRobinDomain = processApp + ".app.localspace";
        try {
            InetAddress[] processAppHosts = InetAddress.getAllByName(roundRobinDomain);
            for (InetAddress processAppHost : processAppHosts) {
                ServiceInstance remoteServiceInstance = new DynoProcessServiceInstanceBuilder()
                        .processApp(processApp)
                        .host(processAppHost.getHostAddress())
                        .port(Integer.parseInt(System.getenv("CLUSTER_PORT")))
                        .build();
                serviceInstances.add(remoteServiceInstance);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
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
