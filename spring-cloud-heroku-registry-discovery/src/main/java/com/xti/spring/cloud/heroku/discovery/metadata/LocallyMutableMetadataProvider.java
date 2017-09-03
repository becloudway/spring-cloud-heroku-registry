package com.xti.spring.cloud.heroku.discovery.metadata;

import com.xti.spring.cloud.heroku.discovery.instance.HerokuInstanceProvider;
import com.xti.spring.cloud.heroku.discovery.process.HerokuServiceProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

public class LocallyMutableMetadataProvider {

    private Map<String, String> metadata;

    public LocallyMutableMetadataProvider(RestTemplate restTemplate, HerokuServiceProvider serviceProvider, HerokuInstanceProvider instanceProvider){
        metadata = new ObservableConcurrentHashMap<>(() -> {
            ServiceInstance localServiceInstance = instanceProvider.getLocalServiceInstance(this);
            for (String service : serviceProvider.getProcesses()) {
                instanceProvider.getServiceInstances(service).stream()
                        .filter(serviceInstance ->
                                !Objects.equals(localServiceInstance.getServiceId(), serviceInstance.getHost()) && !Objects.equals(localServiceInstance.getHost(), serviceInstance.getHost()))
                        .forEach(serviceInstance ->
                            restTemplate.exchange(serviceInstance.getUri().resolve("/spring-cloud-heroku-metadata/notify"), HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, String>>() {
                        }));
            }
        });
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }
}
