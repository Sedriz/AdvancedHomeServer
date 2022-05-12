package de.sedriz.smartboot.database.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "video")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "length")
    private Float length;

    @Column(name = "quality")
    private short quality = 0;

    @Column(name = "hidden")
    private boolean hidden = false;

    @Column(name = "upload_date")
    private Date uploadDate = new Date();

    @Column(name = "location", unique = true, nullable = false)
    private String location;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

}
