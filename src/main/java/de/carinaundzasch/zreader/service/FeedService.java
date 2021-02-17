package de.carinaundzasch.zreader.service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import de.carinaundzasch.zreader.mapper.EntryMapper;
import de.carinaundzasch.zreader.model.Feed;
import de.carinaundzasch.zreader.repository.EntryRepository;
import de.carinaundzasch.zreader.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedService {

    private final EntryMapper entryMapper;
    private final FeedRepository feedRepository;
    private final EntryRepository entryRepository;
    private final RestTemplate restTemplate;

    @SneakyThrows
    public void updateAllFeeds() {

        // find all feeds to update
        for (var feed : feedRepository.findAll()) {

            // read feed
            var synd = getSyndFeed(feed.getUrl());

            // find existing entries for feed
            var existingEntries = entryRepository.findAllByFeedId(feed.getId());

            for (var entry : synd.getEntries()) {

                // map syndEntry to Entry adding feed id
                var toSave = entryMapper.syndEntryToEntry(entry);
                toSave.setFeedId(feed.getId());

                // find existing entries with same date
                var hasNoMatch = existingEntries.stream().noneMatch(e ->
                        e.getPublishedDate().compareTo(toSave.getPublishedDate()) == 0);

                // save only if there are no entries with the same date
                if (hasNoMatch) {
                    log.debug("New entry [{}] for feed {}", entry.getTitle(), feed.getTitle());
                    entryRepository.save(toSave);
                } else {
                    log.debug("Ignoring [{}] for feed {}. Duplicate entry!", entry.getTitle(), feed.getTitle());
                }
            }
        }
    }

    @SneakyThrows
    public String getFeedImageOrIcon(String url) {
        var synd = getSyndFeed(url);

        if (synd.getImage() != null) {
            return synd.getImage().getUrl();
        } else if (synd.getIcon() != null) {
            return synd.getIcon().getUrl();
        } else {
            return null;
        }
    }

    @SneakyThrows
    public String getFeedTitle(String url) {
        var synd = getSyndFeed(url);

        if (synd.getTitle() != null) {
            return synd.getTitle();
        } else {
            return "";
        }
    }

    public Feed addFeed(String url) {
        assert url != null;
        return feedRepository.save(
                Feed.builder()
                        .url(url)
                        .title(getFeedTitle(url))
                        .image(getFeedImageOrIcon(url))
                        .build());
    }

    public List<Feed> getAllFeeds() {
        return feedRepository.findAll();
    }

    public void deleteFeedById(Long id) {
        feedRepository.deleteById(id);
    }

    private SyndFeed getSyndFeed(String url) throws FeedException {
        var feed = restTemplate.getForEntity(url, String.class).getBody();
        assert feed != null;
        var sr = new StringReader(feed);
        return new SyndFeedInput().build(sr);
    }
}
