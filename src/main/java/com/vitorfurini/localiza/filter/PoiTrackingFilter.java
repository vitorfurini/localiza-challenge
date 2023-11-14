package com.vitorfurini.localiza.filter;

import lombok.Data;

import java.util.Date;

@Data
public class PoiTrackingFilter {

    private String poiName;
    private String licensePlate;
    private Date initDate;
    private Date endDate;
}
