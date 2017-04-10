package com.xti.spring.cloud.heroku.discovery;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "heroku.registry.discovery.enabled", matchIfMissing = true)
@EnableConfigurationProperties
@ComponentScan("com.xti.spring.cloud.heroku.discovery")
public class HerokuDnsRegistryDiscoveryClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HerokuDnsRegistryDiscoveryClient herokuDnsRegistryDiscoveryClient(ApplicationEventPublisher applicationEventPublisher){
        return new HerokuDnsRegistryDiscoveryClient(applicationEventPublisher);
    }
}
