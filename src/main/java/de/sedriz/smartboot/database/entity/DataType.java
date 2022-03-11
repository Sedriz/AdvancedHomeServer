package de.sedriz.smartboot.database.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Table(name = "data_type", indexes = {
        @Index(name = "data_type_un", columnList = "name", unique = true)
})
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class DataType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 30)
    @Pattern(regexp = "^[a-zA-Z0-9äöüÄÖÜ]{4,30}$", message = "String not matching pattern!")
    private String name;
}