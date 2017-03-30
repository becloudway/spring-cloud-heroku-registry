package com.xti.spring.cloud.heroku.discovery.process;

import java.util.List;

public interface HerokuProcessServiceProvider {

    List<String> getProcesses();
}
