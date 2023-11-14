package com.vitorfurini.localiza.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PosicoesDto {

    private String licensePlate;
    private Date date;
    private Integer velocity;
    private Double latitude;
    private Double longitude;
    private Boolean ignition;

}
