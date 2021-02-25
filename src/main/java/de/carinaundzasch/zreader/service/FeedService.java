package de.carinaundzasch.zreader.service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import de.carinaundzasch.zreader.configuration.IconConfig;
import de.carinaundzasch.zreader.mapper.EntryMapper;
import de.carinaundzasch.zreader.mapper.FeedMapper;
import de.carinaundzasch.zreader.model.Feed;
import de.carinaundzasch.zreader.repository.EntryRepository;
import de.carinaundzasch.zreader.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedService {

    private final IconConfig iconConfig;
    private final EntryMapper entryMapper;
    private final FeedMapper feedMapper;
    private final FeedRepository feedRepository;
    private final EntryRepository entryRepository;
    private final RestTemplate restTemplate;

    private final ImageProxyService proxyService;

    private final RestTemplateBuilder templateBuilder;

    @SneakyThrows
    public void updateAllFeeds() {

        // find all feeds to update
        for (var feed : feedRepository.findAll()) {
            updateFeed(feed);
        }
    }

    @SneakyThrows
    private void updateFeed(Feed feed) {
        // read feed
        var synd = getSyndFeed(feed.getUrl());

        // find existing entries for feed
        var existingEntries = entryRepository.findAllByFeed(feed);

        var proxyNeeded = proxyService.isImageProxyNeeded(feed.getUrl());

        for (var syndEntry : synd.getEntries()) {

            // map syndEntry to Entry
            var entry = entryMapper.syndEntryToEntry(syndEntry);
            entry.setFeed(feed);

            // get image (Comic) content instead of link to comic
            if (proxyNeeded) {
                var imgUrl = proxyService.getImageUrl(entry.getLink());

                StringBuilder sb = new StringBuilder(entry.getContent());
                sb.append("<br><br>");
                sb.append(imgUrl);
                entry.setContent(sb.toString());

                log.info(entry.getContent());
            }

            // find existing entries with same date
            var pubDateMatch = false;
            var updateDateMatch = false;

            if (!existingEntries.isEmpty()) {
                if (entry.getPublishedDate() != null) {
                    pubDateMatch = existingEntries.stream().filter(e -> e.getPublishedDate() != null)
                            .noneMatch(e -> e.getPublishedDate().compareTo(entry.getPublishedDate()) == 0);
                } else if (entry.getUpdatedDate() != null) {
                    updateDateMatch = existingEntries.stream().filter(e -> e.getUpdatedDate() != null)
                            .noneMatch(e -> e.getUpdatedDate().compareTo(entry.getUpdatedDate()) == 0);
                }
            }

            // save only if there are no entries with the same date
            if (!pubDateMatch && !updateDateMatch) {
                log.debug("New entry [{}] for feed {}", syndEntry.getTitle(), feed.getTitle());
                entryRepository.save(entry);
            } else {
                log.debug("Ignoring [{}] for feed {}. Duplicate entry!", syndEntry.getTitle(), feed.getTitle());
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

    private Path downloadIcon(String url, String feedTitle) {

        var name = feedTitle.replaceAll(" ", "_").toLowerCase();
        var ext = url.substring(url.lastIndexOf('.'));
        var path = iconConfig.getPath() + "/" + name + ext;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<byte[]> response = templateBuilder.build()
                    .exchange(url, HttpMethod.GET, entity, byte[].class);
            return Files.write(Paths.get(path), Objects.requireNonNull(response.getBody()));
        } catch (Exception e) {
            log.warn("Cannot download image from {}", url, e);
        }
        return null;
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

    @SneakyThrows
    public Feed addFeed(String url) {
        assert url != null;
        var f = getFeed(getSyndFeed(url));
        if (f.getImageUrl() != null) {
            var imageOnDisk = downloadIcon(f.getImageUrl(), f.getTitle());
            assert imageOnDisk != null;
            f.setImageOnDisk(imageOnDisk.toString());
        }
        f.setUrl(url);

        return feedRepository.save(f);
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

    private Feed getFeed(SyndFeed syndFeed) {
        return feedMapper.syndFeedToFeed(syndFeed);
    }
}
