package com.xti.spring.cloud.heroku.discovery.loadbalancing;

import com.netflix.client.IClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConditionalOnClass(IClient.class)
@ConditionalOnBean(SpringClientFactory.class)
@ConditionalOnProperty(value = "heroku.discovery.ribbon.enabled", matchIfMissing = true)
@AutoConfigureAfter(RibbonAutoConfiguration.class)
@RibbonClients(defaultConfiguration = HerokuRibbonClientConfiguration.class)
public class HerokuRibbonAutoConfiguration {

    public HerokuRibbonAutoConfiguration() {
    }
}
