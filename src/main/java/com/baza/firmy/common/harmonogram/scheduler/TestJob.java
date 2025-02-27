package com.baza.firmy.common.harmonogram.scheduler;


/**
 * Klasa jest reprezentacją testowego zadania do wykonania w Quartz.
 */
class TestJob extends BazowyJob<TestSchedulerService> {

  public TestJob(TestSchedulerService schedulerService) {
    super(schedulerService);
  }
}
