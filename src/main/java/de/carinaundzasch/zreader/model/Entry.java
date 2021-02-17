package de.carinaundzasch.zreader.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.engine.backend.types.Searchable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Indexed
public class Entry {

    @Id
    @GeneratedValue(generator = "entry_id_seq")
    @SequenceGenerator(name = "entry_id_seq", sequenceName = "entry_id_seq", allocationSize = 1)
    private Long id;

    private Long feedId;

    @FullTextField(searchable = Searchable.YES)
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    private Content titleEx;

    private String author;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Author> authors;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Author> contributors;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Category> categories;

    private String comments;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "entry")
    private Content description;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Enclosure> enclosures;

//    @FullTextField(searchable = Searchable.YES)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entry")
    private List<Content> contents;

    @FullTextField(searchable = Searchable.YES)
    private String link;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Link> links;

    private Instant publishedDate;

    private Instant updatedDate;

    private String uri;
}
