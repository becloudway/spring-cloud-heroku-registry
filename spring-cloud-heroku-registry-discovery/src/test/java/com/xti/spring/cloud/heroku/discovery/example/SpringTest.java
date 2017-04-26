package com.xti.spring.cloud.heroku.discovery.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestSpringConfiguration.class)
public class SpringTest {

    @Test
    public void testContext(){
    }
}
