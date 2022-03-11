package de.sedriz.smartboot.objects.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateDTO {
    private boolean isActive;
    private Date timestamp;
}
