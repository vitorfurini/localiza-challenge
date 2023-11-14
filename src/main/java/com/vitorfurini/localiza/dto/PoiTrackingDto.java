package com.vitorfurini.localiza.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PoiTrackingDto {

    private String licensePlate;
    private String poiName;
    private Long timeInSeconds;
    private Date firstDate;
    private Date lastDate;
}
