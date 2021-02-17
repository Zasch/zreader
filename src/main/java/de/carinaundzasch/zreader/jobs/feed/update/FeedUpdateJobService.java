package de.carinaundzasch.zreader.jobs.feed.update;

import de.carinaundzasch.zreader.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedUpdateJobService {

    private final FeedService feedService;

    public void printTime() {
        log.info("Updating feeds at {}", LocalDateTime.now().toString());
        feedService.updateAllFeeds();
    }
}
