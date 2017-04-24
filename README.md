# spring-cloud-heroku-registry [![Build Status](https://travis-ci.org/XT-i/spring-cloud-heroku-registry.svg?branch=master)](https://travis-ci.org/XT-i/spring-cloud-heroku-registry)

Spring Cloud Discovery extension based on Heroku Private Spaces DNS Registry.

More information about Heroku DNS registry: https://devcenter.heroku.com/articles/dyno-dns-registry

With the current implementation only dyno's within the same app can be clustered. Feel free to create an issue if you have ideas about how to know about other hosts ports on Heroku Private Spaces.

## Usage
Depend on the following spring starter dependency:

    <dependency>
        <groupId>com.xt-i</groupId>
        <artifactId>spring-cloud-starter-heroku-registry-discovery</artifactId>
        <version>0.2.0</version>
    </dependency>
    
Use a standard Heroku Java Procfile

    web: java $JAVA_WEB_OPTS -Dserver.port=$PORT -jar target/*.jar

### Configuration without client loadbalancing support  
Use the following Spring annotations on your configuration class to run the application on Heroku.
The embedded tomcat will both run on the $PORT used by the Heroku loadbalancer and the internally used 8080 port by processes within the Private Space.
This internal port can overridden by setting the SPRING_CLOUD_HEROKU_PORT environment variable.
By default a filter will run causing the /spring-cloud-heroku-metadata endpoint to be only available on the internal port.

    @Configuration
    @EnableDiscoveryClient
    public class YourConfigClass {
    
        @Value("${SPRING_CLOUD_HEROKU_PORT:8080}")
        private String springCloudHerokuPort;
    
        public EmbeddedServletContainerFactory servletContainer() {
            TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setScheme("http");
            connector.setPort(Integer.valueOf(springCloudHerokuPort));
            tomcat.addAdditionalTomcatConnectors(connector);
            return tomcat;
        }
    }
    
### Configuration with client loadbalancing support (RestTemplate with Ribbon) - From 1.0.0 (available soon)

Depend on the following additional dependencies:

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-ribbon</artifactId>
        <version>1.2.0.RELEASE</version>
    </dependency>
    
Add @EnableAutoConfiguration annotation to the configuration to register beans used by Ribbon.
Also register a @LoadBalanced RestTemplate bean.
In order to send traffic over a known port on Heroku

    @Configuration
    @EnableDiscoveryClient
    @EnableAutoConfiguration
    public class YourConfigClass {
    
        @Value("${SPRING_CLOUD_HEROKU_PORT:8080}")
        private String springCloudHerokuPort;
    
        public EmbeddedServletContainerFactory servletContainer() {
            TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setScheme("http");
            connector.setPort(Integer.valueOf(springCloudHerokuPort));
            tomcat.addAdditionalTomcatConnectors(connector);
            return tomcat;
        }
    
        @LoadBalanced
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }
    
Use the @LoadBalanced RestTemplate bean where necessary by using {processName}.{appName} as host.

    @Service
    public class MyService {
        
        @Autowired
        private RestTemplate restTemplate;
        
        public void call(){
            return restTemplate.getForObject("http://processname.herokuapp/api/hello", String.class);
        }
    }

## Example

https://github.com/XT-i/spring-cloud-heroku-registry-example
