package com.xti.spring.cloud.heroku.discovery.instance;

import com.xti.spring.cloud.heroku.discovery.HerokuSpaceTopologyWatcher;
import com.xti.spring.cloud.heroku.discovery.instance.port.PortSelectorChain;
import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyDyno;
import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyV1;
import org.springframework.cloud.client.ServiceInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HerokuSpaceTopologyInstanceProvider implements HerokuInstanceProvider {

    private PortSelectorChain portSelectorChain;
    private HerokuSpaceTopologyWatcher watcher;

    public HerokuSpaceTopologyInstanceProvider(PortSelectorChain portSelectorChain, HerokuSpaceTopologyWatcher watcher) {
        this.portSelectorChain = portSelectorChain;
        this.watcher = watcher;
    }

    @Override
    public List<ServiceInstance> getServiceInstances(String appProcess) {

        List<ServiceInstance> serviceInstances = new ArrayList<>();

        final String[] herokuParts = appProcess.split("\\.");
        final String processName = herokuParts[0];
        final String appName = herokuParts[1];

        HerokuSpaceTopologyV1 topology = watcher.getTopology();

        if(topology != null){
            topology.getApps().stream()
                    .filter(app -> Objects.equals(app.getAppName(), appName))
                    .forEach(app -> app.getFormation().stream()
                            .filter(process -> Objects.equals(process.getProcessType(), processName))
                            .forEach(process -> {for (HerokuSpaceTopologyDyno dyno : process.getDynos()) {
                                ServiceInstance remoteServiceInstance = new DynoProcessServiceInstanceBuilder()
                                        .appProcess(appProcess)
                                        .host(dyno.getPrivateIp())
                                        .portSelectorChain(portSelectorChain)
                                        .build();
                                serviceInstances.add(remoteServiceInstance);
                            }
                            }
                            ));
        }

        return serviceInstances;
    }

    @Override
    public ServiceInstance getLocalServiceInstance() {
        return new DynoProcessServiceInstanceBuilder().portSelectorChain(portSelectorChain).local(true).build();
    }
}
