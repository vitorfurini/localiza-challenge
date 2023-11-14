package com.vitorfurini.localiza.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "poi")
public class Poi implements GeoLocation {

    @Id
    private String id;
    private String name;
    private Integer radius;
    private Double latitude;
    private Double longitude;

    public Poi(String name, Integer radius, Double latitude, Double longitude) {
        this.name = name;
        this.radius = radius;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Poi{" + "id=" + id + ", name=" + name + ", radius=" + radius + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }
}
