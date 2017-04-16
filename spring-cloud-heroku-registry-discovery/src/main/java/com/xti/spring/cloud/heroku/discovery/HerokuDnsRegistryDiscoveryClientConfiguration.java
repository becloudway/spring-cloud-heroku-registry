package com.xti.spring.cloud.heroku.discovery;

import com.xti.spring.cloud.heroku.discovery.instance.HerokuInstanceProvider;
import com.xti.spring.cloud.heroku.discovery.instance.HerokuSpaceTopologyInstanceProvider;
import com.xti.spring.cloud.heroku.discovery.instance.port.DefaultPortSelectorChain;
import com.xti.spring.cloud.heroku.discovery.process.HerokuServiceProvider;
import com.xti.spring.cloud.heroku.discovery.process.HerokuSpaceTopologyServiceProvider;
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
    public HerokuServiceProvider getHerokuServiceProvider(HerokuSpaceTopologyWatcher watcher){
        return new HerokuSpaceTopologyServiceProvider(watcher);
    }

    @Bean
    @ConditionalOnMissingBean
    public HerokuInstanceProvider getHerokuInstanceProvider(HerokuSpaceTopologyWatcher watcher){
        return new HerokuSpaceTopologyInstanceProvider(new DefaultPortSelectorChain(), watcher);
    }

    @Bean
    @ConditionalOnMissingBean
    public HerokuDnsRegistryDiscoveryClient herokuDnsRegistryDiscoveryClient(ApplicationEventPublisher applicationEventPublisher, HerokuServiceProvider herokuServiceProvider, HerokuInstanceProvider herokuInstanceProvider){
        return new HerokuDnsRegistryDiscoveryClient(applicationEventPublisher, herokuServiceProvider, herokuInstanceProvider);
    }
}
