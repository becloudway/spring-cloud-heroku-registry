package com.xti.spring.cloud.heroku.discovery.instance;

import com.xti.spring.cloud.heroku.discovery.instance.port.PortSelectorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class HerokuDnsInstanceProvider implements HerokuInstanceProvider {

    private static final Logger log = LoggerFactory.getLogger(HerokuDnsInstanceProvider.class);

    private PortSelectorChain portSelectorChain;

    public HerokuDnsInstanceProvider(PortSelectorChain portSelectorChain) {
        this.portSelectorChain = portSelectorChain;
    }

    @Override
    public List<ServiceInstance> getServiceInstances(String appProcess) {
        List<ServiceInstance> serviceInstances = new ArrayList<>();
        String roundRobinDomain = appProcess + ".app.localspace";
        try {
            InetAddress[] processAppHosts = InetAddress.getAllByName(roundRobinDomain);
            for (InetAddress processAppHost : processAppHosts) {
                ServiceInstance remoteServiceInstance = new DynoProcessServiceInstanceBuilder()
                        .appProcess(appProcess)
                        .host(processAppHost.getHostAddress())
                        .portSelectorChain(portSelectorChain)
                        .build();
                serviceInstances.add(remoteServiceInstance);
            }
        } catch (UnknownHostException e) {
            log.warn("Unknown Host exception when getting IP addresses for process.", e);
        }
        return serviceInstances;
    }

    @Override
    public ServiceInstance getLocalServiceInstance() {
        return new DynoProcessServiceInstanceBuilder().portSelectorChain(portSelectorChain).local(true).build();
    }
}
