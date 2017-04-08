package com.xti.spring.cloud.heroku.discovery.instance.port;

public class DefaultPortSelectorChain extends AbstractPortSelectorChain {

    public DefaultPortSelectorChain() {
        super(
                new SpringCloudHerokuPortEnvVarPortSelector(),
                new ServerPortSysPropPortSelecter(),
                new PortEnvVarPortSelector());
    }
}
