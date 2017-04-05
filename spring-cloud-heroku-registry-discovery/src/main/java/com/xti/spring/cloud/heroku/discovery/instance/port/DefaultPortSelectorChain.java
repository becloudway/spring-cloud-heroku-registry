package com.xti.spring.cloud.heroku.discovery.instance.port;

public class DefaultPortSelectorChain extends AbstractPortSelectorChain {

    public DefaultPortSelectorChain() {
        super(
                new ServerPortSysPropPortSelecter(),
                new SpringCloudHerokuPortEnvVarPortSelector(),
                new PortEnvVarPortSelector());
    }
}
