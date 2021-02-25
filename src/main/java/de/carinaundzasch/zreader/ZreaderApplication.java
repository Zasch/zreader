package de.carinaundzasch.zreader;

import de.carinaundzasch.zreader.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties
@SpringBootApplication
public class ZreaderApplication implements CommandLineRunner {

    private final FeedService feedService;

    public static void main(String[] args) {
        SpringApplication.run(ZreaderApplication.class, args);
    }

    @Override
    public void run(String... args) {

//        log.error(proxy.getImageUrl("https://dilbert.com/strip/2021-02-23"));
        feedService.addFeed("https://dilbert.com/feed");

//        feedService.addFeed("http://feeds2.feedburner.com/stadt-bremerhaven/dqXM");

//        feedRepository.save(Feed.builder().title("Caschys Blog").url("http://feeds2.feedburner.com/stadt-bremerhaven/dqXM").build());
    }
}
