package de.carinaundzasch.zreader.jobs;

import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@RequiredArgsConstructor
public class HelloJob implements Job {

    private final HelloJobService service;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        service.printTime();
        // read all feeds
    }
}
