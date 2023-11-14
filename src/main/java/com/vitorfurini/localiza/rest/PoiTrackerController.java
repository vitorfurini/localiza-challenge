package com.vitorfurini.localiza.rest;

import com.vitorfurini.localiza.dto.PoiTrackingDto;
import com.vitorfurini.localiza.filter.PoiTrackingFilter;
import com.vitorfurini.localiza.service.PoiTrackingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "POI Tracker Controller")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/poiTracker")
public class PoiTrackerController {

    @Autowired
    private PoiTrackingService service;

    @PostMapping("/track")
    @ApiOperation("Recurso disponível para fazer a consulta se um determinado veículo está/esteve no POI")
    public ResponseEntity<List<PoiTrackingDto>> search(@RequestBody PoiTrackingFilter filter) {
        List<PoiTrackingDto> result = service.trackPosition(filter);

        if (CollectionUtils.isEmpty(result)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
