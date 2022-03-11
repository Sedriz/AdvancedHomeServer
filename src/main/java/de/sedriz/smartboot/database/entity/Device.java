package de.sedriz.smartboot.database.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.LinkedHashSet;
import java.util.Set;

@Table(name = "device", indexes = {
        @Index(name = "device_un", columnList = "name", unique = true)
})
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 30)
    @Pattern(regexp = "^[a-zA-Z0-9äöüÄÖÜ]{4,30}$", message = "String not matching pattern!")
    private String name;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "type", nullable = false)
    private Type type;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "data_type_devices",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "data_type_id"))
    private Set<DataType> dataTypes = new LinkedHashSet<>();

    @Column(name = "button_controlled", nullable = false)
    private Boolean buttonControlled = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

}