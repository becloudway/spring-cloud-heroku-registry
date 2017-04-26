package com.xti.spring.cloud.heroku.discovery;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class HeartBeatTimerTaskTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testRun() throws Exception {
        HeartBeatTimerTask heartBeatTimerTask = new HeartBeatTimerTask(applicationEventPublisher);
        heartBeatTimerTask.run();

        verify(applicationEventPublisher).publishEvent(any(HeartbeatEvent.class));
    }
}