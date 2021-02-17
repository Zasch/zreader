package de.carinaundzasch.zreader.repository;

import de.carinaundzasch.zreader.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
