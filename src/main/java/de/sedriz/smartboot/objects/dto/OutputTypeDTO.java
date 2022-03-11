package de.sedriz.smartboot.objects.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputTypeDTO {
    private int id;
    private String name;
    private boolean reachable;
    private Set<OutputActionDTO> actionsSet;
}
