package de.carinaundzasch.zreader.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feed {

    @Id
    @GeneratedValue(generator = "feed_id_seq")
    @SequenceGenerator(name = "feed_id_seq", sequenceName = "feed_id_seq", allocationSize = 1)
    private Long id;

    private String title;

    private String url;

    private String imageUrl;

    private String imageOnDisk;

    private String author;

    private String language;

    private String feedType;

    private String link;

    private String description;

    @ToString.Exclude
    @OneToMany(mappedBy = "feed", fetch = FetchType.EAGER)
    private List<Entry> entries;
}
