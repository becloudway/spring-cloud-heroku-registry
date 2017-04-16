package com.xti.spring.cloud.heroku.discovery.instance;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface HerokuInstanceProvider {
    List<ServiceInstance> getServiceInstances(String service);
    ServiceInstance getLocalServiceInstance();
}
