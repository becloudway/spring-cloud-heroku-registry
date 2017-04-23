package com.xti.spring.cloud.heroku.discovery;

import com.xti.spring.cloud.heroku.discovery.instance.HerokuInstanceProvider;
import com.xti.spring.cloud.heroku.discovery.instance.HerokuSpaceTopologyInstanceProvider;
import com.xti.spring.cloud.heroku.discovery.instance.port.ClusterPortNotFoundException;
import com.xti.spring.cloud.heroku.discovery.instance.port.DefaultPortSelectorChain;
import com.xti.spring.cloud.heroku.discovery.instance.port.PortSelectorChain;
import com.xti.spring.cloud.heroku.discovery.metadata.MetadataFilter;
import com.xti.spring.cloud.heroku.discovery.process.HerokuServiceProvider;
import com.xti.spring.cloud.heroku.discovery.process.HerokuSpaceTopologyServiceProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
    public PortSelectorChain portSelectorChain(){
        return new DefaultPortSelectorChain();
    }

    @Bean
    @ConditionalOnMissingBean
    public HerokuInstanceProvider getHerokuInstanceProvider(HerokuSpaceTopologyWatcher watcher, PortSelectorChain portSelectorChain){
        return new HerokuSpaceTopologyInstanceProvider(portSelectorChain, watcher);
    }

    @Bean
    @ConditionalOnMissingBean
    public HerokuDnsRegistryDiscoveryClient herokuDnsRegistryDiscoveryClient(ApplicationEventPublisher applicationEventPublisher, HerokuServiceProvider herokuServiceProvider, HerokuInstanceProvider herokuInstanceProvider){
        return new HerokuDnsRegistryDiscoveryClient(applicationEventPublisher, herokuServiceProvider, herokuInstanceProvider);
    }

    @Bean
    @ConditionalOnMissingBean()
    public MetadataFilter metadataFilter(PortSelectorChain portSelectorChain){
        try {
            return new MetadataFilter(portSelectorChain.getPort());
        } catch (ClusterPortNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean metadataFilterRegistration(MetadataFilter metadataFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(metadataFilter);
        registration.addUrlPatterns("/spring-cloud-heroku-metadata");
        registration.setName("metadatafilter");
        registration.setOrder(1);
        return registration;
    }
}
