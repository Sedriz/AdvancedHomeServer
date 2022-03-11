package de.sedriz.smartboot.objects.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimestampRequestDTO {
    private Date date;
}
