package com.baza.firmy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync(proxyTargetClass = true)
@Configuration
public class AsyncConfiguration {

  @Bean(name = "pobierzOdpisAktualny")
  public ThreadPoolTaskExecutor pobierzOdpisAktualny() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    // Core and max pool size
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(20);

    // How many tasks can wait in queue before new threads are spawned
    executor.setQueueCapacity(500);

    // Optional: Thread idle timeout before shrinking back to core size
    executor.setKeepAliveSeconds(60);

    // Thread naming for logs and debugging
    executor.setThreadNamePrefix("pobierz-odpis-aktualny-");

    // Rejection policy: what to do when queue is full and all threads are busy
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    // Alternatives: AbortPolicy (throws exception), DiscardPolicy, DiscardOldestPolicy

    // Graceful shutdown behavior
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(300); // Wait up to 5 minutes for tasks to finish - same value as used in PostingScheduledJobProcessor

    executor.initialize(); // Don't forget!
    return executor;
  }

  @Bean(name = "pobierzDaneZRaportu")
  public ThreadPoolTaskExecutor pobierzDaneZRaportu() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    // Core and max pool size
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(20);

    // How many tasks can wait in queue before new threads are spawned
    executor.setQueueCapacity(500);

    // Optional: Thread idle timeout before shrinking back to core size
    executor.setKeepAliveSeconds(60);

    // Thread naming for logs and debugging
    executor.setThreadNamePrefix("pobierz-dane-z-raportu-");

    // Rejection policy: what to do when queue is full and all threads are busy
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    // Alternatives: AbortPolicy (throws exception), DiscardPolicy, DiscardOldestPolicy

    // Graceful shutdown behavior
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(300); // Wait up to 5 minutes for tasks to finish - same value as used in PostingScheduledJobProcessor

    executor.initialize(); // Don't forget!
    return executor;
  }

  @Bean(name = "stworzListeFirmCrmDlaKlienta")
  public ThreadPoolTaskExecutor stworzListeFirmCrmDlaKlienta() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    // Core and max pool size
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(20);

    // How many tasks can wait in queue before new threads are spawned
    executor.setQueueCapacity(500);

    // Optional: Thread idle timeout before shrinking back to core size
    executor.setKeepAliveSeconds(60);

    // Thread naming for logs and debugging
    executor.setThreadNamePrefix("stworz-liste-firm-crm-dla-klienta-");

    // Rejection policy: what to do when queue is full and all threads are busy
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    // Alternatives: AbortPolicy (throws exception), DiscardPolicy, DiscardOldestPolicy

    // Graceful shutdown behavior
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(300); // Wait up to 5 minutes for tasks to finish - same value as used in PostingScheduledJobProcessor

    executor.initialize(); // Don't forget!
    return executor;
  }
}
