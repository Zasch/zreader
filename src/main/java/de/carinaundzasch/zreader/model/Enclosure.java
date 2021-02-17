package de.carinaundzasch.zreader.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Enclosure {

    @Id
    @GeneratedValue(generator = "enclosure_id_seq")
    @SequenceGenerator(name = "enclosure_id_seq", sequenceName = "enclosure_id_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long length;

    private String type;

    private String url;
}
