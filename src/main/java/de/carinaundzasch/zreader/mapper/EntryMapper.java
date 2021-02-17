package de.carinaundzasch.zreader.mapper;

import com.rometools.rome.feed.synd.SyndEntry;
import de.carinaundzasch.zreader.model.Entry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntryMapper {

    @Mapping(target = "id", ignore = true)
    Entry syndEntryToEntry(SyndEntry source);

}
