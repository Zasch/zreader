package de.carinaundzasch.zreader.controller;

import de.carinaundzasch.zreader.model.Feed;
import de.carinaundzasch.zreader.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/feeds")
public class FeedController {

    private final FeedService feedService;

    @GetMapping
    public ResponseEntity<List<Feed>> getAllFeeds(Principal principal) {
        System.err.println("Principal: " + principal.getName());
        return ResponseEntity.ok(feedService.getAllFeeds());
    }

    @PostMapping
    public ResponseEntity<Feed> addFeed(@RequestBody String url) {
        return ResponseEntity.ok(feedService.addFeed(url));
    }

    @DeleteMapping
    public void deleteFeed(@RequestParam Long id) {
        feedService.deleteFeedById(id);
    }
}
