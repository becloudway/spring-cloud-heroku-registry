package com.xti.heroku.spring.cloud.discovery.process;

import java.util.List;

public interface HerokuProcessServiceProvider {

    List<String> getProcesses();
}
