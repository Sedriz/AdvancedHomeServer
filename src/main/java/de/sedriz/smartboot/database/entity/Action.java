package de.sedriz.smartboot.database.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Table(name = "action", indexes = {
        @Index(name = "actions_un", columnList = "name", unique = true)
})
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false, length = 30)
    @Pattern(regexp = "^[a-zA-Z0-9äöüÄÖÜ]{4,30}$", message = "String not matching pattern!")
    private String name;

    @Column(name = "action_data_type", nullable = false)
    private Class<?> actionDataType;
}