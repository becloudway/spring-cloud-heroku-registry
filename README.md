# spring-cloud-heroku-discovery

Spring Cloud Discovery extension based on Heroku Private Spaces DNS Registry.

More information about Heroku DNS registry: https://devcenter.heroku.com/articles/dyno-dns-registry

## Example
Maven project demonstrating clustered behaviour together with Axon framework 3.0.3-SNAPSHOT.

You can find the project under the example folder.

### Prerequisites:
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

#### Axon Framework SNAPSHOT
Due to a bug in the latest release of Axon Framework install a 3.0.3-SNAPSHOT version of the axon framework in your local maven repository.
Clone the **axon-3.0.x** branch of the Axon Framework project from https://github.com/AxonFramework/AxonFramework and run mvn clean install.

## Run two simultaneous instances 

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
