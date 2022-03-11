package de.sedriz.smartboot.database.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "location")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 30)
    @Pattern(regexp = "^[a-zA-Z0-9äöüÄÖÜ]{4,30}$", message = "String not matching pattern!")
    private String name;

    @Column(name = "description", length = 100)
    @Pattern(regexp = "^[a-zA-Z0-9äöüÄÖÜ\\s]{4,100}$", message = "String not matching pattern!")
    private String description;

}