package com.xti.spring.cloud.heroku.discovery;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "spring.cloud.heroku.discovery.enabled", matchIfMissing = true)
@EnableConfigurationProperties
public class HerokuDnsRegistryDiscoveryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HerokuDnsRegistryDiscoveryClient herokuDnsRegistryDiscoveryClient(ApplicationEventPublisher applicationEventPublisher){
        return new HerokuDnsRegistryDiscoveryClient(applicationEventPublisher);
    }
}
