package de.carinaundzasch.zreader.jobs;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
public class HelloJobConfig {

    @Bean
    public JobDetail helloJob() {
        return newJob(HelloJob.class).withIdentity("helloJob").storeDurably().build();
    }

    @Bean
    public Trigger triggerHelloJob(JobDetail helloJob) {
        return newTrigger()
                .forJob(helloJob)
                .withIdentity("myTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                .build();
    }
}
