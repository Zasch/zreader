package de.carinaundzasch.zreader;

import de.carinaundzasch.zreader.jobs.feed.update.FeedUpdateJobConfig;
import de.carinaundzasch.zreader.model.Feed;
import de.carinaundzasch.zreader.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(value = {FeedUpdateJobConfig.class})
@SpringBootApplication
public class ZreaderApplication implements CommandLineRunner {

    private final FeedRepository feedRepository;

    public static void main(String[] args) {
        SpringApplication.run(ZreaderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        feedRepository.save(Feed.builder().title("Caschys Blog").url("http://feeds2.feedburner.com/stadt-bremerhaven/dqXM").build());
    }
}
