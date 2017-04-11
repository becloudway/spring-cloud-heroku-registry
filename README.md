# spring-cloud-heroku-registry [![Build Status](https://travis-ci.org/XT-i/spring-cloud-heroku-registry.svg?branch=master)](https://travis-ci.org/XT-i/spring-cloud-heroku-registry)

Spring Cloud Discovery extension based on Heroku Private Spaces DNS Registry.

More information about Heroku DNS registry: https://devcenter.heroku.com/articles/dyno-dns-registry

With the current implementation only dyno's within the same app can be clustered. Feel free to create an issue if you have ideas about how to know about other hosts ports on Heroku Private Spaces.

## Usage
Depend on the following spring starter dependency:

    <dependency>
        <groupId>com.xt-i</groupId>
        <artifactId>spring-cloud-starter-heroku-registry-discovery</artifactId>
        <version>0.1.1</version>
    </dependency>
    
Use the following Spring annotations on your configuration class

    @Configuration
    @EnableDiscoveryClient
    public class YourConfigClass {

## Example

https://github.com/XT-i/spring-cloud-heroku-registry-example
