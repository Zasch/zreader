package de.carinaundzasch.zreader.jobs.feed.update;

import lombok.Getter;
import lombok.Setter;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
@ConfigurationProperties(ignoreInvalidFields = true, prefix = "de.carinaundzasch.jobs.feed.update")
public class FeedUpdateJobConfig {

    @Getter
    @Setter
    private String cron;

    @Bean
    public JobDetail feedUpdateJob() {
        return newJob(FeedUpdateJob.class).withIdentity("feedUpdateJob").storeDurably().build();
    }

    @Bean
    public Trigger triggerFeedUpdateJob(JobDetail feedUpdateJob) {
        return newTrigger()
                .forJob(feedUpdateJob)
                .startNow()
                .withIdentity("feedUpdate")
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
    }
}
