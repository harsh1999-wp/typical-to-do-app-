package com.example.Miss.Minutes.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTodoDto {

    private String title;
    private String description;
    private Boolean completed;

    public Boolean isCompleted() {
        return completed;
    }
}
