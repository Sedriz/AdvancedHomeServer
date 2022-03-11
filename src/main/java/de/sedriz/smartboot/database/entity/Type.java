package de.sedriz.smartboot.database.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Table(name = "type")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 30, unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9äöüÄÖÜ]{4,30}$", message = "String not matching pattern!")
    private String name;

    @Column(name = "reachable", nullable = false)
    private boolean reachable = false;

    @ManyToMany(cascade = { CascadeType.REFRESH })
    @JoinTable(
            name = "Type_Action",
            joinColumns = { @JoinColumn(name = "type_id") },
            inverseJoinColumns = { @JoinColumn(name = "action_id") }
    )
    Set<Action> actions = new HashSet<>();
}