package de.carinaundzasch.zreader.mapper;

import com.rometools.rome.feed.synd.SyndEntry;
import de.carinaundzasch.zreader.model.Entry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EntryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", expression = "java(source.getContents().get(0).getValue())")
    @Mapping(target = "description", source = "description.value") //, defaultExpression = "java(source.getDescription().getValue())")
    @Mapping(target = "feed", ignore = true)
    Entry syndEntryToEntry(SyndEntry source);
}
