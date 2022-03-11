package de.sedriz.smartboot.objects.dto;

import de.sedriz.smartboot.database.entity.Action;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutputActionDTO {
    private int id;
    private String name;
    private Class<?> actionDataType;

    public OutputActionDTO(Action action) {
        this.id = action.getId();
        this.name = action.getName();
        this.actionDataType = action.getActionDataType();
    }
}