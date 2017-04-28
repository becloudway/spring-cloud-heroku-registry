package com.xti.spring.cloud.heroku.discovery.loadbalancing;

import com.netflix.client.IClient;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.IClientConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.loadbalancer.ServerList;
import com.xti.spring.cloud.heroku.discovery.HerokuDiscoveryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * HerokuRibbonClientConfiguration based on spring-cloud/spring-cloud-cloudfoundry CloudFoundryRibbonClientConfiguration
 */
@Configuration
@ConditionalOnClass(IClient.class)
public class HerokuRibbonClientConfiguration {

    protected static final String DEFAULT_NAMESPACE = "ribbon";
    protected static final String VALUE_NOT_SET = "__not__set__";

    @Value("${ribbon.client.name}")
    private String serviceId;

    @Bean
    @ConditionalOnMissingBean
    public ServerList<?> ribbonServerList(HerokuDiscoveryClient herokuDiscoveryClient,
                                          IClientConfig config) {
        HerokuServerList herokuServerList = new HerokuServerList(herokuDiscoveryClient);
        herokuServerList.initWithNiwsConfig(config);
        return herokuServerList;
    }

    @PostConstruct
    public void postConstruct() {
        setProp(this.serviceId,
                CommonClientConfigKey.DeploymentContextBasedVipAddresses.key(),
                this.serviceId);
        setProp(this.serviceId, CommonClientConfigKey.EnableZoneAffinity.key(), "true");
    }

    protected void setProp(String serviceId, String suffix, String value) {
        String key = getKey(serviceId, suffix);
        DynamicStringProperty property = getProperty(key);
        if (property.get().equals(VALUE_NOT_SET)) {
            ConfigurationManager.getConfigInstance().setProperty(key, value);
        }
    }

    protected DynamicStringProperty getProperty(String key) {
        return DynamicPropertyFactory.getInstance().getStringProperty(key, VALUE_NOT_SET);
    }

    protected String getKey(String serviceId, String suffix) {
        return serviceId + "." + DEFAULT_NAMESPACE + "." + suffix;
    }
}
