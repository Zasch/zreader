package de.carinaundzasch.zreader.mapper;

import com.rometools.rome.feed.synd.SyndFeed;
import de.carinaundzasch.zreader.model.Feed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "url", source = "uri")
    @Mapping(target = "imageOnDisk", ignore = true)
    @Mapping(target = "imageUrl", ignore = true) //, expression = "java(source.getImage().getUrl())")
    @Mapping(target = "entries", ignore = true)
    Feed syndFeedToFeed(SyndFeed source);
}
