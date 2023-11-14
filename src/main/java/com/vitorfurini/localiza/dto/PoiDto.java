package com.vitorfurini.localiza.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PoiDto {

    private String name;
    private Integer radius;
    private Double latitude;
    private Double longitude;

}
