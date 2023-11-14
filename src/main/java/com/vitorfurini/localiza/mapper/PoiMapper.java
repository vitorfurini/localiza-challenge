package com.vitorfurini.localiza.mapper;

import com.vitorfurini.localiza.dto.PoiDto;
import com.vitorfurini.localiza.entity.Poi;
import org.springframework.stereotype.Component;

@Component
public class PoiMapper {

    public Poi toEntity(PoiDto poidto) {
        return new Poi(
                poidto.getName(),
                poidto.getRadius(),
                poidto.getLatitude(),
                poidto.getLongitude()
        );
    }

    public PoiDto toDto(Poi poi) {
        return new PoiDto(
                poi.getName(),
                poi.getRadius(),
                poi.getLatitude(),
                poi.getLongitude()
        );
    }
}
