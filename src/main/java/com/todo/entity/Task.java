package com.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Task {

    private boolean checkbox;
    private String taskItem;

    protected Task() {
    }


}
