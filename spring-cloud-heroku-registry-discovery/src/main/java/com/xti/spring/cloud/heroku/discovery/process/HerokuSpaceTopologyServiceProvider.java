package com.xti.spring.cloud.heroku.discovery.process;

import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyListener;
import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyApp;
import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyProcess;
import com.xti.spring.cloud.heroku.discovery.topology.HerokuSpaceTopologyV1;

import java.util.ArrayList;
import java.util.List;

public class HerokuSpaceTopologyServiceProvider implements HerokuServiceProvider {

    private HerokuSpaceTopologyListener listener;

    public HerokuSpaceTopologyServiceProvider(HerokuSpaceTopologyListener listener) {
        this.listener = listener;
    }

    @Override
    public List<String> getProcesses() {
        HerokuSpaceTopologyV1 topology = listener.getTopology();
        List<String> processes = new ArrayList<>();

        if(topology != null){
            for (HerokuSpaceTopologyApp app : topology.getApps()) {
                String appName = app.getAppName();

                if(appName != null){
                    for (HerokuSpaceTopologyProcess process : app.getFormation()) {
                        processes.add(process.getProcessType() + "." + appName);
                    }
                }

            }
        }

        return processes;
    }
}
