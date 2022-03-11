package de.sedriz.smartboot.objects.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeDTO {
    private String name;
    private boolean reachable;
    private Set<Integer> actionIdSet;
}
