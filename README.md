# spring-cloud-heroku-discovery [![Build Status](https://travis-ci.org/XT-i/spring-cloud-heroku-discovery.svg?branch=master)](https://travis-ci.org/XT-i/spring-cloud-heroku-discovery)

Spring Cloud Discovery extension based on Heroku Private Spaces DNS Registry.

More information about Heroku DNS registry: https://devcenter.heroku.com/articles/dyno-dns-registry

With the current implementation only dyno's within the same app can be clustered. Feel free to create an issue if you have ideas about how to know about other hosts ports on Heroku Private Spaces.

## Usage
Depend on the following spring starter dependency:

    <dependency>
        <groupId>com.xt-i</groupId>
        <artifactId>spring-cloud-starter-heroku-registry-discovery</artifactId>
        <version>0.1</version>
    </dependency>
    
Use the following Spring annotations on your configuration class

    @Configuration
    @EnableDiscoveryClient
    @EnableAutoConfiguration
    public class YourConfigClass {

## Example
Maven project demonstrating clustered behaviour together with Axon framework 3.0.3.

You can find the example project in the example folder.

### Prerequisites (Local Setup):
#### Additional loopback address
macOS:
sudo ifconfig lo0 alias 127.0.0.2 up

#### Local DNS server
Dnsmasq example:

/usr/local/etc/dnsmasq.conf:
```
addn-hosts=/etc/hosts.web.cloudapp.app.localspace
```
/etc/hosts.web.cloudapp.app.localspace:
```
127.0.0.1 web.cloudapp.app.localspace
127.0.0.2 web.cloudapp.app.localspace
```
### Run two simultaneous instances 

In order to run the example locally set the following environment variables and system properties which are also available on Heroku:

HEROKU_DNS_FORMATION_NAME: web.cloudapp.app.localspace

HEROKU_PRIVATE_IP: 127.0.0.1 and 127.0.0.2 for the first and second instance respectively.

SPRING_CLOUD_HEROKU_PORT or PORT or server.port system property: 8080 

Use 127.0.0.1 and 127.0.0.2 as the server.address system property for the first and second instance respectively.

Send the following payload with content-type application/json to the NoteController exposed API at POST http://127.0.0.1:8080/note or http://127.0.0.2:8080/note:
```
{
	"count": 1000,
	"text": "someNote"
}
```

You should see that the generated id's of the notes are split between the two instances.
