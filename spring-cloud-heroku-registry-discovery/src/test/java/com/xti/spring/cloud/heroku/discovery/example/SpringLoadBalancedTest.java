package com.xti.spring.cloud.heroku.discovery.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestSpringLoadBalancedConfiguration.class)
public class SpringLoadBalancedTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testContext(){
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        assertThat(beanDefinitionNames).contains(
                "herokuDiscoveryClient",
                "com.xti.spring.cloud.heroku.discovery.loadbalancing.HerokuRibbonAutoConfiguration");
    }
}
