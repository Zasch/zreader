package de.carinaundzasch.zreader.repository;

import de.carinaundzasch.zreader.model.Entry;
import de.carinaundzasch.zreader.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    List<Entry> findAllByFeedId(Long id);

    List<Entry> findAllByFeed(Feed feed);

}
