# spring-cloud-heroku-registry [![Build Status](https://travis-ci.org/XT-i/spring-cloud-heroku-registry.svg?branch=master)](https://travis-ci.org/XT-i/spring-cloud-heroku-registry) [![Quality Gate](https://sonarqube.com/api/badges/gate?key=com.xt-i:spring-cloud-heroku-registry-parent&cache=1)](https://sonarqube.com/dashboard/index/com.xt-i:spring-cloud-heroku-registry-parent)

Spring Cloud Discovery extension based on Heroku Private Spaces Registry.

More information about Heroku DNS registry: https://devcenter.heroku.com/articles/dyno-dns-registry

Service discovery is performed by depending on the /etc/heroku/space-topology.json file which is updated within 10 seconds of topology changes.

## Usage
Depend on the following spring starter dependency:

    <dependency>
        <groupId>com.xt-i</groupId>
        <artifactId>spring-cloud-starter-heroku-registry-discovery</artifactId>
        <version>1.0.1</version>
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
    
        @Bean
        public EmbeddedServletContainerFactory servletContainer() {
            TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setScheme("http");
            connector.setPort(Integer.valueOf(springCloudHerokuPort));
            tomcat.addAdditionalTomcatConnectors(connector);
            return tomcat;
        }
    }
    
### Configuration with client loadbalancing support (RestTemplate with Ribbon)

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

    @Configuration
    @EnableDiscoveryClient
    @EnableAutoConfiguration
    public class YourConfigClass {
    
        @Value("${SPRING_CLOUD_HEROKU_PORT:8080}")
        private String springCloudHerokuPort;
    
        @Bean
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
            restTemplate.getForObject("http://processname.herokuapp/api/hello", String.class);
        }
    }

## Example

https://github.com/XT-i/spring-cloud-heroku-registry-example
