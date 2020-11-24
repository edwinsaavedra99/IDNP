package com.myappdeport.model.entity.functional;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NutritionalAdvice extends Entity {
    private String title;
    private String shortDescription;
    private String longDescription;
    private String imageUrlCloudStorage;
}
