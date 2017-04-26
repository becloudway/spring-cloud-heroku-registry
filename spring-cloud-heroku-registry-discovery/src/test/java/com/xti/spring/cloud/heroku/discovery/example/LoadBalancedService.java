package com.xti.spring.cloud.heroku.discovery.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoadBalancedService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public void call(){
        restTemplate.getForObject("http://processname.herokuapp/api/hello", String.class);
    }
}