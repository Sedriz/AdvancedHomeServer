package de.sedriz.smartboot.objects.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ButtonEventDTO {
    @NotNull
    @PastOrPresent
    private Date timestamp;
    
    private int locationId;

    private boolean isMotionEvent;
}
