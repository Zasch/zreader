package de.carinaundzasch.zreader.jobs.feed.update;

import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@RequiredArgsConstructor
public class FeedUpdateJob implements Job {

    private final FeedUpdateJobService service;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        service.printTime();
        // read all feeds
    }
}
