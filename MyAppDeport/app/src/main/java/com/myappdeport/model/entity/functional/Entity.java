package com.myappdeport.model.entity.functional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Entity {
    protected Long id;
    protected String documentId;
}
