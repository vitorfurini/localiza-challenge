package com.vitorfurini.localiza.service;

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.vitorfurini.localiza.dto.PoiTrackingDto;
import com.vitorfurini.localiza.entity.GeoLocation;
import com.vitorfurini.localiza.entity.Poi;
import com.vitorfurini.localiza.entity.Posicao;
import com.vitorfurini.localiza.filter.PoiTrackingFilter;
import com.vitorfurini.localiza.service.impl.PoiServiceImpl;
import com.vitorfurini.localiza.service.impl.PositionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.grum.geocalc.Point;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;


@Service
public class PoiTrackingService {

    @Autowired
    private PoiServiceImpl poiService;

    @Autowired
    private PositionServiceImpl posistionService;

    private List<PoiTrackingDto> trackingInProcess;
    private List<PoiTrackingDto> trackingClosed;

    public List<PoiTrackingDto> trackPosition(PoiTrackingFilter filter) {
        trackingInProcess = new ArrayList<>();
        trackingClosed = new ArrayList<>();

        final List<Poi> pois;
        final List<Posicao> positions;

        prepareFilter(filter);
        pois = loadPois(filter);
        positions = loadPositions(filter);
        calculeTracking(positions, pois);

        for (PoiTrackingDto poiTrackingDTO : trackingClosed) {
            calculateTime(poiTrackingDTO);
        }

        return trackingClosed;
    }

    private void prepareFilter(PoiTrackingFilter filter) {
        if (filter.getInitDate() == null) {
            filter.setInitDate(new Date(0L));
        }
        if (filter.getEndDate() == null) {
            filter.setEndDate(new Date());
        }
    }

    private List<Poi> loadPois(PoiTrackingFilter filter) {
        if (!StringUtils.hasLength(filter.getPoiName())) {
            return poiService.findAll();
        } else {
            return poiService.findByName(filter.getPoiName());
        }
    }

    private List<Posicao> loadPositions(PoiTrackingFilter filter) {
        List<Posicao> positions;
        if (!StringUtils.hasLength(filter.getLicensePlate())) {
            positions = posistionService.findByDateBetween(filter.getInitDate(), filter.getEndDate());
        } else {
            positions = posistionService.findByDateBetweenAndLicensePlate(filter.getInitDate(), filter.getEndDate(), filter.getLicensePlate());
        }
        Collections.sort(positions);

        return positions;
    }

    private Point getGeoPoint(GeoLocation geo) {
        Coordinate lat = Coordinate.fromDegrees(geo.getLatitude());
        Coordinate lng = Coordinate.fromDegrees(geo.getLongitude());
        return Point.at(lat, lng);
    }

    private Boolean isUnderPoi(Point positionPoint, Point poiPoint, Integer radius) {
        double distance = EarthCalc.gcd.distance(positionPoint, poiPoint);

        return radius.doubleValue() >= distance;
    }

    private PoiTrackingDto getTrackingDTOInProcess(String poiName, String licensePlate) {
        return trackingInProcess
                .stream()
                .filter(dto
                        -> dto.getPoiName().equals(poiName)
                && dto.getLicensePlate().equals(licensePlate)
                ).findFirst()
                .orElse(null);
    }

    private PoiTrackingDto getOrCreateTrackingDTO(String poiName, String licensePlate) {
        PoiTrackingDto track;
        track = getTrackingDTOInProcess(poiName, licensePlate);

        if (track == null) {
            track = new PoiTrackingDto();
            track.setLicensePlate(licensePlate);
            track.setPoiName(poiName);
            track.setTimeInSeconds(0L);

            trackingInProcess.add(track);
        }
        return track;
    }

    private void calculateTime(PoiTrackingDto dto) {
        if (dto.getFirstDate() == null) {
            throw new RuntimeException("Tracking without firstDate");
        }
        if (dto.getLastDate() == null) {
            throw new RuntimeException("Tracking without lastDate");
        }

        LocalDateTime firstDate = dto.getFirstDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime lastDate = dto.getLastDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Long time = SECONDS.between(firstDate, lastDate);
        dto.setTimeInSeconds(time);
    }

    private void calculeTracking(List<Posicao> positions, List<Poi> pois) {
        PoiTrackingDto dto;

        for (Poi poi : pois) {
            String poiName = poi.getName();
            Point poiPoint = getGeoPoint(poi);

            for (Posicao position : positions) {
                String licensePlate = position.getLicensePlate();
                Date positionDate = position.getDate();
                Point positionPoint = getGeoPoint(position);

                Boolean isUnderPoi = isUnderPoi(positionPoint, poiPoint, poi.getRadius());

                if (isUnderPoi) {
                    dto = getOrCreateTrackingDTO(poiName, licensePlate);
                    if (dto.getFirstDate() == null) {
                        dto.setFirstDate(positionDate);
                    }
                    dto.setLastDate(positionDate);
                } else {
                    dto = getTrackingDTOInProcess(poiName, licensePlate);
                    if (dto != null) {
                        trackingInProcess.remove(dto);
                        trackingClosed.add(dto);
                    }
                }

            }
        }
        trackingClosed.addAll(trackingInProcess);
    }
}
