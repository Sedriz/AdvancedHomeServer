package de.sedriz.smartboot.database.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "device_data")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class DeviceData {
    @EmbeddedId
    private DeviceDataId id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "device_id", insertable = false, updatable = false)
    private Device device;

    @ManyToOne(optional = false)
    @JoinColumn(name = "data_type_id", nullable = false)
    private DataType dataType;

    @Column(name = "data", nullable = false)
    private String data;

}