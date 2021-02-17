package de.carinaundzasch.zreader.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feed {

    @Id
    @GeneratedValue(generator = "feed_id_seq")
    @SequenceGenerator(name = "feed_id_seq", sequenceName = "feed_id_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String url;

    private String image;
}
