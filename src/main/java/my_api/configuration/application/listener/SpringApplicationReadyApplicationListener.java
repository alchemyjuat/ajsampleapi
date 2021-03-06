package my_api.configuration.application.listener;
// **********************************************************************
// Code generated by AlchemyJ Compiler.
// PLEASE DO NOT EDIT THIS FILE.
// **********************************************************************

import lombok.extern.slf4j.Slf4j;
import my_api.util.SpringInitializerLogger;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.Ordered;

@Slf4j
public class SpringApplicationReadyApplicationListener
    implements SmartApplicationListener, Ordered {

  @Override
  public void onApplicationEvent(ApplicationEvent event) {
    SpringInitializerLogger.getInstance().afterSpringEnvInitializer();
    log.info("Spring Application Ready...");
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }

  @Override
  public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
    return ApplicationEnvironmentPreparedEvent.class.isAssignableFrom(eventType);
  }

  @Override
  public boolean supportsSourceType(Class<?> sourceType) {
    return true;
  }
}
